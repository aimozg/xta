package xta.game.combat.actions.abilities

import xta.Player
import kotlin.math.roundToInt

abstract class AbstractWhiteSpell(
	actor: Player,
	override val name: String
): AbstractSpell(actor) {
	// TODO apply cost reduction
	override val manaCost: Int
		get() = (baseManaCost * caster.spellCostStat.value).roundToInt()
}
