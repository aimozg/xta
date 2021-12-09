package xta.game.stats

class StatStore : IStatHolder {

	val stats = HashMap<String, IStat>()

	fun addStat(stat: IStat) {
		stats[stat.statName] = stat
	}

	override fun findStat(statName: String): IStat? {
		stats[statName]?.let { return it }
		val prefix = statName.substringBefore('.')
		return (stats[prefix] as? IStatHolder)?.findStat(statName)
	}

	override fun allStatNames(): Collection<String> {
		return stats.keys
	}

	override fun allStats(): Collection<IStat> {
		return stats.values
	}

}
