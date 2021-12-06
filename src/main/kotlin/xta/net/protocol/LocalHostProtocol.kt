package xta.net.protocol

import xta.Game
import xta.Player
import xta.ScreenManager
import xta.logging.LogContext
import xta.logging.LogManager
import xta.net.GameServer
import xta.net.transport.AbstractConnection
import xta.net.transport.AbstractHostConnection
import kotlin.js.Promise

class LocalHostProtocol(
	val connection: AbstractHostConnection,
	override val player: Player,
	val server: GameServer
): HostProtocol(), LogContext by server {

	init {
		connection.onReady {
			player.isHost = true
			player.guest = LocalGuestProtocol(player, null)
			Game.hostProtocol = this
			logger.debug(this, "Game hosted")
			Game.localMessage("Connected! Use invite code ${connection.inviteCode()}")
			ScreenManager.showGameScreen()
			Game.started()
		}
		connection.onClose { reason ->
			if (Game.hostProtocol == this) {
				Game.localErrorMessage("Disconnected: $reason")
				logger.warn(this, "Game closed", reason)
				ScreenManager.showStartMenu()
			}
		}
		connection.onGuest { guest ->
			logger.info(guest, "Guest ", guest.identity, "joined")
			Game.localMessage("Guest connected: ${guest.identity}","-server")
			setupRemoteGuest(guest)
		}
		connection.onGuestLeave { guest, reason ->
			Game.localMessage("Guest ${guest.identity} left ($reason)","-server")
			logger.info(guest, "Guest ", guest.identity, "left", reason)
		}
	}

	override val isConnected: Boolean
		get() = connection.isActive


	private fun setupRemoteGuest(guest: AbstractConnection) {
		logger.info(guest, "Incoming guest ",guest.identity, guest.displayName)
		// TODO reconnecting
		val player = Player(false)
		player.guest = RemoteGuestProtocol(player, guest)
		server.playerJoined(player)
		guest.onDisconnect { _, reason ->
			server.playerLeft(player, reason)
		}
		guest.onMessage { connection, message ->
			server.handleRawMessage(connection,message)
		}
	}

	override fun sendMessage(message: MessageToHost) {
		server.handleIncomingMessage(player, message)
	}

	fun register(): Promise<HostProtocol> {
		return connection.register().then { this }
	}

	companion object {
		private val logger = LogManager.getLogger("xta.net.protocol.HostProtocol")
	}
}
