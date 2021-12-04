package xta.game.settings

import js.Object
import kotlinx.browser.localStorage
import xta.logging.LogManager
import xta.utils.jsObject
import xta.utils.randomString
import kotlin.random.Random

/*
 * Created by aimozg on 30.11.2021.
 */
object GameSettings {
	var localStorageKey = "cocxta-settings"
	var data = jsObject<GameSettingsJson> {  }

	init {
		reset()
	}
	fun reset() {
		data.eula = 0

		data.wsLobbyUrl = "ws://127.0.0.1:8081/lobby"
		data.wsIdentity = Random.Default.randomString(16)
		data.wsToken = Random.Default.randomString(16)
		data.wsHostRoom = Random.Default.randomString(16)
		data.wsJoinInvite = ""
		data.wsPingInterval = 15

		data.render = true
	}

	fun load() {
		try {
			Object.assign(data, JSON.parse(localStorage.getItem(localStorageKey)?:"{}"))
		} catch (e:Error) {
			logger.error(null,"Error parsing preferences",e)
			e.printStackTrace()
			reset()
		}
	}

	fun save() {
		localStorage.setItem(localStorageKey,JSON.stringify(data))
	}

	private val logger by lazy {
		LogManager.getLogger("xta.settings.GameSettings")
	}
}

