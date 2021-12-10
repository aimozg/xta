package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup

enum class GillType(override val cocID: Int): CocId {
	NONE(0),
	ANEMONE(1),
	FISH(2),
	GILLS_IN_TENTACLE_LEGS(3),
	;
	companion object: CocIdLookup<GillType>(values())
}
