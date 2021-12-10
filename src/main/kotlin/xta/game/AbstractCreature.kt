package xta.game

import xta.game.creature.PerkManager
import xta.game.creature.body.*
import xta.game.stats.*
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
	val strStat by nestedProperty(PrimaryStat(this as Creature, Stats.STR))
	val touStat by nestedProperty(PrimaryStat(this as Creature, Stats.TOU))
	val speStat by nestedProperty(PrimaryStat(this as Creature, Stats.SPE))
	val intStat by nestedProperty(PrimaryStat(this as Creature, Stats.INT))
	val wisStat by nestedProperty(PrimaryStat(this as Creature, Stats.WIS))
	val libStat by nestedProperty(PrimaryStat(this as Creature, Stats.LIB))
	val sensStat by nestedProperty(BuffableStat(Stats.SENS, baseValue = 15.0))

	//Primary stats
	var cor:Int by property(0)
	var fatigue:Int by property(0)
	var mana:Int by property(0)
	var soulforce:Int by property(0)

	//Combat Stats
	var hp:Int by property(0)
	var lust:Int by property(0)
	var wrath:Int by property(0)

	val maxHpBaseStat by nestedProperty(BuffableStat(Stats.HP_MAX_BASE, baseValue = 50.0))
	val maxHpPerLevelStat by nestedProperty(BuffableStat(Stats.HP_MAX_PERLEVEL, baseValue = 60.0))
	val maxHpMultStat by nestedProperty(BuffableStat(Stats.HP_MAX_MULT, baseValue = 1.0))
	val maxManaBaseStat by nestedProperty(BuffableStat(Stats.MANA_MAX_BASE, baseValue = 300.0))
	val maxManaPerLevelStat by nestedProperty(BuffableStat(Stats.MANA_MAX_PERLEVEL, baseValue = 10.0))
	val maxManaMultStat by nestedProperty(BuffableStat(Stats.MANA_MAX_MULT, baseValue = 1.0))


	//Level Stats
	var xp: Int by property(0)
	var level: Int by property(0)
	var gems: Int by property(0)

	val perks by nestedProperty(PerkManager(this as Creature))

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
	var beardStyle by property(BeardStyle.NORMAL)
	var beardLength by property(0)

	val skin by nestedProperty(SkinPart(this as Creature))
	val face by nestedProperty(FacePart(this as Creature))
	val claws by nestedProperty(ClawsPart(this as Creature))
	val underBody by nestedProperty(UnderBodyPart(this as Creature))
	val ears by nestedProperty(EarsPart(this as Creature))
	val horns by nestedProperty(HornsPart(this as Creature))
	val wings by nestedProperty(WingsPart(this as Creature))
	val lowerBody by nestedProperty(LowerBodyPart(this as Creature))
	val tail by nestedProperty(TailPart(this as Creature))
	val antennae by nestedProperty(AntennaePart(this as Creature))
	val eyes by nestedProperty(EyePart(this as Creature))
	val tongue by nestedProperty(TonguePart(this as Creature))
	val arms by nestedProperty(ArmsPart(this as Creature))
	val gills by nestedProperty(GillsPart(this as Creature))
	val rearBody by nestedProperty(RearBodyPart(this as Creature))

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

	init {
		for (descriptor in propertyDescriptors) {
			val stat = descriptor.fieldValue as? IStat ?: continue
			statStore.addStat(stat)
		}
	}
}
