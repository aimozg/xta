package xta.net.protocol

import xta.Player
import xta.net.transport.AbstractConnection

/**
 * Other player playing on this host
 */
class RemoteGuestProtocol(
	override val player: Player,
	val connection: AbstractConnection
): GuestProtocol() {
	override fun toLogString() = connection.toLogString()
	override val isConnected: Boolean
		get() = connection.isConnected
	override val identity: String
		get() = connection.identity

	override fun onMessage(message: MessageToGuest) {
		connection.send(message)
	}
}

