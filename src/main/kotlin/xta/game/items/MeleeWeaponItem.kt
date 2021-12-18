package xta.game.items

import xta.game.ItemType
import xta.game.PlayerCharacter

/*
 * Created by aimozg on 19.12.2021.
 */
@JsExport
open class MeleeWeaponItem(
	id: String,
	name: String,
	longName: String,
	override val description: String,
	val type: MeleeWeaponType,
	val attackVerb: String,
	open val attack: Int,
	val cost: Int
): ItemType(id, name) {
	override val tooltipHtml: String
		get() = buildString {
			append(super.tooltipHtml)
			append("\n")
			append("\tType: ")
			append(type.displayName)
			append("\nAttack: ")
			append(attack)
			append("\nCost: ")
			append(cost)
		}

	/**
	 * Called when weapon is put in the slot by internal process (ex. from JSON)
	 */
	open fun loaded(creature: PlayerCharacter) { equipped(creature) }

	open fun equipped(creature: PlayerCharacter) {

	}
	open fun unequipped(creature: PlayerCharacter) {

	}
	companion object {
		const val BUFF_TAG_SLOT = "slot_weapon"
	}
}

enum class MeleeWeaponType(
	val displayName: String
) {
	AXE("Axe"),
	DAGGER("Dagger"),
	GAUNTLET("Gauntlet"),
	MACE("Mace/Hammer"),
	SWORD("Sword"),
	WAND("Wand"),
	EXOTIC("Exotic"),
}
