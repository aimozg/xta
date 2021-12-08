package xta.text

import xta.Player

interface TextOutput {
	fun selectSelf()
	fun selectPerson(person: Player)
	fun clearOutput()
	fun rawOutput(text: String)
	fun outputText(text: String)
}
