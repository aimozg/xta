package xta.game.stats

import xta.net.serialization.JsonSerializable
import xta.net.serialization.VariableHolder

open class RawStat(
	override val statName: String,
	startValue: Double = 0.0,
	open val min: Double = Double.NEGATIVE_INFINITY,
	open val max: Double = Double.POSITIVE_INFINITY
): JsonSerializable(), IStat {

	override var value: Double by doubleProperty("value",object:VariableHolder<Double>(startValue) {
		override fun set(value: Double) {
			super.set(value.coerceIn(min, max))
		}
	})

	/**
	 * TODO move this, min, max to IStat?
	 * A value relative to (min-max) bounds, to restore proportion after changing them.
	 * Has special cases for infinite bounds:
	 * * value = 50 in (-100, +100): `relativeValue` = 0.75 = (value+min)/(max+min)
	 * * value = 50 in (-100, +∞): `relativeValue` = +150 = value+min
	 * * value = 50 in (-∞, +100): `relativeValue` = -50 = value-max
	 * * value = 50 in (-∞, +∞): `relativeValue` = 50 = value
	 */
	var relativeValue: Double
		get() {
			val range = max + min
			return when {
				range.isFinite() -> (value + min) / (max + min)
				min.isFinite() -> value + min
				max.isFinite() -> value - max
				else -> value
			}
		}
		set(value) {
			val range = max + min
			this.value =
				when {
					range.isFinite() -> (value - min) * (max + min)
					min.isFinite() -> value - min
					max.isFinite() -> value + max
					else -> value
				}
		}

	/**
	 * Value scaled by 1/[max], for representation as a progress/percentage
	 */
	var zeroRelative: Double
		get() = value / max
		set(value) {
			this.value = value * max
		}
}
