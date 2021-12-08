package xta.game

import xta.game.creature.Gender
import xta.game.creature.body.SkinCoatType
import xta.game.stats.BuffableStat
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

	abstract fun race(): String

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
		get() = hp.toDouble()/maxHp()
		set(value) {
			hp = (maxHp()*value).roundToInt()
		}
	var lustRatio
		get() = lust.toDouble()/maxLust()
		set(value) {
			lust = (maxLust()*value).roundToInt()
		}
	var wrathRatio
		get() = wrath.toDouble()/maxWrath()
		set(value) {
			wrath = (maxWrath()*value).roundToInt()
		}
	val staminaRatio get() = 1.0-fatigueRatio
	var fatigueRatio
		get() = fatigue.toDouble()/maxFatigue()
		set(value) {
			fatigue = (maxFatigue()*value).roundToInt()
		}
	var manaRatio
		get() = mana.toDouble()/maxMana()
		set(value) {
			mana = (maxMana()*value).roundToInt()
		}
	var sfRatio
		get() = soulforce.toDouble()/maxSoulforce()
		set(value) {
			soulforce = (maxSoulforce()*value).roundToInt()
		}

	fun xpToLevelUp(): Int {
		var xpm = 100
		if (level >= 42) xpm += 100
		if (level >= 102) xpm += 100
		if (level >= 180) xpm += 100
		return (level*xpm).coerceAtMost(74000)
	}
	open fun maxHp():Int {
		return (maxHpBase()*maxHpMult()).roundToInt().coerceAtMost(9_999_999)
	}
	open fun maxHpPerLevel():Int {
		return 60
	}
	open fun maxHpBase():Int {
		var max = 0
		val tou = tou.roundToInt()
		// TODO account for perks

		max += tou*2+50
		if (tou >= 21) max += tou
		if (tou >= 41) max += tou
		if (tou >= 61) max += tou
		if (tou >= 81) max += tou
		if (tou >= 101) max += tou * ((tou - 100) / 50 + 1)

		max += level * maxHpPerLevel()
		if (level <= 6) max += level * 60

		return max
	}
	open fun maxHpMult():Double {
		// TODO scores, green cock socks, mecha
		return 1.0
	}
	open fun minLust():Int {
		// TODO calculations
		return 0
	}
	open fun maxLust():Int {
		return (maxLustBase()*maxLustMult()).roundToInt().coerceAtMost(199_999)
	}
	open fun maxLustBase():Int {
		var max = 100
		// TODO perk bonuses
		max += level*maxLustPerLevel()
		if (level <= 6) max += level*3
		return max
	}
	open fun maxLustPerLevel():Int {
		// TODO perk bonuses
		return 3
	}
	open fun maxLustMult():Double {
		// TODO calculate
		return 1.0
	}
	open fun maxWrath():Int {
		return (maxWrathBase()*maxWrathMult()).roundToInt().coerceAtMost(476_999)
	}
	open fun maxWrathBase():Int {
		var max = 500
		// TODO perk, item, race bonuses
		max += level*maxWrathPerLevel()
		if (level <= 6) max += level*5
		return max
	}
	open fun maxWrathPerLevel():Int {
		// TODO perk, item, race bonuses
		return 5
	}
	open fun maxWrathMult():Double {
		// TODO perk, item, race bonuses
		return 1.0
	}

	/**
	 * Usable fatigue points
	 */
	var stamina:Int
		get() = maxFatigue() - fatigue
		set(value) {
			fatigue = maxFatigue() - value
		}
	open fun maxFatigue():Int {
		return (maxFatigueBase()*maxFatigueMult()).roundToInt().coerceAtMost(1_499_999)
	}
	open fun maxFatigueBase():Int {
		var max = 150
		// TODO racial scores items and perks
		max += level*maxFatiguePerLevel()
		if (level <= 6) max += level*5
		return max
	}
	open fun maxFatiguePerLevel():Int {
		// TODO racial scores items and perks
		return 5
	}
	open fun maxFatigueMult():Double {
		// TODO racial scores items and perks
		return 1.0
	}
	open fun maxMana():Int {
		return (maxManaBase()*maxManaMult()).roundToInt().coerceAtMost(2_499_999)
	}
	open fun maxManaBase():Int {
		var max = 300
		// TODO perk, race, item bonuses
		max += level*maxManaPerLevel()
		if (level <= 6) max += level*10
		return max
	}
	open fun maxManaPerLevel():Int {
		// TODO perk, race, item bonuses
		return 10
	}
	open fun maxManaMult():Double {
		// TODO perk, race, item bonuses
		return 1.0
	}
	open fun maxSoulforce():Int {
		return (maxSoulforceBase()*maxSoulforceMult()).roundToInt().coerceAtMost(1_499_999)
	}
	open fun maxSoulforceBase():Int {
		var max = 50
		// TODO perk, cultivation level, item, racial bonuses
		max += level*maxSoulforcePerLevel()
		if (level <= 6) max += level*5
		return max
	}
	open fun maxSoulforcePerLevel():Int {
		// TODO perk, cultivation level, item, racial bonuses
		return 5
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

	val eyeColor get() = eyePart.irisColor
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
	// hasCoat, hasFullCoat, coatType, hasCoatOfType, hasFullCoatOfType, skinDesc, skinAdj
	// hasGills
	// faceType
	// clawTone, clawType
	// lowerBody, legCount
	val isTaur get() = false // TODO check lower body type/legs count
	// tailType, tailVenom, tailCount, tailRecharge
	fun hasFur() = skin.hasCoatOfType(SkinCoatType.FUR)

	fun hasCock() = cocks.isNotEmpty()
	fun hasVagina() = vaginas.isNotEmpty()

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
}
