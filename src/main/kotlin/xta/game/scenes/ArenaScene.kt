package xta.game.scenes

import xta.Game
import xta.game.Scene
import xta.text.Display
import xta.text.Parser
import xta.text.joinToSequence

object ArenaScene : Scene("Town/arena") {
	override val updateOnVisit: Boolean
		get() = true

	override fun Display.doExecute() {
		outputText("Coming closer to the arena you see two muscular tigersharks standing on each side of the entrance, they only briefly glance at you the moment you pass by them. IA few a moments after you entered, a tall, slightly muscular male cat-morph approaches you. Most of its body is covered by armor yet two long tails waves behind him from time to time.")
		outputText("\n\n\"<i>Welcome to the Soul Arena. Don't start fights outside of the proper place or you will be thrown out. If you break any rules here you will be kicked out. Go ahead and pick an area where you want to train or instead go to the challenges area. Oh and fights in each section cost some spirit stones so be sure to have enough of them as we not run charity here,</i>\"")
		outputText(" without wasting time the nekomata overseer of this place explains to you all that you needed to know about the place and walks away.")
		outputText("\n\n")
		val others = otherPlayers()
		if (others.isNotEmpty()) {
			outputText("You see ")
			rawOutput(others.joinToSequence {
				Parser(player, it).parse(
					"[name], level ${it.char.level} [malefemaleherm] [race]"
				)
			})
			outputText(" waiting here.")

			for ((index, other) in others.withIndex()) {
				addButton("Challenge "+other.char.name,"challenge$index") {
					outputText("Sorry, not implemented yet")
					Game.server?.updateScreen(player)
				}
			}
		}

		addButton("Back", TownScene, "back")
	}
}
