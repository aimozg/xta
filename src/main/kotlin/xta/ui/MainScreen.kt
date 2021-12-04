package xta.ui

import kotlinx.dom.appendElement
import kotlinx.dom.clear
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.asList
import xta.Game
import xta.ScreenManager
import xta.game.PlayerCharacter
import xta.game.settings.GameSettings
import xta.net.protocol.messages.ScreenJson

/*
 * Created by aimozg on 01.12.2021.
 */
class MainScreen: UiScreen("main-screen") {
	private val textOutputDiv = fragment.ref("text-output")
	private val actionsDiv = fragment.ref("actions")
	private val statusDiv = fragment.ref("status")
	private val statusPanel = StatusPanel().also { it.insertTo(statusDiv) }

	init {
		ScreenManager.chatEnabled = true
	}

	fun disableActions() {
		for (btn in actionsDiv.querySelectorAll("button").asList()) {
			(btn as HTMLButtonElement).disabled = true
		}
	}

	fun displayScene(scene: ScreenJson) {
		// TODO sanitize!
		textOutputDiv.innerHTML = scene.content
		actionsDiv.clear()
		for (action in scene.actions) {
			actionsDiv.appendElement("button") {
				this as HTMLButtonElement
				className = "action"
				textContent = action.label
				if (action.disabled == true) disabled = true
				addTooltip(action.hint?:"")
				onclick = {
					disableActions()
					Game.hostProtocol.sendSceneAction(scene.sceneId, action.actionId)
				}
			}
		}
	}

	fun showCharacter(char: PlayerCharacter) {
		statusPanel.showCharacter(char, GameSettings.data.render?:false)
	}

}
