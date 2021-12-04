package xta.ui

import xta.game.PlayerCharacter
import xta.charview.CharViewImage
import kotlinx.dom.clear

/*
 * Created by aimozg on 02.12.2021.
 */
class StatusPanel: UiElement("status-panel") {
	private val titleDiv = fragment.ref("title")
	private val subtitleDiv = fragment.ref("subtitle")
	private val renderDiv = fragment.ref("render")

	fun showCharacter(char: PlayerCharacter, render:Boolean) {
		titleDiv.textContent = char.name
		subtitleDiv.textContent = "Level ${char.level} ${char.race()}"
		renderDiv.clear()
		if (render) {
			renderDiv.append(CharViewImage.INSTANCE.renderCharacter(char).canvas)
		}
	}
}
