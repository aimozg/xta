package xta.game.combat

import xta.game.Creature
import xta.net.serialization.JsonSerializer
import xta.text.numberOfThings
import xta.utils.buildJson

// TODO refactor. Custom effects can have own properties.
class StatusEffect(
	val host: Creature,
	val type: StatusType,
	var durationRounds: Int
) {
	val displayName: String get() = type.displayName
	val tooltipHtml get() = displayName+" ("+ numberOfThings(durationRounds, "round") +")"

	class Serializer(val host:Creature): JsonSerializer<StatusEffect> {
		override fun serializeObject(t: StatusEffect) = buildJson {
			it["id"] = t.type.id
			it["durationRounds"] = t.durationRounds
		}

		override fun deserializeObject(j: dynamic): StatusEffect =
			StatusEffect(host, StatusType.byId(j.id as String), j.durationRounds as Int)
	}
}
