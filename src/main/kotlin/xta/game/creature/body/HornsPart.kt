package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

class HornsPart(val creature: Creature): JsonSerializable() {
	var type: HornType by property(HornType.NONE)
	var count: Int by property(0)

	fun appearanceDescription():String =
		type.appearanceDescription(creature)
}
