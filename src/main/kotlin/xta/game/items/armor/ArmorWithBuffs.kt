package xta.game.items.armor

import xta.game.PlayerCharacter
import xta.game.items.ArmorItem
import xta.game.items.ArmorType
import xta.game.stats.BuffableStat.Companion.explainBuff
import xta.game.stats.StatMeta
import xta.utils.capitalized

open class ArmorWithBuffs(
	id: String, name: String, longName: String, description: String,
	type: ArmorType, def: Int, mdef: Int, cost: Int,
	vararg val buffs: Pair<StatMeta,Double>
) : ArmorItem(id, name, longName, description, type, def, mdef, cost) {

	override fun tooltipHtml(wielder: PlayerCharacter?) = buildString {
		append(super.tooltipHtml(wielder))
		for ((stat, value) in buffs) {
			explainBuff(stat.displayName, value, true, stat.isPercentage, stat.isGood)
		}
	}

	override fun equipped(creature: PlayerCharacter) {
		super.equipped(creature)
		for ((stat, value) in buffs) {
			creature.statStore.addOrReplaceBuff(
				stat.id,
				buffTag,
				value,
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
