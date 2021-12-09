package xta.game.creature.perks

import xta.game.stats.Stats

/*
 * Created by aimozg on 09.12.2021.
 */
object PerkLib {

	val BasicSpirituality = mkbuffs(
		"Basic Spirituality",
		"Basic Spirituality",
		"Increases maximum mana by 135.",
		Stats.MANA_MAX_BASE to 135.0
	)
	val Fast = mkbuffs(
		"Fast",
		"Fast",
		"+25% to speed",
		Stats.SPE_MULT to 0.50
	)

	private fun mk(id:String, name:String, desc:String) = SimplePerk(id,name,desc)
	private fun mkbuffs(id: String, name: String, desc: String, vararg buffs: Pair<String, Double>) =
		SimplePerkWithBuffs(id,name,desc,*buffs)
}

