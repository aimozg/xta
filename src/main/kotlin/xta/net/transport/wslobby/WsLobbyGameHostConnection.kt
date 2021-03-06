package xta.net.transport.wslobby

import kotlinx.browser.window
import org.khronos.webgl.Uint8Array
import org.w3c.dom.btoa
import xta.game.settings.GameSettings
import xta.logging.LogManager
import xta.logging.Logger
import xta.net.transport.AbstractConnection
import xta.net.transport.AbstractHostConnection
import xta.utils.fullpath
import xta.utils.stringify
import kotlin.js.Promise

/*
 * Created by aimozg on 30.11.2021.
 */
class WsLobbyGameHostConnection(
	val url:String,
	override val identity:String,
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
		// if lobby is on same host&port
		// ex. https://example.com/game with lobby wss://example.com/lobby
		// remove origin from lobby url ("/lobby")
		val sameurl = window.location.origin.replace(Regex("^http"),"ws")
		val inviteUrl =
			if (url.startsWith(sameurl)) url.replace(Regex("^wss?://[^/]+"),"")
			else url
		return window.location.fullpath.substringBefore('#') + "#" +
				btoa(arrayOf("join",inviteUrl,roomId).stringify())
	}

	override fun register():Promise<WsLobbyGameHostConnection> {
		var resolved = false
		return Promise { resolve, reject ->
			val ws = WsLobbyHost(url, this.identity, token, roomId)
			logger.logObject(Logger.Level.DEBUG, this, "Host init",ws)
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

	companion object {
		private val logger = LogManager.getLogger("xta.net.transport.wslobby.WsLobbyGameHostConnection")
	}
}

