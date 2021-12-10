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
): CocId {
	NORMAL(0, "normal") {
		override fun appearanceDescription(creature: Creature) =
			"Although not particularly remarkable, your ${hairInfo(creature)} looks good on you, accentuating your features well."
	},
	FEATHER(1, "feather", ignoresStyle = true) {
		override fun appearanceDescription(creature: Creature): String {
			return "Your ${hairInfo(creature)} is made completely out of feathers rather than actual strands."
		}
	},
	//	EnumValue.add(Types, FEATHER, "FEATHER", {
	//		shortDesc: "feather-{hair}",
	//	});
	//
	GHOST(2, "ghost") {
		override fun appearanceDescription(creature: Creature): String {
			return "Although your ${hairInfo(creature)} technically has the features of normal human hair, it is also completely transparent."
		}
	},
	//	EnumValue.add(Types, GHOST, "GHOST", {
	//		shortDesc: "transparent {hair}",
	//		beardDesc: "transparent "
	//	});
	//
	GOO(3, "goopy") {
		override fun appearanceDescription(creature: Creature): String {
			return "Atop your head is a gooey ${hairInfo(creature)}, more like an amorphous blob imitating the familiar shape than the real deal."
		}
	},
	//	EnumValue.add(Types, GOO, "GOO", {
	//		shortDesc: "goo-{hair}",
	//		beardDesc: "gooey "
	//	});
	//
	ANEMONE(4, "tentacle") {
		override fun appearanceDescription(creature: Creature): String {
			TODO("Not yet implemented")
		}
	},
	//	EnumValue.add(Types, ANEMONE, "ANEMONE", {
	//		name:"tentacle",
	//		shortDesc: "tentacle-{hair}",
	//		appearanceDesc: "Your {hairInfo} is in truth made out of anemone tentacles, only vaguely resembling the real deal.",
	//		beardDesc: "tentacley ",
	//		ignoresStyle: true
	//	});
	//
	QUILL(5, "quill") {
		override fun appearanceDescription(creature: Creature): String {
			TODO("Not yet implemented")
		}
	},
	//	EnumValue.add(Types, QUILL, "QUILL", {
	//		name:"quill",
	//		shortDesc: "quill-{hair}",
	//		appearanceDesc: "Your {hairInfo} is made completely out of quills rather than actual strands.",
	//		ignoresStyle: true
	//	});
	//
	GORGON(6, "snake-like") {
		override fun appearanceDescription(creature: Creature): String {
			TODO("Not yet implemented")
		}
	},
	//	EnumValue.add(Types, GORGON, "GORGON", {
	//		name:"snake-like",
	//		shortDesc: "snakes-{hair}",
	//		appearanceDesc: "Atop your head is technically {hairInfo}, if one were to ignore that it is made of snakes rather than actual hair.",
	//		ignoresStyle: true
	//	});
	//
	LEAF(7, "leaf") {
		override fun appearanceDescription(creature: Creature): String {
			TODO("Not yet implemented")
		}
	},
	//	EnumValue.add(Types, LEAF, "LEAF", {
	//		name:"leaf",
	//		shortDesc: "leaf-{hair}",
	//		appearanceDesc: "Considering how your {hairInfo} is made completely out of leaves, you seem to have some sort of connection to nature.",
	//		beardDesc: "moss "
	//	});
	//
	FLUFFY(8, "fluffy") {
		override fun appearanceDescription(creature: Creature): String {
			TODO("Not yet implemented")
		}
	},
	//	EnumValue.add(Types, FLUFFY, "FLUFFY", {
	//		name:"fluffy",
	//		shortDesc: "fluffy {hair}",
	//		appearanceDesc: "You have a white pillowy {hair}, very much wooly to the touch and constantly trying to lull you to sleep anytime you lie against a surface.",
	//		ignoresStyle: true
	//	});
	//
	GRASS(9, "grass") {
		override fun appearanceDescription(creature: Creature): String {
			TODO("Not yet implemented")
		}
	},
	//	EnumValue.add(Types, GRASS, "GRASS", {
	//		name:"grass",
	//		shortDesc: "grass-{hair}",
	//		appearanceDesc: "Rather than normal strands, your {hairInfo} is actually made entirely of grass, like some sort of nature spirit's."
	//	});
	//
	SILKEN(10, "silky") {
		override fun appearanceDescription(creature: Creature): String {
			TODO("Not yet implemented")
		}
	},
	//	EnumValue.add(Types, SILKEN, "SILKEN", {
	//		name:"silky",
	//		shortDesc: "elven {hair}",
	//		appearanceDesc: "Your {hairInfo} is extremely glossy and smooth, its elvish features so perfect by human standards to the point of feeling unnatural."
	//	});
	//
	STORM(11, "glowing lightning-shaped") {
		override fun appearanceDescription(creature: Creature): String {
			TODO("Not yet implemented")
		}
	},
	//	EnumValue.add(Types, STORM, "STORM", {
	//		name:"glowing lightning-shaped",
	//		shortDesc: "lightning {hair}",
	//		appearanceDesc: "Your {hairInfo}'s tips end in glowing lightning-shaped locks, crackling with electricity whenever you will them to."
	//	});
	//
	BURNING(12, "burning") {
		override fun appearanceDescription(creature: Creature): String {
			TODO("Not yet implemented")
		}
	},
	//	EnumValue.add(Types, BURNING, "BURNING", {
	//		name:"burning",
	//		shortDesc: "burning hair",
	//		appearanceDesc: "Your {hairInfo} has its tips overtaken by a magic flame, burning anything you wish it to and nothing more."
	//	});
	//
	SNOWY(13, "burning") {
		override fun appearanceDescription(creature: Creature): String {
			TODO("Not yet implemented")
		}
	},
	//	EnumValue.add(Types, SNOWY, "SNOWY", {
	//		name:"snowy",
	//		shortDesc: "snowy {hair}",
	//		appearanceDescFunc: function(creature: *): String {
	//			var desc: String = "Your {hairInfo} is human in appearance, but cold to the touch and regularly used as a nest by snow flurries";
	//
	//			if (creature.rearBody.type == RearBody.GLACIAL_AURA) {
	//				desc += ", an effect amplified by your bone-chilling aura";
	//			}
	//
	//			desc += "."
	//
	//			return desc;
	//		}
	//	});
	//
	FAIRY(14, "otherworldly silky and almost translucent") {
		override fun appearanceDescription(creature: Creature): String {
			TODO("Not yet implemented")
		}
	},
	//	EnumValue.add(Types, FAIRY, "FAIRY", {
	//		name:"otherworldly silky and almost translucent",
	//		shortDesc: "silky fairy {hair}",
	//		appearanceDesc: "Despite looking almost human, your {hairInfo} is otherworldly smooth and almost translucent, its fairy traits easy to notice if not identify."
	//	});
	//
	CRAZY(15, "crazy") {
		override fun appearanceDescription(creature: Creature): String {
			TODO("Not yet implemented")
		}
	},
	//	EnumValue.add(Types, CRAZY, "CRAZY", {
	//		name:"crazy",
	//		shortDesc: "crazy {hair}",
	//		appearanceDesc: "Your wild {hairInfo} is positively crazy, with spiked tips pointing outwards to your sides.",
	//		ignoresStyle: true
	//	});
	//
	WINDSWEPT(16, "windswept") {
		override fun appearanceDescription(creature: Creature): String {
			TODO("Not yet implemented")
		}
	},
	//	EnumValue.add(Types, WINDSWEPT, "WINDSWEPT", {
	//		name:"windswept",
	//		appearanceDesc: "Your {hairInfo} is quite aerodynamic, shaped as to avoid encumbering you even in a windstorm."
	//	});
	//
	RATATOSKR(17, "stripped") {
		override fun appearanceDescription(creature: Creature): String {
			TODO("Not yet implemented")
		}
	},
	//	EnumValue.add(Types, RATATOSKR, "RATATOSKR", {
	//		name:"stripped",
	//		shortDesc: "stripped {hair}",
	//		appearanceDesc: "Though your {hairInfo} could almost pass for human, it's stripped at the center with light tips not unlike the head of a chipmunk."
	//	});
	//
	PRISMATIC(18, "prismatic") {
		override fun appearanceDescription(creature: Creature): String {
			TODO("Not yet implemented")
		}
	},
	//	EnumValue.add(Types, PRISMATIC, "PRISMATIC", {
	//		name:"prismatic",
	//		shortDesc: "prismatic {hair}",
	//		appearanceDesc: "Although your {hairInfo} looks mostly human, that notion is quickly discarded by its prismatic strands, varying in hue along their length to display all the colors of a rainbow."
	//	});
	//
	MINDBREAKER(19, "mindbreaker") {
		override fun appearanceDescription(creature: Creature): String {
			TODO("Not yet implemented")
		}
	},
	//	EnumValue.add(Types, MINDBREAKER, "MINDBREAKER", {
	//		name:"mindbreaker",
	//		shortDesc: "slimy tentacle {hair}",
	//		appearanceDesc: "Your {hairInfo} is made out of tentacles, only vaguely resembling the real deal."
	//	});
	//
	MINDBREAKERMALE(20, "mindbreaker") {
		override fun appearanceDescription(creature: Creature): String {
			return "Smaller webbings frill along the top of your head, forming a crown-like ring. Along these frills are longer hair-like tentacles that reach down to drape past your shoulders, just above your pectorals."
		}
	},
	//	EnumValue.add(Types, MINDBREAKERMALE, "MINDBREAKERMALE", {
	//		shortDesc: "slimy tentacle {hair}",
	//	});
	;

	abstract fun appearanceDescription(creature: Creature): String

	protected open fun hairInfo(creature: Creature): String {
		//If furry and longish hair sometimes call it a mane (50%)
		val hair: String =
			if (creature.hasFur() && creature.hairLength > 3 && fxrng.nextInt(2) == 0) "mane"
			else "hair"

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

	companion object: CocIdLookup<HairType>(values())
}
