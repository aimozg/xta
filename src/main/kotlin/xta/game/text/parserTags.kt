package xta.game.text

import xta.game.PlayerCharacter
import xta.text.Parser
import xta.text.adjustCase
import xta.text.num2text

/*
 * Created by aimozg on 28.11.2021.
 */

fun evalNpcTag(forMe:Boolean, char: PlayerCharacter, tag:String, tagArgs: String) = when (tag) {
	"bodytype" -> Appearance.bodyType(char)
	"butt" -> Appearance.buttDescript(char)
	"eyecolor" -> char.eyes.irisColor
	"haircolor" -> char.hairColor
	"hips" -> Appearance.hipDescript(char)
	"level" -> char.level.toString()
	"malefemaleherm" -> Appearance.maleFemaleHerm(char)
	"name" -> char.name
	"race" -> char.raceName()
	"racefull" -> char.raceFullName()
	"skin" -> when (tagArgs.trim()) {
		"isare" -> char.skin.isAre()
		"vs" -> char.skin.isAre("s", "")

		"skin" -> char.skin.describeSkin(noadj = false, nocolor = false)
		"noadj" -> char.skin.describeSkin(noadj = true, nocolor = false)
		"notone", "nocolor" -> char.skin.describeSkin(noadj = true, nocolor = false)
		"type" -> char.skin.describeSkin(noadj = true, nocolor = true)
		"color" -> char.skin.color
		"color2" -> char.skin.color2

		"base" -> char.skin.describeBase(noadj = false, nocolor = false)
		"base.noadj" -> char.skin.describeBase(noadj = true, nocolor = false)
		"base.notone", "base.nocolor" -> char.skin.describeBase(noadj = true, nocolor = false)
		"base.type" -> char.skin.describeBase(noadj = true, nocolor = true)
		"base.color" -> char.skin.baseColor
		"base.color2" -> char.skin.baseColor2

		"coat" -> char.skin.describeCoat(noadj = false, nocolor = false)
		"coat.noadj" -> char.skin.describeCoat(noadj = true, nocolor = false)
		"coat.notone", "coat.nocolor" -> char.skin.describeCoat(noadj = true, nocolor = false)
		"coat.type" -> char.skin.describeCoat(noadj = true, nocolor = true)
		"coat.color" -> char.skin.coatColor
		"coat.color2" -> char.skin.coatColor2

		"full" -> char.skin.describeFull(noadj = false, nocolor = false)
		"full.noadj" -> char.skin.describeFull(noadj = true, nocolor = false)
		"full.notone", "full.nocolor" -> char.skin.describeFull(noadj = true, nocolor = false)
		"full.type" -> char.skin.describeFull(noadj = true, nocolor = true)

		else -> error("Unknown tag $tag$tagArgs")
	}
	"tailcount" -> num2text(char.tail.count)

	// Tags to address either player itself or other character
	"is", "are" -> if (forMe) "are" else "is"
	"have", "has" -> if (forMe) "have" else "has"
	"he" -> if (forMe) "you" else char.mf("he", "she")
	"him" -> if (forMe) "you" else char.mf("him", "her")
	"his" -> if (forMe) "your" else char.mf("his", "her")

	"you" -> if (forMe) "you" else char.name
	"your" -> if (forMe) "your" else char.mf("his", "her") //char.name+"'s"
	"youre" -> if (forMe) "you're" else char.mf("he's", "she's") //char.name+"'s"
	"youve" -> if (forMe) "you've" else char.mf("he's", "she's") //char.name+"'s"
	"yourself" -> if (forMe) "yourself" else char.mf("himself", "herself")

	// You are/do/kiss/walk
	// He is/does/kisses/walks
	// Usage: [verb be], [verb do], [verb kiss], [verb walk]
	"verb" -> {
		val verb = tagArgs.trim()
		if (forMe) {
			when (verb) {
				"be", "is", "are" -> "are"
				"was", "were" -> "were"
				"has", "have" -> "have"
				else -> verb
			}
		} else when (verb) {
			"be", "is", "are" -> "is"
			"was", "were" -> "was"
			"has", "have" -> "has"
			else -> {
				if (verb.endsWith("o") || verb.endsWith("s") || verb.endsWith("ch")) verb+"es"
				else verb+"s"
			}
		}
	}
	else -> {
		error("Unknown tag $tag$tagArgs")
	}
}

fun shiftTagArg(restArgs:String):Pair<String,String>? {
	if (restArgs.isEmpty()) return null
	val si = restArgs.indexOf(' ')
	if (si < 0) return restArgs to ""
	return restArgs.substring(0, si) to restArgs.substring(si+1).trimStart()
}

fun Parser.evalGameTag(tag:String, tagArgs:String):String {
	val char = npc0
	val myChar = myPlayer?.char
	return when {
		tag == "pg" -> "\n\n"
		// TODO need to somehow force third-person use for own character as well
		tag.startsWith("npc") -> {
			val id = tag.removePrefix("npc").toIntOrNull() ?: error("Invalid npc tag '$tag'")
			val npc = npcs[id] ?: error("Character for tag '$tag' not selected")
			val (subtag,subargs) = shiftTagArg(tagArgs.trimStart()) ?: error("Invalid npc tag '$tag$tagArgs'")
			adjustCase(subtag, evalNpcTag(npc === myChar, npc, subtag.lowercase(), subargs))
		}
		else -> {
			if (char == null) error("Character for tag '$tag' not selected")
			else {
				evalNpcTag(char === myChar, char, tag, tagArgs)
			}
		}
	}
}
