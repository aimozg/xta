package xta

import xta.net.GameServer
import xta.net.protocol.HostProtocol
import xta.net.protocol.RemoteHostProtocol
import xta.net.transport.DeadConnection
import xta.utils.jsobject
import xta.utils.randomString
import kotlin.js.Json
import kotlin.random.Random

/*
 * Created by aimozg on 28.11.2021.
 */

/**
 * Globals
 */
@Suppress("NON_EXPORTABLE_TYPE")
@JsExport
object Game {
	// Both guest and host
	val me = Player(id=Random.Default.randomString(8), isMe = true)
	val myCharacter get() = me.char

	// Host
	var server: GameServer? = null

	// Guest
	var hostProtocol: HostProtocol = RemoteHostProtocol(me, DeadConnection())
	val knownPlayers = hashMapOf(me.id to me)
	fun addKnownPlayer(player:Player) {
		knownPlayers[player.id] = player
	}
	fun requireKnownPlayer(id:String, charData: Json?=null): Player {
		val player = knownPlayers.getOrPut(id) { Player(id, false) }
		if (charData != null) player.char.deserializeFromJson(charData)
		return player
	}
	fun setMyPlayerId(id:String) {
		knownPlayers.remove(me.id)
		me.id = id
		knownPlayers[id] = me
	}

	// Both guest and host
	fun localMessage(message: String, style: String = "-system") {
		ScreenManager.displayChatMessage(jsobject {
			it.content = message
			it.contentStyle = style
		})
	}
	fun localWarnMessage(message: String) {
		ScreenManager.displayChatMessage(jsobject {
			it.senderName = "[Warning]"
			it.senderStyle = "-warning"
			it.content = message
			it.contentStyle = "-warning"
		})
	}
	fun localErrorMessage(message: String, style:String = "-error") {
		ScreenManager.displayChatMessage(jsobject {
			it.senderName = "[Error]"
			it.senderStyle = style
			it.content = message
			it.contentStyle = style
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
