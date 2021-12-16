package xta.game.combat

import xta.game.Creature

/*
 * Created by aimozg on 10.12.2021.
 */
object CombatMath {

	fun meleeTotalAccuracy(attacker: Creature, target: Creature): Double {
		// return (attacker.meleeAim - target.meleeDodge).coerceIn(0.0, 1.0)
		return (attacker.meleeAim.coerceIn(0.0, 1.0) * (1.0 - meleeEvadeChance(attacker, target))).coerceIn(0.0, 1.0)
	}

	fun speedBasedDodge(attacker:Creature, target: Creature):Double {
		/*
		CoCX formula:
		if (speedDiff < 0) return 0.0
		return 1.0 - 100.0 / ( speedDiff*0.3125 + 100.0)
		 */
		return (target.spe - attacker.spe) / (target.spe + attacker.spe)
	}
	/**
	 * Chance to evade attack that is about to hit
	 */
	fun meleeEvadeChance(attacker:Creature, target: Creature):Double {
		// TODO account for other items & perks, see Creature.getEvasionReason()
		return (target.meleeDodgeStat.value + speedBasedDodge(attacker, target)).coerceIn(0.0, 1.0)
	}

	fun meleeDamage(attacker: Creature, target: Creature, randomize:Boolean=true): Double {
		return attacker.meleeDamage(randomize)
	}

	fun meleeDamageCritChancePercent(attacker: Creature, target: Creature): Double {
		// TODO port calculations from Combat.as:5289-5310
		return 5.0
	}

	fun meleeDamageCritMultiplier(attacker: Creature, target: Creature): Double {
		// TODO port calculations from Combat.as:5289-5310
		return 1.75
	}

	fun meleeDamageReduction(target: Creature, damage:Double):Double {
		return damage * target.physDamageReduction()
	}
}
