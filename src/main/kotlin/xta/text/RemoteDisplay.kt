package xta.text

import js.jspush
import xta.Player
import xta.game.Scene
import xta.game.scenes.Limbo
import xta.net.protocol.messages.ScreenJson
import xta.utils.jsobject

class RemoteDisplay(
	override val player: Player,
	override val parser: Parser
): Display() {
	var screen: ScreenJson = jsobject { scene ->
		scene.sceneId = Limbo.scene.sceneId
		scene.content = "You float in nothingness"
		scene.actions = emptyArray()
	}
	val callbacks = HashMap<Int,()->Unit>()
	private var actionId: Int = 0

	override fun rawOutput(text: String) {
		screen.content += text
	}

	override var sceneId: String
		get() = screen.sceneId
		set(value) {
			screen.sceneId = value
		}

	override fun endScene() {
		if (!player.guest.isConnected) return
		player.guest.onMessage(jsobject { msg ->
			msg.sceneTransition = jsobject {
				it.screen = this@RemoteDisplay.screen
			}
		})
	}

	override fun clearOutput() {
		actionId = 0
		screen.content = ""
		screen.actions = emptyArray()
		callbacks.clear()
	}

	override fun goto(scene: Scene) {
		scene.execute(player)
	}

	// auto-generate action ids?
	override fun addButton(label: String, hint: String, disabled: Boolean, callback: () -> Unit) {
		screen.actions.jspush(jsobject {
			if (disabled) it.disabled = true
			it.label = parser.parse(label)
			it.actionId = actionId++
			if (hint.isNotEmpty()) it.hint = parser.parse(hint)
			if (!disabled) {
				callbacks[it.actionId] = callback
			}
		})
	}
}
