package js

/*
 * Created by aimozg on 03.12.2021.
 */

@Suppress("NOTHING_TO_INLINE")
fun Double.toFixed(digits:Int=0):String = this.asDynamic().toFixed(digits) as String
@Suppress("NOTHING_TO_INLINE")
fun Double.toPrecision(digits:Int):String = this.asDynamic().toPrecision(digits) as String
@Suppress("NOTHING_TO_INLINE")
fun Double.toExponential(fractionDigits:Int?= undefined):String = this.asDynamic().toExponential(fractionDigits) as String

