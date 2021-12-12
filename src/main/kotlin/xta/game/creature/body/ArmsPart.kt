package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

class ArmsPart(val creature: Creature): JsonSerializable() {
	var type by property(ArmType.HUMAN)

	fun appearanceDescription():String = type.appearanceDescription(creature)
}
