package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

class LowerBodyPart(val host: Creature): JsonSerializable() {
	var type by property(LowerBodyType.HUMAN)
}
