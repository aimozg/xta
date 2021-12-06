package xta.net.protocol

import xta.Player
import xta.game.PlayerCharacter
import xta.logging.LogContext
import xta.utils.jsobject

/*
 * Created by aimozg on 01.12.2021.
 */
abstract class HostProtocol: LogContext {
	abstract val isConnected: Boolean
	abstract val player:Player

	abstract fun sendMessage(message: MessageToHost)

	fun sendChatMessage(content: String) {
		sendMessage(jsobject { msg ->
			msg.chat = jsobject {
				it.content = content
			}
		})
	}

	fun sendOfferCharMessage(char: PlayerCharacter) {
		sendMessage(jsobject { msg ->
			msg.offerChar = jsobject {
				it.char = char.serializeToJson()
			}
		})
	}

	fun sendStatusRequest(char:Boolean=false,screen:Boolean=false) {
		sendMessage(jsobject { msg ->
			msg.statusRequest = jsobject {
				it.char = char
				it.screen = screen
			}
		})
	}

	fun sendSceneAction(sceneId:String, actionId:Int) {
		sendMessage(jsobject { msg ->
			msg.sceneAction = jsobject {
				it.sceneId = sceneId
				it.actionId = actionId
			}
		})
	}
}

