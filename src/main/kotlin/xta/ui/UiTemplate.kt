package xta.ui

import kotlinx.browser.document
import org.w3c.dom.*
import xta.utils.trim

open class UiTemplate(
	template: HTMLTemplateElement
) {
	constructor(templateId: String): this(findTemplate(templateId).trim())

	val fragment: DocumentFragment = template.content.cloneNode(true) as DocumentFragment

	protected fun ParentNode.ref(ref:String): HTMLElement {
		val element = (querySelector("[data-ref=$ref]") as? HTMLElement
			?: error("Missing element '$ref'"))
		element.removeAttribute("data-ref")
		return element
	}
	protected inline fun<reified T: HTMLElement> ParentNode.ref(ref:String):T =
		ref(ref) as? T ?: error("Element '$ref' is not a ${T::class.js}")

	fun insertTo(node: Element) {
		node.append(fragment)
	}

	companion object {
		fun findTemplate(templateId: String): HTMLTemplateElement =
			(document.getElementById(templateId) as? HTMLTemplateElement)
				?: error("Cannot find template #$templateId")
	}
}
