package xta.game.creature.body

enum class SkinBaseType(
	val cocID: Int,
	val displayName: String,
	val plural: Boolean,
	private val appearanceDesc: String = ""
) {
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

	companion object {
		fun byId(id: Int) = values().find { it.cocID == id }
	}
}
