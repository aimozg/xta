package xta.game.stats

external interface BuffJson {
	var tag:String
	var value: Double
	var text: String
	var rate: Int
	var ticks:Int
	var save: Boolean?
	var show: Boolean?
}
