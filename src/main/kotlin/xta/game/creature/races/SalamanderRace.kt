package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.body.*
import xta.game.stats.Stats

object SalamanderRace : Race(43, "salamander", 4) {
    val salamanderCoatColour = listOf("red", "blazing red", "orange", "reddish-orange")
    val salamanderSkinColour = listOf("tan", "light", "dark", "mohagany", "russet")



    object STAGE_PRIMORDIAL_SALAMANDER : RacialStage(
        this, "primordial salamander",
        Stats.STR_MULT to +1.05,
        Stats.TOU_MULT to +0.8,
        Stats.LIB_MULT to +1.3,
        Stats.SENS to +0.75
    )

    object STAGE_SALAMANDER : RacialStage(
        this, "salamander",
        Stats.STR_MULT to +0.25,
        Stats.TOU_MULT to +0.25,
        Stats.LIB_MULT to +0.4
    )

    object STAGE_HALF_SALAMANDER : RacialStage(
        this, "half salamander",
        Stats.STR_MULT to +0.15,
        Stats.TOU_MULT to +0.15,
        Stats.LIB_MULT to +0.3
    )

    override fun stageForScore(creature: PlayerCharacter, score: Int): RacialStage? {
        return when {
            score >= 16 -> STAGE_PRIMORDIAL_SALAMANDER
            score >= 7 -> STAGE_SALAMANDER
            score >= 4 -> STAGE_HALF_SALAMANDER
            else -> null
        }
    }

    override fun basicScore(creature: PlayerCharacter): Int = with(creature) {
        var score = 0
        if (skin.hasPartialCoatOfType(SkinCoatType.SCALES))
            score++
            if (skin.coatColor in salamanderCoatColour)
                score++
            if(skin.baseColor in salamanderSkinColour)
                score++
        if (face.type == FaceType.SALAMANDER_FANGS)
            score++
            if (ears.type == EarType.HUMAN || ears.type == EarType.LIZARD)
                score++
        if (eyes.type == EyeType.LIZARD)
            score++
        if (arms.type == ArmType.SALAMANDER)
            score++
        if (lowerBody.type == LowerBodyType.SALAMANDER)
            score++
        if (tail.type == TailType.SALAMANDER)
            score += 2
            if (wings.type == WingType.NONE)
                score++
            if (horns.type == HornType.NONE)
                score++
            if(rearBody.type == RearBodyType.NONE)
                score++
        if (countCocksOfType(PenisType.LIZARD) > 0)
            score++
        /*
        TODO PERKS AND OTHER STUFF
        */
        if (wings.type == WingType.FEATHERED_PHOENIX)
            score = 0
        score
    }
}
