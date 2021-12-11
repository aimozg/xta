package xta.game.text

import xta.game.PlayerCharacter
import xta.text.displayInches
import xta.utils.decapitalized

/*
 * Created by aimozg on 05.12.2021.
 */
class PlayerAppearance(private val character:PlayerCharacter) {
	fun describe() = buildString {
		append("<h3>"+character.name+", Level "+character.level+" "+character.raceFullName()+"</h3>")
		append(describeRace())

		append("[pg]")
		append(describeSkin())

		append(" ")
		append(describeFace())

		append(" Your features are adorned by ")
		append(character.face.describeMF(true))
		append(".")
		append("[pg]")

		append(describeHair())
		// TODO ears
		// TODO horns
		// TODO horns
		// TODO antennae
		// TODO beard
		// TODO gills
		// TODO visage
		// TODO arms
		// TODO lower body
		// TODO wings
		// TODO rear body
		// TODO tail
		// TODO breasts
		// TODO crotch
		// TODO cock
		// TODO balls
		// TODO pussy
		// TODO asshole
		// TODO piercings
		// TODO special
		// TODO pregnancy
		// TODO equipment
	}

	fun describeRace() = buildString {
		//Discuss race
		if (character.raceName() != character.startingRace) append("[He] began [his] journey as a " + character.startingRace + ", but gave that up as [he] explored the dangers of this realm. ")
		//Height and race.
		append("[He] [is] a ")
		append(character.tallness.displayInches())
		append(" tall [racefull], with [bodytype].")
		// TODO move to races
		// val genderlessRace:Array<String> = arrayOf("half cow-morph", "half cow-girl", "cow-girl", "cow-girl", "cow-morph", "minotaur", "half-minotaur", "alraune", "liliraune", "half unicorn", "unicorn", "unicornkin", "half alicorn", "alicorn", "alicornkin", "true alicorn", "half bicorn", "bicorn", "bicornkin", "half nightmare","nightmare", "nightmarekin", "true nightmare")
//		if (pcrace !in genderlessRace) {
//			append(" tall [malefemaleherm] [race], with [bodytype].");
//		} else {
//			append(" tall [race], with [bodytype].");
//		}
	}

	fun describeSkin() = buildString {
		val skinDesc = character.skin.baseType.appearanceDescription()
		val patternDesc = character.skin.basePattern.appearanceDescription()
		append(skinDesc)
		if (skinDesc.isNotEmpty() && patternDesc.isNotEmpty()) {
			append(", while ")
			append(patternDesc.decapitalized())
		} else {
			append(patternDesc)
		}
	}

	fun describeFace() = character.face.appearanceDescription()

	fun describeHair() = character.hair.appearanceDescription()
}
