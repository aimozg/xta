package xta.game.combat

import xta.Player

/*
 * Created by aimozg on 04.12.2021.
 */
class Combat(
	val partyA: Party,
	val partyB: Party
) {
	var ongoing = false
	fun start() {
		for (player in partyA.players) {
			player.party = partyA
		}
		for (player in partyB.players) {
			player.party = partyB
		}
		ongoing = true
		for (player in participants) {
			player.combat = this
			player.updateCombatStatus()
		}
	}
	val participants = partyA.players + partyB.players

	class Party(val players:List<Player>) {
		fun isAlive() =
			players.any { it.char.isAlive }
	}
}
