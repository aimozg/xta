package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.body.*
import xta.game.stats.Stats

object ElfRace : Race(32, "elf", 5) {
    var elfHairColour = listOf("black", "leaf green", "golden blonde", "silver")
    var elfSkinColour = listOf("dark", "light","tan")

    object STAGE_ELF: RacialStage(this, "elf",
        Stats.STR_MULT to -0.1,
        Stats.TOU_MULT to -0.15,
        Stats.SPE_MULT to +0.8,
        Stats.INT_MULT to +0.8,
        Stats.WIS_MULT to +0.6,
        Stats.SENS to + 0.3
    ){
        override fun nameOf(creature: PlayerCharacter) = when {
            creature.isTaur->
                "elf-taur"
            else ->
                "elf"
        }
    }

    object STAGE_HALF_ELF: RacialStage(this, "half elf",
        Stats.STR_MULT to -0.05,
        Stats.TOU_MULT to -0.1,
        Stats.SPE_MULT to +0.45,
        Stats.INT_MULT to +0.45,
        Stats.WIS_MULT to +0.3,
        Stats.SENS to + 0.15
    ){
        override fun nameOf(creature: PlayerCharacter) = when {
            creature.isTaur->
                "half elf-taur"
            else ->
                "half elf"
        }
    }

    override fun stageForScore(creature: PlayerCharacter, score: Int): RacialStage? {
        return when {
            score >= 11 -> STAGE_ELF
            score >= 5 -> STAGE_HALF_ELF
            else -> null
        }
    }

    override fun basicScore(creature: PlayerCharacter): Int = with(creature) {
        var score = 0
        if (ears.type == EarType.ELVEN)
            score++
        if(eyes.type == EyeType.ELF)
            score++
        if (face.type == FaceType.ELF)
            score++
        if (tongue.type == TongueType.ELF)
            score++
        if (arms.type == ArmType.ELF)
            score++
        if (lowerBody.type == LowerBodyType.ELF)
            score++
        if (hairType == HairType.SILKEN)
            score++
        if (wings.type == WingType.NONE)
            score++
        if (score >= 2){
            if (hairColor in elfHairColour)
                score++
            if (skinColor in elfSkinColour)
                score++
            if (hasPlainSkinOnly() && skin.adj == "flawless")
                score++
            if (tone <= 60)
                score++
            if (thickness <= 50)
                score++
            if (hasCock() && cocks.count() < 6)
                score++
            if (hasVagina() && biggestTitSize() >= 3)
                score++
        }
        /*
        TODO Perks and stuff
         */
        score
    }
}
