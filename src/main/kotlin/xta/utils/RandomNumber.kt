package xta.utils

import kotlin.random.Random

@JsExport
class RandomNumber(val from:Double, val until:Double) {
	fun roll(rng: Random = gamerng): Double = rng.nextDouble(from, until)
	fun mean() = (from+until)/2

	operator fun plus(other:RandomNumber) =
		RandomNumber(from+other.from, until+other.until)
	@JsName("plusNumber")
	operator fun plus(other:Double) =
		RandomNumber(from+other, until+other)

	companion object {
		fun constant(value:Double) = RandomNumber(value, value)
	}
}
