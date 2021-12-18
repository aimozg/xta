package xta.game.stats

import xta.net.serialization.JsonSerializer
import xta.utils.jsobject
import kotlin.js.Json

@JsExport
class Buff(
	stat: BuffableStat,
	tag: String,
	override var value: Double,
	text: String,
	rate: BuffRate,
	ticks:Int,
	val save: Boolean = true,
	show: Boolean = true
): IBuff(stat, tag, text, rate, ticks, show) {

	companion object {

		fun serializer(stat: BuffableStat) = object: JsonSerializer<Buff> {
			override fun serializeObject(t: Buff) = jsobject<BuffJson> { json ->
				json.tag = t.tag
				json.value = t.value
				json.text = t.text
				json.rate = BuffRate.values().indexOf(t.rate) // TODO replace with ordinal in KT 1.6.20
				json.ticks = t.ticks
				json.save = t.save
				json.show = t.show
			}

			override fun deserializeObject(j: Json): Buff {
				@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
				j as BuffJson
				return Buff(
					stat,
					j.tag,
					j.value,
					j.text,
					BuffRate.values()[j.rate],
					j.ticks,
					j.save?:true,
					j.show?:true
				)
			}
		}
	}
}

