package xta.net.protocol

import xta.net.protocol.guest.*

external interface MessageToHost {
	var chat: SendChatMessage?
	var offerChar: OfferCharMessage?
	var statusRequest: StatusRequestMessage?
	var sceneAction: SceneActionMessage?
}
