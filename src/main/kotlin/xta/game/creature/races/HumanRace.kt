package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.body.*

object HumanRace : Race(1, "human", 1) {

	val STAGE_MAIN = RacialStage(this, "human")

	override fun stageForScore(creature: PlayerCharacter, score: Int): RacialStage? {
		return when {
			score > 0 -> STAGE_MAIN
			else -> null
		}
	}

	override fun basicScore(creature: PlayerCharacter): Int = with(creature) {
		var score = 0

		if (skin.hasPlainSkinOnly() && skin.adj != "slippery") score++
		if (hairType == HairType.NORMAL) score++
		if (face.type == FaceType.HUMAN) score++
		if (eyes.type == EyeType.HUMAN) score++
		if (ears.type == EarType.HUMAN) score++
		if (ears.type == EarType.ELVEN) score -= 7
		if (tongue.type == TongueType.HUMAN) score++
		if (gills.type == GillType.NONE) score++
		if (antennae.type == AntennaeType.NONE) score++
		if (horns.count == 0) score++
		if (wings.type == WingType.NONE) score++
		if (tail.type == TailType.NONE) score++
		if (arms.type == ArmType.HUMAN) score++
		if (lowerBody.type == LowerBodyType.HUMAN) score++
		if (rearBody.type == RearBodyType.NONE) score++
		if (countCocksOfType(PenisType.HUMAN) >= 1 || (hasVagina() && vaginas[0].type == VaginaType.HUMAN)) score++
		if (breastRows.size == 1 && skin.hasPlainSkinOnly() && skin.adj != "slippery") score++
		if (skin.basePattern == SkinBasePatternType.NONE) score++
		// TODO internal chimera score
		// score += (121 - internalChimeraScore())
		score
	}
}
