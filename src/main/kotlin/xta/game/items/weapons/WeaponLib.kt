package xta.game.items.weapons

import xta.game.items.MeleeWeaponType
import xta.game.stats.Stats

/*
 * Created by aimozg on 21.12.2021.
 */
object WeaponLib {
	val B_SWORD = ItemBeautifulSword
	val W_STAFF = MeleeWeaponWithBuffs(
		"W.Staff", "wizard's staff", "a wizard's staff",
		"This staff is made of very old wood and seems to tingle to the touch.  The top has an odd zig-zag shape to it, and the wood is worn smooth from lots of use.  It probably belonged to a wizard at some point and would aid magic use.",
		MeleeWeaponType.STAFF, "smack", 3, 240,
		emptyArray(),
		Stats.SPELL_POWER to +0.40
	).apply {
		sprite = "weapon/staffGeneric"
	}
}
