package xta.charview

import js.Object
import org.khronos.webgl.Uint32Array
import org.khronos.webgl.get
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.Image
import org.w3c.dom.ImageData
import xta.logging.LogManager
import kotlin.js.Date
import kotlin.js.Json
import kotlin.js.json

/*
 * Created by aimozg on 02.12.2021.
 */
open class CompositeImage(val width:Int, val height:Int) {
	// sparse array of rgb->rgb
	val keyColors:dynamic = js("([])")
	val partsByName = HashMap<String,CompositePart>()
	val partsLayered = HashMap<String,HashMap<String,CompositePart>>()
	val partsOrdered = ArrayList<CompositePart>()

	class CompositePart(
		val layerName:String,
		val partName:String,
		val canvas: HTMLCanvasElement,
		val data:ImageData,
		val dx:Double,
		val dy:Double
	) {
		private var index: Json? = null
		fun recolor(keyColors: dynamic): HTMLCanvasElement {
			val index = index
			if (index == null || Object.keys(keyColors).any {
					index[it] == true
				}) {
				return data.recolor(keyColors).toCanvas()
			}
			return canvas
		}

		fun indexColors() {
			val bytes = Uint32Array(data.data.buffer)
			val index = json()
			for (i in 0 until bytes.length) {
				val abgr = bytes[i]
				val alpha = abgr shr 24
				if (alpha != 0) {
					val bgr = abgr and 0x00ffffff
					index[bgr.toString()] = true
				}
			}
			this.index = index
		}

		val fullName = "$layerName/$partName"
		var visible = true
	}

	fun loadPartsFromJson(
		sprites:Array<SpriteJson>,
		images:Map<String, Image>,
		originX:Int,
		originY:Int
	) {
		for (sprite in sprites) {
			logger.debug(null,"Loading image",sprite.file)
			val image = images[sprite.file] ?: error("Missing file ${sprite.file}")
			for (cell in sprite.cells) {
				val cellWidth = cell.rect[2]
				val cellHeight = cell.rect[3]
				val c2d = createContext2D(cellWidth,cellHeight)
				val dx = cell.dx-originX
				val dy = cell.dy-originY
				c2d.drawImage(image,
					cell.rect[0]+0.0, cell.rect[1]+0.0, cellWidth+0.0, cellHeight+0.0,
					0.0, 0.0, cellWidth+0.0, cellHeight+0.0
				)
				loadPart(cell.part,
					c2d.canvas,
					c2d.getImageData(0.0, 0.0, cellWidth+0.0, cellHeight+0.0),
					dx+0.0, dy+0.0)
			}
		}
	}
	fun loadPart(name:String, canvas: HTMLCanvasElement, data:ImageData, dx:Double, dy:Double) {
		val (layerName,partName) = name.split("/")
		val part = CompositePart(layerName, partName, canvas, data, dx, dy)
		part.indexColors()
		partsByName[name] = part
		partsLayered.getOrPut(layerName){HashMap()}[partName] = part
	}
	fun setKeyColor(rgb:Int, replacement:Color) {
		keyColors[rgb2bgr(rgb)] = rgb2bgr(replacement.rgb)
	}
	fun layer(layerName:String): Collection<CompositePart> =
		partsLayered[layerName]?.values?: emptyList()
	fun setLayerOrder(vararg layerNames:String) {
		partsOrdered.clear()
		for (layerName in layerNames.reversed()) {
			for (part in layer(layerName)) {
				partsOrdered.add(part)
			}
		}
	}
	fun showPart(fullName:String) {
		partsByName[fullName]?.visible = true
	}
	fun hidePart(fullName:String) {
		partsByName[fullName]?.visible = false
	}

	fun hasVisibleParts(layerName:String): Boolean =
		layer(layerName).any { it.visible }
	fun hideLayer(layerName:String) {
		for (part in layer(layerName)) {
			part.visible = false
		}
	}
	fun hideAll() {
		for (part in partsByName.values) {
			part.visible = false
		}
	}

	fun compose():CanvasRenderingContext2D {
		logger.debug(null,"Composing image")
		val t0 = Date().getTime()
		var n = 0
		val canvas = createContext2D(width, height)
		for (part in partsOrdered) {
			if (!part.visible) continue
			n++
			logger.trace(null,"Adding part",part.fullName)
			canvas.drawImage(
				part.recolor(keyColors),
				part.dx,
				part.dy
			)
		}
		logger.debug(null,"Rendered $n layers in ${Date().getTime()-t0} ms")
		return canvas
	}

	companion object {
		private val logger = LogManager.getLogger("xta.charview.CompositeImage")
	}
}

