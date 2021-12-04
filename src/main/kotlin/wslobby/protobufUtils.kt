package wslobby

import org.khronos.webgl.Uint8Array
import kotlin.js.Json

/*
 * Created by aimozg on 30.11.2021.
 */
external interface ProtobufJsMessageClass<INTERFACE,MESSAGE:INTERFACE> {
	fun create(properties: INTERFACE = definedExternally): MESSAGE
	fun encode(message: INTERFACE, writer: ProtobufWriter = definedExternally): ProtobufWriter
	fun encodeDelimited(message: INTERFACE, writer: ProtobufWriter = definedExternally): ProtobufWriter
	fun decode(reader: ProtobufReader, length: Int = definedExternally): MESSAGE
	fun decode(reader: Uint8Array, length: Int = definedExternally): MESSAGE
	fun decodeDelimited(reader: ProtobufReader): MESSAGE
	fun decodeDelimited(reader: Uint8Array): MESSAGE
	fun verify(message: INTERFACE): String?
	fun verify(message: Json): String?
	fun fromObject(obj: Json): MESSAGE
	fun toObject(message: MESSAGE, options: ProtobufIConversionOptions = definedExternally): Json
}

fun<INTERFACE,MESSAGE:INTERFACE> ProtobufJsMessageClass<INTERFACE,MESSAGE>.compose(message: INTERFACE):Uint8Array {
	verify(message)?.let { throw Error(it) }
	return encode(message).finish()
}
