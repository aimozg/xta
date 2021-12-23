package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.body.*
import xta.game.stats.Stats

object AlrauneRace : Race(54, "alraune", 13) {
    val alrauneIrisColour = listOf("light purple", "green", "light green")
    val alrauneSkinColour = listOf("green", "light purple")
    val alrauneSkin2Colour = listOf("leaf green", "lime green", "turquoise", "light green")

    object STAGE_ALRAUNE_17 : RacialStage(
        this, "alraune",
        Stats.TOU_MULT to +1.15,
        Stats.SPE_MULT to -0.6,
        Stats.LIB_MULT to +2.0
    ){
        override fun nameOf(creature: PlayerCharacter) = when (creature.lowerBody.type) {
            LowerBodyType.PLANT_FLOWER ->
                "alraune"
            LowerBodyType.FLOWER_LILIRAUNE ->
                "liliraune"
            else -> ""  //This should never occur.
        }
    }

    object STAGE_ALRAUNE_13 : RacialStage(
        this, "alraune",
        Stats.TOU_MULT to +1.0,
        Stats.SPE_MULT to -0.5,
        Stats.LIB_MULT to +1.45
    ){
        override fun nameOf(creature: PlayerCharacter) = when (creature.lowerBody.type) {
            LowerBodyType.PLANT_FLOWER ->
                "alraune"
            LowerBodyType.FLOWER_LILIRAUNE ->
                "liliraune"
            else -> ""  //This should never occur.
        }
    }

    override fun stageForScore(creature: PlayerCharacter, score: Int): RacialStage? {
        return when {
            score >= 17 -> STAGE_ALRAUNE_17
            score >= 13 -> STAGE_ALRAUNE_13
            else -> null
        }
    }

    override fun basicScore(creature: PlayerCharacter): Int = with(creature) {
        var score = 0
        if (face.type == FaceType.HUMAN) {
            score++
        }
        if (eyes.type == EyeType.HUMAN) {
            score++
        }
        if (eyes.irisColor in alrauneIrisColour) {
            score++
        }
        if (ears.type == EarType.ELFIN) {
            score++
        }
        if ((hair.type == HairType.LEAF || hair.type == HairType.GRASS) && skin.baseColor in alrauneSkinColour) {
        score++
        }
        if (hasPlainSkinOnly() && skin.baseColor in alrauneSkin2Colour) {
            score++
        }
        if (arms.type == ArmType.PLANT) {
            score++
        }
        if (wings.type == WingType.NONE) {
            score++
        }
        if (lowerBody.type.isAlraune) {
            score++
        }
        if (countCocksOfType(PenisType.STAMEN) > 0) {
            score++
        }
        if (hasVagina() && vaginas[0].type == VaginaType.ALRAUNE) {
            score++
        }
        /*
        TODO PERKS AND OTHER STUFF
        */
        score
    }
}
