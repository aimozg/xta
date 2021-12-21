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
	private val staticDescription: String,
	val type: ArmorType,
	open val def: Int,
	open val mdef: Int,
	val cost: Int
) : ItemType(id, name) {
	open fun render(image: CharViewImage, creature: PlayerCharacter) {}

	override fun description(wielder: PlayerCharacter?) = staticDescription

	override fun tooltipHtml(wielder: PlayerCharacter?) = buildString {
			append(super.tooltipHtml(wielder))
			append("\n")
			append("\tType: ")
			append(type.displayName)
			if (def != 0) {
				append("\nDefense: ")
				append(def)
			}
			if (mdef != 0) {
				append("\nMag. Defense: ")
				append(mdef)
			}
			append("\nCost: ")
			append(cost)
		}

	/**
	 * Called when armor is put in the slot by internal process (ex. from JSON)
	 */
	open fun loaded(creature: PlayerCharacter){ equipped(creature)}

	open fun equipped(creature: PlayerCharacter){
		creature.resistPhysStat.addOrReplaceBuff(
			BUFF_TAG_SLOT,
			def/100.0,
			text = "Armor",
			save = false
		)
		creature.resistMagStat.addOrReplaceBuff(
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

