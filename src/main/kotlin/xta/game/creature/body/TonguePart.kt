package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

class TonguePart(val host: Creature): JsonSerializable() {
	var type: TongueType by property(TongueType.HUMAN)
}
