package xta.game.creature.perks

import xta.game.Creature
import xta.game.creature.PerkType

class SimplePerkWithBuffs(id:String,
                          name:String,
                          override val description: String,
                          vararg val buffs:Pair<String,Double>
						  ): PerkType(id, name) {

	val buffTag = BUFF_TAG_PREFIX + id
	override fun onAdd(host: Creature) {
		for (buff in buffs) {
			host.statStore.addOrReplaceBuff(
				buff.first,
				buffTag,
				buff.second,
				text = "Perk: $name",
				save = false
			)
		}
	}

	override fun onRemove(host: Creature) {
		host.statStore.removeBuffs(buffTag)
	}
}
