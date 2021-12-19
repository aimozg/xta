package xta.game.combat.actions

import xta.Player
import xta.game.combat.AbstractCombatAction
import xta.game.combat.CombatMath
import xta.game.combat.CombatRoll
import xta.game.combat.DamageType
import xta.utils.gamerng
import xta.utils.percentRoll
import kotlin.math.round
import kotlin.math.roundToInt

class CombatMeleeAttack(
	actor: Player,
	val target: Player
) : AbstractCombatAction(actor) {
	private val attacker = actor.char
	private val defender = target.char

	inner class Roll: CombatRoll(attacker, defender, display) {
		// TODO multi-attack
		// TODO handle seals
		// TODO port combat math
		// TODO feral combat
			init {
				aim = attacker.meleeAim
				dodgeChance = CombatMath.meleeEvadeChance(attacker, target)
				damageType = DamageType.PHYSICAL
				critChance = CombatMath.meleeDamageCritChancePercent(attacker, target)
				critMultiplier = CombatMath.meleeDamageCritMultiplier(attacker, target)
			}

		override fun phaseCalcEffect() {
			damage = CombatMath.meleeDamage(attacker, target)
			val crit = gamerng.percentRoll(critChance)
			if (crit) {
				damage *= critMultiplier
			}
			damage = round(CombatMath.meleeDamageReduction(target, damage))
		}

		override fun phaseHit() {
			display.selectNpcs(attacker, target)
			if (damage < 0) {
				display.outputText("[Your] attacks are deflected by [npc1 you].")
				failed = true
				return
			}
			// TODO weapon verbs
			display.outputText("[You] [verb hit] [npc1 you]! ")
		}

		override fun doEffect() {
			dealDamage()
		}
	}

	override fun perform() {
		Roll().execute()
	}

	override val label: String
		get() = "Attack ${defender.name}"
	override val tooltip: String
		get() = buildString {
			// TODO use weapon name
			append("Strike ${defender.name} with your weapon")
			append("\n")
//			val hitChance = CombatMath.meleeTotalAccuracy(attacker, defender).times(100).roundToInt()
//			append("\nHit chance: $hitChance%")
			append("\nAim: ${attacker.meleeAim.coerceIn(0.0, 1.0).times(100).roundToInt()}%")
			append("\nDodge: ${CombatMath.meleeEvadeChance(attacker, defender).times(100).roundToInt()}%")
			append("\nApprox. damage: " + CombatMath.meleeDamage(attacker, defender, false).roundToInt())
		}
}
