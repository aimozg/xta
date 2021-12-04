package xta.net.transport

import kotlin.js.Promise

abstract class AbstractGuestConnection: AbstractConnection() {
	abstract fun connect(invite:String): Promise<AbstractGuestConnection>


}
