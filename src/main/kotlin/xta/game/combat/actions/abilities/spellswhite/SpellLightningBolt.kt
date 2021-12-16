package xta.game.combat.actions.abilities.spellswhite

import xta.Player
import xta.game.combat.CombatPipeline
import xta.game.combat.CombatRoll
import xta.game.combat.DamageType
import xta.game.combat.DealDamagePipe
import xta.game.combat.actions.abilities.AbstractWhiteSpell
import xta.game.creature.KnownThings
import kotlin.math.floor

class SpellLightningBolt(
	actor: Player,
	val targetPlayer: Player
): AbstractWhiteSpell(
	actor,
	"Lightning Bolt"
) {
	// TODO cooldown
	val target = targetPlayer.char
	override fun isKnown() = caster.knows(KnownThings.SPELL_LIGHTNINGBOLT)
	override val description: String
		get() = "Lightning Bolt is a basic lightning attack that will electrocute your foe with a single bolt of lightning."

	override val baseManaCost
		get() = 40

	override fun describeEffect(): String =
		"~"+calcDamage(false)+" lightning damage"

	fun calcDamage(randomize:Boolean = true):Double {
		var damage = 2.0 * caster.scalingBonusIntelligence(randomize)
		// TODO adjust spell damage
		damage *= caster.spellPowerStat.value
		return floor(damage)
	}

	override fun performAbilityEffect() {
		display.selectNpcs(caster, target)
		display.outputText("[You] [verb charge] out energy in your hand and [verb fire] it out in the form of a powerful bolt of lightning at [npc1 you] !\n")
		val roll = CombatRoll(caster, target)
		roll.damageType = DamageType.LIGHTNING
		// TODO proper damage dealing function
		// TODO crit and repeat damage
		roll.damage = calcDamage()
		CombatPipeline.execute(
			arrayOf(
				DealDamagePipe
			), display, roll
		)
	}
}
