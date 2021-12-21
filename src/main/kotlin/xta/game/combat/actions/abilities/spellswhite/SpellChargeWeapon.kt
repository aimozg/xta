package xta.game.combat.actions.abilities.spellswhite

import xta.Player
import xta.game.PlayerCharacter
import xta.game.combat.Combat
import xta.game.combat.actions.CombatAbility
import xta.game.combat.actions.abilities.AbstractWhiteSpell
import xta.game.creature.KnownThings

class SpellChargeWeapon(actor: Player): AbstractWhiteSpell(actor, "Charge Weapon") {
	override val ability: CombatAbility
		get() = Companion
	override val baseManaCost: Int
		get() = 60
	override val description: String
		get() = "The Charge Weapon spell will surround your weapons in electrical energy, causing them to do even more damage.  The effect lasts for a few combat turns."

	// TODO describeEffect

	fun calcDuration():Int {
		// TODO + combat.magic.perkRelatedDurationBoosting()
		return 5
	}

	override fun performAbilityEffect() {
		TODO("Not yet implemented")
	}

	companion object: CombatAbility.TargetingSelf("Charge Weapon") {
		override fun isKnownBy(caster: PlayerCharacter) = caster.knows(KnownThings.SPELL_CHARGEWEAPON)

		override fun createAction(actor: Player, combat: Combat) = SpellChargeWeapon(actor)
	}
}
