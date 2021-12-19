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

	abstract val description: String
	open val tooltipHtml: String
	get() = buildString {
		append("<b>")
		append(name.capitalized())
		append("</b>\n\n")
		append(description)
	}

	init {
		BY_ID[id] = this
	}
	companion object {
		val BY_ID = HashMap<String,ItemType>()
	}
}
