package xta.game.stats

import xta.net.serialization.JsonSerializable
import kotlin.js.Json

open class BuffPool(
	override val statName: String,
	val aggregate: Aggregate = Aggregate.SUM,
	val baseValue: Double = aggregate.defaultBase,
	open val min: Double = Double.NEGATIVE_INFINITY,
	open val max: Double = Double.POSITIVE_INFINITY
) : JsonSerializable(), IStat {
	val buffs: MutableList<Buff> by nestedList(Buff.serializer(this))

	override fun deserializeFromJson(input: Json) {
		super.deserializeFromJson(input)
		dirty = true
	}

	private var dirty = false
	private var cachedValue = baseValue
	override val value: Double
		get() {
			if (dirty) recalculate()
			return cachedValue
		}

	fun recalculate() {
		cachedValue = when (aggregate) {
			Aggregate.SUM ->
				baseValue + buffs.sumOf { it.value }
			Aggregate.PRODUCT ->
				buffs.fold(baseValue) { acc, buff -> acc * buff.value }
			Aggregate.MAX ->
				maxOf(baseValue, buffs.maxOfOrNull { it.value } ?: baseValue)
			Aggregate.MIN ->
				minOf(buffs.minOfOrNull { it.value } ?: baseValue)
		}.coerceIn(min, max)
		dirty = false
	}

	fun indexOfBuff(tag: String) = buffs.indexOfFirst { it.tag == tag }

	fun addOrReplaceBuff(
		tag: String,
		value: Double,
		text: String = tag,
		rate: Buff.Rate = Buff.Rate.PERMANENT,
		ticks: Int = 0,
		save: Boolean = true,
		show: Boolean = true
	) {
		val i = indexOfBuff(tag)
		val buff = Buff(
			this,
			tag,
			value,
			text,
			rate,
			ticks,
			save,
			show
		)
		if (i == -1) {
			buffs.add(buff)
		} else {
			buffs[i] = buff
		}
		dirty = true
	}

	enum class Aggregate(val defaultBase: Double) {
		SUM(0.0),
		PRODUCT(1.0),
		MAX(Double.NEGATIVE_INFINITY),
		MIN(Double.POSITIVE_INFINITY)
	}
}
