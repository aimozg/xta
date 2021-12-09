package xta.game.settings

import js.Object
import kotlinx.browser.localStorage
import kotlinx.browser.window
import xta.game.PlayerCharacter
import xta.logging.LogManager
import xta.utils.jsObject
import xta.utils.jsobject
import xta.utils.randomString
import xta.utils.stringify
import kotlin.random.Random

/*
 * Created by aimozg on 30.11.2021.
 */
object GameSettings {
	var localStorageKey = "cocxta-settings"
	var localStorageCharKey = "cocxta-character"
	var data = jsObject<GameSettingsJson> {  }

	init {
		reset()
	}
	fun reset() {
		data.eula = 0

		val location = window.location
		val port = if (location.port.isEmpty()) "" else ":"+location.port
		data.wsLobbyUrl = when (location.protocol) {
			"http:" -> "ws://" + location.hostname + port + "/lobby"
			"https:" -> "wss://" + location.hostname + port + "/lobby"
			else -> "ws://127.0.0.1:8081/lobby"
		}
		data.wsIdentity = Random.Default.randomString(16)
		data.wsToken = Random.Default.randomString(16)
		data.wsHostRoom = Random.Default.randomString(16)
		data.wsJoinInvite = ""
		data.wsPingInterval = 15

		data.render = true
		data.renderX2 = false
	}

	fun load() {
		try {
			Object.assign(data, JSON.parse(localStorage.getItem(localStorageKey)?:"{}"))
		} catch (e:Error) {
			logger.error(null,"Error importing game settings",e)
			reset()
		}
	}
	fun loadCharacter():PlayerCharacter? {
		try {
			val scj = JSON.parse<SavedCharacterJson>(localStorage.getItem(localStorageCharKey)?:"{}")
			if (scj.version == SCJ_VERSION) return PlayerCharacter().apply { deserializeFromJson(scj.data) }
		} catch (e:Error) {
			logger.error(null,"Error importing character",e)
		}
		return null
	}

	fun save() {
		try {
			localStorage.setItem(localStorageKey, JSON.stringify(data))
		} catch (e:Error) {
			logger.error(null,"Error saving settings",e)
		}
	}
	fun saveCharacter(char:PlayerCharacter) {
		try {
			localStorage.setItem(localStorageCharKey, jsobject<SavedCharacterJson> {
				it.version = SCJ_VERSION
				it.data = char.serializeToJson()
			}.stringify())
		} catch (e:Error) {
			logger.error(null,"Error saving character",e)
		}
	}

	private val logger by lazy {
		LogManager.getLogger("xta.settings.GameSettings")
	}
	private const val SCJ_VERSION = 1
}

