package xta.game.creature.body

import xta.game.Creature
import xta.utils.either
import xta.utils.fxrng

/*
 * Created by aimozg on 28.11.2021.
 */
enum class HairType(
	val cocID: Int,
	val displayName: String,
	val ignoresStyle: Boolean = false
) {
	NORMAL(0, "normal") {
		override fun appearanceDesc(creature: Creature) =
			"Although not particularly remarkable, your ${hairInfo(creature)} looks good on you, accentuating your features well."
	};

	abstract fun appearanceDesc(creature: Creature): String

	protected open fun hairInfo(creature: Creature): String {
		//If furry and longish hair sometimes call it a mane (50%)
		val hair: String =
			if (creature.hasFur() && creature.hairLength > 3 && fxrng.nextInt(2) == 0) "mane"
			else "hair"

		return (if (ignoresStyle) "" else creature.hairStyle.adjective + " ") +
				getHairLength(creature) + " " + creature.hairColor + " " + hair

	}

	protected fun getHairLength(creature: Creature): String {
		val hairScale = ((creature.hairLength / creature.tallness) * 100).toInt() / 100.0
		return when {
			hairScale == 0.0 ->
				fxrng.either("shaved", "bald", "smooth", "hairless", "glabrous")
			hairScale <= 0.05 ->
				fxrng.either("close-cropped", "trim", "very short")
			hairScale <= 0.1 ->
				"short"
			hairScale <= 0.14 ->
				"shaggy"
			hairScale <= 0.17 ->
				"moderately long"
			hairScale <= 0.2 ->
				fxrng.either("long", "shoulder-length")
			hairScale <= 0.25 ->
				"very long"
			hairScale <= 0.4 ->
				"back-covering"
			hairScale <= 0.5 ->
				"ass-length"
			hairScale <= 1.0 ->
				"obscenely long"
			else ->
				"floor-length"
		}
	}

	companion object {
		fun byId(id:Int) = values().find { it.cocID == id }
	}
}
