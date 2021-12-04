package xta.game.creature.body

enum class SkinCoatType(
	val cocID:Int,
	val displayName: String
) {
	FUR(1, "fur"),
	SCALES(2, "scales"),
	CHITIN(5, "chitin"),
	BARK(6, "bark"),
	AQUA_SCALES(9, "scales"),
	DRAGON_SCALES(14, "dragon scales"),
	MOSS(15, "moss"),
	FEATHER(21,"feather");

	companion object {
		fun byId(id: Int) = SkinCoatType.values().find { it.cocID == id }
	}
}
