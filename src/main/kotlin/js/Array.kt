package js

/*
 * Created by aimozg on 03.12.2021.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun jsarrayFrom(o: Any): Array<Any?> = js("Array").from(o) as Array<Any?>

@Suppress("NOTHING_TO_INLINE")
inline fun <T> Array<T>.jspush(element: T): Int =
	asDynamic().push(element) as Int

@Suppress("NOTHING_TO_INLINE")
inline fun <T> Array<T>.jspush(vararg elements: T): Int =
	asDynamic().push.apply(this, elements) as Int

@Suppress("NOTHING_TO_INLINE")
inline fun <T> Array<T>.jsunshift(element: T): Int =
	asDynamic().unshift(element) as Int

@Suppress("NOTHING_TO_INLINE")
inline fun <T> Array<T>.jsunshift(vararg elements: T): Int =
	asDynamic().unshift.apply(this, elements) as Int

@Suppress("NOTHING_TO_INLINE")
inline fun <T> Array<T>.jsconcat(other: Array<T>): Array<T> =
	asDynamic().concat(other) as Array<T>

@Suppress("NOTHING_TO_INLINE")
inline fun <T> Array<T>.jsconcat(vararg others: Array<T>): Array<T> =
	asDynamic().concat.apply(this, others) as Array<T>

@Suppress("NOTHING_TO_INLINE")
inline fun <T> Array<T>.jspop(): T? =
	asDynamic().pop() as T?

@Suppress("NOTHING_TO_INLINE")
inline fun <T> Array<T>.jsshift(): T? =
	asDynamic().shift() as T?

@Suppress("NOTHING_TO_INLINE")
inline fun <T : O, O> Array<T>.jsslice(start: Int = 0, end: Int = size): Array<O> =
	asDynamic().slice(start, end) as Array<O>

@Suppress("NOTHING_TO_INLINE")
inline fun <T> Array<T>.jssplice(start: Int, deleteCount: Int = size - start): Array<T> =
	asDynamic().splice(start, deleteCount) as Array<T>

@Suppress("NOTHING_TO_INLINE")
inline fun <T> Array<T>.jssplice(start: Int, deleteCount: Int, vararg items: T): Array<T> =
	asDynamic().splice.apply(this, arrayOf(start, deleteCount, *items)) as Array<T>
