package xta.text

import js.toFixed

/*
 * Created by aimozg on 28.11.2021.
 */
fun Int.displayInches():String {
	// TODO switch to metric/other style depending on parser
	if (this < 0) return "-"+(-this).displayInches()
	val feet = this/12
	val inches = this%12
	return when {
		feet > 0 && inches > 0 -> "$feet'$inches\""
		feet > 0 -> "$feet'"
		else -> "$inches\""
	}
}

private val REX_TRAILING_ZEROS = Regex("""\.?0+$""")

fun Double.toNiceString(maxDecimals: Int): String {
	if (maxDecimals == 0) return toFixed(0)
	return toFixed(maxDecimals).replace(REX_TRAILING_ZEROS,"")
}

private val NUMBER_WORDS_NORMAL = arrayOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten")

fun num2text(count:Int):String {
	return NUMBER_WORDS_NORMAL.getOrNull(count) ?: count.toString()
}

object EmptyMatchResult: MatchResult {
	override val groupValues: List<String>
		get() = emptyList()
	override val groups: MatchGroupCollection = object:AbstractCollection<MatchGroup?>(),MatchGroupCollection {
			private val list:List<MatchGroup?> = listOf(MatchGroup(""))
			override val size: Int
				get() = list.size

			override fun iterator() = list.iterator()

			override fun get(index: Int): MatchGroup? = list[index]
		}
	override val range: IntRange
		get() = 0..0
	override val value: String
		get() = ""

	override fun next(): MatchResult? = null
}
