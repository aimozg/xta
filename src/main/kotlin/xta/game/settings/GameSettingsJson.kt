package xta.game.settings

external interface GameSettingsJson {
	var eula: Int

	var wsLobbyUrl: String
	var wsIdentity: String
	var wsToken: String
	var wsHostRoom: String
	var wsJoinInvite: String
	var wsPingInterval: Int

	var chatHistoryLimit:Int
	var render:Boolean?
	var renderX2:Boolean?
}
