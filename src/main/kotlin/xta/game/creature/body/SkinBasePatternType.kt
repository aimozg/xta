package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup

enum class SkinBasePatternType(
	override val cocID: Int,
	private val appearanceDesc: String = ""
): CocId {
	NONE(0),
	MAGICAL_TATTOO(1, "[Your] body is covered with runic tattoos."),
	ORCA_UNDERBODY(2, "A [skin color2] underbelly runs on the underside of [your] limbs bearing a glossy shine, similar to that of an orca."),
	BATTLE_TATTOO(5, "[Your] body is covered with battle tattoos."),
	LIGHTNING_SHAPED_TATTOO(7, "[Your] body is covered with glowing lightning tattoos."),
	SCAR_SHAPED_TATTOO(9, "[Your] body is covered with scar-shaped tattoos."),
	WHITE_BLACK_VEINS(10, "Many [skin color2] veins are clearly visible on [your] [skin full.noadj] body."),
	VENOMOUS_MARKINGS(11, "[Your] skin is covered in intricate purple designs which pump venom alongside their paths."),
	USHI_ONI_TATOO(12, "[You] have strange ushi-oni tattoos on your belly, chest, breasts, shoulders and even face; some are like a black sheen plate, while others are just fur."),
	SCAR_WINDSWEPT(13, "[Your] body is covered with scars as if your skin was cut in various place by a windstorm"),
	OIL(14, "[Your] body is dripping with oily black fluids."),
	SEA_DRAGON_UNDERBODY(15, "An underbelly colored [skin color2] runs on the underside of [your] limbs bearing a glossy shine, on top of being lined up with bioluminescent dots like those of a deep sea fish."),
	;

	open fun appearanceDescription(): String = appearanceDesc

	companion object: CocIdLookup<SkinBasePatternType>(values())
}
