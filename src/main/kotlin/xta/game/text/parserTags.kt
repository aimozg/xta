package xta.game.text

import xta.text.Parser

/*
 * Created by aimozg on 28.11.2021.
 */

fun Parser.evalGameTag(tag:String, tagArgs:String):String {
	val char = player?.char
	val myChar = myPlayer?.char
	return when (tag) {
		"pg" -> "\n\n"
		else -> {
			if (char == null) error("Character for tag '$tag' not selected")
			else {
				val forMe = char === myChar
				when (tag) {
					"bodytype" -> Appearance.bodyType(char)
					"malefemaleherm" -> Appearance.maleFemaleHerm(char)
					"race" -> char.race()

					"name" -> char.name
					"is" -> if (forMe) "are" else "is"
					"he" -> if (forMe) "you" else char.mf("he", "she")
					"him" -> if (forMe) "your" else char.mf("him", "her")
					"his" -> if (forMe) "your" else char.mf("his", "her")

					"you" -> if (forMe) "you" else char.name
					"your" -> if (forMe) "your" else char.name+"'s"

					// You are/do/kiss/walk
					// He is/does/kisses/walks
					// Usage: [verb be], [verb do], [verb kiss], [verb walk]
					"verb" -> {
						val verb = tagArgs.trim()
						if (forMe) {
							when (verb) {
								"be", "is", "are" -> "are"
								"was", "were" -> "were"
								else -> verb
							}
						} else when {
							verb == "be" || verb == "is" || verb == "are" -> "is"
							verb == "was" || verb == "were" -> "was"
							verb.endsWith("o") || verb.endsWith("s") -> verb+"es"
							else -> verb+"s"
						}
					}
					else -> {
						error("Unknown tag $tag")
					}
				}
			}
		}
	}
}
