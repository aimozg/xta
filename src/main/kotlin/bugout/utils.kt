package bugout

/*
 * Created by aimozg on 29.11.2021.
 */

/**
 * Fires whenever we make a connection with another Bugout instance. `address` is the remote instance's address.
 */
fun Bugout.onSeen(callback: (address: String) -> Unit) {
	on("seen", callback)
}

/**
 * Fires when a connection is made to a Bugout instance who's address specifically matches the `identifier` passed into the `Bugout(identifier)` instantiation. In other words, when a connection is made to some Bugout server.
 */
fun Bugout.onServer(callback: (address: String) -> Unit) {
	on("server", callback)
}

/**
 * Fires when the number of connections (wires) into the network changes. Note that the number of connections may be different from the number of peers we see as some peers will be connected to indirectly through other peers.
 */
fun Bugout.onConnections(callback: (count: Int) -> Unit) {
	on("connections", callback)
}

/**
 * Fires when a generic message is recieved by a remote Bugout instance from a peer node.
 */
fun Bugout.onMessage(callback: (address: String, message: dynamic, packet: dynamic) -> Unit) {
	on("message", callback)
}

/**
 * Fires when a remote Bugout instance send a ping message.
 */
fun Bugout.onPing(callback: (address: String) -> Unit) {
	on("ping", callback)
}

/**
 * Fires when a remote Bugout instance leaves the server identifier/room.
 */
fun Bugout.onLeft(callback: (address: String) -> Unit) {
	on("left", callback)
}

/**
 * Fires when a remote Bugout instance times out (requires `.heartbeat()` to be running).
 */
fun Bugout.onTimeout(callback: (address: String) -> Unit) {
	on("timeout", callback)
}

/**
 * Fires when an RPC response is sent out to a caller.
 */
fun Bugout.onRpc(callback: (address: String, call: String, args: dynamic, nonce: dynamic) -> Unit) {
	on("rpc", callback)
}

/**
 * Fires when a remote Bugout instance send a ping message.
 */
fun Bugout.onRpcResponse(callback: (address: String, nonce: dynamic, response: dynamic) -> Unit) {
	on("rpc-response", callback)
}
