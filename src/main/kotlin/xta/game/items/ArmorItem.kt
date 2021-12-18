package xta.game.items

import xta.charview.CharViewImage
import xta.game.ItemType
import xta.game.PlayerCharacter
import xta.game.stats.Stats

/*
 * Created by aimozg on 18.12.2021.
 */
@JsExport
open class ArmorItem(
	id: String,
	name: String,
	longName: String,
	type: ArmorType,
	open val def: Int,
	open val mdef: Int,
	val cost: Int
) : ItemType(id, name) {
	open fun render(image: CharViewImage, creature: PlayerCharacter) {}

	/**
	 * Called when armor is put in the slot by internal process (ex. from JSON)
	 */
	open fun loaded(creature: PlayerCharacter){ equipped(creature)}

	open fun equipped(creature: PlayerCharacter){
		creature.statStore.addOrReplaceBuff(
			Stats.RESIST_PHYS,
			BUFF_TAG_SLOT,
			def/100.0,
			text = "Armor",
			save = false
		)
		creature.statStore.addOrReplaceBuff(
			Stats.RESIST_MAG,
			BUFF_TAG_SLOT,
			mdef/100.0,
			text = "Armor",
			save = false
		)
	}

	open fun unequipped(creature: PlayerCharacter){
		creature.statStore.removeBuffs(BUFF_TAG_SLOT)
	}

	companion object {
		const val BUFF_TAG_SLOT = "slot_armor"
	}
}

