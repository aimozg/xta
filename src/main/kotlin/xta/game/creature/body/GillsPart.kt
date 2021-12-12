package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

class GillsPart(val creature: Creature): JsonSerializable() {
	var type: GillType by property(GillType.NONE)

	fun appearanceDescription() = type.appearanceDescription(creature)
}
