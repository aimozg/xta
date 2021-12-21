package xta.game.items

import xta.game.ItemType
import xta.net.serialization.JsonSerializer

object ItemSerializers {
	object ArmorSerializer: JsonSerializer<ArmorItem?> {
		override fun serializeObject(t: ArmorItem?) = t?.id

		override fun deserializeObject(j: dynamic): ArmorItem? {
			if (j == null || j == undefined) return null
			val item = ItemType.BY_ID[j as String] ?: return null
			return item as? ArmorItem ?: error("Item with id '$j' is not an armor")
		}
	}
	object MeleeWeaponSerializer: JsonSerializer<MeleeWeaponItem?> {
		override fun serializeObject(t: MeleeWeaponItem?) = t?.id

		override fun deserializeObject(j: dynamic): MeleeWeaponItem? {
			if (j == null || j == undefined) return null
			val item = ItemType.BY_ID[j as String] ?: return null
			return item as? MeleeWeaponItem ?: error("Item with id '$j' is not a melee weapon")
		}
	}
}
