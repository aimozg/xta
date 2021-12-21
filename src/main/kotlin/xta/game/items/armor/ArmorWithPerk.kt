package xta.game.items.armor

import xta.game.PlayerCharacter
import xta.game.creature.PerkType
import xta.game.items.ArmorItem
import xta.game.items.ArmorType

open class ArmorWithPerk(
	id: String, name: String, longName: String, description: String,
	type: ArmorType, def: Int, mdef: Int, cost: Int,
	vararg val perks: PerkType
) : ArmorItem(id, name, longName, description, type, def, mdef, cost) {
	override fun equipped(creature: PlayerCharacter) {
		super.equipped(creature)
		for (perk in perks) {
			creature.addPerk(perk)
		}
	}

	override fun unequipped(creature: PlayerCharacter) {
		super.unequipped(creature)
		for (perk in perks) {
			creature.removePerk(perk)
		}
	}
}
