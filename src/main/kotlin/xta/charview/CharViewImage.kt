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
		// TODO port model.xml code
		showPart("ears_bg/human")

		/* EYE AREA */
		when (char.eyePart.type) {
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
		// TODO port model.xml code
		showPart("hair/0")
		showPart("hair/_bg0")

		/* HAIR STYLE AREA */
		// TODO port model.xml code

		val skinb:String
		val skinx:String
		// TODO port model.xml code
		when (char.skin.coverage) {
			SkinPart.Coverage.NONE -> when (char.skin.baseType) {
				SkinBaseType.GOO -> {
					skinb = "goo"
					skinx = "goo"
				}
				else -> {
					skinb = "human"
					skinx = "human"
				}
			}
			SkinPart.Coverage.LOW,
			SkinPart.Coverage.MEDIUM -> when (char.skin.coatType) {
				/*SkinCoatType.SCALES,
				SkinCoatType.DRAGON_SCALES -> {
					skinb = "pscales"
					skinx = "pscales"
				}*/
				else -> {
					skinb = "human"
					skinx = "human"
				}
			}
			SkinPart.Coverage.HIGH,
			SkinPart.Coverage.COMPLETE -> when (char.skin.coatType) {
				SkinCoatType.FUR -> {
					// TODO if ears type = panda: set to panda
					skinb = "fur"
					skinx = "fur"
				}
				SkinCoatType.AQUA_SCALES -> {
					// TODO orca pattern
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
					// TODO bee pattern
					skinb = "chitin"
					skinx = "chitin"
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
		if (char.cor >= 100) {
			// TODO and (eyes.type!=Eyes.CENTIPEDE)
			showPart("forehead/SpecialForeheadShadow")
		}

		// face type - TODO port model.xml code
		showPart("face/human")

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
		// TODO port model.xml code

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
