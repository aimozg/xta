package xta.game

import xta.game.combat.CombatCondition
import xta.game.combat.StatusEffect
import xta.game.combat.StatusType
import xta.game.combat.statuses.StatusLib
import xta.game.creature.Gender
import xta.game.creature.PerkType
import xta.game.creature.body.LowerBodyType
import xta.game.creature.body.PenisType
import xta.game.creature.body.SkinCoatType
import xta.game.stats.BuffableStat
import xta.utils.RandomNumber
import kotlin.math.round
import kotlin.math.roundToInt

/*
 * Created by aimozg on 28.11.2021.
 */
@JsExport
abstract class Creature: AbstractCreature() {
	init {
		meleeAimStat.dynamicBuff("leveled",show=false, persistent = true) {
			(it.level*0.06).coerceAtMost(0.36)
		}
		meleeAimStat.dynamicBuff("condition_Blind","Blinded", persistent = true) {
			if (it.hasCondition(CombatCondition.BLIND)) {
				// 50% of base+leveled
				-0.5*((0.64+it.level*0.06).coerceAtMost(1.00))
			} else 0.0
		}
		// TODO flying penalty
		// TODO perk bonuses
		// TODO item bonuses
		// TODO mastery
	}

	/*
	 *    ███████  ██████  ██    ██ ██ ██████  ███    ███ ███████ ███    ██ ████████
	 *    ██      ██    ██ ██    ██ ██ ██   ██ ████  ████ ██      ████   ██    ██
	 *    █████   ██    ██ ██    ██ ██ ██████  ██ ████ ██ █████   ██ ██  ██    ██
	 *    ██      ██ ▄▄ ██ ██    ██ ██ ██      ██  ██  ██ ██      ██  ██ ██    ██
	 *    ███████  ██████   ██████  ██ ██      ██      ██ ███████ ██   ████    ██
	 *                ▀▀
	 *
	 */

	fun allClothesDescript(nakedText:String = "gear"): String {
		// TODO upper and lower garment
		return armor?.name ?: nakedText
	}

	/*
	 *     █████  ██████  ██████  ███████  █████  ██████   █████  ███    ██  ██████ ███████
	 *    ██   ██ ██   ██ ██   ██ ██      ██   ██ ██   ██ ██   ██ ████   ██ ██      ██
	 *    ███████ ██████  ██████  █████   ███████ ██████  ███████ ██ ██  ██ ██      █████
	 *    ██   ██ ██      ██      ██      ██   ██ ██   ██ ██   ██ ██  ██ ██ ██      ██
	 *    ██   ██ ██      ██      ███████ ██   ██ ██   ██ ██   ██ ██   ████  ██████ ███████
	 *
	 *
	 */

	abstract fun raceName(): String // "cat-taur"
	abstract fun raceFullName(): String // "cat-boy", "female human"

	/*
	 *    ███████ ████████  █████  ████████     ███████ ███    ██ ███████
	 *    ██         ██    ██   ██    ██        ██      ████   ██ ██
	 *    ███████    ██    ███████    ██        █████   ██ ██  ██ ███████
	 *         ██    ██    ██   ██    ██        ██      ██  ██ ██      ██
	 *    ███████    ██    ██   ██    ██        ██      ██   ████ ███████
	 *
	 *
	 */
	val str get() = strStat.value
	val tou get() = touStat.value
	val spe get() = speStat.value
	val int get() = intStat.value
	val wis get() = wisStat.value
	val lib get() = libStat.value
	val sens get() = sensStat.value

	var hpRatio
		get() = hp /maxHp()
		set(value) {
			hp = (maxHp()*value)
		}
	var lustRatio
		get() = lust /maxLust()
		set(value) {
			lust = (maxLust()*value)
		}
	var wrathRatio
		get() = wrath /maxWrath()
		set(value) {
			wrath = (maxWrath()*value)
		}
	val staminaRatio get() = 1.0-fatigueRatio
	var fatigueRatio
		get() = fatigue /maxFatigue()
		set(value) {
			fatigue = (maxFatigue()*value)
		}
	var manaRatio
		get() = mana /maxMana()
		set(value) {
			mana = (maxMana()*value)
		}
	var sfRatio
		get() = soulforce /maxSoulforce()
		set(value) {
			soulforce = (maxSoulforce()*value)
		}

	fun xpToLevelUp(): Int {
		var xpm = 100
		if (level >= 42) xpm += 100
		if (level >= 102) xpm += 100
		if (level >= 180) xpm += 100
		return (level*xpm).coerceAtMost(74000)
	}
	open fun maxHp():Double {
		return round(maxHpBase()*maxHpMult()).coerceAtMost(9_999_999.0)
	}
	open fun maxHpPerLevel():Double {
		return 60.0
	}
	open fun maxHpBase():Double {
		var max = 0.0
		// TODO account for perks

		max += tou*2+maxHpBaseStat.value.roundToInt()
		if (tou >= 21) max += tou
		if (tou >= 41) max += tou
		if (tou >= 61) max += tou
		if (tou >= 81) max += tou
		if (tou >= 101) max += tou * ((tou - 100) / 50 + 1)

		max += level * maxHpPerLevel()
		if (level <= 6) max += level * 60

		return round(max)
	}
	open fun maxHpMult():Double {
		// TODO scores, green cock socks, mecha
		return maxHpMultStat.value
	}
	open fun minLust():Double {
		// TODO calculations
		return minLustStat.value
	}
	open fun maxLust():Double {
		return round(maxLustBase()*maxLustMult()).coerceAtMost(199_999.0)
	}
	open fun maxLustBase():Double {
		var max = maxLustBaseStat.value
		// TODO perk bonuses
		max += level*maxLustPerLevel()
		if (level <= 6) max += level*3
		return round(max)
	}
	open fun maxLustPerLevel():Double {
		// TODO perk bonuses
		return maxLustPerLevelStat.value
	}
	open fun maxLustMult():Double {
		// TODO calculate
		return maxLustPerLevelStat.value
	}
	open fun maxWrath():Double {
		return round(maxWrathBase()*maxWrathMult()).coerceAtMost(476_999.0)
	}
	open fun maxWrathBase():Double {
		var max = maxWrathBaseStat.value
		// TODO perk, item, race bonuses
		max += level*maxWrathPerLevel()
		if (level <= 6) max += level*5
		return round(max)
	}
	open fun maxWrathPerLevel():Double {
		// TODO perk, item, race bonuses
		return maxWrathPerLevelStat.value
	}
	open fun maxWrathMult():Double {
		// TODO perk, item, race bonuses
		return maxWrathMultStat.value
	}

	/**
	 * Usable fatigue points
	 */
	var stamina:Double
		get() = maxFatigue() - fatigue
		set(value) {
			fatigue = maxFatigue() - value
		}
	open fun maxFatigue():Double {
		return round(maxFatigueBase()*maxFatigueMult()).coerceAtMost(1_499_999.0)
	}
	open fun maxFatigueBase():Double {
		var max = maxFatigueBaseStat.value
		// TODO racial scores items and perks
		max += level*maxFatiguePerLevel()
		if (level <= 6) max += level*5
		max += spe*maxFatiguePerSpeStat.value
		return round(max)
	}
	open fun maxFatiguePerLevel():Double {
		// TODO racial scores items and perks
		return maxFatiguePerLevelStat.value
	}
	open fun maxFatigueMult():Double {
		// TODO racial scores items and perks
		return maxFatigueMultStat.value
	}
	open fun maxMana():Double {
		return round(maxManaBase()*maxManaMult()).coerceAtMost(2_499_999.0)
	}
	open fun maxManaBase():Double {
		var max = maxManaBaseStat.value
		// TODO perk, race, item bonuses
		max += level*maxManaPerLevel()
		if (level <= 6) max += level*10
		max += int*maxManaPerIntStat.value
		max += wis*maxManaPerWisStat.value
		return round(max)
	}
	open fun maxManaPerLevel():Double {
		// TODO perk, race, item bonuses
		return maxManaPerLevelStat.value
	}
	open fun maxManaMult():Double {
		// TODO perk, race, item bonuses
		return maxManaMultStat.value
	}
	open fun maxSoulforce():Double {
		return round(maxSoulforceBase()*maxSoulforceMult()).coerceAtMost(1_499_999.0)
	}
	open fun maxSoulforceBase():Double {
		var max = maxSfBaseStat.value
		// TODO perk, cultivation level, item, racial bonuses
		max += level*maxSoulforcePerLevel()
		if (level <= 6) max += level*5
		return round(max)
	}
	open fun maxSoulforcePerLevel():Double {
		// TODO perk, cultivation level, item, racial bonuses
		return maxSfPerLevelStat.value
	}
	open fun maxSoulforceMult():Double {
		// TODO perk, cultivation level, item, racial bonuses
		return maxSfMultStat.value
	}

	/*
	 *    ██████   ██████  ██████  ██    ██     ███████ ███    ██ ███████
	 *    ██   ██ ██    ██ ██   ██  ██  ██      ██      ████   ██ ██
	 *    ██████  ██    ██ ██   ██   ████       █████   ██ ██  ██ ███████
	 *    ██   ██ ██    ██ ██   ██    ██        ██      ██  ██ ██      ██
	 *    ██████   ██████  ██████     ██        ██      ██   ████ ███████
	 *
	 *
	 */

	val gender
		get() = when {
			hasCock() && hasVagina() -> Gender.HERM
			hasCock() -> Gender.MALE
			hasVagina() -> Gender.FEMALE
			else -> Gender.GENDERLESS
		}

	val hairType get() = hair.type
	val hairStyle get() = hair.style
	val hairColor get() = hair.color
	val hairColor2 get() = hair.color2
	val hairLength get() = hair.length
	val eyeColor get() = eyes.irisColor
	val skinTone get() = skin.color
	val skinColor get() = skin.skinColor
	val skinColor2 get() = skin.skinColor2
	val furColor get() = skin.furColor
	val furColor2 get() = skin.furColor2
	val scaleColor get() = skin.scaleColor
	val scaleColor2 get() = skin.scaleColor2
	val chitinColor get() = skin.chitinColor
	val chitinColor2 get() = skin.chitinColor2
	// coatColor, coatColor2, nakedCoatColor
	fun hasCoat() = skin.hasCoat()
	fun hasFullCoat() = skin.hasFullCoat()
	fun hasCoatOfType(vararg types: SkinCoatType) = skin.hasCoatOfType(*types)
	fun hasPartialCoatOfType(vararg types:SkinCoatType) = skin.hasPartialCoatOfType(*types)
	fun hasFullCoatOfType(vararg types: SkinCoatType) = skin.hasFullCoatOfType(*types)
	fun hasPlainSkinOnly() = skin.hasPlainSkinOnly()
	fun hasFur() = skin.hasCoatOfType(SkinCoatType.FUR)
	fun hasScales() = skin.hasCoatOfType(SkinCoatType.SCALES)
	// coatType, skinDesc, skinAdj
	// hasGills
	val faceType get() = face.type
	// clawTone, clawType
	val legCount get() = lowerBody.legCount
	val isTaur get() = lowerBody.isTaur
	val isBiped get() = lowerBody.isBiped
	val isNaga get() = lowerBody.isNaga
	// tailVenom, tailRecharge
	val tailType get() = tail.type
	val tailCount get() = tail.count

	fun hasCock() = cocks.isNotEmpty()
	fun hasVagina() = vaginas.isNotEmpty()
	val vaginaType get() = vaginas.firstOrNull()?.type
	fun vaginalCapacity():Int {
		val vagina = vaginas.firstOrNull() ?: return 0
		var bonus = 0
		if (isTaur) bonus += 50
		else if (lowerBody.type == LowerBodyType.NAGA) bonus += 20
		// TODO perk bonuses
		// TODO BonusVCapacity seffect
		// TOOD port looseness/wetness based calculation calculation
		return bonus /*+ 8*vagina.looseness*vagina.looseness*(1+vagina.wetness/10.0)*/
	}
	fun countCocksOfType(type:PenisType) = cocks.count { it.type == type }

	fun biggestTitSize():Int =
		if (breastRows.isEmpty()) -1
		else breastRows.maxOf { it.breastRating }

	fun looksFemale():Boolean {
		val bts = biggestTitSize()
		return when (gender) {
			Gender.HERM,
			Gender.GENDERLESS ->
				bts >= 3
					|| bts == 2 && femininity >= 15
					|| bts == 1 && femininity >= 40
					|| femininity > 65
			Gender.MALE ->
				bts >= 3 && femininity >= 5
						|| bts == 2 && femininity >= 35
						|| bts == 1 && femininity >= 65
						|| femininity >= 95
			Gender.FEMALE ->
				bts >= 3
						|| bts == 2 && femininity >= 5
						|| bts == 1 && femininity >= 15
						|| femininity >= 40
		}
	}
	fun mf(m:String,f:String) = if (looksFemale()) f else m

	fun hasPerk(perkType: PerkType) = perkType in perks
	fun addPerk(perkType: PerkType) = perks.addPerk(perkType)
	fun removePerk(perkType: PerkType) = perks.removePerk(perkType)

	// TODO maxVenom
	fun maxVenom():Double = 0.0

	/*
	 *     ██████  ██████  ███    ███ ██████   █████  ████████     ███████ ███    ██ ███████
	 *    ██      ██    ██ ████  ████ ██   ██ ██   ██    ██        ██      ████   ██ ██
	 *    ██      ██    ██ ██ ████ ██ ██████  ███████    ██        █████   ██ ██  ██ ███████
	 *    ██      ██    ██ ██  ██  ██ ██   ██ ██   ██    ██        ██      ██  ██ ██      ██
	 *     ██████  ██████  ██      ██ ██████  ██   ██    ██        ██      ██   ████ ███████
	 *
	 *
	 */

	var surrendered = false
	val isAlive get() = !surrendered && hp > 0 && lust < maxLust()
	val canAct get() = isAlive // TODO and not stunned

	fun clearCombatStatuses() {
		surrendered = false
		allStatsAndSubstats().forEach {
			(it as? BuffableStat)?.removeCombatBuffs()
		}
		// TODO clear combat statuses only and call their callbacks
		statusEffects.clear()
	}

	fun hasCondition(condition: CombatCondition): Boolean = when (condition ){
		CombatCondition.BLIND -> hasStatusEffect(StatusLib.Blind)
	}

	private fun scalingBonusIwlRoll(stat: Int): RandomNumber {
		val scale = when {
			stat > 100_000 -> 50.75
			stat > 100 -> 1.0 + ((stat-100)/50) * 0.25
			else -> 2.0/6.0 + (stat/20)/6.0
		}
		val rng =
			if (stat <= 100) stat*(scale+0.25)
			else stat*(scale+0.5)
		return RandomNumber(stat*scale, stat*scale + rng)
	}

	fun scalingBonusStrength(randomize:Boolean=true):Double {
		val roll = scalingBonusIwlRoll(str.roundToInt())
		if (randomize) return roll.roll() else return roll.mean()
	}

	fun scalingBonusIntelligence(randomize:Boolean=true):Double {
		val roll = scalingBonusIwlRoll(int.roundToInt())
		if (randomize) return roll.roll() else return roll.mean()
	}

	val meleeAim: Double
		get() = meleeAimStat.value
	val meleeDodge: Double
		get() {
			return meleeDodgeStat.value + dodgeStat.value
		}
	fun meleeWeaponDamageFactor():Double {
		// TODO Player.weaponAttack uses extra scaling with perks
		val weaponAttack = meleeWeapon?.attack(this)?:0
		return when {
			weaponAttack < 51 -> (1.0 + weaponAttack * 0.03)
			weaponAttack < 101 -> (2.5 + (weaponAttack-50) * 0.025)
			weaponAttack < 151 -> (3.75 + (weaponAttack-100) * 0.02)
			weaponAttack < 201 -> (4.75 + (weaponAttack-150) * 0.015)
			else -> (5.5 + (weaponAttack-200) * 0.01)
		}
	}
	fun meleeDamage(randomize:Boolean=true):Double {
		var dmg = meleeDamageStat.value
		// Strength-based damage
		// TODO port calculations from Combat.meleeDamageAcc (BASIC DAMAGE STUFF)
		dmg += str
		dmg += scalingBonusStrength(randomize)*0.2
		dmg = round(dmg)
		dmg = dmg.coerceAtLeast(10.0)
		// Weapon-based damage
		dmg *= meleeWeaponDamageFactor()
		dmg *= meleeDamageMultStat.value
		// TODO port other calculations from Combat.meleeDamageAcc (Weapon addition!)
		// Damage post-processing
		// TODO port calculations from Combat.meleeDamageAcc (damage post-processing)
		// TODO port calculations from Combat.meleeDamageAcc (some more perk-based damage)
		// TODO port calculations from Combat.meleeDamageAcc (damage boosted by dao)
		// TODO port calculations from doDamage/doXXXDamage functions (damage type-related adjustments)
		return dmg
	}
	fun physDamageReduction():Double {
		var percentage = 100.0
		percentage -= resistPhysStat.value*100.0
		if (percentage < 20) percentage = 20.0
		// TODO port from player.damagePercent()
		return percentage/100.0
	}

	fun addStatusEffect(effect: StatusEffect) {
		statusEffects.add(effect)
	}
	fun statusEffectsByType(type: StatusType): List<StatusEffect> =
		statusEffects.filter { it.type == type }
	fun statusEffectByType(type:StatusType): StatusEffect? =
		statusEffects.find { it.type == type }
	fun hasStatusEffect(type:StatusType): Boolean =
		statusEffects.any { it.type == type }
	fun createStatusEffect(type: StatusType, duration: Int) {
		if (!type.isStackable) {
			val existing = statusEffectByType(type)
			if (existing != null) {
				existing.durationRounds += duration
				return
			}
		}
		addStatusEffect(StatusEffect(this, type, duration))
	}

}
