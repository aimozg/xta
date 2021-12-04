package xta.game.text

import xta.game.PlayerCharacter

/*
 * Created by aimozg on 28.11.2021.
 */

fun evalParserTag(
	myCharacter: PlayerCharacter?,
	character: PlayerCharacter?,
	tag: String,
	tagArgs: String
): String = when (tag) {
	"pg" -> "\n\n"
	else ->
		if (character == null) error("Character for tag '$tag' not selected")
		else when (tag) {
			"bodytype" -> Appearance.bodyType(character)
			"malefemaleherm" -> Appearance.maleFemaleHerm(character)
			"race" -> character.race()

			"name" -> character.name
			"is" -> if (character === myCharacter) "are" else "is"
			"he" -> if (character === myCharacter) "you" else character.mf("he","she")
			"him" -> if (character === myCharacter) "your" else character.mf("him","her")
			"his" -> if (character === myCharacter) "your" else character.mf("his","her")
			else -> {
				error("Unknown tag $tag")
			}
		}
}
