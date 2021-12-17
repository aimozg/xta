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
		appearanceDesc = "[Your] {legCount} digitigrade [gargoylematerial] legs end in sharp-clawed stone feet. There are three long toes on the front, and a small hind claw on the back."
	),
	PLANT_HIGH_HEELS(
		31, "vine-covered",
		appearanceDesc = "[Your] {legCount} perfect lissome legs end in human feet, apart from delicate vines covered in spade-like leaves crawling around them on the whole length."
	),
	PLANT_ROOT_CLAWS(
		32, "root feet",
		appearanceDesc = "[Your] {legCount} legs look quite normal aside feet. They turned literally into roots only vaguely retaining the shape of the feet."
	),
	WOLF(
		33, "wolf",
		canTaur = true,
		appearanceDesc = "{legCount} digitigrade legs grow downwards from [your] waist, ending in clawed wolf-like hind-paws."
	) {
		override val canPounce: Boolean
			get() = true
	},
	PLANT_FLOWER(
		34, "plant flower",
		defaultLegCount = 12,
		wordLegs = "vine-like tentacle stamens",
		wordLeg = "vine-like tentacle stamen",
		appearanceDesc = "Around [your] waist, the petals of a large [skin nakedcoatcolor.color] orchid expand, big enough to engulf you entirely on their own, coupled with a pitcher-like structure in the centre, which is filled with syrupy nectar straight from [your] loins. When [you] [verb wish] to rest, these petals draw up around [her], encapsulating [her] in a beautiful bud.  While [you] [verb do]n't technically have legs anymore, [he] can still move around on [your] {legCount} vine-like stamens."
	) {
		override val hasTentacles: Boolean
			get() = true
		override val isAlraune: Boolean
			get() = true
	},
	LION(
		35, "lion",
		canTaur = true,
		appearanceDesc = "[Your] four legs are covered in [skin coat.color] fur up to the thigh where it fades to white. They end with digitigrade lion paws. [You] can dash on all fours as gracefully as [you] would on two legs."
	) {
		override val isFeline get() = true
		override val canPounce get() = true
	},
	YETI(
		36, "yeti",
		appearanceDesc = "[Your] {legCount} fur covered legs end with a pair of very large yeti feet, leaving large tracks and granting [you] easy mobility in the snow."
	),
	ORCA(
		37, "orca",
		canTaur = true,
		appearanceDesc = "[Your] {legCount} legs are mostly human save for the webbing between [your] toes that assists [you] in swimming."
	),
	YGG_ROOT_CLAWS(
		38, "root feet",
		appearanceDesc = "[Your] {legCount} legs looks quite normal until your feet. [Your] roots have condensed into a self-contained shape of three clawed toes on the front, and a small hind-claw in the back. You doubt they can gather moisture very well like this, but at least [you] [have] an excellent grip."
	),
	ONI(
		39, "oni",
		appearanceDesc = "[Your] {legCount} legs are covered with a set of warlike tattoo and [your] feet end with sharp black nails."
	),
	ELF(
		40, "elf",
		appearanceDesc = "[Your] {legCount} perfect lissom legs end in delicate but agile elven feet, allowing [you] to move gracefully and swiftly."
	),
	RAIJU(
		41, "raiju",
		canTaur = true,
		appearanceDesc = "[You] [have] {legCount} fluffy, furred legs that look vaguely like knee high socks. [Your] pawed feet end in four thick toes, which serve as [your] main source of balance. [You] can walk on them as normally as [your] old plantigrade legs. A thick strand of darkly colored fur breaks out from [your] ankles, emulating a bolt of lighting in appearance."
	),
	RED_PANDA(
		42, "red panda",
		appearanceDesc = "[Your] {legCount} legs are equally covered in [skin coat.color] fur, ending on red-panda paws with short claws. They have a nimble and strong build, in case [you] [verb need] to escape from something."
	),
	GARGOYLE_2(
		43, "gargoyle",
		appearanceDesc = "[Your] {legCount} [gargoylematerial] legs aside of their stone structure look pretty much human."
	),
	AVIAN(
		44, "avian",
		canTaur = true,
		appearanceDesc = "[You] [have] strong thighs perfect for launching [you] into the air which end in slender, bird-like legs, covered with a [skin coat.color] plumage down to [your] knees and slightly rough, [skin] below. [You] [have] digitigrade feet, with toes that end in sharp talons."
	),
	GRYPHON(
		45, "gryphon",
		canTaur = true,
		appearanceDesc = "[You] [have] strong thighs perfect for launching [you] into the air ending in furred, feline legs, covered with a coat of soft, [skin coat.color2] fur. [Your] [have] digitigrade feet, lion-like, with soft, pink soles and paw pads, with feline toes ending in sharp, retractile claws."
	),
	ORC(
		46, "orc",
		appearanceDesc = "[Your] {legCount} bowed legs are covered with a set of scar-like tattoos and [your] feet end with sharp, pointed nails."
	),
	CAVE_WYRM(
		47, "cave wyrm",
		appearanceDesc = "{legCount} digitigrade legs covered in thick, leathery black scales up to the mid-thigh grow down from [your] [hips], ending in clawed feet. There are three long toes on the front, and a small hind-claw on the back."
	),
	MOUSE(
		48, "mouse",
		appearanceDesc = "[You] [verb stand] on {legCount} digitigrade mouse legs ending in five toed clawed paws."
	),
	HINEZUMI(
		49, "hinezumi",
		appearanceDesc = "[Your] {legCount} digitigrade mouse legs are covered in flames up to [your] knee. [Your] kicks leave a vicious burn on those who cross [you], which they are sure to remember."
	),
	BEAR(
		50, "bear",
		canTaur = true,
		appearanceDesc = "[Your] {legCount} legs are covered with [skin coat.color] fur. They end with powerful bear-like paws."
	),
	HYDRA(
		51, "hydra",
		defaultLegCount = 2,
		wordLegs = "snake-like coils",
		wordLeg = "hydra-tail",
		wordFeet = "coils",
		wordFoot = "coiled tail"
	) {
		// TODO replace [hydraheads] with number of heads
		override fun appearanceDescription(creature: Creature) =
			"Below [your] waist [your] flesh is fused together into the body of a snake which split into [hydraheads] [doubletallness] long serpentine coils each ending with a snake head. Your many heads tend to hiss when you are in pain or angry."

		override fun crotchDescription(creature: Creature) = buildString {
			if (creature.gender != Gender.GENDERLESS) {
				append("[Your] ")
				if (creature.gender == Gender.HERM || creature.cocks.size > 1) append("sexes are ")
				else append("sex is ")
				append("concealed within a cavity in [your] tail when not in use, though when the need arises, [you] can part your concealing slit and reveal [your] true self.")
			}
		}
	},
	FIRE_SNAIL(
		52, "fire snail",
		appearanceDesc = "Below [your] waist [your] flesh is fused together into the fat tail of a snail, with a flat wet underbelly that glistens continuously."
	),
	GHOST(
		53, "phantom",
		appearanceDesc = "[You] [have] {legCount} partially transparent due to their ghostly nature human legs grow down from [your] waist, ending in normal human feet."
	),
	GHOST_2(
		54, "poltergeist",
		appearanceDesc = "Below [your] waist, [your] body fuses and fades away, like a ghost."
	) {
		override val noLowerGarment get() = true
	},
	JIANGSHI(
		55, "jiangshi",
		appearanceDesc = "While [your] legs are human in appearance [your] body is so rigid due to this pseudo rigor mortis that the only way [you] found for movement is by hopping around."
	),
	YUKI_ONNA(
		56, "yuki onna",
		appearanceDesc = "[Your] legs are human in appearance albeit for the bluish nails."
	),
	MELKIE(
		57, "melkie",
		appearanceDesc = "Beneath [your] waist [your] body ends in the tail of a leopard seal. It allows [you] to swim gracefully in arctic waters. However, when the time to move on land arises, [you] can part the fur at your waist in order to let [your] two human legs out and walk on solid ground as the land dwellers do."
	) {
		override val canTailSlam = true

		override fun crotchDescription(creature: Creature) =
			"[You] [have] a outer set of vaginal lips at the junction between [your] human body and seal tail, in which [your] internal sex and human legs are hidden when not in use."
	},
	CENTIPEDE(
		58, "centipede",
		appearanceDesc = "In place of legs [you] [have] the body of a giant centipede. [Your] long segmented insectoid body has over a dozen pairs of spindly legs and is tipped with a pair of stingers that can pierce even the strongest armor."
	) {
		override val noTail get() = true
	},
	KRAKEN(
		59, "kraken",
		appearanceDesc = "Where [your] legs would normally start, [youve] grown the body of a giant squid, with {legCount} tentacle legs that sprout from [your] [hips], two of them larger than the others. [Your] tentacles reach for up to [doubletallness] feet in length!"
	) {
		override fun crotchDescription(creature: Creature) = when (creature.gender) {
			Gender.GENDERLESS -> ""
			Gender.MALE -> "[Your] sex is concealed between [your] front octopus tentacle legs, dangling freely when not in use."
			Gender.FEMALE -> "[Your] sex is concealed underneath [your] octopus tentacle legs when not in use, though when the need arises, [you] can rise some of the tentacles and reveal [your] true self."
			Gender.HERM -> buildString {
				append("[Your] sex")
				if (creature.cocks.size > 1) {
					append("es are ")
				} else {
					append(" is ")
				}
				append("concealed between [your] front octopus tentacle legs, dangling freely. The other set is concealed underneath [your] octopus tentacle legs when not in use, though when the need arises, [you] can rise some of the tentacles and reveal it.")
			}
		}

		override val isScylla: Boolean
			get() = true
		override val hasTentacles: Boolean
			get() = true
		override val noTail: Boolean
			get() = true
	},
	CRAB(
		60, "crab",
		appearanceDesc = "[Your] {legCount} legs are covered in a reflective [skin coat.color], crab like carapace up to [your] mid-thigh, looking more like a set of 'fuck-me-boots' than exoskeleton."
	),
	CANCER(
		61, "cancer",
		defaultLegCount = 6,
		appearanceDesc = "Where [your] legs would normally start, [you] [have] grown the body of a crab, with {legCountMinusTwo} chitin plated legs and two large pincers capable of tearing steel plating to shreds. A pair of stalk mounted crab eyes on the front of [your] shell look at [your] surroundings, giving [her] a full peripheral vision. On the front of [your] crab half, covering [your] privates, is a set of chitinous mandibula covered in feelers, constantly chittering and foaming with your drooling fluids."
	) {
		override val isDrider: Boolean
			get() = true
		override val hasClaws: Boolean
			get() = true
		override val hasPincers: Boolean
			get() = true
	},
	FROSTWYRM(62, "frost wyrm") {
		override fun appearanceDescription(creature: Creature) = buildString {
			append("Below [your] waist [your] flesh is fused together into a very long snake-like tail easily [quadrupletallness] feet long. [Your] entire tail length up to the waist is covered with thick snow white fur, and underneath is a whole layer of [skin coat.color] dragon scales, harder than steel and capable of deflecting most weapons.")

			if (creature.tallness > 120) {
				append(" [Your] body is so large it's no wonder [your] passage underground can cause tremors.")
			}
		}

		override fun crotchDescription(creature: Creature) = buildString {
			if(creature.gender != Gender.GENDERLESS) {
				append("[Your] sex")
				if(creature.gender == Gender.HERM || creature.cocks.size > 1) append("es are ")
				else append(" is ")
				append("concealed within a cavity in [your] tail when not in use, though when the need arises, [you] can part your concealing slit and reveal [your] true self.")
			}
		}

		override val hasOwntail: Boolean
			get() = true
		override val canTailSlam: Boolean
			get() = true
		override val isNaga: Boolean
			get() = true
		override val isDraconic: Boolean
			get() = true
	},
	USHI_ONI(63, "ushi-oni",
		appearanceDesc = "[You] [have] the lower body of an ushi-oni, it is like the one of a drider except that it is covered in fur with the exoskeleton under it, the usually black sheen legs of a drider are replaced with rough bone ones of an ushi-oni."
	) {
		override val isDrider: Boolean
			get() = true
	},
	FLOWER_LILIRAUNE(64, "liliraune flower",
		appearanceDesc = "Around [your] waist, the petals of a large [skin nakedcoatcolor.color] orchid expand, big enough to engulf [you] entirely on their own, coupled with a pitcher-like structure in the center, which is filled with syrupy nectar straight from [your] loins. When [you] [wishes] to rest, these petals draw up around [him], encapsulating [him] in a beautiful bud. While [you] [verb do]n't technically [have] legs anymore, [you] can still move around on [your] {legCount} vine-like stamens. [You] used to be alone in the pitcher, but [you] now [verb share] space with [your] twin sister, taking turns with her whether it's for battle or sex."
	) {
		override val hasTentacles: Boolean
			get() = true
		override val isAlraune: Boolean
			get() = true
	},
	WEASEL(65, "weasel",
		canTaur = true,
		appearanceDesc = "[You] [have] {legCount} fluffy, furred legs like those of a weasel. [Your] pawed feet end in four thick toes, which serve as [your] main source of balance. [You] can walk on them as normally as [your] old plantigrade legs."
	),
	GAZER(66, "gazer",
		appearanceDesc = "[Your] {legCount} legs are human in appearance but drips with oily black fluids. [Youve] not been using them just as much as of late since [you] [are] constantly levitating anyway."),
	SQUIRREL(67, "squirrel",
		canTaur = true,
		appearanceDesc = "[You] [have] {legCount} fluffy, furred legs are covered in [skin coat.color] fur up to the thigh. They end with digitigrade squirrel paws."
	),
	WENDIGO(68, "wendigo",
		appearanceDesc = "[Your] legs are covered in fur up to the knee however they are entirely devoid of feet."
	),
	ATLACH_NACHA(69, "atlach nacha",
		defaultLegCount = 8,
		wordLegs = "spider legs",
		appearanceDesc = "Where [your] legs would normally start [you] [have] grown the body of a spider, with {legCount} spindly legs that sprout from its sides."
	) {
		override val isDrider: Boolean
			get() = true
	},
	SEA_DRAGON(70, "sea dragon",
		canTaur = true,
		appearanceDesc = "[Your] {legCount} legs are mostly human save for the webbing between [your] clawed toes that assists you in swimming."
	) {
		override val isDraconic: Boolean
			get() = true
	},
	MINDBREAKER(71, "mindbreaker",
		appearanceDesc = "Where [you] would normally have feet, eight tentacles extends, connected together by a fleshy membrane at the base like a skirt. [You] [verb leave] an ominous trail of green slime wherever [you] [verb pass]. Along [your] legs three extra pairs of green eyes stare at the world lined from [your] tight to the waist up."
	),
	MINDBREAKERMALE(72, "mindbreaker",
		appearanceDesc = "[You] [verb stand] bipedal, [your] feet are mostly human in shape though [your] toes are webbed, presumably to assist with swimming. They are fit enough to support the weight of [yourself] and someone else if they were to be on top of [you]."
	),
	;

	open fun appearanceDescription(creature: Creature) =
		appearanceDesc.replace("""{legCount}""", num2text(creature.lowerBody.legCount)).capitalized()

	open fun crotchDescription(creature: Creature): String = ""

	open val isAlraune: Boolean get() = false
	open val isDraconic: Boolean get() = false
	open val isDrider: Boolean get() = false
	open val isEggLayer: Boolean get() = false
	open val isFeline: Boolean get() = false
	open val isGoo: Boolean get() = false
	open val isNaga: Boolean get() = false
	open val isScylla: Boolean get() = false
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
