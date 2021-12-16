package xta.ui

import kotlinx.dom.appendElement
import kotlinx.dom.clear
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.asList
import xta.Game
import xta.ScreenManager
import xta.game.PlayerCharacter
import xta.net.protocol.messages.ScreenJson

/*
 * Created by aimozg on 01.12.2021.
 */
class MainScreen: UiScreen("main-screen") {
	private val textOutputDiv = fragment.ref("text-output")
	private val actionsDiv = fragment.ref("actions")
	private val leftDiv = fragment.ref("left")
	private val rightDiv = fragment.ref("right")
	private val playerPanel = CharacterPanel().also { it.insertTo(leftDiv) }

	init {
		ScreenManager.chatEnabled = true
	}

	fun disableActions() {
		for (btn in actionsDiv.querySelectorAll("button").asList()) {
			(btn as HTMLButtonElement).disabled = true
		}
	}

	fun dislaySceneContent(scene: ScreenJson) {
		// TODO sanitize!
		hideTooltip()
		textOutputDiv.innerHTML = scene.content
		actionsDiv.clear()
		for (action in scene.actions) {
			actionsDiv.appendElement("div") {
				this as HTMLElement
				className = "action"
				appendElement("button") {
					this as HTMLButtonElement
					textContent = action.label
					if (action.disabled == true) disabled = true
					onclick = {
						hideTooltip()
						disableActions()
						Game.hostProtocol.sendSceneAction(scene.sceneId, action.actionId)
					}
				}
				addTooltip(action.hint ?: "")
			}
		}
	}

	fun showCharacter(char: PlayerCharacter) {
		playerPanel.showCharacter(char)
	}

}
