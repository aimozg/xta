package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.body.*
import xta.game.stats.Stats

object OniRace : Race(31, "oni", 6) {
    val oniEyeColours = listOf("red", "orange", "yellow", "green")
    val oniSkinColours = listOf("red", "reddish-orange","purple", "blue")

    object STAGE_ELDER_ONI: RacialStage(this, "elder oni",
        Stats.STR_MULT to +1.50,
        Stats.TOU_MULT to +0.9,
        Stats.INT_MULT to -0.3,
        Stats.WIS_MULT to +0.6
    )

    object STAGE_ONI: RacialStage(this, "oni",
        Stats.STR_MULT to +1.00,
        Stats.TOU_MULT to +0.6,
        Stats.INT_MULT to -0.2,
        Stats.WIS_MULT to +0.4
    )

    object STAGE_HALF_ONI: RacialStage(this, "half oni",
        Stats.STR_MULT to +0.50,
        Stats.TOU_MULT to +0.3,
        Stats.INT_MULT to -0.1,
        Stats.WIS_MULT to +0.2
    )

    override fun stageForScore(creature: PlayerCharacter, score: Int) = when {
        score >= 18 -> STAGE_ELDER_ONI
        score >= 12 -> STAGE_ONI
        score >= 6 -> STAGE_HALF_ONI
        else -> null
    }

    override fun basicScore(creature: PlayerCharacter): Int = with(creature) {
        var score = 0
        if (ears.type == EarType.ONI) {
            score++
        }
        if (face.type == FaceType.ONI_TEETH) {
            score++
        }
        if (horns.type == HornType.ONI || horns.type == HornType.ONI_X2) {
            score++
        }
        if (arms.type == ArmType.ONI) {
            score++
        }
        if (lowerBody.type == LowerBodyType.ONI) {
            score++
        }
        if (eyes.type == EyeType.ONI && eyeColor in oniEyeColours) {
            score++
        }
        if (skinColor in oniSkinColours) {
            score++
        }
        if(skin.basePattern == SkinBasePatternType.BATTLE_TATTOO) {
            score++
        }
        if (rearBody.type == RearBodyType.NONE) {
            score++
        }
        if (tail.type == TailType.NONE) {
            score++
        }
        if (creature.tone >= 80) {
            score++
        }
        if (creature.tone >= 120 && score >= 4) {
            score++
        }
        if (creature.tone >= 160 && score >= 8) {
            score++
        }
        if((hasVagina() && breastRows.size >= 19) || creature.cocks.any { it.length > 18 }) {
            score++
        }
        if (creature.tallness >= 108) {
            score++
        }
        /*
        TODO Perks and stuff
         */
        score
    }
}