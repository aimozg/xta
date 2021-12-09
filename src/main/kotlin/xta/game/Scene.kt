package xta.game

import xta.Player
import xta.logging.LogManager
import xta.text.Display

/*
 * Created by aimozg on 28.11.2021.
 */
abstract class Scene(
	val sceneId: String
) {
	/**
	 * Scene re-plays when other players leave or enter the location
	 */
	open val playersDynamic: Boolean get() = false
	open fun onLeave(player: Player) {
		logger.debug(player,"leaves scene",sceneId)
	}

	fun execute(player: Player) {
		val oldScene = player.scene
		player.scene = this
		if (oldScene != this) {
			oldScene.onLeave(player)
			logger.debug(player,"enters scene",sceneId)
		}
		execute(player.display)
	}

	fun execute(display: Display) {
		display.startScene(sceneId)
		display.doExecute()
		display.endScene()
	}

	abstract fun Display.doExecute()

	companion object {
		private val logger = LogManager.getLogger("xta.game.Scene")
	}
}

