package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup

/*
 * Created by aimozg on 28.11.2021.
 */
enum class HairStyle(
	override val cocID: Int,
	val adjective: String
): CocId {
	PLAIN(
		0,
		adjective = ""
	);

	companion object: CocIdLookup<HairStyle>(values())
}
