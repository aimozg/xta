package xta.game.creature.body

enum class EyeType(
	val cocID: Int,
	val displayName: String
) {
	HUMAN(0, "human");

	companion object {
		fun byId(id:Int) = values().find { it.cocID == id }
	}
}
