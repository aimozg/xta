package xta.game.creature.body

import xta.game.Creature
import xta.net.serialization.JsonSerializable
import xta.utils.decapitalized

@JsExport
class FacePart(val creature: Creature): JsonSerializable() {
	var type: FaceType by property(FaceType.HUMAN)

	fun appearanceDescription()  = buildString {
		if (type.isHumanShaped) {
			if (creature.skin.coverage < SkinCoverage.COMPLETE) {
				append("[Your] face is human in shape and structure")
				if (creature.skin.isCoverLowMid()) {
					append(", and on [your] cheeks [you] [have] [skin coat]")
				}
			} else {
				append("Under [your] [skin coat], [you] [have] a face which is human in shape and structure")
			}
		}

		val desc = type.appearanceDescription(creature)
		if (isNotEmpty()) {
			if (desc.isNotEmpty()) {
				append("; however, ")
				append(desc.decapitalized(true))
			} else {
				append(".")
			}
		} else {
			append(desc)
		}
	}

	fun hasBeard() = creature.beardLength > 0

	fun describeMF(article:Boolean=false): String {
		val femininity = creature.femininity
		val a:String
		val an:String
		val the:String
		if (article) {
			a = "a "
			an = "an "
			the = "the "
		} else {
			a = ""
			an = ""
			the = ""
		}
		return when {
			//0-9
			femininity < 10 -> buildString {
				append(a)
				append("square chin")
				if (!hasBeard()) {
					append(" and chiseled jawline")
				} else {
					append(", chiseled jawline, and beard")
				}
			}
			//10-20
			femininity < 20 -> buildString {
				append(a)
				append("rugged look ")
				if (hasBeard()) {
					append("and beard")
				}
				append("that's surely handsome")
			}
			//21-28
			femininity < 28 -> a + "well-defined jawline and a fairly masculine profile"
			//28+-35
			femininity < 35 -> a + "somewhat masculine, angular jawline"
			//35-45
			femininity < 45 -> the + "barest hint of masculinity on its features"
			//45-55
			femininity <= 55 -> an + "androgynous appearance that would look normal on a male or female"
			//55+-65
			femininity <= 65 -> a + "tiny touch of femininity to it, with gentle curves"
			//65+-72
			femininity <= 72 -> a + "nice set of cheekbones and lips that have the barest hint of pout"
			//72+-80
			femininity <= 80 -> a + "beautiful, feminine shapeliness that's sure to draw the attention of males"
			//81-90
			femininity <= 90 -> a + "gorgeous profile with full lips, a button nose, and noticeable eyelashes"
			//91-100
			else -> a + "jaw-droppingly feminine shape with full, pouting lips, an adorable nose, and long, beautiful eyelashes"
		}
	}
}
