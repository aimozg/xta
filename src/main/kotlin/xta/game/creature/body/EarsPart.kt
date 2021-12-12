package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

class EarsPart(val creature: Creature): JsonSerializable() {
	var type: EarType by property(EarType.HUMAN)

	fun appearanceDescription():String =
		type.appearanceDescription(creature)
}

