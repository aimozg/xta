package xta.game.creature

import xta.net.serialization.IJsonSerializable

class PcKnowledge(
	private val set: MutableSet<String> = HashSet()
): IJsonSerializable, MutableSet<String> by set {
	override fun serializeToJson(): Array<String> = set.toTypedArray()

	override fun deserializeFromJson(input: dynamic) {
		set.clear()
		for (id in input as Array<String>) {
			set.add(id)
		}
	}
}
