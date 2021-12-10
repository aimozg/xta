package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup

enum class RearBodyType(override val cocID: Int): CocId {
	NONE(0),
	DRACONIC_MANE(1),
	DRACONIC_SPIKES(2),
	FENRIR_ICE_SPIKES(3),
	LION_MANE(4),
	BEHEMOTH(5),
	SHARK_FIN(6),
	ORCA_BLOWHOLE(7),
	RAIJU_MANE(8),
	BAT_COLLAR(9),
	WOLF_COLLAR(10),
	DISPLACER_TENTACLES(11),
	SNAIL_SHELL(12),
	METAMORPHIC_GOO(13),
	GHOSTLY_AURA(15),
	YETI_FUR(16),
	GLACIAL_AURA(17),
	CENTIPEDE(18),
	KRAKEN(19),
	FROSTWYRM(20),
	FUR_COAT(21),
	TENTACLE_EYESTALKS(22),
	ATLACH_NACHA(23),
	MINDBREAKER(24),
	;
	companion object: CocIdLookup<RearBodyType>(values())
}
