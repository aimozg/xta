package xta.game.combat.actions.abilities.spellswhite

import xta.Player
import xta.game.PlayerCharacter
import xta.game.combat.Combat
import xta.game.combat.actions.CombatAbility
import xta.game.combat.actions.abilities.AbstractWhiteSpell
import xta.game.creature.KnownThings
import kotlin.math.roundToInt

/*
 * Created by aimozg on 21.12.2021.
 */
class SpellHeal(
	actor: Player
): AbstractWhiteSpell(actor, "Heal") {
	override val ability: CombatAbility
		get() = Companion
	override val baseManaCost: Int
		get() = 30
	override val description: String
		get() = "Heal will attempt to use white magic to instantly close your wounds and restore your body."
	// TODO cooldown

	fun calcPower(randomize:Boolean = true):Int {
		var heal = 0.0
		heal += caster.scalingBonusIntelligence(randomize)
		// TODO perk bonus, healModWhite bonus, item bonus
		return heal.roundToInt()
	}

	override fun performAbilityEffect() {
		display.selectPerson(actor.char)
		display.outputText("[You] [verb chant] a magical song of healing and recovery and [your] wounds start knitting themselves shut in response. ")
		val power = calcPower()
		// TODO crit roll
		caster.hp += power
		display.outputText("(<span class='text-heal'>$power</span>)")
	}

	companion object: CombatAbility.TargetingSelf("Heal") {
		override fun isKnownBy(caster: PlayerCharacter) = caster.knows(KnownThings.SPELL_HEAL)

		override fun createAction(actor: Player, combat: Combat) = SpellHeal(actor)
	}
}

