package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

class WingsPart(val host: Creature): JsonSerializable() {
	var type by property(WingType.NONE)
}
