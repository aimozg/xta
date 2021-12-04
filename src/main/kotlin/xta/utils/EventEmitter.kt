package xta.utils

/*
 * Created by aimozg on 29.11.2021.
 */
open class EventEmitter {
	protected val events = HashMap<String,ArrayList<Function<Unit>>>()
	protected fun registerEventTypes(vararg types:String) {
		for (type in types) {
			events[type] = ArrayList()
		}
	}
	protected fun emit(eventName: String, vararg args:dynamic) {
		events[eventName]?.forEach {
			it.asDynamic().apply(null, args)
			Unit
		}
	}

	fun on(eventName:String, callback:Function<Unit>) {
		events[eventName]?.add(callback) ?: error("Invalid event name '$eventName'")
	}
	fun off(eventName:String, callback:Function<Unit>) {
		events[eventName]?.remove(callback) ?: error("Invalid event name '$eventName'")
	}
}
