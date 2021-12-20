package xta.game.combat

import xta.Game
import xta.Player
import xta.game.PlayerCharacter
import xta.game.Scene
import xta.game.combat.actions.*
import xta.logging.LogContext
import xta.logging.LogManager
import xta.text.TextOutput
import xta.utils.nextUid
import xta.utils.walk

/*
 * Created by aimozg on 04.12.2021.
 */
class Combat(
	val id: String,
	val partyA: Party,
	val partyB: Party,
	val returnScene: Scene
): LogContext {
	override fun logContextLabel(): String = buildString {
		append("[Combat ")
		append(partyA.players.joinToString { it.id })
		append(" vs ")
		append(partyB.players.joinToString { it.id })
		append("]")
	}

	override fun toString() = logContextLabel()

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

		override fun selectNpc(index: Int, npc: PlayerCharacter) {
			participants.forEach { it.display.selectNpc(index, npc) }
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
		participants.map { it.location }.toSet().forEach { it.onCombatStatusChange() }
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
					for (ability in CombatAbility.ALL) {
						if (ability.isKnownBy(player.char)) {
							for (action in ability.createActions(player, this)) {
								if (action.isKnown()) {
									actions.add(action)
								}
							}
						}
					}
				}
				actions.add(CombatSurrender(player))
			} else {
				actions.add(CombatFinish(player))
			}
		} else {
			logger.error(this, "buildCombatActions was called for $player (${player.combat}) inside $this")
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

	private fun roundStart() {
		for (player in participants) {
			if (!player.char.isAlive) continue
			// TODO other things-on-new-round
			player.char.statusEffects.walk { iterator, value ->
				// TODO delegate to effect itself
				value.durationRounds--
				if (value.durationRounds <= 0) {
					iterator.remove()
				}
			}
		}
	}

	private fun nextRound() {
		roundNumber++
		logger.debug(this, "nextRound()",roundNumber)
		if (checkEnd()) return
		roundStart()
		if (checkEnd()) return
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
			Combat(nextUid().toString(), Party(playerA), Party(playerB), returnScene)
	}
}
