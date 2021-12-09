package xta.net.serialization

/*
 * Created by aimozg on 05.12.2021.
 */
interface IJsonSerializable {
	@JsName("serializeToJson")
	fun serializeToJson(): dynamic
	@JsName("deserializeFromJson")
	fun deserializeFromJson(input:dynamic)
}

