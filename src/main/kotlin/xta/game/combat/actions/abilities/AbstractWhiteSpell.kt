package xta.game.combat.actions.abilities

import xta.Player

abstract class AbstractWhiteSpell(
	actor: Player,
	override val name: String
): AbstractSpell(actor) {
	// TODO apply cost reduction
	override val manaCost: Int
		get() = baseManaCost
}
