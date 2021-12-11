package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable
import xta.utils.joinToSentence

/*
 * Created by aimozg on 28.11.2021.
 */
@JsExport
class SkinPart(val host: Creature): JsonSerializable() {

	var coverage by property(SkinCoverage.NONE)
	var baseType by property(SkinBaseType.PLAIN)
	var baseColor by property("")
	var baseColor2 by property("")
	var baseAdj by property("")
	var baseCustomDesc by property("")
	var baseDesc
		get() = baseCustomDesc.ifEmpty { baseType.displayName }
		set(value) {
			baseCustomDesc = if (value == baseType.displayName) "" else value
		}
	var basePattern by property(SkinBasePatternType.NONE)
	var coatType by property(SkinCoatType.FUR)
	var coatColor by property("")
	var coatColor2 by property("")
	var coatAdj by property("")
	var coatCustomDesc by property("")
	var coatDesc
		get() = coatCustomDesc.ifEmpty { coatType.displayName }
		set(value) {
			coatCustomDesc = if (value == coatType.displayName) "" else value
		}
	var coatPattern by property(SkinCoatPatternType.NONE)

	val color get() = skinValue(baseColor, coatColor)
	val color2 get() = skinValue(baseColor2, coatColor2)
	val adj get() = skinValue(baseAdj, coatAdj)

	val skinColor get() = baseColor
	val skinColor2 get() = baseColor2
	val furColor get() = coatColor
	val furColor2 get() = coatColor2
	val chitinColor get() = coatColor
	val chitinColor2 get() = coatColor2
	val scaleColor get() = coatColor
	val scaleColor2 get() = coatColor2

	private fun skinValue(baseVal:String, coatVal:String) = when (coverage) {
		SkinCoverage.NONE,
		SkinCoverage.LOW ->
			baseVal
		SkinCoverage.MEDIUM ->
			"$baseVal and $coatVal"
		SkinCoverage.HIGH,
		SkinCoverage.COMPLETE ->
			coatVal
	}

	fun isCoverLowMid() = coverage == SkinCoverage.LOW || coverage == SkinCoverage.MEDIUM

	fun hasCoat() = coverage != SkinCoverage.NONE

	fun hasFullCoat() = coverage >= SkinCoverage.HIGH

	fun hasCoatOfType(vararg types: SkinCoatType): Boolean =
		hasCoat() && this.coatType in types

	fun hasPartialCoatOfType(vararg types: SkinCoatType): Boolean =
		coverage == SkinCoverage.LOW && this.coatType in types

	fun hasFullCoatOfType(vararg types: SkinCoatType): Boolean =
		hasFullCoat() && this.coatType in types

	fun hasPlainSkinOnly(): Boolean =
		!hasCoat() && baseType == SkinBaseType.PLAIN

	fun describeBase(noadj:Boolean, nocolor:Boolean):String = listOf(
		if (noadj) "" else baseAdj,
		if (nocolor) "" else baseColor,
		baseDesc
	).joinToSentence(
		separator=", ",
		beforeLast = " ",
		pair = " "
	)

	fun describeCoat(noadj:Boolean, nocolor:Boolean):String = listOf(
		if (noadj) "" else coatAdj,
		if (nocolor) "" else coatColor,
		coatDesc
	).joinToSentence(
		separator=", ",
		beforeLast = " ",
		pair = " "
	)

	fun describeSkin(noadj: Boolean, nocolor: Boolean): String {
		return skinValue(describeBase(noadj, nocolor), describeCoat(noadj, nocolor))
	}

	fun describeFull(noadj: Boolean, nocolor: Boolean): String {
		val base = describeBase(noadj, nocolor)
		val coat = describeCoat(noadj, nocolor)
		return when (coverage) {
			SkinCoverage.NONE -> base
			SkinCoverage.LOW -> "$base with patches of $coat"
			SkinCoverage.MEDIUM -> "$base and $coat"
			SkinCoverage.HIGH -> "$base under $coat"
			SkinCoverage.COMPLETE -> coat
		}
	}

	fun isAre(singular:String="is",plural:String="are"):String = skinValue(
		if (baseType.plural) plural else singular,
		if (coatType.plural) plural else singular
	)
}

