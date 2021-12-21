package xta.game.combat.actions.abilities

import xta.Player
import xta.game.combat.AbstractCombatAction
import xta.game.combat.actions.CombatAbility
import xta.text.numberOfThings
import xta.utils.joinAppend
import xta.utils.wrapIfNotEmpty

/*
 * Created by aimozg on 14.12.2021.
 */
abstract class CombatAbilityAction(actor:Player): AbstractCombatAction(actor) {
	abstract val ability: CombatAbility
	protected val caster = actor.char

	open fun isKnown() = ability.isKnownBy(caster)

	fun isUsable():Boolean = usabilityCheck() == null
	fun isKnownAndUsable():Boolean = isKnown() && isUsable()

	open fun usabilityCheck():String? {
		// TODO is lasting and not stackable and active
		if (currentCooldown > 0) return "You need to wait " + numberOfThings(currentCooldown,"round") +
				" before you can use this ability again."
		return null
	}
	override val enabled: Boolean
		get() = isKnownAndUsable()

	abstract val name: String
	abstract val description: String
	open fun describeEffect():String = ""
	open fun describeCost():String = buildString {
		if (wrathCost > 0) append("Wrath Cost: $wrathCost")
		if (staminaCost > 0) joinAppend("Stamina Cost: $staminaCost")
		if (manaCost > 0) joinAppend("Mana Cost: $manaCost")
		if (sfCost > 0) joinAppend("Soulforce Cost: $sfCost")
	}

	open val wrathCost: Int = 0
	open val staminaCost: Int = 0
	open val manaCost: Int = 0
	open val sfCost: Int = 0
	open val currentCooldown: Int = 0

	open fun useResources() {}
	protected abstract fun performAbilityEffect()

	override fun perform() {
		useResources()
		performAbilityEffect()
	}

	// TODO 'Cast X on Y'
	override val label: String
		get() = name
	override val tooltip: String?
		get() = buildString {
			val ucheck = usabilityCheck()
			if (ucheck != null) {
				append("<b>")
				append(ucheck)
				append("</b>\n\n")
			}
			append(description)
			append("\n")
			append(describeEffect().wrapIfNotEmpty("\n<b>Effect: ","</b>"))
			append(describeCost().wrapIfNotEmpty("\n<b>","</b>"))
			// TODO presentTags
		}
}

