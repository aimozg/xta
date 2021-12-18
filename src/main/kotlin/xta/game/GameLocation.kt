package xta.game

import xta.Player
import xta.logging.LogManager
import xta.text.Display

abstract class GameLocation(val id:String) {
	val players = ArrayList<Player>()

	protected val logger = LogManager.getLogger("xta.game.GameLocation/$id")

	fun playersInScene(sceneId:String) = players.filter { it.scene.sceneId == sceneId }
	fun playerEntered(player: Player) {
		if (player !in players) {
			logger.info(player,"player",player.id,"entered location",id)
			players.add(player)
			for (other in players) {
				if (other != player && other.scene.playersDynamic && !other.inCombat) {
					other.replayScene()
				}
			}
			onEnter(player)
		}
	}
	fun playerLeft(player: Player) {
		if (players.remove(player)) {
			logger.info(player,"player",player.id,"left location",id)
			for (other in players) {
				if (other != player && other.scene.playersDynamic && !other.inCombat) {
					other.replayScene()
				}
			}
			onLeave(player)
		}
	}

	open fun onEnter(player: Player){}
	open fun onLeave(player: Player){}
	open fun onCombatStatusChange(){}

	fun scene(suffix:String, playersDynamic: Boolean=false, body: Display.()->Unit) =
		LocationScene(this,suffix, playersDynamic, body)

}
