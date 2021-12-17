package xta.game.text

import xta.game.PlayerCharacter
import xta.game.creature.Gender
import xta.game.creature.body.BeardStyle
import xta.text.displayInches
import xta.utils.decapitalized
import xta.utils.fxrng
import xta.utils.joinToSentence
import xta.utils.prependIfNotEmpty

/*
 * Created by aimozg on 05.12.2021.
 */
class PlayerAppearance(private val character:PlayerCharacter) {
	fun describe() = buildString {
		append("<h3>"+character.name+", Level "+character.level+" "+character.raceFullName()+"</h3>")
		append(describeRace())

		append("[pg]")
		append(describeSkin())

		append(" ")
		append(describeFace())

		append(" [Your] features are adorned by ")
		append(character.face.describeMF(true))
		append(".")
		append("[pg]")

		append(
			listOf(
				describeHair(),
				describeEars()
			).joinToSentence(pair = ", while ")
		)

		val horns = describeHorns()
		val antennae = describeAntennae()
		if (horns.isNotEmpty() || antennae.isNotEmpty()) {
			append(" Beyond that, ")
			append(
				listOf(horns, antennae).joinToSentence().decapitalized()
			)
		}

		append(
			listOf(
				describeEyes(),
				describeTongue()
			).joinToSentence()
		)

		val beard = describeBeard()
		val gills = describeGills()
		if (beard.isNotEmpty() || gills.isNotEmpty()) {
			append("Also, ")
			append(
				listOf(beard, gills).joinToSentence().decapitalized()
			)
		}

		append(describeVisage().prependIfNotEmpty("[pg]"))

		append("[pg]")
		append(
			listOf(
				describeArms(),
				describeLowerBody()
			).joinToSentence()
		)

		append(describeWings().prependIfNotEmpty("[pg]"))

		append(describeRearBody().prependIfNotEmpty("[pg]"))

		append(describeTail().prependIfNotEmpty(" "))

		append("[pg]")
		append(describeBreasts())

		append(describeCrotch().prependIfNotEmpty(" "))

		if (character.hasCock()) {
			append("[pg]")
			append(describeCock())
			append("[pg]")
			append(describeBalls())
		}

		append(describePussy().prependIfNotEmpty("[pg]"))

		append("[pg]")
		if (character.gender == Gender.GENDERLESS) {
			append(
				listOf(
					"You have a curious lack of any sexual endowments.",
					describeAsshole()
				).joinToSentence()
			)
		} else {
			append(describeAsshole())
		}

		append(describePiercings().prependIfNotEmpty("[pg]"))

		append(describeSpecialCases().prependIfNotEmpty("[pg]"))

		append(describePregnancy().prependIfNotEmpty("[pg]"))

		append(describeEquipment().prependIfNotEmpty("[pg]"))
	}

	fun describeRace() = buildString {
		//Discuss race
		if (character.raceName() != character.startingRace) append("[You] began [your] journey as a " + character.startingRace + ", but gave that up as [he] explored the dangers of this realm. ")
		//Height and race.
		append("[He] [is] a ")
		append(character.tallness.displayInches())
		append(" tall [racefull], with [bodytype].")
		// TODO move to races
		// val genderlessRace:Array<String> = arrayOf("half cow-morph", "half cow-girl", "cow-girl", "cow-girl", "cow-morph", "minotaur", "half-minotaur", "alraune", "liliraune", "half unicorn", "unicorn", "unicornkin", "half alicorn", "alicorn", "alicornkin", "true alicorn", "half bicorn", "bicorn", "bicornkin", "half nightmare","nightmare", "nightmarekin", "true nightmare")
//		if (pcrace !in genderlessRace) {
//			append(" tall [malefemaleherm] [race], with [bodytype].");
//		} else {
//			append(" tall [race], with [bodytype].");
//		}
	}

	fun describeSkin() =
		listOf(
			character.skin.baseType.appearanceDescription(),
			character.skin.basePattern.appearanceDescription()
		).joinToSentence(pair = ", while ")

	fun describeFace() = character.face.appearanceDescription()

	fun describeHair() = character.hair.appearanceDescription()

	fun describeEars() = character.ears.appearanceDescription()

	fun describeHorns() = character.horns.appearanceDescription()

	fun describeAntennae() = character.antennae.appearanceDescription()

	fun describeEyes() = character.eyes.appearanceDescription()

	fun describeTongue() = character.tongue.appearanceDescription()

	fun describeBeard() = buildString {
		if (character.beardLength > 0) {
			append("You have a ")
			append(Appearance.beardDescsript(character))
			if (character.beardStyle != BeardStyle.GOATEE) {
				append(" covering your")
				if (fxrng.nextBoolean()) append("jaw")
				else append("chin and cheeks")
			} else {
				append(" protruding from your chin")
			}
			append(".")
		}
	}

	fun describeGills() = character.gills.appearanceDescription()

	fun describeVisage() = buildString {
		if (false) {// TODO if character.hasPerk(PerkLib.DarkenedKitsune)
			if (character.cor >= 75) {
				append("The corruption has turned you into an inhuman being; With your head tilted to 35 degrees and ears twitching erratically every so often, it would almost be cute if not for your gesugao expression.")
			} else {
				append("The corruption has turned you into a different being; With your head slightly off-center and ears constantly moving at the slightest sound every so often, it would almost be considered cute, if not for your paranoid expression.")
			}
		}
	}

	fun describeArms() = character.arms.appearanceDescription()

	fun describeLowerBody() = buildString {
		append(character.lowerBody.appearanceDescription())

		//ASS
		if (character.isTaur) {
			//Horse version
			append(" [Your] ")
			if (character.tone < 65) {
				//FATBUTT
				append("[butt]")
				when {
					character.buttRating < 4 ->
						append(" is lean, from what you can see of it.")
					character.buttRating < 6 ->
						append(" looks fairly average.")
					character.buttRating < 10 ->
						append(" is fairly plump and healthy.")
					character.buttRating < 15 ->
						append(" jiggles a bit as [you] [verb trot] around.")
					character.buttRating < 20 ->
						append(" jiggles and wobbles as [you] [verb trot] about.")
					else ->
						append(" is obscenely large, bordering freakish, even for a horse.")
				}
			} else {
				//GIRL LOOK AT DAT BOOTY
				append("[butt]")
				when {
					character.buttRating < 4 ->
						append(" is barely noticeably, showing off the muscles of [your] haunches.")
					character.buttRating < 6 ->
						append(" matches [your] toned equine frame quite well.")
					character.buttRating < 10 ->
						append(" gives hints of just how much muscle [you] could put into a kick.")
					character.buttRating < 15 ->
						append(" surges with muscle whenever [you] [verb trot] about.")
					character.buttRating < 20 ->
						append(" flexes its considerable mass as [you] [verb move].")
					else ->
						append(" is stacked with layers of muscle, huge even for a horse.")
				}
			}
		} else if (character.isBiped || character.isNaga) {
			//Non-horse PCs
			//Hip info only displays if you aren't a centaur.
			append(" [You] [have] ")
			when {
				character.thickness > 70 -> {
					append("[hips]")
					when {
						character.hipRating < 6 ->
							if (character.tone < 65) append(" buried under a noticeable muffin-top")
							else append(" that blend into [your] pillar-like waist")
						character.hipRating < 10 ->
							append(" that blend into the rest of [your] thick form")
						character.hipRating < 15 ->
							append(" that would be much more noticeable if [you] [verb were]n't so wide-bodied")
						character.hipRating < 20 ->
							append(" that sway and emphasize [your] thick, curvy shape")
						else ->
							append(" that sway hypnotically on [your] extra-curvy frame")
					}
				}
				character.thickness < 30 -> {
					append("[hips]")
					when {
						character.hipRating < 6 ->
							append(" that match [your] trim, lithe body")
						character.hipRating < 10 ->
							append(" that sway to and fro, emphasized by [your] trim body")
						character.hipRating < 15 ->
							append(" that swell out under [your] trim waistline")
						character.hipRating < 20 ->
							append(", emphasized by [your] narrow waist")
						else ->
							append(" that swell disproportionately wide on [your] lithe frame")
					}
				}
				else -> {
					//STANDARD
					append("[hips]")
					if (character.femininity > 50) {
						when {
							character.hipRating < 6 -> {}
							character.hipRating < 10 ->
								append(" that draw the attention of those around [you]")
							character.hipRating < 15 ->
								append(" that make [you] walk with a sexy, swinging gait")
							character.hipRating < 20 ->
								append(" that make it look like [youve] birthed many children")
							else ->
								append(" that make [you] look more like an animal waiting to be bred than any kind of human")
						}
					} else {
						when {
							character.hipRating < 6 -> {}
							character.hipRating < 10 ->
								append(" that give [you] a graceful stride")
							character.hipRating < 15 ->
								append(" that add a little feminine swing to [your] gait")
							character.hipRating < 20 ->
								append(" that force you to sway and wiggle as [you] [verb move]")
							character.hipRating >= 20 -> {
								append(" that give [your] ")
								when {
									character.balls > 0 -> {
										append("balls plenty of room to breathe")
									}
									character.hasCock() -> {
										append("[multicockdescript] plenty of room to swing")
									}
									character.hasVagina() -> {
										append("[vagina] a nice, wide berth")
									}
									else -> append("vacant groin plenty of room")
								}
							}
						}
					}
				}
			}
			append(", and")
			//TUBBY ASS
			if (character.tone < 60) {
				append(" [your] [butt]")
				when {
					character.buttRating < 4 ->
						append(" looks great under [your] gear.")
					character.buttRating < 6 ->
						append(" has the barest amount of sexy jiggle.")
					character.buttRating < 10 ->
						append(" fills out [your] clothing nicely.")
					character.buttRating < 15 ->
						append(" wobbles enticingly with every step.")
					character.buttRating < 20 ->
						append(" wobbles like a bowl full of jello as [you] [verb walk].")
					else ->
						append(" is obscenely large, bordering freakish, and makes it difficult to run.")
				}
			}
			//FITBUTT
			else {
				append(" your [butt]")
				when {
					character.buttRating < 4 ->
						append(" molds closely against [your] form.")
					character.buttRating < 6 ->
						append(" contracts with every motion, displaying the detailed curves of its lean musculature.")
					character.buttRating < 10 ->
						append(" fills out [your] clothing nicely.")
					character.buttRating < 15 ->
						append(" stretches [your] gear, flexing it with each step.")
					character.buttRating < 20 ->
						append(" threatens to bust out from under your kit each time [you] [verb clench] it.")
					else ->
						append(" is marvelously large, but completely stacked with muscle.")
				}
			}
		}
	}

	fun describeWings() = character.wings.appearanceDescription()

	fun describeRearBody() = character.rearBody.appearanceDescription()

	fun describeTail() = character.tail.appearanceDescription()

	fun describeBreasts() = "" // TODO

	fun describeCrotch() = "" // TODO

	fun describeCock() = "" // TODO

	fun describeBalls() = "" // TODO

	fun describePussy() = "" // TODO

	fun describeAsshole() = "" // TODO

	fun describePiercings() = "" // TODO

	fun describeSpecialCases() = "" // TODO

	fun describePregnancy() = "" // TODO

	fun describeEquipment() = "" // TODO
}
