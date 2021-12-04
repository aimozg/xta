package xta.text

import xta.logging.LogManager
import xta.utils.ENTITY_REGEX
import xta.utils.decodeEntity
import kotlinx.browser.document
import org.w3c.dom.DocumentFragment
import org.w3c.dom.ParentNode

/*
 * Created by aimozg on 04.12.2021.
 */

class HTMLParser() {


	fun sanitizeHTML(html: String): DocumentFragment = Lexer(html).run {
		val f = document.createDocumentFragment()
		var output: ParentNode = f

		/**
		 * (current_tag to parent)
		 */
		val nest = ArrayList<Pair<String,ParentNode>>()
		eatLoop {
			when {
				tryEat(LA_SAFETEXT) != null -> {
					output.append(match.value)
				}
				tryEat(LA_ENTITY) != null -> {
					output.append(decodeEntity(match.value))
				}
				tryEat(LA_TAG_START) != null -> {
					val tag = match.groupValues[1].lowercase()
					val attrs = match.groupValues[2]
					if (tag in SAFE_HTML_TAGS) {
						val ele = document.createElement(tag)
						for (matchResult in REX_ATTRIBUTE.findAll(attrs)) {
							val (name,v1,v2,v3) = matchResult.destructured
							if (name in SAFE_HTML_ATTRIBUTES) {
								ele.setAttribute(name,v1+v2+v3)
							} else {
								logger.warn(null,"Unsafe html attribute $tag.$name")
							}
						}
						if (tag !in SINGULAR_HTML_TAGS && match.groupValues[3] == "") {
							nest.add(tag to output)
							output = ele
						}
					} else {
						logger.warn(null,"Unsafe html tag $tag")
					}
				}
				tryEat(LA_TAG_END) != null -> {
					val tag = match.groupValues[1].lowercase()
					if (tag in SAFE_HTML_TAGS) {
						val last = nest.lastOrNull()
						if (tag == last?.first) {
							output = last.second
							nest.removeLast()
						} else {
							logger.warn(null,"Unexpected closing tag $tag")
						}
					}
				}
				else -> {
					logger.error(null, "Unexpected html input $remainder")
				}
			}
		}
		if (nest.isNotEmpty()) {
			logger.warn(null,"Tag "+nest.last().first+" not closed")
		}
		return f
	}

	companion object {
		private val logger = LogManager.getLogger("xta.text.HTMLParser")

		private val SAFE_HTML_TAGS = setOf("b","i","u","p","h1","h2","h3","h4","h5","hr","br","span")
		private val SINGULAR_HTML_TAGS = setOf("br","hr","img","input")
		private val SAFE_HTML_ATTRIBUTES = setOf("class")

		// "Lookahead" regexes

		/**
		 * Safe text
		 */
		val LA_SAFETEXT = Regex("""^[^<&]+""")

		/**
		 * Opening or single HTML element tag.
		 * - Group 1: tag name
		 * - Group 2: raw attributes string
		 * - Group 3: '/' or empty string before the >
		 */
		val LA_TAG_START = Regex(
			"""^< *([\w\d:-]+) *(""" +
					/* name     */ """(?: *[\w\d:_-]+""" +
					/* ="value" */ """(?: *=(?:"[^"]*"|'[^']*'|[\w\d_-]+))?)*""" +
					""") *(/?)>"""
		)
		/**
		 * Closing HTML element tag
		 * - Group 1: tag name
		 */
		val LA_TAG_END = Regex("""^</ *([\w\d:-]+) *>""")

		/**
		 * HTML character entity
		 */
		val LA_ENTITY = Regex("^"+ ENTITY_REGEX.pattern)

		/**
		 * Single HTML tag attribute
		 * - Group 1: Attribute name
		 * - Group 2 (optional): Attribute value in double quotes
		 * - Group 3 (optional): Attribute value in single quotes
		 * - Group 4 (optional): Unquoted attribute value
		 */
		val REX_ATTRIBUTE = Regex("""([\w\d:_-]+)(?:=(?:"([^"]*)"|'([^']*)'|([\w\d_-]+)))?""")
	}
}
