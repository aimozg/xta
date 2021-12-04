package xta.game

import xta.game.creature.Gender
import xta.game.creature.body.*

/*
 * Created by aimozg on 28.11.2021.
 */
abstract class Creature {

	var name: String = "You"

	/***
	 *    ███████  ██████  ██    ██ ██ ██████  ███    ███ ███████ ███    ██ ████████
	 *    ██      ██    ██ ██    ██ ██ ██   ██ ████  ████ ██      ████   ██    ██
	 *    █████   ██    ██ ██    ██ ██ ██████  ██ ████ ██ █████   ██ ██  ██    ██
	 *    ██      ██ ▄▄ ██ ██    ██ ██ ██      ██  ██  ██ ██      ██  ██ ██    ██
	 *    ███████  ██████   ██████  ██ ██      ██      ██ ███████ ██   ████    ██
	 *                ▀▀
	 *
	 */


	/***
	 *    ███████ ████████  █████  ████████ ███████
	 *    ██         ██    ██   ██    ██    ██
	 *    ███████    ██    ███████    ██    ███████
	 *         ██    ██    ██   ██    ██         ██
	 *    ███████    ██    ██   ██    ██    ███████
	 */

	//Primary stats
	var cor:Int = 0
	var fatigue:Int = 0
	var mana:Int = 0
	var soulforce:Int = 0

	//Combat Stats
	var HP:Int = 0
	var lust:Int = 0
	var wrath:Int = 0

	//Level Stats
	var xp: Int = 0
	var level: Int = 0
	var gems: Int = 0

	/***
	 *     █████  ██████  ██████  ███████  █████  ██████   █████  ███    ██  ██████ ███████
	 *    ██   ██ ██   ██ ██   ██ ██      ██   ██ ██   ██ ██   ██ ████   ██ ██      ██
	 *    ███████ ██████  ██████  █████   ███████ ██████  ███████ ██ ██  ██ ██      █████
	 *    ██   ██ ██      ██      ██      ██   ██ ██   ██ ██   ██ ██  ██ ██ ██      ██
	 *    ██   ██ ██      ██      ███████ ██   ██ ██   ██ ██   ██ ██   ████  ██████ ███████
	 *
	 *
	 */

	abstract fun race(): String

	var tallness: Int = 0
	var femininity: Int = 50

	var hairType = HairType.NORMAL
	var hairStyle = HairStyle.PLAIN
	var hairColor = "no"
	var hairColor2 = ""
	var hairLength = 0
//	var beardStyle = BeardStyle.NORMAL
//	var beardLength = 0

	val skin = SkinPart(this)
	val skinColor get() = skin.skinColor
	val skinColor2 get() = skin.skinColor2
	val furColor get() = skin.furColor
	val furColor2 get() = skin.furColor2
	val scaleColor get() = skin.scaleColor
	val scaleColor2 get() = skin.scaleColor2
	val chitinColor get() = skin.chitinColor
	val chitinColor2 get() = skin.chitinColor2
	// facePart
	// clawsPart
	// underBody
	// ears
	// horns
	// wings
	// lowerBodyPart
	// tail
	// antennae
	val eyePart = EyePart(this)
	val eyeColor get() = eyePart.irisColor
	// tongue
	// arms
	// gills
	// rearBody

	var thickness: Int = 0
	var tone: Int = 0
	var hipRating: Int = 0
	var buttRating: Int = 0
	// ass

	// piercings

	// Sexual Stuff
	// MALE STUFF
	val cocks = ArrayList<PenisPart>()
	var balls = 0
	var cumMultiplier = 1.0
	var ballSize = 0
	// hoursSinceCum?

	// FEMALE STUFF
	val vaginas = ArrayList<VaginaPart>()
	// clitLength
	// fertility
	// nipplelength
	val breastRows = ArrayList<BreastRowPart>()


	/***
	 *    ██    ██ ████████ ██ ██      ██ ████████ ██ ███████ ███████
	 *    ██    ██    ██    ██ ██      ██    ██    ██ ██      ██
	 *    ██    ██    ██    ██ ██      ██    ██    ██ █████   ███████
	 *    ██    ██    ██    ██ ██      ██    ██    ██ ██           ██
	 *     ██████     ██    ██ ███████ ██    ██    ██ ███████ ███████
	 *
	 *
	 */

	val isAlive get() = HP > 0

	val gender
		get() = when {
			hasCock() && hasVagina() -> Gender.HERM
			hasCock() -> Gender.MALE
			hasVagina() -> Gender.FEMALE
			else -> Gender.GENDERLESS
		}

	val skinTone get() = skin.color
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
