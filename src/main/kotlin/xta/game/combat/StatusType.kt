package xta.game.combat

/*
 * Created by aimozg on 16.12.2021.
 */
open class StatusType(
	val id:String,
	val displayName: String,
	val effectClass: String,
	val effectIcon: String,
	val isStackable: Boolean = false
) {
	init {
		BY_ID[id] = this
	}
	companion object {
		val BY_ID = HashMap<String,StatusType>()

		fun byId(id:String): StatusType = BY_ID[id] ?: error("Unknown Status $id")
	}
}

