package xta.net.protocol

import xta.Player
import xta.logging.LogContext

abstract class GuestProtocol: LogContext {
	abstract fun onMessage(message: MessageToGuest)

	abstract val player: Player
	abstract val identity: String
	abstract val isConnected: Boolean

}
