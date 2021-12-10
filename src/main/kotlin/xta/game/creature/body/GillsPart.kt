package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

class GillsPart(val host: Creature): JsonSerializable() {
	var type: GillType by property(GillType.NONE)
}
