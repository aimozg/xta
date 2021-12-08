package xta.text

import xta.Game
import xta.Player
import xta.game.text.evalGameTag

/*
 * Created by aimozg on 28.11.2021.
 */
/**
 * [myPlayer] - Player who reads the text, [player] - player who is described.
 *
 * If [myPlayer] is Alice and [player] is Bob, then `"[name] [is]"` evals to `Bob is`;
 * If [myPlayer] and [player] are the same, then `"[name] [is]"` evals to `You are`.
 */
class Parser(
	val myPlayer: Player?,
	var player: Player?
): AbstractParser() {
	override fun toLogString() = player?.toLogString()?: Game.me.toLogString()

	override fun doEvaluateTag(tag: String, tagArgs: String): String {
		return when (tag) {
			"if" -> TODO("If tag is not implemented")
			else -> evalGameTag(tag, tagArgs)
		}
	}

}
