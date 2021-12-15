package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.body.*
import xta.game.stats.Stats

object HorseRace : Race (18, "horse", 4) {

    object STAGE_HALF_HORSE: RacialStage(this, "half equine-morph"
    )

    object STAGE_HORSE: RacialStage(this, "equine-morph"
    )

    override fun stageForScore(creature: PlayerCharacter, score: Int) = when {
        score >= 7 -> STAGE_HORSE
        score >= 4 -> STAGE_HALF_HORSE
        else -> null
    }

    override fun basicScore(creature: PlayerCharacter): Int = with(creature) {
        var score = 0
        if (face.type == FaceType.HORSE)
            score++
        if (ears.type == EarType.HORSE)
            score++
        if (horns.type == HornType.UNICORN)
            score = 0
        if (tail.type == TailType.HORSE)
            score++
        if (lowerBody.type == LowerBodyType.HOOFED)
            /*
            TODO: Missing/comment-blocked Centaur LowerBodyType
            || lowerBody.type == LowerBodyType.CENTAUR)
            */
            score++
        if (countCocksOfType(PenisType.HORSE) > 0)
            score++
        if (hasVagina())
            /*
            TODO:vaginas.type is not working. What are we supposed to use?
            && vaginas.type == VaginaType.EQUINE)
            */
            score++
        if (skin.hasCoatOfType(SkinCoatType.FUR))
            score++
            if (arms.type == ArmType.HUMAN)
                score++
        if (creature.isTaur
        /*
        TODO: Missing races, is this how it should be implemented?
        ||  UnicornRace.basicScore(creature) > 9
        ||  AlicornRace.basicScore(creature) > 11
           */
        )
            if (score >= 7)
                score -= 7
            else
                score = 0
        /*
        TODO: perk bonus, isGargoyle?
        if (hasPerk(PerkLib.ChimericalBodyUltimateStage))
            dogCounter += 50;
        if (hasPerk(PerkLib.AscensionHybridTheory) && dogCounter >= 4)
            dogCounter += 1;
        if (hasPerk(PerkLib.AscensionCruelChimerasThesis) && dogCounter >= 8)
            dogCounter += 1;
		if (hasPerk(PerkLib.ElementalBody))
            dogCounter = 0;
         */

        score
    }
}