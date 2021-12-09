package xta.net.serialization

import xta.utils.mapToDynamicArray
import kotlin.js.Json

sealed class JsonPropertyDescriptor {
	abstract val fieldName:String
	abstract val fieldValue:Any?
	abstract fun serialize():dynamic
	abstract fun deserialize(input:dynamic)

	class StringField(
		override val fieldName: String,
		val property: VariableHolder<String>
	): JsonPropertyDescriptor(){
		override val fieldValue get() = property.value
		override fun serialize() = property.value

		override fun deserialize(input: dynamic) {
			property.value = input as String
		}
	}

	class IntField(
		override val fieldName: String,
		val property: VariableHolder<Int>
	): JsonPropertyDescriptor() {
		override val fieldValue get() = property.value
		override fun serialize() = property.value

		override fun deserialize(input: dynamic) {
			property.value = input as Int
		}
	}

	class DoubleField(
		override val fieldName: String,
		val property: VariableHolder<Double>
	): JsonPropertyDescriptor() {
		override val fieldValue get() = property.value
		override fun serialize() = property.value

		override fun deserialize(input: dynamic) {
			property.value = input as Double
		}
	}

	class BooleanField(
		override val fieldName: String,
		val property: VariableHolder<Boolean>
	): JsonPropertyDescriptor() {
		override val fieldValue get() = property.value
		override fun serialize() = property.value

		override fun deserialize(input: dynamic) {
			property.value = input as Boolean
		}
	}

	class EnumField<T:Enum<T>>(
		override val fieldName: String,
		val property: VariableHolder<T>,
		val values:Array<T>
	): JsonPropertyDescriptor() {
		override val fieldValue get() = property.value
		// TODO replace with ordinal when https://youtrack.jetbrains.com/issue/KT-45056 goes live
		override fun serialize() = values.indexOf(property.value)

		override fun deserialize(input: dynamic) {
			property.value = values[input as Int]
		}
	}

	class NestedVal<T:IJsonSerializable>(
		override val fieldName: String,
		val property: ValueHolder<T>
	): JsonPropertyDescriptor() {
		override val fieldValue get() = property.value
		override fun serialize(): dynamic {
			return property.value.serializeToJson()
		}

		override fun deserialize(input: dynamic) {
			@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
			property.value.deserializeFromJson(input as Any as Json)
		}
	}

	class NestedList<T:Any>(
		override val fieldName: String,
		val property: ValueHolder<MutableList<T>>,
		val serializer:JsonSerializer<T>
	): JsonPropertyDescriptor() {
		override val fieldValue get() = property.value
		override fun serialize(): dynamic {
			return property.get().mapToDynamicArray { serializer.serializeObject(it) }
		}

		override fun deserialize(input: dynamic) {
			val list = property.get()
			list.clear()
			for (json in input as Array<dynamic>) {
				list.add(serializer.deserializeObject(json))
			}
		}
	}

	class NestedJsonList<T:IJsonSerializable>(
		override val fieldName: String,
		val property: ValueHolder<MutableList<T>>,
		val spawner:(dynamic)->T
	): JsonPropertyDescriptor(){
		override val fieldValue get() = property.value
		override fun serialize(): dynamic {
			return property.get().mapToDynamicArray { it.serializeToJson() }
		}

		override fun deserialize(input: dynamic) {
			val oldList = property.get()
			val newList = input as Array<dynamic>

			// 1. Adjust size
			while (oldList.size > newList.size) {
				oldList.removeLast()
			}
			while (oldList.size < newList.size) {
				oldList.add(spawner(newList[oldList.size]))
			}

			// 2. Load data
			for (i in oldList.indices) {
				@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
				oldList[i].deserializeFromJson(newList[i] as Any as Json)
			}
		}
	}
}

