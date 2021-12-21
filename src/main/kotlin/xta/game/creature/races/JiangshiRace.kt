package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.body.*
import xta.game.stats.Stats

object JiangshiRace : Race(75, "jiangshi", 20) {
    var jiangshiSkinColour = listOf("ghostly pale", "light blue", "snow white")

    object STAGE_JIANGSHI: RacialStage(this, "jiangshi",
        Stats.STR_MULT to +1.5,
        Stats.SPE_MULT to -0.9,
        Stats.INT_MULT to -0.9,
        Stats.WIS_MULT to +1.3,
        Stats.LIB_MULT to +2.0
    )

    override fun stageForScore(creature: PlayerCharacter, score: Int): RacialStage? {
        return when {
            score >= 20 -> STAGE_JIANGSHI
            else -> null
        }
    }

    override fun basicScore(creature: PlayerCharacter): Int = with(creature) {
        var score = 0
        if (hasPlainSkinOnly() && skin.adj != "slippery")
            score++
        if (skinColor in jiangshiSkinColour)
            score++
        if (hair.type == HairType.NORMAL)
            score++
        if (face.type == FaceType.JIANGSHI)
            score++
        if (eyes.type == EyeType.JIANGSHI)
            score += 2
        if (ears.type == EarType.HUMAN)
            score++
        if (tongue.type == TongueType.HUMAN)
            score++
        if (gills.type == GillType.NONE)
            score++
        if (antennae.type == AntennaeType.NONE)
            score++
        if (horns.type == HornType.SPELL_TAG && horns.count > 0)
            score++
        if (wings.type == WingType.NONE)
            score += 2
        if (tail.type == TailType.NONE)
            score++
        if (arms.type == ArmType.JIANGSHI)
            score++
        if (lowerBody.type == LowerBodyType.JIANGSHI)
            score++
        if (rearBody.type == RearBodyType.NONE)
            score++
        if (skin.basePattern == SkinBasePatternType.NONE)
            score++
        /*
           TODO: Perks and stuff
        */
        score
    }
}
