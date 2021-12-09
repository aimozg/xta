package xta.ui

import org.w3c.dom.HTMLElement

class BarGauge: UiTemplate("bar-gauge") {
	val container = fragment.ref("container")
	private val mainDiv = fragment.ref("main")
	private val extraDiv = fragment.ref("extra")
	private val textDiv = fragment.ref("text")

	private fun setOneBar(element: HTMLElement, value: Double?, max: Double?) {
		if (value == null || max == null || value <= 0) {
			element.style.display = "none"
		} else {
			element.style.display = ""
			val width =
				if (value.isFinite() && max.isFinite()) (value * 100 / max).coerceIn(0.0, 100.0)
				else 100.0
			element.style.width = "$width%"
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
