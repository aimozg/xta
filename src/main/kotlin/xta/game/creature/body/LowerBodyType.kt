package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup
import xta.game.Creature
import xta.game.creature.Gender
import xta.text.num2text
import xta.utils.capitalized

enum class LowerBodyType(
	override val cocID: Int,
	val displayName: String,
	val canTaur: Boolean = false,
	val defaultLegCount: Int = 2,
	val wordFoot: String = "foot",
	val wordFeet: String = "feet",
	val wordLeg: String = "leg",
	val wordLegs: String = "legs",
	val footPrefixes: Array<String>? = null,
	val legPrefixes: Array<String>? = null,
	private val appearanceDesc: String = ""
) : CocId {
	HUMAN(
		0, "human",
		appearanceDesc = "{legCount} human legs grow down from [your] [hips], ending in normal human feet."
	),
	HOOFED(
		1, "hoofed",
		wordFoot = "hoof",
		wordFeet = "hooves",
		canTaur = true
	) {
		override fun appearanceDescription(creature: Creature) = when {
			creature.isBiped ->
				"[Your] two legs are muscled and jointed oddly, covered in [skin coat.color] fur, and end in a bestial hooves."
			creature.isTaur ->
				"From the waist down, [you] [have] the body of a horse, with four muscled legs which are jointed oddly, covered in [skin coat.color] fur, and end in a bestial hooves."
			else -> ""
		}
	},
	DOG(
		2, "dog",
		canTaur = true,
		wordFoot = "dog paw",
		wordFeet = "dog paws",
		appearanceDesc = "{legCount} digitigrade legs grow downwards from [your] waist, ending in dog-like hind-paws."
	),
	NAGA(
		3, "naga",
		wordFoot = "coiled tail",
		wordFeet = "coils",
		wordLeg = "snake-tail",
		wordLegs = "snake-like coils",
		defaultLegCount = 1,
		appearanceDesc = "Below [your] waist, [your] flesh is fused together into a very long snake-like tail.",
	) {
		override val isNaga get() = true
		override val canTailSlam get() = true
		override val hasOwntail get() = true
		override fun crotchDescription(creature: Creature) = buildString {
			if (creature.gender != Gender.GENDERLESS) {
				append("[Your] ")
				if (creature.gender == Gender.HERM || creature.cocks.size > 1) append("sexes are ")
				else append("sex is ")
				append("concealed within a cavity in [your] tail when not in use, though when the need arises, [you] can part your concealing slit and reveal [your] true self.")
			}
		}
	},

	//	CENTAUR(4),
	DEMONIC_HIGH_HEELS(
		5, "demonic high-heels",
		wordFoot = "demonic high-heel",
		wordFeet = "demonic high-heels",
		appearanceDesc = "[Your] {legCount} perfect lissome legs end in mostly human feet, apart from the horns protruding straight down from the heel that force [you] to walk with a sexy, swaying gait."
	),
	DEMONIC_CLAWS(
		6, "demonic claws",
		wordFoot = "demonic foot-claw",
		wordFeet = "demonic foot-claws",
		appearanceDesc = "[Your] {legCount} lithe legs are capped with flexible clawed feet. Sharp black nails grow where once [you] had toe-nails, giving [you] fantastic grip."
	),
	BEE(
		7, "bee",
		appearanceDesc = "[Your] {legCount} legs are covered in a shimmering insectile carapace up to mid-thigh, looking more like a set of 'fuck-me-boots' than an exoskeleton. A bit of downy yellow and black fur fuzzes [your] upper thighs, just like a bee."
	),
	GOO(
		8, "goo",
		wordLegs = "mounds of goo",
		wordLeg = "mound of goo",
		wordFeet = "slimey cillia",
		wordFoot = "slimey undercarriage",
		defaultLegCount = 1,
		appearanceDesc = "In place of legs [you] [have] a shifting amorphous blob. Thankfully it's quite easy to propel [yourself] around on. The lowest portions of [your] [armor] float around inside [you], bringing [you] no discomfort."
	) {
		override val isGoo get() = true
	},
	CAT(
		9, "cat",
		canTaur = true,
		appearanceDesc = "{legCount} digitigrade legs grow downwards from [your] waist, ending in soft, padded cat-paws."
	) {
		override val canPounce get() = true
		override val isFeline get() = true
	},
	LIZARD(
		10, "lizard",
		canTaur = true,
		appearanceDesc = "{legCount} digitigrade legs grow down from [your] [hips], ending in clawed feet. There are three long toes on the front, and a small hind-claw on the back."
	),
	PONY(
		11, "pony",
		wordFoot = "flat pony-foot",
		wordFeet = "flat pony-feet",
		wordLeg = "cartoonish pony-leg",
		wordLegs = "cute pony-legs",
		defaultLegCount = 4,
		canTaur = true
	) {
		override fun appearanceDescription(creature: Creature): String {
			return if (creature.isTaur) "From the waist down, [you] [have] an incredibly cute and cartoonish parody of a horse's body, with all four legs ending in flat, rounded feet."
			else ""
		}

	},
	BUNNY(
		12, "bunny",
		footPrefixes = arrayOf("large bunny ", "rabbit ", "large ", ""),
		legPrefixes = arrayOf("fuzzy bunny ", "fut-covered ", "furry ", ""),
		canTaur = true,
		appearanceDesc = "[Your] {legCount} legs thicken below the waist as they turn into soft-furred rabbit-like legs. [You] even [have] large bunny feet that make hopping around a little easier than walking."
	),
	HARPY(
		13, "harpy",
		footPrefixes = arrayOf("taloned ", ""),
		legPrefixes = arrayOf("bird-like ", "feathered ", ""),
		appearanceDesc = "[Your] {legCount} legs are covered with [haircolor] plumage. Thankfully the thick, powerful thighs are perfect for launching [you] into the air, and [your] feet remain mostly human, even if they are two-toed and tipped with talons."
	),
	KANGAROO(
		14, "kangaroo",
		wordFoot = "foot-paw",
		wordFeet = "foot-paws",
		appearanceDesc = "[Your] {legCount} furry legs have short thighs and long calves, with even longer feet ending in prominently-nailed toes."
	),
	CHITINOUS_SPIDER_LEGS(
		15, "chitinous spider legs",
		appearanceDesc = "[Your] {legCount} legs are covered in a reflective [skin coat.color], insectile carapace up to [your] mid-thigh, looking more like a set of 'fuck-me-boots' than exoskeleton."
	),
	DRIDER(
		16, "drider",
		defaultLegCount = 8,
		wordLeg = "spider leg",
		wordLegs = "spider legs",
		appearanceDesc = "Where [your] legs would normally start, [you] [have] grown the body of a spider, with {legCount} spindly legs that sprout from its sides."
	) {
		override val isDrider get() = true
	},
	FOX(
		17, "fox",
		canTaur = true,
		footPrefixes = arrayOf("soft padded ", "fox ", "", ""),
		wordFoot = "paw",
		wordFeet = "paws",
		legPrefixes = arrayOf("fox ", "vulpine ", "", ""),
		appearanceDesc = "[Your] {legCount} fox legs are crooked into high knees with hocks and long feet; cute bulbous toes decorate the ends."
	),
	DRAGON(
		18, "dragon",
		appearanceDesc = "{legCount} human-like legs grow down from [your] [hips], sheathed in scales and ending in clawed feet. There are three long toes on the front, and a small hind-claw on the back."
	) {
		override val isDraconic get() = true
	},
	RACCOON(
		19, "raccoon",
		footPrefixes = arrayOf("raccoon ", "long-toed ", ""),
		wordFoot = "paw",
		wordFeet = "paws",
		legPrefixes = arrayOf("racoon-like ", ""),
		canTaur = true,
		appearanceDesc = "[Your] {legCount} legs, though covered in fur, are humanlike. Long feet on the ends bear equally long toes, and the pads on the bottoms are quite sensitive to the touch."
	),
	FERRET(
		20, "ferret",
		canTaur = true,
		appearanceDesc = "{legCount} furry, digitigrade legs form below [your] [hips]. The fur is thinner on the feet, and [your] toes are tipped with claws."
	),
	CLOVEN_HOOFED(
		21, "cloven-hoofed",
		canTaur = true,
		legPrefixes = arrayOf("pig-like ", "cloven-hoofed ", "", ""),
		appearanceDesc = "{legCount} digitigrade legs form below [your] [hips], ending in cloven hooves."
	),
	ECHIDNA(23, "echidna", canTaur = true),

	//DEERTAUR(24),
	SALAMANDER(
		25, "salamander",
		canTaur = true,
		appearanceDesc = "{legCount} digitigrade legs covered in thick, leathery red scales up to the mid-thigh grow down from [your] [hips], ending in clawed feet. There are three long toes on the front, and a small hind-claw on the back."
	) {
		override val isEggLayer get() = true
	},
	SCYLLA(
		26, "slippery octopus tentacles",
		appearanceDesc = "Where [your] legs would normally start, [you] [have] grown the body of an octopus, with {legCount} tentacle legs that sprout from your [hips]."
	) {
		override fun crotchDescription(creature: Creature): String {
			return when (creature.gender) {
				Gender.GENDERLESS -> ""
				Gender.MALE -> "[Your] sex is concealed between [your] front octopus tentacle legs dangling freely when not in use."
				Gender.FEMALE -> "[Your] sex is concealed underneath [your] octopus tentacle legs when not in use, though when the need arises, [you] can rise some of the tentacles and reveal [your] true self."
				Gender.HERM -> buildString {
					if (creature.cocks.size > 1) {
						append("[Your] sexes are ")
					} else {
						append("[Your] sex is ")
					}
					append("concealed between [your] front octopus tentacle legs dangling freely. Other set is concealed underneath [your] octopus tentacle legs when not in use, though when the need arises, [you] can rise some of the tentacles and reveal it.")
				}
			}
		}

		override val isScylla: Boolean
			get() = true
		override val hasTentacles: Boolean
			get() = true
		override val noTail: Boolean
			get() = true
	},
	MANTIS(
		27, "mantis",
		canTaur = true,
		appearanceDesc = "[Your] {legCount} legs are covered in a shimmering [skin coat.color], insectile carapace up to mid-thigh, looking more like a set of 'fuck-me-boots' than exoskeleton."
	),
	SHARK(
		29, "shark",
		canTaur = true
	) {
		override fun appearanceDescription(creature: Creature) = when {
			creature.isBiped ->
				"[Your] two legs are mostly human save for the webbing between your toes."
			creature.isTaur ->
				"[Your] four legs end in three-toed scaled paws with webbing between the toes, and an even larger webbing running on the entire length."
			else ->
				""
		}
	},
	GARGOYLE(
		30, "gargoyle",
		appearanceDesc = "Your {legCount} digitigrade [gargoylematerial] legs end in sharp-clawed stone feet. There are three long toes on the front, and a small hind claw on the back."
	),

	// TODO continue porting descriptions from here
	PLANT_HIGH_HEELS(31, "vine-covered"),
	PLANT_ROOT_CLAWS(32, "root feet"),
	WOLF(33, "wolf"),
	PLANT_FLOWER(34, "plant flower"),
	LION(35, "lion"),
	YETI(36, "yeti"),
	ORCA(37, "orca"),
	YGG_ROOT_CLAWS(38, "root feet"),
	ONI(39, "oni"),
	ELF(40, "elf"),
	RAIJU(41, "raiju"),
	RED_PANDA(42, "red panda"),
	GARGOYLE_2(43, "gargoyle"),
	AVIAN(44, "avian"),
	GRYPHON(45, "gryphon"),
	ORC(46, "orc"),
	CAVE_WYRM(47, "cave wyrm"),
	MOUSE(48, "mouse"),
	HINEZUMI(49, "hinezumi"),
	BEAR(50, "bear"),
	HYDRA(51, "hydra"),
	FIRE_SNAIL(52, "fire snail"),
	GHOST(53, "phantom"),
	GHOST_2(54, "poltergeist"),
	JIANGSHI(55, "jiangshi"),
	YUKI_ONNA(56, "yuki onna"),
	MELKIE(57, "melkie"),
	CENTIPEDE(58, "centipede"),
	KRAKEN(59, "kraken"),
	CRAB(60, "crab"),
	CANCER(61, "cancer"),
	FROSTWYRM(62, "frost wyrm"),
	USHI_ONI(63, "ushi-oni"),
	FLOWER_LILIRAUNE(64, "liliraune flower"),
	WEASEL(65, "weasel"),
	GAZER(66, "gazer"),
	SQUIRREL(67, "squirrel"),
	WENDIGO(68, "wendigo"),
	ATLACH_NACHA(69, "atlach nacha"),
	SEA_DRAGON(70, "sea dragon"),
	MINDBREAKER(71, "mindbreaker"),
	MINDBREAKERMALE(72, "mindbreaker"),
	;

	open fun appearanceDescription(creature: Creature) =
		appearanceDesc.replace("""{legCount}""", num2text(creature.lowerBody.legCount)).capitalized()

	open fun crotchDescription(creature: Creature): String = ""

	open val isNaga: Boolean get() = false
	open val isDrider: Boolean get() = false
	open val isGoo: Boolean get() = false
	open val isScylla: Boolean get() = false
	open val isDraconic: Boolean get() = false
	open val isFeline: Boolean get() = false
	open val isEggLayer: Boolean get() = false
	open val hasTentacles: Boolean get() = false
	open val hasPincers: Boolean get() = false
	open val hasTalons: Boolean get() = false
	open val hasClaws: Boolean get() = false
	open val hasOwntail: Boolean get() = false
	open val noTail: Boolean get() = false
	open val canTailSlam: Boolean get() = false
	open val canPounce: Boolean get() = false
	open val noLowerGarment: Boolean get() = false

	companion object : CocIdLookup<LowerBodyType>(values())
}
