package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable

/*
 * Created by aimozg on 28.11.2021.
 */

class SkinPart(val host: Creature): JsonSerializable() {
	enum class Coverage(
		val cocID:Int
	) {
		NONE(0),
		LOW(1),
		MEDIUM(2),
		HIGH(3),
		COMPLETE(4);

		companion object {
			fun byId(id: Int) = values().find { it.cocID == id }
		}
	}

	var coverage by property(Coverage.NONE)
	var baseColor by property("")
	var baseColor2 by property("")
	var baseType by property(SkinBaseType.PLAIN)
	var coatColor by property("")
	var coatColor2 by property("")
	var coatType by property(SkinCoatType.FUR)

	val color get() = skinValue(baseColor, coatColor)

	val skinColor get() = baseColor
	val skinColor2 get() = baseColor2
	val furColor get() = coatColor
	val furColor2 get() = coatColor2
	val chitinColor get() = coatColor
	val chitinColor2 get() = coatColor2
	val scaleColor get() = coatColor
	val scaleColor2 get() = coatColor2

	private fun skinValue(baseVal:String, coatVal:String) = when (coverage) {
		Coverage.NONE,
		Coverage.LOW ->
			baseVal
		Coverage.MEDIUM ->
			"$baseVal and $coatVal"
		Coverage.HIGH,
		Coverage.COMPLETE ->
			coatVal
	}

	fun hasCoat() = coverage != Coverage.NONE

	fun hasCoatOfType(coatType: SkinCoatType): Boolean =
		hasCoat() && this.coatType == coatType
}

