package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup
import xta.game.Creature

enum class FaceType(
	override val cocID: Int,
	val displayName: String,
	private val appearanceDesc: String = "",
	val isHumanShaped: Boolean = false,
	val hasMuzzle: Boolean = false,
	val canBite: Boolean = false
) : CocId {
	HUMAN(0, "human", isHumanShaped = true),
	HORSE(1, "horse", hasMuzzle = true) {
		override fun appearanceDescription(creature: Creature) = when {
			!creature.hasCoat() ->
				"[Your] face is equine in shape and structure. The odd visage is hairless and covered with [skin base]"
			creature.hasFullCoatOfType(SkinCoatType.FUR) ->
				"[Your] face is almost entirely equine in appearance, even having [skin coat]. Underneath the fur, you believe [you] [verb have] [skin base]."
			else ->
				"[You] [verb have] the face and head structure of a horse, overlaid with glittering [skin coat]."
		}
	},
	DOG(2, "dog", hasMuzzle = true, canBite = true) {
		override fun appearanceDescription(creature: Creature) = when {
			!creature.hasCoat() ->
				"[You] [verb have] a dog-like face, complete with a wet nose. The odd visage is hairless and covered with [skin base]."
			creature.hasFullCoatOfType(SkinCoatType.FUR) ->
				"[You] [verb have] a dog's face, complete with wet nose and panting tongue. [Youve] got [skin coat], hiding [your] [skin base] underneath [your] furry visage."
			else ->
				"[You] [verb have] the facial structure of a dog, wet nose and all, but overlaid with glittering patches of [skin coat]"
		}
	},
	COW_MINOTAUR(3, "cow") {
		override fun appearanceDescription(creature: Creature) = when {
			!creature.hasCoat() ->
				"[You] [verb have] a face resembling that of a minotaur, with cow-like features, particularly a squared off wet nose. Despite [your] lack of fur elsewhere, [your] visage does have a short layer of [haircolor] fuzz."
			creature.hasFullCoatOfType(SkinCoatType.FUR) ->
				"[You] [verb have] a face resembling that of a minotaur, with cow-like features, particularly a squared off wet nose. [Your] [skin coat] thickens noticeably on [your] head, looking shaggy and more than a little monstrous once laid over [your] visage."
			creature.hasFullCoat() ->
				"[Your] face resembles a minotaur's, though strangely it is covered in shimmering [skin coat], right up to the flat cow-like nose that protrudes from [your] face."
			else ->
				"[Your] face resembles a minotaur's, though strangely it is covered small patches of shimmering [skin coat], right up to the flat cow-like nose that protrudes from [your] face."
		}
	},
	SHARK_TEETH(
		4, "shark", isHumanShaped = true, canBite = true,
		appearanceDesc = "A set of razor-sharp, retractable shark-teeth fill [your] mouth and gives [your] visage a slightly angular appearance."
	),
	SNAKE_FANGS(
		5, "snake", isHumanShaped = true, canBite = true,
		appearanceDesc = "A pair of fangs hang over [your] lower lip, dripping with venom."
	),
	CAT(6, "cat", canBite = true, hasMuzzle = true) {
		override fun appearanceDescription(creature: Creature) = when {
			!creature.hasCoat() ->
				"[You] [verb have] a cat-like face, complete with a cute, moist nose and whiskers. The [skin] that is revealed by [your] lack of fur looks quite unusual on so feline a face."
			creature.hasFullCoatOfType(SkinCoatType.FUR) ->
				"[You] [verb have] a cat-like face, complete with moist nose and whiskers. [Your] [skin coat.nocolor] is [skin coat.color], hiding [your] [skin base] underneath."
			else ->
				"[Your] facial structure blends humanoid features with those of a cat. A moist nose and whiskers are included, but overlaid with glittering patches of [skin coat]."
		}
	},
	LIZARD(7, "lizard", canBite = true, hasMuzzle = true) {
		override fun appearanceDescription(creature: Creature) = when {
			!creature.hasCoat() ->
				"[You] [verb have] a face resembling that of a lizard, and with [your] toothy maw, [you] [verb have] quite a fearsome visage. The reptilian visage does look a little odd with just [skin]."
			creature.hasFullCoatOfType(SkinCoatType.FUR) ->
				"[You] [verb have] a face resembling that of a lizard. Between the toothy maw, pointed snout, and the layer of [skin coat] covering [your] face, [you] [verb have] quite the fearsome visage."
			creature.hasFullCoat() ->
				"[Your] face is that of a lizard, complete with a toothy maw and pointed snout. Reflective [skin coat] complete the look, making [you] look quite fearsome."
			else ->
				"[You] [verb have] a face resembling that of a lizard, and with [your] toothy maw, [you] [verb have] quite a fearsome visage. The reptilian visage does look a little odd with just [skin coat]."
		}
	},
	BUNNY(
		8, "bunny", canBite = true, isHumanShaped = true,
		appearanceDesc = "The constant twitches of [your] nose and the length of [your] incisors gives [your] visage a hint of bunny-like cuteness."
	),
	KANGAROO(9, "kangaroo", hasMuzzle = true) {
		override fun appearanceDescription(creature: Creature) = buildString {
			append("[Your] face is ")
			if (!creature.hasCoat()) {
				append("bald")
			} else append("covered with [skin coat]")
			append(" and shaped like that of a kangaroo, somewhat rabbit-like except for the extreme length of [your] odd visage.")
		}
	},
	SPIDER_FANGS(
		10, "spider", canBite = true, isHumanShaped = true,
		appearanceDesc = "A set of retractable, needle-like fangs sit in place of [your] canines and are ready to dispense their venom."
	),
	FOX(11, "fox", canBite = true, hasMuzzle = true) {
		override fun appearanceDescription(creature: Creature) = buildString {
			append("[You] [verb have] a tapered, shrewd-looking vulpine face with a speckling of downward-curved whiskers just behind the nose.")
			when {
				!creature.hasCoat() ->
					append(" Oddly enough, there's no fur on [your] animalistic muzzle, just [skin coat].")
				creature.hasFullCoatOfType(SkinCoatType.FUR) ->
					append(" A coat of [skin coat] decorates [your] muzzle.")
				creature.skin.isCoverLowMid() ->
					append(" Strangely, [skin coat] adorn [your] animalistic visage.")
				else ->
					append(" Strangely, [skin coat] adorn every inch of [your] animalistic visage.")
			}
		}
	},
	DRAGON(
		12, "dragon", canBite = true, hasMuzzle = true,
		appearanceDesc = "[Your] face is a narrow, reptilian muzzle. It looks like a predatory lizard's, at first glance, but with an unusual array of spikes along the under-jaw. It gives [you] a regal but fierce visage. Opening [your] mouth reveals several rows of dagger-like sharp teeth. The fearsome visage is decorated by [skin coat]."
	),
	RACCOON_MASK(13, "racoon mask", isHumanShaped = true) {
		override fun appearanceDescription(creature: Creature) = buildString {
			append("[You] [verb have] a sly-looking raccoon mask over [your] eyes")

			if (!creature.hasCoat()) {
				if (creature.skin.baseColor in arrayOf("ebony", "black"))
					append(", although it isn't properly visible with [your] dusky hue")
			} else {
				if (creature.skin.baseColor in arrayOf("black", "midnight", "black", "midnight", "black", "midnight"))
					append(", hidden under [your] [skin coat] and barely visible due to [your] inky hue")
			}

			append(".")
		}
	},
	RACCOON(14, "racoon", canBite = true) {
		override fun appearanceDescription(creature: Creature) = buildString {
			append("[You] [verb have] a triangular raccoon face, including sensitive whiskers and a little black nose; a mask shades the space around [your] eyes, set apart from [your] [skin coat] by a band of white.")

			when {
				creature.hasPlainSkinOnly() ->
					append(" It looks a bit strange with no fur coverage.")
				creature.hasScales() ->
					append(" The presence of said scales gives [your] visage an eerie look, more reptile than mammal.")
				creature.skin.hasCoatOfType(SkinCoatType.CHITIN) ->
					append(" The presence of said chitin gives [your] visage an eerie look, more insect than mammal.")
			}
		}
	},
	BUCKTEETH(
		15, "buckteeth", isHumanShaped = true, canBite = true,
		appearanceDesc = "[You] [verb have] very noticeably mousey buckteeth."
	),
	MOUSE(
		16, "mouse", canBite = true,
		appearanceDesc = "[You] [verb have] a snubby, tapered mouse's face, with whiskers and a little pink nose. Two large incisors complete it."
	),
	FERRET_MASK(
		17, "ferret mask", isHumanShaped = true,
		appearanceDesc = "The [skinFurScales] around [your] eyes is significantly darker than the rest of [your] face, giving [you] a cute little ferret mask."
	),
	FERRET(18, "ferret", canBite = true) {
		override fun appearanceDescription(creature: Creature) = when {
			creature.hasFullCoatOfType(SkinCoatType.FUR) ->
				"[Your] face is coated in [skin coat] with [skin base] underneath, an adorable cross between human and ferret features. It is complete with a wet nose and whiskers."
			creature.hasCoat() ->
				"[Your] face is an adorable cross between human and ferret features, complete with a wet nose and whiskers. The only oddity is [skin base] covered with [skin coat]."
			else ->
				"[Your] face is an adorable cross between human and ferret features, complete with a wet nose and whiskers. The only oddity is [your] lack of fur, leaving only [skin] visible on [your] ferret-like face."
		}
	},
	PIG(
		19, "pig",
		appearanceDesc = "[Your] face is like that of a pig, complete with a snout that is always wiggling."
	),
	BOAR(
		20, "boar",
		appearanceDesc = "[Your] face is like that of a boar, complete with tusks and a snout that is always wiggling."
	),
	RHINO(
		21, "rhino",
		appearanceDesc = "[Your] face is like that of a rhino, complete with a long muzzle and a horns on [your] nose."
	),
	ECHIDNA(22, "echidna", hasMuzzle = true) {
		override fun appearanceDescription(creature: Creature) = buildString {
			append("[Your] odd visage consists of a long, thin echidna snout")

			if (!creature.hasCoat()) append(", though it looks rather strange without any coverage whatsoever")

			append(".")
		}
	},
	DEER(23, "deer", hasMuzzle = true) {
		override fun appearanceDescription(creature: Creature) = buildString {
			append("[Your] face is like that of a deer, with a nose at the end of [your] muzzle")

			if (!creature.hasCoat()) append(", though it looks rather strange without any coverage whatsoever")

			append(".")
		}
	},
	WOLF(24, "wolf", canBite = true, hasMuzzle = true) {
		override fun appearanceDescription(creature: Creature) = buildString {
			if (!creature.hasCoat()) {
				append("[You] [verb have] a wolf-like face, complete with a wet nose. ")
				append("The odd visage is hairless and covered with [skin coat].")
			} else if (creature.hasFullCoatOfType(SkinCoatType.FUR)) {
				append("[You] [verb have] a wolf’s face, complete with wet nose a panting tongue and threatening teeth. ")
				append("[Youve] got [skin coat], hiding [your] [skin noadj] underneath [your] furry visage.")
			} else {
				append("[You] [verb have] the facial structure of a wolf, wet nose and all, but overlaid with glittering patches of [skin coat].")
			}
			// TODO Gleipnir Collar
//				if (creature.hasKeyItem("Gleipnir Collar") >= 0) desc += "[Your] breath freezes the air, and cold mist leaks from [your] jaws. ";
		}
	},
	MANTICORE(
		25, "manticore", canBite = true, isHumanShaped = true,
		appearanceDesc = "[You] [verb have] a set of sharp cat-like teeth in [your] mouth."
	),
	SALAMANDER_FANGS(
		26, "salamander", canBite = true, isHumanShaped = true,
		appearanceDesc = "A pair of salamander fangs grows out of [your] mouth giving [you] a menacing smile."
	),
	YETI_FANGS(
		27, "yeti", canBite = true, isHumanShaped = true,
		appearanceDesc = "[Your] mouth, while human-looking, has sharp yeti fangs."
	),
	ORCA(28, "orca", canBite = true, isHumanShaped = true) {
		override fun appearanceDescription(creature: Creature) =
			if (creature.skin.hasPlainSkinOnly() && creature.skin.adj == "glossy" && creature.skinTone == "white and black")
				"[You] [verb have] a wider yet adorable nose, and [your] face is pitch black with a white underbelly; from [your] neck up to [your] mouth and lower cheeks [your] face is white with two extra white circles right under and above [your] eyes."
			else
				"[You] [verb have] a wide nose similar to that of an orca, which goes well with [your] sharp toothed mouth, giving [you] a cute look."
	},
	PLANT_DRAGON(
		29, "plant dragon", canBite = true,
		appearanceDesc = "[Your] face is a narrow, reptilian and regal, reminiscent of a dragon. A [skin coat] decorates [your] visage."
	),
	DRAGON_FANGS(
		30, "dragon fangs", canBite = true,
		appearanceDesc = "[Your] mouth is somewhat human save for [your] draconic fangs giving [you] a menacing smile. It's decorated by [skin coat]."
	),
	DEVIL_FANGS(
		31, "devil fangs", canBite = true,
		appearanceDesc = "[Your] mouth looks human enough, save for [your] fiendish canines. It's decorated by [skin coat]."
	),
	ONI_TEETH(
		32, "oni teeth", canBite = true, isHumanShaped = true,
		appearanceDesc = "[You] [verb have] two large ogre canines in [your] mouth."
	),
	WEASEL(
		33, "weasel", canBite = true,
		appearanceDesc = "[You] [verb have] two sharp weasel canines in [your] mouth."
	),
	VAMPIRE(
		34, "vampire", canBite = true, isHumanShaped = true,
		appearanceDesc = "[You] own a pair of long and pointy vampire canines meant to pierce into victims and reach their blood."
	),
	BUCKTOOTH(
		35, "jabberwocky buckteeth", canBite = true, isHumanShaped = true,
		appearanceDesc = "[You] [verb have] two abnormally large buck tooth, like a Jabberwocky."
	),
	JABBERWOCKY(
		36, "jabberwocky", canBite = true,
		appearanceDesc = "[Your] face is a narrow, reptilian muzzle. It looks like a predatory lizard's, at first glance, but with an unusual array of spikes along the under-jaw. It gives [you] a regal but fierce visage. Opening [your] mouth reveals two buck tooth, which are abnormally large. Like a rabbit or rather a Jabberwocky. The fearsome visage is decorated by [skin coat]."
	),
	RED_PANDA(37, "red panda", canBite = true) {
		override fun appearanceDescription(creature: Creature) = buildString {
			append("[Your] face has a distinctive animalistic muzzle, proper from a red-panda, complete with a cute pink nose")

			if (!creature.hasCoat()) append(", though it looks rather strange without any coverage whatsoever")

			append(".")
		}
	},
	CAT_CANINES(
		38, "cat canines", canBite = true,
		appearanceDesc = "[You] [verb have] a pair of cat-like canines in [your] mouth."
	),
	CHESHIRE(39, "cheshire", canBite = true) {
		override fun appearanceDescription(creature: Creature) = buildString {
			if (!creature.hasCoat()) {
				append("[You] [verb have] a cat-like face, complete with a cute, moist nose and whiskers. The [skin] that is revealed by [your] lack of fur looks quite unusual on so feline a face.")
			} else if (creature.hasFullCoatOfType(SkinCoatType.FUR)) {
				append("[You] [verb have] a cat-like face, complete with moist nose and whiskers. [Your] [skin coat.nocolor] is [skin coat.color], hiding [your] [skin base] underneath.")
			} else {
				append("[Your] facial structure blends humanoid features with those of a cat. A moist nose and whiskers are included, but overlaid with glittering patches of [skin coat].")
			}
			append(" For some reason [your] facial expression is that of an everlasting yet unsettling grin.")
		}
	},
	CHESHIRE_SMILE(
		40, "cheshire", canBite = true, isHumanShaped = true,
		appearanceDesc = "[You] [verb have] a pair of cat-like canines in [your] mouth, and for some reason [your] facial expression is that of an everlasting yet unsettling grin."
	),
	AVIAN(
		41, "avian", canBite = true,
		appearanceDesc = "[Your] visage has a bird-like appearance, complete with an avian beak. A couple of small holes on it makes up for [your] nostrils, while a long, nimble tongue is hidden inside."
	) {
		override val hasBeak get() = true
	},
	WOLF_FANGS(42, "wolf fangs", canBite = true, isHumanShaped = true) {
		override fun appearanceDescription(creature: Creature) = when {
			!creature.hasCoat() ->
				"[Your] face is human in shape and structure with [skin coat]. [Your] mouth is somewhat human save for [your] wolf-like canines."
			creature.hasPartialCoatOfType(SkinCoatType.FUR) ->
				"[Your] face looks human save for [your] wolf-like canines, but overlaid with glittering patches of [skin coat]."
			else ->
				"[Your] face looks human save for [your] wolf-like canines. [Youve] got [skin coat], hiding [your] [skin noadj] underneath [your] furry visage."
		}
	},
	ORC_FANGS(
		43, "orc fangs", canBite = true, isHumanShaped = true,
		appearanceDesc = "[Your] two lower canines resemble boar tusks poking out of [your] mouth."
	),
	ANIMAL_TEETH(44, "animal teeth", canBite = true, isHumanShaped = true) {
		override fun appearanceDescription(creature: Creature) = buildString {
			if (!creature.hasCoat()) {
				append("[Your] face looks human save for [your] sharp canines.")
			} else if (creature.hasFullCoatOfType(SkinCoatType.FUR)) {
				append("[Your] face looks human save for [your] sharp canines. [Your] [skin coat.nocolor] is [skin coat.color], hiding [your] [skin base] underneath.")
			} else {
				append("[Your] face looks human save for [your] sharp canines, but overlaid with glittering patches of [skin coat].")
			}
			if (creature.eyes.type == EyeType.CENTIPEDE) {
				append(" [You] wear a constant expression of sadness, barely drawing attention away from [your] mouth.")
			}
		}
	},
	BEAR(45, "bear", canBite = true, hasMuzzle = true) {
		override fun appearanceDescription(creature: Creature) = buildString {
			append("[Your] face is that of a bear with an adorable wet nose and sharp teeth")

			if (!creature.hasCoat()) append(", though it looks rather strange without any coverage whatsoever")

			append(".")
		}
	},
	PANDA(
		46, "panda", canBite = true, hasMuzzle = true,
		appearanceDesc = "[Your] face is that of a panda with an adorable wet nose and sharp teeth. [Your] face fur, much like most of [your] body, is white with two black circles right around [your] eyes."
	),
	FIRE_SNAIL(
		47, "fire snail", isHumanShaped = true,
		appearanceDesc = "[Your] mouth is drooling from constant arousal."
	),
	GHOST(
		48, "ghost", isHumanShaped = true,
		appearanceDesc = "[You] [verb have] a perpetual wide smile on [your] face, hiding [your] true feelings at all times."
	),
	JIANGSHI(
		49, "jiangshi", isHumanShaped = true,
		appearanceDesc = "[Your] expression is permanently dazed, as though [youre] in an eternal trance."
	),
	YUKI_ONNA(
		50, "yuki onna", isHumanShaped = true,
		appearanceDesc = "[Your] lips, as lacking in wamth as the rest of [your] body, are dyed blue by the cold."
	),
	KUUDERE(
		51, "kuudere", isHumanShaped = true,
		appearanceDesc = "[Your] face shows no emotions whatsoever, hiding [your] true feelings at all times."
	),
	USHI_ONI(
		52, "ushi-oni", canBite = true, isHumanShaped = true,
		appearanceDesc = "[You] [verb have] strange black tattoos circling [your] eyes and small fangs in [your] mouth ready to inject its victims with a weak poison."
	),
	FAIRY(
		53, "fairy", isHumanShaped = true,
		appearanceDesc = "[Your] fairy nature allows [you] to always display a pure, cheerful, innocent smile that warms the hearts of those around [you]."
	),
	CRAZY(
		54, "crazy", isHumanShaped = true,
		appearanceDesc = "Although [your] mouth is quite human-looking, [your] [verb have] a near constant toothy smile makes [you] look quite unhinged, and [your] canines are slightly longer and pointier than human ones."
	),
	SMUG(
		55, "smug", isHumanShaped = true,
		appearanceDesc = "The length of [your] incisors gives [your] visage a hint of squirrel-like cuteness, and once in a while [you] can't help but smirk smuggly at [your] interlocutors."
	),
	SQUIRREL(56, "squirrel", canBite = true, hasMuzzle = true) {
		override fun appearanceDescription(creature: Creature) = when {
			!creature.hasCoat() ->
				"[You] [verb have] a squirrel-like face, complete with a twitching nose. The odd visage is hairless and covered with [skin base]."
			creature.hasFullCoatOfType(SkinCoatType.FUR) ->
				"[You] [verb have] a squirrel's face, complete with twitching nose and two incisors. [Youve] got [skin coat]"
			else ->
				"[You] [verb have] the facial structure of a squirrel, twitching nose incisors and all, but overlaid with glittering patches of [skin coat]"
		}
	},
	ELF(
		57, "elf", isHumanShaped = true,
		appearanceDesc = "[You] [verb have] an enchanting smile and faultless pearlescent white teeth, so unnaturally perfect it makes [you] seem as though [you] came straight from a painting rather than real life."
	)
	;

	open fun appearanceDescription(creature: Creature): String = appearanceDesc

	open val hasBeak: Boolean get() = false

	companion object : CocIdLookup<FaceType>(values())
}
