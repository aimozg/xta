package xta

import xta.game.GameLocation
import xta.game.PlayerCharacter
import xta.game.Scene
import xta.game.combat.AbstractCombatAction
import xta.game.combat.Combat
import xta.game.scenes.Limbo
import xta.logging.LogContext
import xta.net.protocol.GuestProtocol
import xta.net.protocol.RemoteGuestProtocol
import xta.net.protocol.messages.ScreenJson
import xta.net.transport.DeadConnection
import xta.text.RemoteDisplay

/*
 * Created by aimozg on 01.12.2021.
 */

@Suppress("NON_EXPORTABLE_TYPE")
@JsExport
class Player(
	var id:String,
	val isMe: Boolean
) : LogContext {

	// BOTH HOST AND GUEST
	override fun logContextLabel(): String =
		if (isHost && isMe) "[LocalHost]" else guest.logContextLabel()
	override fun toString() = "Player($id)"

	var isHost = false
	val isConnected get() = isHost || guest.isConnected
	val chatName
		get() =
			if (charLoaded) char.chatName
			else "[${guest.identity}]" // TODO username?
	var char = PlayerCharacter()
		set(value) {
			field = value
			charLoaded = true
		}
	var charLoaded = false
	var screen: ScreenJson
		get() = display.screen
		set(value) {
			display.screen = value
		}
	val isOnline get() = isMe || guest.isConnected

	// HOST
	var guest: GuestProtocol = RemoteGuestProtocol(this, DeadConnection())
	val display = RemoteDisplay(this)
	var location: GameLocation = Limbo
		set(value) {
			if (field != value) {
				field.playerLeft(this)
				field = value
				value.playerEntered(this)
			}
		}
	var scene: Scene = Limbo.scene
	fun replayScene() {
		Game.server?.playScene(this)
	}
	fun sendScreen() {
		Game.server?.sendScreen(this)
	}
	fun sendCharUpdate() {
		Game.server?.sendStatusUpdate(this, char=true)
	}
	fun sendFullCombatStatus() {
		Game.server?.sendCombatStatus(this, true)
	}
	fun notify(message:String, style:String="-info") {
		Game.server?.sendChatNotification(this, message, style)
	}

	/*
	 *     ██████  ██████  ███    ███ ██████   █████  ████████
	 *    ██      ██    ██ ████  ████ ██   ██ ██   ██    ██
	 *    ██      ██    ██ ██ ████ ██ ██████  ███████    ██
	 *    ██      ██    ██ ██  ██  ██ ██   ██ ██   ██    ██
	 *     ██████  ██████  ██      ██ ██████  ██   ██    ██
	 *
	 *
	 */

	// BOTH HOST AND GUEST
	var combat: Combat? = null
	val inCombat get() = combat != null

	// HOST
	var party: Combat.Party = Combat.Party(listOf(this))
	var combatActions: List<AbstractCombatAction> = emptyList()
}
