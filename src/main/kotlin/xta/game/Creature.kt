package xta.game

import xta.game.creature.Gender
import xta.game.creature.body.SkinCoatType

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

	val isAlive get() = hp > 0

	// TODO calculations
	fun xpToLevelUp(): Int {
		return 1000
	}
	fun maxHp():Int {
		return 100
	}
	fun minLust():Int {
		return 0
	}
	fun maxLust():Int {
		return 100
	}
	fun maxWrath():Int {
		return 100
	}
	var stamina:Int
		get() = maxFatigue() - fatigue
		set(value) {
			fatigue = maxFatigue() - value
		}
	fun maxFatigue():Int {
		return 100
	}
	fun maxMana():Int {
		return 100
	}
	fun maxSoulforce():Int {
		return 100
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

}
