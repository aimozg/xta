package xta.net

import org.khronos.webgl.Uint8Array
import xta.Game
import xta.Player
import xta.game.PlayerCharacter
import xta.game.Scene
import xta.game.combat.Combat
import xta.game.scenes.Limbo
import xta.game.scenes.TownLocation
import xta.logging.LogContext
import xta.logging.LogManager
import xta.net.protocol.MessageToGuest
import xta.net.protocol.MessageToHost
import xta.net.protocol.RemoteGuestProtocol
import xta.net.protocol.guest.*
import xta.net.transport.AbstractConnection
import xta.utils.*

/**
 * ```
 * Host Player Alice                     ref to remote Bob         Guest Player Bob
 * +--------------+  +-----------------+  +---------------+        +--------------+
 * | LocalGuest   |<-|   GameServer    |->| RemoteGuest   |-- … -->| LocalGuest   |
 * +--------------+  |                 |  +---------------+        +--------------+
 * +--------------+  |                 |                           +--------------+
 * | LocalHost    |->|                 |<-------------------- … <--| RemoteHost   |
 * +--------------+  +-----------------+                           +--------------+
 * ```
 * Elements:
 * * **(Local/Remote)GuestProtocol** notifies player about messages from host
 * * **(Local/Remote)HostProtocol** dispatches player actions
 * * **GameServer** is where game logic works
 *
 * Implementations:
 * * **LocalGuestProtocol** displays messages for current player in browser
 * * **RemoteGuestProtocol** is a reference to remote player and sends messages over the net
 * * **LocalHostProtocol** handles player actions locally
 * * **RemoteHostProtocol** sends player actions over the net
 *
 * Example 1.
 * 1. Alice sends chat message: `Game.host.sendChatMessage()`
 * 2. Alice's host is `LocalHostProtocol`, it invokes `server.handleIncomingMessage()`
 * 3. `server.handleChatMessage()` invokes every recipient's `guest.onMessage()`
 * 4. Alice's guest protocol is `LocalGuestProtocol`, it displays the message in their browser
 * 5. Reference to remote Bob has `RemoteGuestProtocol`, its `onMessage()` transmits the message over the net
 * 6. Bob's network stack receives message and invokes `LocalGuestProtocol.onMessage()`
 * 7. Bob's `LocalGuestProtocol.onChatMessage()` displays in their browser
 *
 * Example 2.
 * 1. Bob sends chat message: `Game.host.sendChatMessage()`
 * 2. Bob's host is `RemoteHostProtocol`, it transmits the message over the net
 * 3. Alice's network stack receives message and invokes `server.handleIncomingMessage()`
 * 4. See 3-7 above
 */
class GameServer(): LogContext {
	override fun toLogString() = "[GameServer]"

	val players = arrayListOf(Game.me)

	fun player(id:String):Player? {
		return players.find { it.id == id }
	}
	fun newPlayerId(guest: AbstractConnection):String {
		return guest.identity
		/*
		while (true) {
			val id = Random.Default.randomString(8)
			if (player(id) == null) return id
		}
		 */
	}
	fun hostGame() {
		placePlayer(Game.me)
	}

	fun playerJoined(player:Player) {
		players.add(player)
	}
	fun playerLeft(player: Player, reason: String) {
		player.scene.onLeave(player)
		player.scene = Limbo.scene
		player.location = Limbo
		players.remove(player)
		broadcastChatMessage("${player.chatName} left the game ($reason)")
		player.combat?.playerLeft(player)
	}
	fun handleRawMessage(sender: AbstractConnection, message: Uint8Array) {
		val player = players.find {
			(it.guest as? RemoteGuestProtocol)?.connection == sender
		}
		if (player == null) {
			sender.close("Orphan")
			return
		}
		try {
			@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
			val json = message.decodeToJson() as MessageToHost
			handleIncomingMessage(player, json)
		} catch (e:Throwable) {
			logger.error(sender, "Malformed message from $sender",e)
		}
	}

	fun handleIncomingMessage(sender: Player, message: MessageToHost) {
		logger.ifdebug(this) { "< " + message.stringify() }
		message.chat?.let {
			handleChatMessage(sender, it)
			return
		}
		message.offerChar?.let {
			handleOfferCharMessage(sender, it)
			return
		}
		message.statusRequest?.let {
			handleStatusRequestMessage(sender, it)
			return
		}
		message.sceneAction?.let {
			handleSceneActionMessage(sender, it)
			return
		}
		message.combatAction?.let {
			handleCombatActionMessage(sender, it)
			return
		}
		logger.error(this, "Received bad message "+message.stringify())
	}

	private fun handleOfferCharMessage(sender: Player, request: OfferCharMessage) {
		// TODO validate
		val oldChar = if (sender.charLoaded) sender.char.chatName else null
		sender.char = PlayerCharacter().apply { deserializeFromJson(request.char) }
		sender.guest.onMessage(jsobject { msg ->
			msg.charAccepted = jsobject {
				it.yourId = sender.id
			}
		})
		if (sender.charLoaded) {
			// local host
			placePlayer(sender)
		}
		if (oldChar == null) {
			broadcastChatMessage("${sender.chatName} joined the game")
		} else {
			broadcastChatMessage("$oldChar is now ${sender.chatName}")
		}
	}

	private fun handleStatusRequestMessage(sender: Player, request: StatusRequestMessage) {
		sender.guest.onMessage(jsobject { msg ->
			msg.statusUpdate = jsobject {
				if (request.char == true) it.char = sender.char.serializeToJson()
				if (request.screen == true) it.screen = sender.screen
			}
		})
	}

	private fun handleChatMessage(sender: Player, request: SendChatMessage) {
		for (player in players) {
			player.guest.onMessage(jsobject { msg ->
				msg.chat = jsobject {
					it.content = request.content
					it.senderName = sender.chatName
					it.senderStyle = when {
						player === sender -> "-me"
						sender.isHost -> "-host"
						else -> ""
					}
				}
			})
		}
	}

	private fun handleSceneActionMessage(player: Player, request:SceneActionMessage) {
		val sceneId = request.sceneId
		val actionId = request.actionId
		if (player.screen.sceneId == sceneId) {
			val callback = player.display.callbacks[actionId]
			if (callback != null) {
				logger.info(player,"Executing action $sceneId/$actionId")
				callback()
				return
			} else {
				logger.warn(player, "Inappropriate action $sceneId/$actionId requested (not in the list)")
			}
		} else {
			logger.warn(player, "Inappropriate action $sceneId/$actionId requested (player is in ${player.screen.sceneId})")
		}
		sendScreen(player)
	}

	private fun handleCombatActionMessage(player: Player, request: CombatActionMessage) {
		val id = request.actionId
		val action = player.combatActions.find { it.uid == request.actionId }
		val combat = player.combat
		if (!player.inCombat || combat == null || action == null || combat.ongoing && combat.currentPlayer != player) {
			logger.warn(player, "Inappropriate combat action $id (currentPlayer is ${combat?.currentPlayer})")
			sendCombatStatus(player, true)
			return
		}
		combat.performCombatAction(action)
	}

	private fun placePlayer(player: Player) {
		player.char.updateStats()
		TownLocation.main.execute(player)
	}

	fun broadcastChatMessage(content:String, contentStyle:String?=null, senderName:String? = "[Server]", senderStyle:String? = "-server") {
		val msg = jsobject<MessageToGuest> { msg ->
			msg.chat = jsobject {
				it.content = content
				it.contentStyle = contentStyle
				it.senderName = senderName
				it.senderStyle = senderStyle
			}
		}
		for (player in players) {
			player.guest.onMessage(msg)
		}
	}
	fun sendChatMessage(receiver:Player, content: String, contentStyle:String?=null, senderName: String? = "[Server]", senderStyle: String? = "-server") {
		receiver.guest.onMessage(jsobject { msg ->
			msg.chat = jsobject {
				it.content = content
				it.contentStyle = contentStyle
				it.senderName = senderName
				it.senderStyle = senderStyle
			}
		})
	}
	fun sendChatNotification(receiver:Player, content: String, contentStyle:String?="-info") {
		sendChatMessage(receiver, content,contentStyle, senderName = null, senderStyle = null)
	}

	fun playScene(player: Player) {
		player.scene.execute(player)
	}
	fun sendStatusUpdate(player: Player, screen:Boolean=false, char:Boolean=false) {
		player.guest.onMessage(jsobject { msg ->
			msg.statusUpdate = jsobject {
				if (screen) it.screen = player.screen
				if (char) it.char = player.char.serializeToJson()
			}
		})
	}
	fun sendScreen(player: Player) {
		sendStatusUpdate(player, screen = true)
	}
	fun sendCombatStatus(
		player: Player,
		chars: Boolean
	) {
		player.guest.onMessage(jsobject { msg ->
			msg.combatUpdate = jsobject { cum -> /* yes, and? */
				cum.inCombat = player.inCombat
				val combat = player.combat
				cum.ongoing = combat?.ongoing?:false
				if (combat != null) {
					cum.partyA = combat.partyA.players.mapToArray { it.id }
					cum.partyB = combat.partyB.players.mapToArray { it.id }
					if (chars) {
						cum.playerData = buildJson { pd ->
							for (c in combat.participants) {
								pd[c.id] = c.char.serializeToJson()
							}
						}
					}
					cum.myActions = player.combatActions.mapToJsonArray { action, json ->
						json.actionId = action.uid
						json.label = action.label
						json.hint = action.tooltip
						if (!action.enabled) json.disabled = true
					}
					cum.actingPlayerId = combat.currentPlayer.id
					cum.myContent = player.screen.content
				}
			}
		})
	}
	fun startCombat(playerA:Player, playerB:Player, returnScene: Scene) {
		if (playerA.inCombat) error("Cannot startCombat: $playerA is in combat")
		if (playerB.inCombat) error("Cannot startCombat: $playerB is in combat")
		Combat.oneOnOne(playerA,playerB, returnScene).start()
	}

	companion object {
		private val logger = LogManager.getLogger("xta.net.GameServer")
	}
}
