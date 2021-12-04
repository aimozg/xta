package xta.game.creature.body

enum class SkinBaseType(
	val cocID: Int,
	val displayName:String
) {
	PLAIN(0, "skin"),
	GOO(3, "skin"),
	STONE(7, "stone"),
	AQUA_RUBER_LIKE(19, "slippery rubber-like skin"),
	TRANSPARENT(22,"transparent");

	companion object {
		fun byId(id: Int) = values().find { it.cocID == id }
	}
}
