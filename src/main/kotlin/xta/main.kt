package xta

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLElement
import org.w3c.dom.atob
import xta.charview.CharViewImage
import xta.game.ItemType
import xta.game.combat.statuses.StatusLib
import xta.game.creature.PerkType
import xta.game.creature.perks.PerkLib
import xta.game.items.armor.ArmorLib
import xta.game.items.weapons.WeaponLib
import xta.game.settings.GameSettings
import xta.logging.LogManager
import xta.logging.Logger

/*
 * Created by aimozg on 28.11.2021.
 */

private val logger = LogManager.getLogger("xta.main")

fun main() {
	js("""require("game.scss")""")

	val errorBlock = document.getElementById("errors") as HTMLElement
	window.onerror = { msg: dynamic, _: String, _: Int, _: Int, error: Any? ->
		errorBlock.innerHTML = "Unhandled error:"
		if (error is Throwable) {
			val stack = error.stackTraceToString()
			errorBlock.append("\n$stack")
			console.error(stack)
		} else {
			errorBlock.append("\n$msg")
		}
		// TODO copy & close buttons
	}

	ScreenManager.init()
	LogManager.setLevelForMany("", Logger.Level.DEBUG)
//	LogManager.setLevelForMany("xta.charview.CompositeImage", Logger.Level.INFO)
//	LogManager.setLevelForMany("xta.text", Logger.Level.ALL)

	// Objects are lazily initialized, poke them
	logger.debug(null,"Loading libraries...")
	PerkLib
	logger.info(null,"Loaded ${PerkType.BY_ID.size} perks")
	StatusLib
	ArmorLib
	WeaponLib
	logger.info(null,"Loaded ${ItemType.BY_ID.size} items")

	GameSettings.load()
	Game.localMessage("Welcome to the CoC-XTA")

	if (window.location.hash.isNotEmpty()) {
		try {
			val hash = JSON.parse<Array<Any?>>(atob(window.location.hash.removePrefix("#")))
			if (hash[0] == "join") {
				var lobbyurl = hash[1] as String
				if (lobbyurl.startsWith("/")) {
					lobbyurl =
						window.location.origin.replace(Regex("^http"),"ws") +
								lobbyurl
				}
				GameSettings.data.wsLobbyUrl = lobbyurl
				GameSettings.data.wsJoinInvite = hash[2] as String
				Game.localMessage("Invite link imported! Load your character and click 'Join game'")
			}
		} catch (ignored: Throwable) {
			console.error(ignored)
		}
	}

	ScreenManager.showStartMenu()
	if (GameSettings.data.render == true) {
		Game.localMessage("Loading images...")
		window.setTimeout({
			CharViewImage.INSTANCE
			Game.localMessage("Images loaded!")
		}, 50)
	}

	window.asDynamic().game = Game
}
