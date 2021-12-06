package xta.game.settings

import kotlin.js.Json

external interface SavedCharacterJson {
	var version: Int?
	var data: Json
}
