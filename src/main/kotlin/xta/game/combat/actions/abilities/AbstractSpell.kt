package xta.game.combat.actions.abilities

import xta.Player

abstract class AbstractSpell(actor: Player): AbstractCombatAbility(actor) {
	abstract val baseManaCost: Double
}
