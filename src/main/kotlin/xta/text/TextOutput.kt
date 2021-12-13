package xta.text

import xta.Player
import xta.game.PlayerCharacter

interface TextOutput {
	fun selectSelf()
	fun selectPerson(person: PlayerCharacter)
	fun selectPerson(person: Player) {
		selectPerson(person.char)
	}
	fun clearOutput()
	fun rawOutput(text: String)
	fun outputText(text: String)
}
