package xta.game.items.armor

import xta.charview.CharViewImage
import xta.game.PlayerCharacter
import xta.game.items.ArmorItem
import xta.game.items.ArmorType
import xta.game.stats.Stats

/*
 * Created by aimozg on 18.12.2021.
 */
object ArmorLib {
	val ADVCLTH = ArmorItem(
		"AdvClth", "green adventurer's clothes", "a green adventurer's outfit, complete with pointed cap",
		ArmorType.LIGHT, 2, 0, 50
	)
	val A_ROBE_ = ArmorWithBuffs(
		"A.Robe", "apprentice's robe", "an apprentice's robe",
		ArmorType.LIGHT, 0, 1, 25,
		Stats.SPELL_COST to -0.10
	)
	val BEEARMR = ArmorItem(
		"BeeArmr","sexy black chitin armor-plating","a set of chitinous armor",
		ArmorType.HEAVY, 18,0, 1080
	)
	val B_QIPAO = ArmorItem(
		"B.Qipao", "Blue Lunar new year dress (f)", "a Blue Lunar new year dress (f)",
		ArmorType.LIGHT, 0, 0, 100
	)
	val C_CLOTH = ArmorItem(
		"C.Cloth", "comfortable clothes", "a set of comfortable clothes",
		ArmorType.LIGHT, 0, 0, 1
	)
	val W_ROBES = ArmorWithBuffs(
		"W.Robes", "wizard's robes", "a wizard's robes",
		ArmorType.LIGHT, 0, 1, 50,
		Stats.SPELL_COST to -0.25
	)
	val WKIMONO = object: ArmorWithBuffs(
		"W.Kimono", "white kimono", "a white kimono",
		ArmorType.LIGHT, 0, 1, 200,
		Stats.SPELL_COST to -0.20,
		Stats.SOULSKILL_COST to -0.20
	) {
		override fun render(image: CharViewImage, creature: PlayerCharacter) {
			// TODO this has extra conditions - need to properly port them
			image.showPart("clothe/whiteKimonoTop")
			image.showPart("clotheLegs/whiteKimonoBottom")
			image.showPart("clothe/whiteKimonoSleeves")
		}
	}
}

