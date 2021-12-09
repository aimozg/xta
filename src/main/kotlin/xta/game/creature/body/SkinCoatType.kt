package xta.game.creature.body

enum class SkinCoatType(
	val cocID: Int,
	val displayName: String,
	val plural: Boolean
) {
	FUR(1, "fur", plural = false),
	SCALES(2, "scales", plural = true),
	CHITIN(5, "chitin", plural = false),
	BARK(6, "bark", plural = false),
	AQUA_SCALES(9, "scales", plural = true),
	DRAGON_SCALES(14, "dragon scales", plural = true),
	MOSS(15, "moss", plural = false),
	FEATHER(21, "feather", plural = false);

	companion object {
		fun byId(id: Int) = SkinCoatType.values().find { it.cocID == id }
	}
}
