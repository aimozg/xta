package xta.game.text

import xta.game.PlayerCharacter
import xta.text.displayInches

/*
 * Created by aimozg on 05.12.2021.
 */
class PlayerAppearance(private val character:PlayerCharacter) {
	fun describe() = buildString {
		append("<h3>"+character.name+", Level "+character.level+" "+character.race()+"</h3>")
		describeRace()
	}

	private fun StringBuilder.describeRace() {
		//Discuss race
		if (character.race() != character.startingRace) append("[He] began [his] journey as a " + character.startingRace + ", but gave that up as [he] explored the dangers of this realm. ")
		//Height and race.
		append("[He] [is] a ")
		append(character.tallness.displayInches())
		val pcrace:String = character.race()
		val genderlessRace:Array<String> = arrayOf("half cow-morph", "half cow-girl", "cow-girl", "cow-girl", "cow-morph", "minotaur", "half-minotaur", "alraune", "liliraune", "half unicorn", "unicorn", "unicornkin", "half alicorn", "alicorn", "alicornkin", "true alicorn", "half bicorn", "bicorn", "bicornkin", "half nightmare","nightmare", "nightmarekin", "true nightmare")
		if (pcrace !in genderlessRace) {
			append(" tall [malefemaleherm] [race], with [bodytype].");
		} else {
			append(" tall [race], with [bodytype].");
		}
	}
}
