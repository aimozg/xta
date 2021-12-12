package xta.game.text

import xta.game.Creature
import xta.game.PlayerCharacter
import xta.game.creature.Gender
import xta.game.creature.body.BreastCup
import xta.utils.either
import xta.utils.fxrng

/*
 * Created by aimozg on 28.11.2021.
 */
object Appearance {

	fun maleFemaleHerm(character: PlayerCharacter): String = when (character.gender) {
		Gender.GENDERLESS -> character.mf("genderless", "fem-genderless")
		Gender.MALE -> character.mf(
			"male",
			if (character.biggestTitSize() > BreastCup.A) "shemale" else "femboy"
		)
		Gender.FEMALE -> character.mf("cuntboy", "female")
		Gender.HERM -> character.mf("maleherm", "hermaphrodite")
	}

	fun bodyType(character: PlayerCharacter): String {
		var desc = ""
		val thickness = character.thickness
		val tone = character.tone
		val biggestTitSize = character.biggestTitSize()
		val hipSize = character.hipRating
		val buttSize = character.buttRating
		//OLD STUFF
		//SUPAH THIN
		if (thickness < 10) {
			//SUPAH BUFF
			if (tone > 150)
				desc += "a lithe body covered in highly visible muscles"
			else if (tone > 100)
				desc += "a lithe body covered in highly visible muscles"
			else if (tone > 90)
				desc += "a lithe body covered in highly visible muscles"
			else if (tone > 75)
				desc += "an incredibly thin, well-muscled frame"
			else if (tone > 50)
				desc += "a very thin body that has a good bit of muscle definition"
			else if (tone > 25)
				desc += "a lithe body and only a little bit of muscle definition"
			else
				desc += "a waif-thin body, and soft, forgiving flesh"
		}
		//Pretty thin
		else if (thickness < 25) {
			if (tone > 150)
				desc += "a thin body and incredible muscle definition"
			else if (tone > 100)
				desc += "a thin body and incredible muscle definition"
			else if (tone > 90)
				desc += "a thin body and incredible muscle definition"
			else if (tone > 75)
				desc += "a narrow frame that shows off [his] muscles"
			else if (tone > 50)
				desc += "a somewhat lithe body and a fair amount of definition"
			else if (tone > 25)
				desc += "a narrow, soft body that still manages to show off a few muscles"
			else
				desc += "a thin, soft body"
		}
		//Somewhat thin
		else if (thickness < 40) {
			if (tone > 150)
				desc += "a fit, somewhat thin body and rippling muscles all over"
			else if (tone > 100)
				desc += "a fit, somewhat thin body and rippling muscles all over"
			else if (tone > 90)
				desc += "a fit, somewhat thin body and rippling muscles all over"
			else if (tone > 75)
				desc += "a thinner-than-average frame and great muscle definition"
			else if (tone > 50)
				desc += "a somewhat narrow body and a decent amount of visible muscle"
			else if (tone > 25)
				desc += "a moderately thin body, soft curves, and only a little bit of muscle"
			else
				desc += "a fairly thin form and soft, cuddle-able flesh"
		}
		//average
		else if (thickness < 60) {
			if (tone > 150)
				desc += "average thickness and a bevy of perfectly defined muscles"
			else if (tone > 100)
				desc += "average thickness and a bevy of perfectly defined muscles"
			else if (tone > 90)
				desc += "average thickness and a bevy of perfectly defined muscles"
			else if (tone > 75)
				desc += "an average-sized frame and great musculature"
			else if (tone > 50)
				desc += "a normal waistline and decently visible muscles"
			else if (tone > 25)
				desc += "an average body and soft, unremarkable flesh"
			else
				desc += "an average frame and soft, untoned flesh with a tendency for jiggle"
		} else if (thickness < 75) {
			if (tone > 150)
				desc += "a somewhat thick body that's covered in slabs of muscle"
			else if (tone > 100)
				desc += "a somewhat thick body that's covered in slabs of muscle"
			else if (tone > 90)
				desc += "a somewhat thick body that's covered in slabs of muscle"
			else if (tone > 75)
				desc += "a body that's a little bit wide and has some highly-visible muscles"
			else if (tone > 50)
				desc += "a solid build that displays a decent amount of muscle"
			else if (tone > 25)
				desc += "a slightly wide frame that displays [his] curves and has hints of muscle underneath"
			else
				desc += "a soft, plush body with plenty of jiggle"
		} else if (thickness < 90) {
			if (tone > 150)
				desc += "a thickset frame that gives [him] the appearance of a wall of muscle"
			else if (tone > 100)
				desc += "a thickset frame that gives [him] the appearance of a wall of muscle"
			else if (tone > 90)
				desc += "a thickset frame that gives [him] the appearance of a wall of muscle"
			else if (tone > 75)
				desc += "a burly form and plenty of muscle definition"
			else if (tone > 50)
				desc += "a solid, thick frame and a decent amount of muscles"
			else if (tone > 25)
				desc += "a wide-set body, some soft, forgiving flesh, and a hint of muscle underneath it"
			else {
				desc += "a wide, cushiony body"
				if (character.hasVagina() || biggestTitSize > 3 || hipSize > 7 || buttSize > 7)
					desc += " and plenty of jiggle on [his] curves"
			}
		}
		//Chunky monkey
		else {
			if (tone > 150)
				desc += "an extremely thickset frame and so much muscle others would find [him] harder to move than a huge boulder"
			else if (tone > 100)
				desc += "an extremely thickset frame and so much muscle others would find [him] harder to move than a huge boulder"
			else if (tone > 90)
				desc += "an extremely thickset frame and so much muscle others would find [him] harder to move than a huge boulder"
			else if (tone > 75)
				desc += "a very wide body and enough muscle to make [him] look like a tank"
			else if (tone > 50)
				desc += "an extremely substantial frame packing a decent amount of muscle"
			else if (tone > 25) {
				desc += "a very wide body"
				if (character.hasVagina() || biggestTitSize > 4 || hipSize > 10 || buttSize > 10)
					desc += ", lots of curvy jiggles,"
				desc += " and hints of muscle underneath"
			} else {
				desc += "a thick"
				if (character.hasVagina() || biggestTitSize > 4 || hipSize > 10 || buttSize > 10)
					desc += ", voluptuous"
				desc += " body and plush, "
				if (character.hasVagina() || biggestTitSize > 4 || hipSize > 10 || buttSize > 10)
					desc += " jiggly curves"
				else
					desc += " soft flesh"
			}
		}
		return desc
	}

	fun beardDescsript(creature: Creature): String {
		if (creature.beardLength == 0) {
			return fxrng.either(
				"shaved",
				"bald",
				"smooth",
				"hairless",
				"glabrous"
			) + " chin and cheeks"
		}
		return buildString {
			//
			// LENGTH ADJECTIVE!
			//
			when {
				creature.beardLength < 0.2 ->
					append(fxrng.either(
						"close-cropped, ",
						"trim, "
					))
				creature.beardLength < 0.5 -> append("short, ")
				creature.beardLength < 1.5 -> append("medium, ")
				creature.beardLength < 3.0 -> append("moderately long, ")
				creature.beardLength < 6.0 -> append(fxrng.either(
					"long, ",
					"neck-length, "
				))
				else -> append(fxrng.either(
					"very long, ",
					"chest-length, "
				))
			}
			//
			// COLORS
			//
			append(creature.hairColor)
			append(" ")
			//
			// BEARD WORDS
			// Follows hair type.
			append(creature.hairType.beardDesc(creature))
			append(creature.beardStyle.noun)
		}
	}

	fun buttDescript(creature: Creature) = buildString {
		when {
			creature.buttRating <= 1 ->
				if (creature.tone >= 60) {
					append("incredibly tight, perky ")
				} else {
					append(fxrng.either("tiny",
						"very small",
						"dainty"))
					//Soft PC's buns!
					if (creature.tone <= 30 && fxrng.nextInt(3) == 0) append(" yet soft")
					append(" ")
				}
			creature.buttRating < 4 -> when {
				creature.tone >= 65 ->
					append(fxrng.either("perky, muscular ",
						"tight, toned ",
						"compact, muscular ",
						"tight ",
						"muscular, toned "))
				//Nondescript
				creature.tone >= 30 ->
					append(fxrng.either("tight ",
						"firm ",
						"compact ",
						"petite "))
				//FLABBAH
				else ->
					append(fxrng.either("small, heart-shaped ",
						"soft, compact ",
						"soft, heart-shaped ",
						"small, cushy ",
						"small ",
						"petite ",
						"snug "))
			}
			creature.buttRating < 6 -> when {
				//TOIGHT LIKE A TIGER
				creature.tone >= 65 ->
					append(fxrng.either("nicely muscled ",
						"nice, toned ",
						"muscly ",
						"nice toned ",
						"toned ",
						"fair "))
				//Nondescript
				creature.tone >= 30 ->
					append(fxrng.either("nice ",
						"fair "))
				//FLABBAH
				else ->
					append(fxrng.either("nice, cushiony ",
						"soft ",
						"nicely-rounded, heart-shaped ",
						"cushy ",
						"soft, squeezable "))
			}
			creature.buttRating < 8 -> when {
				//TOIGHT LIKE A TIGER
				creature.tone >= 65 ->
					append(fxrng.either("full, toned ",
						"muscly handful of ",
						"shapely, toned ",
						"muscular, hand-filling ",
						"shapely, chiseled ",
						"full ",
						"chiseled "))
				//Nondescript
				creature.tone >= 65 ->
					append(fxrng.either("handful of ",
						"full ",
						"shapely ",
						"hand-filling "))
				//FLABBAH
				else -> {
					if (fxrng.nextInt(8) == 0) return "supple, handful of ass"
					append(
						fxrng.either(
							"somewhat jiggly ",
							"soft, hand-filling ",
							"cushiony, full ",
							"plush, shapely ",
							"full ",
							"soft, shapely ",
							"rounded, spongy "
						)
					)
				}
			}
			creature.buttRating < 10 -> when {
				//TOIGHT LIKE A TIGER
				creature.tone >= 65 ->
					append(fxrng.either("large, muscular ",
						"substantial, toned ",
						"big-but-tight ",
						"squeezable, toned ",
						"large, brawny ",
						"big-but-fit ",
						"powerful, squeezable ",
						"large "))
				//Nondescript
				creature.tone >= 65 ->
					append(fxrng.either("squeezable ",
						"large ",
						"substantial "))
				//FLABBAH
				else ->
					append(fxrng.either("large, bouncy ",
						"soft, eye-catching ",
						"big, slappable ",
						"soft, pinchable ",
						"large, plush ",
						"squeezable ",
						"cushiony ",
						"plush ",
						"pleasantly plump "))
			}
			creature.buttRating < 13 -> when {
				//TOIGHT LIKE A TIGER
				creature.tone >= 65 ->
					append(fxrng.either("thick, muscular ",
						"big, burly ",
						"heavy, powerful ",
						"spacious, muscular ",
						"toned, cloth-straining ",
						"thick ",
						"thick, strong "))
				//Nondescript
				creature.tone >= 65 ->
					append(fxrng.either("jiggling ",
						"spacious ",
						"heavy ",
						"cloth-straining "))
				//FLABBAH
				else ->
					append(fxrng.either("super-soft, jiggling ",
						"spacious, cushy ",
						"plush, cloth-straining ",
						"squeezable, over-sized ",
						"spacious ",
						"heavy, cushiony ",
						"slappable, thick ",
						"jiggling ",
						"spacious ",
						"soft, plump "))
			}
			creature.buttRating < 16 -> when {
				//TOIGHT LIKE A TIGER
				creature.tone >= 65 ->
					append(fxrng.either("expansive, muscled ",
						"voluminous, rippling ",
						"generous, powerful ",
						"big, burly ",
						"well-built, voluminous ",
						"powerful ",
						"muscular ",
						"powerful, expansive "))
				//Nondescript
				creature.tone >= 65 ->
					append(fxrng.either("expansive ",
						"generous ",
						"voluminous ",
						"wide "))
				//FLABBAH
				else ->
					append(fxrng.either("pillow-like ",
						"generous, cushiony ",
						"wide, plush ",
						"soft, generous ",
						"expansive, squeezable ",
						"slappable ",
						"thickly-padded ",
						"wide, jiggling ",
						"wide ",
						"voluminous ",
						"soft, padded "))
			}
			creature.buttRating < 20 -> when {
				//TOIGHT LIKE A TIGER
				creature.tone >= 65 ->
					append(fxrng.either("huge, toned ",
						"vast, muscular ",
						"vast, well-built ",
						"huge, muscular ",
						"strong, immense ",
						"muscle-bound "))
				//Nondescript
				creature.tone >= 65 -> {
					if (fxrng.nextInt(5) == 0) return "jiggling expanse of ass"
					if (fxrng.nextInt(5) == 0) return "copious ass-flesh"
					append(
						fxrng.either(
							"huge ",
							"vast ",
							"giant "
						)
					)
				}
				//FLABBAH
				else ->
					append(fxrng.either("vast, cushiony ",
						"huge, plump ",
						"expansive, jiggling ",
						"huge, cushiony ",
						"huge, slappable ",
						"seam-bursting ",
						"plush, vast ",
						"giant, slappable ",
						"giant ",
						"huge ",
						"swollen, pillow-like "))
			}
			else -> when {
				//TOIGHT LIKE A TIGER
				creature.tone >= 65 -> {
					if (fxrng.nextInt(7) == 0) return "colossal, muscly ass"
					append(fxrng.either("ginormous, muscle-bound ",
						"colossal yet toned ",
						"strong, tremdously large ",
						"tremendous, muscled ",
						"ginormous, toned ",
						"colossal, well-defined "))
				}
				//Nondescript
				creature.tone >= 65 ->
					append(fxrng.either("ginormous ",
						"colossal ",
						"tremendous ",
						"gigantic "))
				//FLABBAH
				else ->
					append(fxrng.either("ginormous, jiggly ",
						"plush, ginormous ",
						"seam-destroying ",
						"tremendous, rounded ",
						"bouncy, colossal ",
						"thong-devouring ",
						"tremendous, thickly padded ",
						"ginormous, slappable ",
						"gigantic, rippling ",
						"gigantic ",
						"ginormous ",
						"colossal ",
						"tremendous "))
			}
		}
		append(fxrng.either("butt",
			"butt",
			"butt",
			"butt",
			"ass",
			"ass",
			"ass",
			"ass",
			"backside",
			"backside",
			"derriere",
			"rump",
			"bottom"))
	}

	fun hipDescript(creature: Creature) = buildString {
		when {
			creature.hipRating <= 1 -> {
				append(fxrng.either("tiny ",
					"narrow ",
					"boyish "))
			}
			creature.hipRating < 4 -> {
				if (creature.thickness < 30) {
					append(fxrng.either("slightly-flared ", "curved "))
				} else {
					append(fxrng.either("slender ",
						"narrow ",
						"thin "))
				}
			}
			creature.hipRating < 6 -> {
				if (creature.thickness < 30) {
					append(fxrng.either("flared ", "curvy "))
				} else {
					append(fxrng.either("well-formed ",
						"pleasant "))
				}
			}
			creature.hipRating < 10 -> {
				if (creature.thickness < 30) {
					append(fxrng.either("flared ", "waspish "))
				} else {
					append(fxrng.either("ample ",
						"noticeable ",
						"girly "))
				}
			}
			creature.hipRating < 15 -> {
				if (creature.thickness < 30) {
					append(fxrng.either("flared ", "waspish "))
				} else {
					append(fxrng.either("flared ",
						"curvy ",
						"wide "))
				}
			}
			creature.hipRating < 20 -> {
				if (creature.thickness < 40) {
					append(fxrng.either("flared, ", "waspish, "))
				}
				append(fxrng.either("fertile ",
					"child-bearing ",
					"voluptuous "))
			}
			else -> {
				if (creature.thickness < 30) {
					append(fxrng.either("flaring, ", "incredibly waspish, "))
				}
				append(fxrng.either("broodmother-sized ",
					"cow-like ",
					"inhumanly-wide "))
			}
		}
		when {
			creature.isTaur && fxrng.nextInt(3) == 0 -> append("flanks")
			creature.isNaga && fxrng.nextInt(3) == 0 -> append("sides")
			else -> append(fxrng.either("hips", "thighs"))
		}
	}
}
