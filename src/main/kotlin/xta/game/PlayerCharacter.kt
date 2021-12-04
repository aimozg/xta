package xta.game

/*
 * Created by aimozg on 28.11.2021.
 */
class PlayerCharacter: Creature() {
	val chatName get() = "[$level] $name"

	override fun race(): String {
		return "human"
	}

	var startingRace: String = "human"


}

