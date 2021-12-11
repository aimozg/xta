package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup
import xta.game.Creature
import xta.utils.either
import xta.utils.fxrng

/*
 * Created by aimozg on 28.11.2021.
 */
enum class HairType(
	override val cocID: Int,
	val displayName: String,
	val ignoresStyle: Boolean = false
) : CocId {
	NORMAL(0, "normal") {
		override fun appearanceDescription(creature: Creature) =
			"Although not particularly remarkable, your ${hairInfo(creature)} looks good on you, accentuating your features well."
	},
	FEATHER(1, "feather", ignoresStyle = true) {
		override fun appearanceDescription(creature: Creature) =
			"Your ${hairInfo(creature)} is made completely out of feathers rather than actual strands."

		override fun shortDesc(creature: Creature) =
			"feather-${hairNoun(creature)}"
	},
	GHOST(2, "ghost") {
		override fun appearanceDescription(creature: Creature) =
			"Although your ${hairInfo(creature)} technically has the features of normal human hair, it is also completely transparent."

		override fun shortDesc(creature: Creature) =
			"transparent ${hairNoun(creature)}"

		override fun beardDesc(creature: Creature) =
			"transparent "
	},
	GOO(3, "goopy") {
		override fun appearanceDescription(creature: Creature) =
			"Atop your head is a gooey ${hairInfo(creature)}, more like an amorphous blob imitating the familiar shape than the real deal."

		override fun shortDesc(creature: Creature) =
			"goo-${hairNoun(creature)}"

		override fun beardDesc(creature: Creature) =
			"gooey "
	},
	ANEMONE(4, "tentacle", ignoresStyle = true) {
		override fun appearanceDescription(creature: Creature) =
			"Your ${hairInfo(creature)} is in truth made out of anemone tentacles, only vaguely resembling the real deal."

		override fun shortDesc(creature: Creature) =
			"tentacle-${hairNoun(creature)}"

		override fun beardDesc(creature: Creature) =
			"tentacley "
	},
	QUILL(5, "quill", ignoresStyle = true) {
		override fun appearanceDescription(creature: Creature) =
			"Your ${hairInfo(creature)} is made completely out of quills rather than actual strands."

		override fun shortDesc(creature: Creature) =
			"quill-${hairNoun(creature)}"
	},
	GORGON(6, "snake-like", ignoresStyle = true) {
		override fun appearanceDescription(creature: Creature) =
			"Atop your head is technically ${hairInfo(creature)}, if one were to ignore that it is made of snakes rather than actual hair."

		override fun shortDesc(creature: Creature) =
			"snakes-${hairNoun(creature)}"
	},
	LEAF(7, "leaf") {
		override fun appearanceDescription(creature: Creature) =
			"Considering how your ${hairInfo(creature)} is made completely out of leaves, you seem to have some sort of connection to nature."

		override fun shortDesc(creature: Creature) =
			"leaf-${hairNoun(creature)}"

		override fun beardDesc(creature: Creature) =
			"moss "
	},
	FLUFFY(8, "fluffy", ignoresStyle = true) {
		override fun appearanceDescription(creature: Creature) =
			"You have a white pillowy ${hairNoun(creature)}, very much wooly to the touch and constantly trying to lull you to sleep anytime you lie against a surface."

		override fun shortDesc(creature: Creature) =
			"fluffy ${hairNoun(creature)}"
	},
	GRASS(9, "grass") {
		override fun appearanceDescription(creature: Creature) =
			"Rather than normal strands, your ${hairInfo(creature)} is actually made entirely of grass, like some sort of nature spirit's."

		override fun shortDesc(creature: Creature) =
			"grass-${hairNoun(creature)}"
	},
	SILKEN(10, "silky") {
		override fun appearanceDescription(creature: Creature) =
			"Your ${hairInfo(creature)} is extremely glossy and smooth, its elvish features so perfect by human standards to the point of feeling unnatural."

		override fun shortDesc(creature: Creature) =
			"elven ${hairNoun(creature)}"
	},
	STORM(11, "glowing lightning-shaped") {
		override fun appearanceDescription(creature: Creature) =
			"Your ${hairInfo(creature)}'s tips end in glowing lightning-shaped locks, crackling with electricity whenever you will them to."

		override fun shortDesc(creature: Creature) =
			"lightning ${hairNoun(creature)}"
	},
	BURNING(12, "burning") {
		override fun appearanceDescription(creature: Creature) =
			"Your ${hairInfo(creature)} has its tips overtaken by a magic flame, burning anything you wish it to and nothing more."

		override fun shortDesc(creature: Creature) =
			"burning hair"
	},
	SNOWY(13, "snowy") {
		override fun appearanceDescription(creature: Creature) = buildString {
			append("Your ${hairInfo(creature)} is human in appearance, but cold to the touch and regularly used as a nest by snow flurries")
			if (creature.rearBody.type == RearBodyType.GLACIAL_AURA) {
				append(", an effect amplified by your bone-chilling aura")
			}
			append(".")
		}

		override fun shortDesc(creature: Creature) =
			"snowy ${hairNoun(creature)}"
	},
	FAIRY(14, "otherworldly silky and almost translucent") {
		override fun appearanceDescription(creature: Creature) =
			"Despite looking almost human, your ${hairInfo(creature)} is otherworldly smooth and almost translucent, its fairy traits easy to notice if not identify."

		override fun shortDesc(creature: Creature) =
			"silky fairy ${hairNoun(creature)}"
	},
	CRAZY(15, "crazy", ignoresStyle = true) {
		override fun appearanceDescription(creature: Creature) =
			"Your wild ${hairInfo(creature)} is positively crazy, with spiked tips pointing outwards to your sides."

		override fun shortDesc(creature: Creature) =
			"crazy ${hairNoun(creature)}"
	},
	WINDSWEPT(16, "windswept") {
		override fun appearanceDescription(creature: Creature) =
			"Your ${hairInfo(creature)} is quite aerodynamic, shaped as to avoid encumbering you even in a windstorm."
	},
	RATATOSKR(17, "striped") {
		override fun appearanceDescription(creature: Creature) =
			"Though your ${hairInfo(creature)} could almost pass for human, it's striped at the center with light tips not unlike the head of a chipmunk."

		override fun shortDesc(creature: Creature) =
			"striped ${hairNoun(creature)}"
	},
	PRISMATIC(18, "prismatic") {
		override fun appearanceDescription(creature: Creature) =
			"Although your ${hairInfo(creature)} looks mostly human, that notion is quickly discarded by its prismatic strands, varying in hue along their length to display all the colors of a rainbow."

		override fun shortDesc(creature: Creature) =
			"prismatic ${hairNoun(creature)}"
	},
	MINDBREAKER(19, "mindbreaker") {
		override fun appearanceDescription(creature: Creature) =
			"Your ${hairInfo(creature)} is made out of tentacles, only vaguely resembling the real deal."

		override fun shortDesc(creature: Creature) =
			"slimy tentacle ${hairNoun(creature)}"
	},
	MINDBREAKERMALE(20, "mindbreaker") {
		override fun appearanceDescription(creature: Creature) =
			"Smaller webbings frill along the top of your head, forming a crown-like ring. Along these frills are longer hair-like tentacles that reach down to drape past your shoulders, just above your pectorals."

		override fun shortDesc(creature: Creature) =
			"slimy tentacle ${hairNoun(creature)}"
	},
	;

	abstract fun appearanceDescription(creature: Creature): String

	open fun shortDesc(creature: Creature): String = hairNoun(creature)

	open fun beardDesc(creature: Creature): String = ""

	//If furry and longish hair sometimes call it a mane (50%)
	open fun hairNoun(creature: Creature): String =
		if (creature.hasFur() && creature.hairLength > 3 && fxrng.nextInt(2) == 0) "mane"
		else "hair"

	protected open fun hairInfo(creature: Creature): String {
		val hair = hairNoun(creature)

		return (if (ignoresStyle) "" else creature.hairStyle.adjective + " ") +
				getHairLength(creature) + " " + creature.hairColor + " " + hair

	}

	protected fun getHairLength(creature: Creature): String {
		val hairScale = ((creature.hairLength / creature.tallness) * 100).toInt() / 100.0
		return when {
			hairScale == 0.0 ->
				fxrng.either("shaved", "bald", "smooth", "hairless", "glabrous")
			hairScale <= 0.05 ->
				fxrng.either("close-cropped", "trim", "very short")
			hairScale <= 0.1 ->
				"short"
			hairScale <= 0.14 ->
				"shaggy"
			hairScale <= 0.17 ->
				"moderately long"
			hairScale <= 0.2 ->
				fxrng.either("long", "shoulder-length")
			hairScale <= 0.25 ->
				"very long"
			hairScale <= 0.4 ->
				"back-covering"
			hairScale <= 0.5 ->
				"ass-length"
			hairScale <= 1.0 ->
				"obscenely long"
			else ->
				"floor-length"
		}
	}

	companion object : CocIdLookup<HairType>(values())
}
