package xta.game.creature.perks

import xta.game.stats.StatMeta
import xta.game.stats.Stats

/*
 * Created by aimozg on 09.12.2021.
 */
@Suppress("unused")
object PerkLib {

	val Agility = mk(
		"Agility", "Agility",
		"Boosts armor points by a portion of your speed on light/medium armors."
	)
	val ArcanePool1 = mk(
		"Arcane Pool I", "Arcane Pool I",
		"Extra mana per intelligence and wisdom.",
		Stats.MANA_MAX_PERINT to +3.0,
		Stats.MANA_MAX_PERWIS to +3.0,
	)
	val ArcanePool2 = mk(
		"Arcane Pool II", "Arcane Pool II",
		"Extra mana per intelligence and wisdom.",
		Stats.MANA_MAX_PERINT to +3.0,
		Stats.MANA_MAX_PERWIS to +3.0,
	)
	val ArcanePool3 = mk(
		"Arcane Pool III", "Arcane Pool III",
		"Extra mana per intelligence and wisdom.",
		Stats.MANA_MAX_PERINT to +3.0,
		Stats.MANA_MAX_PERWIS to +3.0,
	)
	val ArcanePool4 = mk(
		"Arcane Pool IV", "Arcane Pool IV",
		"Extra mana per intelligence and wisdom.",
		Stats.MANA_MAX_PERINT to +3.0,
		Stats.MANA_MAX_PERWIS to +3.0,
	)
	val ArcanePool5 = mk(
		"Arcane Pool V", "Arcane Pool V",
		"Extra mana per intelligence and wisdom.",
		Stats.MANA_MAX_PERINT to +3.0,
		Stats.MANA_MAX_PERWIS to +3.0,
	)
	val ArcanePool6 = mk(
		"Arcane Pool VI", "Arcane Pool VI",
		"Extra mana per intelligence and wisdom.",
		Stats.MANA_MAX_PERINT to +3.0,
		Stats.MANA_MAX_PERWIS to +3.0,
	)
	val ArcaneRegeneration1 = mk(
		"Arcane Regeneration (Minor)", "Arcane Regeneration (Minor)",
		"Increase mana recovery and max mana.",
		Stats.MANA_MAX_MULT to +0.05,
		Stats.MANA_REGEN to +10.0
	)
	val ArcaneRegeneration2 = mk(
		"Arcane Regeneration (Major)", "Arcane Regeneration (Major)",
		"Increase mana recovery and max mana.",
		Stats.MANA_MAX_MULT to +0.10,
		Stats.MANA_REGEN to +20.0
	)
	val ArcaneRegeneration3 = mk(
		"Arcane Regeneration (Epic)", "Arcane Regeneration (Epic)",
		"Increase mana recovery and max mana.",
		Stats.MANA_MAX_MULT to +0.15,
		Stats.MANA_REGEN to +30.0
	)
	val ArcaneRegeneration4 = mk(
		"Arcane Regeneration (Legendary)", "Arcane Regeneration (Legendary)",
		"Increase mana recovery and max mana.",
		Stats.MANA_MAX_MULT to +0.20,
		Stats.MANA_REGEN to +40.0
	)
	val ArcaneRegeneration5 = mk(
		"Arcane Regeneration (Mythical)", "Arcane Regeneration (Mythical)",
		"Increase mana recovery and max mana.",
		Stats.MANA_MAX_MULT to +0.25,
		Stats.MANA_REGEN to +50.0
	)
	val ArchersStamina1 = mk(
		"Archer's Stamina I", "Archer's Stamina I",
		"Extra fatigue per point of speed.",
		Stats.FATIGUE_MAX_PERSPE to +1.0,
	)
	val ArchersStamina2 = mk(
		"Archer's Stamina II", "Archer's Stamina II",
		"Extra fatigue per point of speed.",
		Stats.FATIGUE_MAX_PERSPE to +1.0,
	)
	val ArchersStamina3 = mk(
		"Archer's Stamina III", "Archer's Stamina III",
		"Extra fatigue per point of speed.",
		Stats.FATIGUE_MAX_PERSPE to +1.0,
	)
	val ArchersStamina4 = mk(
		"Archer's Stamina IV", "Archer's Stamina IV",
		"Extra fatigue per point of speed.",
		Stats.FATIGUE_MAX_PERSPE to +1.0,
	)
	val ArchersStamina5 = mk(
		"Archer's Stamina V", "Archer's Stamina V",
		"Extra fatigue per point of speed.",
		Stats.FATIGUE_MAX_PERSPE to +1.0,
	)
	val ArchersStamina6 = mk(
		"Archer's Stamina VI", "Archer's Stamina VI",
		"Extra fatigue per point of speed.",
		Stats.FATIGUE_MAX_PERSPE to +1.0,
	)

	// TODO deactivates if int drops <100
	val Archmage = mk(
		"Archmage", "Archmage",
		"Increases base spell strength by 30%, mana pool by 180 and lust bar by 45.",
		Stats.MANA_MAX_BASE to 180.0,
		Stats.LUST_MAX_BASE to 45.0,
		Stats.SPELL_POWER to 0.30
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
	val Endurance1 = mk(
		"Basic Endurance", "Basic Endurance",
		"Increases maximum fatigue.",
		Stats.FATIGUE_MAX_BASE to +30.0
	)
	val Endurance2 = mk(
		"Half-step-to Improved Endurance", "Half-step-to Improved Endurance",
		"Increases maximum fatigue.",
		Stats.FATIGUE_MAX_BASE to +50.0
	)
	val Endurance3 = mk(
		"Improved Endurance", "Improved Endurance",
		"Increases maximum fatigue.",
		Stats.FATIGUE_MAX_BASE to +80.0
	)
	val Endurance4 = mk(
		"Half-step-to Advanced Endurance", "Half-step-to Advanced Endurance",
		"Increases maximum fatigue.",
		Stats.FATIGUE_MAX_BASE to +120.0
	)
	val Endurance5 = mk(
		"Advanced Endurance", "Advanced Endurance",
		"Increases maximum fatigue.",
		Stats.FATIGUE_MAX_BASE to +200.0
	)
	val Endurance6 = mk(
		"Half-step-to Superior Endurance", "Half-step-to Superior Endurance",
		"Increases maximum fatigue.",
		Stats.FATIGUE_MAX_BASE to +320.0
	)
	val Endurance7 = mk(
		"Superior Endurance", "Superior Endurance",
		"Increases maximum fatigue.",
		Stats.FATIGUE_MAX_BASE to +500.0
	)
	val Endurance8 = mk(
		"Half-step-to Peerless Endurance", "Half-step-to Peerless Endurance",
		"Increases maximum fatigue.",
		Stats.FATIGUE_MAX_BASE to +700.0
	)
	val Endurance9 = mk(
		"Peerless Endurance", "Peerless Endurance",
		"Increases maximum fatigue.",
		Stats.FATIGUE_MAX_BASE to +1000.0
	)
	val Endurance10 = mk(
		"Half-step-to Inhuman Endurance", "Half-step-to Inhuman Endurance",
		"Increases maximum fatigue.",
		Stats.FATIGUE_MAX_BASE to +1500.0
	)
	val Endurance11 = mk(
		"Inhuman Endurance", "Inhuman Endurance",
		"Increases maximum fatigue.",
		Stats.FATIGUE_MAX_BASE to +2000.0
	)
	val Endurance12 = mk(
		"Half-step-to Epic Endurance", "Half-step-to Epic Endurance",
		"Increases maximum fatigue.",
		Stats.FATIGUE_MAX_BASE to +3000.0
	)
	val Endurance13 = mk(
		"Epic Endurance", "Epic Endurance",
		"Increases maximum fatigue.",
		Stats.FATIGUE_MAX_BASE to +4500.0
	)
	val Endurance14 = mk(
		"Half-step-to Legendary Endurance", "Half-step-to Legendary Endurance",
		"Increases maximum fatigue.",
		Stats.FATIGUE_MAX_BASE to +7000.0
	)
	val Endurance15 = mk(
		"Legendary Endurance", "Legendary Endurance",
		"Increases maximum fatigue.",
		Stats.FATIGUE_MAX_BASE to +10000.0
	)
	val Endurance16 = mk(
		"Half-step-to Mythical Endurance", "Half-step-to Mythical Endurance",
		"Increases maximum fatigue.",
		Stats.FATIGUE_MAX_BASE to +15000.0
	)
	val Endurance17 = mk(
		"Mythical Endurance", "Mythical Endurance",
		"Increases maximum fatigue.",
		Stats.FATIGUE_MAX_BASE to +20000.0
	)
	val Evade1 = mk(
		"Evade", "Evade",
		"Increases chances of evading enemy attacks.",
		Stats.DODGE_ANY to +0.05
	)
	val Evade2 = mk(
		"Improved Evade", "Improved Evade",
		"Increases chances of evading enemy attacks.",
		Stats.DODGE_ANY to +0.10
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
	val Fast = mk(
		"Fast", "Fast",
		"+25% to speed",
		Stats.SPE_MULT to 0.50
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
	val Resistance1 = mk(
		"Resistance I", "Resistance I",
		"Reduces lust gain by 5%.",
		Stats.RESIST_LUST to +0.05
	)
	val Resistance2 = mk(
		"Resistance II", "Resistance II",
		"Reduces lust gain by 5%.",
		Stats.RESIST_LUST to +0.05
	)
	val Resistance3 = mk(
		"Resistance III", "Resistance III",
		"Reduces lust gain by 5%.",
		Stats.RESIST_LUST to +0.05
	)
	val Resistance4 = mk(
		"Resistance IV", "Resistance IV",
		"Reduces lust gain by 5%.",
		Stats.RESIST_LUST to +0.05
	)
	val Resistance5 = mk(
		"Resistance V", "Resistance V",
		"Reduces lust gain by 5%.",
		Stats.RESIST_LUST to +0.05
	)
	val Resistance6 = mk(
		"Resistance VI", "Resistance VI",
		"Reduces lust gain by 5%.",
		Stats.RESIST_LUST to +0.05
	)
	val SelfControl1 = mk(
		"Basic Self-Control", "Basic Self-Control",
		"Increases maximum lust.",
		Stats.LUST_MAX_BASE to +45.0
	)
	val SelfControl2 = mk(
		"Half-step-to Improved Self-Control", "Half-step-to Improved Self-Control",
		"Increases maximum lust.",
		Stats.LUST_MAX_BASE to +75.0
	)
	val SelfControl3 = mk(
		"Improved Self-Control", "Improved Self-Control",
		"Increases maximum lust.",
		Stats.LUST_MAX_BASE to +120.0
	)
	val SelfControl4 = mk(
		"Half-step-to Advanced Self-Control", "Half-step-to Advanced Self-Control",
		"Increases maximum lust.",
		Stats.LUST_MAX_BASE to +180.0
	)
	val SelfControl5 = mk(
		"Advanced Self-Control", "Advanced Self-Control",
		"Increases maximum lust.",
		Stats.LUST_MAX_BASE to +300.0
	)
	val SelfControl6 = mk(
		"Half-step-to Superior Self-Control", "Half-step-to Superior Self-Control",
		"Increases maximum lust.",
		Stats.LUST_MAX_BASE to +480.0
	)
	val SelfControl7 = mk(
		"Superior Self-Control", "Superior Self-Control",
		"Increases maximum lust.",
		Stats.LUST_MAX_BASE to +750.0
	)
	val SelfControl8 = mk(
		"Half-step-to Peerless Self-Control", "Half-step-to Peerless Self-Control",
		"Increases maximum lust.",
		Stats.LUST_MAX_BASE to +1050.0
	)
	val SelfControl9 = mk(
		"Peerless Self-Control", "Peerless Self-Control",
		"Increases maximum lust.",
		Stats.LUST_MAX_BASE to +1500.0
	)
	val SelfControl10 = mk(
		"Half-step-to Inhuman Self-Control", "Half-step-to Inhuman Self-Control",
		"Increases maximum lust.",
		Stats.LUST_MAX_BASE to +2250.0
	)
	val SelfControl11 = mk(
		"Inhuman Self-Control", "Inhuman Self-Control",
		"Increases maximum lust.",
		Stats.LUST_MAX_BASE to +3000.0
	)
	val SelfControl12 = mk(
		"Half-step-to Epic Self-Control", "Half-step-to Epic Self-Control",
		"Increases maximum lust.",
		Stats.LUST_MAX_BASE to +4500.0
	)
	val SelfControl13 = mk(
		"Epic Self-Control", "Epic Self-Control",
		"Increases maximum lust.",
		Stats.LUST_MAX_BASE to +6750.0
	)
	val SelfControl14 = mk(
		"Half-step-to Legendary Self-Control", "Half-step-to Legendary Self-Control",
		"Increases maximum lust.",
		Stats.LUST_MAX_BASE to +10500.0
	)
	val SelfControl15 = mk(
		"Legendary Self-Control", "Legendary Self-Control",
		"Increases maximum lust.",
		Stats.LUST_MAX_BASE to +15000.0
	)
	val SelfControl16 = mk(
		"Half-step-to Mythical Self-Control", "Half-step-to Mythical Self-Control",
		"Increases maximum lust.",
		Stats.LUST_MAX_BASE to +22500.0
	)
	val SelfControl17 = mk(
		"Mythical Self-Control", "Mythical Self-Control",
		"Increases maximum lust.",
		Stats.LUST_MAX_BASE to +30000.0
	)
	val Spirituality1 = mk(
		"Basic Spirituality", "Basic Spirituality",
		"Increases maximum mana.",
		Stats.MANA_MAX_BASE to 135.0
	)
	val Spirituality2 = mk(
		"Half-step-to Improved Spirituality", "Half-step-to Improved Spirituality",
		"Increases maximum mana.",
		Stats.MANA_MAX_BASE to 225.0
	)
	val Spirituality3 = mk(
		"Improved Spirituality", "Improved Spirituality",
		"Increases maximum mana.",
		Stats.MANA_MAX_BASE to 360.0
	)
	val Spirituality4 = mk(
		"Half-step-to Advanced Spirituality", "Half-step-to Advanced Spirituality",
		"Increases maximum mana.",
		Stats.MANA_MAX_BASE to 540.0
	)
	val Spirituality5 = mk(
		"Advanced Spirituality", "Advanced Spirituality",
		"Increases maximum mana.",
		Stats.MANA_MAX_BASE to 900.0
	)
	val Spirituality6 = mk(
		"Half-step-to Superior Spirituality", "Half-step-to Superior Spirituality",
		"Increases maximum mana.",
		Stats.MANA_MAX_BASE to 1440.0
	)
	val Spirituality7 = mk(
		"Superior Spirituality", "Superior Spirituality",
		"Increases maximum mana.",
		Stats.MANA_MAX_BASE to 2250.0
	)
	val Spirituality8 = mk(
		"Half-step-to Peerless Spirituality", "Half-step-to Peerless Spirituality",
		"Increases maximum mana.",
		Stats.MANA_MAX_BASE to 3150.0
	)
	val Spirituality9 = mk(
		"Peerless Spirituality", "Peerless Spirituality",
		"Increases maximum mana.",
		Stats.MANA_MAX_BASE to 4500.0
	)
	val Spirituality10 = mk(
		"Half-step-to Inhuman Spirituality", "Half-step-to Inhuman Spirituality",
		"Increases maximum mana.",
		Stats.MANA_MAX_BASE to 6750.0
	)
	val Spirituality11 = mk(
		"Inhuman Spirituality", "Inhuman Spirituality",
		"Increases maximum mana.",
		Stats.MANA_MAX_BASE to 9000.0
	)
	val Spirituality12 = mk(
		"Half-step-to Epic Spirituality", "Half-step-to Epic Spirituality",
		"Increases maximum mana.",
		Stats.MANA_MAX_BASE to 13500.0
	)
	val Spirituality13 = mk(
		"Epic Spirituality", "Epic Spirituality",
		"Increases maximum mana.",
		Stats.MANA_MAX_BASE to 20250.0
	)
	val Spirituality14 = mk(
		"Half-step-to Legendary Spirituality", "Half-step-to Legendary Spirituality",
		"Increases maximum mana.",
		Stats.MANA_MAX_BASE to 31500.0
	)
	val Spirituality15 = mk(
		"Legendary Spirituality", "Legendary Spirituality",
		"Increases maximum mana.",
		Stats.MANA_MAX_BASE to 45000.0
	)
	val Spirituality16 = mk(
		"Half-step-to Mythical Spirituality", "Half-step-to Mythical Spirituality",
		"Increases maximum mana.",
		Stats.MANA_MAX_BASE to 67500.0
	)
	val Spirituality17 = mk(
		"Mythical Spirituality", "Mythical Spirituality",
		"Increases maximum mana.",
		Stats.MANA_MAX_BASE to 90000.0
	)
	val Tranquillness1 = mk(
		"Basic Tranquilness", "Basic Tranquilness",
		"Increases maximum wrath.",
		Stats.WRATH_MAX_BASE to +75.0
	)
	val Tranquillness2 = mk(
		"Half-step-to Improved Tranquilness", "Half-step-to Improved Tranquilness",
		"Increases maximum wrath.",
		Stats.WRATH_MAX_BASE to +75.0
	)
	val Tranquillness3 = mk(
		"Improved Tranquilness", "Improved Tranquilness",
		"Increases maximum wrath.",
		Stats.WRATH_MAX_BASE to +200.0
	)
	val Tranquillness4 = mk(
		"Half-step-to Advanced Tranquilness", "Half-step-to Advanced Tranquilness",
		"Increases maximum wrath.",
		Stats.WRATH_MAX_BASE to +300.0
	)
	val Tranquillness5 = mk(
		"Advanced Tranquilness", "Advanced Tranquilness",
		"Increases maximum wrath.",
		Stats.WRATH_MAX_BASE to +500.0
	)
	val Tranquillness6 = mk(
		"Half-step-to Superior Tranquilness", "Half-step-to Superior Tranquilness",
		"Increases maximum wrath.",
		Stats.WRATH_MAX_BASE to +800.0
	)
	val Tranquillness7 = mk(
		"Superior Tranquilness", "Superior Tranquilness",
		"Increases maximum wrath.",
		Stats.WRATH_MAX_BASE to +1250.0
	)
	val Tranquillness8 = mk(
		"Half-step-to Peerless Tranquilness", "Half-step-to Peerless Tranquilness",
		"Increases maximum wrath.",
		Stats.WRATH_MAX_BASE to +1750.0
	)
	val Tranquillness9 = mk(
		"Peerless Tranquilness", "Peerless Tranquilness",
		"Increases maximum wrath.",
		Stats.WRATH_MAX_BASE to +2500.0
	)
	val Tranquillness10 = mk(
		"Half-step-to Inhuman Tranquilness", "Half-step-to Inhuman Tranquilness",
		"Increases maximum wrath.",
		Stats.WRATH_MAX_BASE to +3750.0
	)
	val Tranquillness11 = mk(
		"Inhuman Tranquilness", "Inhuman Tranquilness",
		"Increases maximum wrath.",
		Stats.WRATH_MAX_BASE to +5000.0
	)
	val Tranquillness12 = mk(
		"Half-step-to Epic Tranquilness", "Half-step-to Epic Tranquilness",
		"Increases maximum wrath.",
		Stats.WRATH_MAX_BASE to +7500.0
	)
	val Tranquillness13 = mk(
		"Epic Tranquilness", "Epic Tranquilness",
		"Increases maximum wrath.",
		Stats.WRATH_MAX_BASE to +11250.0
	)
	val Tranquillness14 = mk(
		"Half-step-to Legendary Tranquilness", "Half-step-to Legendary Tranquilness",
		"Increases maximum wrath.",
		Stats.WRATH_MAX_BASE to +17500.0
	)
	val Tranquillness15 = mk(
		"Legendary Tranquilness", "Legendary Tranquilness",
		"Increases maximum wrath.",
		Stats.WRATH_MAX_BASE to +25000.0
	)
	val Tranquillness16 = mk(
		"Half-step-to Mythical Tranquilness", "Half-step-to Mythical Tranquilness",
		"Increases maximum wrath.",
		Stats.WRATH_MAX_BASE to +37500.0
	)
	val Tranquillness17 = mk(
		"Mythical Tranquilness", "Mythical Tranquilness",
		"Increases maximum wrath.",
		Stats.WRATH_MAX_BASE to +50000.0
	)
	val UnlockArdor1 = mk(
		"Unlock: Ardor", "Unlock: Ardor (1st Stage)",
		"Unlocking ardor grants additional 3 Lust on each lvl-up (retroactive effect).",
		Stats.LUST_MAX_PERLEVEL to +3.0
	)
	val UnlockArdor2 = mk(
		"Unlock: Ardor (2nd Stage)", "Unlock: Ardor (2nd Stage)",
		"Unlocking ardor grants additional 3 Lust on each lvl-up (retroactive effect).",
		Stats.LUST_MAX_PERLEVEL to +3.0
	)
	val UnlockArdor3 = mk(
		"Unlock: Ardor (3rd Stage)", "Unlock: Ardor (3rd Stage)",
		"Unlocking ardor grants additional 3 Lust on each lvl-up (retroactive effect).",
		Stats.LUST_MAX_PERLEVEL to +3.0
	)
	val UnlockArdor4 = mk(
		"Unlock: Ardor (4th Stage)", "Unlock: Ardor (4th Stage)",
		"Unlocking ardor grants additional 3 Lust on each lvl-up (retroactive effect).",
		Stats.LUST_MAX_PERLEVEL to +3.0
	)
	val UnlockBody1 = mk(
		"Unlock: Body", "Unlock: Body (1st Stage)",
		"Unlocking body potential grants additional 60 HP on each lvl-up (retroactive effect).",
		Stats.HP_MAX_PERLEVEL to +60.0
	)
	val UnlockBody2 = mk(
		"Unlock: Body (2nd Stage)", "Unlock: Body (2nd Stage)",
		"Unlocking body potential grants additional 60 HP on each lvl-up (retroactive effect).",
		Stats.HP_MAX_PERLEVEL to +60.0
	)
	val UnlockBody3 = mk(
		"Unlock: Body (3rd Stage)", "Unlock: Body (3rd Stage)",
		"Unlocking body potential grants additional 60 HP on each lvl-up (retroactive effect).",
		Stats.HP_MAX_PERLEVEL to +60.0
	)
	val UnlockBody4 = mk(
		"Unlock: Body (4th Stage)", "Unlock: Body (4th Stage)",
		"Unlocking body potential grants additional 60 HP on each lvl-up (retroactive effect).",
		Stats.HP_MAX_PERLEVEL to +60.0
	)
	val UnlockEndurance1 = mk(
		"Unlock: Endurance", "Unlock: Endurance (1st Stage)",
		"Unlocking innate endurance grants additional 5 Fatigue on each lvl-up (retroactive effect).",
		Stats.FATIGUE_MAX_PERLEVEL to +5.0
	)
	val UnlockEndurance2 = mk(
		"Unlock: Endurance (2nd Stage)", "Unlock: Endurance (2nd Stage)",
		"Unlocking innate endurance grants additional 5 Fatigue on each lvl-up (retroactive effect).",
		Stats.FATIGUE_MAX_PERLEVEL to +5.0
	)
	val UnlockEndurance3 = mk(
		"Unlock: Endurance (3rd Stage)", "Unlock: Endurance (3rd Stage)",
		"Unlocking innate endurance grants additional 5 Fatigue on each lvl-up (retroactive effect).",
		Stats.FATIGUE_MAX_PERLEVEL to +5.0
	)
	val UnlockEndurance4 = mk(
		"Unlock: Endurance (4th Stage)", "Unlock: Endurance (4th Stage)",
		"Unlocking innate endurance grants additional 5 Fatigue on each lvl-up (retroactive effect).",
		Stats.FATIGUE_MAX_PERLEVEL to +5.0
	)
	val UnlockId1 = mk(
		"Unlock: Id", "Unlock: Id (1st Stage)",
		"Unlocking id grants additional 5 Wrath on each lvl-up (retroactive effect).",
		Stats.WRATH_MAX_PERLEVEL to +5.0
	)
	val UnlockId2 = mk(
		"Unlock: Id (2nd Stage)", "Unlock: Id (2nd Stage)",
		"Unlocking id grants additional 5 Wrath on each lvl-up (retroactive effect).",
		Stats.WRATH_MAX_PERLEVEL to +5.0
	)
	val UnlockId3 = mk(
		"Unlock: Id (3rd Stage)", "Unlock: Id (3rd Stage)",
		"Unlocking id grants additional 5 Wrath on each lvl-up (retroactive effect).",
		Stats.WRATH_MAX_PERLEVEL to +5.0
	)
	val UnlockId4 = mk(
		"Unlock: Id (4th Stage)", "Unlock: Id (4th Stage)",
		"Unlocking id grants additional 5 Wrath on each lvl-up (retroactive effect).",
		Stats.WRATH_MAX_PERLEVEL to +5.0
	)
	val UnlockSpirit1 = mk(
		"Unlock: Spirit", "Unlock: Spirit (1st Stage)",
		"Unlocking spirit potential grants additional 5 Soulforce on each lvl-up (retroactive effect).",
		Stats.SF_MAX_PERLEVEL to 5.0
	)
	val UnlockSpirit2 = mk(
		"Unlock: Spirit (2nd Stage)", "Unlock: Spirit (2nd Stage)",
		"Unlocking spirit potential grants additional 5 Soulforce Spirit each lvl-up (retroactive effect).",
		Stats.SF_MAX_PERLEVEL to 5.0
	)
	val UnlockSpirit3 = mk(
		"Unlock: Spirit (3rd Stage)", "Unlock: Spirit (3rd Stage)",
		"Unlocking spirit potential grants additional 5 Soulforce on each lvl-up (retroactive effect).",
		Stats.SF_MAX_PERLEVEL to 5.0
	)
	val UnlockSpirit4 = mk(
		"Unlock: Spirit (4th Stage)", "Unlock: Spirit (4th Stage)",
		"Unlocking spirit potential grants additional 5 Soulforce on each lvl-up (retroactive effect).",
		Stats.SF_MAX_PERLEVEL to 5.0
	)
	val UnlockForce1 = mk(
		"Unlock: Force", "Unlock: Force (1st Stage)",
		"Unlocking magic potential grants additional 10 Mana on each lvl-up (retroactive effect).",
		Stats.MANA_MAX_PERLEVEL to 10.0
	)
	val UnlockForce2 = mk(
		"Unlock: Force (2nd Stage)", "Unlock: Force (2nd Stage)",
		"Unlocking magic potential grants additional 10 Mana on each lvl-up (retroactive effect).",
		Stats.MANA_MAX_PERLEVEL to 10.0
	)
	val UnlockForce3 = mk(
		"Unlock: Force (3rd Stage)", "Unlock: Force (3rd Stage)",
		"Unlocking magic potential grants additional 10 Mana on each lvl-up (retroactive effect).",
		Stats.MANA_MAX_PERLEVEL to 10.0
	)
	val UnlockForce4 = mk(
		"Unlock: Force (4th Stage)", "Unlock: Force (4th Stage)",
		"Unlocking magic potential grants additional 10 Mana on each lvl-up (retroactive effect).",
		Stats.MANA_MAX_PERLEVEL to 10.0
	)

	private fun mk(id: String, name: String, desc: String, vararg buffs: Pair<StatMeta, Double>) =
		if (buffs.isEmpty()) SimplePerk(id, name, desc)
		else SimplePerkWithBuffs(id, name, desc, *buffs)
}

