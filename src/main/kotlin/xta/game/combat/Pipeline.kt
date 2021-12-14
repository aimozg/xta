package xta.game.combat

import xta.game.PlayerCharacter
import xta.text.TextOutput
import xta.utils.chanceRoll
import xta.utils.formatBigInt
import xta.utils.gamerng

/*
 * Created by aimozg on 13.12.2021.
 */

class CombatRoll(
	val attacker: PlayerCharacter,
	val defender: PlayerCharacter
) {
	var failed:Boolean = false

	var aim: Double = 0.0
	var canMiss:Boolean = true
	var missed:Boolean = false

	var dodgeChance: Double = 0.0
	var canBeDodged:Boolean = true
	var dodged:Boolean = false

	var damage: Double = 0.0
	var crit: Boolean = false
}

abstract class CombatPipeline {
	abstract fun process(roll: CombatRoll, display: TextOutput)

	companion object {
		fun execute(items:Array<out CombatPipeline>, display: TextOutput, roll: CombatRoll): CombatRoll {
			for (item in items) {
				if (roll.failed) break
				item.process(roll, display)
			}
			return roll
		}
	}
}

object AimPipe: CombatPipeline() {
	override fun process(roll: CombatRoll, display: TextOutput) {
		if (!roll.canMiss) return
		if (!gamerng.chanceRoll(roll.aim)) {
			roll.missed = true
			roll.failed = true
			display.selectNpcs(roll.attacker, roll.defender)
			display.outputText("[You] [verb miss] [npc1 you]!")
		}
	}
}

object DodgePipe: CombatPipeline() {
	override fun process(roll: CombatRoll, display: TextOutput) {
		if (!roll.canBeDodged) return
		if (gamerng.chanceRoll(roll.dodgeChance)) {
			roll.dodged = true
			roll.failed = true
			// dodge
			display.selectNpcs(roll.defender, roll.attacker)
			if (roll.dodgeChance >= 0.5) {
				display.outputText("[You] deftly [verb avoid] [npc1 your] slow attack.")
			} else if (roll.dodgeChance >= 0.25) {
				display.outputText("[You] [verb dodge] [npc1 your] attack with superior quickness!")
			} else {
				display.outputText("[You] narrowly [verb dodge] [npc1 your] attack!")
			}
		}
	}
}

object MeleeHitPipe: CombatPipeline() {
	override fun process(roll: CombatRoll, display: TextOutput) {
		display.selectNpcs(roll.attacker, roll.defender)
		if (roll.damage < 0) {
			display.outputText("[Your] attacks are deflected by [npc1 you].")
			return
		}
		roll.defender.hp -= roll.damage
		// TODO weapon verbs
		display.outputText("[You] [verb hit] [npc1 you]! ")
		if (roll.crit) {
			display.outputText("<b>Critical!</b> ")
		}
		display.outputText("(<span class='text-damage'>${roll.damage.formatBigInt()}</span>)")
	}
}
