package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.body.*
import xta.game.stats.Stats

object GoblinRace : Race(25, "goblin", 10) {
    val goblinSkinColour = listOf("pale yellow", "grayish-blue", "green", "dark green", "emerald")
    val goblinEyeColour = listOf("red", "yellow", "purple")
    val goblinHairColour = listOf("red", "purple", "green", "blue", "pink", "orange")


    object STAGE_GOBLIN : RacialStage(
        this, "goblin",
        Stats.STR_MULT to +0.0,
        Stats.TOU_MULT to +0.0,
        Stats.SPE_MULT to +0.0,
        Stats.INT_MULT to +0.0,
        Stats.WIS_MULT to +0.0,
        Stats.LIB_MULT to +0.3,
        Stats.SENS to +0.0
    ) {
        override fun nameOf(creature: PlayerCharacter) =
            "goblin"
    }

    override fun stageForScore(creature: PlayerCharacter, score: Int): RacialStage? {
        return when {
            score >= 10 -> STAGE_GOBLIN
            else -> null
        }
    }

    override fun basicScore(creature: PlayerCharacter): Int = with(creature) {
        var score = 0
        if (face.type == FaceType.HUMAN || face.type == FaceType.ANIMAL_TEETH)
            score++
        if (ears.type == EarType.ELFIN)
            score++
        if (creature.tallness < 48)
            score++
        if (hasVagina())
            score++
        if (hasPlainSkinOnly())
            score++
            if (skin.baseColor in goblinSkinColour)
                score++
            if (eyes.type == EyeType.HUMAN && eyes.irisColor in goblinEyeColour)
                score++
            if (hairColor in goblinHairColour)
                score++
            if (arms.type == ArmType.HUMAN)
                score++
            if (lowerBody.type == LowerBodyType.HUMAN)
                score++
            if (antennae.type == AntennaeType.NONE)
                score++
        /*
        TODO PERKS AND OTHER STUFF
        */
        if (skin.baseColor !in goblinSkinColour)
            score = 0
        if (ears.type != EarType.ELFIN)
            score = 0
        score
    }
}
