package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

class TailPart(val host: Creature): JsonSerializable() {
	var type by property(TailType.NONE)
	var count by property(0)
}
