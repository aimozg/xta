package xta.net.serialization

interface JsonSerializer<T:Any> {
	fun serializeObject(t:T): dynamic
	fun deserializeObject(j: dynamic):T
}
