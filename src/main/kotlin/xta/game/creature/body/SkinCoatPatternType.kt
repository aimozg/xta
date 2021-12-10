package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup

enum class SkinCoatPatternType(
	override val cocID: Int
): CocId {
	NONE(0),
	BEE_STRIPES(3),
	TIGER_STRIPES(4),
	SPOTTED(6),
	RED_PANDA_UNDERBODY(8),
	;
	companion object: CocIdLookup<SkinCoatPatternType>(values())
}
