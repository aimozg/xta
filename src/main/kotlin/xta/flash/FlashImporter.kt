package xta.flash

import xta.game.PlayerCharacter
import xta.game.creature.body.*
import xta.logging.LogManager
import xta.logging.Logger
import minerva.AMF3
import minerva.AMF3WrappedValue
import minerva.MinervaByteArray
import minerva.unwrap
import org.khronos.webgl.ArrayBuffer
import org.w3c.files.Blob
import org.w3c.files.arrayBuffer
import kotlin.js.Promise

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

	private fun importAMF(amfdata: AMF3WrappedValue): PlayerCharacter {
		logger.debug(null, "Loading ", amfdata)
		val character = PlayerCharacter()

		@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
		val data = (unwrap(amfdata) as CocSaveFileJson).data
		logger.debug(null,"Unwrapped to ", data)
		if (data?.exists != true) error("Save data does not exist")

		character.startingRace = data.startingRace
		character.name = data.short
		// player.a = data.a

		character.level = data.level
		character.xp = data.XP
		character.gems = data.gems
		character.tallness = data.tallness
		character.femininity = data.femininity

		character.hairType = HairType.byId(data.hairType) ?: error("Unknown hairType ${data.hairType}")
		character.hairStyle = HairStyle.byId(data.hairStyle) ?: error("Unknown hairStyle ${data.hairStyle}")
		character.hairColor = data.hairColor
		character.hairLength = data.hairLength

		// skin
		character.skin.coverage = SkinPart.Coverage.byId(data.skin.coverage) ?: error("Unknown skin.coverage ${data.skin.coverage}")
		character.skin.baseType = SkinBaseType.byId(data.skin.base.type)
			?: SkinBaseType.PLAIN.also {
				logger.error(null,"Unknown skin.base.type ${data.skin.base.type}")
			}
		character.skin.baseColor = data.skin.base.color
		character.skin.baseColor2 = data.skin.base.color2
		character.skin.coatType = SkinCoatType.byId(data.skin.coat.type)
			?: SkinCoatType.FUR.also {
				if (character.skin.coverage > SkinPart.Coverage.NONE) {
					error("Unknown skin.coat.type ${data.skin.coat.type}")
				}
			}
		character.skin.coatColor = data.skin.coat.color
		character.skin.coatColor2 = data.skin.coat.color2

		character.eyePart.type = EyeType.byId(data.eyeType) ?: error("Unknown eyeType ${data.eyeType}")
		character.eyePart.irisColor = data.eyeColor

		character.thickness = data.thickness
		character.tone = data.tone
		character.hipRating = data.hipRating
		character.buttRating = data.buttRating

		for (jcock in data.cocks) {
			val penis = PenisPart()
			character.cocks.add(penis)
		}
		character.balls = data.balls
		character.cumMultiplier = data.cumMultiplier
		character.ballSize = data.ballSize

		for (jvagina in data.vaginas) {
			val vagina = VaginaPart()
			character.vaginas.add(vagina)
		}

		if (logger.levelEnabled(Logger.Level.INFO)) {
			console.log("Imported", character)
			logger.info(null,"Imported", character)
		}
		return character
	}

	companion object {
		private val logger by lazy { LogManager.getLogger("xta.flash.FlashImporter") }
	}
}
