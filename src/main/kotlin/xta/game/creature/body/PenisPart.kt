package xta.game.creature.body

import xta.net.serialization.JsonSerializable

/*
 * Created by aimozg on 28.11.2021.
 */
class PenisPart: JsonSerializable() {
	var type: PenisType by property(PenisType.HUMAN)
	var length: Int by property(0)
	var thickness: Int by property(0)
}

