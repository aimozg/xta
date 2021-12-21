package xta.net.serialization

// TODO context/containing object
interface JsonSerializer<T:Any?> {
	fun serializeObject(t:T): dynamic
	fun deserializeObject(j: dynamic):T
}
