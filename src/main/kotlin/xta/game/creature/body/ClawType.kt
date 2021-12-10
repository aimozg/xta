package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup

enum class ClawType(override val cocID: Int) : CocId {
	NORMAL(0),
	LIZARD(1),
	DRAGON(2),
	SALAMANDER(3),
	CAT(4),
	DOG(5),
	RAPTOR(6),
	MANTIS(7),
	IMP(8),
	COCKATRICE(9),
	RED_PANDA(10),
	;

	companion object : CocIdLookup<ClawType>(values())
}
