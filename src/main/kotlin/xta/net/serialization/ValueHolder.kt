package xta.net.serialization

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

open class ValueHolder<out T>(open val value:T): ReadOnlyProperty<Any, T> {
	open fun get():T = value

	override fun getValue(thisRef: Any, property: KProperty<*>): T {
		return get()
	}
}
