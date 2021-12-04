package xta.net.protocol.host

import xta.net.protocol.messages.CharacterJson
import xta.net.protocol.messages.ScreenJson

external interface StatusUpdateMessage {
	var char: CharacterJson?
	var screen: ScreenJson?
}
