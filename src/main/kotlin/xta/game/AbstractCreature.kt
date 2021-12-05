package xta.game

import xta.game.creature.body.*
import xta.game.stats.BuffPool
import xta.game.stats.IStatHolder
import xta.game.stats.PrimaryStat
import xta.game.stats.StatStore
import xta.net.serialization.JsonSerializable

/**
 * [Creature] data as a separate superclass for readability.
 * (If it makes things worse, just push down and remove this class)
 *
 * Created by aimozg on 05.12.2021.
 */
@Suppress("NON_EXPORTABLE_TYPE")
@JsExport
sealed class AbstractCreature: JsonSerializable(), IStatHolder {

	override fun allStats() = statStore.allStats()
	override fun findStat(statName: String) = statStore.findStat(statName)

	/*
	 *    ███████ ████████  █████  ████████ ███████
	 *    ██         ██    ██   ██    ██    ██
	 *    ███████    ██    ███████    ██    ███████
	 *         ██    ██    ██   ██    ██         ██
	 *    ███████    ██    ██   ██    ██    ███████
	 */
	var name: String by property("You")

	val statStore = StatStore()

	//new stat area
	val strStat by nestedProperty(PrimaryStat(this as Creature, "str").also {
		statStore.addStat(it)
	})
	val touStat by nestedProperty(PrimaryStat(this as Creature, "tou").also {
		statStore.addStat(it)
	})
	val speStat by nestedProperty(PrimaryStat(this as Creature, "spe").also {
		statStore.addStat(it)
	})
	val intStat by nestedProperty(PrimaryStat(this as Creature, "int").also {
		statStore.addStat(it)
	})
	val wisStat by nestedProperty(PrimaryStat(this as Creature, "wis").also {
		statStore.addStat(it)
	})
	val libStat by nestedProperty(PrimaryStat(this as Creature, "lib").also {
		statStore.addStat(it)
	})
	val sensStat by nestedProperty(BuffPool("sens", baseValue = 15.0).also {
		statStore.addStat(it)
	})

	//Primary stats
	var cor:Int by property(0)
	var fatigue:Int by property(0)
	var mana:Int by property(0)
	var soulforce:Int by property(0)

	//Combat Stats
	var hp:Int by property(0)
	var lust:Int by property(0)
	var wrath:Int by property(0)

	//Level Stats
	var xp: Int by property(0)
	var level: Int by property(0)
	var gems: Int by property(0)

	/*
	 *     █████  ██████  ██████  ███████  █████  ██████   █████  ███    ██  ██████ ███████
	 *    ██   ██ ██   ██ ██   ██ ██      ██   ██ ██   ██ ██   ██ ████   ██ ██      ██
	 *    ███████ ██████  ██████  █████   ███████ ██████  ███████ ██ ██  ██ ██      █████
	 *    ██   ██ ██      ██      ██      ██   ██ ██   ██ ██   ██ ██  ██ ██ ██      ██
	 *    ██   ██ ██      ██      ███████ ██   ██ ██   ██ ██   ██ ██   ████  ██████ ███████
	 *
	 *
	 */

	var tallness: Int by property(0)
	var femininity: Int by property(50)

	var hairType by property(HairType.NORMAL)
	var hairStyle by property(HairStyle.PLAIN)
	var hairColor by property("no")
	var hairColor2 by property("")
	var hairLength by property(0)
	// TODO body part - beard
//	var beardStyle = BeardStyle.NORMAL
//	var beardLength = 0

	val skin by nestedProperty(SkinPart(this as Creature))
	// facePart
	// clawsPart
	// underBody
	// ears
	// horns
	// wings
	// lowerBodyPart
	// tail
	// antennae
	val eyePart by nestedProperty(EyePart(this as Creature))
	// tongue
	// arms
	// gills
	// rearBody

	var thickness by property(0)
	var tone by property(0)
	var hipRating by property(0)
	var buttRating by property(0)
	// ass

	// piercings

	// Sexual Stuff
	// MALE STUFF
	val cocks by nestedJsonList { PenisPart() }
	var balls by property(0)
	var cumMultiplier by property(1.0)
	var ballSize by property(0)
	// hoursSinceCum?

	// FEMALE STUFF
	val vaginas by nestedJsonList { VaginaPart() }
	// clitLength
	// fertility
	// nipplelength
	val breastRows by nestedJsonList { BreastRowPart() }

}
