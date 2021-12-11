package xta.utils

import kotlin.math.floor
import kotlin.math.round
import kotlin.math.roundToInt

/*
 * Created by aimozg on 04.12.2021.
 */

fun String.capitalized() =
	replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

fun String.decapitalized() =
	replaceFirstChar { if (it.isUpperCase()) it.lowercase() else it.toString() }

fun Int.toHex6() = toString(16).padStart(6, '0')

fun String.crop(maxLength: Int, tail: String = "…"): String =
	if (length < maxLength) this
	else substring(0, maxLength - tail.length) + tail

fun String.prepend(prefix: String) = prefix + this

fun String.wrap(prefix: String, postfix: String) = prefix + this + postfix

fun String.fixedWrap(prefix: String, postfix: String, maxLength: Int, tail: String = "…") =
	prefix + (
			if (length >= maxLength) crop(maxLength, tail)
			else padEnd(maxLength, ' ')
			) + postfix

fun StringBuilder.deletePostfix(postfix:String):Boolean {
	if (endsWith(postfix)) {
		deleteRange(length-postfix.length,length)
		return true
	}
	return false
}
fun StringBuilder.deletePrefix(prefix:String):Boolean {
	if (startsWith(prefix)) {
		deleteRange(0,prefix.length)
		return true
	}
	return false
}

@JsExport
fun Double.formatBigInt():String {
	if (this < 0) return "-"+(-this).formatBigInt()
	if (!isFinite()) return toString()
	var x = round(this)
	var s = ""
	while (x >= 1000) {
		val spart = (x%1000).roundToInt().toString()
		s = "," + spart.padStart (3, '0')+s
		x = floor(x/1000)
	}
	s = x.roundToInt().toString() + s
	return s
}

@JsExport
@JsName("formatBigInt2")
fun Int.formatBigInt():String {
	if (this < 0) return "-"+(-this).formatBigInt()
	var x = this
	var s = ""
	while (x >= 1000) {
		// 678
		val spart = (x%1000).toString()
		s = "," + spart.padStart (3, '0')+s
		x /= 1000
	}
	s = x.toString() + s
	return s
}
