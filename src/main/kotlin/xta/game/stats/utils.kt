package xta.game.stats

import xta.text.toNiceString

/*
 * Created by aimozg on 05.12.2021.
 */

fun StringBuilder.explainBuff(
	text: String,
	value: Double,
	htmlFormat: Boolean,
	asPercentage: Boolean,
	isGood: Boolean
) {
	if (htmlFormat) {
		append("<div class='buff ")
		append(if (isGood) "buff-good" else "buff-bad")
		append(" ")
		append(if (value > 0) "buff-gt0" else "buff-lt0")
		append("'>")
		append("<span class='buff-name'>")
		append(text)
		append("</span>: <span class='buff-value'>")
		if (value > 0) append("+")
		if (asPercentage) {
			append(value.times(100).toInt().toString())
			append("%")
		} else {
			append(value.toNiceString(1))
		}
		append("</span></div>")
	} else {
		append(text)
		append(": ")
		if (value > 0) append("+")
		if (asPercentage) {
			append(value.times(100).toInt().toString())
			append("%")
		} else {
			append(value.toNiceString(1))
		}
		append("\n")
	}
}

fun printStatValueAndBuffs(
	stat: BuffableStat,
	statName: String,
	asPercentage: Boolean = false,
	isGood: Boolean = true
) = buildString {
	append("<div><b>")
	append(statName)
	append("</b>: <span class='buff-value'>")
	if (asPercentage) {
		append(stat.value.times(100).toInt())
		append("%")
	} else {
		append(stat.value.toInt())
	}
	append("</span></div>")
	append("<div class='ml-4'>")
	append(stat.explainBuffs(asPercentage=asPercentage,isGood=isGood))
	append("</div>")
}
