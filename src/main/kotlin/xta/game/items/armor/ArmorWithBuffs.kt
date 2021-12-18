package xta.game.items.armor

import xta.game.PlayerCharacter
import xta.game.items.ArmorItem
import xta.game.items.ArmorType

// TODO add buffs on equip, remove on unequip
open class ArmorWithBuffs(
	id: String, name: String, longName: String,
	type: ArmorType, def: Int, mdef: Int, cost: Int,
	vararg val buffs: Pair<String,Double>
) : ArmorItem(id, name, longName, type, def, mdef, cost) {
	override fun equipped(creature: PlayerCharacter) {
		for (buff in buffs) {
			creature.statStore.addOrReplaceBuff(
				buff.first,
				buffTag,
				buff.second,
				text = "Item: $name",
				save = false
			)
		}
	}

	override fun unequipped(creature: PlayerCharacter) {
		creature.statStore.removeBuffs(buffTag)
	}
}
