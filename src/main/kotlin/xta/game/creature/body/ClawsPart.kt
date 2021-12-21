package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

class ClawsPart(val host: Creature): JsonSerializable() {
	var type by property(ClawType.NORMAL)
	var color by property("")
}
