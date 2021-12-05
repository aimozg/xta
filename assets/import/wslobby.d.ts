import * as $protobuf from "protobufjs";

/** Namespace wslobby. */
export namespace wslobby {

    /** Role enum. */
    enum Role {
        HOST = 0,
        GUEST = 1
    }

    /** Properties of a Command. */
    interface ICommand {

        /** Command id */
        id?: (number|null);

        /** Command ping */
        ping?: (wslobby.Command.IPing|null);

        /** Command identify */
        identify?: (wslobby.Command.IIdentify|null);

        /** Command claimRoom */
        claimRoom?: (wslobby.Command.IClaimRoom|null);

        /** Command joinRoom */
        joinRoom?: (wslobby.Command.IJoinRoom|null);

        /** Command leave */
        leave?: (wslobby.Command.ILeave|null);

        /** Command kickGuest */
        kickGuest?: (wslobby.Command.IKickGuest|null);

        /** Command allowGuest */
        allowGuest?: (wslobby.Command.IAllowGuest|null);

        /** Command broadcast */
        broadcast?: (wslobby.Command.IBroadcast|null);

        /** Command unicast */
        unicast?: (wslobby.Command.IUnicast|null);
    }

    /** Represents a Command. */
    class Command implements ICommand {

        /**
         * Constructs a new Command.
         * @param [properties] Properties to set
         */
        constructor(properties?: wslobby.ICommand);

        /** Command id. */
        public id: number;

        /** Command ping. */
        public ping?: (wslobby.Command.IPing|null);

        /** Command identify. */
        public identify?: (wslobby.Command.IIdentify|null);

        /** Command claimRoom. */
        public claimRoom?: (wslobby.Command.IClaimRoom|null);

        /** Command joinRoom. */
        public joinRoom?: (wslobby.Command.IJoinRoom|null);

        /** Command leave. */
        public leave?: (wslobby.Command.ILeave|null);

        /** Command kickGuest. */
        public kickGuest?: (wslobby.Command.IKickGuest|null);

        /** Command allowGuest. */
        public allowGuest?: (wslobby.Command.IAllowGuest|null);

        /** Command broadcast. */
        public broadcast?: (wslobby.Command.IBroadcast|null);

        /** Command unicast. */
        public unicast?: (wslobby.Command.IUnicast|null);

        /** Command payload. */
        public payload?: ("ping"|"identify"|"claimRoom"|"joinRoom"|"leave"|"kickGuest"|"allowGuest"|"broadcast"|"unicast");

        /**
         * Creates a new Command instance using the specified properties.
         * @param [properties] Properties to set
         * @returns Command instance
         */
        public static create(properties?: wslobby.ICommand): wslobby.Command;

        /**
         * Encodes the specified Command message. Does not implicitly {@link wslobby.Command.verify|verify} messages.
         * @param message Command message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encode(message: wslobby.ICommand, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Encodes the specified Command message, length delimited. Does not implicitly {@link wslobby.Command.verify|verify} messages.
         * @param message Command message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encodeDelimited(message: wslobby.ICommand, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Decodes a Command message from the specified reader or buffer.
         * @param reader Reader or buffer to decode from
         * @param [length] Message length if known beforehand
         * @returns Command
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): wslobby.Command;

        /**
         * Decodes a Command message from the specified reader or buffer, length delimited.
         * @param reader Reader or buffer to decode from
         * @returns Command
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): wslobby.Command;

        /**
         * Verifies a Command message.
         * @param message Plain object to verify
         * @returns `null` if valid, otherwise the reason why it is not
         */
        public static verify(message: { [k: string]: any }): (string|null);

        /**
         * Creates a Command message from a plain object. Also converts values to their respective internal types.
         * @param object Plain object
         * @returns Command
         */
        public static fromObject(object: { [k: string]: any }): wslobby.Command;

        /**
         * Creates a plain object from a Command message. Also converts values to other types if specified.
         * @param message Command
         * @param [options] Conversion options
         * @returns Plain object
         */
        public static toObject(message: wslobby.Command, options?: $protobuf.IConversionOptions): { [k: string]: any };

        /**
         * Converts this Command to JSON.
         * @returns JSON object
         */
        public toJSON(): { [k: string]: any };
    }

    namespace Command {

        /** Properties of a Ping. */
        interface IPing {
        }

        /** Represents a Ping. */
        class Ping implements IPing {

            /**
             * Constructs a new Ping.
             * @param [properties] Properties to set
             */
            constructor(properties?: wslobby.Command.IPing);

            /**
             * Creates a new Ping instance using the specified properties.
             * @param [properties] Properties to set
             * @returns Ping instance
             */
            public static create(properties?: wslobby.Command.IPing): wslobby.Command.Ping;

            /**
             * Encodes the specified Ping message. Does not implicitly {@link wslobby.Command.Ping.verify|verify} messages.
             * @param message Ping message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encode(message: wslobby.Command.IPing, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Encodes the specified Ping message, length delimited. Does not implicitly {@link wslobby.Command.Ping.verify|verify} messages.
             * @param message Ping message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encodeDelimited(message: wslobby.Command.IPing, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Decodes a Ping message from the specified reader or buffer.
             * @param reader Reader or buffer to decode from
             * @param [length] Message length if known beforehand
             * @returns Ping
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): wslobby.Command.Ping;

            /**
             * Decodes a Ping message from the specified reader or buffer, length delimited.
             * @param reader Reader or buffer to decode from
             * @returns Ping
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): wslobby.Command.Ping;

            /**
             * Verifies a Ping message.
             * @param message Plain object to verify
             * @returns `null` if valid, otherwise the reason why it is not
             */
            public static verify(message: { [k: string]: any }): (string|null);

            /**
             * Creates a Ping message from a plain object. Also converts values to their respective internal types.
             * @param object Plain object
             * @returns Ping
             */
            public static fromObject(object: { [k: string]: any }): wslobby.Command.Ping;

            /**
             * Creates a plain object from a Ping message. Also converts values to other types if specified.
             * @param message Ping
             * @param [options] Conversion options
             * @returns Plain object
             */
            public static toObject(message: wslobby.Command.Ping, options?: $protobuf.IConversionOptions): { [k: string]: any };

            /**
             * Converts this Ping to JSON.
             * @returns JSON object
             */
            public toJSON(): { [k: string]: any };
        }

        /** Properties of an Identify. */
        interface IIdentify {

            /** Identify identity */
            identity?: (string|null);

            /** Identify token */
            token?: (string|null);

            /** Identify role */
            role?: (wslobby.Role|null);
        }

        /** Represents an Identify. */
        class Identify implements IIdentify {

            /**
             * Constructs a new Identify.
             * @param [properties] Properties to set
             */
            constructor(properties?: wslobby.Command.IIdentify);

            /** Identify identity. */
            public identity: string;

            /** Identify token. */
            public token: string;

            /** Identify role. */
            public role: wslobby.Role;

            /**
             * Creates a new Identify instance using the specified properties.
             * @param [properties] Properties to set
             * @returns Identify instance
             */
            public static create(properties?: wslobby.Command.IIdentify): wslobby.Command.Identify;

            /**
             * Encodes the specified Identify message. Does not implicitly {@link wslobby.Command.Identify.verify|verify} messages.
             * @param message Identify message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encode(message: wslobby.Command.IIdentify, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Encodes the specified Identify message, length delimited. Does not implicitly {@link wslobby.Command.Identify.verify|verify} messages.
             * @param message Identify message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encodeDelimited(message: wslobby.Command.IIdentify, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Decodes an Identify message from the specified reader or buffer.
             * @param reader Reader or buffer to decode from
             * @param [length] Message length if known beforehand
             * @returns Identify
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): wslobby.Command.Identify;

            /**
             * Decodes an Identify message from the specified reader or buffer, length delimited.
             * @param reader Reader or buffer to decode from
             * @returns Identify
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): wslobby.Command.Identify;

            /**
             * Verifies an Identify message.
             * @param message Plain object to verify
             * @returns `null` if valid, otherwise the reason why it is not
             */
            public static verify(message: { [k: string]: any }): (string|null);

            /**
             * Creates an Identify message from a plain object. Also converts values to their respective internal types.
             * @param object Plain object
             * @returns Identify
             */
            public static fromObject(object: { [k: string]: any }): wslobby.Command.Identify;

            /**
             * Creates a plain object from an Identify message. Also converts values to other types if specified.
             * @param message Identify
             * @param [options] Conversion options
             * @returns Plain object
             */
            public static toObject(message: wslobby.Command.Identify, options?: $protobuf.IConversionOptions): { [k: string]: any };

            /**
             * Converts this Identify to JSON.
             * @returns JSON object
             */
            public toJSON(): { [k: string]: any };
        }

        /** Properties of a ClaimRoom. */
        interface IClaimRoom {

            /** ClaimRoom roomId */
            roomId?: (string|null);
        }

        /** Represents a ClaimRoom. */
        class ClaimRoom implements IClaimRoom {

            /**
             * Constructs a new ClaimRoom.
             * @param [properties] Properties to set
             */
            constructor(properties?: wslobby.Command.IClaimRoom);

            /** ClaimRoom roomId. */
            public roomId: string;

            /**
             * Creates a new ClaimRoom instance using the specified properties.
             * @param [properties] Properties to set
             * @returns ClaimRoom instance
             */
            public static create(properties?: wslobby.Command.IClaimRoom): wslobby.Command.ClaimRoom;

            /**
             * Encodes the specified ClaimRoom message. Does not implicitly {@link wslobby.Command.ClaimRoom.verify|verify} messages.
             * @param message ClaimRoom message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encode(message: wslobby.Command.IClaimRoom, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Encodes the specified ClaimRoom message, length delimited. Does not implicitly {@link wslobby.Command.ClaimRoom.verify|verify} messages.
             * @param message ClaimRoom message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encodeDelimited(message: wslobby.Command.IClaimRoom, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Decodes a ClaimRoom message from the specified reader or buffer.
             * @param reader Reader or buffer to decode from
             * @param [length] Message length if known beforehand
             * @returns ClaimRoom
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): wslobby.Command.ClaimRoom;

            /**
             * Decodes a ClaimRoom message from the specified reader or buffer, length delimited.
             * @param reader Reader or buffer to decode from
             * @returns ClaimRoom
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): wslobby.Command.ClaimRoom;

            /**
             * Verifies a ClaimRoom message.
             * @param message Plain object to verify
             * @returns `null` if valid, otherwise the reason why it is not
             */
            public static verify(message: { [k: string]: any }): (string|null);

            /**
             * Creates a ClaimRoom message from a plain object. Also converts values to their respective internal types.
             * @param object Plain object
             * @returns ClaimRoom
             */
            public static fromObject(object: { [k: string]: any }): wslobby.Command.ClaimRoom;

            /**
             * Creates a plain object from a ClaimRoom message. Also converts values to other types if specified.
             * @param message ClaimRoom
             * @param [options] Conversion options
             * @returns Plain object
             */
            public static toObject(message: wslobby.Command.ClaimRoom, options?: $protobuf.IConversionOptions): { [k: string]: any };

            /**
             * Converts this ClaimRoom to JSON.
             * @returns JSON object
             */
            public toJSON(): { [k: string]: any };
        }

        /** Properties of a JoinRoom. */
        interface IJoinRoom {

            /** JoinRoom roomId */
            roomId?: (string|null);

            /** JoinRoom greeting */
            greeting?: (string|null);
        }

        /** Represents a JoinRoom. */
        class JoinRoom implements IJoinRoom {

            /**
             * Constructs a new JoinRoom.
             * @param [properties] Properties to set
             */
            constructor(properties?: wslobby.Command.IJoinRoom);

            /** JoinRoom roomId. */
            public roomId: string;

            /** JoinRoom greeting. */
            public greeting: string;

            /**
             * Creates a new JoinRoom instance using the specified properties.
             * @param [properties] Properties to set
             * @returns JoinRoom instance
             */
            public static create(properties?: wslobby.Command.IJoinRoom): wslobby.Command.JoinRoom;

            /**
             * Encodes the specified JoinRoom message. Does not implicitly {@link wslobby.Command.JoinRoom.verify|verify} messages.
             * @param message JoinRoom message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encode(message: wslobby.Command.IJoinRoom, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Encodes the specified JoinRoom message, length delimited. Does not implicitly {@link wslobby.Command.JoinRoom.verify|verify} messages.
             * @param message JoinRoom message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encodeDelimited(message: wslobby.Command.IJoinRoom, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Decodes a JoinRoom message from the specified reader or buffer.
             * @param reader Reader or buffer to decode from
             * @param [length] Message length if known beforehand
             * @returns JoinRoom
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): wslobby.Command.JoinRoom;

            /**
             * Decodes a JoinRoom message from the specified reader or buffer, length delimited.
             * @param reader Reader or buffer to decode from
             * @returns JoinRoom
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): wslobby.Command.JoinRoom;

            /**
             * Verifies a JoinRoom message.
             * @param message Plain object to verify
             * @returns `null` if valid, otherwise the reason why it is not
             */
            public static verify(message: { [k: string]: any }): (string|null);

            /**
             * Creates a JoinRoom message from a plain object. Also converts values to their respective internal types.
             * @param object Plain object
             * @returns JoinRoom
             */
            public static fromObject(object: { [k: string]: any }): wslobby.Command.JoinRoom;

            /**
             * Creates a plain object from a JoinRoom message. Also converts values to other types if specified.
             * @param message JoinRoom
             * @param [options] Conversion options
             * @returns Plain object
             */
            public static toObject(message: wslobby.Command.JoinRoom, options?: $protobuf.IConversionOptions): { [k: string]: any };

            /**
             * Converts this JoinRoom to JSON.
             * @returns JSON object
             */
            public toJSON(): { [k: string]: any };
        }

        /** Properties of a Leave. */
        interface ILeave {

            /** Leave goodbye */
            goodbye?: (string|null);
        }

        /** Represents a Leave. */
        class Leave implements ILeave {

            /**
             * Constructs a new Leave.
             * @param [properties] Properties to set
             */
            constructor(properties?: wslobby.Command.ILeave);

            /** Leave goodbye. */
            public goodbye: string;

            /**
             * Creates a new Leave instance using the specified properties.
             * @param [properties] Properties to set
             * @returns Leave instance
             */
            public static create(properties?: wslobby.Command.ILeave): wslobby.Command.Leave;

            /**
             * Encodes the specified Leave message. Does not implicitly {@link wslobby.Command.Leave.verify|verify} messages.
             * @param message Leave message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encode(message: wslobby.Command.ILeave, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Encodes the specified Leave message, length delimited. Does not implicitly {@link wslobby.Command.Leave.verify|verify} messages.
             * @param message Leave message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encodeDelimited(message: wslobby.Command.ILeave, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Decodes a Leave message from the specified reader or buffer.
             * @param reader Reader or buffer to decode from
             * @param [length] Message length if known beforehand
             * @returns Leave
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): wslobby.Command.Leave;

            /**
             * Decodes a Leave message from the specified reader or buffer, length delimited.
             * @param reader Reader or buffer to decode from
             * @returns Leave
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): wslobby.Command.Leave;

            /**
             * Verifies a Leave message.
             * @param message Plain object to verify
             * @returns `null` if valid, otherwise the reason why it is not
             */
            public static verify(message: { [k: string]: any }): (string|null);

            /**
             * Creates a Leave message from a plain object. Also converts values to their respective internal types.
             * @param object Plain object
             * @returns Leave
             */
            public static fromObject(object: { [k: string]: any }): wslobby.Command.Leave;

            /**
             * Creates a plain object from a Leave message. Also converts values to other types if specified.
             * @param message Leave
             * @param [options] Conversion options
             * @returns Plain object
             */
            public static toObject(message: wslobby.Command.Leave, options?: $protobuf.IConversionOptions): { [k: string]: any };

            /**
             * Converts this Leave to JSON.
             * @returns JSON object
             */
            public toJSON(): { [k: string]: any };
        }

        /** Properties of a KickGuest. */
        interface IKickGuest {

            /** KickGuest guestId */
            guestId?: (string|null);

            /** KickGuest message */
            message?: (string|null);
        }

        /** Represents a KickGuest. */
        class KickGuest implements IKickGuest {

            /**
             * Constructs a new KickGuest.
             * @param [properties] Properties to set
             */
            constructor(properties?: wslobby.Command.IKickGuest);

            /** KickGuest guestId. */
            public guestId: string;

            /** KickGuest message. */
            public message: string;

            /**
             * Creates a new KickGuest instance using the specified properties.
             * @param [properties] Properties to set
             * @returns KickGuest instance
             */
            public static create(properties?: wslobby.Command.IKickGuest): wslobby.Command.KickGuest;

            /**
             * Encodes the specified KickGuest message. Does not implicitly {@link wslobby.Command.KickGuest.verify|verify} messages.
             * @param message KickGuest message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encode(message: wslobby.Command.IKickGuest, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Encodes the specified KickGuest message, length delimited. Does not implicitly {@link wslobby.Command.KickGuest.verify|verify} messages.
             * @param message KickGuest message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encodeDelimited(message: wslobby.Command.IKickGuest, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Decodes a KickGuest message from the specified reader or buffer.
             * @param reader Reader or buffer to decode from
             * @param [length] Message length if known beforehand
             * @returns KickGuest
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): wslobby.Command.KickGuest;

            /**
             * Decodes a KickGuest message from the specified reader or buffer, length delimited.
             * @param reader Reader or buffer to decode from
             * @returns KickGuest
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): wslobby.Command.KickGuest;

            /**
             * Verifies a KickGuest message.
             * @param message Plain object to verify
             * @returns `null` if valid, otherwise the reason why it is not
             */
            public static verify(message: { [k: string]: any }): (string|null);

            /**
             * Creates a KickGuest message from a plain object. Also converts values to their respective internal types.
             * @param object Plain object
             * @returns KickGuest
             */
            public static fromObject(object: { [k: string]: any }): wslobby.Command.KickGuest;

            /**
             * Creates a plain object from a KickGuest message. Also converts values to other types if specified.
             * @param message KickGuest
             * @param [options] Conversion options
             * @returns Plain object
             */
            public static toObject(message: wslobby.Command.KickGuest, options?: $protobuf.IConversionOptions): { [k: string]: any };

            /**
             * Converts this KickGuest to JSON.
             * @returns JSON object
             */
            public toJSON(): { [k: string]: any };
        }

        /** Properties of an AllowGuest. */
        interface IAllowGuest {

            /** AllowGuest guestId */
            guestId?: (string|null);
        }

        /** Represents an AllowGuest. */
        class AllowGuest implements IAllowGuest {

            /**
             * Constructs a new AllowGuest.
             * @param [properties] Properties to set
             */
            constructor(properties?: wslobby.Command.IAllowGuest);

            /** AllowGuest guestId. */
            public guestId: string;

            /**
             * Creates a new AllowGuest instance using the specified properties.
             * @param [properties] Properties to set
             * @returns AllowGuest instance
             */
            public static create(properties?: wslobby.Command.IAllowGuest): wslobby.Command.AllowGuest;

            /**
             * Encodes the specified AllowGuest message. Does not implicitly {@link wslobby.Command.AllowGuest.verify|verify} messages.
             * @param message AllowGuest message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encode(message: wslobby.Command.IAllowGuest, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Encodes the specified AllowGuest message, length delimited. Does not implicitly {@link wslobby.Command.AllowGuest.verify|verify} messages.
             * @param message AllowGuest message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encodeDelimited(message: wslobby.Command.IAllowGuest, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Decodes an AllowGuest message from the specified reader or buffer.
             * @param reader Reader or buffer to decode from
             * @param [length] Message length if known beforehand
             * @returns AllowGuest
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): wslobby.Command.AllowGuest;

            /**
             * Decodes an AllowGuest message from the specified reader or buffer, length delimited.
             * @param reader Reader or buffer to decode from
             * @returns AllowGuest
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): wslobby.Command.AllowGuest;

            /**
             * Verifies an AllowGuest message.
             * @param message Plain object to verify
             * @returns `null` if valid, otherwise the reason why it is not
             */
            public static verify(message: { [k: string]: any }): (string|null);

            /**
             * Creates an AllowGuest message from a plain object. Also converts values to their respective internal types.
             * @param object Plain object
             * @returns AllowGuest
             */
            public static fromObject(object: { [k: string]: any }): wslobby.Command.AllowGuest;

            /**
             * Creates a plain object from an AllowGuest message. Also converts values to other types if specified.
             * @param message AllowGuest
             * @param [options] Conversion options
             * @returns Plain object
             */
            public static toObject(message: wslobby.Command.AllowGuest, options?: $protobuf.IConversionOptions): { [k: string]: any };

            /**
             * Converts this AllowGuest to JSON.
             * @returns JSON object
             */
            public toJSON(): { [k: string]: any };
        }

        /** Properties of a Broadcast. */
        interface IBroadcast {

            /** Broadcast data */
            data?: (Uint8Array|null);
        }

        /** Represents a Broadcast. */
        class Broadcast implements IBroadcast {

            /**
             * Constructs a new Broadcast.
             * @param [properties] Properties to set
             */
            constructor(properties?: wslobby.Command.IBroadcast);

            /** Broadcast data. */
            public data: Uint8Array;

            /**
             * Creates a new Broadcast instance using the specified properties.
             * @param [properties] Properties to set
             * @returns Broadcast instance
             */
            public static create(properties?: wslobby.Command.IBroadcast): wslobby.Command.Broadcast;

            /**
             * Encodes the specified Broadcast message. Does not implicitly {@link wslobby.Command.Broadcast.verify|verify} messages.
             * @param message Broadcast message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encode(message: wslobby.Command.IBroadcast, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Encodes the specified Broadcast message, length delimited. Does not implicitly {@link wslobby.Command.Broadcast.verify|verify} messages.
             * @param message Broadcast message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encodeDelimited(message: wslobby.Command.IBroadcast, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Decodes a Broadcast message from the specified reader or buffer.
             * @param reader Reader or buffer to decode from
             * @param [length] Message length if known beforehand
             * @returns Broadcast
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): wslobby.Command.Broadcast;

            /**
             * Decodes a Broadcast message from the specified reader or buffer, length delimited.
             * @param reader Reader or buffer to decode from
             * @returns Broadcast
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): wslobby.Command.Broadcast;

            /**
             * Verifies a Broadcast message.
             * @param message Plain object to verify
             * @returns `null` if valid, otherwise the reason why it is not
             */
            public static verify(message: { [k: string]: any }): (string|null);

            /**
             * Creates a Broadcast message from a plain object. Also converts values to their respective internal types.
             * @param object Plain object
             * @returns Broadcast
             */
            public static fromObject(object: { [k: string]: any }): wslobby.Command.Broadcast;

            /**
             * Creates a plain object from a Broadcast message. Also converts values to other types if specified.
             * @param message Broadcast
             * @param [options] Conversion options
             * @returns Plain object
             */
            public static toObject(message: wslobby.Command.Broadcast, options?: $protobuf.IConversionOptions): { [k: string]: any };

            /**
             * Converts this Broadcast to JSON.
             * @returns JSON object
             */
            public toJSON(): { [k: string]: any };
        }

        /** Properties of an Unicast. */
        interface IUnicast {

            /** Unicast guestId */
            guestId?: (string|null);

            /** Unicast data */
            data?: (Uint8Array|null);
        }

        /** Represents an Unicast. */
        class Unicast implements IUnicast {

            /**
             * Constructs a new Unicast.
             * @param [properties] Properties to set
             */
            constructor(properties?: wslobby.Command.IUnicast);

            /** Unicast guestId. */
            public guestId: string;

            /** Unicast data. */
            public data: Uint8Array;

            /**
             * Creates a new Unicast instance using the specified properties.
             * @param [properties] Properties to set
             * @returns Unicast instance
             */
            public static create(properties?: wslobby.Command.IUnicast): wslobby.Command.Unicast;

            /**
             * Encodes the specified Unicast message. Does not implicitly {@link wslobby.Command.Unicast.verify|verify} messages.
             * @param message Unicast message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encode(message: wslobby.Command.IUnicast, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Encodes the specified Unicast message, length delimited. Does not implicitly {@link wslobby.Command.Unicast.verify|verify} messages.
             * @param message Unicast message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encodeDelimited(message: wslobby.Command.IUnicast, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Decodes an Unicast message from the specified reader or buffer.
             * @param reader Reader or buffer to decode from
             * @param [length] Message length if known beforehand
             * @returns Unicast
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): wslobby.Command.Unicast;

            /**
             * Decodes an Unicast message from the specified reader or buffer, length delimited.
             * @param reader Reader or buffer to decode from
             * @returns Unicast
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): wslobby.Command.Unicast;

            /**
             * Verifies an Unicast message.
             * @param message Plain object to verify
             * @returns `null` if valid, otherwise the reason why it is not
             */
            public static verify(message: { [k: string]: any }): (string|null);

            /**
             * Creates an Unicast message from a plain object. Also converts values to their respective internal types.
             * @param object Plain object
             * @returns Unicast
             */
            public static fromObject(object: { [k: string]: any }): wslobby.Command.Unicast;

            /**
             * Creates a plain object from an Unicast message. Also converts values to other types if specified.
             * @param message Unicast
             * @param [options] Conversion options
             * @returns Plain object
             */
            public static toObject(message: wslobby.Command.Unicast, options?: $protobuf.IConversionOptions): { [k: string]: any };

            /**
             * Converts this Unicast to JSON.
             * @returns JSON object
             */
            public toJSON(): { [k: string]: any };
        }
    }

    /** Properties of a Reply. */
    interface IReply {

        /** Reply id */
        id?: (number|null);

        /** Reply pong */
        pong?: (wslobby.Reply.IPong|null);

        /** Reply ack */
        ack?: (wslobby.Reply.IAck|null);

        /** Reply error */
        error?: (wslobby.Reply.IError|null);

        /** Reply guestKnock */
        guestKnock?: (wslobby.Reply.IGuestKnock|null);

        /** Reply guestLeft */
        guestLeft?: (wslobby.Reply.IGuestLeft|null);

        /** Reply enteredRoom */
        enteredRoom?: (wslobby.Reply.IEnteredRoom|null);

        /** Reply data */
        data?: (wslobby.Reply.IData|null);
    }

    /** Represents a Reply. */
    class Reply implements IReply {

        /**
         * Constructs a new Reply.
         * @param [properties] Properties to set
         */
        constructor(properties?: wslobby.IReply);

        /** Reply id. */
        public id: number;

        /** Reply pong. */
        public pong?: (wslobby.Reply.IPong|null);

        /** Reply ack. */
        public ack?: (wslobby.Reply.IAck|null);

        /** Reply error. */
        public error?: (wslobby.Reply.IError|null);

        /** Reply guestKnock. */
        public guestKnock?: (wslobby.Reply.IGuestKnock|null);

        /** Reply guestLeft. */
        public guestLeft?: (wslobby.Reply.IGuestLeft|null);

        /** Reply enteredRoom. */
        public enteredRoom?: (wslobby.Reply.IEnteredRoom|null);

        /** Reply data. */
        public data?: (wslobby.Reply.IData|null);

        /** Reply payload. */
        public payload?: ("pong"|"ack"|"error"|"guestKnock"|"guestLeft"|"enteredRoom"|"data");

        /**
         * Creates a new Reply instance using the specified properties.
         * @param [properties] Properties to set
         * @returns Reply instance
         */
        public static create(properties?: wslobby.IReply): wslobby.Reply;

        /**
         * Encodes the specified Reply message. Does not implicitly {@link wslobby.Reply.verify|verify} messages.
         * @param message Reply message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encode(message: wslobby.IReply, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Encodes the specified Reply message, length delimited. Does not implicitly {@link wslobby.Reply.verify|verify} messages.
         * @param message Reply message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encodeDelimited(message: wslobby.IReply, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Decodes a Reply message from the specified reader or buffer.
         * @param reader Reader or buffer to decode from
         * @param [length] Message length if known beforehand
         * @returns Reply
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): wslobby.Reply;

        /**
         * Decodes a Reply message from the specified reader or buffer, length delimited.
         * @param reader Reader or buffer to decode from
         * @returns Reply
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): wslobby.Reply;

        /**
         * Verifies a Reply message.
         * @param message Plain object to verify
         * @returns `null` if valid, otherwise the reason why it is not
         */
        public static verify(message: { [k: string]: any }): (string|null);

        /**
         * Creates a Reply message from a plain object. Also converts values to their respective internal types.
         * @param object Plain object
         * @returns Reply
         */
        public static fromObject(object: { [k: string]: any }): wslobby.Reply;

        /**
         * Creates a plain object from a Reply message. Also converts values to other types if specified.
         * @param message Reply
         * @param [options] Conversion options
         * @returns Plain object
         */
        public static toObject(message: wslobby.Reply, options?: $protobuf.IConversionOptions): { [k: string]: any };

        /**
         * Converts this Reply to JSON.
         * @returns JSON object
         */
        public toJSON(): { [k: string]: any };
    }

    namespace Reply {

        /** Properties of a Pong. */
        interface IPong {
        }

        /** Represents a Pong. */
        class Pong implements IPong {

            /**
             * Constructs a new Pong.
             * @param [properties] Properties to set
             */
            constructor(properties?: wslobby.Reply.IPong);

            /**
             * Creates a new Pong instance using the specified properties.
             * @param [properties] Properties to set
             * @returns Pong instance
             */
            public static create(properties?: wslobby.Reply.IPong): wslobby.Reply.Pong;

            /**
             * Encodes the specified Pong message. Does not implicitly {@link wslobby.Reply.Pong.verify|verify} messages.
             * @param message Pong message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encode(message: wslobby.Reply.IPong, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Encodes the specified Pong message, length delimited. Does not implicitly {@link wslobby.Reply.Pong.verify|verify} messages.
             * @param message Pong message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encodeDelimited(message: wslobby.Reply.IPong, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Decodes a Pong message from the specified reader or buffer.
             * @param reader Reader or buffer to decode from
             * @param [length] Message length if known beforehand
             * @returns Pong
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): wslobby.Reply.Pong;

            /**
             * Decodes a Pong message from the specified reader or buffer, length delimited.
             * @param reader Reader or buffer to decode from
             * @returns Pong
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): wslobby.Reply.Pong;

            /**
             * Verifies a Pong message.
             * @param message Plain object to verify
             * @returns `null` if valid, otherwise the reason why it is not
             */
            public static verify(message: { [k: string]: any }): (string|null);

            /**
             * Creates a Pong message from a plain object. Also converts values to their respective internal types.
             * @param object Plain object
             * @returns Pong
             */
            public static fromObject(object: { [k: string]: any }): wslobby.Reply.Pong;

            /**
             * Creates a plain object from a Pong message. Also converts values to other types if specified.
             * @param message Pong
             * @param [options] Conversion options
             * @returns Plain object
             */
            public static toObject(message: wslobby.Reply.Pong, options?: $protobuf.IConversionOptions): { [k: string]: any };

            /**
             * Converts this Pong to JSON.
             * @returns JSON object
             */
            public toJSON(): { [k: string]: any };
        }

        /** Properties of an Ack. */
        interface IAck {
        }

        /** Represents an Ack. */
        class Ack implements IAck {

            /**
             * Constructs a new Ack.
             * @param [properties] Properties to set
             */
            constructor(properties?: wslobby.Reply.IAck);

            /**
             * Creates a new Ack instance using the specified properties.
             * @param [properties] Properties to set
             * @returns Ack instance
             */
            public static create(properties?: wslobby.Reply.IAck): wslobby.Reply.Ack;

            /**
             * Encodes the specified Ack message. Does not implicitly {@link wslobby.Reply.Ack.verify|verify} messages.
             * @param message Ack message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encode(message: wslobby.Reply.IAck, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Encodes the specified Ack message, length delimited. Does not implicitly {@link wslobby.Reply.Ack.verify|verify} messages.
             * @param message Ack message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encodeDelimited(message: wslobby.Reply.IAck, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Decodes an Ack message from the specified reader or buffer.
             * @param reader Reader or buffer to decode from
             * @param [length] Message length if known beforehand
             * @returns Ack
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): wslobby.Reply.Ack;

            /**
             * Decodes an Ack message from the specified reader or buffer, length delimited.
             * @param reader Reader or buffer to decode from
             * @returns Ack
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): wslobby.Reply.Ack;

            /**
             * Verifies an Ack message.
             * @param message Plain object to verify
             * @returns `null` if valid, otherwise the reason why it is not
             */
            public static verify(message: { [k: string]: any }): (string|null);

            /**
             * Creates an Ack message from a plain object. Also converts values to their respective internal types.
             * @param object Plain object
             * @returns Ack
             */
            public static fromObject(object: { [k: string]: any }): wslobby.Reply.Ack;

            /**
             * Creates a plain object from an Ack message. Also converts values to other types if specified.
             * @param message Ack
             * @param [options] Conversion options
             * @returns Plain object
             */
            public static toObject(message: wslobby.Reply.Ack, options?: $protobuf.IConversionOptions): { [k: string]: any };

            /**
             * Converts this Ack to JSON.
             * @returns JSON object
             */
            public toJSON(): { [k: string]: any };
        }

        /** Properties of an Error. */
        interface IError {

            /** Error message */
            message?: (string|null);
        }

        /** Represents an Error. */
        class Error implements IError {

            /**
             * Constructs a new Error.
             * @param [properties] Properties to set
             */
            constructor(properties?: wslobby.Reply.IError);

            /** Error message. */
            public message: string;

            /**
             * Creates a new Error instance using the specified properties.
             * @param [properties] Properties to set
             * @returns Error instance
             */
            public static create(properties?: wslobby.Reply.IError): wslobby.Reply.Error;

            /**
             * Encodes the specified Error message. Does not implicitly {@link wslobby.Reply.Error.verify|verify} messages.
             * @param message Error message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encode(message: wslobby.Reply.IError, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Encodes the specified Error message, length delimited. Does not implicitly {@link wslobby.Reply.Error.verify|verify} messages.
             * @param message Error message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encodeDelimited(message: wslobby.Reply.IError, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Decodes an Error message from the specified reader or buffer.
             * @param reader Reader or buffer to decode from
             * @param [length] Message length if known beforehand
             * @returns Error
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): wslobby.Reply.Error;

            /**
             * Decodes an Error message from the specified reader or buffer, length delimited.
             * @param reader Reader or buffer to decode from
             * @returns Error
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): wslobby.Reply.Error;

            /**
             * Verifies an Error message.
             * @param message Plain object to verify
             * @returns `null` if valid, otherwise the reason why it is not
             */
            public static verify(message: { [k: string]: any }): (string|null);

            /**
             * Creates an Error message from a plain object. Also converts values to their respective internal types.
             * @param object Plain object
             * @returns Error
             */
            public static fromObject(object: { [k: string]: any }): wslobby.Reply.Error;

            /**
             * Creates a plain object from an Error message. Also converts values to other types if specified.
             * @param message Error
             * @param [options] Conversion options
             * @returns Plain object
             */
            public static toObject(message: wslobby.Reply.Error, options?: $protobuf.IConversionOptions): { [k: string]: any };

            /**
             * Converts this Error to JSON.
             * @returns JSON object
             */
            public toJSON(): { [k: string]: any };
        }

        /** Properties of a GuestKnock. */
        interface IGuestKnock {

            /** GuestKnock guestId */
            guestId?: (string|null);

            /** GuestKnock greeting */
            greeting?: (string|null);
        }

        /** Represents a GuestKnock. */
        class GuestKnock implements IGuestKnock {

            /**
             * Constructs a new GuestKnock.
             * @param [properties] Properties to set
             */
            constructor(properties?: wslobby.Reply.IGuestKnock);

            /** GuestKnock guestId. */
            public guestId: string;

            /** GuestKnock greeting. */
            public greeting: string;

            /**
             * Creates a new GuestKnock instance using the specified properties.
             * @param [properties] Properties to set
             * @returns GuestKnock instance
             */
            public static create(properties?: wslobby.Reply.IGuestKnock): wslobby.Reply.GuestKnock;

            /**
             * Encodes the specified GuestKnock message. Does not implicitly {@link wslobby.Reply.GuestKnock.verify|verify} messages.
             * @param message GuestKnock message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encode(message: wslobby.Reply.IGuestKnock, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Encodes the specified GuestKnock message, length delimited. Does not implicitly {@link wslobby.Reply.GuestKnock.verify|verify} messages.
             * @param message GuestKnock message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encodeDelimited(message: wslobby.Reply.IGuestKnock, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Decodes a GuestKnock message from the specified reader or buffer.
             * @param reader Reader or buffer to decode from
             * @param [length] Message length if known beforehand
             * @returns GuestKnock
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): wslobby.Reply.GuestKnock;

            /**
             * Decodes a GuestKnock message from the specified reader or buffer, length delimited.
             * @param reader Reader or buffer to decode from
             * @returns GuestKnock
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): wslobby.Reply.GuestKnock;

            /**
             * Verifies a GuestKnock message.
             * @param message Plain object to verify
             * @returns `null` if valid, otherwise the reason why it is not
             */
            public static verify(message: { [k: string]: any }): (string|null);

            /**
             * Creates a GuestKnock message from a plain object. Also converts values to their respective internal types.
             * @param object Plain object
             * @returns GuestKnock
             */
            public static fromObject(object: { [k: string]: any }): wslobby.Reply.GuestKnock;

            /**
             * Creates a plain object from a GuestKnock message. Also converts values to other types if specified.
             * @param message GuestKnock
             * @param [options] Conversion options
             * @returns Plain object
             */
            public static toObject(message: wslobby.Reply.GuestKnock, options?: $protobuf.IConversionOptions): { [k: string]: any };

            /**
             * Converts this GuestKnock to JSON.
             * @returns JSON object
             */
            public toJSON(): { [k: string]: any };
        }

        /** Properties of a GuestLeft. */
        interface IGuestLeft {

            /** GuestLeft guestId */
            guestId?: (string|null);

            /** GuestLeft goodbye */
            goodbye?: (string|null);
        }

        /** Represents a GuestLeft. */
        class GuestLeft implements IGuestLeft {

            /**
             * Constructs a new GuestLeft.
             * @param [properties] Properties to set
             */
            constructor(properties?: wslobby.Reply.IGuestLeft);

            /** GuestLeft guestId. */
            public guestId: string;

            /** GuestLeft goodbye. */
            public goodbye?: (string|null);

            /** GuestLeft _goodbye. */
            public _goodbye?: "goodbye";

            /**
             * Creates a new GuestLeft instance using the specified properties.
             * @param [properties] Properties to set
             * @returns GuestLeft instance
             */
            public static create(properties?: wslobby.Reply.IGuestLeft): wslobby.Reply.GuestLeft;

            /**
             * Encodes the specified GuestLeft message. Does not implicitly {@link wslobby.Reply.GuestLeft.verify|verify} messages.
             * @param message GuestLeft message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encode(message: wslobby.Reply.IGuestLeft, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Encodes the specified GuestLeft message, length delimited. Does not implicitly {@link wslobby.Reply.GuestLeft.verify|verify} messages.
             * @param message GuestLeft message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encodeDelimited(message: wslobby.Reply.IGuestLeft, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Decodes a GuestLeft message from the specified reader or buffer.
             * @param reader Reader or buffer to decode from
             * @param [length] Message length if known beforehand
             * @returns GuestLeft
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): wslobby.Reply.GuestLeft;

            /**
             * Decodes a GuestLeft message from the specified reader or buffer, length delimited.
             * @param reader Reader or buffer to decode from
             * @returns GuestLeft
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): wslobby.Reply.GuestLeft;

            /**
             * Verifies a GuestLeft message.
             * @param message Plain object to verify
             * @returns `null` if valid, otherwise the reason why it is not
             */
            public static verify(message: { [k: string]: any }): (string|null);

            /**
             * Creates a GuestLeft message from a plain object. Also converts values to their respective internal types.
             * @param object Plain object
             * @returns GuestLeft
             */
            public static fromObject(object: { [k: string]: any }): wslobby.Reply.GuestLeft;

            /**
             * Creates a plain object from a GuestLeft message. Also converts values to other types if specified.
             * @param message GuestLeft
             * @param [options] Conversion options
             * @returns Plain object
             */
            public static toObject(message: wslobby.Reply.GuestLeft, options?: $protobuf.IConversionOptions): { [k: string]: any };

            /**
             * Converts this GuestLeft to JSON.
             * @returns JSON object
             */
            public toJSON(): { [k: string]: any };
        }

        /** Properties of an EnteredRoom. */
        interface IEnteredRoom {

            /** EnteredRoom roomId */
            roomId?: (string|null);

            /** EnteredRoom hostId */
            hostId?: (string|null);
        }

        /** Represents an EnteredRoom. */
        class EnteredRoom implements IEnteredRoom {

            /**
             * Constructs a new EnteredRoom.
             * @param [properties] Properties to set
             */
            constructor(properties?: wslobby.Reply.IEnteredRoom);

            /** EnteredRoom roomId. */
            public roomId: string;

            /** EnteredRoom hostId. */
            public hostId: string;

            /**
             * Creates a new EnteredRoom instance using the specified properties.
             * @param [properties] Properties to set
             * @returns EnteredRoom instance
             */
            public static create(properties?: wslobby.Reply.IEnteredRoom): wslobby.Reply.EnteredRoom;

            /**
             * Encodes the specified EnteredRoom message. Does not implicitly {@link wslobby.Reply.EnteredRoom.verify|verify} messages.
             * @param message EnteredRoom message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encode(message: wslobby.Reply.IEnteredRoom, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Encodes the specified EnteredRoom message, length delimited. Does not implicitly {@link wslobby.Reply.EnteredRoom.verify|verify} messages.
             * @param message EnteredRoom message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encodeDelimited(message: wslobby.Reply.IEnteredRoom, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Decodes an EnteredRoom message from the specified reader or buffer.
             * @param reader Reader or buffer to decode from
             * @param [length] Message length if known beforehand
             * @returns EnteredRoom
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): wslobby.Reply.EnteredRoom;

            /**
             * Decodes an EnteredRoom message from the specified reader or buffer, length delimited.
             * @param reader Reader or buffer to decode from
             * @returns EnteredRoom
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): wslobby.Reply.EnteredRoom;

            /**
             * Verifies an EnteredRoom message.
             * @param message Plain object to verify
             * @returns `null` if valid, otherwise the reason why it is not
             */
            public static verify(message: { [k: string]: any }): (string|null);

            /**
             * Creates an EnteredRoom message from a plain object. Also converts values to their respective internal types.
             * @param object Plain object
             * @returns EnteredRoom
             */
            public static fromObject(object: { [k: string]: any }): wslobby.Reply.EnteredRoom;

            /**
             * Creates a plain object from an EnteredRoom message. Also converts values to other types if specified.
             * @param message EnteredRoom
             * @param [options] Conversion options
             * @returns Plain object
             */
            public static toObject(message: wslobby.Reply.EnteredRoom, options?: $protobuf.IConversionOptions): { [k: string]: any };

            /**
             * Converts this EnteredRoom to JSON.
             * @returns JSON object
             */
            public toJSON(): { [k: string]: any };
        }

        /** Properties of a Data. */
        interface IData {

            /** Data senderId */
            senderId?: (string|null);

            /** Data data */
            data?: (Uint8Array|null);
        }

        /** Represents a Data. */
        class Data implements IData {

            /**
             * Constructs a new Data.
             * @param [properties] Properties to set
             */
            constructor(properties?: wslobby.Reply.IData);

            /** Data senderId. */
            public senderId: string;

            /** Data data. */
            public data: Uint8Array;

            /**
             * Creates a new Data instance using the specified properties.
             * @param [properties] Properties to set
             * @returns Data instance
             */
            public static create(properties?: wslobby.Reply.IData): wslobby.Reply.Data;

            /**
             * Encodes the specified Data message. Does not implicitly {@link wslobby.Reply.Data.verify|verify} messages.
             * @param message Data message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encode(message: wslobby.Reply.IData, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Encodes the specified Data message, length delimited. Does not implicitly {@link wslobby.Reply.Data.verify|verify} messages.
             * @param message Data message or plain object to encode
             * @param [writer] Writer to encode to
             * @returns Writer
             */
            public static encodeDelimited(message: wslobby.Reply.IData, writer?: $protobuf.Writer): $protobuf.Writer;

            /**
             * Decodes a Data message from the specified reader or buffer.
             * @param reader Reader or buffer to decode from
             * @param [length] Message length if known beforehand
             * @returns Data
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): wslobby.Reply.Data;

            /**
             * Decodes a Data message from the specified reader or buffer, length delimited.
             * @param reader Reader or buffer to decode from
             * @returns Data
             * @throws {Error} If the payload is not a reader or valid buffer
             * @throws {$protobuf.util.ProtocolError} If required fields are missing
             */
            public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): wslobby.Reply.Data;

            /**
             * Verifies a Data message.
             * @param message Plain object to verify
             * @returns `null` if valid, otherwise the reason why it is not
             */
            public static verify(message: { [k: string]: any }): (string|null);

            /**
             * Creates a Data message from a plain object. Also converts values to their respective internal types.
             * @param object Plain object
             * @returns Data
             */
            public static fromObject(object: { [k: string]: any }): wslobby.Reply.Data;

            /**
             * Creates a plain object from a Data message. Also converts values to other types if specified.
             * @param message Data
             * @param [options] Conversion options
             * @returns Plain object
             */
            public static toObject(message: wslobby.Reply.Data, options?: $protobuf.IConversionOptions): { [k: string]: any };

            /**
             * Converts this Data to JSON.
             * @returns JSON object
             */
            public toJSON(): { [k: string]: any };
        }
    }
}
