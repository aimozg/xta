package xta.game

import xta.text.Display

class LocationScene(
	val location: GameLocation,
	suffix: String,
	override val playersDynamic: Boolean = false,
	val body: Display.() -> Unit
) : Scene(location.id+"/"+suffix) {
	override fun Display.doExecute() {
		player.location = location
		body()
	}
}
