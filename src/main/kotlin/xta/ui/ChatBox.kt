package xta.ui

import xta.Game
import xta.net.protocol.host.DisplayChatMessage
import xta.utils.KeyCodes
import kotlinx.dom.addClass
import kotlinx.dom.clear
import org.w3c.dom.Element
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLInputElement

class ChatBox(chatEnabled:Boolean): UiElement("chat-box") {
	private val historyDiv = fragment.ref("history")
	private val messageInput = fragment.ref<HTMLInputElement>("message-input")
	private val sendBtn = fragment.ref<HTMLButtonElement>("send-button")
	private val messageItem = fragment.ref("message").apply { remove() }
	var chatEnabled = chatEnabled
		set(value) {
			field = value
			sendBtn.disabled = !value
			messageInput.disabled = !value
		}

	init {
		messageInput.onkeyup = {
			if (it.code == KeyCodes.ENTER) sendBtn.click()
		}
		sendBtn.onclick = {
			val msg = messageInput.value.trim()
			if (msg.isNotEmpty()) {
				messageInput.value = ""
				Game.hostProtocol.sendChatMessage(msg)
			}
		}
	}

	fun displayChatHistory(history: List<DisplayChatMessage>) {
		historyDiv.clear()
		for (message in history) {
			addChatMessage(message)
		}
	}

	fun addChatMessage(message: DisplayChatMessage) {
		val element = messageItem.cloneNode(true) as Element
		element.ref("message-author").also {
			it.addClass(message.senderStyle)
			it.textContent = message.senderName
		}
		element.ref("message-content").textContent = message.content
		historyDiv.append(element)
	}
}
