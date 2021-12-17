package xta.game

import xta.game.combat.StatusEffect
import xta.game.creature.PerkManager
import xta.game.creature.body.*
import xta.game.items.ArmorItem
import xta.game.items.ItemSerializers
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
	val strStat by nestedProperty(PrimaryStat(this as Creature, Stats.STR, "Strength"))
	val touStat by nestedProperty(PrimaryStat(this as Creature, Stats.TOU, "Toughness"))
	val speStat by nestedProperty(PrimaryStat(this as Creature, Stats.SPE, "Speed"))
	val intStat by nestedProperty(PrimaryStat(this as Creature, Stats.INT, "Intelligence"))
	val wisStat by nestedProperty(PrimaryStat(this as Creature, Stats.WIS, "Wisdom"))
	val libStat by nestedProperty(PrimaryStat(this as Creature, Stats.LIB, "Libido"))
	val sensStat by nestedProperty(BuffableStat(Stats.SENS, baseValue = 15.0))

	//Primary stats
	var cor:Int by property(0)
	var fatigue:Double by property(0.0)
	var mana:Double by property(0.0)
	var soulforce:Double by property(0.0)

	//Combat Stats
	var hp:Double by property(0.0)
	var lust:Double by property(0.0)
	var wrath:Double by property(0.0)

	val maxHpBaseStat by nestedProperty(BuffableStat(Stats.HP_MAX_BASE, baseValue = 50.0))
	val maxHpPerLevelStat by nestedProperty(BuffableStat(Stats.HP_MAX_PERLEVEL, baseValue = 60.0))
	val maxHpMultStat by nestedProperty(BuffableStat(Stats.HP_MAX_MULT, baseValue = 1.0))
	val minLustStat by nestedProperty(BuffableStat(Stats.LUST_MIN, baseValue = 0.0))
	val maxLustBaseStat by nestedProperty(BuffableStat(Stats.LUST_MAX_BASE, baseValue = 100.0))
	val maxLustPerLevelStat by nestedProperty(BuffableStat(Stats.LUST_MAX_PERLEVEL, baseValue = 3.0))
	val maxLustMultStat by nestedProperty(BuffableStat(Stats.LUST_MAX_MULT, baseValue = 1.0))
	val maxWrathBaseStat by nestedProperty(BuffableStat(Stats.WRATH_MAX_BASE, baseValue = 500.0))
	val maxWrathPerLevelStat by nestedProperty(BuffableStat(Stats.WRATH_MAX_PERLEVEL, baseValue = 5.0))
	val maxWrathMultStat by nestedProperty(BuffableStat(Stats.WRATH_MAX_MULT, baseValue = 1.0))
	val maxFatigueBaseStat by nestedProperty(BuffableStat(Stats.FATIGUE_MAX_BASE, baseValue = 150.0))
	val maxFatiguePerLevelStat by nestedProperty(BuffableStat(Stats.FATIGUE_MAX_PERLEVEL, baseValue = 5.0))
	val maxFatigueMultStat by nestedProperty(BuffableStat(Stats.FATIGUE_MAX_MULT, baseValue = 1.0))
	val maxManaBaseStat by nestedProperty(BuffableStat(Stats.MANA_MAX_BASE, baseValue = 300.0))
	val maxManaPerLevelStat by nestedProperty(BuffableStat(Stats.MANA_MAX_PERLEVEL, baseValue = 10.0))
	val maxManaMultStat by nestedProperty(BuffableStat(Stats.MANA_MAX_MULT, baseValue = 1.0))
	val maxSfBaseStat by nestedProperty(BuffableStat(Stats.SF_MAX_BASE, baseValue = 50.0))
	val maxSfPerLevelStat by nestedProperty(BuffableStat(Stats.SF_MAX_PERLEVEL, baseValue = 5.0))
	val maxSfMultStat by nestedProperty(BuffableStat(Stats.SF_MAX_MULT, baseValue = 1.0))

	val dodgeStat by nestedProperty(BuffableStat(Stats.DODGE_ANY, baseValue = 0.0))

	val meleeAimStat by nestedProperty(BuffableStat(Stats.AIM_MELEE, baseValue = 0.64))
	val meleeDodgeStat by nestedProperty(BuffableStat(Stats.DODGE_MELEE, baseValue = 0.0))
	val meleeDamageStat by nestedProperty(BuffableStat(Stats.DAMAGE_MELEE, baseValue = 0.0))

	val rangedDamageMultStat by nestedProperty(BuffableStat(Stats.DAMAGE_RANGED_MULT, baseValue = 1.0))

	val resistPhysStat by nestedProperty(BuffableStat(Stats.RESIST_PHYS, baseValue = 0.0))
	val resistMagStat by nestedProperty(BuffableStat(Stats.RESIST_MAG, baseValue = 0.0))
	val resistLustStat by nestedProperty(BuffableStat(Stats.RESIST_LUST, baseValue = 0.0))

	val spellPowerStat by nestedProperty(BuffableStat(Stats.SPELL_POWER, baseValue = 1.0))

	val soulskillPowerStat by nestedProperty(BuffableStat(Stats.SOULSKILL_POWER, baseValue = 1.0))
	val soulskillCostStat by nestedProperty(BuffableStat(Stats.SOULSKILL_COST, baseValue = 1.0))

	//Level Stats
	var xp: Int by property(0)
	var level: Int by property(0)
	var gems: Int by property(0)

	val perks by nestedProperty(PerkManager(this as Creature))
	val statusEffects by nestedList(StatusEffect.Serializer(this as Creature))

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

	var beardStyle by property(BeardStyle.NORMAL)
	var beardLength by property(0)

	val hair by nestedProperty(HairPart(this as Creature))
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

	var armor: ArmorItem? by nestedProperty(null, ItemSerializers.ArmorSerializer)

	init {
		for (descriptor in propertyDescriptors) {
			val stat = descriptor.fieldValue as? IStat ?: continue
			statStore.registerStat(stat)
		}
	}
}
