package xta.text

import xta.logging.LogManager

/*
 * Created by aimozg on 04.12.2021.
 */
open class Lexer(
	private val input:String
) {

	// TODO Optimization note: Don't cut string into parts, track index and use regexes' matchAt()
	private var s:String = input
	val remainder get() = s
	val length get() = s.length
	fun eol():Boolean = s.isEmpty()
	fun skip(n:Int) {
		s = s.drop(n)
	}
	fun eat(n:Int):String {
		val part = s.substring(0, n)
		logger.iftrace(null) { "Matched " + JSON.stringify(part) }
		skip(n)
		return part
	}

	var match:MatchResult = EmptyMatchResult

	fun tryEat(lookahead:Regex):MatchResult? {
		return lookahead.find(s)?.also {
			match = it
			logger.iftrace(null) { "Matched " + JSON.stringify(it.value) }
			skip(it.range.last+1)
		}
	}

	inline fun eatLoop(step:()->Unit) {
		var n = length+1
		while(!eol()) {
			if (length >= n) {
				// Ensure string shortens as we go
				error("Parser stuck at '$remainder'")
			}
			n = length
			step()
		}
	}

	companion object {
		private val logger = LogManager.getLogger("xta.text.Lexer")
	}
}
