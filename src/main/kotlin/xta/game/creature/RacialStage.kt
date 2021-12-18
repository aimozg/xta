package xta.game.creature

import xta.game.PlayerCharacter
import xta.game.stats.StatMeta
import xta.game.text.Appearance

/*
 * Created by aimozg on 11.12.2021.
 */
open class RacialStage(
	val race:Race,
	val name:String,
	val buffs:List<Pair<StatMeta,Double>>
) {
	constructor(race:Race,name:String,vararg buffs:Pair<StatMeta,Double>):
			this(race,name,buffs.asList())

	open fun nameOf(creature: PlayerCharacter):String =
		if (creature.isTaur) "$name-taur" else name
	open fun fullNameOf(creature: PlayerCharacter):String {
		val name = nameOf(creature)
		if (creature.gender == Gender.MALE && name.endsWith("-boy")) return name
		if (creature.gender == Gender.FEMALE && name.endsWith("-girl")) return name
		return Appearance.maleFemaleHerm(creature)+" "+name
	}

	open fun applyBonuses(creature: PlayerCharacter) {
		for ((stat, value) in buffs) {
			creature.statStore.addOrReplaceBuff(
				stat,
				Race.BUFF_TAG,
				value,
				text = Race.BUFF_TEXT
			)
		}
	}
}
