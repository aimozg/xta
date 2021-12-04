package xta.game.scenes

import xta.game.Scene
import xta.text.Display

/*
 * Created by aimozg on 02.12.2021.
 */
object TownScene: Scene("Town") {
	override fun Display.doExecute() {
		outputText("He'Xin'Dao is a large village connected and assembled between many smaller islands in the middle of large river that flows from the east to the west. Aside from bridges connecting each of the islands together, two larger ones connects them as a whole to both sides of the river serving as the only points of access to the village.  The village is strategically laid out, preventing anyone from entering by swimming directly from the river to any of the islands, forcing people to use the bridges if they wish to enter.")
		outputText("\n\nNear one of major briges is located merchant area with various smaller or larger places where all visitors can buy or sell various items. Among then two attracts most attention with first been largest stall here and other largest shop. On almost opposite side of village near other brige is located medium sized shop with sign indicating it govern local exchanges and transformation items market.")
		outputText("\n\nAt the island located on west end of He'Xin'Dao you see one of biggest buildings here. From sounds of weapon clashing it seems to be some kind of local arena.")

		addButton("Arena", ArenaScene,"soularena", "Go to arena")
	}
}

object ArenaScene: Scene("Town/arena") {
	override fun Display.doExecute() {
		outputText("Coming closer to the arena you see two muscular tigersharks standing on each side of the entrance, they only briefly glance at you the moment you pass by them. IA few a moments after you entered, a tall, slightly muscular male cat-morph approaches you. Most of its body is covered by armor yet two long tails waves behind him from time to time.")
		outputText("\n\n\"<i>Welcome to the Soul Arena. Don't start fights outside of the proper place or you will be thrown out. If you break any rules here you will be kicked out. Go ahead and pick an area where you want to train or instead go to the challenges area. Oh and fights in each section cost some spirit stones so be sure to have enough of them as we not run charity here,</i>\"")
		outputText(" without wasting time the nekomata overseer of this place explains to you all that you needed to know about the place and walks away.")

		addButton("Back",TownScene, "back")
	}
}
