package xta.game.creature.body

import xta.flash.CocId
import xta.flash.CocIdLookup
import xta.game.Creature

enum class AntennaeType(
	override val cocID: Int,
	val displayName: String,
	private val appearanceDesc: String = ""
) : CocId {
	NONE(0, "non-existant"),
	MANTIS(
		1, "mantis",
		appearanceDesc = "[You] [have] long prehensile mantis antennae on [your] head, bouncing and swaying in the breeze."
	),
	BEE(
		2, "bee",
		appearanceDesc = "[You] [have] floppy bee antennae on [your] head, bouncing and swaying in the breeze."
	),
	COCKATRICE(3, "cockatrice"),
	FIRE_SNAIL(
		4, "fire snail",
		appearanceDesc = "Two horn like antennae grow from [your] head, feeling the air around and feeding [you] sensory information."
	),
	MOTH(
		5, "moth",
		appearanceDesc = "Floppy moth antennae grow from [your] head, bouncing and swaying in the breeze."
	),
	CENTIPEDE(
		6, "centipede",
		appearanceDesc = "A pair of long antennae have grown on [your] head, occasionally curling and twitching at the slightest of movements."
	),
	SEA_DRAGON(
		7, "sea dragon",
		appearanceDesc = "By the sides of [your] head are four bioluminescent strands that run down [your] neck, beautifully lighting up [your] front and giving [you] an air of mystique."
	),
	;

	open fun appearanceDescription(creature: Creature) = appearanceDesc

	companion object : CocIdLookup<AntennaeType>(values())
}
