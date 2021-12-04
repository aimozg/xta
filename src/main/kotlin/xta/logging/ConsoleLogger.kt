package xta.logging

import xta.Game
import xta.utils.fixedWrap
import js.toFixed
import kotlin.js.Date

class ConsoleLogger(id: String) : Logger(id) {
	val wrappedId by lazy {
		val n = 20
		val id2 = if (id.length <= n) {
			id
		} else {
			id.replace(Regex("""(\w)\w+\."""),"$1.")
		}
		id2.fixedWrap("[","]",n)
	}

	override fun doLog(level: Level, context: LogContext, message: String) {
		val myname = wrappedId
		val actualContext = context.toLogString()
		val dt = (Date().getTime() - t0).div(1000).toFixed(3).padStart(7, ' ')
		val safemessage = message.replace("\n"," ")
		when (level) {
			Level.ALL,
			Level.TRACE,
			Level.DEBUG ->
				console.asDynamic().debug(dt, myname, actualContext, safemessage)
			Level.INFO ->
				console.info(dt, myname, actualContext, safemessage)
			Level.WARNING ->
				console.warn(dt, myname, actualContext, safemessage)
			Level.ERROR,
			Level.CRITICAL,
			Level.NONE -> {
				console.error(dt, myname, actualContext, safemessage)
				Game.whisperToSelf(safemessage, "-error")
			}
		}
	}

	companion object {
		private val t0 = Date().getTime()
	}

}
