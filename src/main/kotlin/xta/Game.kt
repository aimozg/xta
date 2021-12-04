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
object Game {
	val me = Player(isMe = true)

	val myCharacter get() = me.char
	val characterImported get() = me.charLoaded

	var server: GameServer? = null
	var hostProtocol: HostProtocol = RemoteHostProtocol(me, DeadConnection())

	fun whisperToSelf(message: String, style: String = "-system", senderName: String ="[System]") {
		ScreenManager.displayChatMessage(jsobject {
			it.senderName = senderName
			it.senderStyle = style
			it.content = message
		})
	}

	fun started() {
		if (me.isHost) {
			hostProtocol.sendStatusRequest(screen=true)
		} else {
			hostProtocol.sendOfferCharMessage(me.char)
		}
	}
}
