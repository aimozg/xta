package xta.charview

import xta.logging.LogManager
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.Image
import org.w3c.dom.ImageData

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
		val data:ImageData
	) {
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
				val c2d = createContext2D(width,height)
				val cellWidth = cell.rect[2] + 0.0
				val cellHeight = cell.rect[3] + 0.0
				c2d.drawImage(image,
					cell.rect[0]+0.0, cell.rect[1]+0.0, cellWidth, cellHeight,
					cell.dx-originX+0.0, cell.dy-originY+0.0, cellWidth, cellHeight
				)
				loadPart(cell.part, c2d.getImageData(0.0, 0.0, width+0.0, height+0.0))
			}
		}
	}
	fun loadPart(name:String, data:ImageData) {
		val (layerName,partName) = name.split("/")
		val part = CompositePart(layerName, partName, data)
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
	fun showLayer(layerName:String) {
		for (part in layer(layerName)) {
			part.visible = true
		}
	}
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
		val canvas = createContext2D(width, height)
		for (part in partsOrdered) {
			if (!part.visible) continue
			logger.debug(null,"Adding part",part.fullName)
			canvas.drawImage(part.data.recolor(keyColors).toCanvas(),0.0,0.0)
		}
		return canvas
	}

	companion object {
		private val logger = LogManager.getLogger("xta.charview.CompositeImage")
	}
}

