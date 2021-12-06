package xta.net.protocol

import org.khronos.webgl.Uint8Array
import xta.Game
import xta.Player
import xta.ScreenManager
import xta.game.PlayerCharacter
import xta.logging.LogManager
import xta.net.transport.AbstractGuestConnection
import xta.utils.decodeToJson
import xta.utils.stringify

class LocalGuestProtocol(
	override val player: Player,
	val connection: AbstractGuestConnection?
): GuestProtocol() {
	override val isConnected: Boolean
		get() = connection?.isConnected != false
	override val identity: String
		get() = connection?.identity?:"[LocalHost]"

	override fun toLogString() = connection?.toLogString()?:"[LocalHost]"

	override fun onMessage(message: MessageToGuest) {
		logger.ifdebug(this) { "< ${message.stringify()}" }
		message.chat?.let {
			ScreenManager.displayChatMessage(it)
			return
		}
		message.charAccepted?.let {
			Game.hostProtocol.sendStatusRequest(screen=true)
			return
		}
		message.charRejected?.let {
			Game.localErrorMessage("Your character was rejected by server: ${it.message}")
			return
		}
		message.statusUpdate?.let { msg ->
			msg.char?.let {
				Game.me.char = PlayerCharacter().apply { deserializeFromJson(it) }
				ScreenManager.updateCharacter()
			}
			msg.screen?.let {
				Game.me.screen = it
				ScreenManager.displayScreen()
			}
			return
		}
		message.sceneTransition?.let { msg ->
			Game.me.screen = msg.screen
			ScreenManager.displayScreen()
			return
		}
		logger.error(this,"Received bad message "+message.stringify())
	}

	fun onRawMessage(message: Uint8Array) {
		try {
			@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
			val json = message.decodeToJson() as MessageToGuest
			onMessage(json)
		} catch (e:Throwable) {
			logger.warn(this,"Malformed message from host",e)
		}
	}

	companion object {
		private val logger by lazy {
			LogManager.getLogger("xta.net.protocol.LocalRequestHandler")
		}
	}
}
