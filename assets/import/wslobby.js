(function(f){if(typeof exports==="object"&&typeof module!=="undefined"){module.exports=f()}else if(typeof define==="function"&&define.amd){define([],f)}else{var g;if(typeof window!=="undefined"){g=window}else if(typeof global!=="undefined"){g=global}else if(typeof self!=="undefined"){g=self}else{g=this}g.wslobby = f()}})(function(){var define,module,exports;return (function(){function r(e,n,t){function o(i,f){if(!n[i]){if(!e[i]){var c="function"==typeof require&&require;if(!f&&c)return c(i,!0);if(u)return u(i,!0);var a=new Error("Cannot find module '"+i+"'");throw a.code="MODULE_NOT_FOUND",a}var p=n[i]={exports:{}};e[i][0].call(p.exports,function(r){var n=e[i][1][r];return o(n||r)},p,p.exports,r,e,n,t)}return n[i].exports}for(var u="function"==typeof require&&require,i=0;i<t.length;i++)o(t[i]);return o}return r})()({1:[function(require,module,exports){
"use strict";
module.exports = asPromise;

/**
 * Callback as used by {@link util.asPromise}.
 * @typedef asPromiseCallback
 * @type {function}
 * @param {Error|null} error Error, if any
 * @param {...*} params Additional arguments
 * @returns {undefined}
 */

/**
 * Returns a promise from a node-style callback function.
 * @memberof util
 * @param {asPromiseCallback} fn Function to call
 * @param {*} ctx Function context
 * @param {...*} params Function arguments
 * @returns {Promise<*>} Promisified function
 */
function asPromise(fn, ctx/*, varargs */) {
    var params  = new Array(arguments.length - 1),
        offset  = 0,
        index   = 2,
        pending = true;
    while (index < arguments.length)
        params[offset++] = arguments[index++];
    return new Promise(function executor(resolve, reject) {
        params[offset] = function callback(err/*, varargs */) {
            if (pending) {
                pending = false;
                if (err)
                    reject(err);
                else {
                    var params = new Array(arguments.length - 1),
                        offset = 0;
                    while (offset < params.length)
                        params[offset++] = arguments[offset];
                    resolve.apply(null, params);
                }
            }
        };
        try {
            fn.apply(ctx || null, params);
        } catch (err) {
            if (pending) {
                pending = false;
                reject(err);
            }
        }
    });
}

},{}],2:[function(require,module,exports){
"use strict";

/**
 * A minimal base64 implementation for number arrays.
 * @memberof util
 * @namespace
 */
var base64 = exports;

/**
 * Calculates the byte length of a base64 encoded string.
 * @param {string} string Base64 encoded string
 * @returns {number} Byte length
 */
base64.length = function length(string) {
    var p = string.length;
    if (!p)
        return 0;
    var n = 0;
    while (--p % 4 > 1 && string.charAt(p) === "=")
        ++n;
    return Math.ceil(string.length * 3) / 4 - n;
};

// Base64 encoding table
var b64 = new Array(64);

// Base64 decoding table
var s64 = new Array(123);

// 65..90, 97..122, 48..57, 43, 47
for (var i = 0; i < 64;)
    s64[b64[i] = i < 26 ? i + 65 : i < 52 ? i + 71 : i < 62 ? i - 4 : i - 59 | 43] = i++;

/**
 * Encodes a buffer to a base64 encoded string.
 * @param {Uint8Array} buffer Source buffer
 * @param {number} start Source start
 * @param {number} end Source end
 * @returns {string} Base64 encoded string
 */
base64.encode = function encode(buffer, start, end) {
    var parts = null,
        chunk = [];
    var i = 0, // output index
        j = 0, // goto index
        t;     // temporary
    while (start < end) {
        var b = buffer[start++];
        switch (j) {
            case 0:
                chunk[i++] = b64[b >> 2];
                t = (b & 3) << 4;
                j = 1;
                break;
            case 1:
                chunk[i++] = b64[t | b >> 4];
                t = (b & 15) << 2;
                j = 2;
                break;
            case 2:
                chunk[i++] = b64[t | b >> 6];
                chunk[i++] = b64[b & 63];
                j = 0;
                break;
        }
        if (i > 8191) {
            (parts || (parts = [])).push(String.fromCharCode.apply(String, chunk));
            i = 0;
        }
    }
    if (j) {
        chunk[i++] = b64[t];
        chunk[i++] = 61;
        if (j === 1)
            chunk[i++] = 61;
    }
    if (parts) {
        if (i)
            parts.push(String.fromCharCode.apply(String, chunk.slice(0, i)));
        return parts.join("");
    }
    return String.fromCharCode.apply(String, chunk.slice(0, i));
};

var invalidEncoding = "invalid encoding";

/**
 * Decodes a base64 encoded string to a buffer.
 * @param {string} string Source string
 * @param {Uint8Array} buffer Destination buffer
 * @param {number} offset Destination offset
 * @returns {number} Number of bytes written
 * @throws {Error} If encoding is invalid
 */
base64.decode = function decode(string, buffer, offset) {
    var start = offset;
    var j = 0, // goto index
        t;     // temporary
    for (var i = 0; i < string.length;) {
        var c = string.charCodeAt(i++);
        if (c === 61 && j > 1)
            break;
        if ((c = s64[c]) === undefined)
            throw Error(invalidEncoding);
        switch (j) {
            case 0:
                t = c;
                j = 1;
                break;
            case 1:
                buffer[offset++] = t << 2 | (c & 48) >> 4;
                t = c;
                j = 2;
                break;
            case 2:
                buffer[offset++] = (t & 15) << 4 | (c & 60) >> 2;
                t = c;
                j = 3;
                break;
            case 3:
                buffer[offset++] = (t & 3) << 6 | c;
                j = 0;
                break;
        }
    }
    if (j === 1)
        throw Error(invalidEncoding);
    return offset - start;
};

/**
 * Tests if the specified string appears to be base64 encoded.
 * @param {string} string String to test
 * @returns {boolean} `true` if probably base64 encoded, otherwise false
 */
base64.test = function test(string) {
    return /^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$/.test(string);
};

},{}],3:[function(require,module,exports){
"use strict";
module.exports = EventEmitter;

/**
 * Constructs a new event emitter instance.
 * @classdesc A minimal event emitter.
 * @memberof util
 * @constructor
 */
function EventEmitter() {

    /**
     * Registered listeners.
     * @type {Object.<string,*>}
     * @private
     */
    this._listeners = {};
}

/**
 * Registers an event listener.
 * @param {string} evt Event name
 * @param {function} fn Listener
 * @param {*} [ctx] Listener context
 * @returns {util.EventEmitter} `this`
 */
EventEmitter.prototype.on = function on(evt, fn, ctx) {
    (this._listeners[evt] || (this._listeners[evt] = [])).push({
        fn  : fn,
        ctx : ctx || this
    });
    return this;
};

/**
 * Removes an event listener or any matching listeners if arguments are omitted.
 * @param {string} [evt] Event name. Removes all listeners if omitted.
 * @param {function} [fn] Listener to remove. Removes all listeners of `evt` if omitted.
 * @returns {util.EventEmitter} `this`
 */
EventEmitter.prototype.off = function off(evt, fn) {
    if (evt === undefined)
        this._listeners = {};
    else {
        if (fn === undefined)
            this._listeners[evt] = [];
        else {
            var listeners = this._listeners[evt];
            for (var i = 0; i < listeners.length;)
                if (listeners[i].fn === fn)
                    listeners.splice(i, 1);
                else
                    ++i;
        }
    }
    return this;
};

/**
 * Emits an event by calling its listeners with the specified arguments.
 * @param {string} evt Event name
 * @param {...*} args Arguments
 * @returns {util.EventEmitter} `this`
 */
EventEmitter.prototype.emit = function emit(evt) {
    var listeners = this._listeners[evt];
    if (listeners) {
        var args = [],
            i = 1;
        for (; i < arguments.length;)
            args.push(arguments[i++]);
        for (i = 0; i < listeners.length;)
            listeners[i].fn.apply(listeners[i++].ctx, args);
    }
    return this;
};

},{}],4:[function(require,module,exports){
"use strict";

module.exports = factory(factory);

/**
 * Reads / writes floats / doubles from / to buffers.
 * @name util.float
 * @namespace
 */

/**
 * Writes a 32 bit float to a buffer using little endian byte order.
 * @name util.float.writeFloatLE
 * @function
 * @param {number} val Value to write
 * @param {Uint8Array} buf Target buffer
 * @param {number} pos Target buffer offset
 * @returns {undefined}
 */

/**
 * Writes a 32 bit float to a buffer using big endian byte order.
 * @name util.float.writeFloatBE
 * @function
 * @param {number} val Value to write
 * @param {Uint8Array} buf Target buffer
 * @param {number} pos Target buffer offset
 * @returns {undefined}
 */

/**
 * Reads a 32 bit float from a buffer using little endian byte order.
 * @name util.float.readFloatLE
 * @function
 * @param {Uint8Array} buf Source buffer
 * @param {number} pos Source buffer offset
 * @returns {number} Value read
 */

/**
 * Reads a 32 bit float from a buffer using big endian byte order.
 * @name util.float.readFloatBE
 * @function
 * @param {Uint8Array} buf Source buffer
 * @param {number} pos Source buffer offset
 * @returns {number} Value read
 */

/**
 * Writes a 64 bit double to a buffer using little endian byte order.
 * @name util.float.writeDoubleLE
 * @function
 * @param {number} val Value to write
 * @param {Uint8Array} buf Target buffer
 * @param {number} pos Target buffer offset
 * @returns {undefined}
 */

/**
 * Writes a 64 bit double to a buffer using big endian byte order.
 * @name util.float.writeDoubleBE
 * @function
 * @param {number} val Value to write
 * @param {Uint8Array} buf Target buffer
 * @param {number} pos Target buffer offset
 * @returns {undefined}
 */

/**
 * Reads a 64 bit double from a buffer using little endian byte order.
 * @name util.float.readDoubleLE
 * @function
 * @param {Uint8Array} buf Source buffer
 * @param {number} pos Source buffer offset
 * @returns {number} Value read
 */

/**
 * Reads a 64 bit double from a buffer using big endian byte order.
 * @name util.float.readDoubleBE
 * @function
 * @param {Uint8Array} buf Source buffer
 * @param {number} pos Source buffer offset
 * @returns {number} Value read
 */

// Factory function for the purpose of node-based testing in modified global environments
function factory(exports) {

    // float: typed array
    if (typeof Float32Array !== "undefined") (function() {

        var f32 = new Float32Array([ -0 ]),
            f8b = new Uint8Array(f32.buffer),
            le  = f8b[3] === 128;

        function writeFloat_f32_cpy(val, buf, pos) {
            f32[0] = val;
            buf[pos    ] = f8b[0];
            buf[pos + 1] = f8b[1];
            buf[pos + 2] = f8b[2];
            buf[pos + 3] = f8b[3];
        }

        function writeFloat_f32_rev(val, buf, pos) {
            f32[0] = val;
            buf[pos    ] = f8b[3];
            buf[pos + 1] = f8b[2];
            buf[pos + 2] = f8b[1];
            buf[pos + 3] = f8b[0];
        }

        /* istanbul ignore next */
        exports.writeFloatLE = le ? writeFloat_f32_cpy : writeFloat_f32_rev;
        /* istanbul ignore next */
        exports.writeFloatBE = le ? writeFloat_f32_rev : writeFloat_f32_cpy;

        function readFloat_f32_cpy(buf, pos) {
            f8b[0] = buf[pos    ];
            f8b[1] = buf[pos + 1];
            f8b[2] = buf[pos + 2];
            f8b[3] = buf[pos + 3];
            return f32[0];
        }

        function readFloat_f32_rev(buf, pos) {
            f8b[3] = buf[pos    ];
            f8b[2] = buf[pos + 1];
            f8b[1] = buf[pos + 2];
            f8b[0] = buf[pos + 3];
            return f32[0];
        }

        /* istanbul ignore next */
        exports.readFloatLE = le ? readFloat_f32_cpy : readFloat_f32_rev;
        /* istanbul ignore next */
        exports.readFloatBE = le ? readFloat_f32_rev : readFloat_f32_cpy;

    // float: ieee754
    })(); else (function() {

        function writeFloat_ieee754(writeUint, val, buf, pos) {
            var sign = val < 0 ? 1 : 0;
            if (sign)
                val = -val;
            if (val === 0)
                writeUint(1 / val > 0 ? /* positive */ 0 : /* negative 0 */ 2147483648, buf, pos);
            else if (isNaN(val))
                writeUint(2143289344, buf, pos);
            else if (val > 3.4028234663852886e+38) // +-Infinity
                writeUint((sign << 31 | 2139095040) >>> 0, buf, pos);
            else if (val < 1.1754943508222875e-38) // denormal
                writeUint((sign << 31 | Math.round(val / 1.401298464324817e-45)) >>> 0, buf, pos);
            else {
                var exponent = Math.floor(Math.log(val) / Math.LN2),
                    mantissa = Math.round(val * Math.pow(2, -exponent) * 8388608) & 8388607;
                writeUint((sign << 31 | exponent + 127 << 23 | mantissa) >>> 0, buf, pos);
            }
        }

        exports.writeFloatLE = writeFloat_ieee754.bind(null, writeUintLE);
        exports.writeFloatBE = writeFloat_ieee754.bind(null, writeUintBE);

        function readFloat_ieee754(readUint, buf, pos) {
            var uint = readUint(buf, pos),
                sign = (uint >> 31) * 2 + 1,
                exponent = uint >>> 23 & 255,
                mantissa = uint & 8388607;
            return exponent === 255
                ? mantissa
                ? NaN
                : sign * Infinity
                : exponent === 0 // denormal
                ? sign * 1.401298464324817e-45 * mantissa
                : sign * Math.pow(2, exponent - 150) * (mantissa + 8388608);
        }

        exports.readFloatLE = readFloat_ieee754.bind(null, readUintLE);
        exports.readFloatBE = readFloat_ieee754.bind(null, readUintBE);

    })();

    // double: typed array
    if (typeof Float64Array !== "undefined") (function() {

        var f64 = new Float64Array([-0]),
            f8b = new Uint8Array(f64.buffer),
            le  = f8b[7] === 128;

        function writeDouble_f64_cpy(val, buf, pos) {
            f64[0] = val;
            buf[pos    ] = f8b[0];
            buf[pos + 1] = f8b[1];
            buf[pos + 2] = f8b[2];
            buf[pos + 3] = f8b[3];
            buf[pos + 4] = f8b[4];
            buf[pos + 5] = f8b[5];
            buf[pos + 6] = f8b[6];
            buf[pos + 7] = f8b[7];
        }

        function writeDouble_f64_rev(val, buf, pos) {
            f64[0] = val;
            buf[pos    ] = f8b[7];
            buf[pos + 1] = f8b[6];
            buf[pos + 2] = f8b[5];
            buf[pos + 3] = f8b[4];
            buf[pos + 4] = f8b[3];
            buf[pos + 5] = f8b[2];
            buf[pos + 6] = f8b[1];
            buf[pos + 7] = f8b[0];
        }

        /* istanbul ignore next */
        exports.writeDoubleLE = le ? writeDouble_f64_cpy : writeDouble_f64_rev;
        /* istanbul ignore next */
        exports.writeDoubleBE = le ? writeDouble_f64_rev : writeDouble_f64_cpy;

        function readDouble_f64_cpy(buf, pos) {
            f8b[0] = buf[pos    ];
            f8b[1] = buf[pos + 1];
            f8b[2] = buf[pos + 2];
            f8b[3] = buf[pos + 3];
            f8b[4] = buf[pos + 4];
            f8b[5] = buf[pos + 5];
            f8b[6] = buf[pos + 6];
            f8b[7] = buf[pos + 7];
            return f64[0];
        }

        function readDouble_f64_rev(buf, pos) {
            f8b[7] = buf[pos    ];
            f8b[6] = buf[pos + 1];
            f8b[5] = buf[pos + 2];
            f8b[4] = buf[pos + 3];
            f8b[3] = buf[pos + 4];
            f8b[2] = buf[pos + 5];
            f8b[1] = buf[pos + 6];
            f8b[0] = buf[pos + 7];
            return f64[0];
        }

        /* istanbul ignore next */
        exports.readDoubleLE = le ? readDouble_f64_cpy : readDouble_f64_rev;
        /* istanbul ignore next */
        exports.readDoubleBE = le ? readDouble_f64_rev : readDouble_f64_cpy;

    // double: ieee754
    })(); else (function() {

        function writeDouble_ieee754(writeUint, off0, off1, val, buf, pos) {
            var sign = val < 0 ? 1 : 0;
            if (sign)
                val = -val;
            if (val === 0) {
                writeUint(0, buf, pos + off0);
                writeUint(1 / val > 0 ? /* positive */ 0 : /* negative 0 */ 2147483648, buf, pos + off1);
            } else if (isNaN(val)) {
                writeUint(0, buf, pos + off0);
                writeUint(2146959360, buf, pos + off1);
            } else if (val > 1.7976931348623157e+308) { // +-Infinity
                writeUint(0, buf, pos + off0);
                writeUint((sign << 31 | 2146435072) >>> 0, buf, pos + off1);
            } else {
                var mantissa;
                if (val < 2.2250738585072014e-308) { // denormal
                    mantissa = val / 5e-324;
                    writeUint(mantissa >>> 0, buf, pos + off0);
                    writeUint((sign << 31 | mantissa / 4294967296) >>> 0, buf, pos + off1);
                } else {
                    var exponent = Math.floor(Math.log(val) / Math.LN2);
                    if (exponent === 1024)
                        exponent = 1023;
                    mantissa = val * Math.pow(2, -exponent);
                    writeUint(mantissa * 4503599627370496 >>> 0, buf, pos + off0);
                    writeUint((sign << 31 | exponent + 1023 << 20 | mantissa * 1048576 & 1048575) >>> 0, buf, pos + off1);
                }
            }
        }

        exports.writeDoubleLE = writeDouble_ieee754.bind(null, writeUintLE, 0, 4);
        exports.writeDoubleBE = writeDouble_ieee754.bind(null, writeUintBE, 4, 0);

        function readDouble_ieee754(readUint, off0, off1, buf, pos) {
            var lo = readUint(buf, pos + off0),
                hi = readUint(buf, pos + off1);
            var sign = (hi >> 31) * 2 + 1,
                exponent = hi >>> 20 & 2047,
                mantissa = 4294967296 * (hi & 1048575) + lo;
            return exponent === 2047
                ? mantissa
                ? NaN
                : sign * Infinity
                : exponent === 0 // denormal
                ? sign * 5e-324 * mantissa
                : sign * Math.pow(2, exponent - 1075) * (mantissa + 4503599627370496);
        }

        exports.readDoubleLE = readDouble_ieee754.bind(null, readUintLE, 0, 4);
        exports.readDoubleBE = readDouble_ieee754.bind(null, readUintBE, 4, 0);

    })();

    return exports;
}

// uint helpers

function writeUintLE(val, buf, pos) {
    buf[pos    ] =  val        & 255;
    buf[pos + 1] =  val >>> 8  & 255;
    buf[pos + 2] =  val >>> 16 & 255;
    buf[pos + 3] =  val >>> 24;
}

function writeUintBE(val, buf, pos) {
    buf[pos    ] =  val >>> 24;
    buf[pos + 1] =  val >>> 16 & 255;
    buf[pos + 2] =  val >>> 8  & 255;
    buf[pos + 3] =  val        & 255;
}

function readUintLE(buf, pos) {
    return (buf[pos    ]
          | buf[pos + 1] << 8
          | buf[pos + 2] << 16
          | buf[pos + 3] << 24) >>> 0;
}

function readUintBE(buf, pos) {
    return (buf[pos    ] << 24
          | buf[pos + 1] << 16
          | buf[pos + 2] << 8
          | buf[pos + 3]) >>> 0;
}

},{}],5:[function(require,module,exports){
"use strict";
module.exports = inquire;

/**
 * Requires a module only if available.
 * @memberof util
 * @param {string} moduleName Module to require
 * @returns {?Object} Required module if available and not empty, otherwise `null`
 */
function inquire(moduleName) {
    try {
        var mod = eval("quire".replace(/^/,"re"))(moduleName); // eslint-disable-line no-eval
        if (mod && (mod.length || Object.keys(mod).length))
            return mod;
    } catch (e) {} // eslint-disable-line no-empty
    return null;
}

},{}],6:[function(require,module,exports){
"use strict";
module.exports = pool;

/**
 * An allocator as used by {@link util.pool}.
 * @typedef PoolAllocator
 * @type {function}
 * @param {number} size Buffer size
 * @returns {Uint8Array} Buffer
 */

/**
 * A slicer as used by {@link util.pool}.
 * @typedef PoolSlicer
 * @type {function}
 * @param {number} start Start offset
 * @param {number} end End offset
 * @returns {Uint8Array} Buffer slice
 * @this {Uint8Array}
 */

/**
 * A general purpose buffer pool.
 * @memberof util
 * @function
 * @param {PoolAllocator} alloc Allocator
 * @param {PoolSlicer} slice Slicer
 * @param {number} [size=8192] Slab size
 * @returns {PoolAllocator} Pooled allocator
 */
function pool(alloc, slice, size) {
    var SIZE   = size || 8192;
    var MAX    = SIZE >>> 1;
    var slab   = null;
    var offset = SIZE;
    return function pool_alloc(size) {
        if (size < 1 || size > MAX)
            return alloc(size);
        if (offset + size > SIZE) {
            slab = alloc(SIZE);
            offset = 0;
        }
        var buf = slice.call(slab, offset, offset += size);
        if (offset & 7) // align to 32 bit
            offset = (offset | 7) + 1;
        return buf;
    };
}

},{}],7:[function(require,module,exports){
"use strict";

/**
 * A minimal UTF8 implementation for number arrays.
 * @memberof util
 * @namespace
 */
var utf8 = exports;

/**
 * Calculates the UTF8 byte length of a string.
 * @param {string} string String
 * @returns {number} Byte length
 */
utf8.length = function utf8_length(string) {
    var len = 0,
        c = 0;
    for (var i = 0; i < string.length; ++i) {
        c = string.charCodeAt(i);
        if (c < 128)
            len += 1;
        else if (c < 2048)
            len += 2;
        else if ((c & 0xFC00) === 0xD800 && (string.charCodeAt(i + 1) & 0xFC00) === 0xDC00) {
            ++i;
            len += 4;
        } else
            len += 3;
    }
    return len;
};

/**
 * Reads UTF8 bytes as a string.
 * @param {Uint8Array} buffer Source buffer
 * @param {number} start Source start
 * @param {number} end Source end
 * @returns {string} String read
 */
utf8.read = function utf8_read(buffer, start, end) {
    var len = end - start;
    if (len < 1)
        return "";
    var parts = null,
        chunk = [],
        i = 0, // char offset
        t;     // temporary
    while (start < end) {
        t = buffer[start++];
        if (t < 128)
            chunk[i++] = t;
        else if (t > 191 && t < 224)
            chunk[i++] = (t & 31) << 6 | buffer[start++] & 63;
        else if (t > 239 && t < 365) {
            t = ((t & 7) << 18 | (buffer[start++] & 63) << 12 | (buffer[start++] & 63) << 6 | buffer[start++] & 63) - 0x10000;
            chunk[i++] = 0xD800 + (t >> 10);
            chunk[i++] = 0xDC00 + (t & 1023);
        } else
            chunk[i++] = (t & 15) << 12 | (buffer[start++] & 63) << 6 | buffer[start++] & 63;
        if (i > 8191) {
            (parts || (parts = [])).push(String.fromCharCode.apply(String, chunk));
            i = 0;
        }
    }
    if (parts) {
        if (i)
            parts.push(String.fromCharCode.apply(String, chunk.slice(0, i)));
        return parts.join("");
    }
    return String.fromCharCode.apply(String, chunk.slice(0, i));
};

/**
 * Writes a string as UTF8 bytes.
 * @param {string} string Source string
 * @param {Uint8Array} buffer Destination buffer
 * @param {number} offset Destination offset
 * @returns {number} Bytes written
 */
utf8.write = function utf8_write(string, buffer, offset) {
    var start = offset,
        c1, // character 1
        c2; // character 2
    for (var i = 0; i < string.length; ++i) {
        c1 = string.charCodeAt(i);
        if (c1 < 128) {
            buffer[offset++] = c1;
        } else if (c1 < 2048) {
            buffer[offset++] = c1 >> 6       | 192;
            buffer[offset++] = c1       & 63 | 128;
        } else if ((c1 & 0xFC00) === 0xD800 && ((c2 = string.charCodeAt(i + 1)) & 0xFC00) === 0xDC00) {
            c1 = 0x10000 + ((c1 & 0x03FF) << 10) + (c2 & 0x03FF);
            ++i;
            buffer[offset++] = c1 >> 18      | 240;
            buffer[offset++] = c1 >> 12 & 63 | 128;
            buffer[offset++] = c1 >> 6  & 63 | 128;
            buffer[offset++] = c1       & 63 | 128;
        } else {
            buffer[offset++] = c1 >> 12      | 224;
            buffer[offset++] = c1 >> 6  & 63 | 128;
            buffer[offset++] = c1       & 63 | 128;
        }
    }
    return offset - start;
};

},{}],8:[function(require,module,exports){
// minimal library entry point.

"use strict";
module.exports = require("./src/index-minimal");

},{"./src/index-minimal":9}],9:[function(require,module,exports){
"use strict";
var protobuf = exports;

/**
 * Build type, one of `"full"`, `"light"` or `"minimal"`.
 * @name build
 * @type {string}
 * @const
 */
protobuf.build = "minimal";

// Serialization
protobuf.Writer       = require("./writer");
protobuf.BufferWriter = require("./writer_buffer");
protobuf.Reader       = require("./reader");
protobuf.BufferReader = require("./reader_buffer");

// Utility
protobuf.util         = require("./util/minimal");
protobuf.rpc          = require("./rpc");
protobuf.roots        = require("./roots");
protobuf.configure    = configure;

/* istanbul ignore next */
/**
 * Reconfigures the library according to the environment.
 * @returns {undefined}
 */
function configure() {
    protobuf.util._configure();
    protobuf.Writer._configure(protobuf.BufferWriter);
    protobuf.Reader._configure(protobuf.BufferReader);
}

// Set up buffer utility according to the environment
configure();

},{"./reader":10,"./reader_buffer":11,"./roots":12,"./rpc":13,"./util/minimal":16,"./writer":17,"./writer_buffer":18}],10:[function(require,module,exports){
"use strict";
module.exports = Reader;

var util      = require("./util/minimal");

var BufferReader; // cyclic

var LongBits  = util.LongBits,
    utf8      = util.utf8;

/* istanbul ignore next */
function indexOutOfRange(reader, writeLength) {
    return RangeError("index out of range: " + reader.pos + " + " + (writeLength || 1) + " > " + reader.len);
}

/**
 * Constructs a new reader instance using the specified buffer.
 * @classdesc Wire format reader using `Uint8Array` if available, otherwise `Array`.
 * @constructor
 * @param {Uint8Array} buffer Buffer to read from
 */
function Reader(buffer) {

    /**
     * Read buffer.
     * @type {Uint8Array}
     */
    this.buf = buffer;

    /**
     * Read buffer position.
     * @type {number}
     */
    this.pos = 0;

    /**
     * Read buffer length.
     * @type {number}
     */
    this.len = buffer.length;
}

var create_array = typeof Uint8Array !== "undefined"
    ? function create_typed_array(buffer) {
        if (buffer instanceof Uint8Array || Array.isArray(buffer))
            return new Reader(buffer);
        throw Error("illegal buffer");
    }
    /* istanbul ignore next */
    : function create_array(buffer) {
        if (Array.isArray(buffer))
            return new Reader(buffer);
        throw Error("illegal buffer");
    };

var create = function create() {
    return util.Buffer
        ? function create_buffer_setup(buffer) {
            return (Reader.create = function create_buffer(buffer) {
                return util.Buffer.isBuffer(buffer)
                    ? new BufferReader(buffer)
                    /* istanbul ignore next */
                    : create_array(buffer);
            })(buffer);
        }
        /* istanbul ignore next */
        : create_array;
};

/**
 * Creates a new reader using the specified buffer.
 * @function
 * @param {Uint8Array|Buffer} buffer Buffer to read from
 * @returns {Reader|BufferReader} A {@link BufferReader} if `buffer` is a Buffer, otherwise a {@link Reader}
 * @throws {Error} If `buffer` is not a valid buffer
 */
Reader.create = create();

Reader.prototype._slice = util.Array.prototype.subarray || /* istanbul ignore next */ util.Array.prototype.slice;

/**
 * Reads a varint as an unsigned 32 bit value.
 * @function
 * @returns {number} Value read
 */
Reader.prototype.uint32 = (function read_uint32_setup() {
    var value = 4294967295; // optimizer type-hint, tends to deopt otherwise (?!)
    return function read_uint32() {
        value = (         this.buf[this.pos] & 127       ) >>> 0; if (this.buf[this.pos++] < 128) return value;
        value = (value | (this.buf[this.pos] & 127) <<  7) >>> 0; if (this.buf[this.pos++] < 128) return value;
        value = (value | (this.buf[this.pos] & 127) << 14) >>> 0; if (this.buf[this.pos++] < 128) return value;
        value = (value | (this.buf[this.pos] & 127) << 21) >>> 0; if (this.buf[this.pos++] < 128) return value;
        value = (value | (this.buf[this.pos] &  15) << 28) >>> 0; if (this.buf[this.pos++] < 128) return value;

        /* istanbul ignore if */
        if ((this.pos += 5) > this.len) {
            this.pos = this.len;
            throw indexOutOfRange(this, 10);
        }
        return value;
    };
})();

/**
 * Reads a varint as a signed 32 bit value.
 * @returns {number} Value read
 */
Reader.prototype.int32 = function read_int32() {
    return this.uint32() | 0;
};

/**
 * Reads a zig-zag encoded varint as a signed 32 bit value.
 * @returns {number} Value read
 */
Reader.prototype.sint32 = function read_sint32() {
    var value = this.uint32();
    return value >>> 1 ^ -(value & 1) | 0;
};

/* eslint-disable no-invalid-this */

function readLongVarint() {
    // tends to deopt with local vars for octet etc.
    var bits = new LongBits(0, 0);
    var i = 0;
    if (this.len - this.pos > 4) { // fast route (lo)
        for (; i < 4; ++i) {
            // 1st..4th
            bits.lo = (bits.lo | (this.buf[this.pos] & 127) << i * 7) >>> 0;
            if (this.buf[this.pos++] < 128)
                return bits;
        }
        // 5th
        bits.lo = (bits.lo | (this.buf[this.pos] & 127) << 28) >>> 0;
        bits.hi = (bits.hi | (this.buf[this.pos] & 127) >>  4) >>> 0;
        if (this.buf[this.pos++] < 128)
            return bits;
        i = 0;
    } else {
        for (; i < 3; ++i) {
            /* istanbul ignore if */
            if (this.pos >= this.len)
                throw indexOutOfRange(this);
            // 1st..3th
            bits.lo = (bits.lo | (this.buf[this.pos] & 127) << i * 7) >>> 0;
            if (this.buf[this.pos++] < 128)
                return bits;
        }
        // 4th
        bits.lo = (bits.lo | (this.buf[this.pos++] & 127) << i * 7) >>> 0;
        return bits;
    }
    if (this.len - this.pos > 4) { // fast route (hi)
        for (; i < 5; ++i) {
            // 6th..10th
            bits.hi = (bits.hi | (this.buf[this.pos] & 127) << i * 7 + 3) >>> 0;
            if (this.buf[this.pos++] < 128)
                return bits;
        }
    } else {
        for (; i < 5; ++i) {
            /* istanbul ignore if */
            if (this.pos >= this.len)
                throw indexOutOfRange(this);
            // 6th..10th
            bits.hi = (bits.hi | (this.buf[this.pos] & 127) << i * 7 + 3) >>> 0;
            if (this.buf[this.pos++] < 128)
                return bits;
        }
    }
    /* istanbul ignore next */
    throw Error("invalid varint encoding");
}

/* eslint-enable no-invalid-this */

/**
 * Reads a varint as a signed 64 bit value.
 * @name Reader#int64
 * @function
 * @returns {Long} Value read
 */

/**
 * Reads a varint as an unsigned 64 bit value.
 * @name Reader#uint64
 * @function
 * @returns {Long} Value read
 */

/**
 * Reads a zig-zag encoded varint as a signed 64 bit value.
 * @name Reader#sint64
 * @function
 * @returns {Long} Value read
 */

/**
 * Reads a varint as a boolean.
 * @returns {boolean} Value read
 */
Reader.prototype.bool = function read_bool() {
    return this.uint32() !== 0;
};

function readFixed32_end(buf, end) { // note that this uses `end`, not `pos`
    return (buf[end - 4]
          | buf[end - 3] << 8
          | buf[end - 2] << 16
          | buf[end - 1] << 24) >>> 0;
}

/**
 * Reads fixed 32 bits as an unsigned 32 bit integer.
 * @returns {number} Value read
 */
Reader.prototype.fixed32 = function read_fixed32() {

    /* istanbul ignore if */
    if (this.pos + 4 > this.len)
        throw indexOutOfRange(this, 4);

    return readFixed32_end(this.buf, this.pos += 4);
};

/**
 * Reads fixed 32 bits as a signed 32 bit integer.
 * @returns {number} Value read
 */
Reader.prototype.sfixed32 = function read_sfixed32() {

    /* istanbul ignore if */
    if (this.pos + 4 > this.len)
        throw indexOutOfRange(this, 4);

    return readFixed32_end(this.buf, this.pos += 4) | 0;
};

/* eslint-disable no-invalid-this */

function readFixed64(/* this: Reader */) {

    /* istanbul ignore if */
    if (this.pos + 8 > this.len)
        throw indexOutOfRange(this, 8);

    return new LongBits(readFixed32_end(this.buf, this.pos += 4), readFixed32_end(this.buf, this.pos += 4));
}

/* eslint-enable no-invalid-this */

/**
 * Reads fixed 64 bits.
 * @name Reader#fixed64
 * @function
 * @returns {Long} Value read
 */

/**
 * Reads zig-zag encoded fixed 64 bits.
 * @name Reader#sfixed64
 * @function
 * @returns {Long} Value read
 */

/**
 * Reads a float (32 bit) as a number.
 * @function
 * @returns {number} Value read
 */
Reader.prototype.float = function read_float() {

    /* istanbul ignore if */
    if (this.pos + 4 > this.len)
        throw indexOutOfRange(this, 4);

    var value = util.float.readFloatLE(this.buf, this.pos);
    this.pos += 4;
    return value;
};

/**
 * Reads a double (64 bit float) as a number.
 * @function
 * @returns {number} Value read
 */
Reader.prototype.double = function read_double() {

    /* istanbul ignore if */
    if (this.pos + 8 > this.len)
        throw indexOutOfRange(this, 4);

    var value = util.float.readDoubleLE(this.buf, this.pos);
    this.pos += 8;
    return value;
};

/**
 * Reads a sequence of bytes preceeded by its length as a varint.
 * @returns {Uint8Array} Value read
 */
Reader.prototype.bytes = function read_bytes() {
    var length = this.uint32(),
        start  = this.pos,
        end    = this.pos + length;

    /* istanbul ignore if */
    if (end > this.len)
        throw indexOutOfRange(this, length);

    this.pos += length;
    if (Array.isArray(this.buf)) // plain array
        return this.buf.slice(start, end);
    return start === end // fix for IE 10/Win8 and others' subarray returning array of size 1
        ? new this.buf.constructor(0)
        : this._slice.call(this.buf, start, end);
};

/**
 * Reads a string preceeded by its byte length as a varint.
 * @returns {string} Value read
 */
Reader.prototype.string = function read_string() {
    var bytes = this.bytes();
    return utf8.read(bytes, 0, bytes.length);
};

/**
 * Skips the specified number of bytes if specified, otherwise skips a varint.
 * @param {number} [length] Length if known, otherwise a varint is assumed
 * @returns {Reader} `this`
 */
Reader.prototype.skip = function skip(length) {
    if (typeof length === "number") {
        /* istanbul ignore if */
        if (this.pos + length > this.len)
            throw indexOutOfRange(this, length);
        this.pos += length;
    } else {
        do {
            /* istanbul ignore if */
            if (this.pos >= this.len)
                throw indexOutOfRange(this);
        } while (this.buf[this.pos++] & 128);
    }
    return this;
};

/**
 * Skips the next element of the specified wire type.
 * @param {number} wireType Wire type received
 * @returns {Reader} `this`
 */
Reader.prototype.skipType = function(wireType) {
    switch (wireType) {
        case 0:
            this.skip();
            break;
        case 1:
            this.skip(8);
            break;
        case 2:
            this.skip(this.uint32());
            break;
        case 3:
            while ((wireType = this.uint32() & 7) !== 4) {
                this.skipType(wireType);
            }
            break;
        case 5:
            this.skip(4);
            break;

        /* istanbul ignore next */
        default:
            throw Error("invalid wire type " + wireType + " at offset " + this.pos);
    }
    return this;
};

Reader._configure = function(BufferReader_) {
    BufferReader = BufferReader_;
    Reader.create = create();
    BufferReader._configure();

    var fn = util.Long ? "toLong" : /* istanbul ignore next */ "toNumber";
    util.merge(Reader.prototype, {

        int64: function read_int64() {
            return readLongVarint.call(this)[fn](false);
        },

        uint64: function read_uint64() {
            return readLongVarint.call(this)[fn](true);
        },

        sint64: function read_sint64() {
            return readLongVarint.call(this).zzDecode()[fn](false);
        },

        fixed64: function read_fixed64() {
            return readFixed64.call(this)[fn](true);
        },

        sfixed64: function read_sfixed64() {
            return readFixed64.call(this)[fn](false);
        }

    });
};

},{"./util/minimal":16}],11:[function(require,module,exports){
"use strict";
module.exports = BufferReader;

// extends Reader
var Reader = require("./reader");
(BufferReader.prototype = Object.create(Reader.prototype)).constructor = BufferReader;

var util = require("./util/minimal");

/**
 * Constructs a new buffer reader instance.
 * @classdesc Wire format reader using node buffers.
 * @extends Reader
 * @constructor
 * @param {Buffer} buffer Buffer to read from
 */
function BufferReader(buffer) {
    Reader.call(this, buffer);

    /**
     * Read buffer.
     * @name BufferReader#buf
     * @type {Buffer}
     */
}

BufferReader._configure = function () {
    /* istanbul ignore else */
    if (util.Buffer)
        BufferReader.prototype._slice = util.Buffer.prototype.slice;
};


/**
 * @override
 */
BufferReader.prototype.string = function read_string_buffer() {
    var len = this.uint32(); // modifies pos
    return this.buf.utf8Slice
        ? this.buf.utf8Slice(this.pos, this.pos = Math.min(this.pos + len, this.len))
        : this.buf.toString("utf-8", this.pos, this.pos = Math.min(this.pos + len, this.len));
};

/**
 * Reads a sequence of bytes preceeded by its length as a varint.
 * @name BufferReader#bytes
 * @function
 * @returns {Buffer} Value read
 */

BufferReader._configure();

},{"./reader":10,"./util/minimal":16}],12:[function(require,module,exports){
"use strict";
module.exports = {};

/**
 * Named roots.
 * This is where pbjs stores generated structures (the option `-r, --root` specifies a name).
 * Can also be used manually to make roots available accross modules.
 * @name roots
 * @type {Object.<string,Root>}
 * @example
 * // pbjs -r myroot -o compiled.js ...
 *
 * // in another module:
 * require("./compiled.js");
 *
 * // in any subsequent module:
 * var root = protobuf.roots["myroot"];
 */

},{}],13:[function(require,module,exports){
"use strict";

/**
 * Streaming RPC helpers.
 * @namespace
 */
var rpc = exports;

/**
 * RPC implementation passed to {@link Service#create} performing a service request on network level, i.e. by utilizing http requests or websockets.
 * @typedef RPCImpl
 * @type {function}
 * @param {Method|rpc.ServiceMethod<Message<{}>,Message<{}>>} method Reflected or static method being called
 * @param {Uint8Array} requestData Request data
 * @param {RPCImplCallback} callback Callback function
 * @returns {undefined}
 * @example
 * function rpcImpl(method, requestData, callback) {
 *     if (protobuf.util.lcFirst(method.name) !== "myMethod") // compatible with static code
 *         throw Error("no such method");
 *     asynchronouslyObtainAResponse(requestData, function(err, responseData) {
 *         callback(err, responseData);
 *     });
 * }
 */

/**
 * Node-style callback as used by {@link RPCImpl}.
 * @typedef RPCImplCallback
 * @type {function}
 * @param {Error|null} error Error, if any, otherwise `null`
 * @param {Uint8Array|null} [response] Response data or `null` to signal end of stream, if there hasn't been an error
 * @returns {undefined}
 */

rpc.Service = require("./rpc/service");

},{"./rpc/service":14}],14:[function(require,module,exports){
"use strict";
module.exports = Service;

var util = require("../util/minimal");

// Extends EventEmitter
(Service.prototype = Object.create(util.EventEmitter.prototype)).constructor = Service;

/**
 * A service method callback as used by {@link rpc.ServiceMethod|ServiceMethod}.
 *
 * Differs from {@link RPCImplCallback} in that it is an actual callback of a service method which may not return `response = null`.
 * @typedef rpc.ServiceMethodCallback
 * @template TRes extends Message<TRes>
 * @type {function}
 * @param {Error|null} error Error, if any
 * @param {TRes} [response] Response message
 * @returns {undefined}
 */

/**
 * A service method part of a {@link rpc.Service} as created by {@link Service.create}.
 * @typedef rpc.ServiceMethod
 * @template TReq extends Message<TReq>
 * @template TRes extends Message<TRes>
 * @type {function}
 * @param {TReq|Properties<TReq>} request Request message or plain object
 * @param {rpc.ServiceMethodCallback<TRes>} [callback] Node-style callback called with the error, if any, and the response message
 * @returns {Promise<Message<TRes>>} Promise if `callback` has been omitted, otherwise `undefined`
 */

/**
 * Constructs a new RPC service instance.
 * @classdesc An RPC service as returned by {@link Service#create}.
 * @exports rpc.Service
 * @extends util.EventEmitter
 * @constructor
 * @param {RPCImpl} rpcImpl RPC implementation
 * @param {boolean} [requestDelimited=false] Whether requests are length-delimited
 * @param {boolean} [responseDelimited=false] Whether responses are length-delimited
 */
function Service(rpcImpl, requestDelimited, responseDelimited) {

    if (typeof rpcImpl !== "function")
        throw TypeError("rpcImpl must be a function");

    util.EventEmitter.call(this);

    /**
     * RPC implementation. Becomes `null` once the service is ended.
     * @type {RPCImpl|null}
     */
    this.rpcImpl = rpcImpl;

    /**
     * Whether requests are length-delimited.
     * @type {boolean}
     */
    this.requestDelimited = Boolean(requestDelimited);

    /**
     * Whether responses are length-delimited.
     * @type {boolean}
     */
    this.responseDelimited = Boolean(responseDelimited);
}

/**
 * Calls a service method through {@link rpc.Service#rpcImpl|rpcImpl}.
 * @param {Method|rpc.ServiceMethod<TReq,TRes>} method Reflected or static method
 * @param {Constructor<TReq>} requestCtor Request constructor
 * @param {Constructor<TRes>} responseCtor Response constructor
 * @param {TReq|Properties<TReq>} request Request message or plain object
 * @param {rpc.ServiceMethodCallback<TRes>} callback Service callback
 * @returns {undefined}
 * @template TReq extends Message<TReq>
 * @template TRes extends Message<TRes>
 */
Service.prototype.rpcCall = function rpcCall(method, requestCtor, responseCtor, request, callback) {

    if (!request)
        throw TypeError("request must be specified");

    var self = this;
    if (!callback)
        return util.asPromise(rpcCall, self, method, requestCtor, responseCtor, request);

    if (!self.rpcImpl) {
        setTimeout(function() { callback(Error("already ended")); }, 0);
        return undefined;
    }

    try {
        return self.rpcImpl(
            method,
            requestCtor[self.requestDelimited ? "encodeDelimited" : "encode"](request).finish(),
            function rpcCallback(err, response) {

                if (err) {
                    self.emit("error", err, method);
                    return callback(err);
                }

                if (response === null) {
                    self.end(/* endedByRPC */ true);
                    return undefined;
                }

                if (!(response instanceof responseCtor)) {
                    try {
                        response = responseCtor[self.responseDelimited ? "decodeDelimited" : "decode"](response);
                    } catch (err) {
                        self.emit("error", err, method);
                        return callback(err);
                    }
                }

                self.emit("data", response, method);
                return callback(null, response);
            }
        );
    } catch (err) {
        self.emit("error", err, method);
        setTimeout(function() { callback(err); }, 0);
        return undefined;
    }
};

/**
 * Ends this service and emits the `end` event.
 * @param {boolean} [endedByRPC=false] Whether the service has been ended by the RPC implementation.
 * @returns {rpc.Service} `this`
 */
Service.prototype.end = function end(endedByRPC) {
    if (this.rpcImpl) {
        if (!endedByRPC) // signal end to rpcImpl
            this.rpcImpl(null, null, null);
        this.rpcImpl = null;
        this.emit("end").off();
    }
    return this;
};

},{"../util/minimal":16}],15:[function(require,module,exports){
"use strict";
module.exports = LongBits;

var util = require("../util/minimal");

/**
 * Constructs new long bits.
 * @classdesc Helper class for working with the low and high bits of a 64 bit value.
 * @memberof util
 * @constructor
 * @param {number} lo Low 32 bits, unsigned
 * @param {number} hi High 32 bits, unsigned
 */
function LongBits(lo, hi) {

    // note that the casts below are theoretically unnecessary as of today, but older statically
    // generated converter code might still call the ctor with signed 32bits. kept for compat.

    /**
     * Low bits.
     * @type {number}
     */
    this.lo = lo >>> 0;

    /**
     * High bits.
     * @type {number}
     */
    this.hi = hi >>> 0;
}

/**
 * Zero bits.
 * @memberof util.LongBits
 * @type {util.LongBits}
 */
var zero = LongBits.zero = new LongBits(0, 0);

zero.toNumber = function() { return 0; };
zero.zzEncode = zero.zzDecode = function() { return this; };
zero.length = function() { return 1; };

/**
 * Zero hash.
 * @memberof util.LongBits
 * @type {string}
 */
var zeroHash = LongBits.zeroHash = "\0\0\0\0\0\0\0\0";

/**
 * Constructs new long bits from the specified number.
 * @param {number} value Value
 * @returns {util.LongBits} Instance
 */
LongBits.fromNumber = function fromNumber(value) {
    if (value === 0)
        return zero;
    var sign = value < 0;
    if (sign)
        value = -value;
    var lo = value >>> 0,
        hi = (value - lo) / 4294967296 >>> 0;
    if (sign) {
        hi = ~hi >>> 0;
        lo = ~lo >>> 0;
        if (++lo > 4294967295) {
            lo = 0;
            if (++hi > 4294967295)
                hi = 0;
        }
    }
    return new LongBits(lo, hi);
};

/**
 * Constructs new long bits from a number, long or string.
 * @param {Long|number|string} value Value
 * @returns {util.LongBits} Instance
 */
LongBits.from = function from(value) {
    if (typeof value === "number")
        return LongBits.fromNumber(value);
    if (util.isString(value)) {
        /* istanbul ignore else */
        if (util.Long)
            value = util.Long.fromString(value);
        else
            return LongBits.fromNumber(parseInt(value, 10));
    }
    return value.low || value.high ? new LongBits(value.low >>> 0, value.high >>> 0) : zero;
};

/**
 * Converts this long bits to a possibly unsafe JavaScript number.
 * @param {boolean} [unsigned=false] Whether unsigned or not
 * @returns {number} Possibly unsafe number
 */
LongBits.prototype.toNumber = function toNumber(unsigned) {
    if (!unsigned && this.hi >>> 31) {
        var lo = ~this.lo + 1 >>> 0,
            hi = ~this.hi     >>> 0;
        if (!lo)
            hi = hi + 1 >>> 0;
        return -(lo + hi * 4294967296);
    }
    return this.lo + this.hi * 4294967296;
};

/**
 * Converts this long bits to a long.
 * @param {boolean} [unsigned=false] Whether unsigned or not
 * @returns {Long} Long
 */
LongBits.prototype.toLong = function toLong(unsigned) {
    return util.Long
        ? new util.Long(this.lo | 0, this.hi | 0, Boolean(unsigned))
        /* istanbul ignore next */
        : { low: this.lo | 0, high: this.hi | 0, unsigned: Boolean(unsigned) };
};

var charCodeAt = String.prototype.charCodeAt;

/**
 * Constructs new long bits from the specified 8 characters long hash.
 * @param {string} hash Hash
 * @returns {util.LongBits} Bits
 */
LongBits.fromHash = function fromHash(hash) {
    if (hash === zeroHash)
        return zero;
    return new LongBits(
        ( charCodeAt.call(hash, 0)
        | charCodeAt.call(hash, 1) << 8
        | charCodeAt.call(hash, 2) << 16
        | charCodeAt.call(hash, 3) << 24) >>> 0
    ,
        ( charCodeAt.call(hash, 4)
        | charCodeAt.call(hash, 5) << 8
        | charCodeAt.call(hash, 6) << 16
        | charCodeAt.call(hash, 7) << 24) >>> 0
    );
};

/**
 * Converts this long bits to a 8 characters long hash.
 * @returns {string} Hash
 */
LongBits.prototype.toHash = function toHash() {
    return String.fromCharCode(
        this.lo        & 255,
        this.lo >>> 8  & 255,
        this.lo >>> 16 & 255,
        this.lo >>> 24      ,
        this.hi        & 255,
        this.hi >>> 8  & 255,
        this.hi >>> 16 & 255,
        this.hi >>> 24
    );
};

/**
 * Zig-zag encodes this long bits.
 * @returns {util.LongBits} `this`
 */
LongBits.prototype.zzEncode = function zzEncode() {
    var mask =   this.hi >> 31;
    this.hi  = ((this.hi << 1 | this.lo >>> 31) ^ mask) >>> 0;
    this.lo  = ( this.lo << 1                   ^ mask) >>> 0;
    return this;
};

/**
 * Zig-zag decodes this long bits.
 * @returns {util.LongBits} `this`
 */
LongBits.prototype.zzDecode = function zzDecode() {
    var mask = -(this.lo & 1);
    this.lo  = ((this.lo >>> 1 | this.hi << 31) ^ mask) >>> 0;
    this.hi  = ( this.hi >>> 1                  ^ mask) >>> 0;
    return this;
};

/**
 * Calculates the length of this longbits when encoded as a varint.
 * @returns {number} Length
 */
LongBits.prototype.length = function length() {
    var part0 =  this.lo,
        part1 = (this.lo >>> 28 | this.hi << 4) >>> 0,
        part2 =  this.hi >>> 24;
    return part2 === 0
         ? part1 === 0
           ? part0 < 16384
             ? part0 < 128 ? 1 : 2
             : part0 < 2097152 ? 3 : 4
           : part1 < 16384
             ? part1 < 128 ? 5 : 6
             : part1 < 2097152 ? 7 : 8
         : part2 < 128 ? 9 : 10;
};

},{"../util/minimal":16}],16:[function(require,module,exports){
(function (global){(function (){
"use strict";
var util = exports;

// used to return a Promise where callback is omitted
util.asPromise = require("@protobufjs/aspromise");

// converts to / from base64 encoded strings
util.base64 = require("@protobufjs/base64");

// base class of rpc.Service
util.EventEmitter = require("@protobufjs/eventemitter");

// float handling accross browsers
util.float = require("@protobufjs/float");

// requires modules optionally and hides the call from bundlers
util.inquire = require("@protobufjs/inquire");

// converts to / from utf8 encoded strings
util.utf8 = require("@protobufjs/utf8");

// provides a node-like buffer pool in the browser
util.pool = require("@protobufjs/pool");

// utility to work with the low and high bits of a 64 bit value
util.LongBits = require("./longbits");

/**
 * Whether running within node or not.
 * @memberof util
 * @type {boolean}
 */
util.isNode = Boolean(typeof global !== "undefined"
                   && global
                   && global.process
                   && global.process.versions
                   && global.process.versions.node);

/**
 * Global object reference.
 * @memberof util
 * @type {Object}
 */
util.global = util.isNode && global
           || typeof window !== "undefined" && window
           || typeof self   !== "undefined" && self
           || this; // eslint-disable-line no-invalid-this

/**
 * An immuable empty array.
 * @memberof util
 * @type {Array.<*>}
 * @const
 */
util.emptyArray = Object.freeze ? Object.freeze([]) : /* istanbul ignore next */ []; // used on prototypes

/**
 * An immutable empty object.
 * @type {Object}
 * @const
 */
util.emptyObject = Object.freeze ? Object.freeze({}) : /* istanbul ignore next */ {}; // used on prototypes

/**
 * Tests if the specified value is an integer.
 * @function
 * @param {*} value Value to test
 * @returns {boolean} `true` if the value is an integer
 */
util.isInteger = Number.isInteger || /* istanbul ignore next */ function isInteger(value) {
    return typeof value === "number" && isFinite(value) && Math.floor(value) === value;
};

/**
 * Tests if the specified value is a string.
 * @param {*} value Value to test
 * @returns {boolean} `true` if the value is a string
 */
util.isString = function isString(value) {
    return typeof value === "string" || value instanceof String;
};

/**
 * Tests if the specified value is a non-null object.
 * @param {*} value Value to test
 * @returns {boolean} `true` if the value is a non-null object
 */
util.isObject = function isObject(value) {
    return value && typeof value === "object";
};

/**
 * Checks if a property on a message is considered to be present.
 * This is an alias of {@link util.isSet}.
 * @function
 * @param {Object} obj Plain object or message instance
 * @param {string} prop Property name
 * @returns {boolean} `true` if considered to be present, otherwise `false`
 */
util.isset =

/**
 * Checks if a property on a message is considered to be present.
 * @param {Object} obj Plain object or message instance
 * @param {string} prop Property name
 * @returns {boolean} `true` if considered to be present, otherwise `false`
 */
util.isSet = function isSet(obj, prop) {
    var value = obj[prop];
    if (value != null && obj.hasOwnProperty(prop)) // eslint-disable-line eqeqeq, no-prototype-builtins
        return typeof value !== "object" || (Array.isArray(value) ? value.length : Object.keys(value).length) > 0;
    return false;
};

/**
 * Any compatible Buffer instance.
 * This is a minimal stand-alone definition of a Buffer instance. The actual type is that exported by node's typings.
 * @interface Buffer
 * @extends Uint8Array
 */

/**
 * Node's Buffer class if available.
 * @type {Constructor<Buffer>}
 */
util.Buffer = (function() {
    try {
        var Buffer = util.inquire("buffer").Buffer;
        // refuse to use non-node buffers if not explicitly assigned (perf reasons):
        return Buffer.prototype.utf8Write ? Buffer : /* istanbul ignore next */ null;
    } catch (e) {
        /* istanbul ignore next */
        return null;
    }
})();

// Internal alias of or polyfull for Buffer.from.
util._Buffer_from = null;

// Internal alias of or polyfill for Buffer.allocUnsafe.
util._Buffer_allocUnsafe = null;

/**
 * Creates a new buffer of whatever type supported by the environment.
 * @param {number|number[]} [sizeOrArray=0] Buffer size or number array
 * @returns {Uint8Array|Buffer} Buffer
 */
util.newBuffer = function newBuffer(sizeOrArray) {
    /* istanbul ignore next */
    return typeof sizeOrArray === "number"
        ? util.Buffer
            ? util._Buffer_allocUnsafe(sizeOrArray)
            : new util.Array(sizeOrArray)
        : util.Buffer
            ? util._Buffer_from(sizeOrArray)
            : typeof Uint8Array === "undefined"
                ? sizeOrArray
                : new Uint8Array(sizeOrArray);
};

/**
 * Array implementation used in the browser. `Uint8Array` if supported, otherwise `Array`.
 * @type {Constructor<Uint8Array>}
 */
util.Array = typeof Uint8Array !== "undefined" ? Uint8Array /* istanbul ignore next */ : Array;

/**
 * Any compatible Long instance.
 * This is a minimal stand-alone definition of a Long instance. The actual type is that exported by long.js.
 * @interface Long
 * @property {number} low Low bits
 * @property {number} high High bits
 * @property {boolean} unsigned Whether unsigned or not
 */

/**
 * Long.js's Long class if available.
 * @type {Constructor<Long>}
 */
util.Long = /* istanbul ignore next */ util.global.dcodeIO && /* istanbul ignore next */ util.global.dcodeIO.Long
         || /* istanbul ignore next */ util.global.Long
         || util.inquire("long");

/**
 * Regular expression used to verify 2 bit (`bool`) map keys.
 * @type {RegExp}
 * @const
 */
util.key2Re = /^true|false|0|1$/;

/**
 * Regular expression used to verify 32 bit (`int32` etc.) map keys.
 * @type {RegExp}
 * @const
 */
util.key32Re = /^-?(?:0|[1-9][0-9]*)$/;

/**
 * Regular expression used to verify 64 bit (`int64` etc.) map keys.
 * @type {RegExp}
 * @const
 */
util.key64Re = /^(?:[\\x00-\\xff]{8}|-?(?:0|[1-9][0-9]*))$/;

/**
 * Converts a number or long to an 8 characters long hash string.
 * @param {Long|number} value Value to convert
 * @returns {string} Hash
 */
util.longToHash = function longToHash(value) {
    return value
        ? util.LongBits.from(value).toHash()
        : util.LongBits.zeroHash;
};

/**
 * Converts an 8 characters long hash string to a long or number.
 * @param {string} hash Hash
 * @param {boolean} [unsigned=false] Whether unsigned or not
 * @returns {Long|number} Original value
 */
util.longFromHash = function longFromHash(hash, unsigned) {
    var bits = util.LongBits.fromHash(hash);
    if (util.Long)
        return util.Long.fromBits(bits.lo, bits.hi, unsigned);
    return bits.toNumber(Boolean(unsigned));
};

/**
 * Merges the properties of the source object into the destination object.
 * @memberof util
 * @param {Object.<string,*>} dst Destination object
 * @param {Object.<string,*>} src Source object
 * @param {boolean} [ifNotSet=false] Merges only if the key is not already set
 * @returns {Object.<string,*>} Destination object
 */
function merge(dst, src, ifNotSet) { // used by converters
    for (var keys = Object.keys(src), i = 0; i < keys.length; ++i)
        if (dst[keys[i]] === undefined || !ifNotSet)
            dst[keys[i]] = src[keys[i]];
    return dst;
}

util.merge = merge;

/**
 * Converts the first character of a string to lower case.
 * @param {string} str String to convert
 * @returns {string} Converted string
 */
util.lcFirst = function lcFirst(str) {
    return str.charAt(0).toLowerCase() + str.substring(1);
};

/**
 * Creates a custom error constructor.
 * @memberof util
 * @param {string} name Error name
 * @returns {Constructor<Error>} Custom error constructor
 */
function newError(name) {

    function CustomError(message, properties) {

        if (!(this instanceof CustomError))
            return new CustomError(message, properties);

        // Error.call(this, message);
        // ^ just returns a new error instance because the ctor can be called as a function

        Object.defineProperty(this, "message", { get: function() { return message; } });

        /* istanbul ignore next */
        if (Error.captureStackTrace) // node
            Error.captureStackTrace(this, CustomError);
        else
            Object.defineProperty(this, "stack", { value: new Error().stack || "" });

        if (properties)
            merge(this, properties);
    }

    (CustomError.prototype = Object.create(Error.prototype)).constructor = CustomError;

    Object.defineProperty(CustomError.prototype, "name", { get: function() { return name; } });

    CustomError.prototype.toString = function toString() {
        return this.name + ": " + this.message;
    };

    return CustomError;
}

util.newError = newError;

/**
 * Constructs a new protocol error.
 * @classdesc Error subclass indicating a protocol specifc error.
 * @memberof util
 * @extends Error
 * @template T extends Message<T>
 * @constructor
 * @param {string} message Error message
 * @param {Object.<string,*>} [properties] Additional properties
 * @example
 * try {
 *     MyMessage.decode(someBuffer); // throws if required fields are missing
 * } catch (e) {
 *     if (e instanceof ProtocolError && e.instance)
 *         console.log("decoded so far: " + JSON.stringify(e.instance));
 * }
 */
util.ProtocolError = newError("ProtocolError");

/**
 * So far decoded message instance.
 * @name util.ProtocolError#instance
 * @type {Message<T>}
 */

/**
 * A OneOf getter as returned by {@link util.oneOfGetter}.
 * @typedef OneOfGetter
 * @type {function}
 * @returns {string|undefined} Set field name, if any
 */

/**
 * Builds a getter for a oneof's present field name.
 * @param {string[]} fieldNames Field names
 * @returns {OneOfGetter} Unbound getter
 */
util.oneOfGetter = function getOneOf(fieldNames) {
    var fieldMap = {};
    for (var i = 0; i < fieldNames.length; ++i)
        fieldMap[fieldNames[i]] = 1;

    /**
     * @returns {string|undefined} Set field name, if any
     * @this Object
     * @ignore
     */
    return function() { // eslint-disable-line consistent-return
        for (var keys = Object.keys(this), i = keys.length - 1; i > -1; --i)
            if (fieldMap[keys[i]] === 1 && this[keys[i]] !== undefined && this[keys[i]] !== null)
                return keys[i];
    };
};

/**
 * A OneOf setter as returned by {@link util.oneOfSetter}.
 * @typedef OneOfSetter
 * @type {function}
 * @param {string|undefined} value Field name
 * @returns {undefined}
 */

/**
 * Builds a setter for a oneof's present field name.
 * @param {string[]} fieldNames Field names
 * @returns {OneOfSetter} Unbound setter
 */
util.oneOfSetter = function setOneOf(fieldNames) {

    /**
     * @param {string} name Field name
     * @returns {undefined}
     * @this Object
     * @ignore
     */
    return function(name) {
        for (var i = 0; i < fieldNames.length; ++i)
            if (fieldNames[i] !== name)
                delete this[fieldNames[i]];
    };
};

/**
 * Default conversion options used for {@link Message#toJSON} implementations.
 *
 * These options are close to proto3's JSON mapping with the exception that internal types like Any are handled just like messages. More precisely:
 *
 * - Longs become strings
 * - Enums become string keys
 * - Bytes become base64 encoded strings
 * - (Sub-)Messages become plain objects
 * - Maps become plain objects with all string keys
 * - Repeated fields become arrays
 * - NaN and Infinity for float and double fields become strings
 *
 * @type {IConversionOptions}
 * @see https://developers.google.com/protocol-buffers/docs/proto3?hl=en#json
 */
util.toJSONOptions = {
    longs: String,
    enums: String,
    bytes: String,
    json: true
};

// Sets up buffer utility according to the environment (called in index-minimal)
util._configure = function() {
    var Buffer = util.Buffer;
    /* istanbul ignore if */
    if (!Buffer) {
        util._Buffer_from = util._Buffer_allocUnsafe = null;
        return;
    }
    // because node 4.x buffers are incompatible & immutable
    // see: https://github.com/dcodeIO/protobuf.js/pull/665
    util._Buffer_from = Buffer.from !== Uint8Array.from && Buffer.from ||
        /* istanbul ignore next */
        function Buffer_from(value, encoding) {
            return new Buffer(value, encoding);
        };
    util._Buffer_allocUnsafe = Buffer.allocUnsafe ||
        /* istanbul ignore next */
        function Buffer_allocUnsafe(size) {
            return new Buffer(size);
        };
};

}).call(this)}).call(this,typeof global !== "undefined" ? global : typeof self !== "undefined" ? self : typeof window !== "undefined" ? window : {})
},{"./longbits":15,"@protobufjs/aspromise":1,"@protobufjs/base64":2,"@protobufjs/eventemitter":3,"@protobufjs/float":4,"@protobufjs/inquire":5,"@protobufjs/pool":6,"@protobufjs/utf8":7}],17:[function(require,module,exports){
"use strict";
module.exports = Writer;

var util      = require("./util/minimal");

var BufferWriter; // cyclic

var LongBits  = util.LongBits,
    base64    = util.base64,
    utf8      = util.utf8;

/**
 * Constructs a new writer operation instance.
 * @classdesc Scheduled writer operation.
 * @constructor
 * @param {function(*, Uint8Array, number)} fn Function to call
 * @param {number} len Value byte length
 * @param {*} val Value to write
 * @ignore
 */
function Op(fn, len, val) {

    /**
     * Function to call.
     * @type {function(Uint8Array, number, *)}
     */
    this.fn = fn;

    /**
     * Value byte length.
     * @type {number}
     */
    this.len = len;

    /**
     * Next operation.
     * @type {Writer.Op|undefined}
     */
    this.next = undefined;

    /**
     * Value to write.
     * @type {*}
     */
    this.val = val; // type varies
}

/* istanbul ignore next */
function noop() {} // eslint-disable-line no-empty-function

/**
 * Constructs a new writer state instance.
 * @classdesc Copied writer state.
 * @memberof Writer
 * @constructor
 * @param {Writer} writer Writer to copy state from
 * @ignore
 */
function State(writer) {

    /**
     * Current head.
     * @type {Writer.Op}
     */
    this.head = writer.head;

    /**
     * Current tail.
     * @type {Writer.Op}
     */
    this.tail = writer.tail;

    /**
     * Current buffer length.
     * @type {number}
     */
    this.len = writer.len;

    /**
     * Next state.
     * @type {State|null}
     */
    this.next = writer.states;
}

/**
 * Constructs a new writer instance.
 * @classdesc Wire format writer using `Uint8Array` if available, otherwise `Array`.
 * @constructor
 */
function Writer() {

    /**
     * Current length.
     * @type {number}
     */
    this.len = 0;

    /**
     * Operations head.
     * @type {Object}
     */
    this.head = new Op(noop, 0, 0);

    /**
     * Operations tail
     * @type {Object}
     */
    this.tail = this.head;

    /**
     * Linked forked states.
     * @type {Object|null}
     */
    this.states = null;

    // When a value is written, the writer calculates its byte length and puts it into a linked
    // list of operations to perform when finish() is called. This both allows us to allocate
    // buffers of the exact required size and reduces the amount of work we have to do compared
    // to first calculating over objects and then encoding over objects. In our case, the encoding
    // part is just a linked list walk calling operations with already prepared values.
}

var create = function create() {
    return util.Buffer
        ? function create_buffer_setup() {
            return (Writer.create = function create_buffer() {
                return new BufferWriter();
            })();
        }
        /* istanbul ignore next */
        : function create_array() {
            return new Writer();
        };
};

/**
 * Creates a new writer.
 * @function
 * @returns {BufferWriter|Writer} A {@link BufferWriter} when Buffers are supported, otherwise a {@link Writer}
 */
Writer.create = create();

/**
 * Allocates a buffer of the specified size.
 * @param {number} size Buffer size
 * @returns {Uint8Array} Buffer
 */
Writer.alloc = function alloc(size) {
    return new util.Array(size);
};

// Use Uint8Array buffer pool in the browser, just like node does with buffers
/* istanbul ignore else */
if (util.Array !== Array)
    Writer.alloc = util.pool(Writer.alloc, util.Array.prototype.subarray);

/**
 * Pushes a new operation to the queue.
 * @param {function(Uint8Array, number, *)} fn Function to call
 * @param {number} len Value byte length
 * @param {number} val Value to write
 * @returns {Writer} `this`
 * @private
 */
Writer.prototype._push = function push(fn, len, val) {
    this.tail = this.tail.next = new Op(fn, len, val);
    this.len += len;
    return this;
};

function writeByte(val, buf, pos) {
    buf[pos] = val & 255;
}

function writeVarint32(val, buf, pos) {
    while (val > 127) {
        buf[pos++] = val & 127 | 128;
        val >>>= 7;
    }
    buf[pos] = val;
}

/**
 * Constructs a new varint writer operation instance.
 * @classdesc Scheduled varint writer operation.
 * @extends Op
 * @constructor
 * @param {number} len Value byte length
 * @param {number} val Value to write
 * @ignore
 */
function VarintOp(len, val) {
    this.len = len;
    this.next = undefined;
    this.val = val;
}

VarintOp.prototype = Object.create(Op.prototype);
VarintOp.prototype.fn = writeVarint32;

/**
 * Writes an unsigned 32 bit value as a varint.
 * @param {number} value Value to write
 * @returns {Writer} `this`
 */
Writer.prototype.uint32 = function write_uint32(value) {
    // here, the call to this.push has been inlined and a varint specific Op subclass is used.
    // uint32 is by far the most frequently used operation and benefits significantly from this.
    this.len += (this.tail = this.tail.next = new VarintOp(
        (value = value >>> 0)
                < 128       ? 1
        : value < 16384     ? 2
        : value < 2097152   ? 3
        : value < 268435456 ? 4
        :                     5,
    value)).len;
    return this;
};

/**
 * Writes a signed 32 bit value as a varint.
 * @function
 * @param {number} value Value to write
 * @returns {Writer} `this`
 */
Writer.prototype.int32 = function write_int32(value) {
    return value < 0
        ? this._push(writeVarint64, 10, LongBits.fromNumber(value)) // 10 bytes per spec
        : this.uint32(value);
};

/**
 * Writes a 32 bit value as a varint, zig-zag encoded.
 * @param {number} value Value to write
 * @returns {Writer} `this`
 */
Writer.prototype.sint32 = function write_sint32(value) {
    return this.uint32((value << 1 ^ value >> 31) >>> 0);
};

function writeVarint64(val, buf, pos) {
    while (val.hi) {
        buf[pos++] = val.lo & 127 | 128;
        val.lo = (val.lo >>> 7 | val.hi << 25) >>> 0;
        val.hi >>>= 7;
    }
    while (val.lo > 127) {
        buf[pos++] = val.lo & 127 | 128;
        val.lo = val.lo >>> 7;
    }
    buf[pos++] = val.lo;
}

/**
 * Writes an unsigned 64 bit value as a varint.
 * @param {Long|number|string} value Value to write
 * @returns {Writer} `this`
 * @throws {TypeError} If `value` is a string and no long library is present.
 */
Writer.prototype.uint64 = function write_uint64(value) {
    var bits = LongBits.from(value);
    return this._push(writeVarint64, bits.length(), bits);
};

/**
 * Writes a signed 64 bit value as a varint.
 * @function
 * @param {Long|number|string} value Value to write
 * @returns {Writer} `this`
 * @throws {TypeError} If `value` is a string and no long library is present.
 */
Writer.prototype.int64 = Writer.prototype.uint64;

/**
 * Writes a signed 64 bit value as a varint, zig-zag encoded.
 * @param {Long|number|string} value Value to write
 * @returns {Writer} `this`
 * @throws {TypeError} If `value` is a string and no long library is present.
 */
Writer.prototype.sint64 = function write_sint64(value) {
    var bits = LongBits.from(value).zzEncode();
    return this._push(writeVarint64, bits.length(), bits);
};

/**
 * Writes a boolish value as a varint.
 * @param {boolean} value Value to write
 * @returns {Writer} `this`
 */
Writer.prototype.bool = function write_bool(value) {
    return this._push(writeByte, 1, value ? 1 : 0);
};

function writeFixed32(val, buf, pos) {
    buf[pos    ] =  val         & 255;
    buf[pos + 1] =  val >>> 8   & 255;
    buf[pos + 2] =  val >>> 16  & 255;
    buf[pos + 3] =  val >>> 24;
}

/**
 * Writes an unsigned 32 bit value as fixed 32 bits.
 * @param {number} value Value to write
 * @returns {Writer} `this`
 */
Writer.prototype.fixed32 = function write_fixed32(value) {
    return this._push(writeFixed32, 4, value >>> 0);
};

/**
 * Writes a signed 32 bit value as fixed 32 bits.
 * @function
 * @param {number} value Value to write
 * @returns {Writer} `this`
 */
Writer.prototype.sfixed32 = Writer.prototype.fixed32;

/**
 * Writes an unsigned 64 bit value as fixed 64 bits.
 * @param {Long|number|string} value Value to write
 * @returns {Writer} `this`
 * @throws {TypeError} If `value` is a string and no long library is present.
 */
Writer.prototype.fixed64 = function write_fixed64(value) {
    var bits = LongBits.from(value);
    return this._push(writeFixed32, 4, bits.lo)._push(writeFixed32, 4, bits.hi);
};

/**
 * Writes a signed 64 bit value as fixed 64 bits.
 * @function
 * @param {Long|number|string} value Value to write
 * @returns {Writer} `this`
 * @throws {TypeError} If `value` is a string and no long library is present.
 */
Writer.prototype.sfixed64 = Writer.prototype.fixed64;

/**
 * Writes a float (32 bit).
 * @function
 * @param {number} value Value to write
 * @returns {Writer} `this`
 */
Writer.prototype.float = function write_float(value) {
    return this._push(util.float.writeFloatLE, 4, value);
};

/**
 * Writes a double (64 bit float).
 * @function
 * @param {number} value Value to write
 * @returns {Writer} `this`
 */
Writer.prototype.double = function write_double(value) {
    return this._push(util.float.writeDoubleLE, 8, value);
};

var writeBytes = util.Array.prototype.set
    ? function writeBytes_set(val, buf, pos) {
        buf.set(val, pos); // also works for plain array values
    }
    /* istanbul ignore next */
    : function writeBytes_for(val, buf, pos) {
        for (var i = 0; i < val.length; ++i)
            buf[pos + i] = val[i];
    };

/**
 * Writes a sequence of bytes.
 * @param {Uint8Array|string} value Buffer or base64 encoded string to write
 * @returns {Writer} `this`
 */
Writer.prototype.bytes = function write_bytes(value) {
    var len = value.length >>> 0;
    if (!len)
        return this._push(writeByte, 1, 0);
    if (util.isString(value)) {
        var buf = Writer.alloc(len = base64.length(value));
        base64.decode(value, buf, 0);
        value = buf;
    }
    return this.uint32(len)._push(writeBytes, len, value);
};

/**
 * Writes a string.
 * @param {string} value Value to write
 * @returns {Writer} `this`
 */
Writer.prototype.string = function write_string(value) {
    var len = utf8.length(value);
    return len
        ? this.uint32(len)._push(utf8.write, len, value)
        : this._push(writeByte, 1, 0);
};

/**
 * Forks this writer's state by pushing it to a stack.
 * Calling {@link Writer#reset|reset} or {@link Writer#ldelim|ldelim} resets the writer to the previous state.
 * @returns {Writer} `this`
 */
Writer.prototype.fork = function fork() {
    this.states = new State(this);
    this.head = this.tail = new Op(noop, 0, 0);
    this.len = 0;
    return this;
};

/**
 * Resets this instance to the last state.
 * @returns {Writer} `this`
 */
Writer.prototype.reset = function reset() {
    if (this.states) {
        this.head   = this.states.head;
        this.tail   = this.states.tail;
        this.len    = this.states.len;
        this.states = this.states.next;
    } else {
        this.head = this.tail = new Op(noop, 0, 0);
        this.len  = 0;
    }
    return this;
};

/**
 * Resets to the last state and appends the fork state's current write length as a varint followed by its operations.
 * @returns {Writer} `this`
 */
Writer.prototype.ldelim = function ldelim() {
    var head = this.head,
        tail = this.tail,
        len  = this.len;
    this.reset().uint32(len);
    if (len) {
        this.tail.next = head.next; // skip noop
        this.tail = tail;
        this.len += len;
    }
    return this;
};

/**
 * Finishes the write operation.
 * @returns {Uint8Array} Finished buffer
 */
Writer.prototype.finish = function finish() {
    var head = this.head.next, // skip noop
        buf  = this.constructor.alloc(this.len),
        pos  = 0;
    while (head) {
        head.fn(head.val, buf, pos);
        pos += head.len;
        head = head.next;
    }
    // this.head = this.tail = null;
    return buf;
};

Writer._configure = function(BufferWriter_) {
    BufferWriter = BufferWriter_;
    Writer.create = create();
    BufferWriter._configure();
};

},{"./util/minimal":16}],18:[function(require,module,exports){
"use strict";
module.exports = BufferWriter;

// extends Writer
var Writer = require("./writer");
(BufferWriter.prototype = Object.create(Writer.prototype)).constructor = BufferWriter;

var util = require("./util/minimal");

/**
 * Constructs a new buffer writer instance.
 * @classdesc Wire format writer using node buffers.
 * @extends Writer
 * @constructor
 */
function BufferWriter() {
    Writer.call(this);
}

BufferWriter._configure = function () {
    /**
     * Allocates a buffer of the specified size.
     * @function
     * @param {number} size Buffer size
     * @returns {Buffer} Buffer
     */
    BufferWriter.alloc = util._Buffer_allocUnsafe;

    BufferWriter.writeBytesBuffer = util.Buffer && util.Buffer.prototype instanceof Uint8Array && util.Buffer.prototype.set.name === "set"
        ? function writeBytesBuffer_set(val, buf, pos) {
          buf.set(val, pos); // faster than copy (requires node >= 4 where Buffers extend Uint8Array and set is properly inherited)
          // also works for plain array values
        }
        /* istanbul ignore next */
        : function writeBytesBuffer_copy(val, buf, pos) {
          if (val.copy) // Buffer values
            val.copy(buf, pos, 0, val.length);
          else for (var i = 0; i < val.length;) // plain array values
            buf[pos++] = val[i++];
        };
};


/**
 * @override
 */
BufferWriter.prototype.bytes = function write_bytes_buffer(value) {
    if (util.isString(value))
        value = util._Buffer_from(value, "base64");
    var len = value.length >>> 0;
    this.uint32(len);
    if (len)
        this._push(BufferWriter.writeBytesBuffer, len, value);
    return this;
};

function writeStringBuffer(val, buf, pos) {
    if (val.length < 40) // plain js is faster for short strings (probably due to redundant assertions)
        util.utf8.write(val, buf, pos);
    else if (buf.utf8Write)
        buf.utf8Write(val, pos);
    else
        buf.write(val, pos);
}

/**
 * @override
 */
BufferWriter.prototype.string = function write_string_buffer(value) {
    var len = util.Buffer.byteLength(value);
    this.uint32(len);
    if (len)
        this._push(writeStringBuffer, len, value);
    return this;
};


/**
 * Finishes the write operation.
 * @name BufferWriter#finish
 * @function
 * @returns {Buffer} Finished buffer
 */

BufferWriter._configure();

},{"./util/minimal":16,"./writer":17}],19:[function(require,module,exports){
/*eslint-disable block-scoped-var, id-length, no-control-regex, no-magic-numbers, no-prototype-builtins, no-redeclare, no-shadow, no-var, sort-vars*/
"use strict";

var $protobuf = require("protobufjs/minimal");

// Common aliases
var $Reader = $protobuf.Reader, $Writer = $protobuf.Writer, $util = $protobuf.util;

// Exported root namespace
var $root = $protobuf.roots["default"] || ($protobuf.roots["default"] = {});

$root.wslobby = (function() {

    /**
     * Namespace wslobby.
     * @exports wslobby
     * @namespace
     */
    var wslobby = {};

    /**
     * Role enum.
     * @name wslobby.Role
     * @enum {number}
     * @property {number} HOST=0 HOST value
     * @property {number} GUEST=1 GUEST value
     */
    wslobby.Role = (function() {
        var valuesById = {}, values = Object.create(valuesById);
        values[valuesById[0] = "HOST"] = 0;
        values[valuesById[1] = "GUEST"] = 1;
        return values;
    })();

    wslobby.Command = (function() {

        /**
         * Properties of a Command.
         * @memberof wslobby
         * @interface ICommand
         * @property {number|null} [id] Command id
         * @property {wslobby.Command.IPing|null} [ping] Command ping
         * @property {wslobby.Command.IIdentify|null} [identify] Command identify
         * @property {wslobby.Command.IClaimRoom|null} [claimRoom] Command claimRoom
         * @property {wslobby.Command.IJoinRoom|null} [joinRoom] Command joinRoom
         * @property {wslobby.Command.ILeave|null} [leave] Command leave
         * @property {wslobby.Command.IKickGuest|null} [kickGuest] Command kickGuest
         * @property {wslobby.Command.IAllowGuest|null} [allowGuest] Command allowGuest
         * @property {wslobby.Command.IBroadcast|null} [broadcast] Command broadcast
         * @property {wslobby.Command.IUnicast|null} [unicast] Command unicast
         */

        /**
         * Constructs a new Command.
         * @memberof wslobby
         * @classdesc Represents a Command.
         * @implements ICommand
         * @constructor
         * @param {wslobby.ICommand=} [properties] Properties to set
         */
        function Command(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }

        /**
         * Command id.
         * @member {number} id
         * @memberof wslobby.Command
         * @instance
         */
        Command.prototype.id = 0;

        /**
         * Command ping.
         * @member {wslobby.Command.IPing|null|undefined} ping
         * @memberof wslobby.Command
         * @instance
         */
        Command.prototype.ping = null;

        /**
         * Command identify.
         * @member {wslobby.Command.IIdentify|null|undefined} identify
         * @memberof wslobby.Command
         * @instance
         */
        Command.prototype.identify = null;

        /**
         * Command claimRoom.
         * @member {wslobby.Command.IClaimRoom|null|undefined} claimRoom
         * @memberof wslobby.Command
         * @instance
         */
        Command.prototype.claimRoom = null;

        /**
         * Command joinRoom.
         * @member {wslobby.Command.IJoinRoom|null|undefined} joinRoom
         * @memberof wslobby.Command
         * @instance
         */
        Command.prototype.joinRoom = null;

        /**
         * Command leave.
         * @member {wslobby.Command.ILeave|null|undefined} leave
         * @memberof wslobby.Command
         * @instance
         */
        Command.prototype.leave = null;

        /**
         * Command kickGuest.
         * @member {wslobby.Command.IKickGuest|null|undefined} kickGuest
         * @memberof wslobby.Command
         * @instance
         */
        Command.prototype.kickGuest = null;

        /**
         * Command allowGuest.
         * @member {wslobby.Command.IAllowGuest|null|undefined} allowGuest
         * @memberof wslobby.Command
         * @instance
         */
        Command.prototype.allowGuest = null;

        /**
         * Command broadcast.
         * @member {wslobby.Command.IBroadcast|null|undefined} broadcast
         * @memberof wslobby.Command
         * @instance
         */
        Command.prototype.broadcast = null;

        /**
         * Command unicast.
         * @member {wslobby.Command.IUnicast|null|undefined} unicast
         * @memberof wslobby.Command
         * @instance
         */
        Command.prototype.unicast = null;

        // OneOf field names bound to virtual getters and setters
        var $oneOfFields;

        /**
         * Command payload.
         * @member {"ping"|"identify"|"claimRoom"|"joinRoom"|"leave"|"kickGuest"|"allowGuest"|"broadcast"|"unicast"|undefined} payload
         * @memberof wslobby.Command
         * @instance
         */
        Object.defineProperty(Command.prototype, "payload", {
            get: $util.oneOfGetter($oneOfFields = ["ping", "identify", "claimRoom", "joinRoom", "leave", "kickGuest", "allowGuest", "broadcast", "unicast"]),
            set: $util.oneOfSetter($oneOfFields)
        });

        /**
         * Creates a new Command instance using the specified properties.
         * @function create
         * @memberof wslobby.Command
         * @static
         * @param {wslobby.ICommand=} [properties] Properties to set
         * @returns {wslobby.Command} Command instance
         */
        Command.create = function create(properties) {
            return new Command(properties);
        };

        /**
         * Encodes the specified Command message. Does not implicitly {@link wslobby.Command.verify|verify} messages.
         * @function encode
         * @memberof wslobby.Command
         * @static
         * @param {wslobby.ICommand} message Command message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        Command.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            if (message.id != null && Object.hasOwnProperty.call(message, "id"))
                writer.uint32(/* id 1, wireType 0 =*/8).uint32(message.id);
            if (message.ping != null && Object.hasOwnProperty.call(message, "ping"))
                $root.wslobby.Command.Ping.encode(message.ping, writer.uint32(/* id 2, wireType 2 =*/18).fork()).ldelim();
            if (message.identify != null && Object.hasOwnProperty.call(message, "identify"))
                $root.wslobby.Command.Identify.encode(message.identify, writer.uint32(/* id 3, wireType 2 =*/26).fork()).ldelim();
            if (message.claimRoom != null && Object.hasOwnProperty.call(message, "claimRoom"))
                $root.wslobby.Command.ClaimRoom.encode(message.claimRoom, writer.uint32(/* id 4, wireType 2 =*/34).fork()).ldelim();
            if (message.joinRoom != null && Object.hasOwnProperty.call(message, "joinRoom"))
                $root.wslobby.Command.JoinRoom.encode(message.joinRoom, writer.uint32(/* id 5, wireType 2 =*/42).fork()).ldelim();
            if (message.leave != null && Object.hasOwnProperty.call(message, "leave"))
                $root.wslobby.Command.Leave.encode(message.leave, writer.uint32(/* id 6, wireType 2 =*/50).fork()).ldelim();
            if (message.kickGuest != null && Object.hasOwnProperty.call(message, "kickGuest"))
                $root.wslobby.Command.KickGuest.encode(message.kickGuest, writer.uint32(/* id 7, wireType 2 =*/58).fork()).ldelim();
            if (message.allowGuest != null && Object.hasOwnProperty.call(message, "allowGuest"))
                $root.wslobby.Command.AllowGuest.encode(message.allowGuest, writer.uint32(/* id 8, wireType 2 =*/66).fork()).ldelim();
            if (message.broadcast != null && Object.hasOwnProperty.call(message, "broadcast"))
                $root.wslobby.Command.Broadcast.encode(message.broadcast, writer.uint32(/* id 9, wireType 2 =*/74).fork()).ldelim();
            if (message.unicast != null && Object.hasOwnProperty.call(message, "unicast"))
                $root.wslobby.Command.Unicast.encode(message.unicast, writer.uint32(/* id 10, wireType 2 =*/82).fork()).ldelim();
            return writer;
        };

        /**
         * Encodes the specified Command message, length delimited. Does not implicitly {@link wslobby.Command.verify|verify} messages.
         * @function encodeDelimited
         * @memberof wslobby.Command
         * @static
         * @param {wslobby.ICommand} message Command message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        Command.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };

        /**
         * Decodes a Command message from the specified reader or buffer.
         * @function decode
         * @memberof wslobby.Command
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @param {number} [length] Message length if known beforehand
         * @returns {wslobby.Command} Command
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        Command.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.wslobby.Command();
            while (reader.pos < end) {
                var tag = reader.uint32();
                switch (tag >>> 3) {
                case 1:
                    message.id = reader.uint32();
                    break;
                case 2:
                    message.ping = $root.wslobby.Command.Ping.decode(reader, reader.uint32());
                    break;
                case 3:
                    message.identify = $root.wslobby.Command.Identify.decode(reader, reader.uint32());
                    break;
                case 4:
                    message.claimRoom = $root.wslobby.Command.ClaimRoom.decode(reader, reader.uint32());
                    break;
                case 5:
                    message.joinRoom = $root.wslobby.Command.JoinRoom.decode(reader, reader.uint32());
                    break;
                case 6:
                    message.leave = $root.wslobby.Command.Leave.decode(reader, reader.uint32());
                    break;
                case 7:
                    message.kickGuest = $root.wslobby.Command.KickGuest.decode(reader, reader.uint32());
                    break;
                case 8:
                    message.allowGuest = $root.wslobby.Command.AllowGuest.decode(reader, reader.uint32());
                    break;
                case 9:
                    message.broadcast = $root.wslobby.Command.Broadcast.decode(reader, reader.uint32());
                    break;
                case 10:
                    message.unicast = $root.wslobby.Command.Unicast.decode(reader, reader.uint32());
                    break;
                default:
                    reader.skipType(tag & 7);
                    break;
                }
            }
            return message;
        };

        /**
         * Decodes a Command message from the specified reader or buffer, length delimited.
         * @function decodeDelimited
         * @memberof wslobby.Command
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @returns {wslobby.Command} Command
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        Command.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };

        /**
         * Verifies a Command message.
         * @function verify
         * @memberof wslobby.Command
         * @static
         * @param {Object.<string,*>} message Plain object to verify
         * @returns {string|null} `null` if valid, otherwise the reason why it is not
         */
        Command.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            var properties = {};
            if (message.id != null && message.hasOwnProperty("id"))
                if (!$util.isInteger(message.id))
                    return "id: integer expected";
            if (message.ping != null && message.hasOwnProperty("ping")) {
                properties.payload = 1;
                {
                    var error = $root.wslobby.Command.Ping.verify(message.ping);
                    if (error)
                        return "ping." + error;
                }
            }
            if (message.identify != null && message.hasOwnProperty("identify")) {
                if (properties.payload === 1)
                    return "payload: multiple values";
                properties.payload = 1;
                {
                    var error = $root.wslobby.Command.Identify.verify(message.identify);
                    if (error)
                        return "identify." + error;
                }
            }
            if (message.claimRoom != null && message.hasOwnProperty("claimRoom")) {
                if (properties.payload === 1)
                    return "payload: multiple values";
                properties.payload = 1;
                {
                    var error = $root.wslobby.Command.ClaimRoom.verify(message.claimRoom);
                    if (error)
                        return "claimRoom." + error;
                }
            }
            if (message.joinRoom != null && message.hasOwnProperty("joinRoom")) {
                if (properties.payload === 1)
                    return "payload: multiple values";
                properties.payload = 1;
                {
                    var error = $root.wslobby.Command.JoinRoom.verify(message.joinRoom);
                    if (error)
                        return "joinRoom." + error;
                }
            }
            if (message.leave != null && message.hasOwnProperty("leave")) {
                if (properties.payload === 1)
                    return "payload: multiple values";
                properties.payload = 1;
                {
                    var error = $root.wslobby.Command.Leave.verify(message.leave);
                    if (error)
                        return "leave." + error;
                }
            }
            if (message.kickGuest != null && message.hasOwnProperty("kickGuest")) {
                if (properties.payload === 1)
                    return "payload: multiple values";
                properties.payload = 1;
                {
                    var error = $root.wslobby.Command.KickGuest.verify(message.kickGuest);
                    if (error)
                        return "kickGuest." + error;
                }
            }
            if (message.allowGuest != null && message.hasOwnProperty("allowGuest")) {
                if (properties.payload === 1)
                    return "payload: multiple values";
                properties.payload = 1;
                {
                    var error = $root.wslobby.Command.AllowGuest.verify(message.allowGuest);
                    if (error)
                        return "allowGuest." + error;
                }
            }
            if (message.broadcast != null && message.hasOwnProperty("broadcast")) {
                if (properties.payload === 1)
                    return "payload: multiple values";
                properties.payload = 1;
                {
                    var error = $root.wslobby.Command.Broadcast.verify(message.broadcast);
                    if (error)
                        return "broadcast." + error;
                }
            }
            if (message.unicast != null && message.hasOwnProperty("unicast")) {
                if (properties.payload === 1)
                    return "payload: multiple values";
                properties.payload = 1;
                {
                    var error = $root.wslobby.Command.Unicast.verify(message.unicast);
                    if (error)
                        return "unicast." + error;
                }
            }
            return null;
        };

        /**
         * Creates a Command message from a plain object. Also converts values to their respective internal types.
         * @function fromObject
         * @memberof wslobby.Command
         * @static
         * @param {Object.<string,*>} object Plain object
         * @returns {wslobby.Command} Command
         */
        Command.fromObject = function fromObject(object) {
            if (object instanceof $root.wslobby.Command)
                return object;
            var message = new $root.wslobby.Command();
            if (object.id != null)
                message.id = object.id >>> 0;
            if (object.ping != null) {
                if (typeof object.ping !== "object")
                    throw TypeError(".wslobby.Command.ping: object expected");
                message.ping = $root.wslobby.Command.Ping.fromObject(object.ping);
            }
            if (object.identify != null) {
                if (typeof object.identify !== "object")
                    throw TypeError(".wslobby.Command.identify: object expected");
                message.identify = $root.wslobby.Command.Identify.fromObject(object.identify);
            }
            if (object.claimRoom != null) {
                if (typeof object.claimRoom !== "object")
                    throw TypeError(".wslobby.Command.claimRoom: object expected");
                message.claimRoom = $root.wslobby.Command.ClaimRoom.fromObject(object.claimRoom);
            }
            if (object.joinRoom != null) {
                if (typeof object.joinRoom !== "object")
                    throw TypeError(".wslobby.Command.joinRoom: object expected");
                message.joinRoom = $root.wslobby.Command.JoinRoom.fromObject(object.joinRoom);
            }
            if (object.leave != null) {
                if (typeof object.leave !== "object")
                    throw TypeError(".wslobby.Command.leave: object expected");
                message.leave = $root.wslobby.Command.Leave.fromObject(object.leave);
            }
            if (object.kickGuest != null) {
                if (typeof object.kickGuest !== "object")
                    throw TypeError(".wslobby.Command.kickGuest: object expected");
                message.kickGuest = $root.wslobby.Command.KickGuest.fromObject(object.kickGuest);
            }
            if (object.allowGuest != null) {
                if (typeof object.allowGuest !== "object")
                    throw TypeError(".wslobby.Command.allowGuest: object expected");
                message.allowGuest = $root.wslobby.Command.AllowGuest.fromObject(object.allowGuest);
            }
            if (object.broadcast != null) {
                if (typeof object.broadcast !== "object")
                    throw TypeError(".wslobby.Command.broadcast: object expected");
                message.broadcast = $root.wslobby.Command.Broadcast.fromObject(object.broadcast);
            }
            if (object.unicast != null) {
                if (typeof object.unicast !== "object")
                    throw TypeError(".wslobby.Command.unicast: object expected");
                message.unicast = $root.wslobby.Command.Unicast.fromObject(object.unicast);
            }
            return message;
        };

        /**
         * Creates a plain object from a Command message. Also converts values to other types if specified.
         * @function toObject
         * @memberof wslobby.Command
         * @static
         * @param {wslobby.Command} message Command
         * @param {$protobuf.IConversionOptions} [options] Conversion options
         * @returns {Object.<string,*>} Plain object
         */
        Command.toObject = function toObject(message, options) {
            if (!options)
                options = {};
            var object = {};
            if (options.defaults)
                object.id = 0;
            if (message.id != null && message.hasOwnProperty("id"))
                object.id = message.id;
            if (message.ping != null && message.hasOwnProperty("ping")) {
                object.ping = $root.wslobby.Command.Ping.toObject(message.ping, options);
                if (options.oneofs)
                    object.payload = "ping";
            }
            if (message.identify != null && message.hasOwnProperty("identify")) {
                object.identify = $root.wslobby.Command.Identify.toObject(message.identify, options);
                if (options.oneofs)
                    object.payload = "identify";
            }
            if (message.claimRoom != null && message.hasOwnProperty("claimRoom")) {
                object.claimRoom = $root.wslobby.Command.ClaimRoom.toObject(message.claimRoom, options);
                if (options.oneofs)
                    object.payload = "claimRoom";
            }
            if (message.joinRoom != null && message.hasOwnProperty("joinRoom")) {
                object.joinRoom = $root.wslobby.Command.JoinRoom.toObject(message.joinRoom, options);
                if (options.oneofs)
                    object.payload = "joinRoom";
            }
            if (message.leave != null && message.hasOwnProperty("leave")) {
                object.leave = $root.wslobby.Command.Leave.toObject(message.leave, options);
                if (options.oneofs)
                    object.payload = "leave";
            }
            if (message.kickGuest != null && message.hasOwnProperty("kickGuest")) {
                object.kickGuest = $root.wslobby.Command.KickGuest.toObject(message.kickGuest, options);
                if (options.oneofs)
                    object.payload = "kickGuest";
            }
            if (message.allowGuest != null && message.hasOwnProperty("allowGuest")) {
                object.allowGuest = $root.wslobby.Command.AllowGuest.toObject(message.allowGuest, options);
                if (options.oneofs)
                    object.payload = "allowGuest";
            }
            if (message.broadcast != null && message.hasOwnProperty("broadcast")) {
                object.broadcast = $root.wslobby.Command.Broadcast.toObject(message.broadcast, options);
                if (options.oneofs)
                    object.payload = "broadcast";
            }
            if (message.unicast != null && message.hasOwnProperty("unicast")) {
                object.unicast = $root.wslobby.Command.Unicast.toObject(message.unicast, options);
                if (options.oneofs)
                    object.payload = "unicast";
            }
            return object;
        };

        /**
         * Converts this Command to JSON.
         * @function toJSON
         * @memberof wslobby.Command
         * @instance
         * @returns {Object.<string,*>} JSON object
         */
        Command.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };

        Command.Ping = (function() {

            /**
             * Properties of a Ping.
             * @memberof wslobby.Command
             * @interface IPing
             */

            /**
             * Constructs a new Ping.
             * @memberof wslobby.Command
             * @classdesc Represents a Ping.
             * @implements IPing
             * @constructor
             * @param {wslobby.Command.IPing=} [properties] Properties to set
             */
            function Ping(properties) {
                if (properties)
                    for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                        if (properties[keys[i]] != null)
                            this[keys[i]] = properties[keys[i]];
            }

            /**
             * Creates a new Ping instance using the specified properties.
             * @function create
             * @memberof wslobby.Command.Ping
             * @static
             * @param {wslobby.Command.IPing=} [properties] Properties to set
             * @returns {wslobby.Command.Ping} Ping instance
             */
            Ping.create = function create(properties) {
                return new Ping(properties);
            };

            /**
             * Encodes the specified Ping message. Does not implicitly {@link wslobby.Command.Ping.verify|verify} messages.
             * @function encode
             * @memberof wslobby.Command.Ping
             * @static
             * @param {wslobby.Command.IPing} message Ping message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            Ping.encode = function encode(message, writer) {
                if (!writer)
                    writer = $Writer.create();
                return writer;
            };

            /**
             * Encodes the specified Ping message, length delimited. Does not implicitly {@link wslobby.Command.Ping.verify|verify} messages.
             * @function encodeDelimited
             * @memberof wslobby.Command.Ping
             * @static
             * @param {wslobby.Command.IPing} message Ping message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            Ping.encodeDelimited = function encodeDelimited(message, writer) {
                return this.encode(message, writer).ldelim();
            };

            /**
             * Decodes a Ping message from the specified reader or buffer.
             * @function decode
             * @memberof wslobby.Command.Ping
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @param {number} [length] Message length if known beforehand
             * @returns {wslobby.Command.Ping} Ping
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            Ping.decode = function decode(reader, length) {
                if (!(reader instanceof $Reader))
                    reader = $Reader.create(reader);
                var end = length === undefined ? reader.len : reader.pos + length, message = new $root.wslobby.Command.Ping();
                while (reader.pos < end) {
                    var tag = reader.uint32();
                    switch (tag >>> 3) {
                    default:
                        reader.skipType(tag & 7);
                        break;
                    }
                }
                return message;
            };

            /**
             * Decodes a Ping message from the specified reader or buffer, length delimited.
             * @function decodeDelimited
             * @memberof wslobby.Command.Ping
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @returns {wslobby.Command.Ping} Ping
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            Ping.decodeDelimited = function decodeDelimited(reader) {
                if (!(reader instanceof $Reader))
                    reader = new $Reader(reader);
                return this.decode(reader, reader.uint32());
            };

            /**
             * Verifies a Ping message.
             * @function verify
             * @memberof wslobby.Command.Ping
             * @static
             * @param {Object.<string,*>} message Plain object to verify
             * @returns {string|null} `null` if valid, otherwise the reason why it is not
             */
            Ping.verify = function verify(message) {
                if (typeof message !== "object" || message === null)
                    return "object expected";
                return null;
            };

            /**
             * Creates a Ping message from a plain object. Also converts values to their respective internal types.
             * @function fromObject
             * @memberof wslobby.Command.Ping
             * @static
             * @param {Object.<string,*>} object Plain object
             * @returns {wslobby.Command.Ping} Ping
             */
            Ping.fromObject = function fromObject(object) {
                if (object instanceof $root.wslobby.Command.Ping)
                    return object;
                return new $root.wslobby.Command.Ping();
            };

            /**
             * Creates a plain object from a Ping message. Also converts values to other types if specified.
             * @function toObject
             * @memberof wslobby.Command.Ping
             * @static
             * @param {wslobby.Command.Ping} message Ping
             * @param {$protobuf.IConversionOptions} [options] Conversion options
             * @returns {Object.<string,*>} Plain object
             */
            Ping.toObject = function toObject() {
                return {};
            };

            /**
             * Converts this Ping to JSON.
             * @function toJSON
             * @memberof wslobby.Command.Ping
             * @instance
             * @returns {Object.<string,*>} JSON object
             */
            Ping.prototype.toJSON = function toJSON() {
                return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
            };

            return Ping;
        })();

        Command.Identify = (function() {

            /**
             * Properties of an Identify.
             * @memberof wslobby.Command
             * @interface IIdentify
             * @property {string|null} [identity] Identify identity
             * @property {string|null} [token] Identify token
             * @property {wslobby.Role|null} [role] Identify role
             */

            /**
             * Constructs a new Identify.
             * @memberof wslobby.Command
             * @classdesc Represents an Identify.
             * @implements IIdentify
             * @constructor
             * @param {wslobby.Command.IIdentify=} [properties] Properties to set
             */
            function Identify(properties) {
                if (properties)
                    for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                        if (properties[keys[i]] != null)
                            this[keys[i]] = properties[keys[i]];
            }

            /**
             * Identify identity.
             * @member {string} identity
             * @memberof wslobby.Command.Identify
             * @instance
             */
            Identify.prototype.identity = "";

            /**
             * Identify token.
             * @member {string} token
             * @memberof wslobby.Command.Identify
             * @instance
             */
            Identify.prototype.token = "";

            /**
             * Identify role.
             * @member {wslobby.Role} role
             * @memberof wslobby.Command.Identify
             * @instance
             */
            Identify.prototype.role = 0;

            /**
             * Creates a new Identify instance using the specified properties.
             * @function create
             * @memberof wslobby.Command.Identify
             * @static
             * @param {wslobby.Command.IIdentify=} [properties] Properties to set
             * @returns {wslobby.Command.Identify} Identify instance
             */
            Identify.create = function create(properties) {
                return new Identify(properties);
            };

            /**
             * Encodes the specified Identify message. Does not implicitly {@link wslobby.Command.Identify.verify|verify} messages.
             * @function encode
             * @memberof wslobby.Command.Identify
             * @static
             * @param {wslobby.Command.IIdentify} message Identify message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            Identify.encode = function encode(message, writer) {
                if (!writer)
                    writer = $Writer.create();
                if (message.identity != null && Object.hasOwnProperty.call(message, "identity"))
                    writer.uint32(/* id 1, wireType 2 =*/10).string(message.identity);
                if (message.token != null && Object.hasOwnProperty.call(message, "token"))
                    writer.uint32(/* id 2, wireType 2 =*/18).string(message.token);
                if (message.role != null && Object.hasOwnProperty.call(message, "role"))
                    writer.uint32(/* id 3, wireType 0 =*/24).int32(message.role);
                return writer;
            };

            /**
             * Encodes the specified Identify message, length delimited. Does not implicitly {@link wslobby.Command.Identify.verify|verify} messages.
             * @function encodeDelimited
             * @memberof wslobby.Command.Identify
             * @static
             * @param {wslobby.Command.IIdentify} message Identify message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            Identify.encodeDelimited = function encodeDelimited(message, writer) {
                return this.encode(message, writer).ldelim();
            };

            /**
             * Decodes an Identify message from the specified reader or buffer.
             * @function decode
             * @memberof wslobby.Command.Identify
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @param {number} [length] Message length if known beforehand
             * @returns {wslobby.Command.Identify} Identify
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            Identify.decode = function decode(reader, length) {
                if (!(reader instanceof $Reader))
                    reader = $Reader.create(reader);
                var end = length === undefined ? reader.len : reader.pos + length, message = new $root.wslobby.Command.Identify();
                while (reader.pos < end) {
                    var tag = reader.uint32();
                    switch (tag >>> 3) {
                    case 1:
                        message.identity = reader.string();
                        break;
                    case 2:
                        message.token = reader.string();
                        break;
                    case 3:
                        message.role = reader.int32();
                        break;
                    default:
                        reader.skipType(tag & 7);
                        break;
                    }
                }
                return message;
            };

            /**
             * Decodes an Identify message from the specified reader or buffer, length delimited.
             * @function decodeDelimited
             * @memberof wslobby.Command.Identify
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @returns {wslobby.Command.Identify} Identify
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            Identify.decodeDelimited = function decodeDelimited(reader) {
                if (!(reader instanceof $Reader))
                    reader = new $Reader(reader);
                return this.decode(reader, reader.uint32());
            };

            /**
             * Verifies an Identify message.
             * @function verify
             * @memberof wslobby.Command.Identify
             * @static
             * @param {Object.<string,*>} message Plain object to verify
             * @returns {string|null} `null` if valid, otherwise the reason why it is not
             */
            Identify.verify = function verify(message) {
                if (typeof message !== "object" || message === null)
                    return "object expected";
                if (message.identity != null && message.hasOwnProperty("identity"))
                    if (!$util.isString(message.identity))
                        return "identity: string expected";
                if (message.token != null && message.hasOwnProperty("token"))
                    if (!$util.isString(message.token))
                        return "token: string expected";
                if (message.role != null && message.hasOwnProperty("role"))
                    switch (message.role) {
                    default:
                        return "role: enum value expected";
                    case 0:
                    case 1:
                        break;
                    }
                return null;
            };

            /**
             * Creates an Identify message from a plain object. Also converts values to their respective internal types.
             * @function fromObject
             * @memberof wslobby.Command.Identify
             * @static
             * @param {Object.<string,*>} object Plain object
             * @returns {wslobby.Command.Identify} Identify
             */
            Identify.fromObject = function fromObject(object) {
                if (object instanceof $root.wslobby.Command.Identify)
                    return object;
                var message = new $root.wslobby.Command.Identify();
                if (object.identity != null)
                    message.identity = String(object.identity);
                if (object.token != null)
                    message.token = String(object.token);
                switch (object.role) {
                case "HOST":
                case 0:
                    message.role = 0;
                    break;
                case "GUEST":
                case 1:
                    message.role = 1;
                    break;
                }
                return message;
            };

            /**
             * Creates a plain object from an Identify message. Also converts values to other types if specified.
             * @function toObject
             * @memberof wslobby.Command.Identify
             * @static
             * @param {wslobby.Command.Identify} message Identify
             * @param {$protobuf.IConversionOptions} [options] Conversion options
             * @returns {Object.<string,*>} Plain object
             */
            Identify.toObject = function toObject(message, options) {
                if (!options)
                    options = {};
                var object = {};
                if (options.defaults) {
                    object.identity = "";
                    object.token = "";
                    object.role = options.enums === String ? "HOST" : 0;
                }
                if (message.identity != null && message.hasOwnProperty("identity"))
                    object.identity = message.identity;
                if (message.token != null && message.hasOwnProperty("token"))
                    object.token = message.token;
                if (message.role != null && message.hasOwnProperty("role"))
                    object.role = options.enums === String ? $root.wslobby.Role[message.role] : message.role;
                return object;
            };

            /**
             * Converts this Identify to JSON.
             * @function toJSON
             * @memberof wslobby.Command.Identify
             * @instance
             * @returns {Object.<string,*>} JSON object
             */
            Identify.prototype.toJSON = function toJSON() {
                return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
            };

            return Identify;
        })();

        Command.ClaimRoom = (function() {

            /**
             * Properties of a ClaimRoom.
             * @memberof wslobby.Command
             * @interface IClaimRoom
             * @property {string|null} [roomId] ClaimRoom roomId
             */

            /**
             * Constructs a new ClaimRoom.
             * @memberof wslobby.Command
             * @classdesc Represents a ClaimRoom.
             * @implements IClaimRoom
             * @constructor
             * @param {wslobby.Command.IClaimRoom=} [properties] Properties to set
             */
            function ClaimRoom(properties) {
                if (properties)
                    for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                        if (properties[keys[i]] != null)
                            this[keys[i]] = properties[keys[i]];
            }

            /**
             * ClaimRoom roomId.
             * @member {string} roomId
             * @memberof wslobby.Command.ClaimRoom
             * @instance
             */
            ClaimRoom.prototype.roomId = "";

            /**
             * Creates a new ClaimRoom instance using the specified properties.
             * @function create
             * @memberof wslobby.Command.ClaimRoom
             * @static
             * @param {wslobby.Command.IClaimRoom=} [properties] Properties to set
             * @returns {wslobby.Command.ClaimRoom} ClaimRoom instance
             */
            ClaimRoom.create = function create(properties) {
                return new ClaimRoom(properties);
            };

            /**
             * Encodes the specified ClaimRoom message. Does not implicitly {@link wslobby.Command.ClaimRoom.verify|verify} messages.
             * @function encode
             * @memberof wslobby.Command.ClaimRoom
             * @static
             * @param {wslobby.Command.IClaimRoom} message ClaimRoom message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            ClaimRoom.encode = function encode(message, writer) {
                if (!writer)
                    writer = $Writer.create();
                if (message.roomId != null && Object.hasOwnProperty.call(message, "roomId"))
                    writer.uint32(/* id 1, wireType 2 =*/10).string(message.roomId);
                return writer;
            };

            /**
             * Encodes the specified ClaimRoom message, length delimited. Does not implicitly {@link wslobby.Command.ClaimRoom.verify|verify} messages.
             * @function encodeDelimited
             * @memberof wslobby.Command.ClaimRoom
             * @static
             * @param {wslobby.Command.IClaimRoom} message ClaimRoom message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            ClaimRoom.encodeDelimited = function encodeDelimited(message, writer) {
                return this.encode(message, writer).ldelim();
            };

            /**
             * Decodes a ClaimRoom message from the specified reader or buffer.
             * @function decode
             * @memberof wslobby.Command.ClaimRoom
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @param {number} [length] Message length if known beforehand
             * @returns {wslobby.Command.ClaimRoom} ClaimRoom
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            ClaimRoom.decode = function decode(reader, length) {
                if (!(reader instanceof $Reader))
                    reader = $Reader.create(reader);
                var end = length === undefined ? reader.len : reader.pos + length, message = new $root.wslobby.Command.ClaimRoom();
                while (reader.pos < end) {
                    var tag = reader.uint32();
                    switch (tag >>> 3) {
                    case 1:
                        message.roomId = reader.string();
                        break;
                    default:
                        reader.skipType(tag & 7);
                        break;
                    }
                }
                return message;
            };

            /**
             * Decodes a ClaimRoom message from the specified reader or buffer, length delimited.
             * @function decodeDelimited
             * @memberof wslobby.Command.ClaimRoom
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @returns {wslobby.Command.ClaimRoom} ClaimRoom
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            ClaimRoom.decodeDelimited = function decodeDelimited(reader) {
                if (!(reader instanceof $Reader))
                    reader = new $Reader(reader);
                return this.decode(reader, reader.uint32());
            };

            /**
             * Verifies a ClaimRoom message.
             * @function verify
             * @memberof wslobby.Command.ClaimRoom
             * @static
             * @param {Object.<string,*>} message Plain object to verify
             * @returns {string|null} `null` if valid, otherwise the reason why it is not
             */
            ClaimRoom.verify = function verify(message) {
                if (typeof message !== "object" || message === null)
                    return "object expected";
                if (message.roomId != null && message.hasOwnProperty("roomId"))
                    if (!$util.isString(message.roomId))
                        return "roomId: string expected";
                return null;
            };

            /**
             * Creates a ClaimRoom message from a plain object. Also converts values to their respective internal types.
             * @function fromObject
             * @memberof wslobby.Command.ClaimRoom
             * @static
             * @param {Object.<string,*>} object Plain object
             * @returns {wslobby.Command.ClaimRoom} ClaimRoom
             */
            ClaimRoom.fromObject = function fromObject(object) {
                if (object instanceof $root.wslobby.Command.ClaimRoom)
                    return object;
                var message = new $root.wslobby.Command.ClaimRoom();
                if (object.roomId != null)
                    message.roomId = String(object.roomId);
                return message;
            };

            /**
             * Creates a plain object from a ClaimRoom message. Also converts values to other types if specified.
             * @function toObject
             * @memberof wslobby.Command.ClaimRoom
             * @static
             * @param {wslobby.Command.ClaimRoom} message ClaimRoom
             * @param {$protobuf.IConversionOptions} [options] Conversion options
             * @returns {Object.<string,*>} Plain object
             */
            ClaimRoom.toObject = function toObject(message, options) {
                if (!options)
                    options = {};
                var object = {};
                if (options.defaults)
                    object.roomId = "";
                if (message.roomId != null && message.hasOwnProperty("roomId"))
                    object.roomId = message.roomId;
                return object;
            };

            /**
             * Converts this ClaimRoom to JSON.
             * @function toJSON
             * @memberof wslobby.Command.ClaimRoom
             * @instance
             * @returns {Object.<string,*>} JSON object
             */
            ClaimRoom.prototype.toJSON = function toJSON() {
                return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
            };

            return ClaimRoom;
        })();

        Command.JoinRoom = (function() {

            /**
             * Properties of a JoinRoom.
             * @memberof wslobby.Command
             * @interface IJoinRoom
             * @property {string|null} [roomId] JoinRoom roomId
             * @property {string|null} [greeting] JoinRoom greeting
             */

            /**
             * Constructs a new JoinRoom.
             * @memberof wslobby.Command
             * @classdesc Represents a JoinRoom.
             * @implements IJoinRoom
             * @constructor
             * @param {wslobby.Command.IJoinRoom=} [properties] Properties to set
             */
            function JoinRoom(properties) {
                if (properties)
                    for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                        if (properties[keys[i]] != null)
                            this[keys[i]] = properties[keys[i]];
            }

            /**
             * JoinRoom roomId.
             * @member {string} roomId
             * @memberof wslobby.Command.JoinRoom
             * @instance
             */
            JoinRoom.prototype.roomId = "";

            /**
             * JoinRoom greeting.
             * @member {string} greeting
             * @memberof wslobby.Command.JoinRoom
             * @instance
             */
            JoinRoom.prototype.greeting = "";

            /**
             * Creates a new JoinRoom instance using the specified properties.
             * @function create
             * @memberof wslobby.Command.JoinRoom
             * @static
             * @param {wslobby.Command.IJoinRoom=} [properties] Properties to set
             * @returns {wslobby.Command.JoinRoom} JoinRoom instance
             */
            JoinRoom.create = function create(properties) {
                return new JoinRoom(properties);
            };

            /**
             * Encodes the specified JoinRoom message. Does not implicitly {@link wslobby.Command.JoinRoom.verify|verify} messages.
             * @function encode
             * @memberof wslobby.Command.JoinRoom
             * @static
             * @param {wslobby.Command.IJoinRoom} message JoinRoom message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            JoinRoom.encode = function encode(message, writer) {
                if (!writer)
                    writer = $Writer.create();
                if (message.roomId != null && Object.hasOwnProperty.call(message, "roomId"))
                    writer.uint32(/* id 1, wireType 2 =*/10).string(message.roomId);
                if (message.greeting != null && Object.hasOwnProperty.call(message, "greeting"))
                    writer.uint32(/* id 2, wireType 2 =*/18).string(message.greeting);
                return writer;
            };

            /**
             * Encodes the specified JoinRoom message, length delimited. Does not implicitly {@link wslobby.Command.JoinRoom.verify|verify} messages.
             * @function encodeDelimited
             * @memberof wslobby.Command.JoinRoom
             * @static
             * @param {wslobby.Command.IJoinRoom} message JoinRoom message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            JoinRoom.encodeDelimited = function encodeDelimited(message, writer) {
                return this.encode(message, writer).ldelim();
            };

            /**
             * Decodes a JoinRoom message from the specified reader or buffer.
             * @function decode
             * @memberof wslobby.Command.JoinRoom
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @param {number} [length] Message length if known beforehand
             * @returns {wslobby.Command.JoinRoom} JoinRoom
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            JoinRoom.decode = function decode(reader, length) {
                if (!(reader instanceof $Reader))
                    reader = $Reader.create(reader);
                var end = length === undefined ? reader.len : reader.pos + length, message = new $root.wslobby.Command.JoinRoom();
                while (reader.pos < end) {
                    var tag = reader.uint32();
                    switch (tag >>> 3) {
                    case 1:
                        message.roomId = reader.string();
                        break;
                    case 2:
                        message.greeting = reader.string();
                        break;
                    default:
                        reader.skipType(tag & 7);
                        break;
                    }
                }
                return message;
            };

            /**
             * Decodes a JoinRoom message from the specified reader or buffer, length delimited.
             * @function decodeDelimited
             * @memberof wslobby.Command.JoinRoom
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @returns {wslobby.Command.JoinRoom} JoinRoom
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            JoinRoom.decodeDelimited = function decodeDelimited(reader) {
                if (!(reader instanceof $Reader))
                    reader = new $Reader(reader);
                return this.decode(reader, reader.uint32());
            };

            /**
             * Verifies a JoinRoom message.
             * @function verify
             * @memberof wslobby.Command.JoinRoom
             * @static
             * @param {Object.<string,*>} message Plain object to verify
             * @returns {string|null} `null` if valid, otherwise the reason why it is not
             */
            JoinRoom.verify = function verify(message) {
                if (typeof message !== "object" || message === null)
                    return "object expected";
                if (message.roomId != null && message.hasOwnProperty("roomId"))
                    if (!$util.isString(message.roomId))
                        return "roomId: string expected";
                if (message.greeting != null && message.hasOwnProperty("greeting"))
                    if (!$util.isString(message.greeting))
                        return "greeting: string expected";
                return null;
            };

            /**
             * Creates a JoinRoom message from a plain object. Also converts values to their respective internal types.
             * @function fromObject
             * @memberof wslobby.Command.JoinRoom
             * @static
             * @param {Object.<string,*>} object Plain object
             * @returns {wslobby.Command.JoinRoom} JoinRoom
             */
            JoinRoom.fromObject = function fromObject(object) {
                if (object instanceof $root.wslobby.Command.JoinRoom)
                    return object;
                var message = new $root.wslobby.Command.JoinRoom();
                if (object.roomId != null)
                    message.roomId = String(object.roomId);
                if (object.greeting != null)
                    message.greeting = String(object.greeting);
                return message;
            };

            /**
             * Creates a plain object from a JoinRoom message. Also converts values to other types if specified.
             * @function toObject
             * @memberof wslobby.Command.JoinRoom
             * @static
             * @param {wslobby.Command.JoinRoom} message JoinRoom
             * @param {$protobuf.IConversionOptions} [options] Conversion options
             * @returns {Object.<string,*>} Plain object
             */
            JoinRoom.toObject = function toObject(message, options) {
                if (!options)
                    options = {};
                var object = {};
                if (options.defaults) {
                    object.roomId = "";
                    object.greeting = "";
                }
                if (message.roomId != null && message.hasOwnProperty("roomId"))
                    object.roomId = message.roomId;
                if (message.greeting != null && message.hasOwnProperty("greeting"))
                    object.greeting = message.greeting;
                return object;
            };

            /**
             * Converts this JoinRoom to JSON.
             * @function toJSON
             * @memberof wslobby.Command.JoinRoom
             * @instance
             * @returns {Object.<string,*>} JSON object
             */
            JoinRoom.prototype.toJSON = function toJSON() {
                return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
            };

            return JoinRoom;
        })();

        Command.Leave = (function() {

            /**
             * Properties of a Leave.
             * @memberof wslobby.Command
             * @interface ILeave
             * @property {string|null} [goodbye] Leave goodbye
             */

            /**
             * Constructs a new Leave.
             * @memberof wslobby.Command
             * @classdesc Represents a Leave.
             * @implements ILeave
             * @constructor
             * @param {wslobby.Command.ILeave=} [properties] Properties to set
             */
            function Leave(properties) {
                if (properties)
                    for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                        if (properties[keys[i]] != null)
                            this[keys[i]] = properties[keys[i]];
            }

            /**
             * Leave goodbye.
             * @member {string} goodbye
             * @memberof wslobby.Command.Leave
             * @instance
             */
            Leave.prototype.goodbye = "";

            /**
             * Creates a new Leave instance using the specified properties.
             * @function create
             * @memberof wslobby.Command.Leave
             * @static
             * @param {wslobby.Command.ILeave=} [properties] Properties to set
             * @returns {wslobby.Command.Leave} Leave instance
             */
            Leave.create = function create(properties) {
                return new Leave(properties);
            };

            /**
             * Encodes the specified Leave message. Does not implicitly {@link wslobby.Command.Leave.verify|verify} messages.
             * @function encode
             * @memberof wslobby.Command.Leave
             * @static
             * @param {wslobby.Command.ILeave} message Leave message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            Leave.encode = function encode(message, writer) {
                if (!writer)
                    writer = $Writer.create();
                if (message.goodbye != null && Object.hasOwnProperty.call(message, "goodbye"))
                    writer.uint32(/* id 1, wireType 2 =*/10).string(message.goodbye);
                return writer;
            };

            /**
             * Encodes the specified Leave message, length delimited. Does not implicitly {@link wslobby.Command.Leave.verify|verify} messages.
             * @function encodeDelimited
             * @memberof wslobby.Command.Leave
             * @static
             * @param {wslobby.Command.ILeave} message Leave message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            Leave.encodeDelimited = function encodeDelimited(message, writer) {
                return this.encode(message, writer).ldelim();
            };

            /**
             * Decodes a Leave message from the specified reader or buffer.
             * @function decode
             * @memberof wslobby.Command.Leave
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @param {number} [length] Message length if known beforehand
             * @returns {wslobby.Command.Leave} Leave
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            Leave.decode = function decode(reader, length) {
                if (!(reader instanceof $Reader))
                    reader = $Reader.create(reader);
                var end = length === undefined ? reader.len : reader.pos + length, message = new $root.wslobby.Command.Leave();
                while (reader.pos < end) {
                    var tag = reader.uint32();
                    switch (tag >>> 3) {
                    case 1:
                        message.goodbye = reader.string();
                        break;
                    default:
                        reader.skipType(tag & 7);
                        break;
                    }
                }
                return message;
            };

            /**
             * Decodes a Leave message from the specified reader or buffer, length delimited.
             * @function decodeDelimited
             * @memberof wslobby.Command.Leave
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @returns {wslobby.Command.Leave} Leave
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            Leave.decodeDelimited = function decodeDelimited(reader) {
                if (!(reader instanceof $Reader))
                    reader = new $Reader(reader);
                return this.decode(reader, reader.uint32());
            };

            /**
             * Verifies a Leave message.
             * @function verify
             * @memberof wslobby.Command.Leave
             * @static
             * @param {Object.<string,*>} message Plain object to verify
             * @returns {string|null} `null` if valid, otherwise the reason why it is not
             */
            Leave.verify = function verify(message) {
                if (typeof message !== "object" || message === null)
                    return "object expected";
                if (message.goodbye != null && message.hasOwnProperty("goodbye"))
                    if (!$util.isString(message.goodbye))
                        return "goodbye: string expected";
                return null;
            };

            /**
             * Creates a Leave message from a plain object. Also converts values to their respective internal types.
             * @function fromObject
             * @memberof wslobby.Command.Leave
             * @static
             * @param {Object.<string,*>} object Plain object
             * @returns {wslobby.Command.Leave} Leave
             */
            Leave.fromObject = function fromObject(object) {
                if (object instanceof $root.wslobby.Command.Leave)
                    return object;
                var message = new $root.wslobby.Command.Leave();
                if (object.goodbye != null)
                    message.goodbye = String(object.goodbye);
                return message;
            };

            /**
             * Creates a plain object from a Leave message. Also converts values to other types if specified.
             * @function toObject
             * @memberof wslobby.Command.Leave
             * @static
             * @param {wslobby.Command.Leave} message Leave
             * @param {$protobuf.IConversionOptions} [options] Conversion options
             * @returns {Object.<string,*>} Plain object
             */
            Leave.toObject = function toObject(message, options) {
                if (!options)
                    options = {};
                var object = {};
                if (options.defaults)
                    object.goodbye = "";
                if (message.goodbye != null && message.hasOwnProperty("goodbye"))
                    object.goodbye = message.goodbye;
                return object;
            };

            /**
             * Converts this Leave to JSON.
             * @function toJSON
             * @memberof wslobby.Command.Leave
             * @instance
             * @returns {Object.<string,*>} JSON object
             */
            Leave.prototype.toJSON = function toJSON() {
                return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
            };

            return Leave;
        })();

        Command.KickGuest = (function() {

            /**
             * Properties of a KickGuest.
             * @memberof wslobby.Command
             * @interface IKickGuest
             * @property {string|null} [guestId] KickGuest guestId
             * @property {string|null} [message] KickGuest message
             */

            /**
             * Constructs a new KickGuest.
             * @memberof wslobby.Command
             * @classdesc Represents a KickGuest.
             * @implements IKickGuest
             * @constructor
             * @param {wslobby.Command.IKickGuest=} [properties] Properties to set
             */
            function KickGuest(properties) {
                if (properties)
                    for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                        if (properties[keys[i]] != null)
                            this[keys[i]] = properties[keys[i]];
            }

            /**
             * KickGuest guestId.
             * @member {string} guestId
             * @memberof wslobby.Command.KickGuest
             * @instance
             */
            KickGuest.prototype.guestId = "";

            /**
             * KickGuest message.
             * @member {string} message
             * @memberof wslobby.Command.KickGuest
             * @instance
             */
            KickGuest.prototype.message = "";

            /**
             * Creates a new KickGuest instance using the specified properties.
             * @function create
             * @memberof wslobby.Command.KickGuest
             * @static
             * @param {wslobby.Command.IKickGuest=} [properties] Properties to set
             * @returns {wslobby.Command.KickGuest} KickGuest instance
             */
            KickGuest.create = function create(properties) {
                return new KickGuest(properties);
            };

            /**
             * Encodes the specified KickGuest message. Does not implicitly {@link wslobby.Command.KickGuest.verify|verify} messages.
             * @function encode
             * @memberof wslobby.Command.KickGuest
             * @static
             * @param {wslobby.Command.IKickGuest} message KickGuest message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            KickGuest.encode = function encode(message, writer) {
                if (!writer)
                    writer = $Writer.create();
                if (message.guestId != null && Object.hasOwnProperty.call(message, "guestId"))
                    writer.uint32(/* id 1, wireType 2 =*/10).string(message.guestId);
                if (message.message != null && Object.hasOwnProperty.call(message, "message"))
                    writer.uint32(/* id 2, wireType 2 =*/18).string(message.message);
                return writer;
            };

            /**
             * Encodes the specified KickGuest message, length delimited. Does not implicitly {@link wslobby.Command.KickGuest.verify|verify} messages.
             * @function encodeDelimited
             * @memberof wslobby.Command.KickGuest
             * @static
             * @param {wslobby.Command.IKickGuest} message KickGuest message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            KickGuest.encodeDelimited = function encodeDelimited(message, writer) {
                return this.encode(message, writer).ldelim();
            };

            /**
             * Decodes a KickGuest message from the specified reader or buffer.
             * @function decode
             * @memberof wslobby.Command.KickGuest
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @param {number} [length] Message length if known beforehand
             * @returns {wslobby.Command.KickGuest} KickGuest
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            KickGuest.decode = function decode(reader, length) {
                if (!(reader instanceof $Reader))
                    reader = $Reader.create(reader);
                var end = length === undefined ? reader.len : reader.pos + length, message = new $root.wslobby.Command.KickGuest();
                while (reader.pos < end) {
                    var tag = reader.uint32();
                    switch (tag >>> 3) {
                    case 1:
                        message.guestId = reader.string();
                        break;
                    case 2:
                        message.message = reader.string();
                        break;
                    default:
                        reader.skipType(tag & 7);
                        break;
                    }
                }
                return message;
            };

            /**
             * Decodes a KickGuest message from the specified reader or buffer, length delimited.
             * @function decodeDelimited
             * @memberof wslobby.Command.KickGuest
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @returns {wslobby.Command.KickGuest} KickGuest
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            KickGuest.decodeDelimited = function decodeDelimited(reader) {
                if (!(reader instanceof $Reader))
                    reader = new $Reader(reader);
                return this.decode(reader, reader.uint32());
            };

            /**
             * Verifies a KickGuest message.
             * @function verify
             * @memberof wslobby.Command.KickGuest
             * @static
             * @param {Object.<string,*>} message Plain object to verify
             * @returns {string|null} `null` if valid, otherwise the reason why it is not
             */
            KickGuest.verify = function verify(message) {
                if (typeof message !== "object" || message === null)
                    return "object expected";
                if (message.guestId != null && message.hasOwnProperty("guestId"))
                    if (!$util.isString(message.guestId))
                        return "guestId: string expected";
                if (message.message != null && message.hasOwnProperty("message"))
                    if (!$util.isString(message.message))
                        return "message: string expected";
                return null;
            };

            /**
             * Creates a KickGuest message from a plain object. Also converts values to their respective internal types.
             * @function fromObject
             * @memberof wslobby.Command.KickGuest
             * @static
             * @param {Object.<string,*>} object Plain object
             * @returns {wslobby.Command.KickGuest} KickGuest
             */
            KickGuest.fromObject = function fromObject(object) {
                if (object instanceof $root.wslobby.Command.KickGuest)
                    return object;
                var message = new $root.wslobby.Command.KickGuest();
                if (object.guestId != null)
                    message.guestId = String(object.guestId);
                if (object.message != null)
                    message.message = String(object.message);
                return message;
            };

            /**
             * Creates a plain object from a KickGuest message. Also converts values to other types if specified.
             * @function toObject
             * @memberof wslobby.Command.KickGuest
             * @static
             * @param {wslobby.Command.KickGuest} message KickGuest
             * @param {$protobuf.IConversionOptions} [options] Conversion options
             * @returns {Object.<string,*>} Plain object
             */
            KickGuest.toObject = function toObject(message, options) {
                if (!options)
                    options = {};
                var object = {};
                if (options.defaults) {
                    object.guestId = "";
                    object.message = "";
                }
                if (message.guestId != null && message.hasOwnProperty("guestId"))
                    object.guestId = message.guestId;
                if (message.message != null && message.hasOwnProperty("message"))
                    object.message = message.message;
                return object;
            };

            /**
             * Converts this KickGuest to JSON.
             * @function toJSON
             * @memberof wslobby.Command.KickGuest
             * @instance
             * @returns {Object.<string,*>} JSON object
             */
            KickGuest.prototype.toJSON = function toJSON() {
                return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
            };

            return KickGuest;
        })();

        Command.AllowGuest = (function() {

            /**
             * Properties of an AllowGuest.
             * @memberof wslobby.Command
             * @interface IAllowGuest
             * @property {string|null} [guestId] AllowGuest guestId
             */

            /**
             * Constructs a new AllowGuest.
             * @memberof wslobby.Command
             * @classdesc Represents an AllowGuest.
             * @implements IAllowGuest
             * @constructor
             * @param {wslobby.Command.IAllowGuest=} [properties] Properties to set
             */
            function AllowGuest(properties) {
                if (properties)
                    for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                        if (properties[keys[i]] != null)
                            this[keys[i]] = properties[keys[i]];
            }

            /**
             * AllowGuest guestId.
             * @member {string} guestId
             * @memberof wslobby.Command.AllowGuest
             * @instance
             */
            AllowGuest.prototype.guestId = "";

            /**
             * Creates a new AllowGuest instance using the specified properties.
             * @function create
             * @memberof wslobby.Command.AllowGuest
             * @static
             * @param {wslobby.Command.IAllowGuest=} [properties] Properties to set
             * @returns {wslobby.Command.AllowGuest} AllowGuest instance
             */
            AllowGuest.create = function create(properties) {
                return new AllowGuest(properties);
            };

            /**
             * Encodes the specified AllowGuest message. Does not implicitly {@link wslobby.Command.AllowGuest.verify|verify} messages.
             * @function encode
             * @memberof wslobby.Command.AllowGuest
             * @static
             * @param {wslobby.Command.IAllowGuest} message AllowGuest message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            AllowGuest.encode = function encode(message, writer) {
                if (!writer)
                    writer = $Writer.create();
                if (message.guestId != null && Object.hasOwnProperty.call(message, "guestId"))
                    writer.uint32(/* id 1, wireType 2 =*/10).string(message.guestId);
                return writer;
            };

            /**
             * Encodes the specified AllowGuest message, length delimited. Does not implicitly {@link wslobby.Command.AllowGuest.verify|verify} messages.
             * @function encodeDelimited
             * @memberof wslobby.Command.AllowGuest
             * @static
             * @param {wslobby.Command.IAllowGuest} message AllowGuest message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            AllowGuest.encodeDelimited = function encodeDelimited(message, writer) {
                return this.encode(message, writer).ldelim();
            };

            /**
             * Decodes an AllowGuest message from the specified reader or buffer.
             * @function decode
             * @memberof wslobby.Command.AllowGuest
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @param {number} [length] Message length if known beforehand
             * @returns {wslobby.Command.AllowGuest} AllowGuest
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            AllowGuest.decode = function decode(reader, length) {
                if (!(reader instanceof $Reader))
                    reader = $Reader.create(reader);
                var end = length === undefined ? reader.len : reader.pos + length, message = new $root.wslobby.Command.AllowGuest();
                while (reader.pos < end) {
                    var tag = reader.uint32();
                    switch (tag >>> 3) {
                    case 1:
                        message.guestId = reader.string();
                        break;
                    default:
                        reader.skipType(tag & 7);
                        break;
                    }
                }
                return message;
            };

            /**
             * Decodes an AllowGuest message from the specified reader or buffer, length delimited.
             * @function decodeDelimited
             * @memberof wslobby.Command.AllowGuest
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @returns {wslobby.Command.AllowGuest} AllowGuest
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            AllowGuest.decodeDelimited = function decodeDelimited(reader) {
                if (!(reader instanceof $Reader))
                    reader = new $Reader(reader);
                return this.decode(reader, reader.uint32());
            };

            /**
             * Verifies an AllowGuest message.
             * @function verify
             * @memberof wslobby.Command.AllowGuest
             * @static
             * @param {Object.<string,*>} message Plain object to verify
             * @returns {string|null} `null` if valid, otherwise the reason why it is not
             */
            AllowGuest.verify = function verify(message) {
                if (typeof message !== "object" || message === null)
                    return "object expected";
                if (message.guestId != null && message.hasOwnProperty("guestId"))
                    if (!$util.isString(message.guestId))
                        return "guestId: string expected";
                return null;
            };

            /**
             * Creates an AllowGuest message from a plain object. Also converts values to their respective internal types.
             * @function fromObject
             * @memberof wslobby.Command.AllowGuest
             * @static
             * @param {Object.<string,*>} object Plain object
             * @returns {wslobby.Command.AllowGuest} AllowGuest
             */
            AllowGuest.fromObject = function fromObject(object) {
                if (object instanceof $root.wslobby.Command.AllowGuest)
                    return object;
                var message = new $root.wslobby.Command.AllowGuest();
                if (object.guestId != null)
                    message.guestId = String(object.guestId);
                return message;
            };

            /**
             * Creates a plain object from an AllowGuest message. Also converts values to other types if specified.
             * @function toObject
             * @memberof wslobby.Command.AllowGuest
             * @static
             * @param {wslobby.Command.AllowGuest} message AllowGuest
             * @param {$protobuf.IConversionOptions} [options] Conversion options
             * @returns {Object.<string,*>} Plain object
             */
            AllowGuest.toObject = function toObject(message, options) {
                if (!options)
                    options = {};
                var object = {};
                if (options.defaults)
                    object.guestId = "";
                if (message.guestId != null && message.hasOwnProperty("guestId"))
                    object.guestId = message.guestId;
                return object;
            };

            /**
             * Converts this AllowGuest to JSON.
             * @function toJSON
             * @memberof wslobby.Command.AllowGuest
             * @instance
             * @returns {Object.<string,*>} JSON object
             */
            AllowGuest.prototype.toJSON = function toJSON() {
                return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
            };

            return AllowGuest;
        })();

        Command.Broadcast = (function() {

            /**
             * Properties of a Broadcast.
             * @memberof wslobby.Command
             * @interface IBroadcast
             * @property {Uint8Array|null} [data] Broadcast data
             */

            /**
             * Constructs a new Broadcast.
             * @memberof wslobby.Command
             * @classdesc Represents a Broadcast.
             * @implements IBroadcast
             * @constructor
             * @param {wslobby.Command.IBroadcast=} [properties] Properties to set
             */
            function Broadcast(properties) {
                if (properties)
                    for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                        if (properties[keys[i]] != null)
                            this[keys[i]] = properties[keys[i]];
            }

            /**
             * Broadcast data.
             * @member {Uint8Array} data
             * @memberof wslobby.Command.Broadcast
             * @instance
             */
            Broadcast.prototype.data = $util.newBuffer([]);

            /**
             * Creates a new Broadcast instance using the specified properties.
             * @function create
             * @memberof wslobby.Command.Broadcast
             * @static
             * @param {wslobby.Command.IBroadcast=} [properties] Properties to set
             * @returns {wslobby.Command.Broadcast} Broadcast instance
             */
            Broadcast.create = function create(properties) {
                return new Broadcast(properties);
            };

            /**
             * Encodes the specified Broadcast message. Does not implicitly {@link wslobby.Command.Broadcast.verify|verify} messages.
             * @function encode
             * @memberof wslobby.Command.Broadcast
             * @static
             * @param {wslobby.Command.IBroadcast} message Broadcast message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            Broadcast.encode = function encode(message, writer) {
                if (!writer)
                    writer = $Writer.create();
                if (message.data != null && Object.hasOwnProperty.call(message, "data"))
                    writer.uint32(/* id 1, wireType 2 =*/10).bytes(message.data);
                return writer;
            };

            /**
             * Encodes the specified Broadcast message, length delimited. Does not implicitly {@link wslobby.Command.Broadcast.verify|verify} messages.
             * @function encodeDelimited
             * @memberof wslobby.Command.Broadcast
             * @static
             * @param {wslobby.Command.IBroadcast} message Broadcast message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            Broadcast.encodeDelimited = function encodeDelimited(message, writer) {
                return this.encode(message, writer).ldelim();
            };

            /**
             * Decodes a Broadcast message from the specified reader or buffer.
             * @function decode
             * @memberof wslobby.Command.Broadcast
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @param {number} [length] Message length if known beforehand
             * @returns {wslobby.Command.Broadcast} Broadcast
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            Broadcast.decode = function decode(reader, length) {
                if (!(reader instanceof $Reader))
                    reader = $Reader.create(reader);
                var end = length === undefined ? reader.len : reader.pos + length, message = new $root.wslobby.Command.Broadcast();
                while (reader.pos < end) {
                    var tag = reader.uint32();
                    switch (tag >>> 3) {
                    case 1:
                        message.data = reader.bytes();
                        break;
                    default:
                        reader.skipType(tag & 7);
                        break;
                    }
                }
                return message;
            };

            /**
             * Decodes a Broadcast message from the specified reader or buffer, length delimited.
             * @function decodeDelimited
             * @memberof wslobby.Command.Broadcast
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @returns {wslobby.Command.Broadcast} Broadcast
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            Broadcast.decodeDelimited = function decodeDelimited(reader) {
                if (!(reader instanceof $Reader))
                    reader = new $Reader(reader);
                return this.decode(reader, reader.uint32());
            };

            /**
             * Verifies a Broadcast message.
             * @function verify
             * @memberof wslobby.Command.Broadcast
             * @static
             * @param {Object.<string,*>} message Plain object to verify
             * @returns {string|null} `null` if valid, otherwise the reason why it is not
             */
            Broadcast.verify = function verify(message) {
                if (typeof message !== "object" || message === null)
                    return "object expected";
                if (message.data != null && message.hasOwnProperty("data"))
                    if (!(message.data && typeof message.data.length === "number" || $util.isString(message.data)))
                        return "data: buffer expected";
                return null;
            };

            /**
             * Creates a Broadcast message from a plain object. Also converts values to their respective internal types.
             * @function fromObject
             * @memberof wslobby.Command.Broadcast
             * @static
             * @param {Object.<string,*>} object Plain object
             * @returns {wslobby.Command.Broadcast} Broadcast
             */
            Broadcast.fromObject = function fromObject(object) {
                if (object instanceof $root.wslobby.Command.Broadcast)
                    return object;
                var message = new $root.wslobby.Command.Broadcast();
                if (object.data != null)
                    if (typeof object.data === "string")
                        $util.base64.decode(object.data, message.data = $util.newBuffer($util.base64.length(object.data)), 0);
                    else if (object.data.length)
                        message.data = object.data;
                return message;
            };

            /**
             * Creates a plain object from a Broadcast message. Also converts values to other types if specified.
             * @function toObject
             * @memberof wslobby.Command.Broadcast
             * @static
             * @param {wslobby.Command.Broadcast} message Broadcast
             * @param {$protobuf.IConversionOptions} [options] Conversion options
             * @returns {Object.<string,*>} Plain object
             */
            Broadcast.toObject = function toObject(message, options) {
                if (!options)
                    options = {};
                var object = {};
                if (options.defaults)
                    if (options.bytes === String)
                        object.data = "";
                    else {
                        object.data = [];
                        if (options.bytes !== Array)
                            object.data = $util.newBuffer(object.data);
                    }
                if (message.data != null && message.hasOwnProperty("data"))
                    object.data = options.bytes === String ? $util.base64.encode(message.data, 0, message.data.length) : options.bytes === Array ? Array.prototype.slice.call(message.data) : message.data;
                return object;
            };

            /**
             * Converts this Broadcast to JSON.
             * @function toJSON
             * @memberof wslobby.Command.Broadcast
             * @instance
             * @returns {Object.<string,*>} JSON object
             */
            Broadcast.prototype.toJSON = function toJSON() {
                return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
            };

            return Broadcast;
        })();

        Command.Unicast = (function() {

            /**
             * Properties of an Unicast.
             * @memberof wslobby.Command
             * @interface IUnicast
             * @property {string|null} [guestId] Unicast guestId
             * @property {Uint8Array|null} [data] Unicast data
             */

            /**
             * Constructs a new Unicast.
             * @memberof wslobby.Command
             * @classdesc Represents an Unicast.
             * @implements IUnicast
             * @constructor
             * @param {wslobby.Command.IUnicast=} [properties] Properties to set
             */
            function Unicast(properties) {
                if (properties)
                    for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                        if (properties[keys[i]] != null)
                            this[keys[i]] = properties[keys[i]];
            }

            /**
             * Unicast guestId.
             * @member {string} guestId
             * @memberof wslobby.Command.Unicast
             * @instance
             */
            Unicast.prototype.guestId = "";

            /**
             * Unicast data.
             * @member {Uint8Array} data
             * @memberof wslobby.Command.Unicast
             * @instance
             */
            Unicast.prototype.data = $util.newBuffer([]);

            /**
             * Creates a new Unicast instance using the specified properties.
             * @function create
             * @memberof wslobby.Command.Unicast
             * @static
             * @param {wslobby.Command.IUnicast=} [properties] Properties to set
             * @returns {wslobby.Command.Unicast} Unicast instance
             */
            Unicast.create = function create(properties) {
                return new Unicast(properties);
            };

            /**
             * Encodes the specified Unicast message. Does not implicitly {@link wslobby.Command.Unicast.verify|verify} messages.
             * @function encode
             * @memberof wslobby.Command.Unicast
             * @static
             * @param {wslobby.Command.IUnicast} message Unicast message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            Unicast.encode = function encode(message, writer) {
                if (!writer)
                    writer = $Writer.create();
                if (message.guestId != null && Object.hasOwnProperty.call(message, "guestId"))
                    writer.uint32(/* id 1, wireType 2 =*/10).string(message.guestId);
                if (message.data != null && Object.hasOwnProperty.call(message, "data"))
                    writer.uint32(/* id 2, wireType 2 =*/18).bytes(message.data);
                return writer;
            };

            /**
             * Encodes the specified Unicast message, length delimited. Does not implicitly {@link wslobby.Command.Unicast.verify|verify} messages.
             * @function encodeDelimited
             * @memberof wslobby.Command.Unicast
             * @static
             * @param {wslobby.Command.IUnicast} message Unicast message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            Unicast.encodeDelimited = function encodeDelimited(message, writer) {
                return this.encode(message, writer).ldelim();
            };

            /**
             * Decodes an Unicast message from the specified reader or buffer.
             * @function decode
             * @memberof wslobby.Command.Unicast
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @param {number} [length] Message length if known beforehand
             * @returns {wslobby.Command.Unicast} Unicast
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            Unicast.decode = function decode(reader, length) {
                if (!(reader instanceof $Reader))
                    reader = $Reader.create(reader);
                var end = length === undefined ? reader.len : reader.pos + length, message = new $root.wslobby.Command.Unicast();
                while (reader.pos < end) {
                    var tag = reader.uint32();
                    switch (tag >>> 3) {
                    case 1:
                        message.guestId = reader.string();
                        break;
                    case 2:
                        message.data = reader.bytes();
                        break;
                    default:
                        reader.skipType(tag & 7);
                        break;
                    }
                }
                return message;
            };

            /**
             * Decodes an Unicast message from the specified reader or buffer, length delimited.
             * @function decodeDelimited
             * @memberof wslobby.Command.Unicast
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @returns {wslobby.Command.Unicast} Unicast
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            Unicast.decodeDelimited = function decodeDelimited(reader) {
                if (!(reader instanceof $Reader))
                    reader = new $Reader(reader);
                return this.decode(reader, reader.uint32());
            };

            /**
             * Verifies an Unicast message.
             * @function verify
             * @memberof wslobby.Command.Unicast
             * @static
             * @param {Object.<string,*>} message Plain object to verify
             * @returns {string|null} `null` if valid, otherwise the reason why it is not
             */
            Unicast.verify = function verify(message) {
                if (typeof message !== "object" || message === null)
                    return "object expected";
                if (message.guestId != null && message.hasOwnProperty("guestId"))
                    if (!$util.isString(message.guestId))
                        return "guestId: string expected";
                if (message.data != null && message.hasOwnProperty("data"))
                    if (!(message.data && typeof message.data.length === "number" || $util.isString(message.data)))
                        return "data: buffer expected";
                return null;
            };

            /**
             * Creates an Unicast message from a plain object. Also converts values to their respective internal types.
             * @function fromObject
             * @memberof wslobby.Command.Unicast
             * @static
             * @param {Object.<string,*>} object Plain object
             * @returns {wslobby.Command.Unicast} Unicast
             */
            Unicast.fromObject = function fromObject(object) {
                if (object instanceof $root.wslobby.Command.Unicast)
                    return object;
                var message = new $root.wslobby.Command.Unicast();
                if (object.guestId != null)
                    message.guestId = String(object.guestId);
                if (object.data != null)
                    if (typeof object.data === "string")
                        $util.base64.decode(object.data, message.data = $util.newBuffer($util.base64.length(object.data)), 0);
                    else if (object.data.length)
                        message.data = object.data;
                return message;
            };

            /**
             * Creates a plain object from an Unicast message. Also converts values to other types if specified.
             * @function toObject
             * @memberof wslobby.Command.Unicast
             * @static
             * @param {wslobby.Command.Unicast} message Unicast
             * @param {$protobuf.IConversionOptions} [options] Conversion options
             * @returns {Object.<string,*>} Plain object
             */
            Unicast.toObject = function toObject(message, options) {
                if (!options)
                    options = {};
                var object = {};
                if (options.defaults) {
                    object.guestId = "";
                    if (options.bytes === String)
                        object.data = "";
                    else {
                        object.data = [];
                        if (options.bytes !== Array)
                            object.data = $util.newBuffer(object.data);
                    }
                }
                if (message.guestId != null && message.hasOwnProperty("guestId"))
                    object.guestId = message.guestId;
                if (message.data != null && message.hasOwnProperty("data"))
                    object.data = options.bytes === String ? $util.base64.encode(message.data, 0, message.data.length) : options.bytes === Array ? Array.prototype.slice.call(message.data) : message.data;
                return object;
            };

            /**
             * Converts this Unicast to JSON.
             * @function toJSON
             * @memberof wslobby.Command.Unicast
             * @instance
             * @returns {Object.<string,*>} JSON object
             */
            Unicast.prototype.toJSON = function toJSON() {
                return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
            };

            return Unicast;
        })();

        return Command;
    })();

    wslobby.Reply = (function() {

        /**
         * Properties of a Reply.
         * @memberof wslobby
         * @interface IReply
         * @property {number|null} [id] Reply id
         * @property {wslobby.Reply.IPong|null} [pong] Reply pong
         * @property {wslobby.Reply.IAck|null} [ack] Reply ack
         * @property {wslobby.Reply.IError|null} [error] Reply error
         * @property {wslobby.Reply.IGuestKnock|null} [guestKnock] Reply guestKnock
         * @property {wslobby.Reply.IGuestLeft|null} [guestLeft] Reply guestLeft
         * @property {wslobby.Reply.IEnteredRoom|null} [enteredRoom] Reply enteredRoom
         * @property {wslobby.Reply.IData|null} [data] Reply data
         */

        /**
         * Constructs a new Reply.
         * @memberof wslobby
         * @classdesc Represents a Reply.
         * @implements IReply
         * @constructor
         * @param {wslobby.IReply=} [properties] Properties to set
         */
        function Reply(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }

        /**
         * Reply id.
         * @member {number} id
         * @memberof wslobby.Reply
         * @instance
         */
        Reply.prototype.id = 0;

        /**
         * Reply pong.
         * @member {wslobby.Reply.IPong|null|undefined} pong
         * @memberof wslobby.Reply
         * @instance
         */
        Reply.prototype.pong = null;

        /**
         * Reply ack.
         * @member {wslobby.Reply.IAck|null|undefined} ack
         * @memberof wslobby.Reply
         * @instance
         */
        Reply.prototype.ack = null;

        /**
         * Reply error.
         * @member {wslobby.Reply.IError|null|undefined} error
         * @memberof wslobby.Reply
         * @instance
         */
        Reply.prototype.error = null;

        /**
         * Reply guestKnock.
         * @member {wslobby.Reply.IGuestKnock|null|undefined} guestKnock
         * @memberof wslobby.Reply
         * @instance
         */
        Reply.prototype.guestKnock = null;

        /**
         * Reply guestLeft.
         * @member {wslobby.Reply.IGuestLeft|null|undefined} guestLeft
         * @memberof wslobby.Reply
         * @instance
         */
        Reply.prototype.guestLeft = null;

        /**
         * Reply enteredRoom.
         * @member {wslobby.Reply.IEnteredRoom|null|undefined} enteredRoom
         * @memberof wslobby.Reply
         * @instance
         */
        Reply.prototype.enteredRoom = null;

        /**
         * Reply data.
         * @member {wslobby.Reply.IData|null|undefined} data
         * @memberof wslobby.Reply
         * @instance
         */
        Reply.prototype.data = null;

        // OneOf field names bound to virtual getters and setters
        var $oneOfFields;

        /**
         * Reply payload.
         * @member {"pong"|"ack"|"error"|"guestKnock"|"guestLeft"|"enteredRoom"|"data"|undefined} payload
         * @memberof wslobby.Reply
         * @instance
         */
        Object.defineProperty(Reply.prototype, "payload", {
            get: $util.oneOfGetter($oneOfFields = ["pong", "ack", "error", "guestKnock", "guestLeft", "enteredRoom", "data"]),
            set: $util.oneOfSetter($oneOfFields)
        });

        /**
         * Creates a new Reply instance using the specified properties.
         * @function create
         * @memberof wslobby.Reply
         * @static
         * @param {wslobby.IReply=} [properties] Properties to set
         * @returns {wslobby.Reply} Reply instance
         */
        Reply.create = function create(properties) {
            return new Reply(properties);
        };

        /**
         * Encodes the specified Reply message. Does not implicitly {@link wslobby.Reply.verify|verify} messages.
         * @function encode
         * @memberof wslobby.Reply
         * @static
         * @param {wslobby.IReply} message Reply message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        Reply.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            if (message.id != null && Object.hasOwnProperty.call(message, "id"))
                writer.uint32(/* id 1, wireType 0 =*/8).uint32(message.id);
            if (message.pong != null && Object.hasOwnProperty.call(message, "pong"))
                $root.wslobby.Reply.Pong.encode(message.pong, writer.uint32(/* id 2, wireType 2 =*/18).fork()).ldelim();
            if (message.ack != null && Object.hasOwnProperty.call(message, "ack"))
                $root.wslobby.Reply.Ack.encode(message.ack, writer.uint32(/* id 3, wireType 2 =*/26).fork()).ldelim();
            if (message.error != null && Object.hasOwnProperty.call(message, "error"))
                $root.wslobby.Reply.Error.encode(message.error, writer.uint32(/* id 4, wireType 2 =*/34).fork()).ldelim();
            if (message.guestKnock != null && Object.hasOwnProperty.call(message, "guestKnock"))
                $root.wslobby.Reply.GuestKnock.encode(message.guestKnock, writer.uint32(/* id 5, wireType 2 =*/42).fork()).ldelim();
            if (message.guestLeft != null && Object.hasOwnProperty.call(message, "guestLeft"))
                $root.wslobby.Reply.GuestLeft.encode(message.guestLeft, writer.uint32(/* id 6, wireType 2 =*/50).fork()).ldelim();
            if (message.enteredRoom != null && Object.hasOwnProperty.call(message, "enteredRoom"))
                $root.wslobby.Reply.EnteredRoom.encode(message.enteredRoom, writer.uint32(/* id 7, wireType 2 =*/58).fork()).ldelim();
            if (message.data != null && Object.hasOwnProperty.call(message, "data"))
                $root.wslobby.Reply.Data.encode(message.data, writer.uint32(/* id 8, wireType 2 =*/66).fork()).ldelim();
            return writer;
        };

        /**
         * Encodes the specified Reply message, length delimited. Does not implicitly {@link wslobby.Reply.verify|verify} messages.
         * @function encodeDelimited
         * @memberof wslobby.Reply
         * @static
         * @param {wslobby.IReply} message Reply message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        Reply.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };

        /**
         * Decodes a Reply message from the specified reader or buffer.
         * @function decode
         * @memberof wslobby.Reply
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @param {number} [length] Message length if known beforehand
         * @returns {wslobby.Reply} Reply
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        Reply.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.wslobby.Reply();
            while (reader.pos < end) {
                var tag = reader.uint32();
                switch (tag >>> 3) {
                case 1:
                    message.id = reader.uint32();
                    break;
                case 2:
                    message.pong = $root.wslobby.Reply.Pong.decode(reader, reader.uint32());
                    break;
                case 3:
                    message.ack = $root.wslobby.Reply.Ack.decode(reader, reader.uint32());
                    break;
                case 4:
                    message.error = $root.wslobby.Reply.Error.decode(reader, reader.uint32());
                    break;
                case 5:
                    message.guestKnock = $root.wslobby.Reply.GuestKnock.decode(reader, reader.uint32());
                    break;
                case 6:
                    message.guestLeft = $root.wslobby.Reply.GuestLeft.decode(reader, reader.uint32());
                    break;
                case 7:
                    message.enteredRoom = $root.wslobby.Reply.EnteredRoom.decode(reader, reader.uint32());
                    break;
                case 8:
                    message.data = $root.wslobby.Reply.Data.decode(reader, reader.uint32());
                    break;
                default:
                    reader.skipType(tag & 7);
                    break;
                }
            }
            return message;
        };

        /**
         * Decodes a Reply message from the specified reader or buffer, length delimited.
         * @function decodeDelimited
         * @memberof wslobby.Reply
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @returns {wslobby.Reply} Reply
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        Reply.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };

        /**
         * Verifies a Reply message.
         * @function verify
         * @memberof wslobby.Reply
         * @static
         * @param {Object.<string,*>} message Plain object to verify
         * @returns {string|null} `null` if valid, otherwise the reason why it is not
         */
        Reply.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            var properties = {};
            if (message.id != null && message.hasOwnProperty("id"))
                if (!$util.isInteger(message.id))
                    return "id: integer expected";
            if (message.pong != null && message.hasOwnProperty("pong")) {
                properties.payload = 1;
                {
                    var error = $root.wslobby.Reply.Pong.verify(message.pong);
                    if (error)
                        return "pong." + error;
                }
            }
            if (message.ack != null && message.hasOwnProperty("ack")) {
                if (properties.payload === 1)
                    return "payload: multiple values";
                properties.payload = 1;
                {
                    var error = $root.wslobby.Reply.Ack.verify(message.ack);
                    if (error)
                        return "ack." + error;
                }
            }
            if (message.error != null && message.hasOwnProperty("error")) {
                if (properties.payload === 1)
                    return "payload: multiple values";
                properties.payload = 1;
                {
                    var error = $root.wslobby.Reply.Error.verify(message.error);
                    if (error)
                        return "error." + error;
                }
            }
            if (message.guestKnock != null && message.hasOwnProperty("guestKnock")) {
                if (properties.payload === 1)
                    return "payload: multiple values";
                properties.payload = 1;
                {
                    var error = $root.wslobby.Reply.GuestKnock.verify(message.guestKnock);
                    if (error)
                        return "guestKnock." + error;
                }
            }
            if (message.guestLeft != null && message.hasOwnProperty("guestLeft")) {
                if (properties.payload === 1)
                    return "payload: multiple values";
                properties.payload = 1;
                {
                    var error = $root.wslobby.Reply.GuestLeft.verify(message.guestLeft);
                    if (error)
                        return "guestLeft." + error;
                }
            }
            if (message.enteredRoom != null && message.hasOwnProperty("enteredRoom")) {
                if (properties.payload === 1)
                    return "payload: multiple values";
                properties.payload = 1;
                {
                    var error = $root.wslobby.Reply.EnteredRoom.verify(message.enteredRoom);
                    if (error)
                        return "enteredRoom." + error;
                }
            }
            if (message.data != null && message.hasOwnProperty("data")) {
                if (properties.payload === 1)
                    return "payload: multiple values";
                properties.payload = 1;
                {
                    var error = $root.wslobby.Reply.Data.verify(message.data);
                    if (error)
                        return "data." + error;
                }
            }
            return null;
        };

        /**
         * Creates a Reply message from a plain object. Also converts values to their respective internal types.
         * @function fromObject
         * @memberof wslobby.Reply
         * @static
         * @param {Object.<string,*>} object Plain object
         * @returns {wslobby.Reply} Reply
         */
        Reply.fromObject = function fromObject(object) {
            if (object instanceof $root.wslobby.Reply)
                return object;
            var message = new $root.wslobby.Reply();
            if (object.id != null)
                message.id = object.id >>> 0;
            if (object.pong != null) {
                if (typeof object.pong !== "object")
                    throw TypeError(".wslobby.Reply.pong: object expected");
                message.pong = $root.wslobby.Reply.Pong.fromObject(object.pong);
            }
            if (object.ack != null) {
                if (typeof object.ack !== "object")
                    throw TypeError(".wslobby.Reply.ack: object expected");
                message.ack = $root.wslobby.Reply.Ack.fromObject(object.ack);
            }
            if (object.error != null) {
                if (typeof object.error !== "object")
                    throw TypeError(".wslobby.Reply.error: object expected");
                message.error = $root.wslobby.Reply.Error.fromObject(object.error);
            }
            if (object.guestKnock != null) {
                if (typeof object.guestKnock !== "object")
                    throw TypeError(".wslobby.Reply.guestKnock: object expected");
                message.guestKnock = $root.wslobby.Reply.GuestKnock.fromObject(object.guestKnock);
            }
            if (object.guestLeft != null) {
                if (typeof object.guestLeft !== "object")
                    throw TypeError(".wslobby.Reply.guestLeft: object expected");
                message.guestLeft = $root.wslobby.Reply.GuestLeft.fromObject(object.guestLeft);
            }
            if (object.enteredRoom != null) {
                if (typeof object.enteredRoom !== "object")
                    throw TypeError(".wslobby.Reply.enteredRoom: object expected");
                message.enteredRoom = $root.wslobby.Reply.EnteredRoom.fromObject(object.enteredRoom);
            }
            if (object.data != null) {
                if (typeof object.data !== "object")
                    throw TypeError(".wslobby.Reply.data: object expected");
                message.data = $root.wslobby.Reply.Data.fromObject(object.data);
            }
            return message;
        };

        /**
         * Creates a plain object from a Reply message. Also converts values to other types if specified.
         * @function toObject
         * @memberof wslobby.Reply
         * @static
         * @param {wslobby.Reply} message Reply
         * @param {$protobuf.IConversionOptions} [options] Conversion options
         * @returns {Object.<string,*>} Plain object
         */
        Reply.toObject = function toObject(message, options) {
            if (!options)
                options = {};
            var object = {};
            if (options.defaults)
                object.id = 0;
            if (message.id != null && message.hasOwnProperty("id"))
                object.id = message.id;
            if (message.pong != null && message.hasOwnProperty("pong")) {
                object.pong = $root.wslobby.Reply.Pong.toObject(message.pong, options);
                if (options.oneofs)
                    object.payload = "pong";
            }
            if (message.ack != null && message.hasOwnProperty("ack")) {
                object.ack = $root.wslobby.Reply.Ack.toObject(message.ack, options);
                if (options.oneofs)
                    object.payload = "ack";
            }
            if (message.error != null && message.hasOwnProperty("error")) {
                object.error = $root.wslobby.Reply.Error.toObject(message.error, options);
                if (options.oneofs)
                    object.payload = "error";
            }
            if (message.guestKnock != null && message.hasOwnProperty("guestKnock")) {
                object.guestKnock = $root.wslobby.Reply.GuestKnock.toObject(message.guestKnock, options);
                if (options.oneofs)
                    object.payload = "guestKnock";
            }
            if (message.guestLeft != null && message.hasOwnProperty("guestLeft")) {
                object.guestLeft = $root.wslobby.Reply.GuestLeft.toObject(message.guestLeft, options);
                if (options.oneofs)
                    object.payload = "guestLeft";
            }
            if (message.enteredRoom != null && message.hasOwnProperty("enteredRoom")) {
                object.enteredRoom = $root.wslobby.Reply.EnteredRoom.toObject(message.enteredRoom, options);
                if (options.oneofs)
                    object.payload = "enteredRoom";
            }
            if (message.data != null && message.hasOwnProperty("data")) {
                object.data = $root.wslobby.Reply.Data.toObject(message.data, options);
                if (options.oneofs)
                    object.payload = "data";
            }
            return object;
        };

        /**
         * Converts this Reply to JSON.
         * @function toJSON
         * @memberof wslobby.Reply
         * @instance
         * @returns {Object.<string,*>} JSON object
         */
        Reply.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };

        Reply.Pong = (function() {

            /**
             * Properties of a Pong.
             * @memberof wslobby.Reply
             * @interface IPong
             */

            /**
             * Constructs a new Pong.
             * @memberof wslobby.Reply
             * @classdesc Represents a Pong.
             * @implements IPong
             * @constructor
             * @param {wslobby.Reply.IPong=} [properties] Properties to set
             */
            function Pong(properties) {
                if (properties)
                    for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                        if (properties[keys[i]] != null)
                            this[keys[i]] = properties[keys[i]];
            }

            /**
             * Creates a new Pong instance using the specified properties.
             * @function create
             * @memberof wslobby.Reply.Pong
             * @static
             * @param {wslobby.Reply.IPong=} [properties] Properties to set
             * @returns {wslobby.Reply.Pong} Pong instance
             */
            Pong.create = function create(properties) {
                return new Pong(properties);
            };

            /**
             * Encodes the specified Pong message. Does not implicitly {@link wslobby.Reply.Pong.verify|verify} messages.
             * @function encode
             * @memberof wslobby.Reply.Pong
             * @static
             * @param {wslobby.Reply.IPong} message Pong message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            Pong.encode = function encode(message, writer) {
                if (!writer)
                    writer = $Writer.create();
                return writer;
            };

            /**
             * Encodes the specified Pong message, length delimited. Does not implicitly {@link wslobby.Reply.Pong.verify|verify} messages.
             * @function encodeDelimited
             * @memberof wslobby.Reply.Pong
             * @static
             * @param {wslobby.Reply.IPong} message Pong message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            Pong.encodeDelimited = function encodeDelimited(message, writer) {
                return this.encode(message, writer).ldelim();
            };

            /**
             * Decodes a Pong message from the specified reader or buffer.
             * @function decode
             * @memberof wslobby.Reply.Pong
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @param {number} [length] Message length if known beforehand
             * @returns {wslobby.Reply.Pong} Pong
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            Pong.decode = function decode(reader, length) {
                if (!(reader instanceof $Reader))
                    reader = $Reader.create(reader);
                var end = length === undefined ? reader.len : reader.pos + length, message = new $root.wslobby.Reply.Pong();
                while (reader.pos < end) {
                    var tag = reader.uint32();
                    switch (tag >>> 3) {
                    default:
                        reader.skipType(tag & 7);
                        break;
                    }
                }
                return message;
            };

            /**
             * Decodes a Pong message from the specified reader or buffer, length delimited.
             * @function decodeDelimited
             * @memberof wslobby.Reply.Pong
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @returns {wslobby.Reply.Pong} Pong
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            Pong.decodeDelimited = function decodeDelimited(reader) {
                if (!(reader instanceof $Reader))
                    reader = new $Reader(reader);
                return this.decode(reader, reader.uint32());
            };

            /**
             * Verifies a Pong message.
             * @function verify
             * @memberof wslobby.Reply.Pong
             * @static
             * @param {Object.<string,*>} message Plain object to verify
             * @returns {string|null} `null` if valid, otherwise the reason why it is not
             */
            Pong.verify = function verify(message) {
                if (typeof message !== "object" || message === null)
                    return "object expected";
                return null;
            };

            /**
             * Creates a Pong message from a plain object. Also converts values to their respective internal types.
             * @function fromObject
             * @memberof wslobby.Reply.Pong
             * @static
             * @param {Object.<string,*>} object Plain object
             * @returns {wslobby.Reply.Pong} Pong
             */
            Pong.fromObject = function fromObject(object) {
                if (object instanceof $root.wslobby.Reply.Pong)
                    return object;
                return new $root.wslobby.Reply.Pong();
            };

            /**
             * Creates a plain object from a Pong message. Also converts values to other types if specified.
             * @function toObject
             * @memberof wslobby.Reply.Pong
             * @static
             * @param {wslobby.Reply.Pong} message Pong
             * @param {$protobuf.IConversionOptions} [options] Conversion options
             * @returns {Object.<string,*>} Plain object
             */
            Pong.toObject = function toObject() {
                return {};
            };

            /**
             * Converts this Pong to JSON.
             * @function toJSON
             * @memberof wslobby.Reply.Pong
             * @instance
             * @returns {Object.<string,*>} JSON object
             */
            Pong.prototype.toJSON = function toJSON() {
                return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
            };

            return Pong;
        })();

        Reply.Ack = (function() {

            /**
             * Properties of an Ack.
             * @memberof wslobby.Reply
             * @interface IAck
             */

            /**
             * Constructs a new Ack.
             * @memberof wslobby.Reply
             * @classdesc Represents an Ack.
             * @implements IAck
             * @constructor
             * @param {wslobby.Reply.IAck=} [properties] Properties to set
             */
            function Ack(properties) {
                if (properties)
                    for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                        if (properties[keys[i]] != null)
                            this[keys[i]] = properties[keys[i]];
            }

            /**
             * Creates a new Ack instance using the specified properties.
             * @function create
             * @memberof wslobby.Reply.Ack
             * @static
             * @param {wslobby.Reply.IAck=} [properties] Properties to set
             * @returns {wslobby.Reply.Ack} Ack instance
             */
            Ack.create = function create(properties) {
                return new Ack(properties);
            };

            /**
             * Encodes the specified Ack message. Does not implicitly {@link wslobby.Reply.Ack.verify|verify} messages.
             * @function encode
             * @memberof wslobby.Reply.Ack
             * @static
             * @param {wslobby.Reply.IAck} message Ack message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            Ack.encode = function encode(message, writer) {
                if (!writer)
                    writer = $Writer.create();
                return writer;
            };

            /**
             * Encodes the specified Ack message, length delimited. Does not implicitly {@link wslobby.Reply.Ack.verify|verify} messages.
             * @function encodeDelimited
             * @memberof wslobby.Reply.Ack
             * @static
             * @param {wslobby.Reply.IAck} message Ack message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            Ack.encodeDelimited = function encodeDelimited(message, writer) {
                return this.encode(message, writer).ldelim();
            };

            /**
             * Decodes an Ack message from the specified reader or buffer.
             * @function decode
             * @memberof wslobby.Reply.Ack
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @param {number} [length] Message length if known beforehand
             * @returns {wslobby.Reply.Ack} Ack
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            Ack.decode = function decode(reader, length) {
                if (!(reader instanceof $Reader))
                    reader = $Reader.create(reader);
                var end = length === undefined ? reader.len : reader.pos + length, message = new $root.wslobby.Reply.Ack();
                while (reader.pos < end) {
                    var tag = reader.uint32();
                    switch (tag >>> 3) {
                    default:
                        reader.skipType(tag & 7);
                        break;
                    }
                }
                return message;
            };

            /**
             * Decodes an Ack message from the specified reader or buffer, length delimited.
             * @function decodeDelimited
             * @memberof wslobby.Reply.Ack
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @returns {wslobby.Reply.Ack} Ack
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            Ack.decodeDelimited = function decodeDelimited(reader) {
                if (!(reader instanceof $Reader))
                    reader = new $Reader(reader);
                return this.decode(reader, reader.uint32());
            };

            /**
             * Verifies an Ack message.
             * @function verify
             * @memberof wslobby.Reply.Ack
             * @static
             * @param {Object.<string,*>} message Plain object to verify
             * @returns {string|null} `null` if valid, otherwise the reason why it is not
             */
            Ack.verify = function verify(message) {
                if (typeof message !== "object" || message === null)
                    return "object expected";
                return null;
            };

            /**
             * Creates an Ack message from a plain object. Also converts values to their respective internal types.
             * @function fromObject
             * @memberof wslobby.Reply.Ack
             * @static
             * @param {Object.<string,*>} object Plain object
             * @returns {wslobby.Reply.Ack} Ack
             */
            Ack.fromObject = function fromObject(object) {
                if (object instanceof $root.wslobby.Reply.Ack)
                    return object;
                return new $root.wslobby.Reply.Ack();
            };

            /**
             * Creates a plain object from an Ack message. Also converts values to other types if specified.
             * @function toObject
             * @memberof wslobby.Reply.Ack
             * @static
             * @param {wslobby.Reply.Ack} message Ack
             * @param {$protobuf.IConversionOptions} [options] Conversion options
             * @returns {Object.<string,*>} Plain object
             */
            Ack.toObject = function toObject() {
                return {};
            };

            /**
             * Converts this Ack to JSON.
             * @function toJSON
             * @memberof wslobby.Reply.Ack
             * @instance
             * @returns {Object.<string,*>} JSON object
             */
            Ack.prototype.toJSON = function toJSON() {
                return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
            };

            return Ack;
        })();

        Reply.Error = (function() {

            /**
             * Properties of an Error.
             * @memberof wslobby.Reply
             * @interface IError
             * @property {string|null} [message] Error message
             */

            /**
             * Constructs a new Error.
             * @memberof wslobby.Reply
             * @classdesc Represents an Error.
             * @implements IError
             * @constructor
             * @param {wslobby.Reply.IError=} [properties] Properties to set
             */
            function Error(properties) {
                if (properties)
                    for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                        if (properties[keys[i]] != null)
                            this[keys[i]] = properties[keys[i]];
            }

            /**
             * Error message.
             * @member {string} message
             * @memberof wslobby.Reply.Error
             * @instance
             */
            Error.prototype.message = "";

            /**
             * Creates a new Error instance using the specified properties.
             * @function create
             * @memberof wslobby.Reply.Error
             * @static
             * @param {wslobby.Reply.IError=} [properties] Properties to set
             * @returns {wslobby.Reply.Error} Error instance
             */
            Error.create = function create(properties) {
                return new Error(properties);
            };

            /**
             * Encodes the specified Error message. Does not implicitly {@link wslobby.Reply.Error.verify|verify} messages.
             * @function encode
             * @memberof wslobby.Reply.Error
             * @static
             * @param {wslobby.Reply.IError} message Error message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            Error.encode = function encode(message, writer) {
                if (!writer)
                    writer = $Writer.create();
                if (message.message != null && Object.hasOwnProperty.call(message, "message"))
                    writer.uint32(/* id 1, wireType 2 =*/10).string(message.message);
                return writer;
            };

            /**
             * Encodes the specified Error message, length delimited. Does not implicitly {@link wslobby.Reply.Error.verify|verify} messages.
             * @function encodeDelimited
             * @memberof wslobby.Reply.Error
             * @static
             * @param {wslobby.Reply.IError} message Error message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            Error.encodeDelimited = function encodeDelimited(message, writer) {
                return this.encode(message, writer).ldelim();
            };

            /**
             * Decodes an Error message from the specified reader or buffer.
             * @function decode
             * @memberof wslobby.Reply.Error
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @param {number} [length] Message length if known beforehand
             * @returns {wslobby.Reply.Error} Error
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            Error.decode = function decode(reader, length) {
                if (!(reader instanceof $Reader))
                    reader = $Reader.create(reader);
                var end = length === undefined ? reader.len : reader.pos + length, message = new $root.wslobby.Reply.Error();
                while (reader.pos < end) {
                    var tag = reader.uint32();
                    switch (tag >>> 3) {
                    case 1:
                        message.message = reader.string();
                        break;
                    default:
                        reader.skipType(tag & 7);
                        break;
                    }
                }
                return message;
            };

            /**
             * Decodes an Error message from the specified reader or buffer, length delimited.
             * @function decodeDelimited
             * @memberof wslobby.Reply.Error
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @returns {wslobby.Reply.Error} Error
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            Error.decodeDelimited = function decodeDelimited(reader) {
                if (!(reader instanceof $Reader))
                    reader = new $Reader(reader);
                return this.decode(reader, reader.uint32());
            };

            /**
             * Verifies an Error message.
             * @function verify
             * @memberof wslobby.Reply.Error
             * @static
             * @param {Object.<string,*>} message Plain object to verify
             * @returns {string|null} `null` if valid, otherwise the reason why it is not
             */
            Error.verify = function verify(message) {
                if (typeof message !== "object" || message === null)
                    return "object expected";
                if (message.message != null && message.hasOwnProperty("message"))
                    if (!$util.isString(message.message))
                        return "message: string expected";
                return null;
            };

            /**
             * Creates an Error message from a plain object. Also converts values to their respective internal types.
             * @function fromObject
             * @memberof wslobby.Reply.Error
             * @static
             * @param {Object.<string,*>} object Plain object
             * @returns {wslobby.Reply.Error} Error
             */
            Error.fromObject = function fromObject(object) {
                if (object instanceof $root.wslobby.Reply.Error)
                    return object;
                var message = new $root.wslobby.Reply.Error();
                if (object.message != null)
                    message.message = String(object.message);
                return message;
            };

            /**
             * Creates a plain object from an Error message. Also converts values to other types if specified.
             * @function toObject
             * @memberof wslobby.Reply.Error
             * @static
             * @param {wslobby.Reply.Error} message Error
             * @param {$protobuf.IConversionOptions} [options] Conversion options
             * @returns {Object.<string,*>} Plain object
             */
            Error.toObject = function toObject(message, options) {
                if (!options)
                    options = {};
                var object = {};
                if (options.defaults)
                    object.message = "";
                if (message.message != null && message.hasOwnProperty("message"))
                    object.message = message.message;
                return object;
            };

            /**
             * Converts this Error to JSON.
             * @function toJSON
             * @memberof wslobby.Reply.Error
             * @instance
             * @returns {Object.<string,*>} JSON object
             */
            Error.prototype.toJSON = function toJSON() {
                return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
            };

            return Error;
        })();

        Reply.GuestKnock = (function() {

            /**
             * Properties of a GuestKnock.
             * @memberof wslobby.Reply
             * @interface IGuestKnock
             * @property {string|null} [guestId] GuestKnock guestId
             * @property {string|null} [greeting] GuestKnock greeting
             */

            /**
             * Constructs a new GuestKnock.
             * @memberof wslobby.Reply
             * @classdesc Represents a GuestKnock.
             * @implements IGuestKnock
             * @constructor
             * @param {wslobby.Reply.IGuestKnock=} [properties] Properties to set
             */
            function GuestKnock(properties) {
                if (properties)
                    for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                        if (properties[keys[i]] != null)
                            this[keys[i]] = properties[keys[i]];
            }

            /**
             * GuestKnock guestId.
             * @member {string} guestId
             * @memberof wslobby.Reply.GuestKnock
             * @instance
             */
            GuestKnock.prototype.guestId = "";

            /**
             * GuestKnock greeting.
             * @member {string} greeting
             * @memberof wslobby.Reply.GuestKnock
             * @instance
             */
            GuestKnock.prototype.greeting = "";

            /**
             * Creates a new GuestKnock instance using the specified properties.
             * @function create
             * @memberof wslobby.Reply.GuestKnock
             * @static
             * @param {wslobby.Reply.IGuestKnock=} [properties] Properties to set
             * @returns {wslobby.Reply.GuestKnock} GuestKnock instance
             */
            GuestKnock.create = function create(properties) {
                return new GuestKnock(properties);
            };

            /**
             * Encodes the specified GuestKnock message. Does not implicitly {@link wslobby.Reply.GuestKnock.verify|verify} messages.
             * @function encode
             * @memberof wslobby.Reply.GuestKnock
             * @static
             * @param {wslobby.Reply.IGuestKnock} message GuestKnock message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            GuestKnock.encode = function encode(message, writer) {
                if (!writer)
                    writer = $Writer.create();
                if (message.guestId != null && Object.hasOwnProperty.call(message, "guestId"))
                    writer.uint32(/* id 1, wireType 2 =*/10).string(message.guestId);
                if (message.greeting != null && Object.hasOwnProperty.call(message, "greeting"))
                    writer.uint32(/* id 2, wireType 2 =*/18).string(message.greeting);
                return writer;
            };

            /**
             * Encodes the specified GuestKnock message, length delimited. Does not implicitly {@link wslobby.Reply.GuestKnock.verify|verify} messages.
             * @function encodeDelimited
             * @memberof wslobby.Reply.GuestKnock
             * @static
             * @param {wslobby.Reply.IGuestKnock} message GuestKnock message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            GuestKnock.encodeDelimited = function encodeDelimited(message, writer) {
                return this.encode(message, writer).ldelim();
            };

            /**
             * Decodes a GuestKnock message from the specified reader or buffer.
             * @function decode
             * @memberof wslobby.Reply.GuestKnock
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @param {number} [length] Message length if known beforehand
             * @returns {wslobby.Reply.GuestKnock} GuestKnock
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            GuestKnock.decode = function decode(reader, length) {
                if (!(reader instanceof $Reader))
                    reader = $Reader.create(reader);
                var end = length === undefined ? reader.len : reader.pos + length, message = new $root.wslobby.Reply.GuestKnock();
                while (reader.pos < end) {
                    var tag = reader.uint32();
                    switch (tag >>> 3) {
                    case 1:
                        message.guestId = reader.string();
                        break;
                    case 2:
                        message.greeting = reader.string();
                        break;
                    default:
                        reader.skipType(tag & 7);
                        break;
                    }
                }
                return message;
            };

            /**
             * Decodes a GuestKnock message from the specified reader or buffer, length delimited.
             * @function decodeDelimited
             * @memberof wslobby.Reply.GuestKnock
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @returns {wslobby.Reply.GuestKnock} GuestKnock
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            GuestKnock.decodeDelimited = function decodeDelimited(reader) {
                if (!(reader instanceof $Reader))
                    reader = new $Reader(reader);
                return this.decode(reader, reader.uint32());
            };

            /**
             * Verifies a GuestKnock message.
             * @function verify
             * @memberof wslobby.Reply.GuestKnock
             * @static
             * @param {Object.<string,*>} message Plain object to verify
             * @returns {string|null} `null` if valid, otherwise the reason why it is not
             */
            GuestKnock.verify = function verify(message) {
                if (typeof message !== "object" || message === null)
                    return "object expected";
                if (message.guestId != null && message.hasOwnProperty("guestId"))
                    if (!$util.isString(message.guestId))
                        return "guestId: string expected";
                if (message.greeting != null && message.hasOwnProperty("greeting"))
                    if (!$util.isString(message.greeting))
                        return "greeting: string expected";
                return null;
            };

            /**
             * Creates a GuestKnock message from a plain object. Also converts values to their respective internal types.
             * @function fromObject
             * @memberof wslobby.Reply.GuestKnock
             * @static
             * @param {Object.<string,*>} object Plain object
             * @returns {wslobby.Reply.GuestKnock} GuestKnock
             */
            GuestKnock.fromObject = function fromObject(object) {
                if (object instanceof $root.wslobby.Reply.GuestKnock)
                    return object;
                var message = new $root.wslobby.Reply.GuestKnock();
                if (object.guestId != null)
                    message.guestId = String(object.guestId);
                if (object.greeting != null)
                    message.greeting = String(object.greeting);
                return message;
            };

            /**
             * Creates a plain object from a GuestKnock message. Also converts values to other types if specified.
             * @function toObject
             * @memberof wslobby.Reply.GuestKnock
             * @static
             * @param {wslobby.Reply.GuestKnock} message GuestKnock
             * @param {$protobuf.IConversionOptions} [options] Conversion options
             * @returns {Object.<string,*>} Plain object
             */
            GuestKnock.toObject = function toObject(message, options) {
                if (!options)
                    options = {};
                var object = {};
                if (options.defaults) {
                    object.guestId = "";
                    object.greeting = "";
                }
                if (message.guestId != null && message.hasOwnProperty("guestId"))
                    object.guestId = message.guestId;
                if (message.greeting != null && message.hasOwnProperty("greeting"))
                    object.greeting = message.greeting;
                return object;
            };

            /**
             * Converts this GuestKnock to JSON.
             * @function toJSON
             * @memberof wslobby.Reply.GuestKnock
             * @instance
             * @returns {Object.<string,*>} JSON object
             */
            GuestKnock.prototype.toJSON = function toJSON() {
                return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
            };

            return GuestKnock;
        })();

        Reply.GuestLeft = (function() {

            /**
             * Properties of a GuestLeft.
             * @memberof wslobby.Reply
             * @interface IGuestLeft
             * @property {string|null} [guestId] GuestLeft guestId
             * @property {string|null} [goodbye] GuestLeft goodbye
             */

            /**
             * Constructs a new GuestLeft.
             * @memberof wslobby.Reply
             * @classdesc Represents a GuestLeft.
             * @implements IGuestLeft
             * @constructor
             * @param {wslobby.Reply.IGuestLeft=} [properties] Properties to set
             */
            function GuestLeft(properties) {
                if (properties)
                    for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                        if (properties[keys[i]] != null)
                            this[keys[i]] = properties[keys[i]];
            }

            /**
             * GuestLeft guestId.
             * @member {string} guestId
             * @memberof wslobby.Reply.GuestLeft
             * @instance
             */
            GuestLeft.prototype.guestId = "";

            /**
             * GuestLeft goodbye.
             * @member {string|null|undefined} goodbye
             * @memberof wslobby.Reply.GuestLeft
             * @instance
             */
            GuestLeft.prototype.goodbye = null;

            // OneOf field names bound to virtual getters and setters
            var $oneOfFields;

            /**
             * GuestLeft _goodbye.
             * @member {"goodbye"|undefined} _goodbye
             * @memberof wslobby.Reply.GuestLeft
             * @instance
             */
            Object.defineProperty(GuestLeft.prototype, "_goodbye", {
                get: $util.oneOfGetter($oneOfFields = ["goodbye"]),
                set: $util.oneOfSetter($oneOfFields)
            });

            /**
             * Creates a new GuestLeft instance using the specified properties.
             * @function create
             * @memberof wslobby.Reply.GuestLeft
             * @static
             * @param {wslobby.Reply.IGuestLeft=} [properties] Properties to set
             * @returns {wslobby.Reply.GuestLeft} GuestLeft instance
             */
            GuestLeft.create = function create(properties) {
                return new GuestLeft(properties);
            };

            /**
             * Encodes the specified GuestLeft message. Does not implicitly {@link wslobby.Reply.GuestLeft.verify|verify} messages.
             * @function encode
             * @memberof wslobby.Reply.GuestLeft
             * @static
             * @param {wslobby.Reply.IGuestLeft} message GuestLeft message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            GuestLeft.encode = function encode(message, writer) {
                if (!writer)
                    writer = $Writer.create();
                if (message.guestId != null && Object.hasOwnProperty.call(message, "guestId"))
                    writer.uint32(/* id 1, wireType 2 =*/10).string(message.guestId);
                if (message.goodbye != null && Object.hasOwnProperty.call(message, "goodbye"))
                    writer.uint32(/* id 2, wireType 2 =*/18).string(message.goodbye);
                return writer;
            };

            /**
             * Encodes the specified GuestLeft message, length delimited. Does not implicitly {@link wslobby.Reply.GuestLeft.verify|verify} messages.
             * @function encodeDelimited
             * @memberof wslobby.Reply.GuestLeft
             * @static
             * @param {wslobby.Reply.IGuestLeft} message GuestLeft message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            GuestLeft.encodeDelimited = function encodeDelimited(message, writer) {
                return this.encode(message, writer).ldelim();
            };

            /**
             * Decodes a GuestLeft message from the specified reader or buffer.
             * @function decode
             * @memberof wslobby.Reply.GuestLeft
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @param {number} [length] Message length if known beforehand
             * @returns {wslobby.Reply.GuestLeft} GuestLeft
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            GuestLeft.decode = function decode(reader, length) {
                if (!(reader instanceof $Reader))
                    reader = $Reader.create(reader);
                var end = length === undefined ? reader.len : reader.pos + length, message = new $root.wslobby.Reply.GuestLeft();
                while (reader.pos < end) {
                    var tag = reader.uint32();
                    switch (tag >>> 3) {
                    case 1:
                        message.guestId = reader.string();
                        break;
                    case 2:
                        message.goodbye = reader.string();
                        break;
                    default:
                        reader.skipType(tag & 7);
                        break;
                    }
                }
                return message;
            };

            /**
             * Decodes a GuestLeft message from the specified reader or buffer, length delimited.
             * @function decodeDelimited
             * @memberof wslobby.Reply.GuestLeft
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @returns {wslobby.Reply.GuestLeft} GuestLeft
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            GuestLeft.decodeDelimited = function decodeDelimited(reader) {
                if (!(reader instanceof $Reader))
                    reader = new $Reader(reader);
                return this.decode(reader, reader.uint32());
            };

            /**
             * Verifies a GuestLeft message.
             * @function verify
             * @memberof wslobby.Reply.GuestLeft
             * @static
             * @param {Object.<string,*>} message Plain object to verify
             * @returns {string|null} `null` if valid, otherwise the reason why it is not
             */
            GuestLeft.verify = function verify(message) {
                if (typeof message !== "object" || message === null)
                    return "object expected";
                var properties = {};
                if (message.guestId != null && message.hasOwnProperty("guestId"))
                    if (!$util.isString(message.guestId))
                        return "guestId: string expected";
                if (message.goodbye != null && message.hasOwnProperty("goodbye")) {
                    properties._goodbye = 1;
                    if (!$util.isString(message.goodbye))
                        return "goodbye: string expected";
                }
                return null;
            };

            /**
             * Creates a GuestLeft message from a plain object. Also converts values to their respective internal types.
             * @function fromObject
             * @memberof wslobby.Reply.GuestLeft
             * @static
             * @param {Object.<string,*>} object Plain object
             * @returns {wslobby.Reply.GuestLeft} GuestLeft
             */
            GuestLeft.fromObject = function fromObject(object) {
                if (object instanceof $root.wslobby.Reply.GuestLeft)
                    return object;
                var message = new $root.wslobby.Reply.GuestLeft();
                if (object.guestId != null)
                    message.guestId = String(object.guestId);
                if (object.goodbye != null)
                    message.goodbye = String(object.goodbye);
                return message;
            };

            /**
             * Creates a plain object from a GuestLeft message. Also converts values to other types if specified.
             * @function toObject
             * @memberof wslobby.Reply.GuestLeft
             * @static
             * @param {wslobby.Reply.GuestLeft} message GuestLeft
             * @param {$protobuf.IConversionOptions} [options] Conversion options
             * @returns {Object.<string,*>} Plain object
             */
            GuestLeft.toObject = function toObject(message, options) {
                if (!options)
                    options = {};
                var object = {};
                if (options.defaults)
                    object.guestId = "";
                if (message.guestId != null && message.hasOwnProperty("guestId"))
                    object.guestId = message.guestId;
                if (message.goodbye != null && message.hasOwnProperty("goodbye")) {
                    object.goodbye = message.goodbye;
                    if (options.oneofs)
                        object._goodbye = "goodbye";
                }
                return object;
            };

            /**
             * Converts this GuestLeft to JSON.
             * @function toJSON
             * @memberof wslobby.Reply.GuestLeft
             * @instance
             * @returns {Object.<string,*>} JSON object
             */
            GuestLeft.prototype.toJSON = function toJSON() {
                return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
            };

            return GuestLeft;
        })();

        Reply.EnteredRoom = (function() {

            /**
             * Properties of an EnteredRoom.
             * @memberof wslobby.Reply
             * @interface IEnteredRoom
             * @property {string|null} [roomId] EnteredRoom roomId
             * @property {string|null} [hostId] EnteredRoom hostId
             */

            /**
             * Constructs a new EnteredRoom.
             * @memberof wslobby.Reply
             * @classdesc Represents an EnteredRoom.
             * @implements IEnteredRoom
             * @constructor
             * @param {wslobby.Reply.IEnteredRoom=} [properties] Properties to set
             */
            function EnteredRoom(properties) {
                if (properties)
                    for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                        if (properties[keys[i]] != null)
                            this[keys[i]] = properties[keys[i]];
            }

            /**
             * EnteredRoom roomId.
             * @member {string} roomId
             * @memberof wslobby.Reply.EnteredRoom
             * @instance
             */
            EnteredRoom.prototype.roomId = "";

            /**
             * EnteredRoom hostId.
             * @member {string} hostId
             * @memberof wslobby.Reply.EnteredRoom
             * @instance
             */
            EnteredRoom.prototype.hostId = "";

            /**
             * Creates a new EnteredRoom instance using the specified properties.
             * @function create
             * @memberof wslobby.Reply.EnteredRoom
             * @static
             * @param {wslobby.Reply.IEnteredRoom=} [properties] Properties to set
             * @returns {wslobby.Reply.EnteredRoom} EnteredRoom instance
             */
            EnteredRoom.create = function create(properties) {
                return new EnteredRoom(properties);
            };

            /**
             * Encodes the specified EnteredRoom message. Does not implicitly {@link wslobby.Reply.EnteredRoom.verify|verify} messages.
             * @function encode
             * @memberof wslobby.Reply.EnteredRoom
             * @static
             * @param {wslobby.Reply.IEnteredRoom} message EnteredRoom message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            EnteredRoom.encode = function encode(message, writer) {
                if (!writer)
                    writer = $Writer.create();
                if (message.roomId != null && Object.hasOwnProperty.call(message, "roomId"))
                    writer.uint32(/* id 1, wireType 2 =*/10).string(message.roomId);
                if (message.hostId != null && Object.hasOwnProperty.call(message, "hostId"))
                    writer.uint32(/* id 2, wireType 2 =*/18).string(message.hostId);
                return writer;
            };

            /**
             * Encodes the specified EnteredRoom message, length delimited. Does not implicitly {@link wslobby.Reply.EnteredRoom.verify|verify} messages.
             * @function encodeDelimited
             * @memberof wslobby.Reply.EnteredRoom
             * @static
             * @param {wslobby.Reply.IEnteredRoom} message EnteredRoom message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            EnteredRoom.encodeDelimited = function encodeDelimited(message, writer) {
                return this.encode(message, writer).ldelim();
            };

            /**
             * Decodes an EnteredRoom message from the specified reader or buffer.
             * @function decode
             * @memberof wslobby.Reply.EnteredRoom
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @param {number} [length] Message length if known beforehand
             * @returns {wslobby.Reply.EnteredRoom} EnteredRoom
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            EnteredRoom.decode = function decode(reader, length) {
                if (!(reader instanceof $Reader))
                    reader = $Reader.create(reader);
                var end = length === undefined ? reader.len : reader.pos + length, message = new $root.wslobby.Reply.EnteredRoom();
                while (reader.pos < end) {
                    var tag = reader.uint32();
                    switch (tag >>> 3) {
                    case 1:
                        message.roomId = reader.string();
                        break;
                    case 2:
                        message.hostId = reader.string();
                        break;
                    default:
                        reader.skipType(tag & 7);
                        break;
                    }
                }
                return message;
            };

            /**
             * Decodes an EnteredRoom message from the specified reader or buffer, length delimited.
             * @function decodeDelimited
             * @memberof wslobby.Reply.EnteredRoom
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @returns {wslobby.Reply.EnteredRoom} EnteredRoom
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            EnteredRoom.decodeDelimited = function decodeDelimited(reader) {
                if (!(reader instanceof $Reader))
                    reader = new $Reader(reader);
                return this.decode(reader, reader.uint32());
            };

            /**
             * Verifies an EnteredRoom message.
             * @function verify
             * @memberof wslobby.Reply.EnteredRoom
             * @static
             * @param {Object.<string,*>} message Plain object to verify
             * @returns {string|null} `null` if valid, otherwise the reason why it is not
             */
            EnteredRoom.verify = function verify(message) {
                if (typeof message !== "object" || message === null)
                    return "object expected";
                if (message.roomId != null && message.hasOwnProperty("roomId"))
                    if (!$util.isString(message.roomId))
                        return "roomId: string expected";
                if (message.hostId != null && message.hasOwnProperty("hostId"))
                    if (!$util.isString(message.hostId))
                        return "hostId: string expected";
                return null;
            };

            /**
             * Creates an EnteredRoom message from a plain object. Also converts values to their respective internal types.
             * @function fromObject
             * @memberof wslobby.Reply.EnteredRoom
             * @static
             * @param {Object.<string,*>} object Plain object
             * @returns {wslobby.Reply.EnteredRoom} EnteredRoom
             */
            EnteredRoom.fromObject = function fromObject(object) {
                if (object instanceof $root.wslobby.Reply.EnteredRoom)
                    return object;
                var message = new $root.wslobby.Reply.EnteredRoom();
                if (object.roomId != null)
                    message.roomId = String(object.roomId);
                if (object.hostId != null)
                    message.hostId = String(object.hostId);
                return message;
            };

            /**
             * Creates a plain object from an EnteredRoom message. Also converts values to other types if specified.
             * @function toObject
             * @memberof wslobby.Reply.EnteredRoom
             * @static
             * @param {wslobby.Reply.EnteredRoom} message EnteredRoom
             * @param {$protobuf.IConversionOptions} [options] Conversion options
             * @returns {Object.<string,*>} Plain object
             */
            EnteredRoom.toObject = function toObject(message, options) {
                if (!options)
                    options = {};
                var object = {};
                if (options.defaults) {
                    object.roomId = "";
                    object.hostId = "";
                }
                if (message.roomId != null && message.hasOwnProperty("roomId"))
                    object.roomId = message.roomId;
                if (message.hostId != null && message.hasOwnProperty("hostId"))
                    object.hostId = message.hostId;
                return object;
            };

            /**
             * Converts this EnteredRoom to JSON.
             * @function toJSON
             * @memberof wslobby.Reply.EnteredRoom
             * @instance
             * @returns {Object.<string,*>} JSON object
             */
            EnteredRoom.prototype.toJSON = function toJSON() {
                return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
            };

            return EnteredRoom;
        })();

        Reply.Data = (function() {

            /**
             * Properties of a Data.
             * @memberof wslobby.Reply
             * @interface IData
             * @property {string|null} [senderId] Data senderId
             * @property {Uint8Array|null} [data] Data data
             */

            /**
             * Constructs a new Data.
             * @memberof wslobby.Reply
             * @classdesc Represents a Data.
             * @implements IData
             * @constructor
             * @param {wslobby.Reply.IData=} [properties] Properties to set
             */
            function Data(properties) {
                if (properties)
                    for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                        if (properties[keys[i]] != null)
                            this[keys[i]] = properties[keys[i]];
            }

            /**
             * Data senderId.
             * @member {string} senderId
             * @memberof wslobby.Reply.Data
             * @instance
             */
            Data.prototype.senderId = "";

            /**
             * Data data.
             * @member {Uint8Array} data
             * @memberof wslobby.Reply.Data
             * @instance
             */
            Data.prototype.data = $util.newBuffer([]);

            /**
             * Creates a new Data instance using the specified properties.
             * @function create
             * @memberof wslobby.Reply.Data
             * @static
             * @param {wslobby.Reply.IData=} [properties] Properties to set
             * @returns {wslobby.Reply.Data} Data instance
             */
            Data.create = function create(properties) {
                return new Data(properties);
            };

            /**
             * Encodes the specified Data message. Does not implicitly {@link wslobby.Reply.Data.verify|verify} messages.
             * @function encode
             * @memberof wslobby.Reply.Data
             * @static
             * @param {wslobby.Reply.IData} message Data message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            Data.encode = function encode(message, writer) {
                if (!writer)
                    writer = $Writer.create();
                if (message.senderId != null && Object.hasOwnProperty.call(message, "senderId"))
                    writer.uint32(/* id 1, wireType 2 =*/10).string(message.senderId);
                if (message.data != null && Object.hasOwnProperty.call(message, "data"))
                    writer.uint32(/* id 2, wireType 2 =*/18).bytes(message.data);
                return writer;
            };

            /**
             * Encodes the specified Data message, length delimited. Does not implicitly {@link wslobby.Reply.Data.verify|verify} messages.
             * @function encodeDelimited
             * @memberof wslobby.Reply.Data
             * @static
             * @param {wslobby.Reply.IData} message Data message or plain object to encode
             * @param {$protobuf.Writer} [writer] Writer to encode to
             * @returns {$protobuf.Writer} Writer
             */
            Data.encodeDelimited = function encodeDelimited(message, writer) {
                return this.encode(message, writer).ldelim();
            };

            /**
             * Decodes a Data message from the specified reader or buffer.
             * @function decode
             * @memberof wslobby.Reply.Data
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @param {number} [length] Message length if known beforehand
             * @returns {wslobby.Reply.Data} Data
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            Data.decode = function decode(reader, length) {
                if (!(reader instanceof $Reader))
                    reader = $Reader.create(reader);
                var end = length === undefined ? reader.len : reader.pos + length, message = new $root.wslobby.Reply.Data();
                while (reader.pos < end) {
                    var tag = reader.uint32();
                    switch (tag >>> 3) {
                    case 1:
                        message.senderId = reader.string();
                        break;
                    case 2:
                        message.data = reader.bytes();
                        break;
                    default:
                        reader.skipType(tag & 7);
                        break;
                    }
                }
                return message;
            };

            /**
             * Decodes a Data message from the specified reader or buffer, length delimited.
             * @function decodeDelimited
             * @memberof wslobby.Reply.Data
             * @static
             * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
             * @returns {wslobby.Reply.Data} Data
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            Data.decodeDelimited = function decodeDelimited(reader) {
                if (!(reader instanceof $Reader))
                    reader = new $Reader(reader);
                return this.decode(reader, reader.uint32());
            };

            /**
             * Verifies a Data message.
             * @function verify
             * @memberof wslobby.Reply.Data
             * @static
             * @param {Object.<string,*>} message Plain object to verify
             * @returns {string|null} `null` if valid, otherwise the reason why it is not
             */
            Data.verify = function verify(message) {
                if (typeof message !== "object" || message === null)
                    return "object expected";
                if (message.senderId != null && message.hasOwnProperty("senderId"))
                    if (!$util.isString(message.senderId))
                        return "senderId: string expected";
                if (message.data != null && message.hasOwnProperty("data"))
                    if (!(message.data && typeof message.data.length === "number" || $util.isString(message.data)))
                        return "data: buffer expected";
                return null;
            };

            /**
             * Creates a Data message from a plain object. Also converts values to their respective internal types.
             * @function fromObject
             * @memberof wslobby.Reply.Data
             * @static
             * @param {Object.<string,*>} object Plain object
             * @returns {wslobby.Reply.Data} Data
             */
            Data.fromObject = function fromObject(object) {
                if (object instanceof $root.wslobby.Reply.Data)
                    return object;
                var message = new $root.wslobby.Reply.Data();
                if (object.senderId != null)
                    message.senderId = String(object.senderId);
                if (object.data != null)
                    if (typeof object.data === "string")
                        $util.base64.decode(object.data, message.data = $util.newBuffer($util.base64.length(object.data)), 0);
                    else if (object.data.length)
                        message.data = object.data;
                return message;
            };

            /**
             * Creates a plain object from a Data message. Also converts values to other types if specified.
             * @function toObject
             * @memberof wslobby.Reply.Data
             * @static
             * @param {wslobby.Reply.Data} message Data
             * @param {$protobuf.IConversionOptions} [options] Conversion options
             * @returns {Object.<string,*>} Plain object
             */
            Data.toObject = function toObject(message, options) {
                if (!options)
                    options = {};
                var object = {};
                if (options.defaults) {
                    object.senderId = "";
                    if (options.bytes === String)
                        object.data = "";
                    else {
                        object.data = [];
                        if (options.bytes !== Array)
                            object.data = $util.newBuffer(object.data);
                    }
                }
                if (message.senderId != null && message.hasOwnProperty("senderId"))
                    object.senderId = message.senderId;
                if (message.data != null && message.hasOwnProperty("data"))
                    object.data = options.bytes === String ? $util.base64.encode(message.data, 0, message.data.length) : options.bytes === Array ? Array.prototype.slice.call(message.data) : message.data;
                return object;
            };

            /**
             * Converts this Data to JSON.
             * @function toJSON
             * @memberof wslobby.Reply.Data
             * @instance
             * @returns {Object.<string,*>} JSON object
             */
            Data.prototype.toJSON = function toJSON() {
                return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
            };

            return Data;
        })();

        return Reply;
    })();

    return wslobby;
})();

module.exports = $root;

},{"protobufjs/minimal":8}]},{},[19])(19)
});
