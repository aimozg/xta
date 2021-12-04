package xta.logging

import xta.Game

/*
 * Created by aimozg on 01.12.2021.
 */
abstract class Logger(val id:String) {
	var level: Level = Level.INFO

	abstract fun doLog(level:Level, context: LogContext, message:String)
	fun log(level:Level, context: LogContext?, message:String) {
		if (level >= this.level) doLog(level, context ?: Game.me, message)
	}

	fun trace(context: LogContext?, message: String) = log(Level.TRACE, context, message)
	fun debug(context: LogContext?, message: String) = log(Level.DEBUG, context, message)
	fun info(context: LogContext?, message: String) = log(Level.INFO, context, message)
	fun warn(context: LogContext?, message: String) = log(Level.WARNING, context, message)
	fun error(context: LogContext?, message: String) = log(Level.ERROR, context, message)
	fun critical(context: LogContext?, message: String) = log(Level.CRITICAL, context, message)

	fun trace(context: LogContext?, vararg message: Any?) {
		if (Level.TRACE >= level) log(Level.TRACE, context, message.joinToString(" "))
	}
	fun debug(context: LogContext?, vararg message: Any?) {
		if (Level.DEBUG >= level) log(Level.DEBUG, context, message.joinToString(" "))
	}
	fun info(context: LogContext?, vararg message: Any?) {
		if (Level.INFO >= level) log(Level.INFO, context, message.joinToString(" "))
	}
	fun warn(context: LogContext?, vararg message: Any?) {
		if (Level.WARNING >= level) log(Level.WARNING, context, message.joinToString(" "))
	}
	fun error(context: LogContext?, vararg message: Any?) {
		if (Level.ERROR >= level) log(Level.ERROR, context, message.joinToString(" "))
	}
	fun critical(context: LogContext?, vararg message: Any?) {
		if (Level.CRITICAL >= level) log(Level.CRITICAL, context, message.joinToString(" "))
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

