package xta.text

import xta.game.PlayerCharacter

interface TextOutput {
	@JsName("selectSelf")
	fun selectSelf()

	@JsName("selecPerson")
	fun selectPerson(person: PlayerCharacter) {
		selectNpc(0, person)
	}

	// sorry for the naming
	@JsName("selectNpc")
	fun selectNpc(index: Int, npc: PlayerCharacter)

	@JsName("selectNpcs")
	fun selectNpcs(vararg npcs: PlayerCharacter) {
		for ((i, npc) in npcs.withIndex()) {
			selectNpc(i, npc)
		}
	}

	@JsName("clearOutput")
	fun clearOutput()

	@JsName("rawOutput")
	fun rawOutput(text: String)

	@JsName("outputText")
	fun outputText(text: String)
}
