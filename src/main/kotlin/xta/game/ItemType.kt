package xta.game

import xta.utils.capitalized

/*
 * Created by aimozg on 18.12.2021.
 */
@JsExport
abstract class ItemType(
	val id:String,
	val name:String
) {
	val buffTag get() = "item_$id"

	abstract fun description(wielder: PlayerCharacter?): String
	open fun tooltipHtml(wielder:PlayerCharacter?) = buildString {
		append("<b>")
		append(name.capitalized())
		append("</b>\n\n")
		append(description(wielder))
	}

	init {
		BY_ID[id] = this
	}
	companion object {
		val BY_ID = HashMap<String,ItemType>()
	}
}
