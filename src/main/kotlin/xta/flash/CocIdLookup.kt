package xta.flash

/*
 * Created by aimozg on 10.12.2021.
 */
abstract class CocIdLookup<T : CocId>(private val values: Array<T>) {

	fun byIdOrNull(id: Int): T? =
		values.find { it.cocID == id }
	fun byId(id: Int): T =
		values.find { it.cocID == id }
			?: error("Unknown ${values.first()::class.simpleName} id = $id")
}

