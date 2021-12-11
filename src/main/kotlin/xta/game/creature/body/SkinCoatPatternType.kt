package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup

enum class SkinCoatPatternType(
	override val cocID: Int,
	private val appearanceDesc: String = ""
): CocId {
	NONE(0),
	BEE_STRIPES(3, "[You] have [skin color] [skin] covered by a bee-like [skin color2] stripe pattern."),
	TIGER_STRIPES(4, "[You] have [skin color] [skin] covered by a tiger-like [skin color2] stripe pattern."),
	SPOTTED(6, "[You] have many [skin color2] spots around [your] [skin color] fur."),
	RED_PANDA_UNDERBODY(8, "[You] have an underbelly colored [skin color2]."),
	;

	open fun appearanceDescription(): String = appearanceDesc

	companion object: CocIdLookup<SkinCoatPatternType>(values())
}
