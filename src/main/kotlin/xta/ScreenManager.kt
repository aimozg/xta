package xta

import kotlinx.browser.document
import kotlinx.dom.clear
import org.w3c.dom.Element
import xta.net.protocol.host.DisplayChatMessage
import xta.net.protocol.messages.ScreenJson
import xta.ui.*
import xta.utils.getElementByIdOrThrow

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
		when (val screen = currentScreen) {
			is MainScreen -> screen.displayScene(scene)
			else -> Game.localErrorMessage("inappropriate displayScreen call")
		}
	}

	fun updateCharacter() {
		when (val screen = currentScreen) {
			is MainScreen -> screen.showCharacter(Game.myCharacter)
			is CombatScreen -> screen.updatePlayer()
			else -> Game.localErrorMessage("inappropriate updateCharacter call")
		}
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

	fun transitionToCombat() {
		val screen = CombatScreen()
		screen.show()
		screen.update()
	}

	fun transitionOutOfCombat() {
		showGameScreen()
	}

	fun updateCombatScreen(canChangeScreen:Boolean = false){
		when (val screen = currentScreen) {
			is CombatScreen -> screen.update()
			else -> {
				if (canChangeScreen) {
					transitionToCombat()
				} else {
					Game.localErrorMessage("inappropriate updateCombatScreen call")
				}
			}
		}
	}

}
