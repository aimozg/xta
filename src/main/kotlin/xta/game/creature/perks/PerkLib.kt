package xta.game.creature.perks

import xta.game.stats.StatMeta
import xta.game.stats.Stats

/*
 * Created by aimozg on 09.12.2021.
 */
object PerkLib {

	val Agility = mk(
		"Agility", "Agility",
		"Boosts armor points by a portion of your speed on light/medium armors."
	)

	// TODO deactivates if int drops <100
	val Archmage = mk(
		"Archmage", "Archmage",
		"Increases base spell strength by 30%, mana pool by 180 and lust bar by 45.",
		Stats.MANA_MAX_BASE to 180.0,
		Stats.LUST_MAX_BASE to 45.0,
		Stats.SPELL_POWER to 0.30
	)
	val BasicEndurance = mk(
		"Basic Endurance", "Basic Endurance",
		"Increases maximum fatigue by 30.",
		Stats.FATIGUE_MAX_BASE to +30.0
	)
	val BasicSelfControl = mk(
		"Basic Self-Control", "Basic Self-Control",
		"Increases maximum lust by 45.",
		Stats.LUST_MAX_BASE to +45.0
	)
	val BasicSpirituality = mk(
		"Basic Spirituality", "Basic Spirituality",
		"Increases maximum mana by 135.",
		Stats.MANA_MAX_BASE to 135.0
	)
	val BasicTranquillness = mk(
		"Basic Tranquilness", "Basic Tranquilness",
		"Increases maximum wrath by 75.",
		Stats.WRATH_MAX_BASE to +75.0
	)

	// TODO deactivates if int drops <60
	val Channeling = mk(
		"Channeling", "Channeling",
		"Increases base spell strength by 20% and base mana pool by 90.",
		Stats.MANA_MAX_BASE to 90.0,
		Stats.SPELL_POWER to 0.20
	)
	val CorruptedLibido = mk(
		"Corrupted Libido", "Corrupted Libido",
		"Reduces lust gain by 10%.",
		Stats.RESIST_LUST to 0.10
	)

	// TODO +50% bonus SF recovery (+100% on rest?)
	val DaoistCultivator = mk(
		"Daoist Cultivator", "Daoist Cultivator",
		"Allow you to train your soul cultivator mind to unleash soulskills with their apex power. (+20% soulskill/m.soulskill power, -10% soulskills cost, +50% base soulforce recovery multiplier)",
		Stats.SF_MAX_BASE to +25.0,
		Stats.SOULSKILL_POWER to +0.20,
		Stats.SOULSKILL_COST to -0.10
	)

	// TODO "Elemental Bolt" ability
	val ElementalBolt = mk(
		"Elemental Bolt", "Elemental Bolt",
		"Enable use of Elemental bolt. (would prevent decay of buff from building up elemental damage)"
	)
	val Fast = mk(
		"Fast", "Fast",
		"+25% to speed",
		Stats.SPE_MULT to 0.50
	)
	val Evade = mk(
		"Evade", "Evade",
		"Increases chances of evading enemy attacks. (+5% to evasion)",
		Stats.DODGE_ANY to +0.05
	)
	val EzekielBlessing = mk(
		"Ezekiel Blessing", "Ezekiel Blessing",
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
	val GrandMage = mk(
		"Grand Mage", "Grand Mage",
		"Increases base spell strength by 20%, base mana pool by 135 and lust bar by 30.",
		Stats.MANA_MAX_BASE to +135.0,
		Stats.LUST_MAX_BASE to +30.0,
		Stats.SPELL_POWER to +0.20
	)
	val JobBeastWarrior = mk(
		"Job: Beast Warrior", "Job: Beast Warrior",
		"You've trained to use of your own body and natural weapons to their limits in fights.",
		Stats.STR_MULT to 0.05,
		Stats.TOU_MULT to 0.05,
		Stats.SPE_MULT to 0.05,
		Stats.INT_MULT to -0.05,
		Stats.WIS_MULT to -0.05,
		Stats.WRATH_MAX_BASE to +100.0
	)
	val JobGuardian = mk(
		"Job: Guardian", "Job: Guardian",
		"You've trained in defensive combat.",
		Stats.TOU_MULT to +0.05,
		Stats.HP_MAX_BASE to +120.0
	)
	val JobLeader = mk(
		"Job: Leader", "Job: Leader",
		"You've trained in ways to better lead combat companions or command minions.",
		Stats.INT_MULT to +0.05,
		Stats.WIS_MULT to +0.05,
		Stats.LIB_MULT to -0.05,
	)
	val JobRanger = mk(
		"Job: Ranger", "Job: Ranger",
		"You've trained in ranged combat.",
		Stats.SPE_MULT to +0.05,
		Stats.FATIGUE_MAX_BASE to +5.0,
		Stats.DAMAGE_RANGED_MULT to +0.05
	)

	// TODO bonus tease damage
	val JobSeducer = mk(
		"Job: Seducer", "Job: Seducer",
		"You've trained the art of seduction.",
		Stats.LIB_MULT to +0.05,
		Stats.LUST_MAX_BASE to +30.0,
	)

	// TODO mana recovery bonus
	val JobSorcerer = mk(
		"Job: Sorcerer", "Job: Sorcerer",
		"You've trained in magic combat.",
		Stats.INT_MULT to +0.05,
		Stats.MANA_MAX_BASE to +45.0,
		Stats.SPELL_POWER to +0.10
	)

	// TODO max hunger +20
	// TODO SF recovery bonus
	val JobSoulCultivator = mk(
		"Job: Soul Cultivator", "Job: Soul Cultivator",
		"You've cultivated powers of your soul.",
		Stats.WIS_MULT to +0.05,
		Stats.SF_MAX_BASE to +50.0,
	)
	val ResistanceI = mk(
		"Resistance I", "Resistance I",
		"Reduces lust gain by 5%.",
		Stats.RESIST_LUST to +0.05
	)
	val ResistanceII = mk(
		"Resistance II", "Resistance II",
		"Reduces lust gain by 5%.",
		Stats.RESIST_LUST to +0.05
	)
	val ResistanceIII = mk(
		"Resistance III", "Resistance III",
		"Reduces lust gain by 5%.",
		Stats.RESIST_LUST to +0.05
	)
	val ResistanceIV = mk(
		"Resistance IV", "Resistance IV",
		"Reduces lust gain by 5%.",
		Stats.RESIST_LUST to +0.05
	)
	val ResistanceV = mk(
		"Resistance V", "Resistance V",
		"Reduces lust gain by 5%.",
		Stats.RESIST_LUST to +0.05
	)
	val ResistanceVI = mk(
		"Resistance VI", "Resistance VI",
		"Reduces lust gain by 5%.",
		Stats.RESIST_LUST to +0.05
	)
	val UnlockArdor = mk(
		"Unlock: Ardor", "Unlock: Ardor (1st Stage)",
		"Unlocking ardor grants additional 3 Lust on each lvl-up (retroactive effect).",
		Stats.LUST_MAX_PERLEVEL to +3.0
	)
	val UnlockArdor2ndStage = mk(
		"Unlock: Ardor (2nd Stage)", "Unlock: Ardor (2nd Stage)",
		"Unlocking ardor grants additional 3 Lust on each lvl-up (retroactive effect).",
		Stats.LUST_MAX_PERLEVEL to +3.0
	)
	val UnlockArdor3rdStage = mk(
		"Unlock: Ardor (3rd Stage)", "Unlock: Ardor (3rd Stage)",
		"Unlocking ardor grants additional 3 Lust on each lvl-up (retroactive effect).",
		Stats.LUST_MAX_PERLEVEL to +3.0
	)
	val UnlockArdor4thStage = mk(
		"Unlock: Ardor (4th Stage)", "Unlock: Ardor (4th Stage)",
		"Unlocking ardor grants additional 3 Lust on each lvl-up (retroactive effect).",
		Stats.LUST_MAX_PERLEVEL to +3.0
	)
	val UnlockBody = mk(
		"Unlock: Body", "Unlock: Body (1st Stage)",
		"Unlocking body potential grants additional 60 HP on each lvl-up (retroactive effect).",
		Stats.HP_MAX_PERLEVEL to +60.0
	)
	val UnlockBody2ndStage = mk(
		"Unlock: Body (2nd Stage)", "Unlock: Body (2nd Stage)",
		"Unlocking body potential grants additional 60 HP on each lvl-up (retroactive effect).",
		Stats.HP_MAX_PERLEVEL to +60.0
	)
	val UnlockBody3rdStage = mk(
		"Unlock: Body (3rd Stage)", "Unlock: Body (3rd Stage)",
		"Unlocking body potential grants additional 60 HP on each lvl-up (retroactive effect).",
		Stats.HP_MAX_PERLEVEL to +60.0
	)
	val UnlockBody4thStage = mk(
		"Unlock: Body (4th Stage)", "Unlock: Body (4th Stage)",
		"Unlocking body potential grants additional 60 HP on each lvl-up (retroactive effect).",
		Stats.HP_MAX_PERLEVEL to +60.0
	)
	val UnlockEndurance = mk(
		"Unlock: Endurance", "Unlock: Endurance (1st Stage)",
		"Unlocking innate endurance grants additional 5 Fatigue on each lvl-up (retroactive effect).",
		Stats.FATIGUE_MAX_PERLEVEL to +5.0
	)
	val UnlockEndurance2ndStage = mk(
		"Unlock: Endurance (2nd Stage)", "Unlock: Endurance (2nd Stage)",
		"Unlocking innate endurance grants additional 5 Fatigue on each lvl-up (retroactive effect).",
		Stats.FATIGUE_MAX_PERLEVEL to +5.0
	)
	val UnlockEndurance3rdStage = mk(
		"Unlock: Endurance (3rd Stage)", "Unlock: Endurance (3rd Stage)",
		"Unlocking innate endurance grants additional 5 Fatigue on each lvl-up (retroactive effect).",
		Stats.FATIGUE_MAX_PERLEVEL to +5.0
	)
	val UnlockEndurance4thStage = mk(
		"Unlock: Endurance (4th Stage)", "Unlock: Endurance (4th Stage)",
		"Unlocking innate endurance grants additional 5 Fatigue on each lvl-up (retroactive effect).",
		Stats.FATIGUE_MAX_PERLEVEL to +5.0
	)
	val UnlockId = mk(
		"Unlock: Id", "Unlock: Id (1st Stage)",
		"Unlocking id grants additional 5 Wrath on each lvl-up (retroactive effect).",
		Stats.WRATH_MAX_PERLEVEL to +5.0
	)
	val UnlockId2ndStage = mk(
		"Unlock: Id (2nd Stage)", "Unlock: Id (2nd Stage)",
		"Unlocking id grants additional 5 Wrath on each lvl-up (retroactive effect).",
		Stats.WRATH_MAX_PERLEVEL to +5.0
	)
	val UnlockId3rdStage = mk(
		"Unlock: Id (3rd Stage)", "Unlock: Id (3rd Stage)",
		"Unlocking id grants additional 5 Wrath on each lvl-up (retroactive effect).",
		Stats.WRATH_MAX_PERLEVEL to +5.0
	)
	val UnlockId4thStage = mk(
		"Unlock: Id (4th Stage)", "Unlock: Id (4th Stage)",
		"Unlocking id grants additional 5 Wrath on each lvl-up (retroactive effect).",
		Stats.WRATH_MAX_PERLEVEL to +5.0
	)
	val UnlockSpirit = mk(
		"Unlock: Spirit", "Unlock: Spirit (1st Stage)",
		"Unlocking spirit potential grants additional 5 Soulforce on each lvl-up (retroactive effect).",
		Stats.SF_MAX_PERLEVEL to 5.0
	)
	val UnlockSpirit2ndStage = mk(
		"Unlock: Spirit (2nd Stage)", "Unlock: Spirit (2nd Stage)",
		"Unlocking spirit potential grants additional 5 Soulforce Spirit each lvl-up (retroactive effect).",
		Stats.SF_MAX_PERLEVEL to 5.0
	)
	val UnlockSpirit3rdStage = mk(
		"Unlock: Spirit (3rd Stage)", "Unlock: Spirit (3rd Stage)",
		"Unlocking spirit potential grants additional 5 Soulforce on each lvl-up (retroactive effect).",
		Stats.SF_MAX_PERLEVEL to 5.0
	)
	val UnlockSpirit4thStage = mk(
		"Unlock: Spirit (4th Stage)", "Unlock: Spirit (4th Stage)",
		"Unlocking spirit potential grants additional 5 Soulforce on each lvl-up (retroactive effect).",
		Stats.SF_MAX_PERLEVEL to 5.0
	)
	val UnlockForce = mk(
		"Unlock: Force", "Unlock: Force (1st Stage)",
		"Unlocking magic potential grants additional 10 Mana on each lvl-up (retroactive effect).",
		Stats.MANA_MAX_PERLEVEL to 10.0
	)
	val UnlockForce2ndStage = mk(
		"Unlock: Force (2nd Stage)", "Unlock: Force (2nd Stage)",
		"Unlocking magic potential grants additional 10 Mana on each lvl-up (retroactive effect).",
		Stats.MANA_MAX_PERLEVEL to 10.0
	)
	val UnlockForce3rdStage = mk(
		"Unlock: Force (3rd Stage)", "Unlock: Force (3rd Stage)",
		"Unlocking magic potential grants additional 10 Mana on each lvl-up (retroactive effect).",
		Stats.MANA_MAX_PERLEVEL to 10.0
	)
	val UnlockForce4thStage = mk(
		"Unlock: Force (4th Stage)", "Unlock: Force (4th Stage)",
		"Unlocking magic potential grants additional 10 Mana on each lvl-up (retroactive effect).",
		Stats.MANA_MAX_PERLEVEL to 10.0
	)

	private fun mk(id: String, name: String, desc: String, vararg buffs: Pair<StatMeta, Double>) =
		if (buffs.isEmpty()) SimplePerk(id, name, desc)
		else SimplePerkWithBuffs(id, name, desc, *buffs)
}

