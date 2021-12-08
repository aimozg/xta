package xta

import kotlinx.browser.window
import xta.charview.CharViewImage
import xta.game.settings.GameSettings
import xta.logging.LogManager
import xta.logging.Logger

/*
 * Created by aimozg on 28.11.2021.
 */

fun main() {
	js("""require("game.scss")""")

	window.asDynamic().game = Game
	//TODO set onerror
	ScreenManager.init()
	LogManager.setLevelForMany("", Logger.Level.DEBUG)
//	LogManager.setLevelForMany("xta.charview.CompositeImage", Logger.Level.INFO)
//	LogManager.setLevelForMany("xta.text", Logger.Level.ALL)
	GameSettings.load()
	Game.localMessage("Welcome to the CoC-XTA")
	ScreenManager.showStartMenu()
	if (GameSettings.data.render == true) {
		Game.localMessage("Loading images...")
		window.setTimeout({
			CharViewImage.INSTANCE
			Game.localMessage("Images loaded!")
		}, 50)
	}
}
