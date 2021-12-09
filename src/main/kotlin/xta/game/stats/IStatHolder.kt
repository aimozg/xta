package xta.game.stats

interface IStatHolder {
	fun findStat(statName: String): IStat?

	fun findBuffableStat(statName: String): BuffableStat? {
		return when (val stat = findStat(statName)) {
			is BuffableStat -> stat
			is PrimaryStat -> stat.bonus
			else -> null
		}
	}

	fun removeBuffs(tag:String) {
		for (stat in allStats()) {
			if (stat is BuffableStat) {
				stat.removeBuff(tag)
			} else if (stat is IStatHolder) {
				stat.removeBuffs(tag)
			}
		}
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

