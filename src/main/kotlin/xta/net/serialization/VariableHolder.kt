package xta.net.serialization

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class VariableHolder<T>(override var value:T): ValueHolder<T>(value), ReadWriteProperty<Any, T> {

	open fun set(value:T) {
		this.value = value
	}

	override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
		set(value)
	}
}
