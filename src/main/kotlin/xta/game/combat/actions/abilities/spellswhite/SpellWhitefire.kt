package xta.game.combat.actions.abilities.spellswhite

import xta.Player
import xta.game.combat.actions.abilities.AbstractWhiteSpell
import xta.game.creature.KnownThings
import xta.utils.formatBigInt
import kotlin.math.floor

class SpellWhitefire(
	actor: Player,
	val targetPlayer: Player
): AbstractWhiteSpell(
	actor,
	"Whitefire"
) {
	val target = targetPlayer.char
	override fun isKnown() = caster.knows(KnownThings.SPELL_WHITEFIRE)
	override val description: String
		get() = "Whitefire is a potent fire based attack that will burn your foe with flickering white flames, ignoring their physical toughness and most armors."

	override val baseManaCost: Double
		get() = 40.0

	override fun describeEffect(): String =
		"~"+calcDamage(false)+" fire damage"

	fun calcDamage(randomize:Boolean = true):Double {
		var damage = 2.0 * caster.scalingBonusIntelligence(randomize)
		// TODO adjust spell damage
		damage *= caster.spellPowerStat.value
		return floor(damage)
	}

	override fun performAbilityEffect() {
		display.selectNpcs(caster, target)
		display.outputText("[You] [verb narrow] [your] eyes, focusing [your] mind with deadly intent.  [You] [verb snap] [your] fingers and [npc1 you] [npc1 are] enveloped in a flash of white flames! ")
		val damage = calcDamage()
		// TODO proper damage dealing function
		// TODO crit and repeat damage
		display.outputText("(<span class='text-damage'>${damage.formatBigInt()}</span>)")
		target.hp -= damage
	}
}
