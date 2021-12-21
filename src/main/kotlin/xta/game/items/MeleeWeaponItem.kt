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

	open fun render(image: CharViewImage, creature: PlayerCharacter) {
		when (type) {
			MeleeWeaponType.AXE -> {
				// TODO fire blade
				image.showPart("weapon/axeGeneric")
				if (isDual) image.showPart("weapon/axeGenericOH")
			}
			MeleeWeaponType.DAGGER -> {
				// TODO fire blade
				image.showPart("weapon/daggerGeneric")
			}
			MeleeWeaponType.DUELING,
			MeleeWeaponType.EXOTIC -> when {
				isKatana -> {
					// TODO fire blade
					image.showPart("weapon/katanaGeneric")
				}
				isRapier -> {
					// TODO fire blade
					image.showPart("weapon/rapierGeneric")
				}
			}
			MeleeWeaponType.MACE -> {
				if (isTesubo) image.showPart("weapon/tetsuGeneric")
				else {
					image.showPart("weapon/hammerGeneric")
					if (isDual) image.showPart("weapon/hammerGenericOH")
				}
			}
			MeleeWeaponType.SPEAR -> image.showPart("weapon/spearGeneric")
			MeleeWeaponType.STAFF -> image.showPart("weapon/staffGeneric")
			MeleeWeaponType.SWORD -> {
				// TODO fire blade
				image.showPart("weapon/swordGeneric")
				if (isDual) image.showPart("weapon/swordGenericOH")
			}
			MeleeWeaponType.GAUNTLET,
			MeleeWeaponType.RIBBON,
			MeleeWeaponType.SCYTHE,
			MeleeWeaponType.WAND,
			MeleeWeaponType.WHIP -> {}
		}
	}

	override fun description(wielder: PlayerCharacter?) = staticDescription

	override fun tooltipHtml(wielder: PlayerCharacter?) = buildString {
		append(super.tooltipHtml(wielder))
		append("\n")
		append("\tType: ")
		append(type.displayName)
		for (tag in tags) {
			append(", ")
			append(tag.displayName)
		}
		append("\nAttack: ")
		append(attack(wielder))
		if (cost > 0) {
			append("\nCost: ")
			append(cost)
		}
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
	val isDual get() = hasTag(MeleeWeaponTag.DUAL)
	val isTesubo get() = hasTag(MeleeWeaponTag.TESTUBO)
	val isKatana get() = hasTag(MeleeWeaponTag.KATANA)
	val isRapier get() = hasTag(MeleeWeaponTag.RAPIER)
	val isThrown get() = hasTag(MeleeWeaponTag.THROWN)

	companion object {
		const val BUFF_TAG_SLOT = "slot_weapon"
	}
}

