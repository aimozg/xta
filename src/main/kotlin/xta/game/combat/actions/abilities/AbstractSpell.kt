package xta.game.combat.actions.abilities

import xta.Player

abstract class AbstractSpell(actor: Player): AbstractCombatAbility(actor) {
	abstract val baseManaCost: Int

	override fun usabilityCheck(): String? {
		// TODO blood magic & last resort
		if (caster.mana < manaCost) return "Your mana is too low to cast this spell"

		// TODO other costs
		if (caster.wrath < wrathCost) return "Your wrath it too low to cast this spell"

		return super.usabilityCheck()
	}
}
