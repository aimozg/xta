package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup

enum class TailType(override val cocID: Int): CocId {
	NONE(0),
	HORSE(1),
	DOG(2),
	DEMONIC(3),
	COW(4),
	SPIDER_ADBOMEN(5),
	BEE_ABDOMEN(6),
	SHARK(7),
	CAT(8),
	LIZARD(9),
	RABBIT(10),
	HARPY(11),
	KANGAROO(12),
	FOX(13),
	DRACONIC(14),
	RACCOON(15),
	MOUSE(16),
	FERRET(17),
	BEHEMOTH(18),
	PIG(19),
	SCORPION(20),
	GOAT(21),
	RHINO(22),
	ECHIDNA(23),
	DEER(24),
	SALAMANDER(25),
	KITSHOO(26),
	MANTIS_ABDOMEN(27),
	MANTICORE_PUSSYTAIL(28),
	WOLF(29),
	GARGOYLE(30),
	ORCA(31),
	YGGDRASIL(32),
	RAIJU(33),
	RED_PANDA(34),
	GARGOYLE_2(35),
	AVIAN(36),
	GRIFFIN(37),
	LION(38),
	BURNING(39),
	NEKOMATA_FORKED_1_3(40),
	NEKOMATA_FORKED_2_3(41),
	CAVE_WYRM(42),
	HINEZUMI(43),
	THUNDERBIRD(44),
	BEAR(45),
	TWINKASHA(46),
	USHI_ONI(47),
	WEASEL(48),
	SQUIRREL(49),
	MONKEY(50),
	WENDIGO(51),
	;
	companion object: CocIdLookup<TailType>(values())
}
