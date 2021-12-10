package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

class AntennaePart(val host: Creature): JsonSerializable() {
	var type: AntennaeType by property(AntennaeType.NONE)
}
