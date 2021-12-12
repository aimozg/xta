package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup
import xta.game.Creature

enum class ArmType(
	override val cocID: Int,
	val displayName:String,
	private val appearanceDesc:String = "",
	val hasScythe:Boolean = false,
	val hasClaws:Boolean = false,
	val canSlam:Boolean = false,
	val canPounce:Boolean = false
): CocId {
	HUMAN(0, "human",
		appearanceDesc = "[You] [have] a pair of normal human arms without any unusual traits."
	),
	HARPY(1, "harpy",
		appearanceDesc = "Feathers hang off [your] arms from shoulder to wrist."
	),
	SPIDER(2, "spider",
		appearanceDesc = "A shining [skin coat.color] exoskeleton covers [your] arms from the biceps down, resembling a pair of long [skin coat.color] gloves from a distance."
	),
	MANTIS(3, "mantis",
		appearanceDesc = "A shining [skin coat.color] exoskeleton covers [your] arms from the biceps down, with long and sharp scythes extending from the wrists.",
		hasScythe = true
	),
	BEE(4, "bee",
		appearanceDesc = "A shining black exoskeleton covers [your] arms from the biceps down, resembling a pair of long black gloves ending with a yellow fuzz from a distance."
	),
	SALAMANDER(5, "salamander",
		appearanceDesc = "Thick, leathery red scales cover [your] arms from the biceps down, and [your] fingernails are short curved claws.",
		hasClaws = true
	),
	PHOENIX(6, "phoenix",
		appearanceDesc = "Crimson feathers hang off [your] arms from shoulder to wrist."
	),
	PLANT(7, "plant",
		appearanceDesc = "Delicate vines covered by spade-like leaves crawl down from [your] shoulders to [your] wrists, bobbing whenever [you] [verb move]."
	),
	SHARK(8, "shark",
		appearanceDesc = "A pair of shark fins have sprouted on each of [your] forearms near [your] elbows, and the skin between [your] fingers forms a small webbing which assists [your] swimming."
	),
	GARGOYLE(9, "gargoyle",
		appearanceDesc = "[Your] [gargoylematerial] arms end in stone sharp clawed hands.",
		canSlam = true
	),
	WOLF(10, "wolf",
		appearanceDesc = "[Your] arms are covered by thick fur, ending in clawed hands with paw pads.",
		hasClaws = true,
		canPounce = true
	),
	LION(11, "lion",
		appearanceDesc = "[Your] arms are covered by [skin coat.color] fur up to [your] shoulders, where it turns white, and end in a pair of five-toed lion paws armed with lethal claws.",
		hasClaws = true,
		canPounce = true
	),
	KITSUNE(12, "kitsune",
		appearanceDesc = "[Your] arms are mostly human, save for [your] sharp nails.",
		hasClaws = true
	),
	FOX(13, "fox",
		appearanceDesc = "[Your] arms are covered by thick fur, ending in clawed hands with paw pads.",
		hasClaws = true,
		canPounce = true
	),
	LIZARD(14, "lizard",
		appearanceDesc = "Thick, leathery scales cover [your] arms from the biceps down, and [your] fingernails are short curved claws.",
		hasClaws = true,
	),
	DRACONIC(15, "dragon",
		appearanceDesc = "Thick, leathery scales cover [your] arms from the biceps down, and [your] fingernails are short curved claws.",
		hasClaws = true,
	),
	YETI(16, "yeti",
		appearanceDesc = "[Your] two arms covered by thick fur end in large and powerful yeti hands. [You] can use them to smash or punch things when [youre] angry.",
		canSlam = true
	),
	ORCA(17, "orca",
		appearanceDesc = "A pair of orca fins have sprouted on each of [your] forearms near [your] elbows, and the skin between [your] fingers forms a small webbing which assists [your] swimming."
	),
	PLANT2(18, "tentacle-covered",
		appearanceDesc = "Vines crawl down from [your] shoulders to your wrists, tipped with slits that drool precum. They look like innocent decorations from a distance."
	),
	DEVIL(19, "devil",
		appearanceDesc = "[Your] forearms are covered by fur and end in four-fingered paw-like hands armed with claws. Despite their weird shape, [you] [have] more than enough dexterity to draw even the most complex magical signs when spellcasting.",
		hasClaws = true,
	),
	ONI(20, "oni",
		appearanceDesc = "[Your] arms are mostly human besides sharp black nails, and covered by war tattoos.",
		canSlam = true
	),
	ELF(21, "elf",
		appearanceDesc = "[Your] delicate elven hands are almost supernaturally dexterous, allowing [you] to manipulate objects or cast spells with unbelievable ease."
	),
	RAIJU(22, "raiju",
		appearanceDesc = "[Your] arms are practically human, save for the sharp white claws [you] [have] in place of normal nails.",
		hasClaws = true,
	),
	RED_PANDA(23, "red-panda",
		appearanceDesc = "Soft, black-brown fluff covers [your] arms. [Your] paws have cute, pink paw pads and short claws.",
		hasClaws = true,
	),
	GARGOYLE_2(24, "gargoyle",
		appearanceDesc = "[Your] [gargoylematerial] arms end in normal human like hands.",
		hasClaws = true,
	),
	CAT(25, "cat",
		appearanceDesc = "[Your] arms are covered by [skin coat.color] fur up to your shoulder. They end in a pair of five-toed cat paws armed with lethal claws.",
		hasClaws = true,
		canPounce = true
	),
	AVIAN(26, "avian",
		appearanceDesc = "[Your] arms are covered by [skin coat.color] colored feathers just a bit past [your] elbow. [Your] humanoid hands have [skinTone], slightly rough skin, and end in short claws.",
		hasClaws = true,
	),
	GRYPHON(27, "gryphon",
		appearanceDesc = "The feathers on [your] arms reach a bit past your elbows, with a fringe of [skin coat.color] plumage leading to [your] [skinTone], slightly rough-skinned hands equipped with short, avian claws.",
		hasClaws = true,
	),
	SPHINX(28, "sphinx",
		appearanceDesc = "[Your] arms are covered by [skin coat.color] fur. They end in somewhat human-like hands armed with lethal claws.",
		hasClaws = true,
	),
	PIG(29, "pig"),
	BOAR(30, "boar",
		appearanceDesc = "[Your] arms are covered by thick [skin coat.color] fur, ending in hands with sharp black nails."
	),
	ORC(31, "orc",
		appearanceDesc = "Although [your] nails are rather sharp, [your] arms covered in scar-like tattoos look mostly human, aside from being a bit longer than usual."
	),
	DISPLACER(32, "displacer",
		appearanceDesc = "Where a normal creature would have only two arms, [you] instead [have] four [skin coat.color] furred appendages, all of which end in a pair of five-toed lion paws armed with lethal claws.",
		hasClaws = true,
		canPounce = true
	),
	CAVE_WYRM(33, "cave wyrm",
		appearanceDesc = "Thick, leathery black scales cover [your] arms from the biceps down, and [your] fingernails are short curved claws.",
		hasClaws = true
	),
	HINEZUMI(34, "hinezumi",
		appearanceDesc = "[Your] forearms, while human in shape, are covered by a thick coat of flames that burn depending on [your] mood.",
		canSlam = true
	),
	BEAR(35, "bear",
		appearanceDesc = "[You] have [skin coat.color] furry arms, ending in a pair of powerful five-toed bear paws armed with lethal claws.",
		hasClaws = true
	),
	GOO(36, "goo",
		appearanceDesc = "While usually kept in a human shape, [your] constantly dripping arms are actually liquid, allowing [you] to shape them to [your] convenience."
	),
	HYDRA(37, "hydra",
		appearanceDesc = "Plate-like scales cover [your] arms from the biceps down, and [your] fingernails are sharp curved claws perfect for tearing through flesh.",
		hasClaws = true
	),
	GHOST(38, "ghost",
		appearanceDesc = "[Your] arms and hands are practically human, save for the fact that they are ghastly in nature."
	),
	JIANGSHI(39, "jiangshi",
		appearanceDesc = "[Your] arms are essentially human, but underneath [your] purple nails is a thick layer of deadly poison.",
		hasClaws = true
	),
	RAIJU_PAWS(40, "raiju paws",
		appearanceDesc = "[Your] forearms are covered by dense fur upon which an electric current runs free. [Your] pawed hands end in sharp claws capable of delivering powerful discharges.",
		hasClaws = true
	),
	YUKI_ONNA(41, "yuki onna",
		appearanceDesc = "[Your] arms and hands are human in appearance, but [your] blue-nailed hands are colder than death."
	),
	MELKIE(42, "melkie",
		appearanceDesc = "[Your] arms and hands are human in appearance, but [your] blue-nailed hands are colder than death.",
		hasClaws = true
	),
	CENTIPEDE(43, "centipede",
		appearanceDesc = "[Your] arms and hands are mostly human in shape, ending in sharp purple nails. Intricate flowing purple markings stretch from [your] shoulders to [your] hands."
	),
	KRAKEN(44, "kraken"),
	FROSTWYRM(45, "frostwyrm",
		appearanceDesc = "[Your] forearms are covered by scales which themselves are covered by fur, ending in five fingered clawed hands. [Your] claws are strong enough to rip and tear through next to anything.",
		hasClaws = true
	),
	CANCER(46, "cancer"),
	USHI_ONI(47, "ushi-oni",
		appearanceDesc = "[You] [have] ushi-oni arms, longer and thicker than those of most races. A strange pattern of fur descends from [your] middle biceps down to [your] hands, where [you] [have] bone-like claws rather than fingers."
	),
	KAMAITACHI(48, "kamaitachi",
		appearanceDesc = "[Your] arms are covered by [skin coat.color] fur up to [your] shoulders. They end in a pair of five-toed weasel paws armed with claws. [Your] forearms' fur part ways slightly as a pair of long scythes-like blades curves outward toward your elbow, sharper than any sword.",
		hasScythe = true
	),
	GAZER(49, "gazer",
		appearanceDesc = "[Your] arms are human in appearance, but drip with oily black fluid."
	),
	RACCOON(50, "raccoon",
		hasClaws = true
	),
	WEASEL(51, "weasel",
		hasClaws = true
	),
	SQUIRREL(52, "squirrel",
		appearanceDesc = "[Your] arms are covered by [skin coat.color] fur up to [your] shoulders. They end in a pair of five-toed squirrel paws, armed with claws which can assist [you] when climbing trees.",
		hasClaws = true
	),
	WENDIGO(53, "wendigo",
		appearanceDesc = "[Your] arms and hands are practically human, save for the sharp white claws [you] [have] in place of normal nails.",
		hasClaws = true
	),
	BAT(54, "bat",
		appearanceDesc = "The bones in [your] arms are thin and light, granting [you] the ability to take flight. Instead of the five fingers [you] started out with, [you] now [have] three that are both larger and stronger. Besides the occasional troubles with an awkward grip, [you] can still hold various items even with [your] abnormal hands, albeit at the cost of preventing flight while doing so.",
		hasClaws = true
	) {
		override val canFly: Boolean get() = true
		override val canWingSlap: Boolean get() = true
	},
	SEA_DRAGON(55, "sea dragon",
		appearanceDesc = "A pair of fins have sprouted on each of [your] forearms near [your] elbows, and the skin between [your] fingers forms a small webbing which assists [your] swimming. [You] [have] sharp and deadly claws which allow [you] to impale your prey or rip your foes to shreds."
	),
	MINDBREAKER(56, "mindbreaker",
		appearanceDesc = "[Your] arms are sleeved up in a web-like membrane, it’s very likely that they could be good for swimming too."
	),
	;

	open fun appearanceDescription(creature: Creature):String = appearanceDesc
	open val canFly: Boolean get() = false
	open val canWingSlap: Boolean get() = false

	companion object: CocIdLookup<ArmType>(values())
}
