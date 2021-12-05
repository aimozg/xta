package xta.net.serialization

import kotlin.js.Json
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty

open class JsonSerializable: IJsonSerializable {
	val propertyDescriptors = ArrayList<JsonPropertyDescriptor>()
	override fun serializeToJson(): Json {
		@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
		val result = js("({})") as Json
		for (descriptor in propertyDescriptors) {
			result[descriptor.fieldName] = descriptor.serialize()
		}
		return result
	}

	override fun deserializeFromJson(input: Json) {
		for (descriptor in propertyDescriptors) {
			descriptor.deserialize(input[descriptor.fieldName])
		}
	}

	// Field name is same as property

	//Shortcuts under the same name
	fun property(defaultValue: String) = stringProperty(defaultValue)
	fun property(defaultValue: Int) = intProperty(defaultValue)
	fun property(defaultValue: Double) = doubleProperty(defaultValue)
	fun property(defaultValue: Boolean) = booleanProperty(defaultValue)
	inline fun<reified T:Enum<T>> property(defaultValue: T) = enumProperty(defaultValue)

	fun stringProperty(defaultValue: String) =
		PropertyDelegateProvider<Any, VariableHolder<String>> { _, property ->
			stringProperty(property.name, VariableHolder(defaultValue))
		}

	fun intProperty(defaultValue: Int) =
		PropertyDelegateProvider<Any, VariableHolder<Int>> { _, property ->
			intProperty(property.name, VariableHolder(defaultValue))
		}

	fun doubleProperty(defaultValue: Double) =
		PropertyDelegateProvider<Any, VariableHolder<Double>> { _, property ->
			doubleProperty(property.name, VariableHolder(defaultValue))
		}

	fun booleanProperty(defaultValue: Boolean) =
		PropertyDelegateProvider<Any, VariableHolder<Boolean>> { _, property ->
			booleanProperty(property.name, VariableHolder(defaultValue))
		}

	inline fun<reified T:Enum<T>> enumProperty(defaultValue: T) =
		PropertyDelegateProvider<Any, VariableHolder<T>> { _, property ->
			enumProperty(property.name, VariableHolder(defaultValue), enumValues())
		}

	fun<T:IJsonSerializable> nestedProperty(defaultValue: T) =
		PropertyDelegateProvider<Any, ReadOnlyProperty<Any, T>> { _, property ->
			nestedValProperty(property.name, ValueHolder(defaultValue))
		}

	fun<T:IJsonSerializable> nestedJsonList(
		defaultValue: ArrayList<T> = ArrayList(),
		spawner: (dynamic)->T
	) = PropertyDelegateProvider<Any, ReadOnlyProperty<Any, MutableList<T>>> { _, property ->
		nestedJsonListProperty(property.name, ValueHolder(defaultValue), spawner)
	}

	fun<T:Any> nestedList(
		serializer: JsonSerializer<T>,
		defaultValue: ArrayList<T> = ArrayList()
	) = PropertyDelegateProvider<Any, ReadOnlyProperty<Any, MutableList<T>>> { _, property ->
		nestedListProperty(property.name, ValueHolder(defaultValue), serializer)
	}

	// Custom field name or holder

	fun stringProperty(properyName: String, variableHolder: VariableHolder<String>) = variableHolder.also {
		propertyDescriptors.add(
			JsonPropertyDescriptor.StringField(properyName, it)
		)
	}
	fun intProperty(properyName: String, variableHolder: VariableHolder<Int>) = variableHolder.also {
		propertyDescriptors.add(
			JsonPropertyDescriptor.IntField(properyName, it)
		)
	}
	fun doubleProperty(properyName: String, variableHolder: VariableHolder<Double>) = variableHolder.also {
		propertyDescriptors.add(
			JsonPropertyDescriptor.DoubleField(properyName, it)
		)
	}
	fun booleanProperty(properyName: String, variableHolder: VariableHolder<Boolean>) = variableHolder.also {
		propertyDescriptors.add(
			JsonPropertyDescriptor.BooleanField(properyName, it)
		)
	}
	fun<T:Enum<T>> enumProperty(propertyName: String, variableHolder: VariableHolder<T>, values: Array<T>)= variableHolder.also {
		propertyDescriptors.add(
			JsonPropertyDescriptor.EnumField(propertyName, it, values)
		)
	}
	fun<T:IJsonSerializable> nestedValProperty(properyName: String, valueHolder: ValueHolder<T>) = valueHolder.also {
		propertyDescriptors.add(
			JsonPropertyDescriptor.NestedVal(properyName, it)
		)
	}
	fun<T:IJsonSerializable> nestedJsonListProperty(
		properyName: String,
		valueHolder: ValueHolder<MutableList<T>>,
		spawner:(dynamic)->T
	) = valueHolder.also {
		propertyDescriptors.add(
			JsonPropertyDescriptor.NestedJsonList(properyName, it, spawner)
		)
	}
	fun<T:Any> nestedListProperty(
		properyName: String,
		valueHolder: ValueHolder<MutableList<T>>,
		serializer: JsonSerializer<T>
	) = valueHolder.also {
		propertyDescriptors.add(
			JsonPropertyDescriptor.NestedList(properyName, it, serializer)
		)
	}
}
