package org.w3c.dom

/*
 * Created by aimozg on 19.12.2021.
 */

/**
 * @return An ASCII string containing the Base64 representation of [stringToEncode].
 */
external fun btoa(stringToEncode:String):String

/**
 * @return An ASCII string containing decoded data from [encodedData].
 */
external fun atob(encodedData:String):String
