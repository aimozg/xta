package xta.net.transport.wslobby

import xta.utils.encodeToArray
import org.w3c.dom.TextEncoder
import xta.utils.jsObject
import org.khronos.webgl.Uint8Array
import kotlin.js.Json

class WsLobbyHost(
	url: String,
	identity: String,
	token: String,
	val roomId: String
) : WsLobbyClient(url, identity, token, wslobby.Role.HOST) {

	val knockingGuests = HashMap<String,Guest>()
	val guests = HashMap<String,Guest>()
	var bouncer:(Guest)->Unit = { it.allow() }

	init {
		registerEventTypes(EVENT_GUEST_JOINED, EVENT_GUEST_LEFT)
	}

	inner class Guest(
		val identity:String,
		val greeting:String
	) {
		var inRoom = false; private set
		private var knocking:Boolean = true
		internal fun left(reason:String) {
			knocking = false
			inRoom = false
			knockingGuests.remove(identity)
			guests.remove(identity)
			emit(EVENT_GUEST_LEFT, this, reason)
		}
		fun allow() {
			if (knocking) {
				knocking = false
				inRoom = true
				guests[identity] = this
				knockingGuests.remove(identity)
				emit(EVENT_GUEST_JOINED, this)
				send {
					id = mid++
					allowGuest = jsObject {
						guestId = identity
					}
				}
			}
		}
		fun kick(reason:String) {
			send {
				id = mid++
				kickGuest = jsObject {
					guestId = identity
					message = reason
				}
			}
		}
		fun whisper(data:Uint8Array) {
			send {
				id = mid++
				unicast = jsObject {
					guestId = identity
					this.data = data
				}
			}
		}
		fun whisper(data:String) {
			whisper(TextEncoder().encode(data))
		}
		fun whisper(data: Json) {
			whisper(data.encodeToArray())
		}
	}

	fun broadcast(data:Uint8Array) {
		send {
			id = mid++
			broadcast = jsObject {
				this.data = data
			}
		}
	}
	fun broadcast(data:String) {
		broadcast(TextEncoder().encode(data))
	}
	fun broadcast(data: Json) {
		broadcast(data.encodeToArray())
	}

	override fun afterAuthorized() {
		send {
			id = mid++
			claimRoom = jsObject {
				roomId = this@WsLobbyHost.roomId
			}
		}
	}

	override fun handleGuestLeft(msg: wslobby.Reply) {
		val guestId = msg.guestLeft!!.guestId!!
		val reason = msg.guestLeft!!.goodbye!!
		guests[guestId]?.left(reason)
		knockingGuests[guestId]?.left(reason)
	}

	override fun handleGuestKnock(msg: wslobby.Reply) {
		val guest = Guest(msg.guestKnock!!.guestId!!, msg.guestKnock!!.greeting!!)
		knockingGuests[guest.identity] = guest
		bouncer(guest)
	}

	fun onGuestJoined(callback:(guest:Guest)->Unit) {
		this.on(EVENT_GUEST_JOINED, callback)
	}
	fun onGuestLeft(callback:(guest:Guest, reason:String)->Unit) {
		this.on(EVENT_GUEST_LEFT, callback)
	}

	companion object {
		const val EVENT_GUEST_JOINED = "guest-joined"
		const val EVENT_GUEST_LEFT = "guest-left"
	}
}
