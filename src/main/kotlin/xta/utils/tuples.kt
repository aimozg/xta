package xta.utils

import kotlin.js.Json

/*
 * Created by aimozg on 05.12.2021.
 */

@Suppress("unused")
external interface JsonTuple3<T1,T2,T3>: Json

@Suppress("UNCHECKED_CAST")
inline operator fun<T1> JsonTuple3<T1,*,*>.component1() = this.asDynamic()["0"] as T1
@Suppress("UNCHECKED_CAST")
inline operator fun<T2> JsonTuple3<*,T2,*>.component2() = this.asDynamic()["1"] as T2
@Suppress("UNCHECKED_CAST")
inline operator fun<T3> JsonTuple3<*,*,T3>.component3() = this.asDynamic()["2"] as T3
