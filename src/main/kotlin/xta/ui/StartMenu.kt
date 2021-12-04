package xta.ui

import xta.Game
import xta.game.PlayerCharacter
import xta.ScreenManager
import xta.charview.CharViewImage
import xta.flash.FlashImporter
import xta.game.scenes.PlayerAppearanceScene
import xta.text.Parser
import kotlinx.dom.clear
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLInputElement
import org.w3c.files.get

/*
 * Created by aimozg on 29.11.2021.
 */
class StartMenu: UiScreen("start-menu") {
	private val outputElement = fragment.ref("maintext")
	private val hostButton = fragment.ref<HTMLButtonElement>("host-btn")
	private val joinButton = fragment.ref<HTMLButtonElement>("join-btn")
	private val saveFile = fragment.ref<HTMLInputElement>("savefile")
	private val agreement = fragment.ref<HTMLInputElement>("agreement")
	private val charviewDiv = fragment.ref("charview")

	private val display = HTMLElementDisplay(Game.me,Parser(Game.me,Game.me), outputElement)

	fun showCharacter() {
		display.clearOutput()
		PlayerAppearanceScene.execute(display)
		charviewDiv.clear()
		charviewDiv.append(CharViewImage.INSTANCE.renderCharacter(Game.myCharacter).canvas)
	}

	init {
		ScreenManager.chatEnabled = false
		hostButton.disabled = true
		joinButton.disabled = true

		fun importCharacter(character: PlayerCharacter) {
			Game.me.char = character
			showCharacter()
			if (agreement.checked) {
				hostButton.disabled = false
				joinButton.disabled = false
			}
		}
		if (Game.characterImported) {
			showCharacter()
		}
		agreement.onchange = {
			if (Game.characterImported && agreement.checked) {
				hostButton.disabled = false
				joinButton.disabled = false
			} else {
				hostButton.disabled = true
				joinButton.disabled = true
			}
		}
		saveFile.addEventListener("change", {
			val files = saveFile.files
			if (files?.length == 1) {
				FlashImporter().importBlob(files[0]!!).then { character ->
					importCharacter(character)
				}.catch { e ->
					console.error(e)
					Game.whisperToSelf("Failed to import: "+e.message,"-error")
				}
			}
		})
		hostButton.onclick = {
			ScreenManager.showConnectMenu(true)
		}
		joinButton.onclick = {
			ScreenManager.showConnectMenu(false)
		}
	}

}


