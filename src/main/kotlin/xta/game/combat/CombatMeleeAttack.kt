package xta.game.combat

import xta.Player
import xta.utils.gamerng
import kotlin.math.roundToInt

class CombatMeleeAttack(
	actor: Player,
	val target: Player
): AbstractCombatAction(actor) {
	override fun perform() {
		// TODO port combat math
		val hitChance = (actor.char.spe - target.char.spe).coerceIn(5.0, 95.0)
		if (gamerng.nextDouble(100.0) > hitChance) {
			// miss
			display.selectPerson(actor)
			display.outputText("[You] [verb miss] ")
			display.selectPerson(target)
			display.outputText("[you]!")
		} else {
			// hit
			val damage = (gamerng.nextDouble(0.5,1.5)*actor.char.str).roundToInt().coerceAtLeast(1)
			target.char.hp -= damage
			display.selectPerson(actor)
			display.outputText("[You] [verb hit] ")
			display.selectPerson(target)
			display.outputText("[you] (<span class='text-damage'>$damage</span>)!")

		}
	}

	override val label: String
		get() = "Attack ${target.char.name}"
	override val tooltip: String
		get() = "Strike ${target.char.name} with your weapon" // TODO use weapon name
}
