package xta.game

import xta.Game
import xta.Player
import xta.logging.LogManager
import xta.text.Display

/*
 * Created by aimozg on 28.11.2021.
 */
abstract class Scene(val sceneId: String) {
	open val updateOnVisit: Boolean get() = false
	open fun onLeave(player: Player) {
		logger.debug(player,"leaves",sceneId)
		if (updateOnVisit) {
			for (other in playersHere()) {
				if (player != other) {
					Game.server?.updateScene(other)
				}
			}
		}
	}

	fun execute(player: Player) {
		val isNew = player.scene != this
		execute(player.display)
		val oldScene = player.scene
		if (oldScene != this) {
			player.scene = this
			oldScene.onLeave(player)
			logger.debug(player,"enters",sceneId)
			if (isNew && updateOnVisit) {
				for (other in playersHere()) {
					if (player != other) {
						Game.server?.updateScene(other)
					}
				}
			}
		}
	}

	fun execute(display: Display) {
		display.startScene(sceneId)
		display.doExecute()
		display.endScene()
	}

	abstract fun Display.doExecute()

	fun allPlayers(): List<Player> = Game.server?.players ?: emptyList()
	fun playersHere() = allPlayers().filter { it.scene == this }

	companion object {
		private val logger = LogManager.getLogger("xta.game.Scene")
	}
}

