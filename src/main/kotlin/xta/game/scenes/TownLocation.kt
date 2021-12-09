package xta.game.scenes

import xta.game.GameLocation
import xta.game.Scene

/*
 * Created by aimozg on 02.12.2021.
 */
object TownLocation:GameLocation("Town") {
	val main: Scene = scene("main") {
		outputText("<b>This is placeholder text.</b>\n\n")
		outputText("He'Xin'Dao is a large village connected and assembled between many smaller islands in the middle of large river that flows from the east to the west. Aside from bridges connecting each of the islands together, two larger ones connects them as a whole to both sides of the river serving as the only points of access to the village.  The village is strategically laid out, preventing anyone from entering by swimming directly from the river to any of the islands, forcing people to use the bridges if they wish to enter.")
		outputText("\n\nNear one of major briges is located merchant area with various smaller or larger places where all visitors can buy or sell various items. Among then two attracts most attention with first been largest stall here and other largest shop. On almost opposite side of village near other brige is located medium sized shop with sign indicating it govern local exchanges and transformation items market.")
		outputText("\n\nAt the island located on west end of He'Xin'Dao you see one of biggest buildings here. From sounds of weapon clashing it seems to be some kind of local arena.")

		addButton("Rest", healScene, "Recover your HP and other stats")
		addButton("Arena", ArenaLocation.enterScene, "Go to arena")
	}

	val healScene = scene("heal") {
		outputText("You evoke the debug powers of pre-alpha game build, and your HP and other stats are restored!")
		val pc = player.char
		pc.hp = pc.maxHp()
		pc.lust = pc.minLust()
		pc.wrath = 0
		pc.fatigue = 0
		pc.mana = pc.maxMana()
		pc.soulforce = pc.maxSoulforce()
		player.sendCharUpdate()

		addButton("Back", main)
	}
}

