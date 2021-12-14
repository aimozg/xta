package xta.ui

import kotlinx.dom.appendElement
import kotlinx.dom.clear
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.asList
import xta.Game
import xta.ScreenManager
import xta.game.combat.Combat

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

	fun disableActions() {
		for (btn in actionsDiv.querySelectorAll("button").asList()) {
			(btn as HTMLButtonElement).disabled = true
		}
	}

	fun update() {
		val combat = Game.me.combat ?: return
		// TODO many-on-many battles
		val myParty:Combat.Party
		val otherParty:Combat.Party
		if (Game.me in combat.partyA) {
			myParty = combat.partyA
			otherParty = combat.partyB
		} else {
			myParty = combat.partyB
			otherParty = combat.partyA
		}

		leftCharPanel.showCharacter(myParty.players.firstOrNull()?.char)
		rightCharPanel.showCharacter(otherParty.players.firstOrNull()?.char)

		// TODO sanitize!
		textOutputDiv.innerHTML = Game.me.screen.content

		actionsDiv.clear()
		for (action in Game.me.screen.actions) {
			actionsDiv.appendElement("button") {
				this as HTMLButtonElement
				className = "action"
				textContent = action.label
				if (action.disabled == true) disabled = true
				addTooltip(action.hint?:"")
				// TODO add tooltip on disabled buttons
				onclick = {
					hideTooltip()
					disableActions()
					Game.hostProtocol.sendCombatAction(action.actionId)
				}
			}
		}
	}
}
