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
		"A set of comfortable green adventurer's clothes.  It even comes complete with a pointy hat!",
		ArmorType.LIGHT, 2, 0, 50
	)
	val A_ROBE_ = ArmorWithBuffs(
		"A.Robe", "apprentice's robe", "an apprentice's robe",
		"This drab robe lacks adornment, yet retains an air of mysticality. The low quality of the fabric coupled with its mystic air suggests that it is a garment meant for mages in training.",
		ArmorType.LIGHT, 0, 1, 25,
		Stats.SPELL_COST to -0.10
	)
	val BEEARMR = ArmorItem(
		"BeeArmr","sexy black chitin armor-plating","a set of chitinous armor",
		"A suit of armor cleverly fashioned from giant bee chitin. It comes with a silken loincloth to protect your modesty.",
		ArmorType.HEAVY, 18,0, 1080
	)
	val B_QIPAO = ArmorItem(
		"B.Qipao", "Blue Lunar new year dress (f)", "a Blue Lunar new year dress (f)",
		"A simple yet elegant blue qipao with floral motif. This dress is said to bring in good luck increasing evasiveness and critical chance.",
		ArmorType.LIGHT, 0, 0, 100
	)
	val C_CLOTH = ArmorItem(
		"C.Cloth", "comfortable clothes", "a set of comfortable clothes",
		"These loose fitting and comfortable clothes allow you to move freely while protecting you from the elements.",
		ArmorType.LIGHT, 0, 0, 1
	)
	val W_ROBES = ArmorWithBuffs(
		"W.Robes", "wizard's robes", "a wizard's robes",
		"These robes appear to have once belonged to a female wizard.  They're long with a slit up the side and full billowing sleeves.  The top is surprisingly low cut.  Somehow you know wearing it would aid your spellcasting.",
		ArmorType.LIGHT, 0, 1, 50,
		Stats.SPELL_COST to -0.25
	)
	val WKIMONO = object: ArmorWithBuffs(
		"W.Kimono", "white kimono", "a white kimono",
		"This lovely white kimono is adorned with a floral pattern. It will likely increase your spiritual power as a kitsune.",
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

