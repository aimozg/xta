package xta.game.settings

external interface GameSettingsJson {
	var wsLobbyUrl: String
	var wsIdentity: String
	var wsToken: String
	var wsHostRoom: String
	var wsJoinInvite: String
	var wsPingInterval: Int

	var render:Boolean
}
