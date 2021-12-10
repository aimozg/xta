package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup

enum class AntennaeType(override val cocID: Int): CocId {
	NONE(0),
	MANTIS(1),
	BEE(2),
	COCKATRICE(3),
	FIRE_SNAIL(4),
	MOTH(5),
	CENTIPEDE(6),
	SEA_DRAGON(7),
	;
	companion object: CocIdLookup<AntennaeType>(values())
}
