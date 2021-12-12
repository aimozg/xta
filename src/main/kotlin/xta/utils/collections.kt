package xta.utils

/*
 * Created by aimozg on 06.12.2021.
 */

inline fun<T> MutableListIterator<T>.walk(body:(MutableListIterator<T>,value:T)->Unit) {
	for (ele in this) {
		body(this, ele)
	}
}
inline fun<T> MutableList<T>.walk(body:(MutableListIterator<T>,value:T)->Unit) {
	val li = this.listIterator()
	for (ele in li) {
		body(li, ele)
	}
}

inline fun <IN, reified OUT> Array<IN>.mapToArray(map: (IN) -> OUT): Array<OUT> =
	Array(size) { map(this[it]) }
inline fun <IN, reified OUT> Collection<IN>.mapToArray(map: (IN) -> OUT): Array<OUT> {
	val iter = iterator()
	return Array(size) { map(iter.next()) }
}

fun <IN> Array<IN>.mapToDynamicArray(map: (IN) -> dynamic): Array<dynamic> =
	Array<Any?>(size) { map(this[it]) }
fun <IN> Collection<IN>.mapToDynamicArray(map: (IN) -> dynamic): Array<dynamic> {
	val iter = iterator()
	return Array<Any?>(size) { map(iter.next()) }
}

@Suppress("UNCHECKED_CAST")
inline fun <IN, OUT> List<IN>.mapToJsonArray(builder: (input:IN, output:OUT) -> Unit): Array<OUT> =
	Array<Any?>(size) {
		val json = js("({})") as OUT
		builder(this[it], json)
		json
	} as Array<OUT>
@Suppress("UNCHECKED_CAST")
inline fun <IN, OUT> Array<IN>.mapToJsonArray(builder: (input:IN, output:OUT) -> Unit): Array<OUT> =
	Array<Any?>(size) {
		val json = js("({})") as OUT
		builder(this[it], json)
		json
	} as Array<OUT>

fun List<String>.joinToSentence(
	separator:String=", ",
	beforeLast:String=", and ",
	pair:String=" and ",
	decapitalize:Boolean = true,
	dropPeriods:Boolean = true
): String {
	if (any { it.isBlank() }) return filter { it.isNotBlank() }.joinToSentence(separator, beforeLast, pair)
	val list = this
	val size = size
	return buildString {
		for ((i,s) in list.withIndex()) {
			when {
				size == 2 && i == 1 ->
					append(pair)
				size > 2 && i == size-1 ->
					append(beforeLast)
				i > 0 ->
					append(separator)
			}
			val s2 = if (dropPeriods && i < size-1) s.removeSuffix(".") else s
			if (decapitalize && i > 0) {
				append(s2.decapitalized(true))
			} else {
				append(s2)
			}
		}
	}
}

inline fun<T> List<T>.joinToSentence(
	separator:String=", ",
	beforeLast:String=", and ",
	pair:String=" and ",
	decapitalize:Boolean = true,
	dropPeriods:Boolean = true,
	transform:(T)->String
): String = map(transform).joinToSentence(separator, beforeLast, pair, decapitalize, dropPeriods)
