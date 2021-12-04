package xta.net.protocol.messages

/*
 * Created by aimozg on 02.12.2021.
 */
external interface ScreenJson {
	var sceneId:String
	var content:String
	var actions:Array<ActionJson>
}

external interface ActionJson {
	var actionId:String
	var label:String
	var hint:String?
	var disabled:Boolean?
}
