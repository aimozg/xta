package xta.net.protocol.host

import xta.net.protocol.messages.ScreenJson

external interface SceneTransitionMessage {
	var screen: ScreenJson
}
