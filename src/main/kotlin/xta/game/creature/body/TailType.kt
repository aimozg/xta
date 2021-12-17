package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup
import xta.game.Creature

enum class TailType(
	override val cocID: Int,
	val displayName: String,
	val isLong: Boolean = false,
	val canTailSlam: Boolean = false,
	private val appearanceDesc: String = ""
) : CocId {
	NONE(0, "non-existant"),
	HORSE(
		1, "horse",
		appearanceDesc = "A long [skin coat.color] horse tail hangs from [your] [butt], smooth and shiny."
	),
	DOG(
		2, "dog",
		isLong = true,
		appearanceDesc = "A fuzzy [skin coat.color] dog tail sprouts just above [your] [butt], wagging to and fro whenever [youre] happy."
	),
	DEMONIC(
		3, "demonic",
		isLong = true,
		appearanceDesc = "A narrow tail ending in a spaded tip curls down from [your] [butt], wrapping around [your] [leg] sensually at every opportunity."
	),
	COW(
		4, "cow",
		isLong = true,
		appearanceDesc = "A long cow tail with a puffy tip swishes back and forth, as if swatting at flies."
	),
	SPIDER_ADBOMEN(5, "spider abdomen") {
		override fun appearanceDesc(creature: Creature) = buildString {
			append("A large, spherical spider abdomen has grown out from [your] backside, covered in shiny [skin coat.color] chitin. Though it's heavy and bobs with every motion, it doesn't seem to slow [him] down.")
			val venom = creature.tail.venom / creature.maxVenom()
			when {
				venom >= 1.0 ->
					append(" [Your] swollen spider-butt is distended with the sheer amount of webbing it's holding.")
				venom >= 0.8 ->
					append(" [Your] arachnid rear bulges and feels very full of webbing.")
				venom >= 0.5 ->
					append(" [Your] bulging arachnid posterior feels fairly full of webbing.")
			}
		}
	},
	BEE_ABDOMEN(6, "bee abdomen") {
		override val hasStinger get() = true
		override fun appearanceDesc(creature: Creature) = buildString {
			append("A large bee abdomen dangles from just above [your] backside, bobbing with its own weight as [you] [verb shift]. It is covered in hard chitin with black and yellow stripes, and tipped with a dagger-like stinger.")
			val venom = creature.tail.venom / creature.maxVenom()
			when {
				venom >= 1.0 ->
					append(" Venom drips from [your] poisoned stinger regularly.")
				venom >= 0.8 ->
					append(" Poisonous bee venom coats [your] stinger completely.")
				venom >= 0.5 ->
					append(" A single drop of poison hangs from [your] exposed stinger.")
			}
		}
	},
	SHARK(
		7, "shark",
		isLong = true,
		canTailSlam = true,
		appearanceDesc = "A long shark tail trails down from [your] backside, swaying to and fro while giving [him] a dangerous air."
	),
	CAT(
		8, "cat",
		isLong = true
	) {
		override fun appearanceDesc(creature: Creature) =
			if (creature.tail.count == 2) {
				"A pair of soft [skin coat.color] cat tails sprout just above [your] [butt], curling and twisting with every step to maintain perfect balance."
			} else {
				"A soft [skin coat.color] cat tail sprouts just above [your] [butt], curling and twisting with every step to maintain perfect balance."
			}
	},
	LIZARD(
		9, "lizard",
		isLong = true,
		canTailSlam = true,
		appearanceDesc = "A tapered tail hangs down from just above [your] [butt]. It sways back and forth, assisting [him] in keeping [your] balance."
	),
	RABBIT(
		10, "rabbit",
		appearanceDesc = "A short, soft bunny tail sprouts just above [your] [butt], twitching constantly even when [you] [verb do]n't think about it."
	),
	HARPY(
		11, "harpy",
		appearanceDesc = "A tail of feathers fans out from just above [your] [butt], twitching instinctively to help guide you if [you] [verb were] to take flight."
	),
	KANGAROO(
		12, "kangaroo",
		isLong = true
	) {
		override fun appearanceDesc(creature: Creature) = buildString {
			append("A conical, ")

			if (creature.hasFur()) {
				append("furry, and [skin coat.color] ")
			} else {
				append("gooey, and [skintone] ")
			}
			append("tail extends from [your] [butt], bouncing up and down as [you] [verb move] to assist in [your] balance.")
		}
	},
	FOX(
		13, "fox",
		isLong = true
	) {
		override fun appearanceDesc(creature: Creature) =
			if (creature.tail.count == 1) {
				"A swishing [skin coat.color] fox brush extends from [your] [butt], curling around [your] body; the soft fur feels lovely, and [you] [verb wonder] how many would be enticed by it."
			} else {
				"[TailCount] swishing [skin coat.color] fox tails extend from [your] [butt], curling around [your] body; the soft fur feels lovely, and you wonder how many would be enticed by it."
			}
	},
	DRACONIC(
		14, "draconic",
		isLong = true,
		canTailSlam = true,
		appearanceDesc = "A thin, scaly, prehensile reptilian tail, almost as long as [you] [are] tall, swings behind [you] like a living bullwhip. The end of its tip is equipped with spikes of bone, meant to deliver painful blows."
	) {
		override val isDraconic: Boolean
			get() = true
	},
	RACCOON(
		15, "raccoon",
		isLong = true,
		appearanceDesc = "A black-and-[skin coat.color]-ringed raccoon tail waves behind [you]."
	),
	MOUSE(
		16, "mouse",
		isLong = true,
		appearanceDesc = "A naked, [skintone] mouse tail pokes from [your] butt, dragging on the ground and twitching occasionally."
	),
	FERRET(
		17, "ferret",
		isLong = true
	),
	BEHEMOTH(
		18, "behemoth",
		isLong = true,
		canTailSlam = true,
		appearanceDesc = "A long, seemingly tapering behemoth tail pokes from [your] butt, ending in spikes."
	),
	PIG(
		19, "pig",
		appearanceDesc = "A short, curly pig tail sprouts from just above [your] butt."
	),
	SCORPION(
		20, "scorpion",
		isLong = true
	) {
		override val hasStinger: Boolean
			get() = true

		override fun appearanceDesc(creature: Creature) = buildString {
			append("A large insectile scorpion tail dangles from just above [your] backside, bobbing with its own weight as [you] shift. It is covered in hard chitin and tipped with a stinger.")
			val venom = creature.tail.venom / creature.maxVenom()
			when {
				venom >= 1.0 ->
					append(" Venom drips from [your] poisoned stinger regularly.")
				venom >= 0.8 ->
					append(" Poisonous bee venom coats [your] stinger completely.")
				venom >= 0.5 ->
					append(" A single drop of poison hangs from [your] exposed stinger.")
			}
		}
	},
	GOAT(
		21, "goat",
		appearanceDesc = "A stubby goat tail sprouts from just above [your] butt."
	),
	RHINO(
		22, "rhino",
		appearanceDesc = "A ropey rhino tail sprouts from just above [your] butt, swishing from time to time."
	),
	ECHIDNA(
		23, "echidna",
		appearanceDesc = "A stumpy echidna tail forms just above [your] [butt]."
	),
	DEER(
		24, "deer",
		appearanceDesc = "A stubby deer tail sprouts from just above [your] butt."
	),
	SALAMANDER(
		25, "salamander",
		isLong = true,
		canTailSlam = true,
		appearanceDesc = "A tapered tail covered in red scales hangs down from just above [your] [butt]. It sways back and forth, improving [your] balance, and [you] can set it ablaze in red-hot fire whenever [you] want."
	),
	KITSHOO(26, "kitshoo"),
	MANTIS_ABDOMEN(
		27, "mantis abdomen",
		appearanceDesc = "A large insectile mantis abdomen dangles from just above [your] backside, bobbing with its own weight as [you] [verb shift]. It is covered in hard [skin coat.color] chitinous material."
	),
	MANTICORE_PUSSYTAIL(
		28, "manticore pussytail",
		appearanceDesc = "[Your] tail is covered in armored chitin from the base to the tip, ending in a flower-like bulb. [You] can open and close [your] tail tip at will, and its pussy-like interior can be used to milk male organs. The deadly set of spikes covering the tip regularly drips with [your] potent venom. When impaling [your] tail spikes in a prey isn’t enough, [you] can fling them at a target with a precision matching a talented archer."
	) {
		override val hasStinger: Boolean
			get() = true
	},
	WOLF(
		29, "wolf",
		isLong = true,
		appearanceDesc = "A bushy [skin coat.color] wolf tail sprouts just above [your] [butt], wagging to and fro whenever [you] [are] happy."
	),
	GARGOYLE(
		30, "mace-shaped gargoyle",
		canTailSlam = true,
		appearanceDesc = "A long spiked tail hangs down from just above [your] [butt]. It sways back and forth, improving [your] balance."
	),
	ORCA(
		31, "orca",
		canTailSlam = true,
		appearanceDesc = "A long, powerful orca tail trails down from [your] backside, swaying to and fro, always ready to propulse [you] through the water or smack an opponent on the head. It has a huge fin at the end, and a smaller one not so far from [your] ass."
	) {
		override val isDraconic: Boolean
			get() = true
	},
	YGGDRASIL(
		32, "yggdrasil",
		appearanceDesc = "A thin, prehensile reptilian tail swings behind [you], covered in [skin coat]. Adorning its tip is a leaf, bobbing with each of [your] tail's movements."
	),
	RAIJU(
		33, "raiju",
		appearanceDesc = "[Your] silky tail extends out from just above [your] [butt]. Its fur is lovely to the touch and glows with lightning at the tip."
	),
	RED_PANDA(
		34, "red-panda",
		appearanceDesc = "Sprouting from [your] [ass] is a long, bushy tail adorned by a beautiful pattern of [skin coat.color] rings. It waves playfully as [you] [verb walk], giving [your] step a mesmerizing touch."
	),
	GARGOYLE_2(
		35, "axe-shaped gargoyle",
		canTailSlam = true,
		appearanceDesc = "A long tail ending with an axe blade on both sides hangs down from just above [your] [butt]. It sways back and forth, improving [your] balance."
	),
	AVIAN(
		36, "avian",
		appearanceDesc = "A fan-like tail made of [skin coat.color] feathers rests above [your] [butt], twitching instinctively to help guide [you] if [you] [were] to take flight."
	),
	GRIFFIN(
		37, "griffin",
		appearanceDesc = "From [your] backside hangs a long tail, leonine in shape and covered mostly by a layer of [skin coat.color2] fur, featuring a tip made of a tuft of [skin coat.color] feathers. It moves sinuously as [you] [verb walk]."
	),
	LION(
		38, "lion",
		isLong = true,
		appearanceDesc = "A soft [skin coat.color] lion tail sprouts just above [your] [butt], curling and twisting with every step to maintain perfect balance. It ends in a small puffy hair."
	),
	BURNING(
		39, "burning",
		isLong = true,
		appearanceDesc = "A blazing cat tail pokes out from [your] [butt]. It has a tendency to light things on fire if [you] [are] not careful, but at least it assists with [your] balance."
	),
	NEKOMATA_FORKED_1_3(
		40, "forked cat",
		appearanceDesc = "A soft [skin coat.color] cat-tail, forked on its one-third length, sprouts just above [your] [butt], curling and twisting with every step to maintain perfect balance."
	),
	NEKOMATA_FORKED_2_3(
		41, "forked cat",
		appearanceDesc = "A soft [skin coat.color] cat-tail, forked on its two-thirds length, sprouts just above [your] [butt], curling and twisting with every step to maintain perfect balance."
	),
	CAVE_WYRM(
		42, "cave wyrm",
		isLong = true,
		canTailSlam = true,
		appearanceDesc = "A large newt tail trails down from [your] [butt], tapering on the ground behind you. While it is heavy and plump, it allows [you] to swim underwater like a fish, if necessary."
	),
	HINEZUMI(
		43, "hinezumi",
		appearanceDesc = "A blazing, [skintone] mouse tail pokes out from [your] [butt]. It has a tendency to light things on fire if [you] [are] not careful."
	),
	THUNDERBIRD(
		44, "thunderbird",
		appearanceDesc = "From just above [your] [butt] extends a long thin sinuous tail, tipped with feathers shaped like a lightning bolt."
	),
	BEAR(
		45, "bear",
		appearanceDesc = "A cute, furry ursan tail sits up from [your] backside."
	),
	TWINKASHA(
		46, "twinkasha",
		appearanceDesc = "A pair blazing cat tail pokes out from [your] [butt]. They have a tendency to light things on fire if [you] [are] not careful, but at least they assist with [your] balance. From these tails [you] [verb draw] in tremendous fell power."
	),
	USHI_ONI(47, "ushi-oni") {
		override fun appearanceDesc(creature: Creature) =
			"[You] [have] an ushi-oni tail whose furred member is " + (if (creature.tallness > 72) "five" else "four") + " feet long and prehensile. Its tip can shoot very thick and strong web strings which double as an aphrodisiac when in contact with the victim."
	},
	WEASEL(
		48, "weasel",
		appearanceDesc = "[Your] short, silky weasel tail extends out from just above [your] [butt]. Its fur is lovely to the touch."
	),
	SQUIRREL(
		49, "squirrel",
		appearanceDesc = "From [your] back sprouts a furry, striped squirrel tail that curves upwards."
	),
	MONKEY(50, "monkey"),
	WENDIGO(
		51, "wendigo",
		appearanceDesc = "[Your] silky tail extends out from just above [your] [butt]. Its fur is lovely to the touch and warm, and it protects [you] well against the cold."
	),
	;

	open val isDraconic get() = false
	open val hasStinger get() = false
	open fun appearanceDesc(creature: Creature) = appearanceDesc

	companion object : CocIdLookup<TailType>(values())
}
