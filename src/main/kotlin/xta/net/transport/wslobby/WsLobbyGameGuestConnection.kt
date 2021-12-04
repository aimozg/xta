package xta.net.transport.wslobby

import xta.net.transport.AbstractGuestConnection
import xta.game.settings.GameSettings
import org.khronos.webgl.Uint8Array
import kotlin.js.Promise

class WsLobbyGameGuestConnection(
	val url:String,
	override val identity:String,
	val token:String,
	override val displayName: String
): AbstractGuestConnection() {
	private var ws: WsLobbyGuest? = null

	override fun connect(invite: String) = Promise<WsLobbyGameGuestConnection> { resolve, reject ->
		val ws = WsLobbyGuest(url,
			this.identity,token,invite.removePrefix("wsl-"),displayName)
		this.ws = ws
		var resolved = false
		ws.onRoomEntry {
			emitConnectEvent()
			resolved = true
			ws.sendPeriodicPing(GameSettings.data.wsPingInterval)
			resolve(this)
		}
		ws.onClose { reason ->
			emitDisconnectEvent(reason.ifEmpty { "(Unknown reason)" })
			if (!resolved) {
				reject(Error(reason))
			}
		}
		ws.onData { _, data ->
			emitMessageEvent(data)
		}
	}

	override fun send(message: Uint8Array) {
		ws?.sendData(message)
	}

	override fun close(reason: String) {
		ws?.close(reason)
	}

	override val isConnected: Boolean
		get() = ws?.state == WsLobbyClient.State.INROOM

}
