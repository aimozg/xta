package xta.game.items.weapons

import xta.charview.CharViewImage
import xta.game.PlayerCharacter
import xta.game.items.MeleeWeaponItem
import xta.game.items.MeleeWeaponType
import xta.utils.capitalized

object ItemBeautifulStaff : MeleeWeaponItem(
	"B.Staff",
	"beautiful staff",
	"a beautiful shining staff",
	"This beautiful staff shines brilliantly in the light, showing the flawless craftsmanship.  The pommel and guard are heavily decorated in gold and brass.  Some craftsman clearly poured his heart and soul into this staff.",
	MeleeWeaponType.STAFF,
	"smack",
	2,
	160,
) {
	// TODO corruption blocks equipping
	// TODO smack/shot if Staff Channeling

	override fun equipped(creature: PlayerCharacter) {
		super.equipped(creature)
		creature.spellPowerStat.dynamicBuff(buffTag, name.capitalized()) {
			(0.40 - it.cor*0.01).coerceAtLeast(10.0)
		}
	}

	override fun render(image: CharViewImage, creature: PlayerCharacter) {
		super.render(image, creature)
		creature.statStore.removeBuffs(buffTag)
	}
}
