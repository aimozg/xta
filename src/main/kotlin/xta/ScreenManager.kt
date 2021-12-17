package xta

import kotlinx.browser.document
import kotlinx.dom.clear
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import xta.game.settings.GameSettings
import xta.logging.LogContext
import xta.logging.LogManager
import xta.net.protocol.host.DisplayChatMessage
import xta.net.protocol.messages.ScreenJson
import xta.ui.*
import xta.utils.getElementByIdOrThrow

/*
 * Created by aimozg on 01.12.2021.
 */
object ScreenManager: LogContext {

	lateinit var uiRoot: Element
	var currentScreen: UiScreen = UiScreen.EMPTY

	fun init() {
		uiRoot = document.getElementByIdOrThrow("root")
	}

	fun setScreen(screen:UiScreen) {
		uiRoot.clear()
		hideTooltip()
		screen.insertTo(uiRoot)
		currentScreen = screen
	}

	val chatHistory = ArrayList<DisplayChatMessage>()
	val chatBox = ChatBox(false, document.getElementByIdOrThrow("chatbox-container") as HTMLElement)
	var chatEnabled: Boolean
		get() = chatBox.chatEnabled
		set(value) {
			chatBox.chatEnabled = value
		}

	fun displayChatMessage(message: DisplayChatMessage) {
		chatHistory.add(message)
		chatBox.addChatMessage(message)
		while (chatHistory.size > GameSettings.data.chatHistoryLimit) {
			chatHistory.removeFirst()
			chatBox.removeFirst()
		}
	}

	fun displaySceneContent(scene: ScreenJson = Game.me.screen) {
		logger.debug(this, "displayScreen")
		when (val screen = currentScreen) {
			is MainScreen -> screen.dislaySceneContent(scene)
			else -> logger.error(this, "inappropriate displayScreen call")
		}
	}

	fun updateCharacter() {
		logger.debug(this, "updateCharacter")
		when (val screen = currentScreen) {
			is MainScreen -> screen.showCharacter(Game.myCharacter)
			is CombatScreen -> screen.updatePlayer()
			else -> logger.error(this,"inappropriate updateCharacter call")
		}
	}

	fun showStartMenu() {
		logger.debug(this, "showStartMenu")
		StartMenu().show()
	}

	fun showConnectMenu(asHost:Boolean) {
		logger.debug(this, "showConnectMenu")
		ConnectMenu(asHost).show()
	}

	fun showGameScreen() {
		logger.debug(this, "showGameScreen")
		val screen = MainScreen()
		screen.show()
		screen.showCharacter(Game.myCharacter)
	}

	fun transitionToCombat() {
		logger.debug(this, "transitionToCombat")
		val screen = CombatScreen()
		screen.show()
		screen.update()
	}

	fun transitionOutOfCombat() {
		logger.debug(this, "transitionOutOfCombat")
		showGameScreen()
	}

	fun updateCombatScreen(canChangeScreen:Boolean = false){
		logger.debug(this, "updateCombatScreen")
		when (val screen = currentScreen) {
			is CombatScreen -> screen.update()
			else -> {
				if (canChangeScreen) {
					transitionToCombat()
				} else {
					logger.error(null,"inappropriate updateCombatScreen call")
				}
			}
		}
	}

	override fun toLogString() = "[ScreenManager]"

	private val logger = LogManager.getLogger("xta.ScreenManager")
}
