package xta.game.creature.perks

import xta.game.Creature
import xta.game.creature.PerkType

class SimplePerk(id:String, name:String, override val description: String): PerkType(id, name) {
	val buffs = ArrayList<Pair<String,Double>>()

	val buffTag get() = "perk_$id"
	override fun onAdd(host: Creature) {
		for (buff in buffs) {
			host.statStore.findBuffableStat(buff.first)?.addOrReplaceBuff(
				buffTag,
				buff.second,
				text = name,
				save = false
			)
		}
	}

	override fun onRemove(host: Creature) {
		host.statStore.removeBuffs(buffTag)
	}
}

