package xta.game.stats

enum class BuffRate(val cocID:Int) {
	PERMANENT(0),
	ROUNDS(1),
	HOURS(2),
	DAYS(3);

	companion object {
		fun byID(id:Int) = values().find { it.cocID == id }
	}
}
