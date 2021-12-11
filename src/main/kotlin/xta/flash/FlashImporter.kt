package xta.flash

import minerva.AMF3
import minerva.AMF3WrappedValue
import minerva.MinervaByteArray
import minerva.unwrap
import org.khronos.webgl.ArrayBuffer
import org.w3c.files.Blob
import org.w3c.files.arrayBuffer
import xta.game.PlayerCharacter
import xta.game.creature.body.*
import xta.game.stats.Buff
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
			dest.addOrReplaceBuff(tag,
				value,
				options.text,
				Buff.Rate.byID(options.rate) ?: error("Invalid buff ${dest.statName}/$tag rate ${options.rate}"),
				options.tick,
				true,
				options.show?:true
			)
		}
	}
	private fun importStat(dest:RawStat, src:CocRawStatJson) {
		dest.value = src.value
	}
	private fun importStat(dest:PrimaryStat, src:CocPrimaryStatJson) {
		importStat(dest.bonus, src.bonus)
		importStat(dest.mult, src.mult)
		importStat(dest.core, src.core)
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
		// TODO clawsPart.tone
		// TODO underbody
		character.ears.type = EarType.byId(data.earType)
		character.horns.type = HornType.byId(data.hornType)
		character.horns.count = data.horns
		character.wings.type = WingType.byId(data.wingType)
		// TODO wingDesc
		character.lowerBody.type = LowerBodyType.byId(data.lowerBodyPart.type)
		// TODO leg count
		character.tail.type = TailType.byId(data.tail.type)
		character.tail.count = data.tail.count
		// TODO tail venom, recharge
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

		character.perks.clear()
		for (jperk in data.perks) {
			character.perks.loadPerk(jperk.id)
		}

		logger.logObject(Logger.Level.INFO, null, "Imported",character)
		return character
	}

	companion object {
		private val logger by lazy { LogManager.getLogger("xta.flash.FlashImporter") }
	}
}
