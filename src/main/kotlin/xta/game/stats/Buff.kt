package xta.game.stats

import xta.net.serialization.JsonSerializer
import xta.utils.jsobject
import kotlin.js.Json

class Buff(
	val stat: BuffableStat,
	val tag: String,
	var value: Double,
	var text: String,
	val rate: Rate,
	var ticks:Int,
	val save: Boolean = true,
	val show: Boolean = true
) {

	val isNatural: Boolean
		get() = tag.startsWith("perk_") || tag in NATURAL_TAGS

	enum class Rate(val cocID:Int) {
		PERMANENT(0),
		ROUNDS(1),
		HOURS(2),
		DAYS(3);

		companion object {
			fun byID(id:Int) = values().find { it.cocID == id }
		}
	}

	companion object {
		val NATURAL_TAGS = setOf("Racials","Alchemical","Mutagen","Knowledge")

		fun serializer(stat: BuffableStat) = object: JsonSerializer<Buff> {
			override fun serializeObject(t: Buff) = jsobject<BuffJson> { json ->
				json.tag = t.tag
				json.value = t.value
				json.text = t.text
				json.rate = Rate.values().indexOf(t.rate) // TODO replace with ordinal in KT 1.6.20
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
					Rate.values()[j.rate],
					j.ticks,
					j.save?:true,
					j.show?:true
				)
			}
		}
	}
}

