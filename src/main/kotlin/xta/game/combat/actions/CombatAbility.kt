package xta.game.combat.actions

import xta.Player
import xta.game.PlayerCharacter
import xta.game.combat.Combat
import xta.game.combat.actions.abilities.CombatAbilityAction
import xta.game.combat.actions.abilities.spellswhite.SpellBlind
import xta.game.combat.actions.abilities.spellswhite.SpellHeal
import xta.game.combat.actions.abilities.spellswhite.SpellLightningBolt
import xta.game.combat.actions.abilities.spellswhite.SpellWhitefire

/*
 * Created by aimozg on 20.12.2021.
 */
sealed class CombatAbility {
	abstract val displayName: String
	abstract fun isKnownBy(caster: PlayerCharacter): Boolean
	abstract fun createActions(actor:Player, combat: Combat): List<CombatAbilityAction>

	abstract class TargetingSelf(override val displayName: String): CombatAbility() {
		abstract fun createAction(actor: Player, combat: Combat): CombatAbilityAction
		override fun createActions(actor: Player, combat: Combat) = listOf(createAction(actor,combat))
	}

	abstract class TargetingOneEnemy(override val displayName: String): CombatAbility() {

		abstract fun createAction(
			actor: Player, targetPlayer: Player, combat: Combat
		): CombatAbilityAction

		open fun isValidTarget(target: PlayerCharacter, actor: Player, combat: Combat): Boolean = true

		override fun createActions(actor: Player, combat: Combat): List<CombatAbilityAction> =
			combat.opponentsOf(actor)?.players?.mapNotNull {
				if (isValidTarget(it.char, actor, combat)) createAction(actor, it, combat) else null
			} ?: error("Invalid player $actor")
	}

	companion object {
		val ALL_WHITE_SPELLS = listOf(
			SpellWhitefire,
//			SpellWhitefireEx,
//			SpellPyreBurst,
//			SpellPyreBurstEx,
			SpellLightningBolt,
//			SpellLightningBoltEx,
//			SpellChainLightning,
//			SpellChainLightningEx,
			SpellBlind,
//			SpellChargeWeapon,
//			SpellChargeArmor,
			SpellHeal,
//			SpellBlizzard,
//			SpellMentalShield,
//			SpellCure,
//			SpellFireStorm,
//			SpellMeteorShower
		)
		val ALL_BLACK_SPELLS = listOf<CombatAbility>()
		val ALL_GREY_SPELLS = listOf<CombatAbility>()
		val ALL_HEX_SPELLS = listOf<CombatAbility>()
		val ALL_DIVINE_SPELLS = listOf<CombatAbility>()
		val ALL_NECRO_SPELLS = listOf<CombatAbility>()
		val ALL_BLOOD_SPELLS = listOf<CombatAbility>()

		val ALL_SPELLS = listOf(
			ALL_WHITE_SPELLS,
			ALL_BLACK_SPELLS,
			ALL_GREY_SPELLS,
			ALL_HEX_SPELLS,
			ALL_DIVINE_SPELLS,
			ALL_NECRO_SPELLS,
			ALL_BLOOD_SPELLS
		).flatten()

		val ALL = ALL_SPELLS
	}
}

