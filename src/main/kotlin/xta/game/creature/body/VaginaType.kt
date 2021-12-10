package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup

enum class VaginaType(
	override val cocID:Int
): CocId {
	HUMAN(0);

	companion object: CocIdLookup<VaginaType>(values())
}
