package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

class TonguePart(val creature: Creature): JsonSerializable() {
	var type: TongueType by property(TongueType.HUMAN)

	fun appearanceDescription() = type.appearanceDescription(creature)
}
