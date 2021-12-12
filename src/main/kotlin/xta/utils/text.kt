package xta.utils

import kotlin.math.floor
import kotlin.math.round
import kotlin.math.roundToInt

/*
 * Created by aimozg on 04.12.2021.
 */

private val FIRST_LETTER = Regex("""^[^\w]*\w""")

/**
 * Convert first character ([skipPunctuation]=false) or first letter ([skipPunctuation]=true) to uppercase
 */
fun String.capitalized(skipPunctuation:Boolean = true): String {
	return if (skipPunctuation) {
		replace(FIRST_LETTER) { it.value.uppercase() }
	} else {
		replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
	}
}

/**
 * Convert first character ([skipPunctuation]=false) or first letter ([skipPunctuation]=true) to lowercase
 */
fun String.decapitalized(skipPunctuation:Boolean = true): String {
	return if (skipPunctuation) {
		replace(FIRST_LETTER) { it.value.lowercase() }
	} else {
		replaceFirstChar { if (it.isUpperCase()) it.lowercase() else it.toString() }
	}
}

fun Int.toHex6() = toString(16).padStart(6, '0')

fun String.crop(maxLength: Int, tail: String = "…"): String =
	if (length < maxLength) this
	else substring(0, maxLength - tail.length) + tail

fun String.prepend(prefix: String) = prefix + this
fun String.prependIfNotEmpty(prefix: String) = if (isEmpty()) "" else (prefix+this)
fun String.appendIfNotEmpty(postfix: String) = if (isEmpty()) "" else (this+postfix)

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
