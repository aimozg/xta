package xta.text

import xta.Game
import xta.Player
import xta.game.PlayerCharacter
import xta.game.text.evalGameTag

/*
 * Created by aimozg on 28.11.2021.
 */
/**
 * [myPlayer] - Player who reads the text, [npc0] - character who is described.
 *
 * If [myPlayer] is Alice and [npc0] is Bob, then `"[name] [is]"` evals to `Bob is`;
 * If [myPlayer] and [npc0] are the same, then `"[name] [is]"` evals to `You are`.
 */
class Parser(
	val myPlayer: Player?,
	npc0: PlayerCharacter? = myPlayer?.char
): AbstractParser() {
	constructor(myPlayer: Player?, otherPlayer:Player?): this(myPlayer, otherPlayer?.char)

	val npcs = arrayOfNulls<PlayerCharacter>(10)
	init {
		npcs[0] = npc0
	}
	var npc0 get() = npcs[0]; set(value) { npcs[0] = value }
	var npc1 get() = npcs[1]; set(value) { npcs[1] = value }

	override fun toLogString() = Game.me.toLogString()

	override fun doEvaluateTag(tag: String, tagArgs: String): String {
		return when {
			tag == "if" -> TODO("If tag is not implemented")
			else -> evalGameTag(tag, tagArgs)
		}
	}

	fun clearNpcs(npc0: PlayerCharacter?) {
		for (i in npcs.indices) {
			npcs[i] = null
		}
		this.npc0 = npc0
	}

}
