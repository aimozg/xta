package xta

import xta.game.GameLocation
import xta.game.PlayerCharacter
import xta.game.Scene
import xta.game.combat.Combat
import xta.game.scenes.Limbo
import xta.logging.LogContext
import xta.net.protocol.GuestProtocol
import xta.net.protocol.RemoteGuestProtocol
import xta.net.protocol.messages.ScreenJson
import xta.net.transport.DeadConnection
import xta.text.Parser
import xta.text.RemoteDisplay

/*
 * Created by aimozg on 01.12.2021.
 */

class Player(
	val isMe: Boolean
) : LogContext {
	var guest: GuestProtocol = RemoteGuestProtocol(this, DeadConnection())
	val id:String get() = guest.identity // TODO make connection-independent constant?
	override fun toLogString(): String =
		if (isHost && isMe) "[LocalHost]" else guest.toLogString()
	override fun toString() = "Player($id)"

	var isHost = false
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

	val isOnline get() = isMe || guest.isConnected

	val display = RemoteDisplay(this, Parser(this, this))
	var location: GameLocation = Limbo
		set(value) {
			if (field != value) {
				field.playerLeft(this)
				field = value
				value.playerEntered(this)
			}
		}
	var scene: Scene = Limbo.scene
	var screen: ScreenJson
		get() = display.screen
		set(value) {
			display.screen = value
		}
	fun replayScene() {
		Game.server?.playScene(this)
	}
	fun updateScreen() {
		Game.server?.updateScreen(this)
	}
	fun notify(message:String, style:String="-info") {
		Game.server?.sendChatNotifiaction(this, message, style)
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

	var combat: Combat? = null
	var party: Combat.Party = Combat.Party(listOf(this))
	val inCombat get() =
		combat?.ongoing == true

}
