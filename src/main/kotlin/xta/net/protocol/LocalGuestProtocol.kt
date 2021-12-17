package xta.net.protocol

import org.khronos.webgl.Uint8Array
import xta.Game
import xta.Player
import xta.ScreenManager
import xta.game.PlayerCharacter
import xta.game.combat.Combat
import xta.game.scenes.Limbo
import xta.logging.LogManager
import xta.net.transport.AbstractGuestConnection
import xta.utils.decodeToJson
import xta.utils.stringify
import kotlin.js.Json

class LocalGuestProtocol(
	override val player: Player,
	val connection: AbstractGuestConnection?
): GuestProtocol() {
	override val isConnected: Boolean
		get() = connection?.isConnected != false
	override val identity: String
		get() = connection?.identity?:"[LocalHost]"

	override fun logContextLabel() = connection?.logContextLabel()?:"[LocalHost]"

	override fun onMessage(message: MessageToGuest) {
		logger.ifdebug(this) { "< ${message.stringify()}" }
		message.chat?.let {
			ScreenManager.displayChatMessage(it)
			return
		}
		message.charAccepted?.let { msg ->
			Game.setMyPlayerId(msg.yourId)
			Game.hostProtocol.sendStatusRequest(screen=true)
			return
		}
		message.charRejected?.let {
			Game.localErrorMessage("Your character was rejected by server: ${it.message}")
			return
		}
		message.statusUpdate?.let { msg ->
			msg.char?.let {
				player.char = PlayerCharacter().apply { deserializeFromJson(it) }
				ScreenManager.updateCharacter()
			}
			msg.screen?.let {
				player.screen = it
				ScreenManager.displaySceneContent()
			}
			return
		}
		message.sceneTransition?.let { msg ->
			player.screen = msg.screen
			ScreenManager.displaySceneContent()
			return
		}
		message.combatUpdate?.let { cum -> /* yes, and?*/
			if (cum.inCombat == true) {
				for (id in ((cum.partyA?: emptyArray()) + (cum.partyB?: emptyArray()))) {
					@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
					Game.requireKnownPlayer(id, cum.playerData?.get(id) as Json?)
				}
				cum.myContent?.let { player.screen.content = it }
				player.screen.actions = cum.myActions ?: emptyArray()
				if (cum.ongoing == true && cum.actingPlayerId != player.id) {
					for (action in player.screen.actions) {
						action.disabled = true
					}
				}
				// TODO check if party changes mid-combat
				if (!player.inCombat) {
					player.combat = Combat(
						Combat.Party(
							(cum.partyA?: emptyArray()).mapTo(ArrayList()) {
								Game.requireKnownPlayer(it)
							}
						),
						Combat.Party(
							(cum.partyB?: emptyArray()).mapTo(ArrayList()) {
								Game.requireKnownPlayer(it)
							}
						),
						Limbo.scene
					)
					ScreenManager.transitionToCombat()
				} else {
					ScreenManager.updateCombatScreen(canChangeScreen=true)
				}
			} else {
				// TODO this conflicts between local host and local guest
				ScreenManager.transitionOutOfCombat()
				player.combat?.ongoing = false
				// player.sendScreen()
			}
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
