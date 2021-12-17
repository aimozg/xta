package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup
import xta.game.Creature

enum class WingType(
	override val cocID: Int,
	val displayName: String,
	val canFly: Boolean = false,
	val canWingSlap: Boolean = false,
	private val appearanceDesc: String = ""
) : CocId {
	NONE(0, "non-existant"),
	BEE_SMALL(
		1, "small bee-like",
		appearanceDesc = "A pair of tiny-yet-beautiful bee-wings sprout from [your] back, too small to allow [you] to fly."
	),
	BEE_LARGE(
		2, "large bee-like",
		canFly = true,
		appearanceDesc = "A pair of large bee-wings sprout from [your] back, reflecting the light through their clear membranes beautifully. They flap quickly, allowing [you] to easily hover in place or fly."
	),
	HARPY(
		4, "harpy",
		canFly = true,
		canWingSlap = true
	),
	IMP(5, "imp"),
	BAT_LIKE_TINY(
		6, "tiny bat-like",
		appearanceDesc = "A pair of tiny bat-like demon-wings sprout from [your] back, flapping cutely, but otherwise being of little use."
	),
	BAT_LIKE_LARGE(
		7, "large bat-like",
		canFly = true,
		canWingSlap = true,
		appearanceDesc = "A pair of large bat-like demon-wings fold behind [your] shoulders. With a muscle-twitch, [you] can extend them, and use them to soar gracefully through the air."
	),
	SHARK_FIN(8, "shark fin"),
	FEATHERED_LARGE(
		9, "large feathered",
		canFly = true,
		canWingSlap = true,
		appearanceDesc = "A pair of large, feathery wings sprout from [your] back. Though [you] usually [verb keep] the [haircolor]-colored wings folded close, they can unfurl to allow [you] to soar as gracefully as a harpy."
	),
	DRACONIC_SMALL(
		10, "small draconic",
		appearanceDesc = "Small, vestigial wings sprout from [your] shoulders. They might look like bat's wings, but the membranes are covered in fine, delicate scales."
	),
	DRACONIC_LARGE(
		11, "large draconic",
		canFly = true,
		canWingSlap = true,
		appearanceDesc = "Large wings sprout from [your] shoulders. When unfurled they stretch further than [your] arm span, and a single beat of them is all [you] [verb need] to set out toward the sky. They look a bit like bat's wings, but the membranes are covered in fine, delicate scales and a wicked talon juts from the end of each bone."
	),
	GIANT_DRAGONFLY(
		12, "giant dragonfly",
		canFly = true,
		appearanceDesc = "Giant dragonfly wings hang from [your] shoulders. At a whim, [you] could twist them into a whirring rhythm fast enough to lift you off the ground and allow [you] to fly."
	),
	BAT_LIKE_LARGE_2(
		13, "two large pairs of bat-like",
		canFly = true,
		canWingSlap = true,
		appearanceDesc = "Two pairs of large bat-like demon-wings fold behind [your] shoulders. With a muscle-twitch, [you] can extend them, and use them to soar gracefully through the air."
	),
	DRACONIC_HUGE(
		14, "large majestic draconic",
		canFly = true,
		canWingSlap = true,
		appearanceDesc = "Magnificent huge wings sprout from [your] shoulders. When unfurled they stretch over twice further than [your] arm span, and a single beat of them is all [you] [verb need] to set out toward the sky. They look a bit like bat's wings, but the membranes are covered in fine, delicate scales and a wicked talon juts from the end of each bone."
	),
	FEATHERED_PHOENIX(
		15, "phoenix",
		canFly = true,
		canWingSlap = true,
		appearanceDesc = "A pair of large, feathery wings sprout from [your] back. Though [you] usually [verb keep] the crimson-colored wings folded close, they can unfurl to allow [you] to soar as gracefully as a phoenix."
	),
	FEATHERED_ALICORN(
		16, "alicorn",
		canFly = true,
		canWingSlap = true,
		appearanceDesc = "A pair of large, feathery wings sprout from [your] back. Though [you] usually [verb keep] the [haircolor]-colored wings folded close, they can unfurl to allow [you] to soar as gracefully as an alicorn."
	),
	MANTIS_SMALL(
		17, "small mantis-like",
		appearanceDesc = "A pair of tiny mantis-wings sprout from [your] back, too small to allow [you] to fly."
	),
	MANTIS_LARGE(
		18, "large mantis-like",
		canFly = true,
		appearanceDesc = "A pair of large mantis-wings sprout from [your] back, reflecting the light through their clear membranes beautifully. They flap quickly, allowing [you] to easily hover in place or fly."
	),
	MANTIS_LARGE_2(19, "two large pairs of mantis-like"),
	GARGOYLE_LIKE_LARGE(
		20, "large stony",
		canFly = true,
		canWingSlap = true
	) {
		override fun appearanceDescription(creature: Creature) = buildString {
			append(" Large ");
			// TODO gargoyle shape and material
			//if (CoC.instance.flags[kFLAGS.GARGOYLE_BODY_MATERIAL] == 1) desc += "marble";
			//if (CoC.instance.flags[kFLAGS.GARGOYLE_BODY_MATERIAL] == 2) desc += "alabaster";
			append("marble")
			append(" wings sprout from [your] shoulders. When unfurled they stretch wider than [your] arm span and a single beat of them is all you need to set out toward the sky. They look a bit like ")
			//if (CoC.instance.flags[kFLAGS.GARGOYLE_WINGS_TYPE] == 1) desc += "bird";
			//if (CoC.instance.flags[kFLAGS.GARGOYLE_WINGS_TYPE] == 2) desc += "bat";
			append("bird")
			append(" wings and, although they were made of stone, they allow [you] to fly around with excellent aerial agility.")
		}
	},
	PLANT(
		21, "three pairs of cockvines",
		appearanceDesc = "Three pairs of oily, prehensile phalluses sprout from [your] shoulders and back. From afar, they may look like innocent vines, but up close, each tentacle contain a bulbous head with a leaking cum-slit, perfect for mass breeding."
	),
	MANTICORE_SMALL(
		22, "small manticore-like",
		appearanceDesc = "A pair of small leathery wings covered with [skin coat.color] fur rest on [your] back. Despite being too small to allow flight they at least look cute on [you]."
	),
	MANTICORE_LARGE(
		23, "large manticore-like",
		canFly = true,
		canWingSlap = true,
		appearanceDesc = "A pair of large ominous leathery wings covered with [skin coat.color] fur expand from [your] back. [You] can open them wide to soar high in search of your next prey."
	),
	BAT_ARM(24, "bat"),
	VAMPIRE(
		25, "large bat",
		canFly = true,
		canWingSlap = true,
		appearanceDesc = "Between [your] shoulder blades rest a pair of large, ominous black wings reminiscent of a bat’s. They can unfurl up to twice [your] arm’s length, allowing [you] to gracefully dance in the night sky."
	),
	FEY_DRAGON(
		26, "large majestic fey draconic",
		canFly = true,
		canWingSlap = true,
		appearanceDesc = "Magnificent huge wings sprout from [your] shoulders. When unfurled they stretch over twice further than [your] arm span, and a single beat of them is all [you] [verb need] to set out toward the sky. They look a bit like bat's wings, but the membranes are covered in fine, delicate scales and a wicked talon juts from the end of each bone. While draconic in appearance the delicate frame of [your] fey like dragon wings allows for even better speed and maneuverability."
	),
	FEATHERED_AVIAN(
		27, "avian",
		canFly = true,
		canWingSlap = true,
		appearanceDesc = "A pair of large, feathery wings sprout from [your] back. Though [you] usually [verb keep] the [skin coat.color] wings folded close, they can unfurl to allow [you] to soar as gracefully as a bird."
	),
	NIGHTMARE(
		28, "leathery",
		canFly = true,
		canWingSlap = true,
		appearanceDesc = "A pair of large ominous black leathery wings expand from [your] back. [You] can open them wide to soar high in the sky."
	),
	FEATHERED_SPHINX(
		29, "sphinx",
		canFly = true,
		canWingSlap = true,
		appearanceDesc = "A pair of large, feathery wings sprout from [your] back. Though [you] usually [verb keep] the [haircolor]-colored wings folded close, they can unfurl to allow [you] to soar as gracefully as a sphinx."
	),
	ETHEREAL(
		30, "ethereal tendrils",
		canFly = true,
		appearanceDesc = "Three pairs of otherworldly tendrils grow out of [your] back. They have an ethereal glow around them and they gently sway against an invisible wind."
	),
	THUNDEROUS_AURA(
		31, "thunderous aura",
		canFly = true,
		appearanceDesc = "[You] [verb generate] so much electricity that the sound of static and voltage follows [him] around, announcing [his] arrival."
	),
	LEVITATION(
		32, "non-existant",
		canFly = true,
		appearanceDesc = "Although [you] [verb do]n't have wings, [you] [have] the ability to levitate in the air should [you] [verb wish] to, though [you] usually [verb prefer] to stay at ground level."
	),
	DEVILFEATHER(
		33, "devil",
		canFly = true,
		appearanceDesc = "A pair of large ominous black feathered wings expand from [your] back. [You] can open them wide to soar high in the sky."
	),
	FAIRY(
		34, "fairy",
		canFly = true,
		appearanceDesc = "A large pair of colorful butterfly wings rest on [your] shoulder blades. [Your] fairy wings give [you] a regal appearance fit for fey royalty."
	),
	WINDY_AURA(
		35, "windy aura",
		canFly = true,
		appearanceDesc = "An aura of strong wind constantly seems to accompany [you] wherever [he] [verb go]. [You] can use it to create tornados and even take flight riding on a dust devil should [you] need to."
	),
	SEA_DRAGON(
		36, "large majestic aquatic",
		canFly = true,
		canWingSlap = true,
		appearanceDesc = "Two large majestic webbed wings not unlike the aquatic flippers of a deep-sea creature unfurls from [your] back, the interior lined with bright color patterns and bioluminescent specks that change depending on [your] mood. These wings are as good to fly as they are to swim."
	),
	;

	open fun appearanceDescription(creature: Creature) = appearanceDesc

	companion object : CocIdLookup<WingType>(values())
}
