package xta.game.stats

import xta.game.Creature
import xta.net.serialization.JsonSerializable

open class PrimaryStat(
	val host: Creature,
	final override val statName: String
): JsonSerializable(), IStat, IStatHolder {

	val core: RawStat by nestedProperty(RawStat("$statName.core"))
	val mult: BuffPool by nestedProperty(BuffPool("$statName.mult", baseValue = 1.0, min = 0.0))
	val bonus: BuffPool by nestedProperty(BuffPool("$statName.bonus"))

	open val min get() = 1.0

	open val max get() = core.max * mult.value + maxOf(0.0, bonus.value)

	override val value: Double
		get() = maxOf(min, core.value * mult.value + bonus.value)

	override fun allStats(): List<IStat> = listOf(core, mult, bonus)
}
