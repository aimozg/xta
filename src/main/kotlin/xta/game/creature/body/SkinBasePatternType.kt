package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup

enum class SkinBasePatternType(
	override val cocID: Int
): CocId {
	NONE(0),
	MAGICAL_TATTOO(1),
	ORCA_UNDERBODY(2),
	BATTLE_TATTOO(5),
	LIGHTNING_SHAPED_TATTOO(7),
	SCAR_SHAPED_TATTOO(9),
	WHITE_BLACK_VEINS(10),
	VENOMOUS_MARKINGS(11),
	USHI_ONI_TATOO(12),
	SCAR_WINDSWEPT(13),
	OIL(14),
	SEA_DRAGON_UNDERBODY(15),
	;
	companion object: CocIdLookup<SkinBasePatternType>(values())
}
