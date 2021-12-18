package xta.game

/*
 * Created by aimozg on 18.12.2021.
 */
@JsExport
abstract class ItemType(
	val id:String,
	val name:String
) {
	val buffTag get() = "item_$id"
	init {
		BY_ID[id] = this
	}
	companion object {
		val BY_ID = HashMap<String,ItemType>()
	}
}
