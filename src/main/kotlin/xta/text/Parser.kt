package xta.text

import xta.Game
import xta.Player
import xta.game.PlayerCharacter
import xta.game.text.evalGameTag

/*
 * Created by aimozg on 28.11.2021.
 */
/**
 * [myPlayer] - Player who reads the text, [char] - character who is described.
 *
 * If [myPlayer] is Alice and [char] is Bob, then `"[name] [is]"` evals to `Bob is`;
 * If [myPlayer] and [char] are the same, then `"[name] [is]"` evals to `You are`.
 */
class Parser(
	val myPlayer: Player?,
	var char: PlayerCharacter? = myPlayer?.char
): AbstractParser() {
	constructor(myPlayer: Player?, otherPlayer:Player?): this(myPlayer, otherPlayer?.char)
	override fun toLogString() = Game.me.toLogString()

	override fun doEvaluateTag(tag: String, tagArgs: String): String {
		return when {
			tag == "if" -> TODO("If tag is not implemented")
			else -> evalGameTag(tag, tagArgs)
		}
	}

}
