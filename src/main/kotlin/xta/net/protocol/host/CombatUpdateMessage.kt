package xta.net.protocol.host

import kotlin.js.Json

external interface CombatUpdateMessage {
	var inCombat: Boolean?
	var partyA: Array<String>?
	var partyB: Array<String>?

	/**
	 * key:player id, value:character data
	 */
	var playerData: Json?
}
