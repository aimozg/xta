package xta.game.stats

interface IStatHolder {
	fun findStat(statName: String): IStat? {
		for (stat in allStats()) {
			if (stat.statName == statName) return stat
			if (statName.startsWith(stat.statName + ".") && stat is IStatHolder) {
				return stat.findStat(statName)
			}
		}
		return null
	}

	fun allStatNames(): Collection<String> = allStats().map { it.statName }

	fun allStats(): Collection<IStat>

	fun allStatsAndSubstats(): Collection<IStat> {
		val result = ArrayList<IStat>()
		for (stat in allStats()) {
			result.add(stat)
			if (stat is IStatHolder) {
				result.addAll(stat.allStatsAndSubstats())
			}
		}
		return result
	}
}

