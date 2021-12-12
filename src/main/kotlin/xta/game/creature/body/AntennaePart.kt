package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

class AntennaePart(val creature: Creature): JsonSerializable() {
	var type: AntennaeType by property(AntennaeType.NONE)

	fun appearanceDescription() = type.appearanceDescription(creature)
}
