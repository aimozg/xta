package xta

import xta.net.protocol.host.DisplayChatMessage
import xta.net.protocol.messages.ScreenJson
import xta.ui.*
import xta.utils.getElementByIdOrThrow
import kotlinx.browser.document
import kotlinx.dom.clear
import org.w3c.dom.Element

/*
 * Created by aimozg on 01.12.2021.
 */
object ScreenManager {

	lateinit var uiRoot: Element
	var currentScreen: UiScreen = UiScreen.EMPTY

	fun init() {
		uiRoot = document.getElementByIdOrThrow("root")
		chatBox.insertTo(document.getElementByIdOrThrow("chatbox-container"))
	}

	fun setScreen(screen:UiScreen) {
		uiRoot.clear()
		screen.insertTo(uiRoot)
		currentScreen = screen
	}

	val chatHistory = ArrayList<DisplayChatMessage>()
	val chatBox = ChatBox(false)
	var chatEnabled: Boolean
		get() = chatBox.chatEnabled
		set(value) {
			chatBox.chatEnabled = value
		}

	fun displayChatMessage(message: DisplayChatMessage) {
		chatHistory.add(message)
		chatBox.addChatMessage(message)
	}

	fun displayScreen(scene: ScreenJson = Game.me.screen) {
		(currentScreen as? MainScreen)?.displayScene(scene)
	}

	fun updateCharacter() {
		(currentScreen as? MainScreen)?.showCharacter(Game.myCharacter)
	}

	fun showStartMenu() {
		StartMenu().show()
	}

	fun showConnectMenu(asHost:Boolean) {
		ConnectMenu(asHost).show()
	}

	fun showGameScreen() {
		val screen = MainScreen()
		screen.show()
		screen.showCharacter(Game.myCharacter)
	}

}
