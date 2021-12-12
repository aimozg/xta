package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup
import xta.game.Creature
import xta.text.displayInches
import xta.text.num2text

enum class HornType(
	override val cocID: Int,
	val displayName: String,
	private val appearanceDesc: String = ""
) : CocId {
	NONE(0, "non-existant"),
	DEMON(1, "demon") {
		override fun appearanceDescription(creature: Creature) = when {
			creature.horns.count == 2 ->
				"A small pair of pointed horns has broken through the [skin.type] on [your] forehead, proclaiming some demonic taint to any who see them."
			creature.horns.count == 4 ->
				"A quartet of prominent horns has broken through [your] [skin.type]. The back pair are longer, and curve back along [your] head. The front pair protrude forward demonically."
			creature.horns.count == 6 ->
				"Six horns have sprouted through [your] [skin.type], the back two pairs curve backwards over [your] head and down towards [your] neck, while the front two horns stand almost eight inches long upwards and a little forward."
			creature.horns.count >= 8 ->
				"A large number of thick demonic horns sprout through [your] [skin.type], each pair sprouting behind the ones before. The front jut forwards nearly ten inches while the rest curve back over [your] head, some of the points ending just below [your] ears. You estimate [you] have a total of " + num2text(
					creature.horns.count
				) + " horns."
			else -> ""
		}
	},
	COW_MINOTAUR(2, "cow") {
		override fun appearanceDescription(creature: Creature) = when {
			creature.horns.count < 3 ->
				"Two tiny horns-like nubs protrude from [your] forehead, resembling the horns of the young livestock kept by your village."
			creature.horns.count < 6 ->
				"Two moderately sized horns grow from [your] forehead, similar in size to those on a young bovine."
			creature.horns.count < 12 ->
				"Two large horns sprout from [your] forehead, curving forwards like those of a bull."
			creature.horns.count < 20 ->
				"Two very large and dangerous looking horns sprout from [your] head, curving forward and over a foot long. They have dangerous looking points."
			else ->
				"Two huge horns erupt from [your] forehead, curving outward at first, then forwards. The weight of them is heavy, and they end in dangerous looking points."
		}
	},
	DRACONIC_X2(3, "2 draconic") {
		override fun appearanceDescription(creature: Creature) =
			"A pair of " + creature.horns.count.displayInches() + " horns grow from the sides of [your] head, sweeping backwards and adding to [your] imposing visage."
	},
	DRACONIC_X4_12_INCH_LONG(
		4, "four 12\" long draconic",
		appearanceDesc = "Two pairs of horns, roughly a foot long, sprout from the sides of [your] head. They sweep back and give [you] a fearsome look, almost like the dragons from your village's legends."
	),
	ANTLERS(5, "deer") {
		override fun appearanceDescription(creature: Creature) =
			if (creature.horns.count > 0)
				"Two antlers, forking into " + num2text(creature.horns.count) + " points, have sprouted from the top of [your] head, forming a spiky, regal crown of bone."
			else ""
	},
	GOAT(6, "goat") {
		override fun appearanceDescription(creature: Creature) =
			if (creature.horns.count == 1)
				"A pair of stubby goat horns sprout from the sides of [your] head."
			else
				"A pair of tall-standing goat horns sprout from the sides of [your] head. They are curved and patterned with ridges."
	},
	UNICORN(7, "unicorn") {
		override fun appearanceDescription(creature: Creature) = when {
			creature.horns.count < 3 ->
				"Tiny horn-like nub protrudes from [your] forehead, resembling the horns of the young unicorn."
			creature.horns.count < 6 ->
				"One moderately sized horn grows from [your] forehead, similar in size to that on a young unicorn."
			creature.horns.count < 12 ->
				"One large horn sprouts from [your] forehead, spiraling and pointing forwards like that of an unicorn."
			creature.horns.count < 20 ->
				"One very large and dangerous looking spiraling horn sprouts from [your] forehead, pointing forward and over a foot long. It have dangerous looking tip."
			else ->
				"One huge and long spiraling horn erupts from [your] forehead, pointing forward. The weight of it is heavy and it ends with dangerous and sharp looking tip."
		}
	},
	RHINO(8, "rhino") {
		override fun appearanceDescription(creature: Creature) = buildString {
			if (creature.horns.count >= 2) {
				if (creature.faceType == FaceType.RHINO) append("A second horn sprouts from [your] forehead just above the horns on [your] nose.")
				else append("A single horns sprout from [your] forehead. It is conical and resembles a rhino's horn.")
				append(" You estimate it to be about seven inches long.")
			} else {
				append("A single horn sprouts from [your] forehead. It is conical and resembles a rhino's horn. You estimate it to be about six inches long.")
			}
		}
	},
	OAK(9, "oak") {
		override fun appearanceDescription(creature: Creature) =
			if (creature.horns.count > 0) "Two branches, forking into " + num2text(creature.horns.count) + " points, have sprouted from the top of your [head], forming a spiky, regal crown made of oak wood."
			else ""
	},
	GARGOYLE(
		10, "gargoyle",
		appearanceDesc = "A large pair of thick demonic looking horns sprout through the side of [your] head giving [you] a fiendish appearance."
	),
	ORCHID(
		11, "orchid",
		appearanceDesc = "A huge pair of [skin coat.color] orchids grows on each side of [your] head, their big long petals flopping gaily when [you] [verb move]."
	),
	ONI_X2(
		12, "2 oni",
		appearanceDesc = "[You] [have] a pair of horns on [your] head warning anyone who looks that [you] [are] an oni and do mean serious business."
	),
	ONI(
		13, "1 oni",
		appearanceDesc = "[You] [have] a single horn on [your] head warning anyone who looks that [you] [are] an oni and do mean serious business."
	),
	BICORN(14, "bicorn") {
		override fun appearanceDescription(creature: Creature) = when {
			creature.horns.count < 3 ->
				"A pair of tiny horns-like nub protrude from [your] forehead, resembling the horns of the young bicorn."
			creature.horns.count < 6 ->
				"Two moderately sized horns grow from [your] forehead, similar in size to those on a young bicorn."
			creature.horns.count < 12 ->
				"Two large horns sprout from [your] forehead, spiraling and pointing forwards like those of a bicorn."
			creature.horns.count < 20 ->
				"Two very large and dangerous looking spiraling horns sprout from [your] forehead, pointing forward and over a foot long. They have dangerous looking tip."
			else ->
				"Two huge and long spiraling horns erupt from [your] forehead, pointing forward. The weight of them is heavy and they end with dangerous and sharp looking tips."
		}
	},
	GHOSTLY_WISPS(
		15, "ghostly wisps",
		appearanceDesc = "Floating above [your] head is several wispy balls of light. They hold an unsettling ethereal presence around them, though in reality they’re merely an extension of [yourself]."
	),
	SPELL_TAG(
		16, "spell tag",
		appearanceDesc = "On [your] forehead is a cursed spell tag, the source of both [your] current predicament and [your] supernatural powers."
	),
	GOATQUAD(
		17, "4 goat",
		appearanceDesc = "Four tall-standing goat horns sprout from the sides of [your] head denouncing your fiendish nature. They are curved and patterned with ridges."
	),
	KRAKEN(
		18, "kraken",
		appearanceDesc = "Crowning above [your] head is a fleshy Kraken hood easily adding [you] a few extra inches in height."
	),
	FROSTWYRM(19, "frostwyrm") {
		override fun appearanceDescription(creature: Creature) = buildString {
			append("Two large sized spiraling horns grow from the side of [your] head, similar to those of a ram or frost wyrm. They kind of look great")
			if (creature.lowerBody.type == LowerBodyType.FROSTWYRM) {
				append(", especially with [your] fur, which makes [you] look like a sheep serpentine dragon")
			}
			append(".")
		}
	},
	USHI_ONI(
		20, "ushi-oni",
		appearanceDesc = "[You] [have] a pair of ushi-oni horns, both come out of [your] skull and go upward in a spiral pattern."
	),
	SEA_DRAGON(
		21, "sea dragon",
		appearanceDesc = "Two large-sized horns grow from the side of [your] head. The faint bioluminescent specks that line the length of each horn enhance with a mesmerizing glow. At the tip of each horn is a bright red glow, both as a gentle warning and an enthralling lure to unwary prey."
	),
	;

	open fun appearanceDescription(creature: Creature) = appearanceDesc

	companion object : CocIdLookup<HornType>(values())
}
