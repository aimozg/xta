package xta.game.combat

import xta.Player

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
