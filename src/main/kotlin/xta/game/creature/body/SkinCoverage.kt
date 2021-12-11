package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup

enum class SkinCoverage(
	override val cocID:Int
): CocId {
	NONE(0),
	LOW(1),
	MEDIUM(2),
	HIGH(3),
	COMPLETE(4);

	companion object: CocIdLookup<SkinCoverage>(values())
}
