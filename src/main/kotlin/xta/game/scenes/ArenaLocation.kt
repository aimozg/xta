package xta.game.scenes

import xta.Player
import xta.game.GameLocation
import xta.text.Parser
import xta.text.joinToSequence
import xta.utils.walk

/*
 * Created by aimozg on 06.12.2021.
 */
object ArenaLocation : GameLocation("Arena") {

	data class ChallengeRequest(val sender: Player, val target: Player)

	// TODO chat notifications
	val challenges = ArrayList<ChallengeRequest>()

	override fun onLeave(player: Player) {
		challenges.walk { iterator, value ->
			if (value.target == player || value.sender == player) {
				iterator.remove()
			}
		}
		for (p in players) {
			p.updateScene()
		}
	}

	val enterScene = scene("enter", updateOnVisit = true) {
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
			outputText(" waiting here.\n\n")

			val outChallenge = challenges.find { it.sender == player }
			val inChallenges = challenges.filter { it.target == player }
			logger.debug(player, "challanges=", challenges, "out=", outChallenge, "in=", inChallenges)

			if (outChallenge != null) {
				outputText("You are challenging "+outChallenge.target.char.name+".\n\n")
				addButton("Cancel challenge","cancelChallenge") {
					challenges.remove(outChallenge)
					player.updateScene()
				}
			}

			if (inChallenges.isNotEmpty()) {
				outputText(inChallenges.joinToSequence { it.sender.char.name })
				outputText(" challenge"+(if (inChallenges.size>1) "" else "s")+" you!\n\n")
				for ((i, challenge) in inChallenges.withIndex()) {
					addButton("Accept "+challenge.sender.char.name+"'s challenge","accept$i") {
						outputText("Not implemented yet!")
						player.updateScreen()
					}
				}
			}

			if (outChallenge == null) {
				for ((index, other) in others.withIndex()) {
					if (inChallenges.any { it.sender == other }) continue
					addButton("Challenge " + other.char.name, "challenge$index") {
						challenges.add(ChallengeRequest(player,other))

						other.updateScene()
						player.updateScene()
					}
				}
			}
		}

		addButton("Back", TownLocation.main, "back")
	}
}
