@file:JsModule("import/wslobby.js")
@file:JsNonModule()
@file:JsQualifier("wslobby")
@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

/*
 * This file was autogenerated by Dukat and fine-tuned manually.
 */
package wslobby

import org.khronos.webgl.Uint8Array
import kotlin.js.Json

//external object wslobby {

    external enum class Role {
        HOST /* = 0 */,
        GUEST /* = 1 */
    }

    external interface ICommand {
        var id: Int?
            get() = definedExternally
            set(value) = definedExternally
        var ping: Command.IPing?
            get() = definedExternally
            set(value) = definedExternally
        var identify: Command.IIdentify?
            get() = definedExternally
            set(value) = definedExternally
        var claimRoom: Command.IClaimRoom?
            get() = definedExternally
            set(value) = definedExternally
        var joinRoom: Command.IJoinRoom?
            get() = definedExternally
            set(value) = definedExternally
        var leave: Command.ILeave?
            get() = definedExternally
            set(value) = definedExternally
        var kickGuest: Command.IKickGuest?
            get() = definedExternally
            set(value) = definedExternally
        var allowGuest: Command.IAllowGuest?
            get() = definedExternally
            set(value) = definedExternally
        var broadcast: Command.IBroadcast?
            get() = definedExternally
            set(value) = definedExternally
        var unicast: Command.IUnicast?
            get() = definedExternally
            set(value) = definedExternally
    }

external open class Command(properties: ICommand = definedExternally) : ICommand {
        override var ping: IPing?
        override var identify: IIdentify?
        override var claimRoom: IClaimRoom?
        override var joinRoom: IJoinRoom?
        override var leave: ILeave?
        override var kickGuest: IKickGuest?
        override var allowGuest: IAllowGuest?
        override var broadcast: IBroadcast?
        override var unicast: IUnicast?
        open var payload: String /* "ping" | "identify" | "claimRoom" | "joinRoom" | "leave" | "kickGuest" | "allowGuest" | "broadcast" | "unicast" */
        open fun toJSON(): Json
        interface IPing
        open class Ping(properties: IPing = definedExternally) : IPing {
            open fun toJSON(): Json

            companion object : ProtobufJsMessageClass<IPing, Ping> {
                override fun create(properties: IPing): Ping
                override fun encode(message: IPing, writer: ProtobufWriter): ProtobufWriter
                override fun encodeDelimited(message: IPing, writer: ProtobufWriter): ProtobufWriter
                override fun decode(reader: ProtobufReader, length: Int): Ping
                override fun decode(reader: Uint8Array, length: Int): Ping
                override fun decodeDelimited(reader: ProtobufReader): Ping
                override fun decodeDelimited(reader: Uint8Array): Ping
                override fun verify(message: Json): String?
                override fun verify(message: IPing): String?
                override fun fromObject(obj: Json): Ping
                override fun toObject(message: Ping, options: ProtobufIConversionOptions): Json
            }
        }

        interface IIdentify {
            var identity: String?
                get() = definedExternally
                set(value) = definedExternally
            var token: String?
                get() = definedExternally
                set(value) = definedExternally
            var role: Role?
                get() = definedExternally
                set(value) = definedExternally
        }

        open class Identify(properties: IIdentify = definedExternally) : IIdentify {
            open fun toJSON(): Json

            companion object : ProtobufJsMessageClass<IIdentify, Identify> {
                override fun create(properties: IIdentify): Identify
                override fun encode(message: IIdentify, writer: ProtobufWriter): ProtobufWriter
                override fun encodeDelimited(message: IIdentify, writer: ProtobufWriter): ProtobufWriter
                override fun decode(reader: ProtobufReader, length: Int): Identify
                override fun decode(reader: Uint8Array, length: Int): Identify
                override fun decodeDelimited(reader: ProtobufReader): Identify
                override fun decodeDelimited(reader: Uint8Array): Identify
                override fun verify(message: Json): String?
                override fun verify(message: IIdentify): String?
                override fun fromObject(obj: Json): Identify
                override fun toObject(message: Identify, options: ProtobufIConversionOptions): Json
            }
        }

        interface IClaimRoom {
            var roomId: String?
                get() = definedExternally
                set(value) = definedExternally
        }

        open class ClaimRoom(properties: IClaimRoom = definedExternally) : IClaimRoom {
            open fun toJSON(): Json

            companion object : ProtobufJsMessageClass<IClaimRoom, ClaimRoom> {
                override fun create(properties: IClaimRoom): ClaimRoom
                override fun encode(message: IClaimRoom, writer: ProtobufWriter): ProtobufWriter
                override fun encodeDelimited(message: IClaimRoom, writer: ProtobufWriter): ProtobufWriter
                override fun decode(reader: ProtobufReader, length: Int): ClaimRoom
                override fun decode(reader: Uint8Array, length: Int): ClaimRoom
                override fun decodeDelimited(reader: ProtobufReader): ClaimRoom
                override fun decodeDelimited(reader: Uint8Array): ClaimRoom
                override fun verify(message: Json): String?
                override fun verify(message: IClaimRoom): String?
                override fun fromObject(obj: Json): ClaimRoom
                override fun toObject(message: ClaimRoom, options: ProtobufIConversionOptions): Json
            }
        }

        interface IJoinRoom {
            var roomId: String?
                get() = definedExternally
                set(value) = definedExternally
            var greeting: String?
                get() = definedExternally
                set(value) = definedExternally
        }

        open class JoinRoom(properties: IJoinRoom = definedExternally) : IJoinRoom {
            open fun toJSON(): Json

            companion object : ProtobufJsMessageClass<IJoinRoom, JoinRoom> {
                override fun create(properties: IJoinRoom): JoinRoom
                override fun encode(message: IJoinRoom, writer: ProtobufWriter): ProtobufWriter
                override fun encodeDelimited(message: IJoinRoom, writer: ProtobufWriter): ProtobufWriter
                override fun decode(reader: ProtobufReader, length: Int): JoinRoom
                override fun decode(reader: Uint8Array, length: Int): JoinRoom
                override fun decodeDelimited(reader: ProtobufReader): JoinRoom
                override fun decodeDelimited(reader: Uint8Array): JoinRoom
                override fun verify(message: Json): String?
                override fun verify(message: IJoinRoom): String?
                override fun fromObject(obj: Json): JoinRoom
                override fun toObject(message: JoinRoom, options: ProtobufIConversionOptions): Json
            }
        }

        interface ILeave {
            var goodbye: String?
                get() = definedExternally
                set(value) = definedExternally
        }

        open class Leave(properties: ILeave = definedExternally) : ILeave {
            open fun toJSON(): Json

            companion object : ProtobufJsMessageClass<ILeave, Leave> {
                override fun create(properties: ILeave): Leave
                override fun encode(message: ILeave, writer: ProtobufWriter): ProtobufWriter
                override fun encodeDelimited(message: ILeave, writer: ProtobufWriter): ProtobufWriter
                override fun decode(reader: ProtobufReader, length: Int): Leave
                override fun decode(reader: Uint8Array, length: Int): Leave
                override fun decodeDelimited(reader: ProtobufReader): Leave
                override fun decodeDelimited(reader: Uint8Array): Leave
                override fun verify(message: Json): String?
                override fun verify(message: ILeave): String?
                override fun fromObject(obj: Json): Leave
                override fun toObject(message: Leave, options: ProtobufIConversionOptions): Json
            }
        }

        interface IKickGuest {
            var guestId: String?
                get() = definedExternally
                set(value) = definedExternally
            var message: String?
                get() = definedExternally
                set(value) = definedExternally
        }

        open class KickGuest(properties: IKickGuest = definedExternally) : IKickGuest {
            open fun toJSON(): Json

            companion object : ProtobufJsMessageClass<IKickGuest, KickGuest> {
                override fun create(properties: IKickGuest): KickGuest
                override fun encode(message: IKickGuest, writer: ProtobufWriter): ProtobufWriter
                override fun encodeDelimited(message: IKickGuest, writer: ProtobufWriter): ProtobufWriter
                override fun decode(reader: ProtobufReader, length: Int): KickGuest
                override fun decode(reader: Uint8Array, length: Int): KickGuest
                override fun decodeDelimited(reader: ProtobufReader): KickGuest
                override fun decodeDelimited(reader: Uint8Array): KickGuest
                override fun verify(message: Json): String?
                override fun verify(message: IKickGuest): String?
                override fun fromObject(obj: Json): KickGuest
                override fun toObject(message: KickGuest, options: ProtobufIConversionOptions): Json
            }
        }

        interface IAllowGuest {
            var guestId: String?
                get() = definedExternally
                set(value) = definedExternally
        }

        open class AllowGuest(properties: IAllowGuest = definedExternally) : IAllowGuest {
            open fun toJSON(): Json

            companion object : ProtobufJsMessageClass<IAllowGuest, AllowGuest> {
                override fun create(properties: IAllowGuest): AllowGuest
                override fun encode(message: IAllowGuest, writer: ProtobufWriter): ProtobufWriter
                override fun encodeDelimited(message: IAllowGuest, writer: ProtobufWriter): ProtobufWriter
                override fun decode(reader: ProtobufReader, length: Int): AllowGuest
                override fun decode(reader: Uint8Array, length: Int): AllowGuest
                override fun decodeDelimited(reader: ProtobufReader): AllowGuest
                override fun decodeDelimited(reader: Uint8Array): AllowGuest
                override fun verify(message: Json): String?
                override fun verify(message: IAllowGuest): String?
                override fun fromObject(obj: Json): AllowGuest
                override fun toObject(message: AllowGuest, options: ProtobufIConversionOptions): Json
            }
        }

        interface IBroadcast {
            var data: Uint8Array?
                get() = definedExternally
                set(value) = definedExternally
        }

        open class Broadcast(properties: IBroadcast = definedExternally) : IBroadcast {
            open fun toJSON(): Json

            companion object : ProtobufJsMessageClass<IBroadcast, Broadcast> {
                override fun create(properties: IBroadcast): Broadcast
                override fun encode(message: IBroadcast, writer: ProtobufWriter): ProtobufWriter
                override fun encodeDelimited(message: IBroadcast, writer: ProtobufWriter): ProtobufWriter
                override fun decode(reader: ProtobufReader, length: Int): Broadcast
                override fun decode(reader: Uint8Array, length: Int): Broadcast
                override fun decodeDelimited(reader: ProtobufReader): Broadcast
                override fun decodeDelimited(reader: Uint8Array): Broadcast
                override fun verify(message: Json): String?
                override fun verify(message: IBroadcast): String?
                override fun fromObject(obj: Json): Broadcast
                override fun toObject(message: Broadcast, options: ProtobufIConversionOptions): Json
            }
        }

        interface IUnicast {
            var guestId: String?
                get() = definedExternally
                set(value) = definedExternally
            var data: Uint8Array?
                get() = definedExternally
                set(value) = definedExternally
        }

        open class Unicast(properties: IUnicast = definedExternally) : IUnicast {
            open fun toJSON(): Json

            companion object : ProtobufJsMessageClass<IUnicast, Unicast> {
                override fun create(properties: IUnicast): Unicast
                override fun encode(message: IUnicast, writer: ProtobufWriter): ProtobufWriter
                override fun encodeDelimited(message: IUnicast, writer: ProtobufWriter): ProtobufWriter
                override fun decode(reader: ProtobufReader, length: Int): Unicast
                override fun decode(reader: Uint8Array, length: Int): Unicast
                override fun decodeDelimited(reader: ProtobufReader): Unicast
                override fun decodeDelimited(reader: Uint8Array): Unicast
                override fun verify(message: Json): String?
                override fun verify(message: IUnicast): String?
                override fun fromObject(obj: Json): Unicast
                override fun toObject(message: Unicast, options: ProtobufIConversionOptions): Json
            }
        }

        companion object : ProtobufJsMessageClass<ICommand, Command> {
            override fun create(properties: ICommand): Command
            override fun encode(message: ICommand, writer: ProtobufWriter): ProtobufWriter
            override fun encodeDelimited(message: ICommand, writer: ProtobufWriter): ProtobufWriter
            override fun decode(reader: ProtobufReader, length: Int): Command
            override fun decode(reader: Uint8Array, length: Int): Command
            override fun decodeDelimited(reader: ProtobufReader): Command
            override fun decodeDelimited(reader: Uint8Array): Command
            override fun verify(message: Json): String?
            override fun verify(message: ICommand): String?
            override fun fromObject(obj: Json): Command
            override fun toObject(message: Command, options: ProtobufIConversionOptions): Json
        }
    }

external interface IReply {
        var id: Int?
            get() = definedExternally
            set(value) = definedExternally
        var pong: Reply.IPong?
            get() = definedExternally
            set(value) = definedExternally
        var ack: Reply.IAck?
            get() = definedExternally
            set(value) = definedExternally
        var error: Reply.IError?
            get() = definedExternally
            set(value) = definedExternally
        var guestKnock: Reply.IGuestKnock?
            get() = definedExternally
            set(value) = definedExternally
        var guestLeft: Reply.IGuestLeft?
            get() = definedExternally
            set(value) = definedExternally
        var enteredRoom: Reply.IEnteredRoom?
            get() = definedExternally
            set(value) = definedExternally
        var data: Reply.IData?
            get() = definedExternally
            set(value) = definedExternally
    }

external open class Reply(properties: IReply = definedExternally) : IReply {
        override var pong: IPong?
        override var ack: IAck?
        override var error: IError?
        override var guestKnock: IGuestKnock?
        override var guestLeft: IGuestLeft?
        override var enteredRoom: IEnteredRoom?
        override var data: IData?
        open var payload: String /* "pong" | "ack" | "error" | "guestKnock" | "guestLeft" | "enteredRoom" | "data" */
        open fun toJSON(): Json
        interface IPong
        open class Pong(properties: IPong = definedExternally) : IPong {
            open fun toJSON(): Json

            companion object {
                fun create(properties: IPong = definedExternally): Pong
                fun encode(message: IPong, writer: ProtobufWriter = definedExternally): ProtobufWriter
                fun encodeDelimited(message: IPong, writer: ProtobufWriter = definedExternally): ProtobufWriter
                fun decode(reader: ProtobufReader, length: Int = definedExternally): Pong
                fun decode(reader: Uint8Array, length: Int = definedExternally): Pong
                fun decodeDelimited(reader: ProtobufReader): Pong
                fun decodeDelimited(reader: Uint8Array): Pong
                fun verify(message: Json): String?
                fun fromObject(obj: Json): Pong
                fun toObject(message: Pong, options: ProtobufIConversionOptions = definedExternally): Json
            }
        }

        interface IAck
        open class Ack(properties: IAck = definedExternally) : IAck {
            open fun toJSON(): Json

            companion object {
                fun create(properties: IAck = definedExternally): Ack
                fun encode(message: IAck, writer: ProtobufWriter = definedExternally): ProtobufWriter
                fun encodeDelimited(message: IAck, writer: ProtobufWriter = definedExternally): ProtobufWriter
                fun decode(reader: ProtobufReader, length: Int = definedExternally): Ack
                fun decode(reader: Uint8Array, length: Int = definedExternally): Ack
                fun decodeDelimited(reader: ProtobufReader): Ack
                fun decodeDelimited(reader: Uint8Array): Ack
                fun verify(message: Json): String?
                fun fromObject(obj: Json): Ack
                fun toObject(message: Ack, options: ProtobufIConversionOptions = definedExternally): Json
            }
        }

        interface IError {
            var message: String?
                get() = definedExternally
                set(value) = definedExternally
        }

        open class Error(properties: IError = definedExternally) : IError {
            open fun toJSON(): Json

            companion object {
                fun create(properties: IError = definedExternally): Error
                fun encode(message: IError, writer: ProtobufWriter = definedExternally): ProtobufWriter
                fun encodeDelimited(message: IError, writer: ProtobufWriter = definedExternally): ProtobufWriter
                fun decode(reader: ProtobufReader, length: Int = definedExternally): Error
                fun decode(reader: Uint8Array, length: Int = definedExternally): Error
                fun decodeDelimited(reader: ProtobufReader): Error
                fun decodeDelimited(reader: Uint8Array): Error
                fun verify(message: Json): String?
                fun fromObject(obj: Json): Error
                fun toObject(message: Error, options: ProtobufIConversionOptions = definedExternally): Json
            }
        }

        interface IGuestKnock {
            var guestId: String?
                get() = definedExternally
                set(value) = definedExternally
            var greeting: String?
                get() = definedExternally
                set(value) = definedExternally
        }

        open class GuestKnock(properties: IGuestKnock = definedExternally) : IGuestKnock {
            open fun toJSON(): Json

            companion object {
                fun create(properties: IGuestKnock = definedExternally): GuestKnock
                fun encode(message: IGuestKnock, writer: ProtobufWriter = definedExternally): ProtobufWriter
                fun encodeDelimited(message: IGuestKnock, writer: ProtobufWriter = definedExternally): ProtobufWriter
                fun decode(reader: ProtobufReader, length: Int = definedExternally): GuestKnock
                fun decode(reader: Uint8Array, length: Int = definedExternally): GuestKnock
                fun decodeDelimited(reader: ProtobufReader): GuestKnock
                fun decodeDelimited(reader: Uint8Array): GuestKnock
                fun verify(message: Json): String?
                fun fromObject(obj: Json): GuestKnock
                fun toObject(message: GuestKnock, options: ProtobufIConversionOptions = definedExternally): Json
            }
        }

        interface IGuestLeft {
            var guestId: String?
                get() = definedExternally
                set(value) = definedExternally
            var goodbye: String?
                get() = definedExternally
                set(value) = definedExternally
        }

        open class GuestLeft(properties: IGuestLeft = definedExternally) : IGuestLeft {
            override var goodbye: String?
            open var _goodbye: String /* "goodbye" */
            open fun toJSON(): Json

            companion object {
                fun create(properties: IGuestLeft = definedExternally): GuestLeft
                fun encode(message: IGuestLeft, writer: ProtobufWriter = definedExternally): ProtobufWriter
                fun encodeDelimited(message: IGuestLeft, writer: ProtobufWriter = definedExternally): ProtobufWriter
                fun decode(reader: ProtobufReader, length: Int = definedExternally): GuestLeft
                fun decode(reader: Uint8Array, length: Int = definedExternally): GuestLeft
                fun decodeDelimited(reader: ProtobufReader): GuestLeft
                fun decodeDelimited(reader: Uint8Array): GuestLeft
                fun verify(message: Json): String?
                fun fromObject(obj: Json): GuestLeft
                fun toObject(message: GuestLeft, options: ProtobufIConversionOptions = definedExternally): Json
            }
        }

        interface IEnteredRoom {
            var roomId: String?
                get() = definedExternally
                set(value) = definedExternally
            var hostId: String?
                get() = definedExternally
                set(value) = definedExternally
        }

        open class EnteredRoom(properties: IEnteredRoom = definedExternally) : IEnteredRoom {
            open fun toJSON(): Json

            companion object {
                fun create(properties: IEnteredRoom = definedExternally): EnteredRoom
                fun encode(message: IEnteredRoom, writer: ProtobufWriter = definedExternally): ProtobufWriter
                fun encodeDelimited(message: IEnteredRoom, writer: ProtobufWriter = definedExternally): ProtobufWriter
                fun decode(reader: ProtobufReader, length: Int = definedExternally): EnteredRoom
                fun decode(reader: Uint8Array, length: Int = definedExternally): EnteredRoom
                fun decodeDelimited(reader: ProtobufReader): EnteredRoom
                fun decodeDelimited(reader: Uint8Array): EnteredRoom
                fun verify(message: Json): String?
                fun fromObject(obj: Json): EnteredRoom
                fun toObject(message: EnteredRoom, options: ProtobufIConversionOptions = definedExternally): Json
            }
        }

        interface IData {
            var senderId: String?
                get() = definedExternally
                set(value) = definedExternally
            var data: Uint8Array?
                get() = definedExternally
                set(value) = definedExternally
        }

        open class Data(properties: IData = definedExternally) : IData {
            open fun toJSON(): Json

            companion object {
                fun create(properties: IData = definedExternally): Data
                fun encode(message: IData, writer: ProtobufWriter = definedExternally): ProtobufWriter
                fun encodeDelimited(message: IData, writer: ProtobufWriter = definedExternally): ProtobufWriter
                fun decode(reader: ProtobufReader, length: Int = definedExternally): Data
                fun decode(reader: Uint8Array, length: Int = definedExternally): Data
                fun decodeDelimited(reader: ProtobufReader): Data
                fun decodeDelimited(reader: Uint8Array): Data
                fun verify(message: Json): String?
                fun fromObject(obj: Json): Data
                fun toObject(message: Data, options: ProtobufIConversionOptions = definedExternally): Json
            }
        }

        companion object {
            fun create(properties: IReply = definedExternally): Reply
            fun encode(message: IReply, writer: ProtobufWriter = definedExternally): ProtobufWriter
            fun encodeDelimited(message: IReply, writer: ProtobufWriter = definedExternally): ProtobufWriter
            fun decode(reader: ProtobufReader, length: Int = definedExternally): Reply
            fun decode(reader: Uint8Array, length: Int = definedExternally): Reply
            fun decodeDelimited(reader: ProtobufReader): Reply
            fun decodeDelimited(reader: Uint8Array): Reply
            fun verify(message: Json): String?
            fun fromObject(obj: Json): Reply
            fun toObject(message: Reply, options: ProtobufIConversionOptions = definedExternally): Json
        }
    }
//}
