package xta.net.protocol.guest

external interface SceneActionMessage {
	var sceneId: String
	var actionId: Int
	// TODO allow extra payload like text input
}
