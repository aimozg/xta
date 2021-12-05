@file:Suppress("PropertyName")

package xta.flash

import xta.utils.JsonTuple3
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
	var counters:Array<dynamic>
	var versionID: Int?

	// CLOTHING/ARMOR
	var armorId:String
	var weaponId:String
	var weaponRangeId:String
	var weaponFlyingSwordsId:String
	var headJewelryId:String
	var necklaceId:String
	var jewelryId:String
	var jewelryId2:String
	var jewelryId3:String
	var jewelryId4:String
	var miscJewelryId:String
	var miscJewelryId2:String
	var shieldId:String
	var upperGarmentId:String
	var lowerGarmentId:String
	var armorName:String
	var vehiclesId:String

	// piercings
	var nipplesPLong:String
	var nipplesPShort:String
	var nipplesPierced:Int
	var lipPLong:String
	var lipPShort:String
	var lipPierced:Int
	var tonguePLong:String
	var tonguePShort:String
	var tonguePierced:Int
	var eyebrowPLong:String
	var eyebrowPShort:String
	var eyebrowPierced:Int
	var earsPLong:String
	var earsPShort:String
	var earsPierced:Int
	var nosePLong:String
	var nosePShort:String
	var nosePierced:Int

	// stats
	var stats: CocStatsJson
	// MAIN STATS
	var str: Double
	var tou: Double
	var spe:Double
	var inte:Double
	var wis:Double
	var lib:Double
	var sens:Double
	var cor:Double
	var fatigue:Double
	var mana:Double
	var soulforce:Double
	var wrath:Double
	// Combat STATS
	var HP:Double
	var lust:Double
	var teaseLevel:Int
	var teaseXP:Int
	// Mastery
	var masteryArcheryLevel:Int
	var masteryArcheryXP:Int
	var masteryAxeLevel:Int
	var masteryAxeXP:Int
	var masteryDaggerLevel:Int
	var masteryDaggerXP:Int
	var masteryDuelingSwordLevel:Int
	var masteryDuelingSwordXP:Int
	var masteryExoticLevel:Int
	var masteryExoticXP:Int
	var masteryFeralCombatLevel:Int
	var masteryFeralCombatXP:Int
	var masteryFirearmsLevel:Int
	var masteryFirearmsXP:Int
	var masteryGauntletLevel:Int
	var masteryGauntletXP:Int
	var masteryMaceHammerLevel:Int
	var masteryMaceHammerXP:Int
	var masteryPolearmLevel:Int
	var masteryPolearmXP:Int
	var masterySpearLevel:Int
	var masterySpearXP:Int
	var masterySwordLevel:Int
	var masterySwordXP:Int
	var masteryThrowingLevel:Int
	var masteryThrowingXP:Int
	var masteryWhipLevel:Int
	var masteryWhipXP:Int
	var dualWFLevel:Int
	var dualWFXP:Int
	var dualWLLevel:Int
	var dualWLXP:Int
	var dualWNLevel:Int
	var dualWNXP:Int
	var dualWSLevel:Int
	var dualWSXP:Int

	//Herbalism
	var herbalismLevel:Int
	var herbalismXP:Int

	//Prison STATS
	var hunger:Double
	var esteem:Double
	var obey:Double
	var obeySoftCap:Boolean?
	var will:Double
	var prisonItems:Array<dynamic>

	// LEVEL STATS
	var XP: Int
	var level: Int
	var gems: Int
	var perkPoints: Int
	var statPoints: Int
	var superPerkPoints: Int
	var ascensionPerkPoints: Int

	// Appearance
	var startingRace: String
	var gender: Int
	var femininity: Int
	var thickness: Int
	var tone: Int
	var tallness: Int
	var hairColor: String
	var hairType: Int
	var hairStyle: Int
	var gillType: Int
	var armType: Int
	var hairLength: Double
	var beardLength: Int
	var eyeType: Int
	var eyeColor: String
	var beardStyle: Int
	var tongueType: Int
	var earType: Int
	var antennae: Int
	var horns: Int
	var hornType: Int
	var rearBody: Int
	var facePart: CocFaceJson
	var lowerBodyPart: CocLowerBodyJson
	var skin: CocSkinJson
	var tail: CocTailJson
	var clawsPart: CocClawsJson
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
	var pregnancyIncubation:Int
	var pregnancyType:Int
	var buttPregnancyIncubation:Int
	var buttPregnancyType:Int

	var cocks: Array<CocPenisJson>
	var vaginas: Array<CocVaginaJson>
	var breastRows: Array<CocBreastRowJson>
	var perks: Array<CocPerkJson>
	var statusAffects: Array<CocStatusEffectJson>
	var ass: CocAssJson
	var keyItems: Array<CocKeyItemJson>
	var itemStorage: Array<CocItemJson>
	var pearlStorage: Array<CocItemJson>
	var gearStorage: Array<CocItemJson>
}

external interface CocPenisJson {
	var cockThickness: Double
	var cockLength: Double
	var cockType: Int
	var knotMultiplier: Double
	var pierced: Int
	var pShortDesc: String
	var pLongDesc: String
	var sock: String
}

external interface CocVaginaJson {
	var type: Int
	var vaginalWetness: Int
	var vaginalLooseness: Int
	var fullness: Double
	var virgin: Boolean?
	var labiaPierced: Int
	var labiaPShort: String
	var labiaPLong: String
	var clitPierced: Int
	var clitPShort: String
	var clitPLong: String
	var clitLength: Double
	var recoveryProgress: Double
}

external interface CocBreastRowJson {
	var breastRating: Double
	var breasts: Int
	var fuckable: Boolean?
	var fullness: Double
	var lactationMultiplier: Double
	var milkFullness: Double
	var nipplesPerBreast: Int
}

external interface CocFaceJson{
	var type: Int
}

external interface CocLowerBodyJson {
	var legCount: Int
	var type: Int
}

external interface CocAssJson  {
	var analLooseness: Int
	var analWetness: Int
	var fullness: Double
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

external interface CocTailJson {
	var count: Int
	var recharge: Int
	var type: Int
	var venom: Int
}

external interface CocClawsJson{
	var type: Int
	var tone: String
}

external interface CocStatsJson {
	var sens: CocBuffableStatJson
	var str: CocPrimaryStatJson
	var tou: CocPrimaryStatJson
	var spe: CocPrimaryStatJson
	var int: CocPrimaryStatJson
	var wis: CocPrimaryStatJson
	var lib: CocPrimaryStatJson
}

external interface CocPrimaryStatJson {
	var mult: CocBuffableStatJson
	var bonus: CocBuffableStatJson
	var core: CocRawStatJson
}

external interface CocRawStatJson {
	var value:Double
}

external interface CocBuffableStatJson {
	var effects: Array<JsonTuple3<Double, String, CocBuffJson>>
}

external interface CocBuffJson {
	var save:Boolean?
	var show:Boolean?
	var text:String
	var rate:Int
	var tick:Int
}

external interface CocItemJson {
	var id: String
	var quantity: Int
	var unlocked: Boolean?
}

external interface CocKeyItemJson {
	var keyName: String
	var value1: Double
	var value2: Double
	var value3: Double
	var value4: Double
}

external interface CocStatusEffectJson {
	var statusAffectName: String
	var value1: Double
	var value2: Double
	var value3: Double
	var value4: Double
}

external interface CocPerkJson {
	var id: String
	var value1: Double
	var value2: Double
	var value3: Double
	var value4: Double
}
