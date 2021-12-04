package xta.charview

import org.khronos.webgl.Uint32Array
import org.khronos.webgl.get
import org.khronos.webgl.set
import org.w3c.dom.ImageData

/*
 * Created by aimozg on 02.12.2021.
 */

/**
 * @param mapping Sparse array or object, mapping BBGGRR int colors
 */
fun ImageData.recolor(mapping:dynamic):ImageData {
	val dst = ImageData(width,height)
	val sarr = Uint32Array(data.buffer)
	val darr = Uint32Array(dst.data.buffer)
	for (i in 0 until darr.length) {
		val sabgr = sarr[i]
		val sbgr = sabgr and 0x00ffffff
		val sa = sabgr and 0xff000000L.toInt()
		val replacement = mapping[sbgr]
		if (replacement === undefined) {
			darr[i] = sabgr
		} else {
			darr[i] = sa or (replacement as Int)
		}
	}
	return dst
}
