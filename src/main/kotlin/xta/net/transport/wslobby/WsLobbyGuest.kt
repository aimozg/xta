package xta.net.transport.wslobby

import xta.utils.encodeToArray
import xta.utils.jsObject
import org.khronos.webgl.Uint8Array
import org.w3c.dom.TextEncoder
import kotlin.js.Json

class WsLobbyGuest(
	url: String,
	identity: String,
	token: String,
	val roomId: String,
	val greeting: String
) : WsLobbyClient(url, identity, token, wslobby.Role.GUEST) {

	fun sendData(data: Uint8Array) {
		send {
			id = mid++
			broadcast = jsObject {
				this.data = data
			}
		}
	}
	fun sendData(data:String) {
		sendData(TextEncoder().encode(data))
	}
	fun sendData(data: Json) {
		sendData(data.encodeToArray())
	}

	override fun afterAuthorized() {
		send {
			id = mid++
			joinRoom = jsObject {
				roomId = this@WsLobbyGuest.roomId
				greeting = this@WsLobbyGuest.greeting
			}
		}
	}
}
