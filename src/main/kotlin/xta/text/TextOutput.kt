package xta.text

import xta.Player
import xta.game.PlayerCharacter

interface TextOutput {
	fun selectSelf()
	fun selectPerson(person: PlayerCharacter) {
		selectNpc(0, person)
	}
	fun selectPerson(person: Player) {
		selectNpc(0, person)
	}
	// sorry for the naming
	fun selectNpc(index:Int, npc:PlayerCharacter)
	fun selectNpc(index:Int, npc:Player) {
		selectNpc(index, npc.char)
	}
	fun selectNpcs(vararg npcs:PlayerCharacter) {
		for ((i, npc) in npcs.withIndex()) {
			selectNpc(i,npc)
		}
	}
	fun selectNpcs(vararg npcs:Player) {
		for ((i, npc) in npcs.withIndex()) {
			selectNpc(i,npc)
		}
	}
	fun clearOutput()
	fun rawOutput(text: String)
	fun outputText(text: String)
}
