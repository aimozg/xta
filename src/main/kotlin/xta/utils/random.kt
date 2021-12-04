package xta.utils

import kotlin.random.Random

/*
 * Created by aimozg on 28.11.2021.
 */

/**
 * Random generator for non-gameplay effects (like text flavour)
 */
val fxrng = Random(Random.Default.nextInt())

fun Random.either(vararg options:String) =
	if (options.isEmpty()) ""
	else options[nextInt(options.size)]

const val BASE58 = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz"
fun Random.randomString(length:Int, chars:String = BASE58):String {
	var s = ""
	for (i in 1..length) {
		s += chars[nextInt(chars.length)]
	}
	return s
}
