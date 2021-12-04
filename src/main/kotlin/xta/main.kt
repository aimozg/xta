package xta

import kotlinx.browser.window
import xta.charview.CharViewImage
import xta.logging.LogManager
import xta.logging.Logger
import xta.game.settings.GameSettings

/*
 * Created by aimozg on 28.11.2021.
 */

fun main() {
	js("""require("res/game.css")""")

	//TODO set onerror
	ScreenManager.init()
	LogManager.setLevelForMany("", Logger.Level.DEBUG)
	LogManager.setLevelForMany("xta.charview.CompositeImage", Logger.Level.INFO)
//	LogManager.setLevelForMany("xta.text", Logger.Level.ALL)
	GameSettings.load()
	Game.whisperToSelf("Welcome to the CoC-XTA")
	ScreenManager.showStartMenu()
	if (GameSettings.data.render) {
		Game.whisperToSelf("Loading images...")
		window.setTimeout({
			CharViewImage.INSTANCE
			Game.whisperToSelf("Images loaded!")
		}, 50)
	}
}
