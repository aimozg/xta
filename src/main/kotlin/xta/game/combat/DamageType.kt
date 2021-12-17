package xta.game.combat

/*
 * Created by aimozg on 16.12.2021.
 */
enum class DamageType(
	val displayName: String
) {
	TRUE("true"),
	PHYSICAL("physical"),
	MAGICAL("magical"),
	FIRE("fire"),
	ICE("ice"),
	LIGHTNING("lightning"),
	DARKNESS("darkness")
}
