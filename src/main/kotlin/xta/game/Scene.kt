package xta.game

import xta.Player
import xta.text.Display

/*
 * Created by aimozg on 28.11.2021.
 */
abstract class Scene(val sceneId: String) {
	fun execute(player: Player) {
		execute(player.display)
	}
	fun execute(display: Display) {
		display.startScene(sceneId)
		display.doExecute()
		display.endScene()
	}
	abstract fun Display.doExecute()
}
