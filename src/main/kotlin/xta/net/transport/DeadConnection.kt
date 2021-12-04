package xta.net.transport

import xta.utils.randomString
import org.khronos.webgl.Uint8Array
import kotlin.random.Random

class DeadConnection : AbstractConnection() {
	override fun send(message: Uint8Array) {
		error("Cannot use DeadConnection")
	}

	override fun toLogString() = "[Disconnected]"

	override val isConnected: Boolean
		get() = false
	override val identity: String = "DeadConnection#${Random.Default.randomString(16)}"
	override val displayName: String
		get() = "DeadConnection"

	override fun close(reason: String) { /*noop*/}

}
