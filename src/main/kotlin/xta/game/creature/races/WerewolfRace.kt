package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.body.*
import xta.game.stats.Stats

object WerewolfRace : Race(14, "werewolf", 12) {

    object STAGE_WEREWOLF: RacialStage(this, "werewolf",
        Stats.STR_MULT to +1.0,
        Stats.TOU_MULT to +0.4,
        Stats.SPE_MULT to +0.6,
        Stats.INT_MULT to -0.2
        )

    override fun stageForScore(creature: PlayerCharacter, score: Int): RacialStage? {
        return when {
            score >= 12 -> STAGE_WEREWOLF
            else -> null
        }
    }

    override fun basicScore(creature: PlayerCharacter): Int = with(creature) {
        var score = 0
        if (face.type == FaceType.WOLF_FANGS)
            score++
        if (eyes.type == EyeType.FERAL)
            score++
        if (eyes.type == EyeType.FENRIR)
            score -= 7
        if (ears.type == EarType.WOLF)
            score++
        if (tongue.type == TongueType.DOG)
            score++
        if (arms.type == ArmType.WOLF)
            score++
        if (lowerBody.type == LowerBodyType.WOLF)
            score++
        if (tail.type == TailType.WOLF)
            score++
        if (skin.hasPartialCoatOfType(SkinCoatType.FUR))
            score++
        if (rearBody.type == RearBodyType.WOLF_COLLAR)
            score++
        if (rearBody.type == RearBodyType.FENRIR_ICE_SPIKES)
            score -= 7
        if (countCocksOfType(PenisType.WOLF) > 0 && score > 0)
            score++
        if (cor >= 20)
            score += 2
        /*
        TODO: Perks and stuff
         */
        score
    }
}
