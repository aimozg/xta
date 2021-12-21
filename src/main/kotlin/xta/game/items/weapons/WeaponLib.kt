package xta.game.items.weapons

import xta.game.items.MeleeWeaponItem
import xta.game.items.MeleeWeaponTag
import xta.game.items.MeleeWeaponType
import xta.game.stats.Stats

/*
 * Created by aimozg on 21.12.2021.
 */
object WeaponLib {
	val ADAGGER = MeleeWeaponItem(
		"ADagger", "amethyst dagger", "an amethyst dagger",
		"This dagger is made of obsidian and grotesquely decorated with amethysts and lead engravings. The magic within this murky blade will bleed unnatural darkness when charged with magic.",
		MeleeWeaponType.DAGGER, "stab", 3, 240,
		arrayOf(MeleeWeaponTag.SMALL)
	)
	val B_STAFF = ItemBeautifulStaff
	val B_SWORD = ItemBeautifulSword
	val BFGAUNT = MeleeWeaponWithBuffs(
		"BFGaunt", "big fucking gauntlets", "a big fucking gauntlets",
		"Big Fucking Gauntlets - the best solution for a tiny e-pen complex at this side of the Mareth!  This huge gauntlets signed by mysterious 'Vi' requires 150 strength to fully unleash it power.",
		MeleeWeaponType.GAUNTLET, "smash", 0, 1600,
		arrayOf(MeleeWeaponTag.LGWRATH, MeleeWeaponTag.STUN40),
		Stats.DAMAGE_MELEE_MULT to +3.0
	)
	val B_SCARB = MeleeWeaponItem(
		"B.ScarB", "broken scarred blade", "a broken scarred blade",
		"This saber, made from lethicite-imbued metal, seems to no longer seek flesh; whatever demonic properties in this weapon is gone now but it's still an effective weapon.",
		MeleeWeaponType.SWORD, "slash", 12, 480
	)
	val CLAWS = MeleeWeaponItem(
		"Claws", "gauntlet with claws", "a gauntlet with claws",
		"Those metal gauntlets have tips of the fingers shaped like sharp natural claws.  Though they lacks the damaging potential of other weapons, they have a chance to leave bleeding wounds.",
		MeleeWeaponType.GAUNTLET, "rend", 0, 100, arrayOf(MeleeWeaponTag.BLEED10)
	)
	val DAGGER = MeleeWeaponItem(
		"Dagger ", "dagger", "a dagger",
		"A small blade.  Preferred weapon for the rogues.",
		MeleeWeaponType.DAGGER, "stab", 3, 120,
		arrayOf(MeleeWeaponTag.SMALL)
	)
	val DAGWHIP = MeleeWeaponItem(
		"DagWhip", "dagger whip", "a dagger whip",
		"A small blade that is tied to the wrist by an 8 ft. cloth.  Could be used to attack few enemies at once.",
		MeleeWeaponType.DAGGER, "whip-like slash", 5, 200,
		arrayOf(MeleeWeaponTag.SMALL, MeleeWeaponTag.WHIPPING)
	)

	val FLAIL = MeleeWeaponItem(
		"Flail  ", "flail", "a flail",
		"This is a flail, a weapon consisting of a metal spiked ball attached to a stick by chain. Be careful with this as you might end up injuring yourself.",
		MeleeWeaponType.MACE, "smash", 10, 400,
		arrayOf(MeleeWeaponTag.WHIPPING)
	)

	// TODO x1.5 vs flying
	val FRTAXE = MeleeWeaponItem(
		"Fr.T.Axe", "Francisca throwing axe", "a Francisca throwing axe",
		"A foreign axe, made in polished steel and decorated with hunting reliefs in gold and silver. It’s unusually light for its size, so you may be able to manage it with a single hand. Some runes engraved on the handle assure that it will return to you once it has hit your opponent.",
		MeleeWeaponType.AXE, "cleave", 25, 2000,
		arrayOf(MeleeWeaponTag.THROWN, MeleeWeaponTag.LARGE)
	)
	val CHAKRAM = MeleeWeaponItem(
		"chakram", "chakram", "a chakram",
		"The chakram is a simple, elegant, and highly portable thrown weapon. It is a flat, open-centered metal discus with a sharpened edge.",
		MeleeWeaponType.EXOTIC,
		"slash", 12, 960, arrayOf(MeleeWeaponTag.THROWN)
	)
	// TODO buffs golems powers
	val G_ROD = MeleeWeaponItem(
		"G. Rod", "Golemancer Rod", "a Golemancer Rod",
		"This metal rod seems to empower golems through energy influx.",
		MeleeWeaponType.WAND, "smack", 0, 100,
	)
	val H_GAUNT = MeleeWeaponItem(
		"H.Gaunt", "hooked gauntlets", "a set of hooked gauntlets",
		"These metal gauntlets are covered in nasty looking hooks that are sure to tear at your foes flesh and cause them harm.",
		MeleeWeaponType.GAUNTLET, "clawing punch", 0, 400,
		arrayOf(MeleeWeaponTag.BLEED25)
	)
	// TODO corruption-scaled damage
	val KARMTOU = MeleeWeaponWithBuffs(
		"KarmTou", "karmic gloves", "a pair of karmic gloves",
		"A pair of gauntlets, made in shining steel and snow-white cloth. Their touch brings waste into the wicked’s flesh, punishing them in the form of blows more painful then should be.",
		MeleeWeaponType.GAUNTLET, "punch", 0, 400,
		arrayOf(MeleeWeaponTag.STUN25),
		Stats.SOULSKILL_PHYS_POWER to +0.50
	)
	// TODO armor piercing 5
	val KATANA = MeleeWeaponItem(
		"Katana ", "katana", "a katana",
		"A curved bladed weapon that cuts through flesh with the greatest of ease.",
		MeleeWeaponType.DUELING, "keen cut", 17, 680,
		arrayOf(MeleeWeaponTag.LARGE)
	)
	val KIHAAXE = MeleeWeaponItem(
		"KihaAxe", "fiery double-bladed axe", "a fiery double-bladed axe",
		"This large, double-bladed axe matches Kiha's axe. It's constantly flaming.",
		MeleeWeaponType.AXE, "fiery cleave", 22, 880,
		arrayOf(MeleeWeaponTag.LARGE)
	)
	val W_STAFF = MeleeWeaponWithBuffs(
		"W.Staff", "wizard's staff", "a wizard's staff",
		"This staff is made of very old wood and seems to tingle to the touch.  The top has an odd zig-zag shape to it, and the wood is worn smooth from lots of use.  It probably belonged to a wizard at some point and would aid magic use.",
		MeleeWeaponType.STAFF, "smack", 3, 240,
		emptyArray(),
		Stats.SPELL_POWER to +0.40
	)
}
