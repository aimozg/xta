package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.body.*
import xta.game.stats.Stats

object WolfRace : Race(13, "wolf", 4)  {

    object STAGE_WOLF: RacialStage(this, "wolf",
        Stats.STR_MULT to +0.15,
        Stats.SPE_MULT to + 0.1,
        Stats.INT_MULT to -0.1

    ) {
        override fun nameOf(creature: PlayerCharacter) = when {
            creature.isTaur && creature.lowerBody.type == LowerBodyType.WOLF ->
                "wolf-taur"
            else ->
                creature.mf("wolf-man", "wolf-girl")
        }
    }

    object STAGE_WOLF_MORPH: RacialStage(this, "wolf-morph",
        Stats.STR_MULT to +0.3,
        Stats.TOU_MULT to +0.1,
        Stats.SPE_MULT to +0.3,
        Stats.INT_MULT to -0.1
    )

    object STAGE_WINTER_WOLF: RacialStage(this, "winter wolf",
        Stats.STR_MULT to +0.3,
        Stats.TOU_MULT to +0.2,
        Stats.SPE_MULT to +0.3,
        Stats.INT_MULT to -0.1
    )

    object STAGE_FENRIR: RacialStage(this, "Fenrir",
        Stats.STR_MULT to +1.35,
        Stats.TOU_MULT to +0.8,
        Stats.SPE_MULT to +1.0,
        Stats.INT_MULT to -0.1
    )

    override fun stageForScore(creature: PlayerCharacter, score: Int) =
        if(score >= 4){
            if (creature.isTaur && creature.lowerBody.type == LowerBodyType.WOLF) STAGE_WOLF
            else if (score >= 20) STAGE_FENRIR
            else if (score >= 7 && creature.skin.hasCoatOfType(SkinCoatType.FUR) && creature.skin.coatColor == "glacial white") STAGE_WINTER_WOLF
            else if (score >= 6) STAGE_WOLF_MORPH
            else STAGE_WOLF
        }else null

    override fun basicScore(creature: PlayerCharacter): Int = with(creature) {
        var score = 0
        if (face.type == FaceType.WOLF || face.type == FaceType.ANIMAL_TEETH)
            score++
        if (eyes.type == EyeType.FENRIR)
            score += 3
        if (eyes.irisColor == "glacial blue")
            score += 2
        if (eyes.type == EyeType.FERAL)
            score -= 11
        if (ears.type == EarType.WOLF)
            score++
        if (arms.type == ArmType.WOLF)
            score++
        if (lowerBody.type == LowerBodyType.WOLF)
            score++
        if (tail.type == TailType.WOLF)
            score++
        if(skin.hasCoatOfType(SkinCoatType.FUR) || skin.hasPartialCoatOfType(SkinCoatType.FUR))
            score++
        if (wings.type == WingType.NONE)
            score++
        if (hairColor == "glacial white")
            score++
        if (skin.coatColor == "glacial white")
            score++
        if (rearBody.type == RearBodyType.FENRIR_ICE_SPIKES)
            score += 6
        if (countCocksOfType(PenisType.WOLF) > 0 && score > 0)
            score++
        /*
        TODO Perks and Gargoyle
         */
        score
    }
}