package xta.game

import xta.Player
import xta.game.creature.PcKnowledge
import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.perks.PerkLib
import xta.game.creature.races.HumanRace
import xta.game.items.ArmorItem
import xta.game.items.ArmorType
import xta.game.stats.Stats

/*
 * Created by aimozg on 28.11.2021.
 */
@JsExport
class PlayerCharacter: Creature() {

	var player: Player? = null
	var startingRace by property("human")
	val knowledge by nestedProperty(PcKnowledge())

	////////////////////////////////////////

	val chatName get() = "[$level] $name"

	fun racialScores():List<Pair<Int,RacialStage>> {
		return Race.ALL_RACES.mapNotNull {
			it.scoreAndStageOrNull(this)
		}
	}
	fun topRaceAndScore():Pair<Int,RacialStage> {
		val data = racialScores()
		val topRace = data.filter {
			it.second.race != HumanRace
		}.maxByOrNull { it.first }
		return topRace ?: (HumanRace.score(this) to HumanRace.STAGE_MAIN)
	}

	fun topRace():RacialStage {
		return topRaceAndScore().second
	}
	override fun raceName(): String {
		return topRace().nameOf(this)
	}
	override fun raceFullName(): String {
		return topRace().fullNameOf(this)
	}

	fun knows(thing:String) = thing in knowledge

	fun updateStats() {
		Race.clearRacialBonuses(this)
		val races = racialScores()
		for ((_, race) in races) {
			race.applyBonuses(this)
		}
		if (hasPerk(PerkLib.Agility)) {
			val buffValue =
				when (armor?.type) {
					null, ArmorType.LIGHT -> spe/10.0
					ArmorType.MEDIUM -> spe/25.0
					else -> 0.0
				}
			statStore.addOrReplaceBuff(
				Stats.RESIST_PHYS,
				PerkLib.Agility.buffTag,
				0.01*buffValue,
				"(Perk) "+PerkLib.Agility.name,
				save = false
			)
		}
	}
	fun resetStats() {
		clearCombatStatuses()
		hp = maxHp()
		lust = minLust()
		wrath = 0.0
		fatigue = 0.0
		mana = maxMana()
		soulforce = maxSoulforce()
	}
	fun equipArmor(armorItem: ArmorItem) {
		unequipArmor()
		armor = armorItem
		armorItem.equipped(this)
	}
	fun unequipArmor(): ArmorItem? {
		val old = armor
		armor = null
		old?.unequipped(this)
		return old
	}

	override fun deserializeFromJson(input: dynamic) {
		super.deserializeFromJson(input)
		armor?.loaded(this)
		meleeWeapon?.loaded(this)
		// TODO other items
		updateStats()
	}
}

