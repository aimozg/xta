package xta.charview

import kotlinx.browser.document
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.ImageData

/*
 * Created by aimozg on 02.12.2021.
 */
fun createContext2D(width:Int,height:Int):CanvasRenderingContext2D {
	val canvas = (document.createElement("canvas") as HTMLCanvasElement)
	canvas.width = width
	canvas.height = height
	val c2d = canvas.getContext("2d") as CanvasRenderingContext2D
	c2d.imageSmoothingEnabled = false
	return c2d
}

fun ImageData.toCanvas():HTMLCanvasElement {
	val c2d = createContext2D(width, height)
	c2d.putImageData(this,0.0,0.0)
	return c2d.canvas
}

fun rgb2bgr(rgb:Int):Int {
	val r = (rgb shr 16) and 0xff
	val g = (rgb shr 8) and 0xff
	val b = rgb and 0xff
	return (b shl 16) or (g shl 8) or r
}
