package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup
import xta.game.Creature

enum class RearBodyType(
	override val cocID: Int,
	val displayName: String,
	val isPlural: Boolean = false,
	private val appearanceDesc: String = ""
) : CocId {
	NONE(0, "none"),
	DRACONIC_MANE(1, "draconic hairy mane"),
	DRACONIC_SPIKES(2, "draconic spiky mane"),
	FENRIR_ICE_SPIKES(
		3, "ice shards",
		isPlural = true,
		appearanceDesc = "Jagged ice shards grows out of [your] back, providing excellent defence and chilling the air around [him]."
	),
	LION_MANE(
		4, "lion mane",
		appearanceDesc = "Around [your] neck there is a thick mane of [skin coat.color] fur. It looks great on [him]."
	),
	BEHEMOTH(5, "behemoth spikes", isPlural = true),
	SHARK_FIN(
		6, "shark fin",
		appearanceDesc = "A large shark-like fin has sprouted between [your] shoulder blades. With it [you] [have] far more control over swimming underwater."
	),
	ORCA_BLOWHOLE(
		7, "orca blowhole",
		appearanceDesc = "Between [your] shoulder blades is a blowhole that allows to breath in air from [your] back while swimming, just like an orca."
	),
	RAIJU_MANE(
		8, "raiju mane",
		appearanceDesc = "A thick collar of fur grows around [your] neck. Multiple strands of fur are colored in a dark shade, making it look like a lightning bolt runs along the center of [your] fur collar."
	),
	BAT_COLLAR(
		9, "bat collar",
		appearanceDesc = "Around [your] neck is a thick collar of fur reminiscent of a bat's."
	),
	WOLF_COLLAR(
		10, "wolf collar",
		appearanceDesc = "Around [your] neck, there is a thick coat of [skin coat.color] fur. It looks great on [him]. That said, [you] can dismiss every one of your bestial features at any time should the need arise for [you] to appear human."
	),
	DISPLACER_TENTACLES(
		11, "displacer tentacles",
		isPlural = true,
		appearanceDesc = "On your back grows two tentacles with a pair of thick, heavy feelers. [You] [verb use] them to suck [your] victims dry of their milk loads as [you] [verb pump] them full of [your] lewd venom."
	),
	SNAIL_SHELL(12, "snail shell") {
		override fun appearanceDescription(creature: Creature) = buildString {
			append("On [your] back is a large spiralling shell which")
			// TODO if has fire affinity perk
//			if (player.hasPerk(PerkLib.FireAffinity)) append(" erupts regularly with magma and")
			append(" protects [your] vital organs.")
		}
	},
	METAMORPHIC_GOO(
		13, "metamorphic goo",
		appearanceDesc = "Since [your] body is made of malleable goo [you] can reshape [your] form however [you] [verb want] to gain tentacle or any limb or appendage whenever [you] [verb need] one either for combat or for fun."
	),
	GHOSTLY_AURA(
		15, "ghostly aura",
		appearanceDesc = "An eerie ghostly aura surrounds [you]. [Your] ominous presence is enough to disturb even the bravest of souls."
	),
	YETI_FUR(16, "yeti fur") {
		override fun appearanceDescription(creature: Creature): String = buildString {
			append("Covered with a natural ")
			if (creature.biggestTitSize() > 1) append("fur bikini") else append("layer of fur")
			append(", [your] ")
			if (creature.biggestTitSize() > 1) append("chest and ")
			append("crotch is protected against the cold.")
		}
	},
	GLACIAL_AURA(
		17, "glacial aura",
		appearanceDesc = "The air temperature around [you] naturally drops to water freezing levels, causing snow flurries to appear around [you] every now and then as moisture enters [your] personal space."
	),
	CENTIPEDE(
		18, "centipede rear",
		appearanceDesc = "Around [your] neck sits a set of pincer like maxillipeds. [You] can use them to sting and hold onto [your] prey."
	),
	KRAKEN(
		19, "kraken rear",
		appearanceDesc = "Small glowing dots draw a trail all over the length of [your] body. They shine beautifully in both the deepest waters and the night giving [you] a somewhat ominous presence."
	),
	FROSTWYRM(
		20, "frostwyrm rear",
		appearanceDesc = "Around [your] neck there is a thick collar of snowy white fur. It looks great on [you] and, best of all, shields [him] against the cold."
	),
	FUR_COAT(
		21, "fur coat",
		appearanceDesc = "On [your] back [you] wear a thick fur coat, not unlike a mantle complete with a hood always worn over [your] head it has a pair of holes just to let a pair of horns or rather antlers juts through it."
	),
	TENTACLE_EYESTALKS(22, "tentacle eyestalks") {
		// TODO number of gazer eye stalks
		override fun appearanceDescription(creature: Creature) =
			"A set of " + /*player.statusEffectv1(StatusEffects.GazerEyeStalksPlayer) +*/ " tentacle eyestalks expand from [your] back giving [you] all around vision. Their gazes are charged with lethal magical powers."
	},
	ATLACH_NACHA(
		23, "Atlach Nacha rear",
		appearanceDesc = "Two pairs of chitinous, black spider legs sprout from [your] shoulders and back. Red eyes blink and close at the tips."
	),
	MINDBREAKER(
		24, "Mindbreaker rear",
		appearanceDesc = "On [your] back spreads a webbed leather membrane, which from afar may look like a cape."
	),
	;

	open fun appearanceDescription(creature: Creature): String = appearanceDesc

	companion object : CocIdLookup<RearBodyType>(values())
}
