package xta.logging

import xta.Game

/*
 * Created by aimozg on 01.12.2021.
 */
abstract class Logger(val id:String) {
	var level: Level = Level.INFO

	abstract fun doLog(level:Level, context: LogContext, message:String)
	abstract fun doLogObject(level:Level, context: LogContext, message:String, obj: Any?)
	open fun format(message:Any?):String = when {
		message is Error -> message.stackTraceToString()+
				(message.cause?.let { " Caused by: "+format(it) }?:"")
		else -> message.toString()
	}

	fun log(level:Level, context: LogContext?, message:String) {
		if (level >= this.level) doLog(level, context ?: Game.me, message)
	}
	fun logObject(level:Level, context: LogContext?, message:String, obj: Any?) {
		if (level >= this.level) doLogObject(level, context ?: Game.me, message, obj)
	}

	fun trace(context: LogContext?, message: String) = log(Level.TRACE, context, message)
	fun debug(context: LogContext?, message: String) = log(Level.DEBUG, context, message)
	fun info(context: LogContext?, message: String) = log(Level.INFO, context, message)
	fun warn(context: LogContext?, message: String) = log(Level.WARNING, context, message)
	fun error(context: LogContext?, message: String) = log(Level.ERROR, context, message)
	fun critical(context: LogContext?, message: String) = log(Level.CRITICAL, context, message)

	fun trace(context: LogContext?, vararg message: Any?) {
		if (Level.TRACE >= level) log(Level.TRACE, context, message.joinToString(" ") {
			format(it)
		})
	}
	fun debug(context: LogContext?, vararg message: Any?) {
		if (Level.DEBUG >= level) log(Level.DEBUG, context, message.joinToString(" ") {
			format(it)
		})
	}
	fun info(context: LogContext?, vararg message: Any?) {
		if (Level.INFO >= level) log(Level.INFO, context, message.joinToString(" ") {
			format(it)
		})
	}
	fun warn(context: LogContext?, vararg message: Any?) {
		if (Level.WARNING >= level) log(Level.WARNING, context, message.joinToString(" ") {
			format(it)
		})
	}
	fun error(context: LogContext?, vararg message: Any?) {
		if (Level.ERROR >= level) log(Level.ERROR, context, message.joinToString(" ") {
			format(it)
		})
	}
	fun critical(context: LogContext?, vararg message: Any?) {
		if (Level.CRITICAL >= level) log(Level.CRITICAL, context, message.joinToString(" ") {
			format(it)
		})
	}

	inline fun iftrace(context: LogContext?, message: ()->String) {
		if (level >= this.level) log(Level.TRACE, context, message())
	}
	inline fun ifdebug(context: LogContext?, message: ()->String) {
		if (level >= this.level) log(Level.DEBUG, context, message())
	}
	inline fun ifinfo(context: LogContext?, message: ()->String) {
		if (level >= this.level) log(Level.INFO, context, message())
	}

	fun levelEnabled(level: Level): Boolean {
		return level >= this.level
	}

	enum class Level {
		ALL,
		TRACE,
		DEBUG,
		INFO,
		WARNING,
		ERROR,
		CRITICAL,
		NONE
	}

}

