package xta.game.creature.body

import xta.net.serialization.JsonSerializable

class VaginaPart: JsonSerializable(){
	var type: VaginaType by property(VaginaType.HUMAN)
	var virgin by property(true)
}
