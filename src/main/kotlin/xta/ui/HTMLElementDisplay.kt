package xta.ui

import xta.Player
import xta.game.Scene
import xta.text.Display
import xta.text.HTMLParser
import xta.text.Parser
import kotlinx.dom.addClass
import kotlinx.dom.appendElement
import kotlinx.dom.clear
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement

/*
 * Created by aimozg on 28.11.2021.
 */

/**
 * Immediate HTML display
 */
class HTMLElementDisplay(
	override val player: Player,
	override val parser: Parser,
	val outputElement: HTMLElement
): Display() {
	override var sceneId: String = ""

	override fun rawOutput(text: String) {
		outputElement.append(HTMLParser().sanitizeHTML(text))
	}

	override fun clearOutput() {
		outputElement.clear()
	}

	override fun goto(scene: Scene) {
		scene.execute(player)
	}

	override fun addButton(label: String, hint: String, disabled: Boolean, callback: () -> Unit) {
		outputElement.appendElement("button") {
			this as HTMLButtonElement
			addClass("action")
			textContent = label
			this.disabled = disabled
			addTooltip(hint)
			onclick = {callback()}
		}
	}
}
