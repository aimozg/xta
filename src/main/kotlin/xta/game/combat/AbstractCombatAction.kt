package xta.game.combat

import xta.Player
import xta.text.TextOutput
import xta.utils.nextUid

/*
 * Created by aimozg on 08.12.2021.
 */
abstract class AbstractCombatAction(val actor: Player) {
	protected val combat = actor.combat!!
	protected val display:TextOutput get() = combat.display
	val uid = nextUid()

	abstract fun perform()

	abstract val label:String
	abstract val tooltip:String?
	open val enabled: Boolean get() = true

	protected open fun otherPlayers(): List<Player> =
		combat.participants.minus(actor)
}

