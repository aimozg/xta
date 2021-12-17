package xta.game.items.armor

import xta.game.creature.PerkType
import xta.game.items.ArmorItem
import xta.game.items.ArmorType

// TODO add perk on equip, remove on unequip
open class ArmorWithPerk(
	id: String, name: String, longName: String,
	type: ArmorType, def: Int, mdef: Int, cost: Int,
	vararg val perks: PerkType
) : ArmorItem(id, name, longName, type, def, mdef, cost)
