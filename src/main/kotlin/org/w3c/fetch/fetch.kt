package org.w3c.fetch

import kotlin.js.Promise

/*
 * Created by aimozg on 02.12.2021.
 */

external fun fetch(resource:String, init:FetchOptions? = definedExternally): Promise<Response>
external fun fetch(resource:Request, init:FetchOptions? = definedExternally): Promise<Response>

external interface FetchOptions {
	var method: String?

	/**
	 * Headers | object
	 */
	var headers: dynamic

	/**
	 * Blob | BufferSource | FormData | URLSearchParams | USVString | ReadableStream
	 */
	var body: dynamic

	/**
	 * "cors" | "no-cors" | "same-origin"
	 */
	var mode: String?

	/**
	 * "omit" | "same-origin" | "include"
	 */
	var credentials: String?

	/**
	 * "default" | "no-store" | "reload" | "no-cache" | "force-cache" | "only-if-cached"
	 */
	var cache: String?

	/**
	 * "follow" | "error" | "manual"
	 */
	var redirect: String?

	var referrer: String?

	/**
	 * "no-referrer" | "no-referrer-when-downgrade" | "same-origin" | "origin" | "strict-origin" |
	 * "origin-when-cross-origin" | "strict-origin-when-cross-origin" | "unsafe-url"
	 */
	var referrerPolicy: String?

	var integrity: dynamic

	var keepalive: dynamic

	/**
	 * AbortSignal
	 */
	var signal: dynamic
}
