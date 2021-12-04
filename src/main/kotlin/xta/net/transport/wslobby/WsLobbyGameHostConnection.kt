package xta.net.transport.wslobby

import xta.net.transport.AbstractConnection
import xta.net.transport.AbstractHostConnection
import xta.game.settings.GameSettings
import org.khronos.webgl.Uint8Array
import xta.logging.LogManager
import kotlin.js.Promise

/*
 * Created by aimozg on 30.11.2021.
 */
class WsLobbyGameHostConnection(
	val url:String,
	val identity:String,
	val token:String,
	val roomId:String
) : AbstractHostConnection() {
	private var ws: WsLobbyHost? = null
	private val guests = HashMap<String,GuestAsClient>()
	override val guestList: Collection<AbstractConnection>
		get() = guests.values

	inner class GuestAsClient(val guest: WsLobbyHost.Guest): AbstractConnection() {
		override fun send(message: Uint8Array) {
			guest.whisper(message)
		}

		override val isConnected: Boolean
			get() = guest.inRoom
		override val identity: String
			get() = guest.identity
		override val displayName: String
			get() = guest.greeting

		override fun close(reason: String) {
			if (isActive && guest.inRoom) {
				guest.kick(reason)
			}
		}

		init {
			registerEventTypes("message","disconnect")
		}

	}

	override val isActive: Boolean
		get() = ws?.state == WsLobbyClient.State.INROOM

	override fun inviteCode(): String {
		return "wsl-$roomId"
	}

	override fun register():Promise<WsLobbyGameHostConnection> {
		var resolved = false
		return Promise { resolve, reject ->
			val ws = WsLobbyHost(url, identity, token, roomId)
			console.log("Host init",ws)
			this.ws = ws

			ws.onRoomEntry {
				emitReady()
				resolved = true
				ws.sendPeriodicPing(GameSettings.data.wsPingInterval)
				resolve(this)
			}
			ws.onClose { reason ->
				emitClose(reason)
				if (!resolved) {
					reject(Error(reason))
				}
			}
			ws.onGuestJoined {
				val gac = GuestAsClient(it)
				guests[it.identity] = gac
				emitGuest(gac)
			}
			ws.onGuestLeft { guest, reason ->
				val gac = guests[guest.identity]
				if (gac != null) {
					gac.emitDisconnectEvent(reason.ifEmpty { "(Unknown reason)" })
					guests.remove(guest.identity)
					emitGuestLeave(gac, reason)
				}
			}
			ws.onData { sender, data ->
				guests[sender]?.emitMessageEvent(data)
			}
		}
	}

	override fun close(reason: String) {
		if (ws?.isSocketConnected == true) {
			ws?.close(reason)
		}
	}

	override val address: String
		get() = identity

	companion object {
		private val logger = LogManager.getLogger("xta.net.transport.wslobby.WsLobbyGameHostConnection")
	}
}

