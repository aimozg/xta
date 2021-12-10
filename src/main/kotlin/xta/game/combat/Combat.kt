package xta.game.combat

import xta.Game
import xta.Player
import xta.game.Scene
import xta.game.combat.actions.CombatFinish
import xta.game.combat.actions.CombatMeleeAttack
import xta.game.combat.actions.CombatSurrender
import xta.game.combat.actions.CombatWait
import xta.logging.LogContext
import xta.logging.LogManager
import xta.text.TextOutput

/*
 * Created by aimozg on 04.12.2021.
 */
class Combat(
	val partyA: Party,
	val partyB: Party,
	val returnScene: Scene
): LogContext {
	override fun toLogString(): String = buildString {
		append("[Combat ")
		append(partyA.players.joinToString { it.id })
		append(" vs ")
		append(partyB.players.joinToString { it.id })
		append("]")
	}

	var ongoing = false
	val participants = partyA.players + partyB.players
	val turnQueue = ArrayList<Player>()
	var roundNumber = 0
	var currentPlayer: Player = partyA.players.first()
	var victor: Party? = null

	val display = object:TextOutput {
		override fun selectSelf() {
			participants.forEach { it.display.selectSelf() }
		}

		override fun selectPerson(person: Player) {
			participants.forEach { it.display.selectPerson(person) }
		}

		override fun clearOutput() {
			participants.forEach { it.display.clearOutput() }
		}

		override fun rawOutput(text: String) {
			participants.forEach { it.display.rawOutput(text) }
		}

		override fun outputText(text: String) {
			participants.forEach { it.display.outputText(text) }
		}
	}

	class Party(val players: List<Player>) {
		constructor(player: Player) : this(listOf(player))
		init {
			require(players.isNotEmpty()) { "Combat.Party cannot be empty" }
		}

		operator fun contains(player: Player) = player in players
		fun isAlive() =
			players.any { it.char.isAlive && it.isConnected }
	}

	fun opponentsOf(player: Player): Party? = when (player.party) {
		partyA -> partyB
		partyB -> partyA
		else -> null
	}

	fun start() {
		logger.info(this, "start()")
		for (player in partyA.players) {
			player.party = partyA
		}
		for (player in partyB.players) {
			player.party = partyB
		}
		ongoing = true
		for (player in participants) {
			player.combat = this
			Game.server?.sendChatNotification(player, "Combat started")
			player.char.clearCombatStatuses()
			player.display.clearOutput()
		}
		nextRound()
		for (player in participants) {
			buildCombatActions(player)
			player.sendFullCombatStatus()
		}
	}

	fun buildCombatActions(player: Player) {
		val actions = ArrayList<AbstractCombatAction>()

		if (player.combat == this) {
			if (ongoing) {
				actions.add(CombatWait(player))
				for (target in opponentsOf(player)?.players ?: emptyList()) {
					actions.add(CombatMeleeAttack(player, target))
				}
				actions.add(CombatSurrender(player))
			} else {
				actions.add(CombatFinish(player))
			}
		}

		player.combatActions = actions
	}

	fun performCombatAction(action: AbstractCombatAction) {
		logger.debug(this, "performCombatAction", action)
		action.perform()
		if (ongoing) {
			if (checkEnd()) {
				return
			}
			nextPlayer()
			// TODO partial update
			for (player in participants) {
				player.display.outputText("<hr>")
			}
			sendUpdateToAll()
		}
	}

	fun sendUpdateToAll() {
		for (player in participants) {
			player.sendFullCombatStatus()
		}
	}

	private fun nextPlayer() {
		if (checkEnd()) {
			return
		}
		while (true) {
			val player = turnQueue.removeFirstOrNull()
			if (player == null) {
				nextRound()
				return
			}
			if (player.char.canAct) {
				currentPlayer = player
				logger.debug(this, "nextPlayer()",player.id)
				return
			}
		}
	}

	private fun nextRound() {
		roundNumber++
		logger.debug(this, "nextRound()",roundNumber)
		// TODO things-on-new-round
		if (checkEnd()) {
			return
		}
		turnQueue.addAll(participants)
		nextPlayer() // TODO protection from endless loop
	}

	fun end() {
		logger.info(this, "end()")
		ongoing = false
		for (player in participants) {
			if (player.isConnected) {
				if (player in victor!!) {
					player.display.outputText("<hr>You won!")
				} else {
					player.display.outputText("<hr>You lost!")
				}
				buildCombatActions(player)
				Game.server?.sendChatNotification(player, "Combat ended")
				player.sendFullCombatStatus()
			}
		}
	}

	fun checkEnd(): Boolean {
		if (!partyA.isAlive()) {
			victor = partyB
			end()
			return true
		}
		if (!partyB.isAlive()) {
			victor = partyA
			end()
			return true
		}
		return false
	}

	fun playerLeft(player: Player) {
		checkEnd()
	}

	companion object {
		private val logger = LogManager.getLogger("xta.game.combat.Combat")

		fun oneOnOne(playerA: Player, playerB: Player, returnScene: Scene) =
			Combat(Party(playerA), Party(playerB), returnScene)
	}
}
