@file:Suppress("PropertyName")

package xta.flash

import kotlin.js.Json

/*
 * Created by aimozg on 28.11.2021.
 */
external interface CocSaveFileJson {
	var data: CocSaveFileDataJson?
}

external interface CocSaveFileDataJson {
	var exists: Boolean?

	var version: String

	// Saveable states
	var ss: Json

	var short: String
	var a: String
	var notes: String

	var flags:Array<dynamic>
	var versionID: Int?

	// piercings

	// stats

	// MAIN STATS
	// CLOTHING/ARMOR

	// Combat STATS

	// LEVEL STATS
	var XP: Int
	var level: Int
	var gems: Int
	var perkPoint: Int
	var statPoints: Int
	var superPerkPoints: Int
	var ascensionPerkPoints: Int

	// Appearance
	var startingRace: String
	var femininity: Int
	var eyeType: Int
	var eyeColor: String
	var beardLength: Int
	var beardStyle: Int
	var tone: Int
	var thickness: Int
	var tallness: Int
	var hairColor: String
	var hairType: Int
	var hairStyle: Int
	var gillType: Int
	var hairLength: Int
	// lower body, wings
	var skin: CocSkinJson
	// claws, face, tail
	var armType: Int
	var tongueType: Int
	var earType: Int
	var antennae: Int
	var horns: Int
	var hornType: Int
	var rearBodyType: Int
	var wingDesc: String
	var wingType: Int
	var hipRating: Int
	var buttRating: Int

	//Sexual Stuff
	var balls: Int
	var cumMultiplier: Double
	var ballSize: Int
	var hoursSinceCum: Int
	var fertility: Double

	//Preggo stuff

	// Cocks
	var cocks: Array<CocPenisJson>
	// Vaginas
	var vaginas: Array<CocVaginaJson>
}

external interface CocPenisJson {
	var cockThickness: Double
	var cockLength: Double
	var cockType: Int
	var knotMultiplier: Double
	var sock: String
	// piercing
}

external interface CocVaginaJson {
	var vaginalWetness: Int
	var vaginalLooseness: Int
	var fullness: Double
	var virgin: Boolean
	var type: Int
	// piercing
}

external interface CocSkinJson {
	var coverage: Int
	var base: CocSkinLayerJson
	var coat: CocSkinLayerJson
}

external interface CocSkinLayerJson {
	var type: Int
	var adj: String
	var desc: String
	var color: String
	var color2: String
	var pattern: Int
}
