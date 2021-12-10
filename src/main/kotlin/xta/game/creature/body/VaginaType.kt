package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup

enum class VaginaType(
	override val cocID:Int
): CocId {
	HUMAN(0),
	EQUINE(1),
	BLACK_SAND_TRAP(5),
	CAVE_WYRM(6),
	VENOM_DRIPPING(7),
	MANTICORE(8),
	CANCER(9),
	DEMONIC(10),
	ALRAUNE(11),
	SCYLLA(12),
	NAGA(13),
	MINDBREAKER(14),
	;

	companion object: CocIdLookup<VaginaType>(values())
}
