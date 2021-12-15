package xta.game.creature

import xta.game.Creature

/*
 * Created by aimozg on 09.12.2021.
 */
abstract class PerkType(
	val id:String,
	val name:String
) {
	abstract val description: String
	val buffTag = BUFF_TAG_PREFIX + id

	open fun onAdd(host: Creature){}
	open fun onRemove(host: Creature){}

	init {
		BY_ID[id] = this
	}

	companion object {
		val BUFF_TAG_PREFIX = "perk_"

		val BY_ID = HashMap<String, PerkType>()
	}
}

