package xta.ui

import kotlinx.dom.addClass
import kotlinx.dom.clear
import org.w3c.dom.Element
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLInputElement
import xta.Game
import xta.net.protocol.host.DisplayChatMessage
import xta.utils.KeyCodes

class ChatBox(chatEnabled:Boolean): UiTemplate("chat-box") {
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
		val senderName = message.senderName
		val senderStyle = message.senderStyle
		val contentStyle = message.contentStyle

		val element = messageItem.cloneNode(true) as Element
		val authorDiv = element.ref("message-author")
		val contentDiv = element.ref("message-content")

		if (senderName.isNullOrBlank()) {
			authorDiv.remove()
		} else {
			if (senderStyle != null) authorDiv.addClass(senderStyle)
			authorDiv.textContent = senderName
		}
		contentDiv.textContent = message.content
		if (contentStyle != null) contentDiv.addClass(contentStyle)

		val shouldScrollDown = historyDiv.scrollTop + historyDiv.offsetHeight >= historyDiv.scrollHeight
		historyDiv.append(element)
		if (shouldScrollDown) {
			element.scrollIntoView()
		}
	}
}
