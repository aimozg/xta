package xta.net.protocol

import xta.Player
import xta.net.transport.AbstractConnection

/**
 * This player playing on remote
 */
class RemoteHostProtocol(
	override val player: Player,
	val connection: AbstractConnection
): HostProtocol() {
	override fun toLogString() = connection.toLogString()
	override val isConnected: Boolean
		get() = connection.isConnected

	override fun sendMessage(message: MessageToHost) {
		connection.send(message)
	}
}
