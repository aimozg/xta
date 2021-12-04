package xta.game.creature.body

/*
 * Created by aimozg on 28.11.2021.
 */
enum class HairStyle(
	val cocID: Int,
	val adjective: String
) {
	PLAIN(
		0,
		adjective = ""
	);

	companion object {
		fun byId(id:Int) = values().find { it.cocID == id }
	}
}
