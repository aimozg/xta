package xta.game.stats

import js.jspush
import xta.game.Creature
import xta.game.creature.PerkType
import xta.net.serialization.IJsonSerializable
import xta.text.toNiceString
import xta.utils.buildJson
import xta.utils.walk
import kotlin.js.Json

@JsExport
open class BuffableStat(
	val host: Creature,
	override val statName: String,
	val aggregate: BuffAggregate = BuffAggregate.SUM,
	val baseValue: Double = aggregate.defaultBase,
	open val min: Double = Double.NEGATIVE_INFINITY,
	open val max: Double = Double.POSITIVE_INFINITY
) : IJsonSerializable, IStat {
	@JsName("constructByMeta")
	constructor(
		host: Creature,
		meta: StatMeta,
		aggregate: BuffAggregate = BuffAggregate.SUM,
		baseValue: Double = aggregate.defaultBase,
		min: Double = Double.NEGATIVE_INFINITY,
		max: Double = Double.POSITIVE_INFINITY
	): this(host, meta.id, aggregate, baseValue, min, max)

	// TODO optimization note: keep 2 arrays for two types, cache static buffs aggregate
	private val buffs = ArrayList<IBuff>()
	private var hasDynbuffs = false

	override fun deserializeFromJson(input: dynamic) {
		@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
		input as Json
		buffs.removeAll { it !is DynamicBuff || !it.persistent }
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
			val jbuffs = arrayOf<dynamic>()
			for (buff in buffs) {
				if (buff !is Buff || !buff.save) continue
				jbuffs.jspush(bsz.serializeObject(buff))
			}
			json["buffs"] = jbuffs
		}
	}

	private var dirty = false
	private var cachedValue = baseValue
	override val value: Double
		get() {
			if (dirty || hasDynbuffs) recalculate()
			return cachedValue
		}

	fun recalculate() {
		cachedValue = when (aggregate) {
			BuffAggregate.SUM ->
				baseValue + buffs.sumOf { it.value }
			BuffAggregate.PRODUCT ->
				buffs.fold(baseValue) { acc, buff -> acc * buff.value }
			BuffAggregate.MAX ->
				maxOf(baseValue, buffs.maxOfOrNull { it.value } ?: baseValue)
			BuffAggregate.MIN ->
				minOf(buffs.minOfOrNull { it.value } ?: baseValue)
		}.coerceIn(min, max)
		dirty = false
	}

	fun indexOfBuff(tag: String) = buffs.indexOfFirst { it.tag == tag }

	fun dynamicBuff(
		tag: String,
		text: String = tag,
		rate: BuffRate = BuffRate.PERMANENT,
		ticks: Int = 0,
		show: Boolean = true,
		persistent: Boolean = false,
		valueFn: (creature:Creature)->Double
	) {
		val i = indexOfBuff(tag)
		val dbuff =DynamicBuff(this,tag,valueFn, text, rate, ticks, show, persistent)
		if (i == -1) {
			buffs.add(dbuff)
		} else {
			buffs[i] = dbuff
		}
		hasDynbuffs = true
		dirty = true
	}
	fun addOrReplaceBuff(
		tag: String,
		value: Double,
		text: String = tag,
		rate: BuffRate = BuffRate.PERMANENT,
		ticks: Int = 0,
		save: Boolean = true,
		show: Boolean = true
	) {
		val i = indexOfBuff(tag)
		if (value == 0.0 && aggregate == BuffAggregate.SUM) {
			if (i != -1) {
				buffs.removeAt(i)
				dirty = true
			}
		} else {
			val buff = Buff(this, tag, value, text, rate, ticks, save, show)
			if (i == -1) {
				buffs.add(buff)
			} else {
				buffs[i] = buff
			}
			dirty = true
		}
	}

	fun removeBuff(tag:String) {
		val i = indexOfBuff(tag)
		if (i == -1) return
		buffs.removeAt(i)
		hasDynbuffs = buffs.any { it is DynamicBuff }
		dirty = true
	}

	fun removeCombatBuffs() {
		buffs.walk { iterator, buff ->
			if (buff.rate == BuffRate.ROUNDS) iterator.remove()
		}
		dirty = true
	}

	fun explainBuffs(
		asPercentage: Boolean,
		htmlFormat:Boolean = true,
		includeHidden: Boolean = false,
		groupPerks: Boolean = true,
		isGood: Boolean = true
	) = buildString {
		var perkBuff = 0.0
		for (buff in buffs) {
			val x = buff.value
			if (groupPerks && buff.tag.startsWith(PerkType.BUFF_TAG_PREFIX)) {
				perkBuff += x
				continue
			}
			if (!includeHidden && !buff.show) continue
			if (asPercentage && 0.0 <= x && x < 0.01) continue
			if (!asPercentage && 0.0 <= x && x < 1.0) continue
			explainBuff(buff.text, x, htmlFormat, asPercentage, isGood)
		}
		if (perkBuff != 0.0) {
			explainBuff("Perks", perkBuff, htmlFormat, asPercentage, isGood)
		}
	}

	fun hasNegativeBuffs(): Boolean {
		return buffs.any { !it.isNatural && it.value < 0 }
	}

	fun hasPositiveBuffs(): Boolean {
		return buffs.any { !it.isNatural && it.value > 0 }
	}

	companion object {
		fun StringBuilder.explainBuff(
			text: String,
			value: Double,
			htmlFormat: Boolean,
			asPercentage: Boolean,
			isGood: Boolean
		) {
			if (htmlFormat) {
				append("<div class='buff ")
				append(if (isGood) "buff-good" else "buff-bad")
				append(" ")
				append(if (value > 0) "buff-gt0" else "buff-lt0")
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
