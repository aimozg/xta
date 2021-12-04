package xta.logging

object LogManager : ILogManager {
	private val loggers = HashMap<String, Logger>()
	private val levelSettings = ArrayList<Pair<String, Logger.Level>>()

	override fun getLogger(id: String): Logger {
		return loggers.getOrPut(id) {
			// TODO logger factory?
			ConsoleLogger(id).apply {
				for ((prefix,level) in levelSettings) {
					if (id.startsWith(prefix)) this.level = level
				}
			}
		}
	}

	override fun setLevel(id: String, level: Logger.Level) {
		levelSettings.removeAll { it.first == id }
		levelSettings.add(id to level)
		loggers[id]?.level = level
	}

	override fun setLevelForMany(prefix: String, level: Logger.Level) {
		levelSettings.removeAll { it.first == prefix }
		levelSettings.add(prefix to level)
		for ((id, logger) in loggers) {
			if (id.startsWith(prefix)) logger.level = level
		}
	}
}

