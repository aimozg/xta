package xta.game.combat.actions

import xta.Player
import xta.game.combat.*
import xta.utils.formatBigInt
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

	override fun perform() {
		// TODO multi-attack
		// TODO handle seals
		// TODO port combat math
		val roll = CombatRoll(attacker, defender)
		roll.aim = attacker.meleeAim
		roll.dodgeChance = CombatMath.meleeEvadeChance(attacker, defender)
		CombatPipeline.execute(
			arrayOf(
				AimPipe,
				DodgePipe
			),
			display,
			roll
		)
		if (roll.failed) return
		// TODO blocking
		// TODO feral combat
		// hit
		var damage = CombatMath.meleeDamage(attacker, defender)
		val crit = gamerng.percentRoll(CombatMath.meleeDamageCritChancePercent(attacker, defender))
		if (crit) {
			damage *= CombatMath.meleeDamageCritMultiplier(attacker, defender)
		}
		damage = round(CombatMath.meleeDamageReduction(defender, damage))
		if (damage < 0) {
			display.selectPerson(actor)
			display.outputText("[Your] attacks are deflected by ")
			display.selectPerson(target)
			display.outputText("[you].")
			return
		}
		defender.hp -= damage
		display.selectPerson(actor)
		// TODO weapon verbs
		display.outputText("[You] [verb hit] ")
		display.selectPerson(target)
		display.outputText("[you]! ")
		if (crit) {
			display.outputText("<b>Critical!</b> ")
		}
		display.outputText("(<span class='text-damage'>${damage.formatBigInt()}</span>)")
		// TODO post-damage effects
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
