package xta.game.items.armor

import xta.game.PlayerCharacter
import xta.game.items.ArmorItem
import xta.game.items.ArmorType
import xta.utils.capitalized

// TODO add buffs on equip, remove on unequip
open class ArmorWithBuffs(
	id: String, name: String, longName: String,
	type: ArmorType, def: Int, mdef: Int, cost: Int,
	vararg val buffs: Pair<String,Double>
) : ArmorItem(id, name, longName, type, def, mdef, cost) {
	override fun equipped(creature: PlayerCharacter) {
		super.equipped(creature)
		for (buff in buffs) {
			creature.statStore.addOrReplaceBuff(
				buff.first,
				buffTag,
				buff.second,
				text = name.capitalized(),
				save = false
			)
		}
	}

	override fun unequipped(creature: PlayerCharacter) {
		super.unequipped(creature)
		creature.statStore.removeBuffs(buffTag)
	}
}
