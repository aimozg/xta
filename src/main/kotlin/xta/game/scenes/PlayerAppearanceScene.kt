package xta.game.scenes

import xta.game.Scene
import xta.text.Display
import xta.text.displayInches

/*
 * Created by aimozg on 28.11.2021.
 */
object PlayerAppearanceScene: Scene("PlayerAppearance") {
	override fun Display.doExecute() {
		outputText("<h3>"+character.name+", Level "+character.level+" "+character.race()+"</h3>")
		describeRace()
	}

	private fun Display.describeRace() {
		//Discuss race
		if (character.race() != character.startingRace) outputText("[He] began [his] journey as a " + character.startingRace + ", but gave that up as [he] explored the dangers of this realm. ")
		//Height and race.
		outputText("[He] [is] a ")
		outputText(character.tallness.displayInches())
		val pcrace:String = character.race()
		val genderlessRace:Array<String> = arrayOf("half cow-morph", "half cow-girl", "cow-girl", "cow-girl", "cow-morph", "minotaur", "half-minotaur", "alraune", "liliraune", "half unicorn", "unicorn", "unicornkin", "half alicorn", "alicorn", "alicornkin", "true alicorn", "half bicorn", "bicorn", "bicornkin", "half nightmare","nightmare", "nightmarekin", "true nightmare")
		if (pcrace !in genderlessRace) {
			outputText(" tall [malefemaleherm] [race], with [bodytype].");
		} else {
			outputText(" tall [race], with [bodytype].");
		}
	}
}
