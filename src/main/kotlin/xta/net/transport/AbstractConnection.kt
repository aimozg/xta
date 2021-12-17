package xta.net.transport

import org.khronos.webgl.Uint8Array
import xta.logging.LogContext
import xta.logging.LogManager
import xta.net.protocol.MessageToGuest
import xta.net.protocol.MessageToHost
import xta.utils.EventEmitter
import xta.utils.encodeToArray
import xta.utils.stringify
import kotlin.js.Json

abstract class AbstractConnection : EventEmitter(), LogContext {

	override fun logContextLabel() = "[$identity]"

	abstract fun send(message: Uint8Array)
	fun send(message: String) {
		logger.ifdebug(this) { "> $message" }
		send(message.encodeToArray())
	}
	fun send(message: Json) {
		send(message.stringify())
	}
	fun send(message: MessageToGuest) {
		send(message.stringify())
	}
	fun send(message: MessageToHost) {
		send(message.stringify())
	}
	fun sendJson(message: Any) {
		send(message.stringify())
	}

	abstract val isConnected: Boolean

	abstract val identity: String

	abstract val displayName: String

	abstract fun close(reason:String)

	fun onMessage(callback: (connection: AbstractConnection, message: Uint8Array) -> Unit) {
		on("message", callback)
	}

	fun emitMessageEvent(message: Uint8Array) {
		emit("message", this, message)
	}

	fun onDisconnect(callback: (connection: AbstractConnection, reason: String) -> Unit) {
		on("disconnect", callback)
	}

	fun emitDisconnectEvent(reason: String) {
		emit("disconnect", this, reason)
	}

	fun onConnect(callback: (connection: AbstractHostConnection)->Unit) {
		on("connect", callback)
	}
	fun emitConnectEvent() {
		emit("connect", this)
	}
	init {
		registerEventTypes("message","connect","disconnect")
	}

	companion object {
		private val logger = LogManager.getLogger("xta.net.transport.AbstractConnection")
	}
}

