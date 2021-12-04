package xta.utils

import kotlinx.browser.document
import org.w3c.dom.Document
import org.w3c.dom.DocumentFragment
import org.w3c.dom.HTMLElement
import org.w3c.dom.asList

fun Document.getElementByIdOrThrow(elementId:String) =
	getElementById(elementId) ?: error("Element #$elementId not found in document")

fun DocumentFragment.getElementByIdOrThrow(elementId:String) =
	getElementById(elementId) ?: error("Element #$elementId not found in document fragment")

fun HTMLElement.appendHTML(content:String) {
	val f = document.createElement("div")
	f.innerHTML = content
	for (node in f.childNodes.asList()) {
		appendChild(node)
	}
}

private val tmpspan = document.createElement("span")

val ENTITY_REGEX = Regex("""&(?:#\d+|#x[\da-fA-F]+|[a-zA-Z]+);""")

fun decodeEntity(entity:String):String {
	if (!ENTITY_REGEX.matches(entity)) return entity
	tmpspan.innerHTML = entity
	return tmpspan.textContent?:""
}
