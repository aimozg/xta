package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

class RearBodyPart(val creature: Creature): JsonSerializable() {
	var type by property(RearBodyType.NONE)

	fun appearanceDescription(): String = type.appearanceDescription(creature)
}
