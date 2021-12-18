package xta.ui

import kotlinx.browser.document
import kotlinx.dom.hasClass
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.MouseEvent
import org.w3c.dom.get
import org.w3c.dom.set
import xta.utils.toggleClass

/*
 * Created by aimozg on 03.12.2021.
 */

private fun placeTooltip(
	target:HTMLElement,
	mouseX:Int,
	mouseY:Int,
	atTop:Boolean,
	tooltip:HTMLElement = globalTooltip
) {
	// TODO better placement (do not overflow viewport)
	tooltip.style.left = "${mouseX}px"
	tooltip.style.top = "${mouseY}px"
	tooltip.toggleClass("-visible", true)
	tooltip.toggleClass("-top", atTop)
}

fun hideTooltip() {
	globalTooltip.toggleClass("-visible", false)
	tooltipTarget = null
}

private fun addTooltipActivator(element: HTMLElement) {
	element.addEventListener("mouseenter", {
		it as MouseEvent
		val html = element.dataset["tooltip"]
		if (html.isNullOrBlank()) {
			hideTooltip()
			return@addEventListener
		}
		globalTooltip.innerHTML = html
		tooltipTarget = element
		placeTooltip(element,
			it.pageX.toInt(),
			it.pageY.toInt(),
			it.offsetY < 2.0
		)
	})
	/*element.addEventListener("mousemove", {
		it as MouseEvent
		element.dataset["tooltip"] ?: return@addEventListener
		placeTooltip(element,
			it.pageX.toInt(),
			it.pageY.toInt(),
			it.offsetY < 2.0
		)
	})*/
	element.addEventListener("mouseleave", {
		hideTooltip()
	})
}

fun HTMLElement.addTooltip(tooltipHtml:String) {
	if (tooltipHtml.isNotBlank() && dataset["tooltip_activator"] != "on") {
		addTooltipActivator(this)
		dataset["tooltip_activator"] = "on"
	}
	dataset["tooltip"] = tooltipHtml
	if (tooltipTarget == this && tooltipVisible()) {
		globalTooltip.innerHTML = tooltipHtml
	}
}

// TODO wrap this shit into proper class...
private val globalTooltip:HTMLElement by lazy {
	document.getElementById("tooltip") as HTMLElement
}
private var tooltipTarget:HTMLElement? = null
private fun tooltipVisible():Boolean = globalTooltip.hasClass("-visible")
