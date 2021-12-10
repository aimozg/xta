package xta.game.creature.races

import xta.game.PlayerCharacter
import xta.game.creature.Race
import xta.game.creature.body.*

/*
 * Created by aimozg on 10.12.2021.
 */
object KitsuneRace: Race(17, "kitsune", 5) {
	val basicHairColors = listOf("white", "black", "black", "black", "red", "red", "red")
	val basicFurColors = listOf("orange and white", "black", "black and white", "red", "red and white", "white")
	val elderColors = listOf("metallic golden", "golden blonde", "metallic silver", "silver blonde", "snow white", "iridescent gray")

	override fun nameOf(creature: PlayerCharacter, score: Int): String {
		val taur = if (creature.isTaur) "-taur" else ""
		if (creature.tail.type == TailType.FOX && creature.tail.count >= 2 && score >= 9) {
			if (score >= 16 && creature.tail.count >= 9) {
				if (score >= 21 /* TODO and has perk 9KoB */) {
					if (score >= 26) return "Inari$taur"
					return "nine tailed kitsune$taur of balance"
				}
				return "nine tailed kitsune$taur"
			}
			return "kitsune$taur"
		} else {
			return "half kitsune"
		}
	}

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
