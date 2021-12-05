package xta.net.serialization

import kotlin.js.Json

/*
 * Created by aimozg on 05.12.2021.
 */
interface IJsonSerializable {
	fun serializeToJson(): Json
	fun deserializeFromJson(input:Json)
}

