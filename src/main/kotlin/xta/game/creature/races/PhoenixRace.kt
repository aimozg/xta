package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.body.*
import xta.game.stats.Stats

object PhoenixRace : Race(51, "phoenix", 10) {

    object STAGE_GREATER_PHOENIX : RacialStage(
        this, "Greater Phoenix",
        Stats.STR_MULT to +0.4,
        Stats.TOU_MULT to +0.2,
        Stats.SPE_MULT to +1.5,
        Stats.LIB_MULT to +1.05
    )

    object STAGE_PHOENIX : RacialStage(
        this, "Phoenix",
        Stats.STR_MULT to +0.2,
        Stats.TOU_MULT to +0.2,
        Stats.SPE_MULT to +0.7,
        Stats.LIB_MULT to +0.4
    )

    override fun stageForScore(creature: PlayerCharacter, score: Int): RacialStage? {
        return when {
            score >= 21 -> STAGE_GREATER_PHOENIX
            score >= 10 -> STAGE_PHOENIX
            else -> null
        }
    }

    override fun basicScore(creature: PlayerCharacter): Int = with(creature) {
        var score = 0
        if (wings.type == WingType.FEATHERED_PHOENIX)
            score++
        if (arms.type == ArmType.PHOENIX)
            score++
        if (hair.type == HairType.FEATHER)
            score++
            if ((face.type == FaceType.HUMAN || face.type == FaceType.SALAMANDER_FANGS) && score > 2)
                score++
            if ((ears.type == EarType.HUMAN || ears.type == EarType.ELFIN || ears.type == EarType.LIZARD) && score > 2)
                score++
        if (eyes.type ==EyeType.LIZARD)
            score++
        if (lowerBody.type == LowerBodyType.SALAMANDER || lowerBody.type == LowerBodyType.HARPY)
            score++
        if (tail.type == TailType.SALAMANDER)
            score++
        if (skin.hasPartialCoatOfType(SkinCoatType.SCALES))
            score++
        if (countCocksOfType(PenisType.LIZARD) > 0)
            score++
        /*
        TODO PERKS AND OTHER STUFF
        */
        if (wings.type != WingType.FEATHERED_PHOENIX)
            score = 0
        score
    }
}
