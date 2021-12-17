package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

class WingsPart(val creature: Creature): JsonSerializable() {
	var type by property(WingType.NONE)
	var desc by property("")

	fun appearanceDescription(): String = type.appearanceDescription(creature)

}
