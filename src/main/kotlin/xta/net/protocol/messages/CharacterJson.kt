package xta.net.protocol.messages

import xta.game.PlayerCharacter
import xta.utils.jsobject

external interface CharacterJson {
	var startingRace: String
	var name:String

	var level: Int
	var xp: Int
	var gems:Int

	var tallness: Int
	var femininity: Int

	var thickness: Int
	var tone: Int
}

/*
 * Created by aimozg on 30.11.2021.
 */
fun PlayerCharacter.toJson(): CharacterJson = jsobject { json ->
	// TODO consider using annotations instead of listing fields
	json.startingRace = startingRace
	json.name = name

	json.level = level
	json.xp = xp
	json.gems = gems

	json.tallness = tallness
	json.femininity = femininity

	json.thickness = thickness
	json.tone = tone
}

fun PlayerCharacter.fromJson(json:CharacterJson): PlayerCharacter {
	startingRace = json.startingRace
	name = json.name

	level = json.level
	xp = json.xp
	gems = json.gems

	tallness = json.tallness
	femininity = json.femininity

	thickness = json.thickness
	tone = json.tone

	return this
}
