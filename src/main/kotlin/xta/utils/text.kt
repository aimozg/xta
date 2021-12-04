package xta.utils

/*
 * Created by aimozg on 04.12.2021.
 */

fun String.capitalized() =
	replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

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
