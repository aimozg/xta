package xta.ui

import kotlinx.dom.addClass
import kotlinx.dom.removeClass
import org.w3c.dom.HTMLElement

fun setupTabList(
	vararg btnToContent: Pair<HTMLElement, HTMLElement>
) {
	for ((btn, content) in btnToContent) {
		btn.addEventListener("click", {
			for ((otherBtn, otherTab) in btnToContent) {
				if (otherBtn != btn) otherBtn.removeClass("-active")
				if (otherTab != content) otherTab.removeClass("-active")
			}
			btn.addClass("-active")
			content.addClass("-active")
		})
	}
}
