package xta.ui

import kotlinx.dom.addClass
import kotlinx.dom.appendElement
import org.w3c.dom.Element

/*
 * Created by aimozg on 03.12.2021.
 */
fun Element.addTooltip(tooltipText:String) {
	if (tooltipText.isBlank()) return
	addClass("tooltip-activator")
	appendElement("div") {
		className = "tooltip"
		textContent = tooltipText
	}
}
