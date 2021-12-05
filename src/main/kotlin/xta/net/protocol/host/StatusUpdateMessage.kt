package xta.net.protocol.host

import xta.net.protocol.messages.ScreenJson
import kotlin.js.Json

external interface StatusUpdateMessage {
	var char: Json?
	var screen: ScreenJson?
}
