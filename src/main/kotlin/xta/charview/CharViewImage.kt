package xta.charview

import org.w3c.dom.CanvasRenderingContext2D
import xta.game.PlayerCharacter
import xta.game.creature.body.*
import xta.logging.LogManager
import kotlin.js.Date

/*
 * Created by aimozg on 02.12.2021.
 */
class CharViewImage : CompositeImage(200, 220) {
	init {
		logger.info(null, "Loading images")
		val t0 = Date().getTime()
		loadPartsFromJson(sprites_json, charviewImages, originX = 15, originY = 10)
		logger.debug(null, "Loaded images in ${Date().getTime() - t0} ms")
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
		val skin = colordb.find("skin", char.skin.color)
		val skin2 = colordb.find("skin", char.skin.color2)
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
		setKeyColor(0xFBCAAE, skin.darken(25).saturate(25).shiftTo(0, 25))
		setKeyColor(0xECB596, skin.darken(50).saturate(25).shiftTo(0, 25))
	}

	fun renderCharacter(char: PlayerCharacter, scale: Boolean): CanvasRenderingContext2D = with(char) {
		// regexes to port model.xml:

		// \<show part *\= *\"([\w-/]+)\"\/\>
		// showPart("$1")

		// \<hide part *\= *\"(\w+\/\w+)\"\/\>
		// hidePart("$1")

		// \<hide part *\= *\"(\w+)\/\*\"\/\>
		// hideLayer("$1")

		// \<if test *\= *\"([^"]+)\"\>
		// if ($1) {

		// \<else\>
		// } else {

		// (\<\/if\>|\<\/case\>|\<\/default\>|\<\/switch\>)
		// }

		// \<case (?:test|values)\=\"([^"]+)\"\>
		// $1 -> {

		// \<default\>
		// else -> {

		// \<switch value\=\"([^"]+)\"\>
		// when ($1) {

		// \<switch\>
		// when {

		// \<\!\-\-(([^-]|-[^-]|--[^>])*)\-\-\>
		// /*$1*/

		// &[g]t;
		// >

		// Carefully!

		// \b[a]nd\b
		// &&

		// \b[o]r\b
		// ||

		hideAll()
		setupColors(char)

		val taur = if (isTaur) "_taur" else ""

		/* VARIABLES AREA */
		val hydraTails = 0 // TODO statusEffectv1(StatusEffects.HydraTailsPlayer)
		val big = if ((cocks.firstOrNull()?.length ?: 0) >= 12) "_big" else ""
		/* <set var="CaveWyrmNipples" value="hasStatusEffect(StatusEffects.GlowingNipples)"/> */
		// TODO CharViewContext.as variables
		val PlayerHasViewableOutfit = false
		val armStanceNonBannedList = false
		val PlayerHasAWeapon = false
		val PlayerHasAShield = false
		val PlayerHasADualWeapon = false
		val showClothing = false
		val playerWearsAStanceBannedDress = false
		val playerWearsAStanceBannedArmor = false
		val sleevelessList = false
		val CancerCrabStance = false
		val playerHasLargeLowerBody = false

		/* WEAPON AREA */
		// TODO use sprite ids from weapons

		/* ANTENNAE AREA */
		when (antennae.type) {
			AntennaeType.NONE -> {}
			AntennaeType.MANTIS,
			AntennaeType.BEE,
			AntennaeType.FIRE_SNAIL,
			AntennaeType.CENTIPEDE ->
				showPart("antennae/bee")
			AntennaeType.SEA_DRAGON ->
				showPart("neck/seaDragonWhisker")
			else ->
				showPart("antennae/bee")
		}

		/* HORN AREA */
		when (horns.type) {
			HornType.NONE -> {}
			HornType.DEMON -> {
				showPart("horns_bg/demon")
			}
			HornType.COW_MINOTAUR -> {
				showPart("horns_bg/cow")
				showPart("horns/cow")
			}
			HornType.GOAT -> {
				showPart("horns/devil")
				showPart("horns_bg/devil")
			}
			HornType.ORCHID -> {
				showPart("horns/orchid")
				showPart("horns_bg/orchid")
			}
			HornType.DRACONIC_X4_12_INCH_LONG -> {
				showPart("horns_bg/dragon")
			}
			HornType.UNICORN,
			HornType.ONI -> {
				showPart("horns/unicorn")
			}
			HornType.BICORN -> {
				showPart("horns/bicorn")
			}
			HornType.ONI_X2 -> {
				showPart("horns/2oni")
			}
			HornType.GOATQUAD -> {
				showPart("horns_bg/devilquad")
			}
			HornType.GHOSTLY_WISPS -> {
				showPart("horns_bg/ghost")
			}
			HornType.KRAKEN -> {
				showPart("horns_bg/kraken")
			}
			HornType.FROSTWYRM -> {
				showPart("horns/frostWyrm")
				showPart("horns/frostWyrmLeft")
				showPart("horns_bg/frostWyrm")
				showPart("horns_bg/frostWyrmLeft")
			}
			HornType.ANTLERS -> {
				showPart("horns_overclothe/deer")
			}
			HornType.SEA_DRAGON -> {
				showPart("horns_bg/seaDragon")
			}
			else -> {
				showPart("horns/2large")
			}
		}

		/* EAR AREA */
		when (ears.type) {
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
		when (eyes.type) {
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
		if (hairLength > 0) {
			when (hairType) {
				HairType.FEATHER -> {
					showPart("hair/feather")
					showPart("hair_bg/feather")
					if (hairLength >= 16) {
						showPart("hair_bg/02")
					}
				}
				HairType.GORGON -> {
					showPart("hair/gorgon")
					showPart("hair_bg/gorgon")
					if (hairLength >= 16) {
						showPart("hair_bg/02")
					}
				}
				HairType.STORM -> {
					showPart("hair/raiju")
					if (hairLength >= 16) {
						showPart("hair_bg/raiju3")
					}
				}
				HairType.BURNING -> {
					showPart("hair/hellcat")
					if (hairLength >= 16) {
						showPart("hair_bg/hellcat")
					}
				}
				HairType.GOO -> {
					showPart("hair/slime")
					if (hairLength >= 16) {
						showPart("hair_bg/slime2")
					}
				}
				HairType.FLUFFY -> {
					showPart("hair/yeti")
					if (hairLength >= 16) {
						showPart("hair_bg/yeti")
					}
				}
				HairType.CRAZY -> {
					showPart("hair/gremlin")
				}
				HairType.RATATOSKR -> {
					showPart("hair/ratatoskr")
					showPart("hair_bg/ratatoskr")
					if (hairLength >= 16) {
						showPart("hair_bg/02")
					}
				}
				HairType.PRISMATIC -> {
					showPart("hair/rainbow")
					showPart("hair_bg/rainbow")
				}
				else -> {
					showPart("hair/0")
					if (hairLength >= 16) {
						showPart("hair_bg/02")
					} else {
						showPart("hair_bg/0")
					}
				}
			}
		}

		/* HAIR STYLE AREA */
		if (hairLength > 0) {
			if (hairType !in arrayOf(HairType.FLUFFY, HairType.GORGON, HairType.ANEMONE, HairType.GOO)) {
				if (hairType !in arrayOf(
						HairType.STORM,
						HairType.BURNING,
						HairType.QUILL,
						HairType.CRAZY,
						HairType.RATATOSKR,
						HairType.PRISMATIC
					)
				) {
					if (hairStyle != HairStyle.PLAIN) {
						hidePart("hair/0")
						hidePart("hair_bg/02")
						showPart("hair/feather")
						showPart("hair_bg/feather")
					}
				}
				when (hairStyle) {
					HairStyle.WILD ->
						if (hairLength >= 16) {
							if (hairType !in arrayOf(HairType.STORM, HairType.BURNING)) {
								hideLayer("hair_bg")
								showPart("hair_bg/02")
							}
						}
					HairStyle.PONYTAIL -> {
						showPart("hair_bg/ponytail")
						hidePart("hair_bg/raiju3")
						hidePart("hair_bg/hellcat")
					}
					HairStyle.LONGTRESSES -> {
						showPart("hair_bg/longTresse")
						hidePart("hair_bg/raiju3")
						hidePart("hair_bg/hellcat")
					}
					HairStyle.TWINTAILS -> {
						showPart("hair_bg/twinPigtail")
						hidePart("hair_bg/raiju3")
						hidePart("hair_bg/hellcat")
					}
					HairStyle.DWARVEN -> {
						showPart("hair_bg/dwarven")
						hidePart("hair_bg/raiju3")
						hidePart("hair_bg/hellcat")
					}
					HairStyle.SNOWLILY -> {
						hideLayer("hair")
						hideLayer("hair_bg")
						showPart("hair/yukionna")
						showPart("hair_bg/yukionna")
					}
					HairStyle.FOURWIND -> {
						hideLayer("hair")
						hideLayer("hair_bg")
						showPart("hair/fourWindBraid")
						showPart("hair_bg/fourWindBraid")
					}
					HairStyle.FOURWINDL -> {
						hideLayer("hair")
						hideLayer("hair_bg")
						showPart("hair/fourWindBraid")
						showPart("hair_bg/02")
					}
					else -> {
						if (hairType !in arrayOf(
								HairType.STORM,
								HairType.BURNING,
								HairType.QUILL,
								HairType.FEATHER,
								HairType.CRAZY,
								HairType.RATATOSKR,
								HairType.PRISMATIC
							)
						) {
							if (hairLength < 16) {
								hideLayer("hair")
								hideLayer("hair_bg")
								showPart("hair/0")
								showPart("hair_bg/0")
							} else {
								hideLayer("hair")
								hideLayer("hair_bg")
								showPart("hair/0")
								showPart("hair_bg/02")
							}
						}
					}
				}
			}
		}

		val skinb: String
		val skinx: String
		when (skin.coverage) {
			SkinCoverage.NONE -> when (skin.baseType) {
				SkinBaseType.GOO -> {
					skinb = "goo"
					skinx = "goo"
				}
				else -> {
					skinb = "human"
					if (skin.basePattern == SkinBasePatternType.ORCA_UNDERBODY) {
						skinx = "orca"
					} else if (skin.basePattern == SkinBasePatternType.SEA_DRAGON_UNDERBODY) {
						skinx = "orca"
						showPart("torso_pattern/Bbioluminescence")
					} else {
						skinx = "human"
					}
				}
			}
			SkinCoverage.LOW,
			SkinCoverage.MEDIUM -> when (skin.coatType) {
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
			SkinCoverage.COMPLETE -> when (skin.coatType) {
				SkinCoatType.FUR -> {
					if (ears.type == EarType.PANDA) {
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
					if (skin.coatPattern == SkinCoatPatternType.BEE_STRIPES) {
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
		if (cor >= 100 && eyes.type != EyeType.CENTIPEDE) {
			showPart("forehead/SpecialForeheadShadow")
		}

		// face type
		when (face.type) {
			FaceType.HUMAN -> {
				when {
					skin.baseType == SkinBaseType.GOO ->
						showPart("face/goo")
					cor >= 100 -> {
						if (lowerBody.type == LowerBodyType.FLOWER_LILIRAUNE) {
							showPart("face/lilirauneCorruptedSmile")
							showPart("forehead/lilirauneCorruptedShadow")
						} else {
							showPart("face/fairySmile")
						}
					}
					cor >= 50 ->
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
				if (eyes.type === EyeType.CENTIPEDE) {
					hidePart("face/human_fang")
					showPart("face/melancholic")
				}
				if (face.type === FaceType.SALAMANDER_FANGS
					&& tongue.type === TongueType.CAVE_WYRM
				) {
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
				if (tongue.type == TongueType.GHOST) {
					showPart("face/ghost")
				} else {
					showPart("face/human")
				}
			}
			else -> {
				showPart("face/human")
			}
		}

		// CHEST AREA
		val breastSize = breastRows.firstOrNull()?.breastRating ?: 0
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
		when (arms.type) {
			ArmType.HUMAN, ArmType.KITSUNE, ArmType.ELF, ArmType.RAIJU, ArmType.CENTIPEDE -> {
				if (PlayerHasViewableOutfit) {
					if (armStanceNonBannedList) {
						if (cor >= 50) {
							if (skin.coverage == SkinCoverage.NONE) {
								hideLayer("hands")
								hideLayer("arms")
								if (PlayerHasAWeapon) {
									showPart("arms_bg/human")
								} else {
									hideLayer("arms_bg")
									hideLayer("hands_bg")
									showPart("arms_bg/demonlewd")
								}
								if (PlayerHasAShield || PlayerHasADualWeapon) {
									showPart("arms/human")
								} else {
									hideLayer("arms")
									hideLayer("hands")
									showPart("arms/demonlewd")
									showPart("arms_fg/demonlewd")
								}
							}
						}
					} else {
						if (femininity >= 40) {
							if (cor >= 50) {
								if (skin.coverage == SkinCoverage.NONE) {
									if (showClothing) {
										hideLayer("hands")
										hideLayer("arms")
										if (PlayerHasAWeapon) {
											showPart("arms_bg/human")
										} else {
											hideLayer("arms_bg")
											hideLayer("hands_bg")
											showPart("arms_bg/demonlewd")
										}
										if (PlayerHasAShield || PlayerHasADualWeapon) {
											showPart("arms/human")
										} else {
											hideLayer("arms")
											hideLayer("hands")
											showPart("arms/demonlewd")
											showPart("arms_fg/demonlewd")
										}
									}
								}
							}
						}
					}
				}
			}
			ArmType.ONI -> {}
			ArmType.HARPY, ArmType.PHOENIX -> {
				showPart("hands/harpy")
				showPart("hands_bg/harpy")
			}
			ArmType.GHOST -> {
				if (femininity >= 40) {
					hideLayer("hands")
					hideLayer("hands_bg")
					hideLayer("arms")
					hideLayer("arms_bg")
					showPart("arms/demonlewd")
					if (PlayerHasAWeapon) {
						showPart("arms_bg/human")
					} else {
						hideLayer("arms_bg")
						hideLayer("hands_bg")
						showPart("arms_bg/demonlewd")
					}
					if (PlayerHasAShield || PlayerHasADualWeapon) {
						showPart("arms/human")
					} else {
						hideLayer("arms")
						hideLayer("hands")
						showPart("arms/demonlewd")
						showPart("arms_fg/demonlewd")
					}
				}
			}
			ArmType.SPIDER -> {
				showPart("hands/chitin2")
				showPart("hands_bg/chitin2")
			}
			ArmType.MANTIS -> {
				showPart("hands/chitin2")
				showPart("hands/blade-kamaitachiMantis")
				showPart("hands_bg/chitin2")
				showPart("hands_bg/blade-kamaitachiMantis")
			}
			ArmType.KAMAITACHI -> {
				showPart("hands/furracoon")
				showPart("hands/blade-kamaitachiMantis")
				showPart("hands_bg/furracoon")
				showPart("hands_bg/blade-kamaitachiMantis")
			}
			ArmType.SQUIRREL -> {
				showPart("hands/furracoon")
				showPart("hands_bg/furracoon")
				showPart("overhands/ratatoskrRight")
				showPart("overhands/ratatoskrLeft")
			}
			ArmType.BEE -> {
				hideLayer("arms")
				hideLayer("arms_bg")
				hideLayer("hands_bg")
				showPart("arms/bee")
				showPart("arms_bg/bee")
			}
			ArmType.SALAMANDER, ArmType.CAVE_WYRM -> {
				hideLayer("arms")
				hideLayer("arms_bg")
				showPart("arms/pscales")
				showPart("arms_bg/pscales")
				showPart("hands/scales")
				showPart("hands_bg/scales")
			}
			ArmType.PLANT, ArmType.PLANT2 -> {
				showPart("hands/alraune")
				showPart("hands_bg/alraune")
			}
			ArmType.SHARK -> {
				showPart("hands/fins-orca")
				showPart("hands_bg/fins-orca")
			}
			ArmType.GARGOYLE, ArmType.GARGOYLE_2 -> {
				if (PlayerHasAWeapon || PlayerHasAShield || PlayerHasADualWeapon) {
					showPart("arms/human")
					showPart("arms_bg/human")
				} else {
					hideLayer("arms")
					hideLayer("arms_bg")
					showPart("arms/gargoyle_sit")
				}
			}
			ArmType.YETI -> {
				hideLayer("arms")
				hideLayer("arms_bg")
				showPart("arms/yeti")
				showPart("hands/yeti")
			}
			ArmType.CAT, ArmType.BEAR, ArmType.MELKIE -> {
				showPart("hands/fur")
				showPart("hands_bg/fur")
				if (ears.type == EarType.PANDA) {
					hidePart("hands/fur")
					hidePart("hands_bg/fur")
					showPart("hands/panda")
					showPart("hands_bg/panda")
				}
			}
			ArmType.LION -> {
				showPart("hands/fur")
				showPart("hands_bg/fur")
				if (legCount == 2) {
					if (lowerBody.type == LowerBodyType.LION) {
						hideLayer("arms")
						hideLayer("arms_bg")
						hideLayer("hands")
						hideLayer("hands_bg")
						hideLayer("weapon")
						hideLayer("shield")
						when {
							hasPartialCoatOfType(
								SkinCoatType.SCALES,
								SkinCoatType.AQUA_SCALES,
								SkinCoatType.DRAGON_SCALES
							) -> {
								showPart("arms/manticore_sit_pscales")
								showPart("arms_fg/manticore_sit")
							}
							hasFullCoatOfType(SkinCoatType.FUR) -> {
								showPart("arms/manticore_sit_fur")
								showPart("arms_fg/manticore_sit")
							}
							hasFullCoatOfType(SkinCoatType.CHITIN) -> {
								showPart("arms/manticore_sit_chitin")
								showPart("arms_fg/manticore_sit")
							}
							hasFullCoatOfType(
								SkinCoatType.SCALES,
								SkinCoatType.AQUA_SCALES,
								SkinCoatType.DRAGON_SCALES
							) -> {
								showPart("arms/manticore_sit_scales")
								showPart("arms_fg/manticore_sit")
							}
							else -> {
								showPart("arms/manticore_sit")
								showPart("arms_fg/manticore_sit")
							}
						}
					}
				}
			}
			ArmType.WOLF -> {
				showPart("hands/fur")
				showPart("hands_bg/fur")
				if (legCount == 2) {
					if (lowerBody.type == LowerBodyType.WOLF && !playerWearsAStanceBannedDress && !playerWearsAStanceBannedArmor) {
						hideLayer("arms")
						hideLayer("arms_bg")
						hideLayer("hands")
						hideLayer("hands_bg")
						hideLayer("weapon")
						hideLayer("shield")
						showPart("arms/feral_human")
						showPart("arms_bg/feral_human")
						when {
							hasPartialCoatOfType(
								SkinCoatType.SCALES,
								SkinCoatType.AQUA_SCALES,
								SkinCoatType.DRAGON_SCALES
							) -> {
								showPart("arms_overlay/feral_pscales")
								showPart("arms_overlay_bg/feral_pscales")
							}
							hasFullCoatOfType(SkinCoatType.FUR) -> {
								showPart("arms_overlay/feral_fur")
								showPart("arms_overlay_bg/feral_fur")
							}
							hasFullCoatOfType(SkinCoatType.CHITIN) -> {
								showPart("arms_overlay/feral_chitin")
								showPart("arms_overlay_bg/feral_chitin")
							}
							hasFullCoatOfType(
								SkinCoatType.SCALES,
								SkinCoatType.AQUA_SCALES,
								SkinCoatType.DRAGON_SCALES
							) -> {
								showPart("arms_overlay/feral_scales")
								showPart("arms_overlay_bg/feral_scales")
							}
						}
					}
				}
			}
			ArmType.DRACONIC -> {
				showPart("hands/scale")
				showPart("hands_bg/scale")
				if (PlayerHasViewableOutfit) {
					if (sleevelessList) {
						if (legCount == 2) {
							if (lowerBody.type == LowerBodyType.DRAGON) {
								hideLayer("arms")
								hideLayer("arms_bg")
								hideLayer("hands")
								hideLayer("hands_bg")
								hideLayer("weapon")
								hideLayer("shield")
								showPart("arms/feral_dragon")
								showPart("arms_bg/feral_dragon")
								when {
									hasPartialCoatOfType(
										SkinCoatType.SCALES,
										SkinCoatType.AQUA_SCALES,
										SkinCoatType.DRAGON_SCALES
									) -> {
										showPart("arms_overlay/feral_pscales")
										showPart("arms_overlay_bg/feral_pscales")
									}
									hasFullCoatOfType(SkinCoatType.FUR) -> {
										showPart("arms_overlay/feral_fur")
										showPart("arms_overlay_bg/feral_fur")
									}
									hasFullCoatOfType(SkinCoatType.CHITIN) -> {
										showPart("arms_overlay/feral_chitin")
										showPart("arms_overlay_bg/feral_chitin")
									}
									hasFullCoatOfType(
										SkinCoatType.SCALES,
										SkinCoatType.AQUA_SCALES,
										SkinCoatType.DRAGON_SCALES
									) -> {
										showPart("arms_overlay/feral_scales")
										showPart("arms_overlay_bg/feral_scales")
									}
								}
							}
						}
					}
				} else {
					if (legCount == 2) {
						if (lowerBody.type == LowerBodyType.DRAGON) {
							hideLayer("arms")
							hideLayer("arms_bg")
							hideLayer("hands")
							hideLayer("hands_bg")
							hideLayer("weapon")
							hideLayer("shield")
							showPart("arms/feral_dragon")
							showPart("arms_bg/feral_dragon")
							when {
								hasPartialCoatOfType(
									SkinCoatType.SCALES,
									SkinCoatType.AQUA_SCALES,
									SkinCoatType.DRAGON_SCALES
								) -> {
									showPart("arms_overlay/feral_pscales")
									showPart("arms_overlay_bg/feral_pscales")
								}
								hasFullCoatOfType(SkinCoatType.FUR) -> {
									showPart("arms_overlay/feral_fur")
									showPart("arms_overlay_bg/feral_fur")
								}
								hasFullCoatOfType(SkinCoatType.CHITIN) -> {
									showPart("arms_overlay/feral_chitin")
									showPart("arms_overlay_bg/feral_chitin")
								}
								hasFullCoatOfType(
									SkinCoatType.SCALES,
									SkinCoatType.AQUA_SCALES,
									SkinCoatType.DRAGON_SCALES
								) -> {
									showPart("arms_overlay/feral_scales")
									showPart("arms_overlay_bg/feral_scales")
								}
							}
						}
					}

				}
			}
			ArmType.DISPLACER -> {
				hideLayer("arms")
				hideLayer("arms_bg")
				hideLayer("hands")
				hideLayer("hands_bg")
				when {
					hasPartialCoatOfType(SkinCoatType.SCALES, SkinCoatType.AQUA_SCALES, SkinCoatType.DRAGON_SCALES) -> {
						showPart("arms/displacerquad_pscales")
						showPart("arms_bg/displacerquad_pscales")
					}
					hasFullCoatOfType(SkinCoatType.FUR) -> {
						showPart("arms/displacerquad_fur")
						showPart("arms_bg/displacerquad_fur")
					}
					hasFullCoatOfType(SkinCoatType.CHITIN) -> {
						showPart("arms/displacerquad_chitin")
						showPart("arms_bg/displacerquad_chitin")
					}
					hasFullCoatOfType(SkinCoatType.SCALES, SkinCoatType.AQUA_SCALES, SkinCoatType.DRAGON_SCALES) -> {
						showPart("arms/displacerquad_scales")
						showPart("arms_bg/displacerquad_scales")
					}
					else -> {
						showPart("arms/displacerquad_human")
						showPart("arms_bg/displacerquad_human")
					}
				}
			}
			ArmType.LIZARD, ArmType.HYDRA -> {
				showPart("hands/scales")
				showPart("hands_bg/scales")
			}
			ArmType.ORCA, ArmType.SEA_DRAGON -> {
				hideLayer("arms")
				hideLayer("arms_bg")
				showPart("arms_bg/orca")
				showPart("arms/orca")
				showPart("hands/fins-orca")
				showPart("hands_bg/fins-orca")
			}
			ArmType.DEVIL, ArmType.RAIJU_PAWS -> {
				showPart("hands/devil")
				showPart("hands_bg/devil")
			}
			ArmType.HINEZUMI -> {
				if (lowerBody.type == LowerBodyType.HINEZUMI && !playerWearsAStanceBannedDress && !playerWearsAStanceBannedArmor) {
					hideLayer("arms")
					hideLayer("arms_bg")
					hideLayer("weapon")
					hideLayer("shield")
					when {
						hasPartialCoatOfType(
							SkinCoatType.SCALES,
							SkinCoatType.AQUA_SCALES,
							SkinCoatType.DRAGON_SCALES
						) -> {
							showPart("arms/hinezumistanced_pscales")
						}
						hasFullCoatOfType(SkinCoatType.FUR) -> {
							showPart("arms/hinezumistanced_fur")
						}
						hasFullCoatOfType(SkinCoatType.CHITIN) -> {
							showPart("arms/hinezumistanced_chitin")
						}
						hasFullCoatOfType(
							SkinCoatType.SCALES,
							SkinCoatType.AQUA_SCALES,
							SkinCoatType.DRAGON_SCALES
						) -> {
							showPart("arms/hinezumistanced_scales")
						}
						else -> {
							showPart("arms/hinezumistanced_human")
						}
					}
				} else {
					showPart("overhands/fire")
					showPart("hands/fur")
					showPart("overhands_bg/fire")
					showPart("hands_bg/fur")

				}
			}
			ArmType.RACCOON, ArmType.FOX, ArmType.SPHINX, ArmType.WEASEL -> {
				showPart("hands/furracoon")
				showPart("hands_bg/furracoon")
			}
			ArmType.KRAKEN -> {
				showPart("hands/kraken")
				showPart("hands_bg/kraken")
			}
			ArmType.FROSTWYRM -> {
				showPart("hands/frostWyrmright")
				showPart("hands_bg/frostWyrmleft")
			}
			ArmType.WENDIGO -> {
				showPart("hands/wendigo")
				showPart("hands_bg/wendigo")
			}
			ArmType.GAZER -> {
				hideLayer("arms")
				hideLayer("arms_bg")
				showPart("arms/gazer")
				showPart("arms_bg/gazer")
			}
			else -> {
				/* use skintype defaults */
			}
		}
		if (CancerCrabStance) {
			if (!playerWearsAStanceBannedDress && !playerWearsAStanceBannedArmor) {
				hideLayer("arms")
				hideLayer("arms_bg")
				hideLayer("hands")
				hideLayer("hands_bg")
				showPart("arms/crabStance")
			}
		}

		/* LEGS AREA */
		when (lowerBody.type) {
			LowerBodyType.HUMAN, LowerBodyType.ELF, LowerBodyType.DEMONIC_HIGH_HEELS -> {
				/*see switch[value=skin.coverage]*/
				if (PlayerHasViewableOutfit) {
					if (armStanceNonBannedList) {
						if ((femininity >= 40) && (cor >= 50) && (skin.coverage == SkinCoverage.NONE)) {
							when (arms.type) {
								ArmType.HUMAN, ArmType.ELF, ArmType.KITSUNE, ArmType.RAIJU, ArmType.CENTIPEDE -> {
									hideLayer("feet")
									hideLayer("legs")
									showPart("legs/demonlewd")
								}
								else -> {
								}
							}
						}
					}
				} else {
					if ((femininity >= 40) && (cor >= 50) && (skin.coverage == SkinCoverage.NONE)) {
						when (arms.type) {
							ArmType.HUMAN, ArmType.ELF, ArmType.KITSUNE, ArmType.RAIJU, ArmType.CENTIPEDE -> {
								hideLayer("feet")
								hideLayer("legs")
								showPart("legs/demonlewd")
							}
							else -> {
							}
						}
					}
				}
			}
			LowerBodyType.HOOFED,LowerBodyType.PONY,LowerBodyType.CLOVEN_HOOFED -> {
                hideLayer("legs")
                when {
                    legCount==4 -> {
						showPart("legs/centaur")
                        showPart("legs_fg/centaur")
                    }
                    else -> {
						when {
							hasPartialCoatOfType(SkinCoatType.SCALES,SkinCoatType.AQUA_SCALES,SkinCoatType.DRAGON_SCALES) -> {
								showPart("legs/hoof_pscales")
							}
							hasFullCoatOfType(SkinCoatType.FUR) -> {
								showPart("legs/hoof_fur")
							}
							hasFullCoatOfType(SkinCoatType.CHITIN) -> {
								showPart("legs/hoof_chitin")
							}
							hasFullCoatOfType(SkinCoatType.SCALES,SkinCoatType.AQUA_SCALES,SkinCoatType.DRAGON_SCALES) -> {
								showPart("legs/hoof_scales")
							}
							else -> {
								showPart("legs/hoof_human")
							}
						}
                    }
                }
            }
            LowerBodyType.BUNNY,LowerBodyType.KANGAROO,LowerBodyType.CAT,LowerBodyType.DOG,LowerBodyType.FOX,LowerBodyType.RAIJU,LowerBodyType.WEASEL,LowerBodyType.RACCOON,LowerBodyType.FERRET,LowerBodyType.MOUSE,LowerBodyType.HINEZUMI,LowerBodyType.BEAR,LowerBodyType.SQUIRREL -> {
                when {
                    legCount==4 -> {
                        hideLayer("legs")
						showPart("legs/cattaur")
                        showPart("legs_fg/cattaur")
						if (lowerBody.type==LowerBodyType.BEAR) {
							hideLayer("legs")
							showPart("legs/beartaur")
							showPart("legs_fg/beartaur")
							if (ears.type==EarType.PANDA) {
								hideLayer("legs")
								hideLayer("legs_fg")
								showPart("legs/pandataur")
								showPart("legs_fg/pandataur")
							}
						}
                    }
					lowerBody.type==LowerBodyType.FOX -> {
						showPart("feet/furracoon")
					}
					lowerBody.type==LowerBodyType.BUNNY -> {
						showPart("feet/bunny")
					}
					lowerBody.type==LowerBodyType.RACCOON -> {
						showPart("feet/furracoon")
					}
					lowerBody.type==LowerBodyType.FERRET -> {
						showPart("feet/furracoon")
					}
					lowerBody.type==LowerBodyType.WEASEL -> {
						showPart("feet/furracoon")
					}
					lowerBody.type==LowerBodyType.SQUIRREL -> {
						showPart("feet/ratatoskr")
					}
					lowerBody.type==LowerBodyType.BEAR -> {
							showPart("feet/furracoon")
							if (ears.type==EarType.PANDA) {
								hideLayer("legs")
								showPart("legs/panda")
								showPart("feet/panda")
							}
					}
					lowerBody.type==LowerBodyType.HINEZUMI -> {
						if ((arms.type==ArmType.HINEZUMI) && (playerWearsAStanceBannedDress == false) && (playerWearsAStanceBannedArmor == false)) {
							hideLayer("legs")
							hideLayer("feet")
							} else {

								showPart("overfeet/fire")
						}
					}
					else -> {
						showPart("feet/fur")
					}
                }
            }
			LowerBodyType.NAGA -> {
                hideLayer("legs")
                showPart("legs/naga")
            }
			LowerBodyType.FROSTWYRM -> {
                hideLayer("legs")
                showPart("legs/frostWyrm")
            }
			LowerBodyType.MINDBREAKER -> {
                hideLayer("legs")
                showPart("legs/mindbreaker")
            }
			LowerBodyType.CANCER -> {
                hideLayer("legs")
                showPart("legs_fg/crabtaur")
            }
			LowerBodyType.ATLACH_NACHA -> {
                hideLayer("legs")
				hidePart("tail/spider")
                showPart("legs_fg/atlach")
				showPart("overGenitals/atlach")
            }
			LowerBodyType.HYDRA -> {
                hideLayer("legs")
				hideLayer("feet")
				hideLayer("tail")
				when (hydraTails) {
					1,2,3 -> {
						showPart("legs_fg/hydra2")
						showPart("legs_bg/hydra2")
                    }
					4,5,6 -> {
						showPart("legs_fg/hydra3")
						showPart("legs_bg/hydra3")
                    }
					7,8 -> {
						showPart("legs_fg/hydra4")
						showPart("legs_bg/hydra4")
                    }
					9,10 -> {
						showPart("legs_fg/hydra5")
						showPart("legs_bg/hydra5")
                    }
					11,12 -> {
						showPart("legs_fg/hydra6")
						showPart("legs_bg/hydra6")
                    }
                    else -> {
						showPart("legs_fg/hydra2")
						showPart("legs_bg/hydra2")
                    }
                }
            }
			LowerBodyType.MELKIE -> {
                hideLayer("legs")
                showPart("legs/melkie")
            }
            LowerBodyType.BEE,LowerBodyType.CRAB -> {
                hideLayer("legs")
                showPart("legs/chitin2")
            }
            LowerBodyType.GOO -> {
                hideLayer("legs")
                showPart("legs/gooblob")
            }
			LowerBodyType.LIZARD,LowerBodyType.DRAGON,LowerBodyType.SALAMANDER,LowerBodyType.CAVE_WYRM -> {
                if (legCount==4) {
                    hideLayer("legs")
					showPart("legs/reptaur")
                    showPart("legs_fg/reptaur")
					} else {
						showPart("feet/scales")
						if (PlayerHasViewableOutfit) {
							if (armStanceNonBannedList) {
								if (arms.type==ArmType.DRACONIC) {
									hideLayer("legs")
									hideLayer("feet")
									showPart("legs/dragon")
								}
							}
							} else {
								if (arms.type==ArmType.DRACONIC) {
									hideLayer("legs")
									hideLayer("feet")
									showPart("legs/dragon")
								}
						}
                }
            }
            LowerBodyType.HARPY -> {
                hideLayer("legs")
                hideLayer("feet")
                when {
                    hasFullCoatOfType(SkinCoatType.FUR) -> {
                        showPart("legs/harpy_fur")
                    }
                    hasFullCoatOfType(SkinCoatType.SCALES) -> {
                        showPart("legs/harpy_scales")
                    }
                    hasFullCoatOfType(SkinCoatType.CHITIN) -> {
                        showPart("legs/harpy_chitin")
                    }
                    hasPartialCoatOfType(SkinCoatType.SCALES) -> {
                        showPart("legs/harpy_pscales")
                    }
                    else -> {
                        showPart("legs/harpy_human")
                    }
                }
            }
            LowerBodyType.CHITINOUS_SPIDER_LEGS -> {
                hideLayer("legs")
                showPart("legs/chitin2")
            }
            LowerBodyType.DRIDER -> {
				hidePart("tail/spider")
                hideLayer("legs")
                showPart("legs_fg/drider")
            }
            /*LowerBodyType.ECHIDNA -> {TODO }*/
            LowerBodyType.SCYLLA -> {
                hideLayer("legs")
                showPart("legs/scylla")
            }
            LowerBodyType.KRAKEN -> {
                hideLayer("legs")
                showPart("legs/kraken")
            }
            LowerBodyType.MANTIS -> {/*TODO */
                hideLayer("legs")
                showPart("legs/chitin")
            }
            /*LowerBodyType.GARGOYLE -> {TODO }*/
            /*LowerBodyType.PLANT_HIGH_HEELS -> {TODO }*/
            /*LowerBodyType.PLANT_ROOT_CLAWS -> {TODO }*/
            LowerBodyType.PLANT_FLOWER -> {
                hideLayer("legs")
                showPart("legs_fg/alraune")
            }
			LowerBodyType.FLOWER_LILIRAUNE -> {
                hideLayer("legs")
				showPart("legs_fg/liliraune")
                showPart("legs_bg/liliraune")
				showPart("torso/liliraune")
				showPart("face/liliraune")
				showPart("arms/liliraune")
				showPart("arms_bg/liliraune")

				showPart("ears/liliraune")
				showPart("horns/LilirauneOrchid")
				showPart("horns_bg/LilirauneOrchid")
				showPart("uniquePlantBath/lilirauneBath")

				/* HAIR AREA */
                if (hairLength < 16) {
					showPart("hair/liliraune")
					showPart("hair_bg/liliraune")
					} else {
						showPart("hair/liliraune")
					showPart("hair_bg/liliraune")
                    showPart("hair_bg/lilirauneLong")
                }

				/* CHEST AREA */
				if (breastRows.size > 0) {
					when {
						breastRows[0].breastRating <= BreastCup.FLAT -> {/*FLAT*/
							showPart("breasts/0liliraune")
						}
						breastRows[0].breastRating <= BreastCup.B -> {/*A-B*/
							showPart("breasts/Bliliraune")
						}
						breastRows[0].breastRating <= BreastCup.C -> {/*C*/
							showPart("breasts/Dliliraune")
						}
						breastRows[0].breastRating <= BreastCup.D -> {/*D*/
							showPart("breasts/Dliliraune")
						}
						breastRows[0].breastRating <= BreastCup.E -> {/*DD-E*/
							showPart("breasts/Fliliraune")
						}
						else -> {
							showPart("breasts/Fliliraune")
						}
					}
				}
            }
			LowerBodyType.SHARK,LowerBodyType.ORCA,LowerBodyType.SEA_DRAGON -> {
				if (lowerBody.type==LowerBodyType.ORCA) {
					hideLayer("legs")
					showPart("legs/orca")
				}
				if (legCount==4) {
					hideLayer("legs")
					showPart("legs/aquataur")
					showPart("legs_fg/aquataur")
				}
            }
			/*Manticore section*/
            LowerBodyType.LION -> {
                showPart("feet/fur")
				if (legCount==4) {
                    hideLayer("legs")
					hideLayer("feet")
					showPart("legs/cattaur")
                    showPart("legs_fg/cattaur")
					} else {
						when {
							arms.type==ArmType.LION -> {
								hideLayer("legs")
								hideLayer("feet")
								when {
									hasPartialCoatOfType(SkinCoatType.SCALES,SkinCoatType.AQUA_SCALES,SkinCoatType.DRAGON_SCALES) -> {
										showPart("legs/manticore_sit_pscales")
									}
									hasFullCoatOfType(SkinCoatType.FUR) -> {
										showPart("legs/manticore_sit_fur")
									}
									hasFullCoatOfType(SkinCoatType.CHITIN) -> {
										showPart("legs/manticore_sit_chitin")
									}
									hasFullCoatOfType(SkinCoatType.SCALES,SkinCoatType.AQUA_SCALES,SkinCoatType.DRAGON_SCALES) -> {
									showPart("legs/manticore_sit_scales")
									}
									else -> {
										showPart("legs/manticore_sit")
									}
								}
							}
							arms.type==ArmType.DISPLACER -> {
								hideLayer("legs")
								hideLayer("feet")
								when {
									hasPartialCoatOfType(SkinCoatType.SCALES,SkinCoatType.AQUA_SCALES,SkinCoatType.DRAGON_SCALES) -> {
										showPart("legs/displacer_pscales")
									}
									hasFullCoatOfType(SkinCoatType.FUR) -> {
										showPart("legs/displacer_fur")
									}
									hasFullCoatOfType(SkinCoatType.CHITIN) -> {
										showPart("legs/displacer_chitin")
									}
									hasFullCoatOfType(SkinCoatType.SCALES,SkinCoatType.AQUA_SCALES,SkinCoatType.DRAGON_SCALES) -> {
										showPart("legs/displacer_scales")
									}
									else -> {
										showPart("legs/displacer_human")
									}
								}
							}
							else -> {
							}
						}
                }
            }
			LowerBodyType.WOLF -> {
                showPart("feet/fur")
				if (legCount==4) {
                    hideLayer("legs")
					hideLayer("feet")
					showPart("legs/cattaur")
                    showPart("legs_fg/cattaur")
					} else {
						when {
							(arms.type==ArmType.WOLF) && (playerWearsAStanceBannedDress == false) && (playerWearsAStanceBannedArmor == false) -> {
								hideLayer("legs")
								hideLayer("feet")
								when {
									hasPartialCoatOfType(SkinCoatType.SCALES,SkinCoatType.AQUA_SCALES,SkinCoatType.DRAGON_SCALES) -> {
										showPart("legs/displacer_pscales")
									}
									hasFullCoatOfType(SkinCoatType.FUR) -> {
										showPart("legs/displacer_fur")
									}
									hasFullCoatOfType(SkinCoatType.CHITIN) -> {
										showPart("legs/displacer_chitin")
									}
									hasFullCoatOfType(SkinCoatType.SCALES,SkinCoatType.AQUA_SCALES,SkinCoatType.DRAGON_SCALES) -> {
										showPart("legs/displacer_scales")
									}
									else -> {
										showPart("legs/displacer_human")
									}
								}
							}
						}
                }
            }
			LowerBodyType.GHOST_2 -> {
                hideLayer("legs")
                showPart("legs/ghost")
            }
			LowerBodyType.YETI -> {
                showPart("feet/yeti")
            }
			LowerBodyType.GARGOYLE,LowerBodyType.GARGOYLE_2 -> {
                hideLayer("legs")
				showPart("legs/gargoyle_sit")
            }
			LowerBodyType.PLANT_HIGH_HEELS -> {
                showPart("feet/alraune")
            }
            LowerBodyType.CENTIPEDE -> {
                hideLayer("legs")
                showPart("legs/centipede")
                showPart("legs_bg/centipede")
            }
			LowerBodyType.FIRE_SNAIL -> {
                hideLayer("legs")
                showPart("legs_fg/snail")
            }
			LowerBodyType.WENDIGO -> {
				hideLayer("legs")
                showPart("legs/wendigo")
            }
			LowerBodyType.GAZER -> {
				hideLayer("legs")
                showPart("legs_truefg/gazer")
            }
            else -> {
                /*see switch[value=skin.coverage]*/
            }
        }

		/* WING AREA */
		when (wings.type) {
			WingType.NONE -> {}
			WingType.BEE_SMALL, WingType.BEE_LARGE -> {
				showPart("wings/bee")
			}
			WingType.HARPY, WingType.FEATHERED_SPHINX, WingType.FEATHERED_ALICORN, WingType.FEATHERED_PHOENIX, WingType.FEATHERED_AVIAN, WingType.FEATHERED_LARGE -> {
				showPart("wings/feather_large")
				showPart("wings_bg/feather_large")
				if (playerHasLargeLowerBody) {
					showPart("wings_front/feather_large")
				}
			}
			WingType.IMP, WingType.BAT_LIKE_TINY, WingType.BAT_LIKE_LARGE, WingType.BAT_LIKE_LARGE_2, WingType.GARGOYLE_LIKE_LARGE -> {
				showPart("wings/demon")
				showPart("wings_bg/demon")
				if (playerHasLargeLowerBody) {
					showPart("wings_front/demon")
				}
			}
			WingType.NIGHTMARE -> {
				showPart("wings/nightmare")
				showPart("wings_bg/nightmare")
				if (isTaur) {
					showPart("wings_front/nightmare")
				}
			}
			WingType.DRACONIC_SMALL, WingType.DRACONIC_LARGE -> {
				showPart("wings_bg/scales")
				showPart("wings/scales")
				if (playerHasLargeLowerBody) {
					showPart("wings_front/scales")
				}
			}
			WingType.DRACONIC_HUGE -> {
				showPart("wings_bg/greatDragonLeft")
				showPart("wings_bg/greatDragonRight")
			}
			WingType.GIANT_DRAGONFLY, WingType.MANTIS_SMALL, WingType.MANTIS_LARGE, WingType.MANTIS_LARGE_2 -> {
				showPart("wings/bee")
			}
			WingType.MANTICORE_SMALL, WingType.MANTICORE_LARGE -> {
				showPart("wings/mantibig")
				showPart("wings_bg/mantibig")
				if (playerHasLargeLowerBody) {
					showPart("wings_front/mantibig")
				}
			}
			WingType.FEY_DRAGON -> {
				showPart("wings/fairy")
				showPart("wings_bg/fairy")
				if (playerHasLargeLowerBody) {
					showPart("wings_front/fairy")
				}
			}
			WingType.FAIRY -> {
				showPart("wings/fairy")
				showPart("wings_bg/fairy")
				if (playerHasLargeLowerBody) {
					showPart("wings_front/fairy")
				}
				if (!PlayerHasViewableOutfit) {
					hideLayer("arms")
					showPart("arms/demonlewd")
					showPart("arms_fg/demonlewd")
					hideLayer("arms_bg")
					showPart("arms_bg/human")
					hideLayer("legs")
					showPart("legs/fairyFlying")
				}
			}
			WingType.DEVILFEATHER -> {
				showPart("wings/devilfeather")
				showPart("wings_bg/devilfeather")
				if (playerHasLargeLowerBody) {
					showPart("wings_front/devilfeather")
				}
			}
			WingType.ETHEREAL -> {
				showPart("wings/ghost")
			}
			WingType.THUNDEROUS_AURA -> {
				showPart("aura/thunderaura")
			}
			WingType.WINDY_AURA -> {
				showPart("aura/windyaura")
			}
			WingType.SEA_DRAGON -> {
				showPart("wings_bg/seaDragonLeft")
				showPart("wings_bg/seaDragonRight")
			}
			WingType.VAMPIRE -> {
				showPart("overclothe/vampire")
				showPart("wings_bg/vampire_bg")
			}
			else -> {}
		}

		/* TAIL AREA */
		when (tail.type) {
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
				if (tail.count >= 2) {
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
				showPart("tail_bg/fox${tail.count}$taur")
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
				if (ears.type == EarType.PANDA) {
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
		if (balls > 0) {
			val ballsz = when {
				ballSize >= 8 -> "sillyhuge"
				ballSize >= 6 -> "huge"
				ballSize >= 4 -> "large"
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
			val x2 = createContext2D(width * 2, height * 2)
			x2.drawImage(compose.canvas, 0.0, 0.0, width * 2.0, height * 2.0)
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
