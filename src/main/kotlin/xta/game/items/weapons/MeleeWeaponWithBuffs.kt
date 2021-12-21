package xta.game.items.weapons

import xta.game.PlayerCharacter
import xta.game.items.MeleeWeaponItem
import xta.game.items.MeleeWeaponTag
import xta.game.items.MeleeWeaponType
import xta.game.stats.StatMeta
import xta.game.stats.explainBuff
import xta.utils.capitalized

open class MeleeWeaponWithBuffs(
	id: String, name:String, longName: String, description:String,
	type: MeleeWeaponType, attackVerb: String, baseAttack: Int, cost: Int,
	tags: Array<MeleeWeaponTag>,
	vararg val buffs: Pair<StatMeta,Double>
): MeleeWeaponItem(id, name, longName, description, type, attackVerb, baseAttack, cost, tags) {
	override fun tooltipHtml(wielder: PlayerCharacter?) = buildString {
		append(super.tooltipHtml(wielder))
		if (buffs.isNotEmpty()) {
			append("Buffs:")
			for ((stat, value) in buffs) {
				explainBuff(stat.displayName, value, true, stat.isPercentage, stat.isGood)
			}
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
