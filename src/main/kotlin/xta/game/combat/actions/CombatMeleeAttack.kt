package xta.game.combat.actions

import xta.Player
import xta.game.combat.*
import kotlin.math.roundToInt

class CombatMeleeAttack(
	actor: Player,
	val target: Player
) : AbstractCombatAction(actor) {
	private val attacker = actor.char
	private val defender = target.char

	override fun perform() {
		// TODO multi-attack
		// TODO handle seals
		// TODO port combat math
		// TODO feral combat
		val roll = CombatRoll(attacker, defender)
		roll.aim = attacker.meleeAim
		roll.dodgeChance = CombatMath.meleeEvadeChance(attacker, defender)
		roll.damageType = DamageType.PHYSICAL
		roll.critChance = CombatMath.meleeDamageCritChancePercent(attacker, defender)
		roll.critMultiplier = CombatMath.meleeDamageCritMultiplier(attacker, defender)
		CombatPipeline.execute(
			arrayOf(
				AimPipe,
				DodgePipe,
				// TODO blocking
				MeleeDamageRollPipe,
				MeleeHitPipe,
				DealDamagePipe
			),
			display,
			roll
		)
		if (roll.failed) return
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
