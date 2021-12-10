package xta.game.combat.actions

import xta.Player
import xta.game.combat.AbstractCombatAction

class CombatSurrender(actor: Player): AbstractCombatAction(actor) {
	override fun perform() {
		display.selectPerson(actor)
		display.outputText("[You] [verb surrender]!..")
		actor.char.surrendered = true
	}

	override val label: String
		get() = "Surrender"
	override val tooltip: String?
		get() = "Give up and lose battle"
}
