package xta.flash

import minerva.AMF3
import minerva.AMF3WrappedValue
import minerva.MinervaByteArray
import minerva.unwrap
import org.khronos.webgl.ArrayBuffer
import org.w3c.files.Blob
import org.w3c.files.arrayBuffer
import xta.game.ItemType
import xta.game.PlayerCharacter
import xta.game.creature.KnownThings
import xta.game.creature.body.*
import xta.game.items.ArmorItem
import xta.game.items.MeleeWeaponItem
import xta.game.stats.BuffRate
import xta.game.stats.BuffableStat
import xta.game.stats.PrimaryStat
import xta.game.stats.RawStat
import xta.logging.LogManager
import xta.logging.Logger
import xta.utils.component1
import xta.utils.component2
import xta.utils.component3
import kotlin.js.Promise
import kotlin.math.round
import kotlin.math.roundToInt

/*
 * Created by aimozg on 28.11.2021.
 */
class FlashImporter {

	fun importBlob(blob: Blob): Promise<PlayerCharacter> {
		return blob.arrayBuffer().then { importBuffer(it) }
	}

	fun importBuffer(buffer: ArrayBuffer): PlayerCharacter {
		return importAMF(AMF3().readData(MinervaByteArray(buffer)))
	}

	private fun importStat(dest:BuffableStat, src:CocBuffableStatJson) {
		for ((value, tag, options) in src.effects) {
			importBuff(dest, tag, value, options)
		}
	}

	private fun importBuff(
		dest: BuffableStat,
		tag: String,
		value: Double,
		options: CocBuffJson
	) {
		if (tag == "Racials" || tag == "EzekielBlessing") return
		dest.addOrReplaceBuff(
			tag,
			value,
			options.text,
			BuffRate.byID(options.rate) ?: error("Invalid buff ${dest.statName}/$tag rate ${options.rate}"),
			options.tick,
			true,
			options.show ?: true
		)
	}

	private fun importStat(dest:RawStat, src:CocRawStatJson) {
		dest.value = src.value
	}
	private fun importStat(dest:PrimaryStat, src:CocPrimaryStatJson) {
		importStat(dest.bonus, src.bonus)
		importStat(dest.mult, src.mult)
		importStat(dest.core, src.core)
	}

	private fun importStatusEffect(character: PlayerCharacter, effect:CocStatusEffectJson) {
		val knowledge = statusEffectIdToKnowledge[effect.statusAffectName]
		if (knowledge != null) {
			character.knowledge.add(knowledge)
			return
		}
		/*
		when (effect.statusAffectName) {

		}
		 */
	}

	private fun importAMF(amfdata: AMF3WrappedValue): PlayerCharacter {
		logger.debug(null, "Loading ", amfdata)
		val character = PlayerCharacter()

		@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
		val data = (unwrap(amfdata) as CocSaveFileJson).data
		logger.logObject(Logger.Level.DEBUG,null, "Unwrapped to",data)
		if (data?.exists != true) error("Save data does not exist")

		character.startingRace = data.startingRace
		character.name = data.short
		// player.a = data.a

		importStat(character.strStat, data.stats.str)
		importStat(character.touStat, data.stats.tou)
		importStat(character.speStat, data.stats.spe)
		importStat(character.intStat, data.stats.int)
		importStat(character.wisStat, data.stats.wis)
		importStat(character.libStat, data.stats.lib)
		importStat(character.sensStat, data.stats.sens)

		character.cor = data.cor.roundToInt()
		character.fatigue = round(data.fatigue)
		character.mana = round(data.mana)
		character.soulforce = round(data.soulforce)

		character.hp = round(data.HP)
		character.lust = round(data.lust)
		character.wrath = round(data.wrath)

		character.xp = data.XP
		character.level = data.level
		character.gems = data.gems

		character.tallness = data.tallness
		character.femininity = data.femininity

		character.hair.type = HairType.byId(data.hairType)
		character.hair.style = HairStyle.byId(data.hairStyle)
		character.hair.color = data.hairColor
		character.hair.length = data.hairLength.roundToInt()
		character.beardStyle = BeardStyle.byId(data.beardStyle)
		character.beardLength = data.beardLength

		// skin
		character.skin.coverage = SkinCoverage.byId(data.skin.coverage)
		character.skin.baseType = SkinBaseType.byIdOrNull(data.skin.base.type)
			?: SkinBaseType.PLAIN.also {
				logger.error(null,"Unknown skin.base.type ${data.skin.base.type}")
			}
		character.skin.baseColor = data.skin.base.color
		character.skin.baseColor2 = data.skin.base.color2
		character.skin.baseDesc = data.skin.base.desc
		character.skin.baseAdj = data.skin.base.adj
		character.skin.basePattern = SkinBasePatternType.byId(data.skin.base.pattern)
		character.skin.coatType = SkinCoatType.byIdOrNull(data.skin.coat.type)
			?: SkinCoatType.FUR.also {
				if (character.skin.coverage > SkinCoverage.NONE) {
					error("Unknown skin.coat.type ${data.skin.coat.type}")
				}
			}
		character.skin.coatColor = data.skin.coat.color
		character.skin.coatColor2 = data.skin.coat.color2
		character.skin.coatDesc = data.skin.coat.desc
		character.skin.coatAdj = data.skin.coat.adj
		character.skin.coatPattern = SkinCoatPatternType.byId(data.skin.coat.pattern)
		character.face.type = FaceType.byId(data.facePart.type)
		character.claws.type = ClawType.byId(data.clawsPart.type)
		character.claws.color = data.clawsPart.tone
		// Underbody - not used in CoCX
		character.ears.type = EarType.byId(data.earType)
		character.horns.type = HornType.byId(data.hornType)
		character.horns.count = data.horns
		character.wings.type = WingType.byId(data.wingType)
		character.wings.desc = data.wingDesc
		character.lowerBody.type = LowerBodyType.byId(data.lowerBodyPart.type)
		character.lowerBody.legCount = data.lowerBodyPart.legCount
		character.tail.type = TailType.byId(data.tail.type)
		character.tail.count = data.tail.count
		character.tail.venom = data.tail.venom
		character.tail.recharge = data.tail.recharge
		character.antennae.type = AntennaeType.byId(data.antennae)
		character.eyes.type = EyeType.byId(data.eyeType)
		character.eyes.irisColor = data.eyeColor
		character.tongue.type = TongueType.byId(data.tongueType)
		character.arms.type = ArmType.byId(data.armType)
		character.gills.type = GillType.byId(data.gillType)
		character.rearBody.type = RearBodyType.byId(data.rearBody)

		character.thickness = data.thickness
		character.tone = data.tone
		character.hipRating = data.hipRating
		character.buttRating = data.buttRating

		for (jcock in data.cocks) {
			character.cocks.add(PenisPart().also { penis ->
				penis.length = jcock.cockLength.roundToInt()
				penis.thickness = jcock.cockThickness.roundToInt()
				// TODO other props
			})
		}
		character.balls = data.balls
		character.cumMultiplier = data.cumMultiplier
		character.ballSize = data.ballSize

		for (jvagina in data.vaginas) {
			character.vaginas.add(VaginaPart().also { vagina ->
				vagina.type = VaginaType.byId(jvagina.type)
				vagina.virgin = jvagina.virgin == true
				// TODO other props
			})
		}

		character.breastRows.clear() // remove default single row
		for (jbreasts in data.breastRows) {
			character.breastRows.add(BreastRowPart().also { row ->
				row.breastRating = jbreasts.breastRating.roundToInt()
				// TODO other props
			})
		}

		for (jperk in data.perks) {
			if (jperk.id in IGNORED_PERK_IDS) continue
			character.perks.loadPerk(jperk.id)
		}

		for (effect in data.statusAffects) {
			importStatusEffect(character, effect)
		}

		if (data.armorId != "nothing") {
			val armor = ItemType.BY_ID[data.armorId] as? ArmorItem
			character.armor = armor
			armor?.loaded(character)
			if (armor == null) {
				logger.warn(null, "Unknown armor id '${data.armorId}'")
			}
		}
		if (data.weaponId != "Fists  ") {
			val weapon = ItemType.BY_ID[data.weaponId] as? MeleeWeaponItem
			character.meleeWeapon = weapon
			weapon?.loaded(character)
			if (weapon == null) {
				logger.warn(null, "Unknown melee weapon id '${data.weaponId}'")
			}
		}

		logger.logObject(Logger.Level.INFO, null, "Imported",character)
		return character
	}

	companion object {
		private val logger by lazy { LogManager.getLogger("xta.flash.FlashImporter") }

		private val IGNORED_PERK_IDS = arrayOf(
			"Wizard's Endurance",
			"Wizard's and Daoists's Endurance"
		)

		private val statusEffectIdToKnowledge = mapOf(
			"Knows Aegis" to KnownThings.SPELL_AEGIS,
			"Knows Arctic Gale" to KnownThings.SPELL_ARCTICGALE,
			"Knows Arouse" to KnownThings.SPELL_AROUSE,
			"Knows Balance of Life" to KnownThings.SPELL_BALANCEOFLIFE,
			// "Knows Barrage" to KnownThings.BARRAGE,
			"Knows Blind" to KnownThings.SPELL_BLIND,
			"Knows Blink" to KnownThings.SPELL_BLINK,
			"Knows Blizzard" to KnownThings.SPELL_BLIZZARD,
			"Knows Blood Chains" to KnownThings.SPELL_BLOODCHAINS,
			"Knows Blood Explosion" to KnownThings.SPELL_BLOODEXPLOSION,
			"Knows Blood Field" to KnownThings.SPELL_BLOODFIELD,
			"Knows Blood Missiles" to KnownThings.SPELL_BLOODMISSILES,
			// "Knows Blood Dewdrops" to KnownThings.BLOODDEWDROPS,
			// "Knows Blood Dewdrops (infused with Soulforce)" to KnownThings.BLOODDEWDROPSSF,
			// "Knows Blood Requiem" to KnownThings.BLOODREQUIEM,
			// "Knows Blood Requiem (infused with Soulforce)" to KnownThings.BLOODREQUIEMSF,
			"Knows Blood Shield" to KnownThings.SPELL_BLOODSHIELD,
			// "Knows Blood Swipe" to KnownThings.BLOODSWIPE,
			// "Knows Blood Swipe (infused with Soulforce)" to KnownThings.BLOODSWIPESF,
			// "Knows Blood Wave" to KnownThings.BLOODWAVE,
			"Knows Boneshatter" to KnownThings.SPELL_BONESHATTER,
			"Knows Bone armor" to KnownThings.SPELL_BONEARMOR,
			"Knows Bone spirit" to KnownThings.SPELL_BONESPIRIT,
			"Knows Chain Lighting" to KnownThings.SPELL_CHAINLIGNTNING,
			"Knows Charge" to KnownThings.SPELL_CHARGEWEAPON,
			"Knows Charge Armor" to KnownThings.SPELL_CHARGEARMOR,
			"Knows Clear Mind" to KnownThings.SPELL_CLEARMIND,
			// "Knows Cleave" to KnownThings.CLEAVE,
			// "Knows Comet" to KnownThings.COMET,
			"Knows Consuming darkness" to KnownThings.SPELL_CONSUMINGDARKNESS,
			"Knows Cure" to KnownThings.SPELL_CURE,
			"Knows Curse of Desire" to KnownThings.SPELL_CURSEOFDESIRE,
			"Knows Curse of Weeping" to KnownThings.SPELL_CURSEOFWEEPING,
			"Knows Darkness Shard" to KnownThings.SPELL_DARKNESSSHARD,
			"Knows Divine shield" to KnownThings.SPELL_DIVINESHIELD,
			// "Knows Draco Sweep" to KnownThings.DRACOSWEEP,
			"Knows Dusk Wave" to KnownThings.SPELL_DUSKWAVE,
			// "Knows Earth Stance" to KnownThings.EARTHSTANCE,
			"Knows Exorcise" to KnownThings.SPELL_EXORCISE,
			"Knows Energy Drain" to KnownThings.SPELL_ENERGYDRAIN,
			// "Knows Fire Punch" to KnownThings.FIREPUNCH,
			"Knows Fire Storm" to KnownThings.SPELL_FIRESTORM,
			// "Knows Flames of Love" to KnownThings.FLAMESOFLOVE,
			// "Knows Grandiose Hail of Blades" to KnownThings.GRANDIOSEHAILOFBLADES,
			// "Knows Grandiose Hail of Moon Blades" to KnownThings.GRANDIOSEHAILOFMOONBLADES,
			// "Knows Hail of Blades" to KnownThings.HAILOFBLADES,
			// "Knows Heart Seeker" to KnownThings.HEARTSEEKER,
			// "Knows Heart Seeker (infused with Soulforce)" to KnownThings.HEARTSEEKERSF,
			"Knows Heal" to KnownThings.SPELL_HEAL,
			// "Knows Heaven's Devourer" to KnownThings.HEAVENSDEVOURER,
			// "Knows Hurricane Dance" to KnownThings.HURRICANEDANCE,
			// "Knows Ice Fist" to KnownThings.ICEFIST,
			"Knows Ice Rain" to KnownThings.SPELL_ICERAIN,
			"Knows Ice Spike" to KnownThings.SPELL_ICESPIKE,
			// "Knows Icicles of Love" to KnownThings.ICICLESOFLOVE,
			"Knows Lifesteal Enchantment" to KnownThings.SPELL_LIFESTEALENCHANTMENT,
			"Knows Lifetap" to KnownThings.SPELL_LIFETAP,
			"Knows Life siphon" to KnownThings.SPELL_LIFESIPHON,
			"Knows Lightning Bolt" to KnownThings.SPELL_LIGHTNINGBOLT,
			// "Knows Many Birds" to KnownThings.MANYBIRDS,
			"Knows Mana Shield" to KnownThings.SPELL_MANASHIELD,
			"Knows Mental Shield" to KnownThings.SPELL_MENTALSHIELD,
			"Knows Meteor Shower" to KnownThings.SPELL_METEORSHOWER,
			"Knows Might" to KnownThings.SPELL_MIGHT,
			// "Knows Night of Brotherhood" to KnownThings.NIGHTOFBROTHERHOOD,
			// "Knows Nonuple Thrust" to KnownThings.NONUPLETHRUST,
			"Knows Nosferatu" to KnownThings.SPELL_NOSFERATU,
			// "Knows Overlimit" to KnownThings.OVERLIMIT,
			"Knows Polar Midnight" to KnownThings.SPELL_POLARMIDNIGHT,
			// "Knows Punishing Kick" to KnownThings.PUNISHINGKICK,
			"Knows Pyre Burst" to KnownThings.SPELL_PYREBURST,
			"Knows Regenerate" to KnownThings.SPELL_REGENERATE,
			"Knows Restore" to KnownThings.SPELL_RESTORE,
			// "Knows Scarlet Spirit Charge" to KnownThings.SCARLETSPIRITCHARGE,
			// "Knows Scarlet Spirit Charge (infused with Soulforce)" to KnownThings.SCARLETSPIRITCHARGESF,
			// "Knows Sextuple Thrust" to KnownThings.SEXTUPLETHRUST,
			// "Knows Sidewinder" to KnownThings.SIDEWINDER,
			// "Knows Soul Blast" to KnownThings.SOULBLAST,
			// "Knows Storm of Sisterhood" to KnownThings.STORMOFSISTERHOOD,
			"Knows Tears of Denial" to KnownThings.SPELL_TEARSOFDENIAL,
			"Knows Thunderstorm" to KnownThings.SPELL_THUNDERSTORM,
			// "Knows Triple Thrust" to KnownThings.TRIPLETHRUST,
			// "Knows Violet Pupil Transformation" to KnownThings.VIOLETPUPILTRANSFORMATION,
			"Knows Wave of Ecstasy" to KnownThings.SPELL_WAVEOFECSTASY,
			// "Knows Were-Beast" to KnownThings.WEREBEAST,
			"Knows Whitefire" to KnownThings.SPELL_WHITEFIRE,
			// "Knows Yin Yang Blast" to KnownThings.YINYANGBLAST,
		)
	}
}
