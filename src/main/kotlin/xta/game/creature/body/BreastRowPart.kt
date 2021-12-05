package xta.game.creature.body

import xta.net.serialization.JsonSerializable

class BreastRowPart: JsonSerializable() {
	var breastRating:Int by property(BreastCup.FLAT)
}
