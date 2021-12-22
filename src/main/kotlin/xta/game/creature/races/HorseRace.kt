package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.body.*
import xta.game.stats.Stats

object HorseRace : Race (18, "horse", 4) {

    object STAGE_HALF_HORSE: RacialStage(this, "half equine-morph",
        Stats.SPE_MULT to +0.4,
        Stats.TOU_MULT to +0.2
    )

    object STAGE_HORSE: RacialStage(this, "equine-morph",
        Stats.SPE_MULT to +0.7,
        Stats.TOU_MULT to +0.35
    )

    override fun stageForScore(creature: PlayerCharacter, score: Int) = when {
        score >= 7 -> STAGE_HORSE
        score >= 4 -> STAGE_HALF_HORSE
        else -> null
    }

    override fun basicScore(creature: PlayerCharacter): Int = with(creature) {
        var score = 0
        if (face.type == FaceType.HORSE) {
            score++
        }
        if (ears.type == EarType.HORSE) {
            score++
        }
        if (horns.type == HornType.UNICORN) {
            score = 0
        }
        if (tail.type == TailType.HORSE){
            score++
        }
        if (lowerBody.type == LowerBodyType.HOOFED) {
            score++
        }
        if (countCocksOfType(PenisType.HORSE) > 0) {
            score++
        }
        if (hasVagina() && vaginas[0].type == VaginaType.EQUINE) {
            score++
        }
        if (skin.hasCoatOfType(SkinCoatType.FUR)) {
            score++
            if (arms.type == ArmType.HUMAN) {
                score++
            }
        }
        if (creature.isTaur
        /*
        TODO: Missing races, is this how it should be implemented?
        ||  UnicornRace.basicScore(creature) > 9
        ||  AlicornRace.basicScore(creature) > 11
           */
        ){
           if (score >= 7) {
               score -= 7
           }
           else {
               score = 0
           }
        }

        score
        }
}