package xta.game.combat

import xta.game.Creature
import xta.game.PlayerCharacter
import xta.text.TextOutput
import xta.utils.chanceRoll
import xta.utils.formatBigInt
import xta.utils.gamerng

/*
 * Created by aimozg on 13.12.2021.
 */

@JsExport
abstract class CombatRoll(
	val attacker: PlayerCharacter,
	val target: PlayerCharacter,
	val display: TextOutput
) {
	var failed:Boolean = false
	val succeeded get() = !failed

	// Phase: Aim

	var canMiss:Boolean = true
	var aim: Double = 0.0
	var missed:Boolean = false

	// Phase: Dodge

	var canBeDodged:Boolean = true
	var dodgeChance: Double = 0.0
	var dodged:Boolean = false

	// Phase: Hit

	var damage: Double = 0.0
	var damageType: DamageType = DamageType.TRUE

	var crit: Boolean = false
	var critChance: Double = 0.0
	var critMultiplier: Double = 1.0

	open fun execute(): Boolean {
		phaseCast()
		if (failed) return false

		phaseAim()
		if (failed) return false

		phaseDodge()
		if (failed) return false

		// TODO blocking

		phaseCalcEffect()
		if (failed) return false

		phaseHit()
		if (failed) return false

		doEffect()

		return succeeded
	}

	open fun phaseCast() {

	}

	open fun phaseAim() {
		if (!canMiss) return
		if (!gamerng.chanceRoll(aim)) {
			missed = true
			failed = true
			// TODO consider moving into displayMiss/displayFailure/displayResult
			display.selectNpcs(attacker, target)
			display.outputText("[You] [verb miss] [npc1 you]!")
		}
	}

	open fun phaseDodge() {
		if (!canBeDodged) return
		if (gamerng.chanceRoll(dodgeChance)) {
			dodged = true
			failed = true
			// TODO consider moving into displayDodge/displayFailure/displayResult
			display.selectNpcs(target, attacker)
			if (dodgeChance >= 0.5) {
				display.outputText("[You] deftly [verb avoid] [npc1 your] slow attack.")
			} else if (dodgeChance >= 0.25) {
				display.outputText("[You] [verb dodge] [npc1 your] attack with superior quickness!")
			} else {
				display.outputText("[You] narrowly [verb dodge] [npc1 your] attack!")
			}
		}
	}

	open fun phaseCalcEffect() {
		// By default - calculate nothing, this roll needs no calculation
	}

	open fun phaseHit() {
		// By default - do nothing, this roll has no special 'on hit' pre-effect
	}

	abstract fun doEffect()

	protected fun dealDamage(
		target: Creature = this.target,
		damage: Double = this.damage,
		crit: Boolean = this.crit,
		doOutput: Boolean = true
	) {
		target.hp -= damage
		if (doOutput) {
			if (crit) {
				display.outputText("<b>Critical!</b> ")
			}
			display.outputText("(<span class='text-damage'>${damage.formatBigInt()}</span>)")
		}
		// TODO post-damage effects
	}
}
