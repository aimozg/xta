package xta.game

import xta.Player
import xta.logging.LogManager
import xta.text.Display

abstract class GameLocation(val id:String) {
	val players = ArrayList<Player>()

	protected val logger = LogManager.getLogger("xta.game.GameLocation/$id")

	fun playerEntered(player: Player) {
		if (player !in players) {
			logger.info(player,"player",player.id,"entered location",id)
			players.add(player)
			onEnter(player)
		}
	}
	fun playerLeft(player: Player) {
		if (players.remove(player)) {
			logger.info(player,"player",player.id,"left location",id)
			onLeave(player)
		}
	}

	open fun onEnter(player: Player){}
	open fun onLeave(player: Player){}

	fun scene(suffix:String,updateOnVisit: Boolean=false,body: Display.()->Unit) =
		LocationScene(this,suffix, updateOnVisit, body)

}
