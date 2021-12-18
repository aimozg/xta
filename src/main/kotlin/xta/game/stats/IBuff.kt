package xta.game.stats

@JsExport
sealed class IBuff(
	val stat: BuffableStat,
	val tag: String,
	var text: String,
	val rate: BuffRate,
	var ticks:Int,
	val show: Boolean
) {
	abstract val value: Double
	val isNatural: Boolean
		get() = tag.startsWith("perk_") || tag in NATURAL_TAGS

	companion object {
		val NATURAL_TAGS = setOf("Racials","Alchemical","Mutagen","Knowledge")
	}
}

