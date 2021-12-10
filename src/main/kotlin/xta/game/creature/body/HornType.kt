package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup

enum class HornType(override val cocID: Int): CocId {
	NONE(0),
	DEMON(1),
	COW_MINOTAUR(2),
	DRACONIC_X2(3),
	DRACONIC_X4_12_INCH_LONG(4),
	ANTLERS(5),
	GOAT(6),
	UNICORN(7),
	RHINO(8),
	OAK(9),
	GARGOYLE(10),
	ORCHID(11),
	ONI_X2(12),
	ONI(13),
	BICORN(14),
	GHOSTLY_WISPS(15),
	SPELL_TAG(16),
	GOATQUAD(17),
	KRAKEN(18),
	FROSTWYRM(19),
	USHI_ONI(20),
	SEA_DRAGON(21),
	;
	companion object: CocIdLookup<HornType>(values())
}
