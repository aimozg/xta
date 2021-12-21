package xta.net

import xta.Game
import xta.ScreenManager
import xta.logging.LogManager
import xta.net.protocol.HostProtocol
import xta.net.protocol.LocalGuestProtocol
import xta.net.protocol.LocalHostProtocol
import xta.net.protocol.RemoteHostProtocol
import xta.net.transport.AbstractGuestConnection
import xta.net.transport.AbstractHostConnection
import xta.net.transport.wslobby.WsLobbyGameGuestConnection
import xta.net.transport.wslobby.WsLobbyGameHostConnection
import kotlin.js.Promise

/*
 * Created by aimozg on 30.11.2021.
 */

fun setupHost(host: AbstractHostConnection): Promise<HostProtocol> {
	val server = GameServer()
	Game.server = server
	return LocalHostProtocol(host, Game.me, server).register().then {
		server.startGame()
		it
	}
}

fun setupWsLobbyHost(
	url: String,
	identity: String,
	token: String,
	roomId: String
): Promise<HostProtocol> {
	Game.localMessage("Connecting to lobby...")
	return setupHost(WsLobbyGameHostConnection(url, identity, token, roomId))
}

fun setupGuest(connection: AbstractGuestConnection, invite: String): Promise<AbstractGuestConnection> {
	val hostProtocol = RemoteHostProtocol(Game.me, connection)
	Game.hostProtocol = hostProtocol
	val guestProtocol = LocalGuestProtocol(Game.me, connection)
	Game.me.guest = guestProtocol
	Game.me.isHost = false
	connection.onDisconnect { _, reason ->
		if (Game.hostProtocol === hostProtocol) {
			Game.localErrorMessage("Disconnected: $reason")
			logger.warn(connection, "Game closed", reason)
			ScreenManager.showStartMenu()
		}
	}
	connection.onConnect {
		ScreenManager.showGameScreen()
		Game.localMessage("Connected!")
		Game.started()
	}
	connection.onMessage { _, message ->
		if (Game.me.guest == guestProtocol) {
			guestProtocol.onRawMessage(message)
		}
	}
	return connection.connect(invite).catch { error ->
		Game.localErrorMessage("Failed to connect: ${error.message}")
		logger.warn(connection,"Failed to connect: ${error.message}")
		throw error
	}
}

fun setupWsLobbyGuest(
	url: String,
	identity: String,
	token: String,
	invite: String
): Promise<WsLobbyGameGuestConnection> {
	Game.localMessage("Connecting to lobby...")
	val character = Game.myCharacter
	// TODO configurable display name
	val greeting = character.name + ", level " + character.level + " " + character.raceFullName()
	@Suppress("UNCHECKED_CAST")
	return setupGuest(
		WsLobbyGameGuestConnection(
			url, identity, token, greeting
		),
		invite
	) as Promise<WsLobbyGameGuestConnection>
}

private val logger by lazy {
	LogManager.getLogger("xta.net.protocol.setup")
}
