package xta.game.combat.actions.abilities.spellswhite

import xta.Player
import xta.game.PlayerCharacter
import xta.game.combat.Combat
import xta.game.combat.actions.CombatAbility
import xta.game.combat.actions.abilities.AbstractWhiteSpell
import xta.game.combat.statuses.StatusLib
import xta.game.creature.KnownThings
import xta.text.numberOfThings
import xta.utils.gamerng
import xta.utils.percentRoll

class SpellBlind(
	actor: Player,
	val targetPlayer: Player
): AbstractWhiteSpell(
	actor,
	"Blind"
) {
	override val ability: CombatAbility
		get() = Companion

	// TODO cooldown
	val target = targetPlayer.char
	override val description: String
		get() = "Blind is a fairly self-explanatory spell.  It will create a bright flash just in front of the victim's eyes, blinding them for a time.  However if they blink it will be wasted."

	override val baseManaCost
		get() = 30

	override fun describeEffect(): String =
		successRatePercent().toString()+"% chance to blind target for "+ numberOfThings(calcDuration(false),"round")

	fun calcDuration(randomize: Boolean = true): Int {
		return 2 + caster.int.toInt() / 20
	}

	fun successRatePercent():Int {
		return (25.0 + caster.int*0.25).toInt().coerceIn(0, 55)
	}

	override fun performAbilityEffect() {
		display.selectNpcs(caster, target)
		display.outputText("[You] [verb glare] at [npc1 you] and [verb point] at [npc1 him].  A bright flash erupts before [npc1 him]! ")
		// TODO combat roll
		if (gamerng.percentRoll(successRatePercent())) {
			display.outputText("<b>[Npc1 you] [npc1 are] blinded!</b>")
			val duration = calcDuration()
			target.createStatusEffect(
				StatusLib.Blind,
				duration
			)
		} else {
			display.outputText("[Npc1 you] blinked!")
		}
	}

	companion object: CombatAbility.TargetingOneEnemy("Blind") {
		override fun isKnownBy(caster: PlayerCharacter) = caster.knows(KnownThings.SPELL_BLIND)

		override fun createAction(actor: Player, targetPlayer: Player, combat: Combat) =
			SpellBlind(actor, targetPlayer)
	}
}
