package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup

enum class EarType(
	override val cocID: Int
): CocId {
	HUMAN(0),
	HORSE(1),
	DOG(2),
	COW(3),
	ELFIN(4),
	CAT(5),
	LIZARD(6),
	BUNNY(7),
	KANGAROO(8),
	FOX(9),
	DRAGON(10),
	RACCOON(11),
	MOUSE(12),
	FERRET(13),
	PIG(14),
	RHINO(15),
	ECHIDNA(16),
	DEER(17),
	WOLF(18),
	LION(19),
	YETI(20),
	ORCA(21),
	ORCA2(22),
	SNAKE(23),
	GOAT(24),
	ONI(25),
	ELVEN(26),
	RAIJU(27),
	BAT(28),
	VAMPIRE(29),
	RED_PANDA(30),
	AVIAN(31),
	GRYPHON(32),
	CAVE_WYRM(33),
	BEAR(34),
	PANDA(35),
	SHARK(36),
	DISPLACER(37),
	MELKIE(38),
	GREMLIN(39),
	WEASEL(40),
	SQUIRREL(41);

	companion object: CocIdLookup<EarType>(values())
}
