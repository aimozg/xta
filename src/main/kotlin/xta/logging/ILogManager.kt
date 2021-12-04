package xta.logging

/*
 * Created by aimozg on 01.12.2021.
 */
interface ILogManager {
	fun getLogger(id:String):Logger
	fun setLevel(id:String, level:Logger.Level)
	fun setLevelForMany(prefix:String, level:Logger.Level)
}
