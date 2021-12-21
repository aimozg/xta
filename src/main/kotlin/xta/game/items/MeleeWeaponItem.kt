package xta.game.items

import xta.charview.CharViewImage
import xta.game.Creature
import xta.game.ItemType
import xta.game.PlayerCharacter

/*
 * Created by aimozg on 19.12.2021.
 */
@JsExport
open class MeleeWeaponItem(
	id: String,
	name: String,
	val longName: String,
	val staticDescription: String,
	val type: MeleeWeaponType,
	val attackVerb: String,
	val baseAttack: Int,
	val cost: Int,
	val tags: Array<out MeleeWeaponTag> = emptyArray()
): ItemType(id, name) {
	var sprite: String? = null

	open fun render(image: CharViewImage, creature: PlayerCharacter) {
		if (sprite != null) image.showPart(sprite!!)
	}

	override fun description(wielder: PlayerCharacter?) = staticDescription

	override fun tooltipHtml(wielder: PlayerCharacter?) = buildString {
			append(super.tooltipHtml(wielder))
			append("\n")
			append("\tType: ")
			append(type.displayName)
			append("\nAttack: ")
			append(attack(wielder))
			append("\nCost: ")
			append(cost)
		}

	open fun attack(wielder: Creature?) = baseAttack

	/**
	 * Called when weapon is put in the slot by internal process (ex. from JSON)
	 */
	open fun loaded(creature: PlayerCharacter) { equipped(creature) }

	open fun equipped(creature: PlayerCharacter) {

	}
	open fun unequipped(creature: PlayerCharacter) {

	}

	fun hasTag(tag: MeleeWeaponTag) = tag in tags

	companion object {
		const val BUFF_TAG_SLOT = "slot_weapon"
	}
}

