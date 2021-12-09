package xta.net.protocol.host

import xta.net.protocol.messages.ActionJson
import kotlin.js.Json

external interface CombatUpdateMessage {
	var inCombat: Boolean?
	var ongoing: Boolean?
	var partyA: Array<String>?
	var partyB: Array<String>?
	var actingPlayerId: String?

	/**
	 * key:player id, value:character data
	 */
	var playerData: Json?

	var myActions: Array<ActionJson>?
	var myContent: String?
}
