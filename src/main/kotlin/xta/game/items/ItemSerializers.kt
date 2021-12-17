package xta.game.items

import xta.game.ItemType
import xta.net.serialization.JsonSerializer

object ItemSerializers {
	object ArmorSerializer: JsonSerializer<ArmorItem?> {
		override fun serializeObject(t: ArmorItem?) = t?.id

		override fun deserializeObject(j: dynamic): ArmorItem? {
			val item = ItemType.BY_ID[j as String] ?: return null
			return item as? ArmorItem ?: error("Item with id '$j' is not an armor")
		}
	}
}
