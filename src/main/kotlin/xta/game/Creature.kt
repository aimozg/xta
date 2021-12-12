package xta.game

import xta.game.combat.CombatCondition
import xta.game.creature.Gender
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

	/*
	 *    ███████  ██████  ██    ██ ██ ██████  ███    ███ ███████ ███    ██ ████████
	 *    ██      ██    ██ ██    ██ ██ ██   ██ ████  ████ ██      ████   ██    ██
	 *    █████   ██    ██ ██    ██ ██ ██████  ██ ████ ██ █████   ██ ██  ██    ██
	 *    ██      ██ ▄▄ ██ ██    ██ ██ ██      ██  ██  ██ ██      ██  ██ ██    ██
	 *    ███████  ██████   ██████  ██ ██      ██      ██ ███████ ██   ████    ██
	 *                ▀▀
	 *
	 */

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
		return 0.0
	}
	open fun maxLust():Double {
		return round(maxLustBase()*maxLustMult()).coerceAtMost(199_999.0)
	}
	open fun maxLustBase():Double {
		var max = 100.0
		// TODO perk bonuses
		max += level*maxLustPerLevel()
		if (level <= 6) max += level*3
		return round(max)
	}
	open fun maxLustPerLevel():Double {
		// TODO perk bonuses
		return 3.0
	}
	open fun maxLustMult():Double {
		// TODO calculate
		return 1.0
	}
	open fun maxWrath():Double {
		return round(maxWrathBase()*maxWrathMult()).coerceAtMost(476_999.0)
	}
	open fun maxWrathBase():Double {
		var max = 500.0
		// TODO perk, item, race bonuses
		max += level*maxWrathPerLevel()
		if (level <= 6) max += level*5
		return round(max)
	}
	open fun maxWrathPerLevel():Double {
		// TODO perk, item, race bonuses
		return 5.0
	}
	open fun maxWrathMult():Double {
		// TODO perk, item, race bonuses
		return 1.0
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
		var max = 150.0
		// TODO racial scores items and perks
		max += level*maxFatiguePerLevel()
		if (level <= 6) max += level*5
		return round(max)
	}
	open fun maxFatiguePerLevel():Double {
		// TODO racial scores items and perks
		return 5.0
	}
	open fun maxFatigueMult():Double {
		// TODO racial scores items and perks
		return 1.0
	}
	open fun maxMana():Double {
		return round(maxManaBase()*maxManaMult()).coerceAtMost(2_499_999.0)
	}
	open fun maxManaBase():Double {
		var max = maxManaBaseStat.value
		// TODO perk, race, item bonuses
		max += level*maxManaPerLevel()
		if (level <= 6) max += level*10
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
		var max = 50.0
		// TODO perk, cultivation level, item, racial bonuses
		max += level*maxSoulforcePerLevel()
		if (level <= 6) max += level*5
		return round(max)
	}
	open fun maxSoulforcePerLevel():Double {
		// TODO perk, cultivation level, item, racial bonuses
		return 5.0
	}
	open fun maxSoulforceMult():Double {
		// TODO perk, cultivation level, item, racial bonuses
		return 1.0
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
	}

	fun hasCondition(condition: CombatCondition): Boolean {
		// TODO condition framework
		return false
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
}
