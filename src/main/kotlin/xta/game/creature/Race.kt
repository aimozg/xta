package xta.game.creature

import xta.game.PlayerCharacter
import xta.game.creature.races.CatRace
import xta.game.creature.races.HumanRace
import xta.game.creature.races.KitsuneRace
import xta.game.creature.races.DogRace
import xta.game.creature.races.HorseRace
import xta.game.creature.races.* //Can we not just use this instead of importing each individually?

/*
 * Created by aimozg on 28.11.2021.
 */
abstract class Race(
	val cocID:Int,
	val name: String,
	val minScore: Int
) {

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
	open fun scoreAndStage(creature: PlayerCharacter):Pair<Int,RacialStage?> {
		val score = score(creature)
		return score to stageForScore(creature, score)
	}
	fun scoreAndStageOrNull(creature: PlayerCharacter):Pair<Int,RacialStage>? {
		val (score,stage) = scoreAndStage(creature)
		return if (stage == null) null else (score to stage)
	}
	abstract fun stageForScore(creature: PlayerCharacter, score: Int):RacialStage?

	companion object {
		val BUFF_TAG = "Racials"
		val BUFF_TEXT = "Racials"

		fun clearRacialBonuses(creature: PlayerCharacter) {
			creature.statStore.removeBuffs(BUFF_TAG)
		}

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
			DogRace,
			// WolfRace,
			// WerewolfRace,
			// FoxRace,
			// FerretRace,
			KitsuneRace,
			HorseRace,
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
