package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup
import xta.game.Creature

enum class EyeType(
	override val cocID: Int,
	val displayName: String,
	private val appearanceDesc: String = "",
	val hasDarkvision: Boolean = false
): CocId {
	HUMAN(0, "human"),
	SPIDER(
		1, "six spider",
		"In addition to [your] primary two [eyecolor] eyes, [you] [have] a second, smaller pair on [your] forehead.",
		hasDarkvision = true
	),
	BLACK_EYES_SAND_TRAP(
		2, "sandtrap",
		"[Your] eyes are solid spheres of inky, alien darkness."
	),
	CAT(
		3, "cat",
		"[Your] [eyecolor] eyes have vertically slit like those of cat.",
		hasDarkvision = true
	),
	GORGON(
		4, "snake",
		"[Your] [eyecolor] eyes are similar to those of snake-like gorgons with ability to temporally petrify.",
		hasDarkvision = true
	),
	FENRIR(
		5, "fenrir",
		"[Your] [eyecolor] eyes glow with a freezing blue light, and wisps of icy mist drift from them.",
		hasDarkvision = true
	),
	MANTICORE(
		6, "manticore",
		"[Your] eyes are similar to those of a cat, with slit pupils. However, their [eyecolor] iris dismiss any links to the regular felines in favor of something way more ominous.",
		hasDarkvision = true
	),
	FOX(
		7, "fox",
		"[Your] [eyecolor] eyes look like those of a fox with a slit in the middle."
	),
	LIZARD(
		8, "lizard",
		"[Your] eyes looks like those of a reptile with [eyecolor] irises and a slit."
	),
	SNAKE(
		9, "snake",
		"[Your] [eyecolor] eyes have slitted pupils like that of a snake."
	),
	DRACONIC(
		10, "draconic",
		"[Your] [eyecolor] eyes have slitted pupils like that of a dragon.",
		hasDarkvision = true
	),
	DEVIL(
		11, "devil",
		"[Your] eyes look fiendish, with their black sclera and glowing [eyecolor] iris.",
		hasDarkvision = true
	),
	ONI(
		12, "oni",
		"[Your] eyes look normal enough save for their fiendish [eyecolor] iris and slitted pupils."
	),
	ELF(
		13, "elf",
		hasDarkvision = true
	) {
		override fun appearanceDescription(creature: Creature): String {
			val desc =
				" [Your] [eyecolor] elven eyes looks somewhat human, save for their cat-like vertical slit which draws light right in, allowing [you] to see with perfect precision both at day and night time."

			/* TODO
			if (creature.hasPerk(PerkLib.BlessingOfTheAncestorTree)) desc += " Despite their otherwordly beauty, the misty hollow at the center of [your] eyes denounce the absence of a soul, which now resides with the forest."

			 */

			return desc
		}
	},
	RAIJU(
		14, "raiju",
		"[Your] eyes are of an electric [eyecolor] hue that constantly glows with voltage power. They have slitted pupils like those of a beast."
	),
	VAMPIRE(
		15, "vampire",
		"[Your] eyes looks somewhat normal, but their [eyecolor] irises seem to have the tendency of drawing in people’s gaze, like moths to a flame.",
		hasDarkvision = true
	),
	GEMSTONES(
		16, "gemstone",
		"Instead of regular eyes [you] [verb see] through a pair of gemstones that change hue based on [your] mood.",
		hasDarkvision = true
	),
	FERAL(
		17, "feral",
		"In [your] [eyecolor] eyes, sometimes a green light is visibly dancing. Hinting at the beast within. It encompasses [your] entire pupil when [you] [verb let] it loose.",
		hasDarkvision = true
	),
	GRYPHON(
		18, "gryphon",
		"[Your] gifted eyes have a bird-like appearance, having an [eyecolor] sclera and a large, black iris. A thin ring of black separates [your] sclera from yo[ur outer iris."
	),
	INFERNAL(
		19, "infernal",
		"[Your] eyes look fiendish, with their black sclera and glowing [eyecolor] iris. What's more, a small trail of fire blazes on the corners making them all the more intimidating.",
		hasDarkvision = true
	),
	ORC(
		20, "orc",
		"[Your] [eyecolor], slanted eyes have slitted pupils. They usually seem to gleam with a sort of malice, avarice, or lechery, giving off beady-eyed vibe."
	),
	CAVE_WYRM(
		21, "cave wyrm",
		"Dark blue iris with [eyecolor] eyes pupil that glow in the dark.",
		hasDarkvision = true
	),
	HINEZUMI(
		22, "hinezumi",
		"[Your] eyes are human-like. However, their [eyecolor] irises are clearly those of an Hinezumi."
	),
	BEAR(
		23, "bear",
		"[Your] eyes are human save for your [eyecolor] pupils closer to those of a bear."
	),
	DISPLACER(
		24, "displacer",
		"Your eyes are similar to those of a cat, with slit pupils. However, their black sclera dismiss any links to the regular felines clearly identifying you to something else.",
		hasDarkvision = true
	),
	FIRE_SNAIL(
		25, "fire snail",
		"[Your] eyes are normal save for your [eyecolor] iris which glows like smoldering embers."
	),
	GHOST(
		26, "ghost",
		"[Your] eyes glow [eyecolor] with a ghostly aura. [You] can see clearly in the shadows and might terrify anyone wandering around alone at night.",
		hasDarkvision = true
	),
	JIANGSHI(
		27, "jiangshi",
		"[Your] [eyecolor] eyes, while humans are vacant and devoid of the warmth of life.",
		hasDarkvision = true
	),
	GOAT(
		28, "goat",
		"[Your] eyes are like those of a goat with horizontal slit pupils at the center of their [eyecolor] iris."
	),
	CENTIPEDE(29, "centipede"),
	KRAKEN(
		30, "kraken",
		"[Your] eyes are like those of a octopus with horizontal slit pupils at the center of their [eyecolor] iris.",
		hasDarkvision = true
	),
	FROSTWYRM(
		31, "frost wyrm",
		"[Your] [eyecolor] eyes have slitted pupils and dark sclera like those of a frost wyrm.",
		hasDarkvision = true
	),
	CANCER(
		32, "cancer",
		"[Your] eyes look human at first glance, save for their natural [eyecolor] irises covered by a completely transparent layer of protective tissue to protect them against things such as salt and sand in the water."
	),
	FAIRY(
		33, "fairy",
		"[Your] beautiful [eyecolor] eyes sparkle with the eternal hope and child-like innocence of fairykind."
	),
	GREMLIN(
		34, "gremlin",
		"[Your] [eyecolor] eyes looks human enough though your eyelids are dark, just as if [you] very tired althought it's more likely traces of [your] demonic corruption."
	),
	WEASEL(
		35, "weasel",
		"[Your] [eyecolor] eyes have slitted pupils like those of a weasel."
	),
	MONOEYE(
		36, "monoeye",
		"[Your] eye sockets have merged together to reform into a single cyclopean eye charged with powerful magical powers. [Your] almighty gaze is as peerless as its unsettling.",
		hasDarkvision = true
	),
	RATATOSKR(37, "ratatoskr",
		"[Your] [eyecolor] eyes have slitted pupils like those of a Ratatoskr. [Your] know it all smug expression plastered in them at all time upsets quite a few."
	),
	FIENDISH(
		38, "fiendish",
		"[Your] [eyecolor] eyes looks like those of a fiend with a slit in the middle.",
		hasDarkvision = true
	),
	DEAD(39, "dead",
		"[Your] eyes look dead, but the pupils glow in the dark with a [eyecolor].",
		hasDarkvision = true
	),
	MINDBREAKER(40, "eldritch",
		"In [your] hair nests many [eyecolor] eyes, granting [you] a nearly full peripheral vision. The biggest eye rests on your forehead. It is from this eye that [you] [verb use] [your] formidable mental powers.",
		hasDarkvision = true
	),
	MINDBREAKERMALE(41, "eldritch male",
		"[You] [have] three two [eyecolor], predatory eyes at the front of [your] head, giving [him] full sight and perfect perception of what’s in front of [him]. The eye along [your] forehead is considerably larger, it is where [he] [verb channel] [his] formidable mental powers.",
		hasDarkvision = true
	)
	;

	open fun appearanceDescription(creature: Creature): String = appearanceDesc

	companion object: CocIdLookup<EyeType>(values())
}
