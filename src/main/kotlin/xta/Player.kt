package xta

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
	override fun toLogString(): String =
		if (isHost && isMe) "[LocalHost]" else guest.toLogString()

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
	var scene: Scene = Limbo
	var screen: ScreenJson
		get() = display.screen
		set(value) {
			display.screen = value
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
