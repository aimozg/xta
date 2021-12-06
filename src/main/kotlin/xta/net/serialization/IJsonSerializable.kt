package xta.net.serialization

import kotlin.js.Json

/*
 * Created by aimozg on 05.12.2021.
 */
interface IJsonSerializable {
	@JsName("serializeToJson")
	fun serializeToJson(): Json
	@JsName("deserializeFromJson")
	fun deserializeFromJson(input:Json)
}

