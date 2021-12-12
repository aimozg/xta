package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

class EyePart(val creature: Creature): JsonSerializable() {
	var irisColor: String by property("brown")
	var type: EyeType by property(EyeType.HUMAN)

	fun appearanceDescription():String =
		type.appearanceDescription(creature)
}

