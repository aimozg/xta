package xta.game.combat.actions

import xta.Player
import xta.game.combat.AbstractCombatAction

class CombatWait(actor: Player): AbstractCombatAction(actor) {
	override fun perform() {
		display.selectNpc(0, actor.char)
		display.outputText("[You] [verb do] nothing.")
	}

	override val label: String
		get() = "Wait"
	override val tooltip: String
		get() = "Skip a turn"
}
