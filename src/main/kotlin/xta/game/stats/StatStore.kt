package xta.game.stats

class StatStore : IStatHolder {

	val stats = HashMap<String, IStat>()

	fun registerStat(stat: IStat) {
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

	fun addOrReplaceBuff(
		statName:String,
		tag:String,
		value:Double,
		text:String = tag,
		rate: BuffRate = BuffRate.PERMANENT,
		ticks: Int = 0,
		save: Boolean = true,
		show: Boolean = true
	) {
		val stat = findBuffableStat(statName) ?: error("Stat does not exist '$statName'")
		stat.addOrReplaceBuff(tag,value,text,rate,ticks,save,show)
	}
	fun addOrReplaceBuff(
		meta:StatMeta,
		tag:String,
		value:Double,
		text:String = tag,
		rate: BuffRate = BuffRate.PERMANENT,
		ticks: Int = 0,
		save: Boolean = true,
		show: Boolean = true
	) {
		addOrReplaceBuff(meta.id, tag, value, text, rate, ticks, save, show)
	}
}
