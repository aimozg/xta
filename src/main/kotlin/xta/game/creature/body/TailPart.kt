package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

class TailPart(val creature: Creature): JsonSerializable() {
	var type by property(TailType.NONE)
	var count by property(0)
	var venom by property(0)
	var recharge by property(0)

	fun appearanceDescription(): String = type.appearanceDesc(creature)
}
