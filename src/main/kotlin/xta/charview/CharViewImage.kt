package xta.charview

import org.w3c.dom.CanvasRenderingContext2D
import xta.game.PlayerCharacter
import xta.game.creature.body.*
import xta.logging.LogManager
import kotlin.js.Date

/*
 * Created by aimozg on 02.12.2021.
 */
class CharViewImage : CompositeImage(200,220) {
	init {
		logger.info(null,"Loading images")
		val t0 = Date().getTime()
		loadPartsFromJson(sprites_json, charviewImages, originX = 15, originY = 10)
		logger.debug(null, "Loaded images in ${Date().getTime()-t0} ms")
		setLayerOrder(
			"fullbody",
			"full_pattern",
			"horns_overclothe",
			"overclothe",
			"shield",
			"weapon",
			"weaponBuff",
			"hands_fg",
			"glove_fg",
			"arms_fg",
			"eyes_fg",
			"horns_fg",
			"clotheScarf",

			"armorNeck",
			"armorRightArm",
			"armorChest",
			"tail_front",
			"clotheHead",
			"clotheAmulet",
			"clotheUnderAmulet",
			"bra_fg",
			"clothe",
			"bra",
			"overGenitals",
			"wings_front",
			"armorLegs_fg",
			"armorLeftArm",

			"penis", /* should never show if pc wears a panty */
			"balls", /* should never show if pc wears a panty */

			"clotheLegs",
			"armorLegs",
			"panty",

			"antennae",
			"horns",
			"ears",
			"hairTop",
			"hairRight",
			"hair",
			"eyes",
			"forehead",
			"ears_bg",
			"clotheHead_bg",

			"faceShading",
			"face",
			"head",

			"neck",
			"horns_bg",
			"overskin",
			"breasts_pattern",
			"breasts",
			"clothRightArm",
			"clothLeftArm",
			"overhands",
			"hands",
			"arms_pattern",
			"arms_overlay",
			"arms",
			"overhands_bg",
			"hands_bg",
			"arms_bg_pattern",
			"legs_truefg",
			"arms_overlay_bg",
			"arms_bg",
			"uniquePlantBath",
			"legs_fg",

			"penis_taur", /* should never show if pc wears a panty */
			"balls_taur", /* should never show if pc wears a panty */

			"overskinpanty",

			"overfeet",
			"feet",
			"legs_pattern",
			"legs",

			"torso_pattern",
			"muscle",
			"torso",
			"ears_truebg",
			"rearbody",
			"tailAccessory",
			"tail_fg",
			"tail",
			"clothCape",
			"clothCape_bg",
			"backAccessory",
			"hair_bg",
			"hairLeft",
			"wings",
			"wings_bg",
			"tail_bg",
			"legs_bg",
			"aura",
		)
	}

	fun setupColors(char: PlayerCharacter) {
		val hairColor = colordb.find("hair", char.hairColor)
		val hair2 = colordb.find("hair", char.hairColor)
		val skin = colordb.find("skin", char.skinColor)
		val skin2 = colordb.find("skin", char.skinColor2)
		val fur = colordb.find("fur", char.furColor)
		val fur2 = colordb.find("fur", char.furColor2)
		val scales = colordb.find("scales", char.scaleColor)
		val scales2 = colordb.find("scales", char.scaleColor2)
		val chitin = colordb.find("chitin", char.chitinColor)
		val chitin2 = colordb.find("chitin", char.chitinColor2)
		val iris = colordb.find("eyes", char.eyeColor)

		setKeyColor(0xFF88FF, hairColor.lighten(25))
		setKeyColor(0xF934F9, hairColor.lighten(10))
		setKeyColor(0xCC00CC, hairColor)
		setKeyColor(0x880088, hairColor.darken(25))
		setKeyColor(0x440044, hairColor.darken(50))
		setKeyColor(0xFFFF88, hair2.lighten(25))
		setKeyColor(0xCCCC00, hair2)
		setKeyColor(0x999900, hair2.darken(25))
		setKeyColor(0x333300, hair2.darken(50))
		setKeyColor(0x88FF88, fur.lighten(25))
		setKeyColor(0x00CC00, fur)
		setKeyColor(0x00ac00, fur.darken(15))
		setKeyColor(0x009900, fur.darken(25))
		setKeyColor(0x003300, fur.darken(50))
		setKeyColor(0xAA88FF, fur2.lighten(25))
		setKeyColor(0x6633CC, fur2)
		setKeyColor(0x442288, fur2.darken(25))
		setKeyColor(0x220044, fur2.darken(50))
		setKeyColor(0x8888FF, scales.lighten(25))
		setKeyColor(0x5c5cfe, scales.lighten(10))
		setKeyColor(0x4444FF, scales)
		setKeyColor(0x0000CC, scales.darken(25))
		setKeyColor(0x000033, scales.darken(50))
		setKeyColor(0xAAFFAA, scales2.lighten(25))
		setKeyColor(0x66CC66, scales2)
		setKeyColor(0x448844, scales2.darken(25))
		setKeyColor(0x223322, scales2.darken(50))
		setKeyColor(0x88FFFF, chitin.lighten(25))
		setKeyColor(0x44CCFF, chitin)
		setKeyColor(0x0099CC, chitin.darken(25))
		setKeyColor(0x006666, chitin.darken(50))
		setKeyColor(0xAACCDD, chitin2.lighten(25))
		setKeyColor(0x77AACC, chitin2)
		setKeyColor(0x4477AA, chitin2.darken(25))
		setKeyColor(0x114477, chitin2.darken(50))
		setKeyColor(0xFFE9CC, skin.lighten(25))
		setKeyColor(0xFACE8D, skin)
		setKeyColor(0xe7be82, skin.darken(8))
		setKeyColor(0xBC9B6D, skin.darken(25))
		setKeyColor(0x7C6647, skin.darken(50))
		setKeyColor(0x1C0F0F, skin.darken(75))
		setKeyColor(0xFFCCCC, skin2.lighten(25))
		setKeyColor(0xFF9999, skin2)
		setKeyColor(0xeb8d8d, skin2.darken(8))
		setKeyColor(0xBB7777, skin2.darken(25))
		setKeyColor(0x884444, skin2.darken(50))
		setKeyColor(0x221111, skin2.darken(75))
		setKeyColor(0xFF0000, iris)
		setKeyColor(0xAA0000, iris.darken(25))
		setKeyColor(0xFBCAAE, skin.darken(25).saturate(25).shiftTo(0,25))
		setKeyColor(0xECB596, skin.darken(50).saturate(25).shiftTo(0,25))
	}

	fun renderCharacter(char: PlayerCharacter, scale:Boolean): CanvasRenderingContext2D {
		hideAll()
		setupColors(char)

		val taur = if (char.isTaur) "_taur" else ""

		/* VARIABLES AREA */
		val hydraTails = 0 // TODO char.statusEffectv1(StatusEffects.HydraTailsPlayer)
		val big = if ((char.cocks.firstOrNull()?.length ?: 0) >= 12) "_big" else ""
		/* <set var="CaveWyrmNipples" value="hasStatusEffect(StatusEffects.GlowingNipples)"/> */

		/* WEAPON AREA */
		// TODO use sprite ids from weapons

		/* ANTENNAE AREA */
		// TODO port model.xml code

		/* HORN AREA */
		// TODO port model.xml code

		/* EAR AREA */
		when (char.ears.type) {
			EarType.HUMAN -> {
				showPart("ears_bg/human")
			}
			EarType.COW,
			EarType.PIG,
			EarType.RHINO,
			EarType.ECHIDNA,
			EarType.GOAT -> {
				showPart("ears/goat")
				showPart("ears_bg/goat")
			}
			EarType.HORSE,
			EarType.DEER,
			EarType.KANGAROO -> {
				showPart("ears_bg/horse")
			}
			EarType.DOG,
			EarType.WOLF,
			EarType.RACCOON,
			EarType.FERRET -> {
				showPart("ears/wolf")
				showPart("ears_bg/wolf")
			}
			EarType.ELFIN,
			EarType.ELVEN,
			EarType.ONI,
			EarType.VAMPIRE -> {
				showPart("ears/elfin")
				showPart("ears_bg/elfin")
			}
			EarType.CAT,
			EarType.LION -> {
				showPart("ears/cat")
				showPart("ears_bg/cat")
			}
			EarType.LIZARD, EarType.ORCA2 -> {}
			EarType.BUNNY -> {
				showPart("ears/bunny")
				showPart("ears_bg/bunny")
			}
			EarType.DRAGON -> {
				showPart("ears/dragon")
				showPart("ears_bg/dragon")
			}
			EarType.FOX,
			EarType.BAT -> {
				showPart("ears/fox")
				showPart("ears_bg/fox")
			}
			EarType.MOUSE -> {
				showPart("ears/mouse")
				showPart("ears_bg/mouse")
			}
			EarType.YETI -> {
				showPart("ears/fur")
			}
			EarType.ORCA -> {
				showPart("ears/orca")
				showPart("ears_bg/orca")
			}
			EarType.SNAKE -> {
				showPart("ears/naga")
				showPart("ears_bg/naga")
			}
			EarType.RAIJU,
			EarType.GREMLIN -> {
				showPart("ears/raiju")
				showPart("ears_bg/raiju")
			}
			EarType.PANDA -> {
				showPart("ears/panda")
				showPart("ears_bg/panda")
			}
			EarType.BEAR,
			EarType.WEASEL -> {
				showPart("ears/bear")
				showPart("ears_bg/bear")
			}
			EarType.MELKIE -> {
				showPart("ears_truebg/melkie")
			}
			EarType.DISPLACER -> {
				showPart("ears/displacer")
			}
			EarType.SHARK -> {
				showPart("ears/shark")
				showPart("ears_bg/shark")
			}
			EarType.CAVE_WYRM -> {
				showPart("ears/caveWyrm")
				showPart("ears_bg/caveWyrm")
			}
			EarType.SQUIRREL -> {
				showPart("ears/ratatoskr")
				showPart("ears_bg/ratatoskrRight")
			}
			else -> {
				showPart("ears_bg/human")
			}
		}

		/* EYE AREA */
		when (char.eyes.type) {
			EyeType.HUMAN -> {
				showPart("eyes/human")
			}
			EyeType.SPIDER -> {
				showPart("horns/spider")
				showPart("eyes/human")
			}
			EyeType.BLACK_EYES_SAND_TRAP -> {
				showPart("eyes/sandtrap")
			}
			EyeType.FOX,
			EyeType.CAT,
			EyeType.LIZARD,
			EyeType.SNAKE,
			EyeType.GORGON,
			EyeType.DRACONIC,
			EyeType.ONI,
			EyeType.ELF,
			EyeType.VAMPIRE,
			EyeType.WEASEL,
			EyeType.FIENDISH -> {
				showPart("eyes/cat")
			}
			EyeType.DEVIL,
			EyeType.FROSTWYRM,
			EyeType.DISPLACER -> {
				showPart("eyes/devil")
			}
			EyeType.MANTICORE,
			EyeType.RAIJU -> {
				showPart("eyes/raiju")
			}
			EyeType.INFERNAL -> {
				showPart("eyes_fg/infernal")
			}
			EyeType.FERAL,
			EyeType.FENRIR -> {
				showPart("eyes/yandere")
			}
			EyeType.GOAT,
			EyeType.KRAKEN -> {
				showPart("eyes/goat")
			}
			EyeType.CENTIPEDE -> {
				showPart("eyes/centipede")
			}
			EyeType.CAVE_WYRM -> {
				showPart("eyes/caveWyrm")
			}
			EyeType.GREMLIN -> {
				showPart("eyes/gremlin")
			}
			EyeType.RATATOSKR -> {
				showPart("eyes/centipede")
			}
			EyeType.MONOEYE -> {
				showPart("eyes/gazer")
			}
			else -> {
				showPart("eyes/human")
			}
		}

		/* REARBODY AREA */
		// TODO port model.xml code

		/* HAIR AREA */
		if (char.hairLength > 0) {
			when (char.hairType) {
				HairType.FEATHER -> {
					showPart("hair/feather")
					showPart("hair_bg/feather")
					if (char.hairLength >= 16) {
						showPart("hair_bg/02")
					}
				}
				HairType.GORGON -> {
					showPart("hair/gorgon")
					showPart("hair_bg/gorgon")
					if (char.hairLength >= 16) {
						showPart("hair_bg/02")
					}
				}
				HairType.STORM -> {
					showPart("hair/raiju")
					if (char.hairLength >= 16) {
						showPart("hair_bg/raiju3")
					}
				}
				HairType.BURNING -> {
					showPart("hair/hellcat")
					if (char.hairLength >= 16) {
						showPart("hair_bg/hellcat")
					}
				}
				HairType.GOO -> {
					showPart("hair/slime")
					if (char.hairLength >= 16) {
						showPart("hair_bg/slime2")
					}
				}
				HairType.FLUFFY -> {
					showPart("hair/yeti")
					if (char.hairLength >= 16) {
						showPart("hair_bg/yeti")
					}
				}
				HairType.CRAZY -> {
					showPart("hair/gremlin")
				}
				HairType.RATATOSKR -> {
					showPart("hair/ratatoskr")
					showPart("hair_bg/ratatoskr")
					if (char.hairLength >= 16) {
						showPart("hair_bg/02")
					}
				}
				HairType.PRISMATIC -> {
					showPart("hair/rainbow")
					showPart("hair_bg/rainbow")
				}
				else -> {
					showPart("hair/0")
					if (char.hairLength >= 16) {
						showPart("hair_bg/02")
					} else {
						showPart("hair_bg/0")
					}
				}
			}
		}

		/* HAIR STYLE AREA */
		// TODO port model.xml code

		val skinb:String
		val skinx:String
		when (char.skin.coverage) {
			SkinCoverage.NONE -> when (char.skin.baseType) {
				SkinBaseType.GOO -> {
					skinb = "goo"
					skinx = "goo"
				}
				else -> {
					skinb = "human"
					if (char.skin.basePattern == SkinBasePatternType.ORCA_UNDERBODY) {
						skinx = "orca"
					} else if (char.skin.basePattern == SkinBasePatternType.SEA_DRAGON_UNDERBODY) {
						skinx = "orca"
						showPart("torso_pattern/Bbioluminescence")
					} else {
						skinx = "human"
					}
				}
			}
			SkinCoverage.LOW,
			SkinCoverage.MEDIUM -> when (char.skin.coatType) {
				SkinCoatType.SCALES,
				SkinCoatType.DRAGON_SCALES -> {
					skinb = "pscales"
					skinx = "pscales"
				}
				else -> {
					skinb = "human"
					skinx = "human"
				}
			}
			SkinCoverage.HIGH,
			SkinCoverage.COMPLETE -> when (char.skin.coatType) {
				SkinCoatType.FUR -> {
					if (char.ears.type == EarType.PANDA) {
						skinb = "panda"
						skinx = "panda"
					} else {
						skinb = "fur"
						skinx = "fur"
					}
				}
				SkinCoatType.AQUA_SCALES -> {
					skinb = "human"
					skinx = "human"
				}
				SkinCoatType.SCALES -> {
					skinb = "scales"
					skinx = "scales"
				}
				SkinCoatType.DRAGON_SCALES -> {
					skinb = "dscales"
					skinx = "dscales"
				}
				SkinCoatType.CHITIN -> {
					skinb = "chitin"
					if (char.skin.coatPattern == SkinCoatPatternType.BEE_STRIPES) {
						skinx = "bee"
					} else {
						skinx = "chitin"
					}
				}
				else -> {
					skinb = "human"
					skinx = "human"
				}
			}
		}
		showPart("head/$skinx")
		showPart("torso/$skinx")
		showPart("arms/$skinx")
		showPart("arms_bg/$skinx")
		showPart("legs/$skinx")

		/* In the event player corruption is maxed enter yandere mode forehead */
		if (char.cor >= 100 && char.eyes.type != EyeType.CENTIPEDE) {
			showPart("forehead/SpecialForeheadShadow")
		}

		// face type
		when (char.face.type) {
			FaceType.HUMAN -> {
				when {
					char.skin.baseType == SkinBaseType.GOO ->
						showPart("face/goo")
					char.cor >= 100 -> {
						if (char.lowerBody.type == LowerBodyType.FLOWER_LILIRAUNE) {
							showPart("face/lilirauneCorruptedSmile")
							showPart("forehead/lilirauneCorruptedShadow")
						} else {
							showPart("face/fairySmile")
						}
					}
					char.cor >= 50 ->
						showPart("face/demonlewd")
					else ->
						showPart("face/human")
				}
			}
			FaceType.HORSE,
			FaceType.FOX,
			FaceType.PIG,
			FaceType.BOAR,
			FaceType.RHINO,
			FaceType.ECHIDNA,
			FaceType.DOG,
			FaceType.CAT,
			FaceType.WOLF ->
				showPart("face/fur")
			FaceType.KANGAROO,
			FaceType.MOUSE,
			FaceType.DEER ->
				showPart("face/fur")
			FaceType.SHARK_TEETH ->
				showPart("face/shark")
			FaceType.LIZARD,
			FaceType.DRAGON ->
				showPart("face/reptile")
			FaceType.BUNNY,
			FaceType.BUCKTEETH,
			FaceType.JABBERWOCKY,
			FaceType.BUCKTOOTH ->
				showPart("face/bunny")
			FaceType.SPIDER_FANGS ->
				showPart("face/human_gang")
			FaceType.RACCOON_MASK,
			FaceType.FERRET_MASK -> {
				showPart("face/racoon_mask")
				showPart("face/human_fang")
			}
			FaceType.RACCOON,
			FaceType.FERRET -> {
				showPart("face/racoon")
			}
			FaceType.SALAMANDER_FANGS,
			FaceType.VAMPIRE,
			FaceType.YETI_FANGS,
			FaceType.ONI_TEETH,
			FaceType.DRAGON_FANGS,
			FaceType.WEASEL,
			FaceType.DEVIL_FANGS,
			FaceType.ANIMAL_TEETH,
			FaceType.WOLF_FANGS,
			FaceType.SNAKE_FANGS,
			FaceType.CAT_CANINES,
			FaceType.MANTICORE -> {
				showPart("face/human_fang")
				if (char.eyes.type === EyeType.CENTIPEDE) {
					hidePart("face/human_fang")
					showPart("face/melancholic")
				}
				if (char.face.type === FaceType.SALAMANDER_FANGS
					&& char.tongue.type === TongueType.CAVE_WYRM) {
					hidePart("face/human_fang")
					showPart("face/caveWyrm")
				}
			}
			FaceType.FIRE_SNAIL ->
				showPart("face/goo")
			FaceType.ORCA ->
				showPart("face/orca")
			FaceType.CHESHIRE,
			FaceType.CHESHIRE_SMILE ->
				showPart("face/cheshire")
			FaceType.PANDA ->
				showPart("face/panda")
			FaceType.COW_MINOTAUR ->
				showPart("face/bovine")
			FaceType.FAIRY,
			FaceType.CRAZY,
			FaceType.SMUG,
			FaceType.ELF ->
				showPart("face/fairySmile")
			FaceType.GHOST -> {
				if (char.tongue.type == TongueType.GHOST) {
					showPart("face/ghost")
				} else {
					showPart("face/human")
				}
			}
			else -> {
				showPart("face/human")
			}
		}

		val breastSize = char.breastRows.firstOrNull()?.breastRating ?: 0
		when {
			breastSize <= BreastCup.FLAT ->
				showPart("breasts/0") // FLAT
			breastSize <= BreastCup.B ->
				showPart("breasts/B$skinx") // A-B
			breastSize <= BreastCup.D ->
				showPart("breasts/D$skinx") // C-D
			else ->
				showPart("breasts/F$skinx") // DD+
		}

		// nipple colors  - TODO port model.xml code

		/* ARMS AREA */
		// TODO port model.xml code

		/* LEGS AREA */
		// TODO port model.xml code

		/* WING AREA */
		// TODO port model.xml code

		/* TAIL AREA */
		when (char.tail.type) {
			TailType.NONE -> {}
			TailType.HORSE,
			TailType.WENDIGO -> {
				showPart("tail/horse$taur")
			}
			TailType.DEMONIC -> {
				showPart("tail/demon$taur")
			}
			TailType.SPIDER_ADBOMEN -> {
				showPart("tail/spider$taur")
			}
			TailType.BEE_ABDOMEN -> {
				showPart("tail/bee_abdomen$taur")
			}
			TailType.MANTIS_ABDOMEN -> {
				showPart("tail/mantis$taur")
			}
			TailType.SQUIRREL -> {
				showPart("tail_bg/tatatoskr$taur")
			}
			TailType.CAT -> {
				if (char.tail.count >= 2) {
					showPart("tail_bg/cat2$taur")
				} else {
					showPart("tail_bg/cat$taur")
				}
			}
			TailType.BURNING -> {
				showPart("tail_bg/hellcat$taur")
			}
			TailType.TWINKASHA -> {
				showPart("tail_bg/kasha$taur")
			}
			TailType.CAVE_WYRM,
			TailType.LIZARD,
			TailType.BEHEMOTH -> {
				showPart("tail/reptile$taur")
			}
			TailType.DRACONIC -> {
				showPart("tail/dragon$taur")
			}
			TailType.RABBIT -> {
				showPart("tail/bunny$taur")
			}
			TailType.HARPY -> {
				showPart("tail/harpy$taur")
			}
			TailType.FOX,
			TailType.WOLF,
			TailType.DOG -> {
				showPart("tail_bg/fox${char.tail.count}$taur")
			}
			TailType.KANGAROO -> {
				showPart("tail/cat")
			}
			TailType.MOUSE -> {
				showPart("tail/mouse$taur")
			}
			TailType.HINEZUMI -> {
				showPart("tail/mouse_fire$taur")
			}
			TailType.COW -> {
				showPart("tail/cow$taur")
			}
			TailType.WEASEL -> {
				showPart("tail/kamaitachi$taur")
			}
			TailType.PIG -> {}
			TailType.SCORPION -> {}
			TailType.GOAT -> {
				showPart("tail/goat$taur")
			}
			TailType.RHINO -> {}
			TailType.ECHIDNA -> {}
			TailType.DEER -> {}
			TailType.SALAMANDER -> {
				showPart("tail/reptile$taur")
				showPart("tail_fg/reptile_fire$taur")
			}
			TailType.KITSHOO -> {}
			TailType.MANTICORE_PUSSYTAIL -> {
				showPart("tail/manticore$taur")
				showPart("tail_front/manticore$taur")
			}
			TailType.GARGOYLE -> {
				showPart("tail/gargoyle_mace")
			}
			TailType.GARGOYLE_2 -> {
				showPart("tail/gargoyle_axe")
			}
			TailType.ORCA -> {
				showPart("tail/orca$taur")
			}
			TailType.SHARK -> {
				showPart("tail/shark$taur")
			}
			TailType.RAIJU -> {
				showPart("tail/weasel$taur")
			}
			TailType.THUNDERBIRD -> {
				showPart("tail/thunderbird$taur")
			}
			TailType.RACCOON,
			TailType.FERRET -> {
				showPart("tail/raccoon$taur")
			}
			TailType.BEAR -> {
				if (char.ears.type == EarType.PANDA){
					showPart("tail/panda$taur")
				} else {
					showPart("tail/bear$taur")
				}
			}
			TailType.YGGDRASIL -> {}
			else -> {}
		}

		/* Penis AREA */
		// TODO port model.xml code

		/* Unique pussy AREA */
		// TODO port model.xml code

		/* BALLS AREA */
		if (char.balls > 0) {
			val ballsz = when {
				char.ballSize >= 8 -> "sillyhuge"
				char.ballSize >= 6 -> "huge"
				char.ballSize >= 4 -> "large"
				else -> "small"
			}
			showPart("balls$taur/B$skinx$ballsz$taur")
			// TODO port model.xml code
		}

		/* Muscle AREA */
		// TODO port model.xml code

		/* PATTERN AREA */
		// TODO port model.xml code

		/* HIDING INVALID ARM PATTERN AREA */
		// TODO port model.xml code

		/* Succu arm stuff */
		// TODO port model.xml code

		/* HIDING INVALID LEG PATTERN AREA */
		// TODO port model.xml code

		/* SLIME CORES */
		// TODO port model.xml code

		/* FULL BODY AND DRESS AREA */
		// TODO port model.xml code; consider items defining their own sprites

		val compose = compose()
		if (scale) {
			val x2 = createContext2D(width*2, height*2)
			x2.drawImage(compose.canvas, 0.0, 0.0, width*2.0, height*2.0)
			return x2
		}
		return compose
	}

	companion object {
		val INSTANCE by lazy {
			CharViewImage()
		}
		private val logger = LogManager.getLogger("xta.charview.CharViewImage")
	}
}
