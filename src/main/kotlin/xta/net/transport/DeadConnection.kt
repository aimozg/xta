package xta.net.transport

import org.khronos.webgl.Uint8Array
import xta.utils.randomString
import kotlin.random.Random

class DeadConnection : AbstractConnection() {
	override fun send(message: Uint8Array) {
		error("Cannot use DeadConnection")
	}

	override fun logContextLabel() = "[Disconnected]"

	override val isConnected: Boolean
		get() = false
	override val identity: String = "DeadConnection#${Random.Default.randomString(16)}"
	override val displayName: String
		get() = "DeadConnection"

	override fun close(reason: String) { /*noop*/}

}
