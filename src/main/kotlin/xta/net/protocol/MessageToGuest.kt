package xta.net.protocol

import xta.net.protocol.host.*

external interface MessageToGuest {
	var chat: DisplayChatMessage?
	var charAccepted: CharAcceptedMessage?
	var charRejected: CharRejectedMessage?
	var statusUpdate: StatusUpdateMessage?
	var sceneTransition: SceneTransitionMessage?
	var combatUpdate: CombatUpdateMessage?
}
