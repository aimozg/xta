package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Race
import xta.game.creature.RacialStage
import xta.game.creature.body.*
import xta.game.stats.Stats

/*
 * Created by aimozg on 10.12.2021.
 */
object KitsuneRace: Race(17, "kitsune", 5) {
	val basicHairColors = listOf("white", "black", "black", "black", "red", "red", "red")
	val basicFurColors = listOf("orange and white", "black", "black and white", "red", "red and white", "white")
	val elderColors = listOf("metallic golden", "golden blonde", "metallic silver", "silver blonde", "snow white", "iridescent gray")

	// 26+
	object STAGE_INARI:RacialStage(this, "Inari",
		Stats.STR_MULT to -0.50,
		Stats.SPE_MULT to +0.50,
		Stats.INT_MULT to +1.40,
		Stats.WIS_MULT to +2.00,
		Stats.LIB_MULT to +1.10,
		Stats.SENS to +60.0,
		Stats.FATIGUE_MAX_BASE to 1000.0,
		Stats.SF_MAX_MULT to +1.0
	)
	// 21-25
	object STAGE_NINETAIL_OF_BALANCE:RacialStage(this, "nine tailed kitsune of balance",
		Stats.STR_MULT to -0.45,
		Stats.SPE_MULT to +0.40,
		Stats.INT_MULT to +1.25,
		Stats.WIS_MULT to +1.60,
		Stats.LIB_MULT to +0.80,
		Stats.SENS to +45.0,
		Stats.FATIGUE_MAX_BASE to 500.0,
		Stats.SF_MAX_MULT to +0.65
	) {
		override fun nameOf(creature: PlayerCharacter) =
			if (creature.isTaur) "nine tailed kitsune-taur of balance"
			else "nine tailed kitsune of balance"
	}
	// 16-20
	object STAGE_NINETAIL: RacialStage(this,"nine tailed kitsune",
		Stats.STR_MULT to -0.40,
		Stats.SPE_MULT to +0.30,
		Stats.INT_MULT to +1.10,
		Stats.WIS_MULT to +1.25,
		Stats.LIB_MULT to +0.45,
		Stats.SENS to +30.0,
		Stats.FATIGUE_MAX_BASE to 300.0,
		Stats.SF_MAX_MULT to +0.4
	)
	// 9-15
	object STAGE_KITSUNE: RacialStage(this,"kitsune",
		Stats.STR_MULT to -0.35,
		Stats.SPE_MULT to +0.25,
		Stats.INT_MULT to +0.60,
		Stats.WIS_MULT to +0.75,
		Stats.LIB_MULT to +0.30,
		Stats.SENS to +20.0,
		Stats.FATIGUE_MAX_BASE to 100.0,
		Stats.SF_MAX_MULT to +0.2
	)
	// 5-8
	object STAGE_HALF_KITSUNE: RacialStage(this,"kitsune",
		Stats.STR_MULT to -0.30,
		Stats.SPE_MULT to +0.20,
		Stats.INT_MULT to +0.35,
		Stats.WIS_MULT to +0.40,
		Stats.LIB_MULT to +0.25,
		Stats.SENS to +15.0,
		Stats.FATIGUE_MAX_BASE to 50.0,
		Stats.SF_MAX_MULT to +0.1
	)

	override fun stageForScore(creature: PlayerCharacter, score: Int) =
		if (score >= 5) {
			if (creature.tail.type == TailType.FOX && creature.tail.count >= 2 && score >= 9) {
				if (score >= 16 && creature.tail.count >= 9) {
					if (score >= 21 /* TODO and has perk 9KoB */) {
						if (score >= 26) STAGE_INARI
						else STAGE_NINETAIL_OF_BALANCE
					} else STAGE_NINETAIL
				} else STAGE_KITSUNE
			} else STAGE_HALF_KITSUNE
		}else null

	override fun basicScore(creature: PlayerCharacter): Int = with(creature) {
		var kitsuneCounter = 0
		var kitsuneCounter2 = 0
		if (eyes.type == EyeType.FOX) {
			kitsuneCounter++
			kitsuneCounter2++
		}
		if (ears.type == EarType.FOX) {
			kitsuneCounter++
			kitsuneCounter2++
		}
		//If the character has ears other than fox ears, -1
		if (ears.type != EarType.FOX)
			kitsuneCounter--
		if (tailType == TailType.FOX && tailCount >= 2) {
			kitsuneCounter += tailCount
			kitsuneCounter2 += tailCount
		}
		if (tailType != TailType.FOX || (tailType == TailType.FOX && tailCount < 2))
			kitsuneCounter -= 7
		if (skin.basePattern == SkinBasePatternType.MAGICAL_TATTOO || hasFur()) {
			kitsuneCounter++
			kitsuneCounter2++
		}
		if (skin.baseType == SkinBaseType.PLAIN)
			kitsuneCounter ++

		if (hairColor in basicHairColors || hairColor in elderColors)
			kitsuneCounter++
		if (hasCoat() && !hasCoatOfType(SkinCoatType.FUR))
			kitsuneCounter -= 2
		if (skin.baseType != SkinBaseType.PLAIN)
			kitsuneCounter -= 3
		if (arms.type == ArmType.HUMAN || arms.type == ArmType.KITSUNE || arms.type == ArmType.FOX) {
			kitsuneCounter++
			kitsuneCounter2++
		}
		if (lowerBody.type == LowerBodyType.FOX || lowerBody.type == LowerBodyType.HUMAN) {
			kitsuneCounter++
			kitsuneCounter2++
		} else {
			kitsuneCounter--
		}
		if (faceType == FaceType.ANIMAL_TEETH || faceType == FaceType.HUMAN || faceType == FaceType.FOX) {
			kitsuneCounter++
			kitsuneCounter2++
		} else {
			kitsuneCounter--
		}
		//If the character has a 'vag of holding', +1
		if (vaginalCapacity() >= 8000)
			kitsuneCounter++
		/*
		 TODO racial perk bonuses
		//When character get Hoshi no tama
		if (hasPerk(PerkLib.StarSphereMastery))
			kitsuneCounter++;
		if (hasPerk(PerkLib.EnlightenedKitsune) || hasPerk(PerkLib.CorruptedKitsune))
			kitsuneCounter++;
		if (hasPerk(PerkLib.NinetailsKitsuneOfBalance))
			kitsuneCounter++;
		if (hasPerk(MutationsLib.KitsuneThyroidGland))
			kitsuneCounter++;
		if (hasPerk(MutationsLib.KitsuneThyroidGlandPrimitive))
			kitsuneCounter++;
		if (hasPerk(MutationsLib.KitsuneThyroidGlandEvolved))
			kitsuneCounter++;
		if (hasPerk(MutationsLib.KitsuneParathyroidGlands))
			kitsuneCounter++;
		if (hasPerk(MutationsLib.KitsuneParathyroidGlandsEvolved))
			kitsuneCounter++;
		if (hasPerk(MutationsLib.KitsuneParathyroidGlandsFinalForm))
			kitsuneCounter++;
		if ((hasPerk(MutationsLib.KitsuneThyroidGland) || hasPerk(MutationsLib.KitsuneParathyroidGlands)) && hasPerk(PerkLib.ChimericalBodySemiImprovedStage))
			kitsuneCounter++;
		if ((hasPerk(MutationsLib.KitsuneThyroidGlandPrimitive) || hasPerk(MutationsLib.KitsuneParathyroidGlandsEvolved)) && hasPerk(PerkLib.ChimericalBodySemiSuperiorStage))
			kitsuneCounter++;
		if ((hasPerk(MutationsLib.KitsuneThyroidGlandEvolved) || hasPerk(MutationsLib.KitsuneParathyroidGlandsFinalForm)) && hasPerk(PerkLib.ChimericalBodySemiEpicStage))
			kitsuneCounter++;
		if (hasPerk(PerkLib.KitsunesDescendant) || hasPerk(PerkLib.BloodlineKitsune))
			kitsuneCounter += 2;
		if (hasPerk(PerkLib.ChimericalBodyUltimateStage))
			kitsuneCounter += 50;
		if (hasPerk(PerkLib.AscensionHybridTheory) && kitsuneCounter >= 4)
			kitsuneCounter++;
		if (hasPerk(PerkLib.AscensionCruelChimerasThesis) && kitsuneCounter >= 8)
			kitsuneCounter++;
		*/
		if (kitsuneCounter2 < 5) kitsuneCounter = kitsuneCounter2
		return minOf(kitsuneCounter, kitsuneCounter2)
	}
}
