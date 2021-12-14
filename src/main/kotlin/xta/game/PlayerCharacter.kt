package xta.game

import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.perks.PerkLib
import xta.game.creature.races.HumanRace
import xta.game.stats.Stats

/*
 * Created by aimozg on 28.11.2021.
 */
class PlayerCharacter: Creature() {
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

	var startingRace: String = "human"

	fun updateStats() {
		Race.clearRacialBonuses(this)
		val races = racialScores()
		for ((_, race) in races) {
			race.applyBonuses(this)
		}
		if (hasPerk(PerkLib.Agility)) {
			// TODO make proper dynamic buff: defense%+ spe/10 if wearing light armor, spe/25 if medium
			statStore.addOrReplaceBuff(
				Stats.RESIST_PHYS,
				PerkLib.Agility.buffTag,
				0.001*spe,
				"Perk: "+PerkLib.Agility.name,
				save = false
			)
		}
	}
}

