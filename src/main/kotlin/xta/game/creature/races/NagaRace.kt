package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.body.*
import xta.game.stats.Stats

object NagaRace : Race(45, "naga", 4) {

    object STAGE_HALF_NAGA : RacialStage(
        this, "half naga",
        Stats.STR_MULT to +0.4,
        Stats.TOU_MULT to +0.2,
        Stats.SPE_MULT to +0.6
    ) {
        override fun nameOf(creature: PlayerCharacter) =
            "half naga"
    }

    object STAGE_NAGA : RacialStage(
        this, "naga",
        Stats.STR_MULT to +0.2,
        Stats.SPE_MULT to +0.4
    ) {
        override fun nameOf(creature: PlayerCharacter) =
            "naga"
    }

    override fun stageForScore(creature: PlayerCharacter, score: Int) =
        if (creature.lowerBody.type == LowerBodyType.NAGA){
            if (score >= 8) STAGE_NAGA
            else if (score >= 4) STAGE_HALF_NAGA
            else null
        } else null

    override fun basicScore(creature: PlayerCharacter): Int = with(creature) {
        var score = 0
        if (isNaga)
            score += 3
            if (arms.type == ArmType.HUMAN)
                score++
        if (tongue.type == TongueType.SNAKE)
            score++
        if (face.type == FaceType.SNAKE_FANGS)
            score++
        if (skin.hasPartialCoatOfType(SkinCoatType.SCALES))
            score++
        if (eyes.type == EyeType.SNAKE)
            score++
        if (ears.type == EarType.SNAKE)
            score++
        if ((hasVagina() && vaginas[0].type == VaginaType.NAGA) || countCocksOfType(PenisType.LIZARD) > 0)
            score++
        /*
        TODO PERKS AND OTHER STUFF
        */
        if (!isNaga || hairType == HairType.GORGON || eyes.type == EyeType.GORGON || horns.type == HornType.DRACONIC_X4_12_INCH_LONG || horns.type == HornType.DRACONIC_X2 || tongue.type == TongueType.DRACONIC || wings.type == WingType.DRACONIC_SMALL || wings.type == WingType.DRACONIC_LARGE || wings.type == WingType.DRACONIC_HUGE || hairType == HairType.FEATHER || arms.type == ArmType.HARPY || wings.type == WingType.FEATHERED_LARGE
            || lowerBody.type == LowerBodyType.HYDRA || arms.type == ArmType.HYDRA)
            score = 0
        score
    }
}
