package xta.game.creature.perks

import xta.game.Creature
import xta.game.creature.PerkType
import xta.game.stats.StatMeta

class SimplePerkWithBuffs(id:String,
                          name:String,
                          override val description: String,
                          vararg val buffs:Pair<StatMeta,Double>
						  ): PerkType(id, name) {

	override fun onAdd(host: Creature) {
		for ((stat, value) in buffs) {
			host.statStore.addOrReplaceBuff(
				stat.id,
				buffTag,
				value,
				text = "(Perk) $name",
				save = false
			)
		}
	}

	override fun onRemove(host: Creature) {
		host.statStore.removeBuffs(buffTag)
	}
}
