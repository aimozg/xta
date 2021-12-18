package xta.game.items

import xta.charview.CharViewImage
import xta.game.ItemType
import xta.game.PlayerCharacter

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

	open fun equipped(creature: PlayerCharacter){}

	open fun unequipped(creature: PlayerCharacter){}
}

