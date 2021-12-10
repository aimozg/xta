package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup

enum class SkinBaseType(
	override val cocID: Int,
	val displayName: String,
	val plural: Boolean,
	private val appearanceDesc: String = ""
): CocId {
	PLAIN(
		0, "skin", plural = false,
		"[Your] [skin full.noadj] has a completely normal texture, at least for [your] original world."
	),
	GOO(
		3, "skin", plural = false,
		"[Your] [skin full.noadj] is {partiallyOrCompletely} made of [skin coat]."
	),
	STONE(
		7, "stone", plural = false,
		"[Your] [skin full.noadj] is completely made of [gargoylematerial]."
	),
	AQUA_RUBBER_LIKE(
		19, "slippery rubber-like skin", plural = false,
		"[Your] [skin full.noadj] has a rubber-like texture."
	),
	TRANSPARENT(
		22, "transparent", plural = false,
		"[Your] [skin full.noadj] is completely transparent, like a ghost's."
	);

	open fun appearanceDescription(): String = appearanceDesc

	companion object: CocIdLookup<SkinBaseType>(values())
}

