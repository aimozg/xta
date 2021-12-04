package xta.game

import xta.text.Display

abstract class GameLocation(val id:String) {
	fun scene(suffix:String,updateOnVisit: Boolean=false,body: Display.()->Unit) =
		SimpleScene("$id/$suffix", updateOnVisit, body)
}
