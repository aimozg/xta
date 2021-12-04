package xta.game.scenes

import xta.game.Scene
import xta.text.Display

/*
 * Created by aimozg on 04.12.2021.
 */
object Limbo: Scene("Limbo") {
	override fun Display.doExecute() {
		outputText("You float in nothingness")
	}
}
