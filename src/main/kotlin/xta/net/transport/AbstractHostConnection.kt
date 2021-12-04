package xta.net.transport

import xta.logging.LogContext
import xta.utils.EventEmitter
import kotlin.js.Promise

/*
 * Created by aimozg on 29.11.2021.
 */
abstract class AbstractHostConnection : EventEmitter(), LogContext {
	override fun toLogString() = "[$address]"

	abstract val guestList: Collection<AbstractConnection>

	abstract val isActive: Boolean

	abstract fun inviteCode(): String

	abstract fun register(): Promise<AbstractHostConnection>

	abstract fun close(reason:String)

	abstract val address: String

	protected fun emitReady() {
		emit("ready", this)
	}

	fun onReady(callback: (server: AbstractHostConnection) -> Unit) {
		on("ready", callback)
	}

	protected fun emitGuest(guest: AbstractConnection) {
		emit("guest", guest)
	}

	fun onGuest(callback: (guest: AbstractConnection) -> Unit) {
		on("guest", callback)
	}

	protected fun emitGuestLeave(guest: AbstractConnection, reason: String) {
		emit("guest-leave", guest, reason)
	}

	fun onGuestLeave(callback: (guest: AbstractConnection, reason:String) -> Unit) {
		on("guest-leave", callback)
	}

	protected fun emitClose(reason: String) {
		emit("close", reason)
	}

	fun onClose(callback: (reason: String) -> Unit) {
		on("close", callback)
	}

	init {
		registerEventTypes("ready", "guest", "guest-leave", "close")
	}
}

