@file:JsModule("import/ByteArray.js")
@file:JsNonModule()

package minerva

import org.khronos.webgl.ArrayBuffer

/*
 * Created by aimozg on 04.12.2021.
 */


@JsName("ByteArray")
external class MinervaByteArray {
	constructor(data: ArrayBuffer)
	constructor(data: ArrayBuffer, endian: String)

	companion object {
		val BIG_ENDIAN: String
		val LITTLE_ENDIAN: String
	}
}
