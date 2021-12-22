package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.body.*
import xta.game.stats.Stats

object VampireRace : Race(68, "vampire", 6) {

    object STAGE_PUREBLOOD_VAMPIRE : RacialStage(
        this, "pureblood vampire",
        Stats.STR_MULT to +0.65,
        Stats.SPE_MULT to +0.65,
        Stats.INT_MULT to +0.65,
        Stats.LIB_MULT to +0.75
    ) {
        override fun nameOf(creature: PlayerCharacter) =
            "pureblood vampire"
    }

    object STAGE_VAMPIRE : RacialStage(
        this, "vampire",
        Stats.STR_MULT to +0.35,
        Stats.SPE_MULT to +0.35,
        Stats.INT_MULT to +0.35,
        Stats.LIB_MULT to +0.45
    ) {
        override fun nameOf(creature: PlayerCharacter) =
            "vampire"
    }

    object STAGE_DHAMPIR : RacialStage(
        this, "dhampir",
        Stats.STR_MULT to +0.2,
        Stats.SPE_MULT to +0.2,
        Stats.INT_MULT to +0.2,
        Stats.LIB_MULT to +0.3
    ) {
        override fun nameOf(creature: PlayerCharacter) =
            "dhampir"
    }

    override fun stageForScore(creature: PlayerCharacter, score: Int): RacialStage? {
        return when {
            score >= 18 -> STAGE_PUREBLOOD_VAMPIRE
            score >= 10 -> STAGE_VAMPIRE
            score >= 6 -> STAGE_DHAMPIR
            else -> null
        }
    }

    override fun basicScore(creature: PlayerCharacter): Int = with(creature) {
        var score = 0
        if (ears.type == EarType.BAT) {
            score -= 10
        }
        if (ears.type == EarType.VAMPIRE) {
            score++
        }
        if (wings.type == WingType.VAMPIRE) {
            score += 4
        }
        if (faceType == FaceType.VAMPIRE) {
            score += 2
        }
        if (eyes.type == EyeType.VAMPIRE) {
            score++
        }
        if (eyes.irisColor == "blood-red") {
            score++
        }
        if (score >= 8) {
            if (arms.type == ArmType.HUMAN) {
                score++
            }
            if (lowerBody.type == LowerBodyType.HUMAN){
                score++
            }
        }
        if (tail.type == TailType.NONE) {
            score++
        }
        if (horns.type == HornType.NONE) {
            score++
        }
        /*
        TODO PERKS AND OTHER STUFF
        */
        score
    }
}
