package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Gender
import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.body.*
import xta.game.stats.Stats

object SpiderRace : Race(38, "spider", 4) {

    object STAGE_SPIDER : RacialStage(
        this, "",
        Stats.STR_MULT to -0.2,
        Stats.TOU_MULT to +0.5,
        Stats.INT_MULT to +0.75
    ) {
        override fun nameOf(creature: PlayerCharacter) =
            if (creature.gender == Gender.FEMALE){
                "spider-girl"
            }   else if (creature.lowerBody.type.isDrider){
                "drider"
            }   else {
                "spider-morph"
            }
    }

    object STAGE_HALF_SPIDER : RacialStage(
        this, "",
        Stats.STR_MULT to -0.1,
        Stats.TOU_MULT to +0.3,
        Stats.INT_MULT to +0.4
    ) {
        override fun nameOf(creature: PlayerCharacter) =
            if (creature.gender == Gender.FEMALE){
                "half spider-girl"
            }   else if (creature.lowerBody.type.isDrider){
                "half drider"
            }   else {
                "half spider-morph"
            }
    }

    override fun stageForScore(creature: PlayerCharacter, score: Int): RacialStage? {
        return when {
            score >= 7 -> STAGE_SPIDER
            score >= 4 -> STAGE_HALF_SPIDER
            else -> null
        }
    }

    override fun basicScore(creature: PlayerCharacter): Int = with(creature) {
        var score = 0
        if (eyes.type == EyeType.SPIDER) {
            score++
        }
        if (faceType == FaceType.SPIDER_FANGS) {
            score++
        }
        if (ears.type == EarType.ELFIN) {
            score++
        }
        if (arms.type == ArmType.SPIDER) {
            score++
        }
        if (lowerBody.type == LowerBodyType.CHITINOUS_SPIDER_LEGS) {
            score++
        }
        if (lowerBody.type == LowerBodyType.DRIDER) {
            score += 2
        }
        if (tailType == TailType.SPIDER_ADBOMEN) {
            score++
        }
        if (!skin.hasPartialCoatOfType(SkinCoatType.CHITIN) && score > 0) {
            score--
        }
        if (skin.hasPartialCoatOfType(SkinCoatType.CHITIN)) {
            score++
        }
        /*
        TODO PERKS AND OTHER STUFF
        */
        /*
        // TODO 'Black nipples' status
        if (creature.hasStatusEffect(StatusLib.BlackNipples)) {
            score++
        }
         */
        score
    }
}
