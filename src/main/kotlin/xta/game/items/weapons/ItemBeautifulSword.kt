package xta.game.items.weapons

import xta.game.Creature
import xta.game.items.MeleeWeaponItem
import xta.game.items.MeleeWeaponType

/*
 * Created by aimozg on 21.12.2021.
 */
object ItemBeautifulSword : MeleeWeaponItem(
	"B.Sword",
	"beautiful sword",
	"a beautiful shining sword",
	"This beautiful sword shines brilliantly in the light, showing the flawless craftsmanship of its blade.  The pommel and guard are heavily decorated in gold and brass.  Some craftsman clearly poured his heart and soul into this blade.",
	MeleeWeaponType.SWORD,
	"slash",
	7,
	560
) {
	// TODO corruption blocks equipping
	init {
		sprite = "weapon/swordHoly"
	}

	override fun attack(wielder: Creature?): Int {
		var attack = baseAttack
		if (wielder == null) return baseAttack
		attack += (10 - wielder.cor/3)
		if (attack < 5) attack = 5
		return attack
	}
}

