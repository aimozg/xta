package org.w3c.files

import org.khronos.webgl.ArrayBuffer
import kotlin.js.Promise

/*
 * Created by aimozg on 03.12.2021.
 */

@Suppress("NOTHING_TO_INLINE")
inline fun Blob.arrayBuffer(): Promise<ArrayBuffer> = asDynamic().arrayBuffer() as Promise<ArrayBuffer>
