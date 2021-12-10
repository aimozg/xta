package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup

enum class PenisType(
	override val cocID: Int,
	val group:String
): CocId {
	HUMAN(0, "human"),
	HORSE(1, "mammal"),
	DOG(2, "mammal"),
	DEMON(3, "super"),
	TENTACLE(4, "tentacle"),
	SCYLLATENTACLE(5, "tentacle"),
	CAT(6, "mammal"),
	CANCER(7, "foaming human"),
	LIZARD(8, "reptile"),
	CAVE_WYRM(9, "reptile"),
	ANEMONE(10, "seaworld"),
	KANGAROO(11, "mammal"),
	DRAGON(12, "reptile"),
	DISPLACER(13, "other"),
	FOX(15, "mammal"),
	BEE(16, "insect"),
	PIG(17, "mammal"),
	AVIAN(18, "avian"),
	RHINO(19, "mammal"),
	ECHIDNA(20, "mammal"),
	WOLF(21, "mammal"),
	STAMEN(22, "plant"),
	RED_PANDA(23, "mammal"),
	GRYPHON(24, "avian"),
	OOMUKADE(25, "venomous"),
	MINDBREAKER(26, "eldritch"),
	USHI_ONI(27, "other"),
	// UNDEFINED(28, ""),
	;
	companion object: CocIdLookup<PenisType>(values())
}
