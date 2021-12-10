package xta.game

import xta.game.creature.Race
import xta.game.creature.races.HumanRace

/*
 * Created by aimozg on 28.11.2021.
 */
class PlayerCharacter: Creature() {
	val chatName get() = "[$level] $name"

	fun raceAndScore():Pair<Race,Int> {
		val data = Race.ALL_RACES.map {
			it to it.score(this)
		}
		val topRace = data.filter {
			it.first != HumanRace && it.second >= it.first.minScore
		}.maxByOrNull { it.second }
		return topRace ?: (HumanRace to HumanRace.score(this))
	}

	override fun raceName(): String {
		val (race,score) = raceAndScore()
		return race.nameOf(this, score)
	}
	override fun raceFullName(): String {
		val (race,score) = raceAndScore()
		return race.fullNameOf(this, score)
	}

	var startingRace: String = "human"


}

