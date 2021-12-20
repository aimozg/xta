package xta.game.combat.actions.abilities.spellswhite

import xta.Player
import xta.game.PlayerCharacter
import xta.game.combat.Combat
import xta.game.combat.CombatRoll
import xta.game.combat.DamageType
import xta.game.combat.actions.CombatAbility
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
	override val ability: CombatAbility
		get() = Companion

	// TODO cooldown
	val target = targetPlayer.char

	inner class Roll : CombatRoll(caster, target, display) {
		init {
			canBeDodged = false
			damageType = DamageType.LIGHTNING
		}

		override fun phaseCast() {
			display.selectNpcs(caster, target)
			display.outputText("[You] [verb charge] out energy in your hand and [verb fire] it out in the form of a powerful bolt of lightning at [npc1 you] !\n")
		}

		override fun phaseCalcEffect() {
			damage = calcDamage()
		}

		override fun doEffect() {
			// TODO proper damage dealing function
			// TODO crit and repeat damage
			dealDamage()
		}
	}

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
		Roll().execute()
	}

	companion object: CombatAbility.TargetingOneEnemy("Lightning Bolt") {
		override fun isKnownBy(caster: PlayerCharacter) =
			caster.knows(KnownThings.SPELL_LIGHTNINGBOLT)

		override fun createAction(actor: Player, targetPlayer: Player, combat: Combat)=
			SpellLightningBolt(actor, targetPlayer)
	}
}
