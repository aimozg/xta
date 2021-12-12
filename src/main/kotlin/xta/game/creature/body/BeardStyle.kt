package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup

enum class BeardStyle(
	override val cocID: Int,
	val noun:String
): CocId {
	NORMAL(0, "beard"),
	GOATEE(1, "goatee"),
	CLEANCUT(2, "clean-cut beard"),
	MOUNTAINMAN(3, "mountain-man beard"),
	;
	companion object: CocIdLookup<BeardStyle>(values())
}
