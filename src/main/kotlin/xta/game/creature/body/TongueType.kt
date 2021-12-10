package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup

enum class TongueType(override val cocID: Int): CocId {
	HUMAN(0),
	SNAKE(1),
	DEMONIC(2),
	DRACONIC(3),
	ECHIDNA(4),
	CAT(5),
	ELF(6),
	DOG(7),
	CAVE_WYRM(8),
	GHOST(9),
	MELKIE(10),
	RATATOSKR(11),
	RAVENOUS_TONGUE(12),
	MINDBREAKER(13),
	;
	companion object: CocIdLookup<TongueType>(values())
}
