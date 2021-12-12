package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

class LowerBodyPart(val creature: Creature): JsonSerializable() {
	var type by property(LowerBodyType.HUMAN)
	var legCount by property(2)

	val isBiped get() = legCount == 2
	val isNaga get() = type.isNaga
	val isTaur get() = legCount == 4 && type.canTaur

	fun appearanceDescription() = type.appearanceDescription(creature)
}
