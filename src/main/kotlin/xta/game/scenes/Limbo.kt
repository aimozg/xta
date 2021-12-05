package xta.game.scenes

import xta.game.GameLocation
import xta.game.Scene
import xta.text.Display

/*
 * Created by aimozg on 04.12.2021.
 */
object Limbo: GameLocation("Limbo") {
	val scene = scene("scene") {
		outputText("You float in nothingness")
	}
}
