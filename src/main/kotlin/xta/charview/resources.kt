@file:Suppress("UnsafeCastFromDynamic")

package xta.charview

import org.w3c.dom.Image

/*
 * Created by aimozg on 02.12.2021.
 */

// These are processed at compile-time by webpack and are limited to simple require-something code
// Do not attempt to wrap in a loop or similar

val palette_json: Array<PaletteJson> = js("""require("import/charview/palette.json")""")
val sprites_json: Array<SpriteJson> = js("""require("import/charview/sprites.json")""")

val body_png_dataurl:String = js("""require("import/charview/body.png")""")
val extra_png_dataurl:String = js("""require("import/charview/extra.png")""")
val hair_png_dataurl:String = js("""require("import/charview/hair.png")""")
val head_png_dataurl:String = js("""require("import/charview/head.png")""")
val lewd_png_dataurl:String = js("""require("import/charview/lewd.png")""")
val tails_png_dataurl:String = js("""require("import/charview/tails.png")""")
val wings_png_dataurl:String = js("""require("import/charview/wings.png")""")

val charviewImages: Map<String, Image> = mapOf(
		"body.png" to body_png_dataurl,
		"extra.png" to extra_png_dataurl,
		"hair.png" to hair_png_dataurl,
		"head.png" to head_png_dataurl,
		"lewd.png" to lewd_png_dataurl,
		"tails.png" to tails_png_dataurl,
		"wings.png" to wings_png_dataurl
	).mapValues { (_, url) ->
		Image().also { it.src = url }
}

external interface SpriteJson {
	var file:String
	var cells: Array<SpriteCellJson>
}

external interface SpriteCellJson {
	var part:String
	var rect:Array<Int>
	var dx:Int
	var dy:Int
}

external interface PaletteJson {
	var name:String
	var colors:Array<PaletteColorJson>
}
external interface PaletteColorJson {
	var name:String
	var rgb:String
}
