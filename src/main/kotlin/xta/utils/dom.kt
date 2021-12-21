package xta.utils

import kotlinx.browser.document
import org.w3c.dom.*

fun HTMLElement.toggleClass(className:String, condition:Boolean) {
	classList.toggle(className, condition)
}

val Location.fullpath get() = origin + pathname

fun Document.getElementByIdOrThrow(elementId:String) =
	getElementById(elementId) ?: error("Element #$elementId not found in document")

fun DocumentFragment.getElementByIdOrThrow(elementId:String) =
	getElementById(elementId) ?: error("Element #$elementId not found in document fragment")

fun<T: Element> T.trim():T {
	trimStart()
	trimEnd()
	return this
}
fun<T: Element> T.trimStart():T {
	val f = firstChild
	when {
		f is Element -> f.trimStart()
		f?.nodeType == Node.TEXT_NODE -> f.textContent = f.textContent?.trimStart()
	}
	return this
}
fun<T: Element> T.trimEnd():T {
	val f = firstChild
	when {
		f is Element -> f.trimEnd()
		f?.nodeType == Node.TEXT_NODE -> f.textContent = f.textContent?.trimEnd()
	}
	return this
}

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
