package xta.utils

import org.khronos.webgl.Uint8Array
import org.w3c.dom.TextDecoder
import org.w3c.dom.TextEncoder
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.js.Json
import kotlin.js.Promise

/*
 * Created by aimozg on 28.11.2021.
 */
inline fun <IN, reified OUT> Array<IN>.mapToArray(map: (IN) -> OUT): Array<OUT> =
	Array(size) { map(this[it]) }
fun <IN> Array<IN>.mapToDynamicArray(map: (IN) -> dynamic): Array<dynamic> =
	Array<Any?>(size) { map(this[it]) }
inline fun <IN, reified OUT> List<IN>.mapToArray(map: (IN) -> OUT): Array<OUT> =
	Array(size) { map(this[it]) }
fun <IN> List<IN>.mapToDynamicArray(map: (IN) -> dynamic): Array<dynamic> =
	Array<Any?>(size) { map(this[it]) }

suspend fun <T> Promise<T>.await(): T = suspendCoroutine { cont ->
	then(
		{ cont.resume(it) },
		{ cont.resumeWithException(it) }
	)
}

inline fun<T:Any> jsObject(init:T.()->Unit) = (js("({})") as T).apply(init)
inline fun<T:Any> jsobject(init:(json:T)->Unit) = (js("({})") as T).also(init)
@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
inline fun buildJson(init:(json:Json)->Unit) = (js("({})") as Json).also(init)

fun Any?.stringify() = JSON.stringify(this)

fun Uint8Array.decodeToJson():dynamic = JSON.parse(TextDecoder().decode(this))
fun Json.encodeToArray() = TextEncoder().encode(JSON.stringify(this))
fun String.encodeToArray() = TextEncoder().encode(this)
