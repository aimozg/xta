@file:JsModule("import/AMF3.js")
@file:JsNonModule()

package minerva

import org.khronos.webgl.ArrayBuffer

/*
 * Created by aimozg on 28.11.2021.
 */

external interface AMF3Traits {
	var type:String
}

external interface AMF3ArrayTraits: AMF3Traits {
	/**
	 * Contains only ordinal (numeric) indices
	 */
	var strict: Boolean?
}
external interface AMF3ObjectTraits: AMF3Traits {
	/**
	 * Class name. "Object" for plain json object
	 */
	var `class`:String

	/**
	 * Class member names
	 */
	var members:Array<String>

	/**
	 * Class member count
	 */
	var count:Int

	/**
	 * an instance of a Class that implements flash.utils.IExternalizable and completely controls the
	 * serialization of its members (no property names are included in the trait information).
	 */
	var externalizable: Boolean?

	/**
	 * an instance of a Class definition with the dynamic trait declared; public variable members can be added
	 *  and removed from instances dynamically at runtime
	 */
	var `dynamic`: Boolean?
}

external interface AMF3WrappedValue {
	val value:dynamic
	val __traits: AMF3Traits
}

external class AMF3 {
	fun readData(a: MinervaByteArray): AMF3WrappedValue
}
