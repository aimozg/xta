package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.perks.PerkLib
import xta.game.creature.body.*
import xta.game.stats.Stats

object GargoyleRace : Race(71, "gargoyle", 22) {
    val gargoyleColour = listOf("light gray", "quartz white")

    object STAGE_GARGOYLE_BASE_1 : RacialStage(
        this, "gargoyle",
        Stats.STR_MULT to +3.0,
        Stats.TOU_MULT to +5.1,
        Stats.SPE_MULT to +1.0,
        Stats.INT_MULT to +0.8
    ) {
        override fun nameOf(creature: PlayerCharacter) = when {
            creature.hasPerk(PerkLib.GargoylePure) ->
                "pure gargoyle"
            else ->
                "corrupted gargoyle"
        }
    }

    object STAGE_GARGOYLE_BASE_2 : RacialStage(
        this, "gargoyle",
        Stats.STR_MULT to +1.0,
        Stats.TOU_MULT to +5.1,
        Stats.SPE_MULT to +0.8,
        Stats.INT_MULT to +3.0
    ) {
        override fun nameOf(creature: PlayerCharacter) = when {
            creature.hasPerk(PerkLib.GargoylePure) ->
                "pure gargoyle"
            else ->
                "corrupted gargoyle"
        }
    }

    object STAGE_GARGOYLE_PURE : RacialStage(
        this, "gargoyle",
        Stats.WIS_MULT to +1.3,
        Stats.LIB_MULT to -0.2,
        Stats.SENS to -0.1
    ) {
        override fun nameOf(creature: PlayerCharacter) = when {
            creature.hasPerk(PerkLib.GargoylePure) ->
                "pure gargoyle"
            else ->
                "corrupted gargoyle"
        }
    }

    object STAGE_GARGOYLE_CORRUPTED : RacialStage(
        this, "gargoyle",
        Stats.WIS_MULT to -0.2,
        Stats.LIB_MULT to +1.4,
    ) {
        override fun nameOf(creature: PlayerCharacter) = when {
            creature.hasPerk(PerkLib.GargoylePure) ->
                "pure gargoyle"
            else ->
                "corrupted gargoyle"
        }
    }


    override fun stageForScore(creature: PlayerCharacter, score: Int): RacialStage? {
        if (score >= 22)
            if (CoC.instance.flags[kFLAGS.GARGOYLE_BODY_MATERIAL] == 1) return STAGE_GARGOYLE_BASE_1
            if (CoC.instance.flags[kFLAGS.GARGOYLE_BODY_MATERIAL] == 2) return STAGE_GARGOYLE_BASE_2
            if (creature.hasPerk(PerkLib.GargoylePure)) return STAGE_GARGOYLE_PURE
            if (creature.hasPerk(PerkLib.GargoyleCorrupted)) return STAGE_GARGOYLE_CORRUPTED
        else return null
    }

    override fun basicScore(creature: PlayerCharacter): Int = with(creature) {
        var score = 0
        if (hairColor in gargoyleColour) {
            score++
        }
        if (skin.baseColor in gargoyleColour) {
            score++
        }
        if (hair.type == HairType.NORMAL) {
            score++
        }
        if (skin.baseType == SkinBaseType.STONE) {
            score++
        }
        if (horns.type == HornType.GARGOYLE) {
            score++
        }
        if (eyes.type == EyeType.GEMSTONES) {
            score++
        }
        if (ears.type == EarType.ELFIN) {
            score++
        }
        if (face.type == FaceType.DEVIL_FANGS) {
            score++
        }
        if (tongue.type == TongueType.DEMONIC) {
            score++
        }
        if (arms.type == ArmType.GARGOYLE || arms.type == ArmType.GARGOYLE_2) {
            score++
        }
        if (tail.type == TailType.GARGOYLE || tail.type == TailType.GARGOYLE_2) {
            score++
        }
        if (lowerBody.type == LowerBodyType.GARGOYLE || lowerBody.type == LowerBodyType.GARGOYLE_2) {
            score++
        }
        if (wings.type == WingType.GARGOYLE_LIKE_LARGE) {
            score += 4
        }
        if (gills.type == GillType.NONE) {
            score++
        }
        if (rearBody.type == RearBodyType.NONE) {
            score++
        }
        if (antennae.type == AntennaeType.NONE) {
            score++
        }
        /*
        TODO PERKS AND OTHER STUFF
        */
        score
    }
}
