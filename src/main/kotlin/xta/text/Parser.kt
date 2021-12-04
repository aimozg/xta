package xta.text

import xta.Game
import xta.Player
import xta.game.text.evalParserTag
import xta.logging.LogContext
import xta.logging.LogManager
import xta.utils.capitalized

/*
 * Created by aimozg on 28.11.2021.
 */
/**
 * [myPlayer] - Player who reads the text, [player] - player who is described.
 *
 * If [myPlayer] is Alice and [player] is Bob, then `"[name] [is]"` evals to `Bob is`;
 * If [myPlayer] and [player] are the same, then `"[name] [is]"` evals to `You are`.
 */
class Parser(
	val myPlayer: Player?,
	val player: Player?
): LogContext {
	override fun toLogString() = player?.toLogString()?: Game.me.toLogString()

	private fun doEvaluateTag(tag:String, tagArgs:String):String = when(tag) {
		"if" -> TODO("If tag is not implemented")
		else -> evalParserTag(myPlayer?.char, player?.char,tag,tagArgs)
	}
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
						logger.iftrace(this@Parser) {"text '${match.value}'"}
						output += match.value
					}
					tryEat(LA_ESCAPED) != null -> {
						logger.iftrace(this@Parser) { "escaped '${match.value[1]}'" }
						output += match.value
					}
					tryEat(LA_SIMPLETAG) != null ->{
						val tag = match.groupValues[1]
						logger.iftrace(this@Parser) { "simpletag '$tag'" }
						try {
							output += evaluateTag(tag, "")
						} catch (e:Throwable) {
							logger.warn(this@Parser, e)
							output += "/!\\${e.message}/!\\"
						}
					}
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
		 * Simple `[parsertag]`.
		 * Group 1 = tag name ("parsertag" in the example)
		 */
		private val LA_SIMPLETAG = Regex("""^\[(\w+)\]""")

		private val logger by lazy {
			LogManager.getLogger("xta.text.Parser")
		}
	}
}
