package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

class ArmsPart(val host: Creature): JsonSerializable() {
	var type by property(ArmType.HUMAN)
}
