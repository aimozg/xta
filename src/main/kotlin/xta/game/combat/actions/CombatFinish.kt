package xta.game.combat.actions

import xta.Game
import xta.Player
import xta.game.combat.AbstractCombatAction

/*
 * Created by aimozg on 09.12.2021.
 */
class CombatFinish(actor: Player) : AbstractCombatAction(actor) {
	override fun perform() {
		actor.combat = null
		actor.char.clearCombatStatuses()
		if (actor.isConnected) {
			Game.server?.sendCombatStatus(actor, false)
			combat.returnScene.execute(actor)
			Game.server?.sendStatusUpdate(actor, char = true)
		}
		actor.location.onCombatStatusChange()
	}

	override val label: String
		get() = "Finish"
	override val tooltip: String
		get() = "Finish combat and return"
}
