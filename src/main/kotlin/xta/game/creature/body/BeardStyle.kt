package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup

enum class BeardStyle(override val cocID: Int): CocId {
	NORMAL(0),
	GOATEE(1),
	CLEANCUT(2),
	MOUNTAINMAN(3),
	;
	companion object: CocIdLookup<BeardStyle>(values())
}
