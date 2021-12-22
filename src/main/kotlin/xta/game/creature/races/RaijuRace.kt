package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.body.*
import xta.game.stats.Stats

object RaijuRace : Race(34, "raiju", 5) {

    object STAGE_RAIJU_14 : RacialStage(
        this, "raiju",
        Stats.SPE_MULT to +1.0,
        Stats.INT_MULT to +0.5,
        Stats.LIB_MULT to +1.2,
        Stats.SENS to +0.6
    )

    object STAGE_RAIJU_10 : RacialStage(
        this, "raiju",
        Stats.SPE_MULT to +0.7,
        Stats.INT_MULT to +0.5,
        Stats.LIB_MULT to +0.8,
        Stats.SENS to +0.5
    )

    object STAGE_HALF_RAIJU : RacialStage(
        this, "half raiju",
        Stats.SPE_MULT to +0.35,
        Stats.INT_MULT to +0.25,
        Stats.LIB_MULT to +0.4,
        Stats.SENS to +0.25
    )


    override fun stageForScore(creature: PlayerCharacter, score: Int): RacialStage? {
        return when {
            score >= 14 -> STAGE_RAIJU_14
            score >= 10 -> STAGE_RAIJU_10
            score >= 5 -> STAGE_HALF_RAIJU
            else -> null
        }
    }

    override fun basicScore(creature: PlayerCharacter): Int = with(creature) {
        var score = 0
        if (ears.type == EarType.RAIJU || ears.type == EarType.WEASEL) {
            score++
        }
        if (eyes.type == EyeType.RAIJU) {
            score++
        }
        if (faceType == FaceType.WEASEL) {
            score++
        }
        if (arms.type == ArmType.RAIJU || arms.type == ArmType.RAIJU_PAWS){
        score++
        }
        if (lowerBody.type == LowerBodyType.RAIJU) {
        score++
        }
        if (tailType == TailType.RAIJU) {
            score++
        }
        if (wings.type == WingType.THUNDEROUS_AURA) {
            score++
        }
        if (rearBody.type == RearBodyType.RAIJU_MANE) {
            score++
        }
        if (skin.basePattern == SkinBasePatternType.LIGHTNING_SHAPED_TATTOO) {
            score++
        }
        if (hairType == HairType.STORM) {
            score++
        }
        /*
        TODO PERKS AND OTHER STUFF
        */
        score
    }
}
