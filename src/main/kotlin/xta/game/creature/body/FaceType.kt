package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup

enum class FaceType(
	override val cocID:Int
):CocId {
	HUMAN(0),
	HORSE(1),
	DOG(2),
	COW_MINOTAUR(3),
	SHARK_TEETH(4),
	SNAKE_FANGS(5),
	CAT(6),
	LIZARD(7),
	BUNNY(8),
	KANGAROO(9),
	SPIDER_FANGS(10),
	FOX(11),
	DRAGON(12),
	RACCOON_MASK(13),
	RACCOON(14),
	BUCKTEETH(15),
	MOUSE(16),
	FERRET_MASK(17),
	FERRET(18),
	PIG(19),
	BOAR(20),
	RHINO(21),
	ECHIDNA(22),
	DEER(23),
	WOLF(24),
	MANTICORE(25),
	SALAMANDER_FANGS(26),
	YETI_FANGS(27),
	ORCA(28),
	PLANT_DRAGON(29),
	DRAGON_FANGS(30),
	DEVIL_FANGS(31),
	ONI_TEETH(32),
	WEASEL(33),
	VAMPIRE(34),
	BUCKTOOTH(35),
	JABBERWOCKY(36),
	RED_PANDA(37),
	CAT_CANINES(38),
	CHESHIRE(39),
	CHESHIRE_SMILE(40),
	AVIAN(41),
	WOLF_FANGS(42),
	ORC_FANGS(43),
	ANIMAL_TEETH(44),
	BEAR(45),
	PANDA(46),
	FIRE_SNAIL(47),
	GHOST(48),
	JIANGSHI(49),
	YUKI_ONNA(50),
	KUDERE(51),
	USHI_ONI(52),
	FAIRY(53),
	CRAZY(54),
	SMUG(55),
	SQUIRREL(56),
	ELF(57);

	companion object: CocIdLookup<FaceType>(values())
}
