package xta.net.transport.wslobby

import xta.logging.LogContext
import xta.logging.LogManager
import xta.utils.EventEmitter
import xta.utils.jsObject
import kotlinx.browser.window
import org.khronos.webgl.Uint8Array
import org.w3c.dom.CloseEvent
import org.w3c.dom.WebSocket
import org.w3c.dom.events.Event
import org.w3c.files.Blob
import org.w3c.files.arrayBuffer
import wslobby.*
import kotlin.random.Random

/*
 * Created by aimozg on 30.11.2021.
 */
abstract class WsLobbyClient(
	val url: String,
	val identity: String,
	val token: String,
	val role: wslobby.Role
) : EventEmitter(), LogContext {
	override fun toLogString() = "[$identity]"

	enum class State {
		CONNECTING,
		UNIDENTIFIED,
		AUTHORIZED,
		INROOM,
		CLOSED
	}

	val socket: WebSocket = WebSocket(url, WS_PROTOCOL)
	val isSocketConnected
		get() = socket.readyState == WebSocket.OPEN
	var state: State = State.CONNECTING
		private set(value) {
			field = value
			emit(EVENT_STATE, value)
		}
	protected var mid = Random.Default.nextInt(1 shl 29)
	protected var authmid = mid++
	protected var pingId = 0

	init {
		registerEventTypes(*ALL_EVENT_NAMES)
		socket.onmessage = {
			socketMessage(it.data)
		}
		socket.onclose = {
			socketClose((it as CloseEvent).reason)
		}
		socket.onerror = {
			socketError(it)
		}
		socket.onopen = {
			socketOpen()
		}
	}

	open fun handleMessage(msg: wslobby.Reply) {
		logger.trace(this, "<",msg.toJSON())
		when (msg.payload) {
			"pong" -> {
				handlePong(msg)
			}
			"ack" -> {
				handleAck(msg)
			}
			"error" -> {
				handleError(msg)
			}
			"guestKnock" -> {
				handleGuestKnock(msg)
			}
			"guestLeft" -> {
				handleGuestLeft(msg)
			}
			"enteredRoom" -> {
				handleEnteredRoom(msg)
			}
			"data" -> {
				handleData(msg)
			}
		}
	}

	protected open fun handlePong(msg: wslobby.Reply) {}
	protected open fun handleAck(msg: wslobby.Reply) {
		if (msg.id == authmid) {
			state = State.AUTHORIZED
			afterAuthorized()
		}
	}

	protected open fun handleError(msg: wslobby.Reply) {}
	protected open fun handleGuestKnock(msg: wslobby.Reply) {}
	protected open fun handleGuestLeft(msg: wslobby.Reply) {}
	protected open fun handleEnteredRoom(msg: wslobby.Reply) {
		state = State.INROOM
		emit(EVENT_INROOM)
	}
	protected open fun handleData(msg: wslobby.Reply) {
		emit(EVENT_DATA, msg.data!!.senderId!!, msg.data!!.data!!)
	}

	protected abstract fun afterAuthorized()

	protected fun send(message: wslobby.ICommand) {
		socket.send(wslobby.Command.compose(message))
	}
	protected inline fun send(messageBuilder:wslobby.ICommand.()->Unit) {
		socket.send(wslobby.Command.compose(jsObject(messageBuilder)))
	}
	fun sendPing() {
		send {
			id = mid++
			ping = jsObject {  }
		}
	}
	fun sendPeriodicPing(intervalSeconds:Int) {
		require(intervalSeconds>0) { "intervalSeconds=$intervalSeconds must be > 0" }
		if (pingId != 0) window.clearInterval(pingId)
		pingId = window.setInterval({
			if (socket.readyState == WebSocket.OPEN) {
				sendPing()
			} else {
				if (pingId != 0) window.clearInterval(pingId)
				pingId = 0
			}
		}, intervalSeconds*1000)
	}

	protected fun socketMessage(data: Any?) {
		if (data !is Blob) {
			logger.warn(this, "Expected Blob, got ", data)
			return
		}
		data.arrayBuffer().then { ab ->
			val msg = wslobby.Reply.decode(Uint8Array(ab))
			handleMessage(msg)
		}
	}

	protected open fun socketOpen() {
		state = State.UNIDENTIFIED
		emit(EVENT_OPEN)
		send {
			id = authmid
			identify = jsObject {
				identity = this@WsLobbyClient.identity
				token = this@WsLobbyClient.token
				role = this@WsLobbyClient.role
			}
		}
	}

	protected fun socketClose(reason:String) {
		if (state != State.CLOSED) {
			state = State.CLOSED
			emit(EVENT_CLOSE, reason)
		}
	}

	protected fun socketError(event: Event) {
		logger.warn(this, event)
		socketClose(event.toString())
	}

	fun close(reason:String) {
		if (isSocketConnected) {
			send {
				id = mid++
				leave = jsObject {
					goodbye = reason
				}
			}
		}
		socket.close(reason=reason)
	}

	fun onStateChange(callback:(State)->Unit) {
		on(EVENT_STATE,callback)
	}
	fun onOpen(callback:()->Unit) {
		on(EVENT_OPEN,callback)
	}
	fun onClose(callback: (reason:String) -> Unit) {
		on(EVENT_CLOSE,callback)
	}
	fun onRoomEntry(callback: () -> Unit) {
		on(EVENT_INROOM, callback)
	}
	fun onData(callback:(sender:String, data:Uint8Array)->Unit) {
		on(EVENT_DATA, callback)
	}

	companion object {
		const val WS_PROTOCOL = "wslobby1"
		const val EVENT_STATE = "state"
		const val EVENT_OPEN = "open"
		const val EVENT_CLOSE = "close"
		const val EVENT_INROOM = "inroom"
		const val EVENT_DATA = "data"
		private val ALL_EVENT_NAMES = arrayOf(EVENT_STATE, EVENT_OPEN, EVENT_CLOSE, EVENT_INROOM, EVENT_DATA)

		private val logger by lazy {
			LogManager.getLogger("xta.net.transport.wslobby.WsLobbyClient")
		}
	}
}

