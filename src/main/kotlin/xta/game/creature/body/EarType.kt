package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup
import xta.game.Creature

enum class EarType(
	override val cocID: Int,
	val displayName: String,
	private val appearanceDesc: String = ""
) : CocId {
	HUMAN(
		0, "human",
		appearanceDesc = "A pair of normal human ears stick out from the sides of [your] head."
	),
	HORSE(
		1, "horse",
		appearanceDesc = "A pair of horse-like ears rise up from the top of [your] head."
	),
	DOG(
		2, "dog",
		appearanceDesc = "A pair of dog ears protrude from [your] head, flopping down adorably."
	),
	COW(
		3, "cow",
		appearanceDesc = "A pair of round, [haircolor] floppy cow ears protrude from the sides of [your] head."
	),
	ELFIN(
		4, "elfin",
		appearanceDesc = "A pair of large pointy ears stick out from [your] head."
	),
	CAT(
		5, "cat",
		appearanceDesc = "A pair of cute, fuzzy cat ears have sprouted from the top of [your] head."
	),
	LIZARD(
		6, "lizard",
		appearanceDesc = "A pair of rounded protrusions with small holes on the sides of [your] head serve as [your] ears."
	),
	BUNNY(
		7, "bunny",
		appearanceDesc = "A pair of floppy rabbit ears stick up from the top of [your] head, flopping around as [you] [verb walk]."
	),
	KANGAROO(8, "kangaroo"),
	FOX(
		9, "fox",
		appearanceDesc = "A pair of large, adept fox ears sit high on [your] head, always listening."
	),
	DRAGON(
		10, "dragon",
		appearanceDesc = "A pair of rounded protrusions with small holes on the sides of [your] head serve as [your] ears. Bony fins sprout behind them."
	),
	RACCOON(
		11, "raccoon",
		appearanceDesc = "A pair of vaguely egg-shaped, furry raccoon ears adorns [your] head."
	),
	MOUSE(
		12, "mouse",
		appearanceDesc = "A pair of large, dish-shaped mouse ears tops [your] head."
	),
	FERRET(
		13, "ferret",
		appearanceDesc = "A pair of small, rounded ferret ears sit on top of [your] head."
	),
	PIG(
		14, "pig",
		appearanceDesc = "A pair of pointy, floppy pig ears have sprouted from the top of [your] head."
	),
	RHINO(
		15, "rhino",
		appearanceDesc = "A pair of open tubular rhino ears protrude from [your] head."
	),
	ECHIDNA(
		16, "echidna",
		appearanceDesc = "A pair of small rounded openings appear on your head that are [your] ears."
	),
	DEER(
		17, "deer",
		appearanceDesc = "A pair of deer-like ears rise up from the top of [your] head."
	),
	WOLF(
		18, "wolf",
		appearanceDesc = "A pair of pointed wolf ears rise up from the top of [your] head."
	),
	LION(
		19, "lion",
		appearanceDesc = "A pair of lion ears have sprouted from the top of [your] head."
	),
	YETI(
		20, "yeti",
		appearanceDesc = "A pair of yeti ears, bigger than your old human ones have sprouted from the top of [your] head."
	),
	ORCA(
		21, "orca",
		appearanceDesc = "A pair of very large fin at least twice as large as [your] head which help [you] orient [yourself] underwater have sprouted from the top of [your] head. Their underside is [skin color2] while the top is [skin color]."
	),
	ORCA2(
		22, "orca",
		appearanceDesc = "A pair of rounded protrusions with small holes on the sides of [your] head serve as [your] ears."
	),
	SNAKE(
		23, "snake",
		appearanceDesc = "A pair of large pointy ears covered in small scales stick out from [your] head."
	),
	GOAT(
		24, "goat",
		appearanceDesc = "A pair of ears look similar to those of a goat, flapping from time to time in response to sounds."
	),
	ONI(
		25, "oni",
		appearanceDesc = "A pair of pointed elf-like oni ears stick out from [your] head."
	),
	ELVEN(
		26, "elven",
		appearanceDesc = "A pair of cute, long, elven, pointy ears, bigger than [your] old human ones and alert to every sound stick out from [your] head."
	),
	RAIJU(
		27, "raiju",
		appearanceDesc = "A pair of sideways leaning raiju ears stick out from [your] head, flicking toward every slight sound."
	),
	BAT(
		28, "bat",
		appearanceDesc = "A pair of bat ears sit atop [your] head, always perked up to catch any stray sound."
	),
	VAMPIRE(
		29, "vampire",
		appearanceDesc = "A pair of pointed elfin ears powerful enough to catch even the heartbeat of those around you stick out from [your] head."
	),
	RED_PANDA(
		30, "red-panda",
		appearanceDesc = "Big, white furred, red-panda ears lie atop [your] head, keeping [you] well aware to your surroundings."
	),
	AVIAN(
		31, "avian",
		appearanceDesc = "Two small holes at each side of [your] head serve [you] as ears. Hidden by tufts of feathers, they’re almost unnoticeable."
	),
	GRYPHON(
		32, "gryphon",
		appearanceDesc = "A duo of triangular, streamlined ears are located at each side of [your] head, helping [you] to pinpoint sounds. They’re covered in soft, [skin coat.color] fur and end in tufts."
	),
	CAVE_WYRM(
		33, "cave wyrm",
		appearanceDesc = "[Your] ears are furry yet they do not actually belong to any known type of mammal. You suspect them to be related to an ancestry closer to that of the serpentine dragons they emulate."
	),
	BEAR(
		34, "bear",
		appearanceDesc = "A pair of two round fuzzy bear ears covered with [skin coat.color] and alert to sound stick out from your head."
	),
	PANDA(
		35, "panda",
		appearanceDesc = "A pair of two round fuzzy panda ears covered with black fur just like a panda and alert to sound stick out from [your] head."
	),
	SHARK(
		36, "shark",
		appearanceDesc = "A pair of fin like ears with fins stick out from [your] head. They allow [you] to hear every sound with perfect clarity while underwater."
	),
	DISPLACER(
		37, "displacer",
		appearanceDesc = "A large long furry ears atop [your] head, always perked up to catch any stray sound."
	),
	MELKIE(
		38, "melkie",
		appearanceDesc = "[Your] furry Melkie ears are long and flat, reaching all the way down to [your] waist."
	),
	GREMLIN(39, "gremlin") {
		override fun appearanceDescription(creature: Creature) = buildString {
			append("A pair of sideways leaning furry ears that flick toward every slight sound. They don’t belong to any known animal but like anything demonic related help improve your devilish charm.")
			// TODO silly mode
			// " With ears like that, anyone would be hard-pressed to resist the urge to headpat [you]."
		}
	},
	WEASEL(
		40, "weasel",
		appearanceDesc = "A pair of two round fuzzy weasel ears covered with [skin coat.color] and alert to sound stick out from [your] head."
	),
	SQUIRREL(
		41, "squirrel",
		appearanceDesc = "A pair of sideways round squirrel ears that flick toward every slight sound."
	);

	open fun appearanceDescription(creature: Creature): String =
		appearanceDesc

	companion object : CocIdLookup<EarType>(values())
}
