package xta.game.combat.actions

import xta.Player
import xta.game.combat.AbstractCombatAction
import xta.game.combat.CombatMath
import xta.utils.chanceRoll
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
		val hitChance = CombatMath.meleeAim(attacker)
		if (!gamerng.chanceRoll(hitChance)) {
			// miss
			display.selectPerson(actor)
			display.outputText("[You] [verb miss] ")
			display.selectPerson(target)
			display.outputText("[you]!")
			return
		}
		val dodgeChance = CombatMath.meleeEvadeChance(attacker, defender)
		if (gamerng.chanceRoll(dodgeChance)) {
			// dodge
			if (dodgeChance >= 0.5) {
				display.selectPerson(target)
				display.outputText("[You] deftly [verb avoid] ")
				display.selectPerson(actor)
				display.outputText("[your] slow attack.")
			} else if (dodgeChance >= 0.25) {
				display.selectPerson(target)
				display.outputText("[You] [verb dodge] ")
				display.selectPerson(actor)
				display.outputText("[your] attack with superior quickness!")
			} else {
				display.selectPerson(target)
				display.outputText("[You] narrowly [verb dodge] ")
				display.selectPerson(actor)
				display.outputText("[your] attack!")
			}
			return
		}
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
			val hitChance = CombatMath.meleeTotalAccuracy(attacker, defender).times(100).roundToInt()
			append("\nHit chance: $hitChance%")
			append("\nApprox. damage: " + CombatMath.meleeDamage(attacker, defender, false).roundToInt())
		}
}
