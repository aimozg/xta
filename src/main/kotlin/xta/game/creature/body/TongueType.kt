package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup
import xta.game.Creature

enum class TongueType(
	override val cocID: Int,
	val displayName: String,
	private val appearanceDesc: String = ""
) : CocId {
	HUMAN(
		0, "human",
		appearanceDesc = "[You] [have] a completely normal tongue, unlike a good number of Mareth's denizens."
	),
	SNAKE(
		1, "snake",
		appearanceDesc = "A snake-like tongue occasionally flits between [your] lips, tasting the air."
	),
	DEMONIC(
		2, "demonic",
		appearanceDesc = "A slowly undulating tongue occasionally slips from between [your] lips. It hangs nearly two feet long when [you] [verb let] the whole thing slide out, though [you] can retract it to appear normal."
	),
	DRACONIC(
		3, "draconic",
		appearanceDesc = "[Your] mouth contains a thick, fleshy tongue that, if [you] so [verb desire], can telescope to a distance of about four feet. It has sufficient manual dexterity that [you] can use it almost like a third arm."
	),
	ECHIDNA(
		4, "echidna",
		appearanceDesc = "A thin echidna tongue, at least a foot long, occasionally flits out from between [your] lips."
	),
	CAT(
		5, "cat",
		appearanceDesc = "[Your] tongue is rough like that of a cat. [You] sometimes [verb groom] [yourself] with it."
	),
	ELF(
		6, "elf",
		appearanceDesc = "[Your] voice is unnaturally beautiful and melodious."
	),
	DOG(
		7, "dog",
		appearanceDesc = "[You] sometime let [your] canine tongue out to vent heat."
	),
	CAVE_WYRM(
		8, "draconic",
		appearanceDesc = "A slowly undulating neon blue tongue that glow in the dark occasionally slips from between [your] lips. It hangs nearly two feet long when [you] [verb let] the whole thing slide out, though [you] can retract it to appear normal."
	),
	GHOST(
		9, "ghost",
		appearanceDesc = "Occasionally a long transparent tongue slicks out of [your] mouth, stretching out about a foot in length."
	),
	MELKIE(
		10, "melkie",
		appearanceDesc = "[Your] voice is unnaturally beautiful and melodious; [your] mermaid-like song is capable of captivating the minds of those who listens to it."
	),
	RATATOSKR(
		11, "ratatoskr",
		appearanceDesc = "[Your] tongue is human in appearance but way more dexterous at handling words, able to form sentences and words so good it becomes art or so bad that it bleeds the ears and drives who hear them insane."
	),
	RAVENOUS_TONGUE(
		12, "ravenous",
		appearanceDesc = "[You] constantly hunger for food and [your] ravenous tongue has gained some unnatural skills of its own, always ready to coax a penis or a pussy into cumming."
	),
	MINDBREAKER(
		13, "mindbreaker",
		appearanceDesc = "In [your] mouth hides a long tentacle-like tongue ready to probe the mouth of others."
	),
	;

	open fun appearanceDescription(creature: Creature): String = appearanceDesc

	companion object : CocIdLookup<TongueType>(values())
}
