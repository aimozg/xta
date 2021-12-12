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
}
