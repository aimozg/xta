package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.body.*
import xta.game.stats.Stats

object CaveWyrmRace : Race(44, "cave wyrm", 5) {

    object STAGE_CAVE_WYRM : RacialStage(
        this, "cave wyrm",
        Stats.STR_MULT to +0.6,
        Stats.TOU_MULT to +0.7,
        Stats.WIS_MULT to -0.3,
        Stats.LIB_MULT to +0.5
    )

    object STAGE_HALF_CAVE_WYRM : RacialStage(
        this, "half cave wyrm",
        Stats.STR_MULT to +0.3,
        Stats.TOU_MULT to +0.35,
        Stats.WIS_MULT to -0.15,
        Stats.LIB_MULT to +0.25
    )

    override fun stageForScore(creature: PlayerCharacter, score: Int): RacialStage? {
        return when {
            score >= 10 -> STAGE_CAVE_WYRM
            score >= 5 -> STAGE_HALF_CAVE_WYRM
            else -> null
        }
    }

    override fun basicScore(creature: PlayerCharacter): Int = with(creature) {
        var score = 0
        if (skin.hasPartialCoatOfType(SkinCoatType.SCALES)) {
            if (skin.coatColor == "midnight black") {
                score++
            }
            score++
        }
        if (skin.baseColor == "grayish-blue") {
            score++
        }
        if (ears.type == EarType.CAVE_WYRM) {
            score++
        }
        if (eyes.type == EyeType.CAVE_WYRM) {
            score++
        }
        if (eyes.irisColor == "neon blue") {
            score++
        }
        if (tongue.type == TongueType.CAVE_WYRM) {
            score++
        }
        if (face.type == FaceType.SALAMANDER_FANGS){
            score++
        }
        if (arms.type == ArmType.CAVE_WYRM) {
            score++
        }
        if (lowerBody.type == LowerBodyType.CAVE_WYRM) {
            score++
        }
        if (tail.type == TailType.CAVE_WYRM) {
            score++
        }
        if (countCocksOfType(PenisType.CAVE_WYRM) > 0 || vaginas.getOrNull(0)?.type == VaginaType.CAVE_WYRM) {
            score++
        }
        //Missing: if Glowing nipples/ glowing asshole status effect +1
        /*
        TODO PERKS AND OTHER STUFF
        */
        score
    }
}
