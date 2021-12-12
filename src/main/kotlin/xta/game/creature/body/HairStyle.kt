package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup

/*
 * Created by aimozg on 28.11.2021.
 */
enum class HairStyle(
	override val cocID: Int,
	val adjective: String
) : CocId {
	PLAIN(0, ""),
	WILD(1, "wild"),
	PONYTAIL(2, "ponytailed"),
	LONGTRESSES(3, "low-ponytailed"),
	TWINTAILS(4, "twintailed"),
	DWARVEN(5, "Dwarven"),
	SNOWLILY(6, "snowlily"),
	FOURWIND(7, "fourwind"),
	FOURWINDL(8, "long fourwind")
	;

	companion object : CocIdLookup<HairStyle>(values())
}
