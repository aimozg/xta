package js

/*
 * Created by aimozg on 03.12.2021.
 */

external object Object {
	fun keys(o:dynamic): Array<String>
	fun values(o:dynamic): Array<dynamic>
	fun entries(o:dynamic): Array<Array<dynamic>>
	fun<T:Any> assign(target:T, vararg source: dynamic):T
}
