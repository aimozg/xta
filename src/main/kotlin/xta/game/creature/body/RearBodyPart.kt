package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

class RearBodyPart(val host: Creature): JsonSerializable() {
	var type by property(RearBodyType.NONE)

	fun appearanceDescription(): String = "" // TODO
}
