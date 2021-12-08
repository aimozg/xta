package xta.text

import xta.Player
import xta.game.Scene

/*
 * Created by aimozg on 28.11.2021.
 */
abstract class Display: TextOutput {
	abstract val parser: Parser
	abstract val player: Player
	val character get() = player.char

	override fun selectSelf() {
		parser.player = player
	}
	override fun selectPerson(person:Player) {
		parser.player = person
	}
	override fun outputText(text: String) {
		rawOutput(parser.parse(text))
	}
	abstract var sceneId: String
	open fun startScene(sceneId:String) {
		this.sceneId = sceneId
		parser.player = player
		clearOutput()
	}
	open fun endScene(){}

	abstract fun goto(scene: Scene)
	abstract fun addButton(label: String, hint: String = "", disabled: Boolean = false, callback:()->Unit)
	open fun addButtonDisabled(label:String, hint:String="") {
		addButton(label, hint, true){}
	}
	open fun addButton(label:String, scene: Scene, actionId: String, hint:String="") {
		addButton(label, hint) {
			goto(scene)
		}
	}

	fun otherPlayers() = player.location.players - player
}

