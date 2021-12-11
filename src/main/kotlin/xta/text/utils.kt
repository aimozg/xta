package xta.text

/*
 * Created by aimozg on 28.11.2021.
 */
fun Int.displayInches():String {
	// TODO switch to metric/other style depending on parser
	if (this < 0) return "-"+(-this).displayInches()
	val feet = this/12
	val inches = this%12
	return "$feet'" + (if (inches > 0) "$inches\"" else "")
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
