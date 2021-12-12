package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup
import xta.game.Creature

enum class GillType(
	override val cocID: Int,
	val displayName: String,
	private val appearanceDesc: String = ""
) : CocId {
	NONE(0, "non-existant"),
	ANEMONE(
		1, "anemone",
		appearanceDesc = "A pair of feathery gills are growing out just below your neck, spreading out horizontally and draping down your chest. They allow you to stay in the water for quite a long time."
	),
	FISH(
		2, "fish",
		appearanceDesc = "A set of fish like gills reside on your neck, several small slits that can close flat against your skin. They allow you to stay in the water for quite a long time."
	),
	GILLS_IN_TENTACLE_LEGS(3, "placed in tentacle legs"),
	;

	open fun appearanceDescription(creature: Creature) = appearanceDesc

	companion object : CocIdLookup<GillType>(values())
}
