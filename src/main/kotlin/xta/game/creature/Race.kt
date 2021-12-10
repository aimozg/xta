package xta.game.creature

import xta.game.PlayerCharacter
import xta.game.creature.races.CatRace
import xta.game.creature.races.HumanRace
import xta.game.creature.races.KitsuneRace
import xta.game.text.Appearance

/*
 * Created by aimozg on 28.11.2021.
 */
abstract class Race(
	val cocID:Int,
	val name: String,
	val minScore: Int
) {

	open fun nameOf(creature: PlayerCharacter, score: Int) = name
	open fun fullNameOf(creature: PlayerCharacter, score: Int): String {
		val name = nameOf(creature, score)
		if (creature.gender == Gender.MALE && name.endsWith("-boy")) return name
		if (creature.gender == Gender.FEMALE && name.endsWith("-girl")) return name
		return Appearance.maleFemaleHerm(creature)+" "+name
	}

	open fun score(creature: PlayerCharacter): Int {
		return finalizeScore(creature, basicScore(creature))
	}
	protected open fun finalizeScore(creature: PlayerCharacter, basicScore: Int):Int {
		/*
		TODO gargoyle, elemental body, racial paragon, ultimate chimerical body
		if (isGargoyle() && this != Race.GARGOYLE) score = 0
		if (hasPerk(PerkLib.ElementalBody)) score = 0
		if (hasPerk(PerkLib.ChimericalBodyUltimateStage) && race filter)
			catCounter += 50;
		 */
		return basicScore
	}
	abstract fun basicScore(creature: PlayerCharacter):Int

	companion object {
		val ALL_RACES: List<Race> = listOf(
			HumanRace,
			CatRace,
			// NekomataRace,
			// CheshireRace,
			// HellcatRace,
			// DisplacerBeastRace,
			// SphinxRace,
			// LizardRace,
			// DragonRace,
			// DragonneRace,
			// RaccoonRace,
			// DogRace,
			// WolfRace,
			// WerewolfRace,
			// FoxRace,
			// FerretRace,
			KitsuneRace,
			// HorseRace,
			// UnicornRace,
			// CentaurRace,
			// MinotaurRace,
			// CowRace,
			// SandtrapRace,
			// BeeRace,
			// GoblinRace,
			// DemonRace,
			// DevilRace,
			// SharkRace,
			// OrcaRace,
			// OomukadeRace,
			// OniRace,
			// ElfRace,
			// OrcRace,
			// RaijuRace,
			// ThunderbirdRace,
			// BunnyRace,
			// HarpyRace,
			// SpiderRace,
			// KangarooRace,
			// MouseRace,
			// ScorpionRace,
			// MantisRace,
			// SalamanderRace,
			// CaveWyrmRace,
			// NagaRace,
			// GorgonRace,
			// VouivreRace,
			// CouatlRace,
			// HydraRace,
			// FireSnailRace,
			// PhoenixRace,
			// ScyllaRace,
			// PlantRace,
			// AlrauneRace,
			// YggdrasilRace,
			// PigRace,
			// SatyrRace,
			// RhinoRace,
			// EchidnaRace,
			// DeerRace,
			// ManticoreRace,
			// RedPandaRace,
			// BearAndPandaRace,
			// SirenRace,
			// YetiRace,
			// YukionnaRace,
			// BatRace,
			// VampireRace,
			// JabberwockyRace,
			// AvianRace,
			// GargoyleRace,
			// SlimeRace,
			// MagmaslimeRace,
			// DarkslimeRace,
			// JiangshiRace,
			// PoltergeistRace,
			// BansheeRace,
			// MelkieRace,
			// EasterBunnyRace,
			// CentipedeRace,
			// FrostWyrmRace,
			// CancerRace,
			// UshiOnnaRace,
			// FairyRace,
			// GremlinRace,
			// KamaitachiRace,
			// GazerRace,
			// RatatoskrRace,
			// WendigoRace,
			// TrollRace,
			// AtlachNachaRace,
			// WoodElfRace,
			// SeaDragonRace,
			// FemaleMindbreakerRace,
		)

		fun byId(cocID: Int) = ALL_RACES.find { it.cocID == cocID }
	}
}
