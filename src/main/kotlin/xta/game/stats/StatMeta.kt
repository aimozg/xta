package xta.game.stats

@JsExport
class StatMeta(
	val id:String,
	val displayName:String,
	val fullName:String = displayName,
	val isPercentage:Boolean = false,
	val isGood:Boolean = true
) {
	init {
		BY_ID[id] = this
	}
	companion object {
		val BY_ID = HashMap<String,StatMeta>()
	}
}
