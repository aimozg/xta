package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

@JsExport
class FacePart(val host: Creature): JsonSerializable() {
	var type: FaceType by property(FaceType.HUMAN)
}
