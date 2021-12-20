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

class SpellWhitefire(
	actor: Player,
	val targetPlayer: Player
) : AbstractWhiteSpell(
	actor,
	"Whitefire"
) {
	override val ability: CombatAbility
		get() = Companion
	val target = targetPlayer.char

	inner class Roll : CombatRoll(caster, target, display) {
		init {
			canBeDodged = false
			damageType = DamageType.FIRE
		}

		override fun phaseCast() {
			display.selectNpcs(caster, target)
			display.outputText("[You] [verb narrow] [your] eyes, focusing [your] mind with deadly intent.  [You] [verb snap] [your] fingers and [npc1 you] [npc1 are] enveloped in a flash of white flames! ")
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

	// TODO cooldown
	override fun isKnown() = caster.knows(KnownThings.SPELL_WHITEFIRE)
	override val description: String
		get() = "Whitefire is a potent fire based attack that will burn your foe with flickering white flames, ignoring their physical toughness and most armors."

	override val baseManaCost
		get() = 40

	override fun describeEffect(): String =
		"~" + calcDamage(false) + " fire damage"

	fun calcDamage(randomize: Boolean = true): Double {
		var damage = 2.0 * caster.scalingBonusIntelligence(randomize)
		// TODO adjust spell damage
		damage *= caster.spellPowerStat.value
		return floor(damage)
	}

	override fun performAbilityEffect() {
		Roll().execute()
	}

	companion object: CombatAbility.TargetingOneEnemy("Whitefire") {
		override fun isKnownBy(caster: PlayerCharacter) = caster.knows(KnownThings.SPELL_WHITEFIRE)

		override fun createAction(actor: Player, targetPlayer: Player, combat: Combat) =
			SpellWhitefire(actor, targetPlayer)
	}
}
