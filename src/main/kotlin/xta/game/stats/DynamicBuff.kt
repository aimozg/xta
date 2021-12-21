package xta.game.stats

import xta.game.Creature

@JsExport
class DynamicBuff(
	stat: BuffableStat,
	tag: String,
	val valueFn: (creature:Creature)->Double,
	text: String = tag,
	rate: BuffRate = BuffRate.PERMANENT,
	ticks: Int = 0,
	show: Boolean = true,
	/**
	 * This buff is always present and should not be removed
	 */
	val persistent: Boolean = false
) : IBuff(stat, tag, text, rate, ticks, show) {
	override val value: Double
		get() = valueFn(stat.host)
}
