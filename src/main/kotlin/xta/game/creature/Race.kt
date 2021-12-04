package xta.game.creature

import xta.game.Creature

/*
 * Created by aimozg on 28.11.2021.
 */
abstract class Race {
	abstract val name: String

	open fun nameOf(creature: Creature) = name

	abstract fun score(creature: Creature): Int

}
