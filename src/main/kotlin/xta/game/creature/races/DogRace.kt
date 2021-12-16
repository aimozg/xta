package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.body.*
import xta.game.stats.Stats

object DogRace : Race(12, "dog", 4) {

    object STAGE_DOG: RacialStage(this, "dog-morph",
        Stats.SPE_MULT to +0.15,
        Stats.INT_MULT to -0.05

    ){
        override fun nameOf(creature: PlayerCharacter) = when {
            creature.isTaur && creature.lowerBody.type == LowerBodyType.DOG ->
                "dog-taur"
            creature.face.type == FaceType.HUMAN ->
                creature.mf("dog-man", "dog-girl")
            else ->
                "dog-morph"
        }
    }

    override fun stageForScore(creature: PlayerCharacter, score: Int) = when {
        score >= 4 -> STAGE_DOG
        else -> null
    }

    override fun basicScore(creature: PlayerCharacter): Int = with(creature) {
        var score = 0
        if (face.type == FaceType.DOG)
            score++
        if (ears.type == EarType.DOG)
            score++
        if (tail.type == TailType.DOG)
            score++
        if (lowerBody.type == LowerBodyType.DOG)
            score++
        if (countCocksOfType(PenisType.DOG) > 0)
            score++
        if (breastRows.size > 1)
            score++
        if (breastRows.size == 3)
            score++
        if (breastRows.size > 3)
            score--
        if (skin.hasCoatOfType(SkinCoatType.FUR) && score > 0)
            score++
        score
    }

}