package xta.game.creature.perks

import xta.game.stats.Stats

/*
 * Created by aimozg on 09.12.2021.
 */
object PerkLib {

	val Agility = mkbuffs(
		"Agility",
		"Agility",
		"Boosts armor points by a portion of your speed on light/medium armors."
	)
	// TODO deactivates if int drops <100
	val Archmage = mkbuffs(
		"Archmage",
		"Archmage",
		"Increases base spell strength by 30%, mana pool by 180 and lust bar by 45.",
		Stats.MANA_MAX_BASE to 180.0,
		Stats.LUST_MAX_BASE to 45.0,
		Stats.SPELL_POWER to 0.30
	)
	val BasicSpirituality = mkbuffs(
		"Basic Spirituality",
		"Basic Spirituality",
		"Increases maximum mana by 135.",
		Stats.MANA_MAX_BASE to 135.0
	)
	// TODO deactivates if int drops <60
	val Channeling = mkbuffs(
		"Channeling",
		"Channeling",
		"Increases base spell strength by 20% and base mana pool by 90.",
		Stats.MANA_MAX_BASE to 90.0,
		Stats.SPELL_POWER to 0.20
	)
	val CorruptedLibido = mkbuffs(
		"Corrupted Libido",
		"Corrupted Libido",
		"Reduces lust gain by 10%.",
		Stats.RESIST_LUST to 0.10
	)
	// TODO +50% bonus SF recovery (+100% on rest?)
	val DaoistCultivator = mkbuffs(
		"Daoist Cultivator",
		"Daoist Cultivator",
		"Allow you to train your soul cultivator mind to unleash soulskills with their apex power. (+20% soulskill/m.soulskill power, -10% soulskills cost, +50% base soulforce recovery multiplier)",
		Stats.SF_MAX_BASE to +25.0,
		Stats.SOULSKILL_POWER to +0.20,
		Stats.SOULSKILL_COST to -0.10
	)
	// TODO "Elemental Bolt" ability
	val ElementalBolt = mk(
		"Elemental Bolt",
		"Elemental Bolt",
		"Enable use of Elemental bolt. (would prevent decay of buff from building up elemental damage)"
	)
	val Fast = mkbuffs(
		"Fast",
		"Fast",
		"+25% to speed",
		Stats.SPE_MULT to 0.50
	)
	val Evade = mkbuffs(
		"Evade",
		"Evade",
		"Increases chances of evading enemy attacks. (+5% to evasion)",
		Stats.DODGE_ANY to +0.05
	)
	val EzekielBlessing = mkbuffs(
		"Ezekiel Blessing",
		"Ezekiel Blessing",
		"You've received the blessing of Ezekiel, enhancing your body and mind. +10 to all stats.",
		Stats.STR_BONUS to +10.0,
		Stats.TOU_BONUS to +10.0,
		Stats.SPE_BONUS to +10.0,
		Stats.INT_BONUS to +10.0,
		Stats.WIS_BONUS to +10.0,
		Stats.LIB_BONUS to +10.0,
		Stats.SENS to +10.0
	)
	// TODO deactivates if int drops < 75
	val GrandMage = mkbuffs(
		"Grand Mage",
		"Grand Mage",
		"Increases base spell strength by 20%, base mana pool by 135 and lust bar by 30.",
		Stats.MANA_MAX_BASE to +135.0,
		Stats.LUST_MAX_BASE to +30.0,
		Stats.SPELL_POWER to +0.20
	)
	// TODO mana recovery bonus
	val JobSorcerer = mkbuffs(
		"Job: Sorcerer",
		"Job: Sorcerer",
		"You've trained in magic combat.",
		Stats.INT_MULT to +0.05,
		Stats.MANA_MAX_BASE to +45.0,
		Stats.SPELL_POWER to +0.10
	)

	private fun mk(id:String, name:String, desc:String) = SimplePerk(id,name,desc)
	private fun mkbuffs(id: String, name: String, desc: String, vararg buffs: Pair<String, Double>) =
		SimplePerkWithBuffs(id,name,desc,*buffs)
}

