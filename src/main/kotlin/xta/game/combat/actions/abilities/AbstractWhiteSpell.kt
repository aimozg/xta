package xta.game.combat.actions.abilities

import xta.Player

abstract class AbstractWhiteSpell(
	actor: Player,
	override val name: String
): AbstractSpell(actor) {
	open fun manaCost():Double {
		// TODO apply cost reduction
		return baseManaCost
	}
}
