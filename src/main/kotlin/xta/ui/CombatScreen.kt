package xta.ui

import xta.Game
import xta.ScreenManager

/*
 * Created by aimozg on 07.12.2021.
 */
class CombatScreen: UiScreen("combat-screen") {
	private val textOutputDiv = fragment.ref("text-output")
	private val actionsDiv = fragment.ref("actions")
	private val leftDiv = fragment.ref("left")
	private val rightDiv = fragment.ref("right")
	private val leftCharPanel = CharacterPanel().also { it.insertTo(leftDiv) }
	private val rightCharPanel = CharacterPanel().also { it.insertTo(rightDiv) }

	init {
		ScreenManager.chatEnabled = true
	}

	fun updatePlayer() {
		val combat = Game.me.combat ?: return
		if (combat.partyA.players.contains(Game.me)) {
			leftCharPanel.showCharacter(Game.myCharacter)
		} else if (combat.partyA.players.contains(Game.me)) {
			rightCharPanel.showCharacter(Game.myCharacter)
		}
	}

	fun update() {
		val combat = Game.me.combat ?: return
		// TODO many-on-many battles
		leftCharPanel.showCharacter(combat.partyA.players.firstOrNull()?.char)
		rightCharPanel.showCharacter(combat.partyB.players.firstOrNull()?.char)

		textOutputDiv.textContent = "Welcome to the cum zone!"
	}
}
