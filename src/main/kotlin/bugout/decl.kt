package bugout

/*
 * Created by aimozg on 29.11.2021.
 */

external class Bugout(identifier: String?, options: BugoutOptionsJson = definedExternally) {
	constructor(options: BugoutOptionsJson)

	/**
	 * Get this Bugout instance's address. Other Bugout instances can connect to this instance by using it's address as the identifier during instantiation.
	 */
	fun address():String

	/**
	 * Register an RPC call which remote Bugout instances can call on this instance using the .rpc() method below.
	 */
	fun register(callname:String, func:(dynamic)->dynamic, docstring:String? = definedExternally)

	/**
	 * Make an RPC call on a remote Bugout instance. If address is omitted then the identifier address (server) is assumed. arguments can be any JSON representable data structure.
	 */
	fun rpc(address:String, callname:String, args:dynamic, callback:(dynamic)->Unit)

	/**
	 * Send a generic JSON message to a particular bugout address.
	 */
	fun send(address:String, message:dynamic)
	/**
	 * Send a generic JSON message to a particular bugout address. If only one argument is passed it is assumed to be the message and the address is assumed to be the channel identifier (server address).
	 */
	fun send(message:dynamic)

	/**
	 * For applications which require an up-to-date list of connected peers, calling this function causes a periodic heartbeat to be sent out meaning the list is kept up to date. The default value for interval is 30 seconds.
	 */
	fun heartbeat(interval:Int)

	/**
	 * Cleans up dangling references and timers and calls callback when done. .close() is an alias for this method.
	 */
	fun destroy(callback:()->Unit)

	fun close(callback:()->Unit)

	/**
	 * Listen out for an event called eventname. Arguments to the callback will depend on the type of event. See below for a list of events.
	 */
	fun on(eventname:String,callback:Function<Unit>)

	/**
	 * As per .on() but stops listening after the event has fired once.
	 */
	fun cen(eventname:String,callback:Function<Unit>)
}

external interface BugoutOptionsJson {
	/**
	 * a WebTorrent instance to re-use. Pass this in if you're making connections to multiple Bugout channels.
	 */
	var wt: dynamic

	/**
	 * options that will be passed when creating the WebTorrent object.
	 */
	var wtOpts: dynamic

	/**
	 * a torrent to extend with Bugout RPC / gossip extension.
	 * If provided a new torrent will not be created.
	 */
	var torrent: WebTorrentTorrentOptionsJson

	/**
	 * options that will be passed to the WebTorrent seed method.
	 */
	var torrentOpts: dynamic

	/**
	 * base58 encoded seed used to generate an nacl signing key pair.
	 */
	var seed: String?

	/**
	 * pass nacl signing key pair directly rather than a seed.
	 */
	var keyPair: dynamic

	/**
	 * start a network heartbeat to update peer list at an interval specified in milliseconds.
	 * See b.heartbeat() docs below.
	 */
	var hearbeat: Int?
	/**
	 * iceServers - pass in custom STUN / TURN servers e.g.: iceServers: [{urls: "stun:server.com:111"} ... ].
	 * Shortcut for passing {rtcConfig: {iceServers: [...]}} to wtOpts.
	 */
	var iceServers: Array<RTCIceServer>?
	/**
	 * use custom announce trackers to introduce peers e.g. ["wss://tracker...", ...].
	 * Only peers using the same trackers will find eachother.
	 * Shortcut for passing {announce: [...]} to torrentOpts.
	 */
	var announce: Array<String>?
}

external interface RTCIceServer {
	var urls: Array<String>
}

external interface WebTorrentTorrentOptionsJson {
	var private: Boolean?
}
