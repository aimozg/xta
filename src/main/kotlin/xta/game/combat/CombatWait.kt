package xta.game.combat

import xta.Player

class CombatWait(actor: Player): AbstractCombatAction(actor) {
	override fun perform() {
		display.selectPerson(actor)
		display.outputText("[You] [verb do] nothing.")
	}

	override val label: String
		get() = "Wait"
	override val tooltip: String
		get() = "Skip a turn"
}
