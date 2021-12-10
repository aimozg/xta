package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Race
import xta.game.creature.body.*

object CatRace : Race(2, "cat", 4) {
	override fun nameOf(creature: PlayerCharacter, score: Int): String {
		return when {
			creature.isTaur && creature.lowerBody.type == LowerBodyType.CAT ->
				if (score >= 8) "cat-taur"
				else if (creature.face.type == FaceType.HUMAN) "half sphinx-morph"
				else "half cat-taur"
			creature.face.type == FaceType.HUMAN ->
				if (score >= 8) creature.mf("cat-boy", "cat-girl")
				else creature.mf("half cat-boy", "half cat-girl")
			else ->
				if (score >= 8) "cat-morph"
				else "half cat-morph"
		}
	}

	override fun basicScore(creature: PlayerCharacter): Int = with(creature) {
		var score = 0
		if (face.type == FaceType.CAT || face.type == FaceType.CAT_CANINES)
			score++
		if (face.type == FaceType.CHESHIRE || face.type == FaceType.CHESHIRE_SMILE)
			score -= 7
		if (eyes.type == EyeType.CAT)
			score++
		if (ears.type == EarType.CAT)
			score++
		if (eyes.type == EyeType.FERAL)
			score -= 11
		if (tongue.type == TongueType.CAT)
			score++
		if (tail.type == TailType.CAT)
			score++
		if (arms.type == ArmType.CAT)
			score++
		if (lowerBody.type == LowerBodyType.CAT)
			score++
		if (countCocksOfType(PenisType.CAT) > 0)
			score++
		if (breastRows.size > 1 && score > 0)
			score++
		if (breastRows.size == 3 && score > 0)
			score++
		if (breastRows.size > 3)
			score -= 2
		if (skin.hasCoatOfType(SkinCoatType.FUR))
			score++
		if (horns.type == HornType.DEMON
			|| horns.type == HornType.DRACONIC_X2
			|| horns.type == HornType.DRACONIC_X4_12_INCH_LONG
		)
			score -= 2
		if (wings.type == WingType.BAT_LIKE_TINY
			|| wings.type == WingType.DRACONIC_SMALL
			|| wings.type == WingType.BAT_LIKE_LARGE
			|| wings.type == WingType.DRACONIC_LARGE
			|| wings.type == WingType.BAT_LIKE_LARGE_2
			|| wings.type == WingType.DRACONIC_HUGE
		)
			score -= 2
		/*
		TODO perk bonuses
		if (hasPerk(PerkLib.Flexibility))
			catCounter++
		if (hasPerk(PerkLib.CatlikeNimbleness))
			catCounter++
		if (hasPerk(PerkLib.CatlikeNimblenessEvolved))
			catCounter++
		if (hasPerk(PerkLib.CatlikeNimblenessFinalForm))
			catCounter++
		if (hasPerk(PerkLib.CatlikeNimbleness) && hasPerk(PerkLib.ChimericalBodySemiImprovedStage))
			catCounter++
		if (hasPerk(PerkLib.CatlikeNimblenessEvolved) && hasPerk(PerkLib.ChimericalBodySemiSuperiorStage))
			catCounter++
		if (hasPerk(PerkLib.CatlikeNimblenessFinalForm) && hasPerk(PerkLib.ChimericalBodySemiEpicStage))
			catCounter++
		if (hasPerk(PerkLib.AscensionHybridTheory) && catCounter >= 4)
			catCounter += 1
		if (hasPerk(PerkLib.AscensionCruelChimerasThesis) && catCounter >= 8)
			catCounter += 1
		*/
		if (arms.type == ArmType.SPHINX
			|| wings.type == WingType.FEATHERED_SPHINX
			|| tail.type == TailType.NEKOMATA_FORKED_1_3
			|| tail.type == TailType.NEKOMATA_FORKED_2_3
			|| (tail.type == TailType.CAT && tail.count > 1)
			|| rearBody.type == RearBodyType.LION_MANE
			|| (hairColor == "lilac and white striped" && skin.coatColor == "lilac and white striped")
			|| eyes.type == EyeType.INFERNAL
			|| hairType == HairType.BURNING
			|| tail.type == TailType.BURNING
			|| eyes.type == EyeType.DISPLACER
			|| ears.type == EarType.DISPLACER
			|| arms.type == ArmType.DISPLACER
			|| rearBody.type == RearBodyType.DISPLACER_TENTACLES
		) score = 0
		score
	}
}
