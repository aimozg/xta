package minerva

import xta.utils.mapToDynamicArray
import js.Object

/*
 * Created by aimozg on 28.11.2021.
 */

/** value: null */
const val AMF3_TYPE_UNDEFINED = "Undefined"
/** value: null */
const val AMF3_TYPE_NULL = "Null"
/** value: 1 */
const val AMF3_TYPE_TRUE = "True"
/** value: 0 */
const val AMF3_TYPE_FALSE = "False"
/** value:number */
const val AMF3_TYPE_INTEGER = "Integer"
/** value:number */
const val AMF3_TYPE_NUMBER = "Number"
/** value:string */
const val AMF3_TYPE_STRING = "String"
/** value:string */
const val AMF3_TYPE_XMLDOCUMENT = "XMLDocument"
/** value:Date */
const val AMF3_TYPE_DATE = "Date"
/** value:Array */
const val AMF3_TYPE_ARRAY = "Array"
/** value:*/
const val AMF3_TYPE_OBJECT = "Object"

const val AMF3_OBJECT_CLASS_OBJECT = "Object"

@JsExport
fun unwrap(wrappedValue: AMF3WrappedValue): dynamic {
	val value = wrappedValue.value
	return when (val type = wrappedValue.__traits.type) {
		AMF3_TYPE_UNDEFINED,
		AMF3_TYPE_NULL,
		AMF3_TYPE_INTEGER,
		AMF3_TYPE_NUMBER,
		AMF3_TYPE_DATE,
		AMF3_TYPE_STRING ->
			value
		AMF3_TYPE_TRUE ->
			true
		AMF3_TYPE_FALSE ->
			false
		AMF3_TYPE_XMLDOCUMENT ->
			value // is string
		AMF3_TYPE_ARRAY -> {
			@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
			if ((wrappedValue.__traits as AMF3ArrayTraits).strict == true) {
				(value as Array<AMF3WrappedValue?>).mapToDynamicArray {
					if (it != null) unwrap(it) else null
				}
			} else {
				val obj = value.slice()
				for (key in Object.keys(value as Any)) {
					@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
					obj[key] = unwrap((value[key] as AMF3WrappedValue))
				}
				obj
			}
		}
		AMF3_TYPE_OBJECT -> {
			val obj = js("({})")
			for (key in Object.keys(value as Any)) {
				@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
				obj[key] = unwrap((value[key] as AMF3WrappedValue))
			}
			obj
		}
		else ->
			error("Unknown AMF3 type $type")
	}
}
