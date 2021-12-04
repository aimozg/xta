package xta.text

import xta.Game
import xta.Player
import xta.game.Scene

/*
 * Created by aimozg on 28.11.2021.
 */
abstract class Display {
	abstract val parser: Parser
	abstract val player: Player
	val character get() = player.char

	fun outputText(text: String) {
		rawOutput(parser.parse(text))
	}
	abstract var sceneId: String
	open fun startScene(sceneId:String) {
		this.sceneId = sceneId
		clearOutput()
	}
	open fun endScene(){}
	abstract fun rawOutput(text: String)
	abstract fun clearOutput()

	abstract fun goto(scene: Scene)
	abstract fun addButton(label: String, actionId: String, hint: String = "", disabled: Boolean = false, callback:()->Unit)
	open fun addButtonDisabled(label:String, hint:String="") {
		addButton(label, "", hint, true){}
	}
	open fun addButton(label:String, scene: Scene, actionId: String, hint:String="") {
		addButton(label,actionId,hint) {
			goto(scene)
		}
	}

	fun allPlayers() = Game.server?.players?:listOf(player)
	fun playersHere() = allPlayers().filter{ it.scene.sceneId == sceneId}
	fun otherPlayers() = playersHere() - player
}

