package xta.game

import xta.text.Display

class SimpleScene(
	sceneId: String,
	override val updateOnVisit: Boolean = false,
	val body: Display.() -> Unit
) : Scene(sceneId) {
	override fun Display.doExecute() {
		body()
	}
}
