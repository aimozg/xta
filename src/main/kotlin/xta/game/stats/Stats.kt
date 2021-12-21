package xta.game.stats

/*
 * Created by aimozg on 09.12.2021.
 */
object Stats {
	val STR = StatMeta("str", "Strength")
	val STR_CORE = StatMeta("str.core", "Strength", "Strength core")
	val STR_MULT = StatMeta("str.mult", "Strength", "Strength mult", isPercentage = true)
	val STR_BONUS = StatMeta("str.bonus", "Strength", "Strength bonus")
	val TOU = StatMeta("tou", "Toughness")
	val TOU_CORE = StatMeta("tou.core", "Toughness", "Toughness core")
	val TOU_MULT = StatMeta("tou.mult", "Toughness", "Toughness mult", isPercentage = true)
	val TOU_BONUS = StatMeta("tou.bonus", "Toughness", "Toughness bonus")
	val SPE = StatMeta("spe", "Speed")
	val SPE_CORE = StatMeta("spe.core", "Speed", "Speed core")
	val SPE_MULT = StatMeta("spe.mult", "Speed", "Speed mult", isPercentage = true)
	val SPE_BONUS = StatMeta("spe.bonus", "Speed", "Speed bonus")
	val INT = StatMeta("int", "Intelligence")
	val INT_CORE = StatMeta("int.core", "Intelligence", "Intelligence core")
	val INT_MULT = StatMeta("int.mult", "Intelligence", "Intelligence mult", isPercentage = true)
	val INT_BONUS = StatMeta("int.bonus", "Intelligence", "Intelligence bonus")
	val WIS = StatMeta("wis", "Wisdom")
	val WIS_CORE = StatMeta("wis.core", "Wisdom", "Wisdom core")
	val WIS_MULT = StatMeta("wis.mult", "Wisdom", "Wisdom mult", isPercentage = true)
	val WIS_BONUS = StatMeta("wis.bonus", "Wisdom", "Wisdom bonus")
	val LIB = StatMeta("lib", "Libido")
	val LIB_CORE = StatMeta("lib.core", "Libido", "Libido core")
	val LIB_MULT = StatMeta("lib.mult", "Libido", "Libido mult", isPercentage = true)
	val LIB_BONUS = StatMeta("lib.bonus", "Libido", "Libido bonus")

	val SENS = StatMeta("sens", "Sensitivity", isGood = false)

	val HP_MAX_BASE = StatMeta("hpmax_base", "HP Max")
	val HP_MAX_MULT = StatMeta("hpmax_mult", "HP Max", "HP Max mult", isPercentage = true)
	val HP_MAX_PERLEVEL = StatMeta("hpmax_perlevel", "HP Max per level")
	val HP_REGEN = StatMeta("hp-regen", "HP regeneration")

	val MANA_MAX_BASE = StatMeta("manamax_base", "Mana Max")
	val MANA_MAX_MULT = StatMeta("manamax_mult", "Mana Max", "Mana Max mult", isPercentage = true)
	val MANA_MAX_PERLEVEL = StatMeta("manamax_perlevel", "Mana Max per level")
	val MANA_MAX_PERINT = StatMeta("manamax_perlevel", "Mana Max per intelligence")
	val MANA_MAX_PERWIS = StatMeta("manamax_perlevel", "Mana Max per wisdom")
	val MANA_REGEN = StatMeta("mana-regen", "Mana regeneration")

	val LUST_MIN = StatMeta("lustmin", "Min Lust", isGood = false)
	val LUST_MAX_BASE = StatMeta("lustmax_base", "Max Lust")
	val LUST_MAX_MULT = StatMeta("lustmax_mult", "Max Lust", "Max Lust mult", isPercentage = true)
	val LUST_MAX_PERLEVEL = StatMeta("lustmax_perlevel", "Lust Max per level")

	val FATIGUE_MAX_BASE = StatMeta("fatgmax_base", "Max Fatigue")
	val FATIGUE_MAX_MULT = StatMeta("fatgmax_mult", "Max Fatigue", "Max Fatigue mult", isPercentage = true)
	val FATIGUE_MAX_PERLEVEL = StatMeta("fatgmax_perlevel", "Fatigue Max per level")
	val FATIGUE_MAX_PERSPE = StatMeta("fatgmax_perspe", "Fatigue Max per speed")
	val FATIGUE_REGEN = StatMeta("fatg-regen", "Fatigue regeneration")

	val WRATH_MAX_BASE = StatMeta("wrathmax_base", "Max Wrath")
	val WRATH_MAX_MULT = StatMeta("wrathmax_mult", "Max Wrath", "Max Wrath mult", isPercentage = true)
	val WRATH_MAX_PERLEVEL = StatMeta("wrathmax_perlevel", "Wrath Max per level")
	val WRATH_REGEN = StatMeta("wrath-regen", "Wrath regeneration")

	val SF_MAX_BASE = StatMeta("sfmax_base", "Max Soulforce")
	val SF_MAX_MULT = StatMeta("sfmax_mult", "Max Soulforce", "Max Soulforce mult", isPercentage = true)
	val SF_MAX_PERLEVEL = StatMeta("sfmax_perlevel", "Soulforce Max per level")
	val SF_REGEN = StatMeta("sf-regen", "Soulforce regeneration")

	val AIM_MELEE = StatMeta("aim_melee", "Melee aim", isPercentage = true)

	val DODGE_ANY = StatMeta("dodge", "Dodge", isPercentage = true)
	val DODGE_MELEE = StatMeta("dodge_melee", "Melee dodge", isPercentage = true)

	val DAMAGE_MELEE = StatMeta("dmg_melee", "Melee damage")
	val DAMAGE_RANGED_MULT = StatMeta("dmg_ranged_mult", "Ranged damage", "Ranged damage mult", isPercentage = true)

	val RESIST_PHYS = StatMeta("resist_phys", "Reisst physical", isPercentage = true)
	val RESIST_MAG = StatMeta("resist_mag", "Resist magical", isPercentage = true)
	val RESIST_LUST = StatMeta("resist_lust", "Resist lust", isPercentage = true)

	val SPELL_POWER = StatMeta("spellpower", "Spell power", isPercentage = true)
	val SPELL_COST = StatMeta("spell_cost", "Spell cost", isPercentage = true, isGood = false)
	val SOULSKILL_POWER = StatMeta("soulskillpower", "Soulskill power", isPercentage = true)
	val SOULSKILL_COST = StatMeta("soulskill_cost", "Soulkill cost", isPercentage = true, isGood = false)
}
