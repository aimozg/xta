package xta.net.transport.webrtc

/*
 * Created by aimozg on 29.11.2021.
 */
/*
class WebRtcServer(
	val seed: String
): AbstractServer() {
	lateinit var bugout: Bugout

	override var isActive: Boolean = false; private set
	private var registering = false

	override fun inviteCode(): String = "wbr-$seed"

	override fun register() {
		if (registering) error("Duplicate register() call for server '$seed'")
		registering = true
		bugout = Bugout(options = jsObject {
			seed = this@WebRtcServer.seed
		})
		bugout.onConnections {
			if (it == 0 && !isActive) {
				isActive = true
				emitReady()
			}
		}
	}

	override fun close() {
		TODO("close")
	}

	override val address: String
		get() = bugout.address()

}
*/
