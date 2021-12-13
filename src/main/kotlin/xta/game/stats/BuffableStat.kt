package xta.game.stats

import xta.game.creature.PerkType
import xta.net.serialization.IJsonSerializable
import xta.text.toNiceString
import xta.utils.buildJson
import xta.utils.mapToDynamicArray
import xta.utils.walk
import kotlin.js.Json

open class BuffableStat(
	override val statName: String,
	val aggregate: Aggregate = Aggregate.SUM,
	val baseValue: Double = aggregate.defaultBase,
	open val min: Double = Double.NEGATIVE_INFINITY,
	open val max: Double = Double.POSITIVE_INFINITY
) : IJsonSerializable, IStat {
	val buffs = ArrayList<Buff>()

	override fun deserializeFromJson(input: dynamic) {
		@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
		input as Json
		buffs.clear()
		val bsz = Buff.serializer(this)
		@Suppress("UNCHECKED_CAST")
		for (jbuff in input["buffs"] as Array<Json>) {
			buffs.add(bsz.deserializeObject(jbuff))
		}
		dirty = true
	}

	override fun serializeToJson(): Json {
		val bsz = Buff.serializer(this)
		return buildJson { json ->
			json["buffs"] = buffs.filter { it.save }.mapToDynamicArray { bsz.serializeObject(it) }
		}
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
		val buff = Buff(this, tag, value, text, rate, ticks, save, show)
		if (i == -1) {
			buffs.add(buff)
		} else {
			buffs[i] = buff
		}
		dirty = true
	}

	fun removeBuff(tag:String) {
		val i = indexOfBuff(tag)
		if (i == -1) return
		buffs.removeAt(i)
		dirty = true
	}

	fun removeCombatBuffs() {
		buffs.walk { iterator, buff ->
			if (buff.rate == Buff.Rate.ROUNDS) iterator.remove()
		}
		dirty = true
	}

	fun explainBuffs(
		asPercentage: Boolean,
		htmlFormat:Boolean = true,
		includeHidden: Boolean = false,
		groupPerks: Boolean = true
	) = buildString {
		var perkBuff = 0.0
		for (buff in buffs) {
			val x = buff.value
			if (groupPerks && buff.tag.startsWith(PerkType.BUFF_TAG_PREFIX)) {
				perkBuff += x
				continue
			}
			if (!includeHidden && !buff.show) continue
			if (x in 0.0..0.01) continue
			explainBuff(buff.text, x, htmlFormat, asPercentage)
		}
		if (perkBuff != 0.0) {
			explainBuff("Perks", perkBuff, htmlFormat, asPercentage)
		}
	}

	enum class Aggregate(val defaultBase: Double) {
		SUM(0.0),
		PRODUCT(1.0),
		MAX(Double.NEGATIVE_INFINITY),
		MIN(Double.POSITIVE_INFINITY)
	}

	companion object {
		private fun StringBuilder.explainBuff(
			text: String,
			value: Double,
			htmlFormat: Boolean,
			asPercentage: Boolean
		) {
			if (htmlFormat) {
				append("<div class='buff ")
				if (value > 0) {
					append("buff-gt0")
				} else {
					append("buff-lt0")
				}
				append("'>")
				append("<span class='buff-name'>")
				append(text)
				append("</span>: <span class='buff-value'>")
				if (value > 0) append("+")
				if (asPercentage) {
					append(value.times(100).toInt().toString())
					append("%")
				} else {
					append(value.toNiceString(1))
				}
				append("</span></div>")
			} else {
				append(text)
				append(": ")
				if (value > 0) append("+")
				if (asPercentage) {
					append(value.times(100).toInt().toString())
					append("%")
				} else {
					append(value.toNiceString(1))
				}
				append("\n")
			}
		}
	}
}
