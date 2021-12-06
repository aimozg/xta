package xta

import xta.net.GameServer
import xta.net.protocol.HostProtocol
import xta.net.protocol.RemoteHostProtocol
import xta.net.transport.DeadConnection
import xta.utils.jsobject

/*
 * Created by aimozg on 28.11.2021.
 */

/**
 * Globals
 */
@JsExport
object Game {
	val me = Player(isMe = true)

	val myCharacter get() = me.char

	var server: GameServer? = null
	var hostProtocol: HostProtocol = RemoteHostProtocol(me, DeadConnection())

	fun localMessage(message: String, style: String = "-system") {
		ScreenManager.displayChatMessage(jsobject {
			it.content = message
			it.contentStyle = style
		})
	}
	fun localErrorMessage(message: String, style:String = "-error") {
		localMessage(message)
	}

	fun started() {
		if (me.isHost) {
			hostProtocol.sendStatusRequest(screen=true)
		} else {
			hostProtocol.sendOfferCharMessage(me.char)
		}
	}
}
