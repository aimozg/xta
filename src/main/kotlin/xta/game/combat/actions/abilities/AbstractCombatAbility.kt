package xta.game.combat.actions.abilities

import xta.Player
import xta.game.combat.AbstractCombatAction
import xta.utils.wrapIfNotEmpty

/*
 * Created by aimozg on 14.12.2021.
 */
abstract class AbstractCombatAbility(actor:Player): AbstractCombatAction(actor) {
	protected val caster = actor.char

	abstract fun isKnown(): Boolean

	fun isUsable():Boolean = usabilityCheck() == null
	fun isKnownAndUsable():Boolean = isKnown() && isUsable()
	open fun usabilityCheck():String? {
		// TODO is lasting and not stackable and active
		// TODO cooldown check
		return null
	}
	override val enabled: Boolean
		get() = isKnownAndUsable()

	abstract val name: String
	abstract val description: String
	open fun describeEffect():String = ""
	open fun describeCost():String = ""

	open fun useResources() {}
	protected abstract fun performAbilityEffect()

	override fun perform() {
		useResources()
		performAbilityEffect()
	}

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

