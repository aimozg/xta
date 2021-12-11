package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

/*
 * Created by aimozg on 12.12.2021.
 */
class HairPart(val creature: Creature): JsonSerializable() {
	var type by property(HairType.NORMAL)
	var style by property(HairStyle.PLAIN)
	var color by property("no")
	var color2 by property("")
	var length by property(0)

	fun appearanceDescription() = when {
		creature.hairLength > 0 ->
			type.appearanceDescription(creature)
		creature.hasCoatOfType(SkinCoatType.FUR) ->
			"[You] [have] no hair, only a thin layer of fur atop of [your] head."
		else ->
			"[You] [are] completely bald, displaying [skin color] [skin type] where [your] hair would otherwise be."
	}

}
