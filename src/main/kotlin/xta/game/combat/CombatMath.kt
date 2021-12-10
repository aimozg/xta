package xta.game.combat

import xta.game.Creature
import kotlin.math.round

/*
 * Created by aimozg on 10.12.2021.
 */
object CombatMath {

	fun meleeTotalAccuracy(attacker: Creature, target: Creature): Double {
		return (meleeAim(attacker) * (1.0 - meleeEvadeChance(attacker, target))).coerceIn(0.0, 1.0)
	}

	/**
	 * Chance to hit the target (it could dodge later)
	 */
	fun meleeAim(attacker: Creature): Double {
		var accuracy = 64.0
		accuracy += (attacker.level*6).coerceAtMost(72)
		// TODO flying penalty
		// TODO perk bonuses
		// TODO item bonuses
		// TODO mastery
		if (attacker.hasCondition(CombatCondition.BLIND)) {
			accuracy /= 2
		}
		return (accuracy/100.0).coerceIn(0.0,1.0)
	}

	/**
	 * Chance to evade attack that is about to hit
	 */
	fun meleeEvadeChance(attacker:Creature, target: Creature):Double {
		// TODO account for other items & perks, see Creature.getEvasionReason()
		val speedDiff = target.spe - attacker.spe
		if (speedDiff < 0) return 0.0
		return 1.0 - 100.0 / ( speedDiff*0.3125 + 100.0)
	}

	fun meleeDamage(attacker: Creature, target: Creature, randomize:Boolean=true): Double {
		var damage = 0.0

		// Strength-based damage

		// TODO port calculations from Combat.as:5166-5203
		damage += attacker.str
		damage += attacker.scalingBonusStrength(randomize)

		if (damage < 10) damage = 10.0

		// Weapon-based damage
		// TODO port calculations from Combat.as:5204-5287
		// Damage post-processing
		// TODO port calculations from Combat.as:5313-5327 (damage post-processing)
		// TODO port calculations from Combat.as:5422-5440 (some more perk-based damage)
		// TODO port calculations from Combat.as:5518-5539 (damage boosted by dao)
		// TODO port calculations from doDamage/doXXXDamage functions (damage type-related adjustments)

		return round(damage)
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
		// TODO monster.damagePercent() or similar player function
		return damage * (100.0/100.0)
	}
}
