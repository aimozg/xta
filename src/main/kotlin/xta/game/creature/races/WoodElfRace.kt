package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.body.*
import xta.game.creature.perks.PerkLib

import xta.game.stats.Stats

object WoodElfRace : Race(92, "wood elf", 22) {

    object STAGE_WOODELF_31 : RacialStage(
        this, "wood elf",
        Stats.STR_MULT to -0.1,
        Stats.TOU_MULT to -0.15,
        Stats.SPE_MULT to +5.5,
        Stats.INT_MULT to +4.95,
        Stats.LIB_MULT to +4.55,
        Stats.SENS to +0.8
    ) {
        override fun nameOf(creature: PlayerCharacter) =
            "wood elf"
    }

    object STAGE_WOODELF_28 : RacialStage(
        this, "wood elf",
        Stats.STR_MULT to -0.1,
        Stats.TOU_MULT to -0.15,
        Stats.SPE_MULT to +4.95,
        Stats.INT_MULT to +4.45,
        Stats.LIB_MULT to +4.15,
        Stats.SENS to +0.7
    ) {
        override fun nameOf(creature: PlayerCharacter) =
            "wood elf"
    }

    object STAGE_WOODELF_25 : RacialStage(
        this, "wood elf",
        Stats.STR_MULT to -0.1,
        Stats.TOU_MULT to -0.15,
        Stats.SPE_MULT to +4.3,
        Stats.INT_MULT to +4.05,
        Stats.LIB_MULT to +3.75,
        Stats.SENS to +0.6
    ) {
        override fun nameOf(creature: PlayerCharacter) =
            "wood elf"
    }

    object STAGE_WOODELF_22 : RacialStage(
        this, "wood elf",
        Stats.STR_MULT to -0.1,
        Stats.TOU_MULT to -0.15,
        Stats.SPE_MULT to +3.75,
        Stats.INT_MULT to +3.55,
        Stats.LIB_MULT to +3.35,
        Stats.SENS to +0.5
    ) {
        override fun nameOf(creature: PlayerCharacter) =
            "wood elf"
    }

    override fun stageForScore(creature: PlayerCharacter, score: Int): RacialStage? {
        return when {
            score >= 31 -> STAGE_WOODELF_31
            score >= 28 -> STAGE_WOODELF_28
            score >= 25 -> STAGE_WOODELF_25
            score >= 22 -> STAGE_WOODELF_22
            else -> null
        }
    }

    override fun basicScore(creature: PlayerCharacter): Int = with(creature) {
        var score = 0
        if(creature.hasPerk(PerkLib.BlessingOfTheAncestorTree))
            score += 4
            if (ears.type == EarType.ELVEN)
                score++
            if (eyes.type == EyeType.ELF)
                score++
            if (faceType == FaceType.ELF)
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
            if (hairColor == "golden blonde")
                score++
            if (eyes.irisColor == "light green")
                score++
            if (skin.baseColor == "light")
                score++
            if (skin.hasPlainSkinOnly() && skin.baseAdj == "flawless")
                score++
            if (tone <= 60)
                score++
            if (thickness <= 50)
                score++
            if ((hasCock() && cocks.size < 6) || (hasVagina() && biggestTitSize() >= 3))
                score++
            if (cor >= 50)
                score++
            if (hasPerk(PerkLib.FlawlessBody))
                score++
            if (hasPerk(PerkLib.ElvenSense))
                score++
        /*
        TODO PERKS AND OTHER STUFF 
        */
        score
    }
}
