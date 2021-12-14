package xta.game.creature

import xta.game.Creature
import xta.logging.LogManager
import xta.net.serialization.IJsonSerializable
import xta.utils.mapToArray

/*
 * Created by aimozg on 10.12.2021.
 */
class PerkManager(
	val host:Creature,
	private val set: HashSet<PerkType> = HashSet()
):
	IJsonSerializable,MutableSet<PerkType> by set
{

	fun addPerk(perk: PerkType) {
		if (set.add(perk)) {
			perk.onAdd(host)
		}
	}

	/**
	 * Add perk that is guaranteed to be not present (ex. when loading a save)
	 */
	fun loadPerk(perk: PerkType) {
		set.add(perk)
		perk.onAdd(host)
	}

	/**
	 * Add perk that is guaranteed to be not present (ex. when loading a save)
	 */
	fun loadPerk(id: String) {
		val perk = PerkType.BY_ID[id]
		if (perk == null) {
			logger.warn(null,"Unknown perk id", id)
		} else {
			loadPerk(perk)
		}
	}

	override fun serializeToJson(): Array<String> {
		return set.mapToArray { it.id }
	}

	override fun deserializeFromJson(input: dynamic) {
		set.clear()
		for (id in input as Array<String>) {
			loadPerk(id)
		}
	}

	companion object {
		private val logger = LogManager.getLogger("xta.game.creature.PerkManager")
	}
}
