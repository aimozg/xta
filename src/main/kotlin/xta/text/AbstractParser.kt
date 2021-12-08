package xta.text

import xta.logging.LogContext
import xta.logging.LogManager
import xta.utils.capitalized

/*
 * Created by aimozg on 08.12.2021.
 */
abstract class AbstractParser: LogContext {
	protected abstract fun doEvaluateTag(tag:String, tagArgs:String):String

	fun evaluateTag(tag:String, tagArgs:String):String {
		val lc = tag.lowercase()
		val output = doEvaluateTag(lc, tagArgs)
		@Suppress("IntroduceWhenSubject")
		return parseTags(when {
			// [RACE] -> HUMAN
			tag == tag.uppercase() -> output.uppercase()
			// [Race] -> Human
			tag == tag.capitalized() -> output.capitalized()
			// [race] -> human
			else -> output
		})
	}

	fun parseTags(input:String):String {
		logger.trace(this,"parseTags",input)
		return Lexer(input).run {
			var output = ""
			eatLoop {
				when {
					tryEat(LA_TEXT) != null -> {
						logger.iftrace(this@AbstractParser) {"text '${match.value}'"}
						output += match.value
					}
					tryEat(LA_ESCAPED) != null -> {
						logger.iftrace(this@AbstractParser) { "escaped '${match.value[1]}'" }
						output += match.value
					}
					tryEat(LA_SIMPLETAG) != null ->{
						val tag = match.groupValues[1]
						val args = match.groupValues[2]
						logger.iftrace(this@AbstractParser) { "simpletag '$tag'" }
						try {
							output += evaluateTag(tag, args)
						} catch (e:Throwable) {
							logger.warn(this@AbstractParser, e)
							output += "/!\\${e.message}/!\\"
						}
					}/*
					tryEat(LA_TAGSTART) != null -> {
						val tag = match.groupValues[1]
						logger.iftrace(this@AbstractParser) { "tagstart '$tag'" }

					}*/
					tryEat(HTMLParser.LA_TAG_START) != null -> {
						val tag = match.groupValues[1]
						// TODO sanitize attrs
						if (tag.lowercase() !in ALLOWED_HTML_TAGS) {
							output += "/!\\HTML tag $tag not allowed/!\\"
						} else {
							output += match.value
						}
					}
					tryEat(HTMLParser.LA_TAG_END) != null -> {
						val tag = match.groupValues[1]
						if (tag.lowercase() in ALLOWED_HTML_TAGS) {
							output += match.value
						}
					}
					else -> {
						error("Cannot parse: '$remainder'")
					}
				}
			}
			output
		}
	}

	fun postProcess(s:String):String {
		logger.trace(this,"postProcess",s)
		return REX_ESCAPED.replace(s) {
			it.value.drop(1)
		}
	}

	fun parse(s:String):String {
		return postProcess(parseTags(s))
	}
	companion object {
		val ALLOWED_HTML_TAGS = setOf("b","i","u","p","h1","h2","h3","h4","h5","hr","br","span")

		private val REX_ESCAPED = Regex("""\\.""")
		// "Lookahead" regexes
		/**
		 * Plain text
		 */
		private val LA_TEXT = Regex("""^[^\\\[<]+""")

		/**
		 * Escaped character
		 */
		private val LA_ESCAPED = Regex("""^\\.""")

		/**
		 * Simple `[parsertag]` or `[tag arguments]`.
		 * Group 1 = tag name
		 * Group 2 = tag arguments
		 */
		private val LA_SIMPLETAG = Regex("""^\[(\w+)(\b[^\]]*)\]""")

		/**
		 * Parser tag start
		 * Group 1 = tag name ("parsertag" in the example)
		 */
		private val LA_TAGSTART = Regex("""^\[(\w+)\b""")

		private val logger by lazy {
			LogManager.getLogger("xta.text.Parser")
		}
	}
}
