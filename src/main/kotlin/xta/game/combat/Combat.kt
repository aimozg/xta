package xta.game.combat

import xta.Game
import xta.Player
import xta.game.Scene

/*
 * Created by aimozg on 04.12.2021.
 */
class Combat(
	val partyA: Party,
	val partyB: Party,
	val returnScene: Scene
) {
	var ongoing = false
	val participants = partyA.players + partyB.players

	class Party(val players: List<Player>) {
		constructor(player: Player) : this(listOf(player))

		operator fun contains(player: Player) = player in players
		fun isAlive() =
			players.any { it.char.isAlive && it.isConnected }
	}

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

	fun end() {
		ongoing = false
		for (player in participants) {
			player.combat = null
			if (player.isConnected) {
				Game.server?.sendChatNotification(player, "Combat ended")
				player.updateCombatStatus()
				returnScene.execute(player)
			}
		}
	}

	fun checkEnd() {
		if (!partyA.isAlive() || !partyB.isAlive()) end()
	}

	fun playerLeft(player: Player) {
		checkEnd()
	}

	companion object {
		fun oneOnOne(playerA: Player, playerB: Player, returnScene: Scene) =
			Combat(Party(playerA), Party(playerB), returnScene)
	}
}
