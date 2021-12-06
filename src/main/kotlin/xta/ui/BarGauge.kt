package xta.ui

import org.w3c.dom.HTMLElement

class BarGauge: UiTemplate("bar-gauge") {
	private val mainDiv = fragment.ref("main")
	private val extraDiv = fragment.ref("extra")
	private val textDiv = fragment.ref("text")

	private fun setOneBar(element: HTMLElement, value: Double?, max: Double?) {
		if (value == null || max == null || value <= 0) {
			element.style.display = "none"
		} else {
			element.style.display = ""
			element.style.width = "" + (value * 100 / max).coerceIn(0.0, 100.0) + "%"
		}
	}

	fun displayValue(
		value: Double? = null,
		extra: Double? = null,
		max: Double? = null,
		text: String = ""
	) {
		setOneBar(mainDiv, value, max)
		setOneBar(extraDiv, extra, max)
		textDiv.textContent = text
	}
}
