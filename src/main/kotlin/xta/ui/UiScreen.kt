package xta.ui

import xta.ScreenManager
import kotlinx.browser.document
import org.w3c.dom.HTMLTemplateElement

open class UiScreen : UiElement {
	constructor(template: HTMLTemplateElement) : super(template)
	constructor(templateId: String): super(templateId)

	fun show() {
		ScreenManager.setScreen(this)
	}

	object EMPTY: UiScreen(document.createElement("template") as HTMLTemplateElement) {
	}
}

