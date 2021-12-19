package xta.game.stats

enum class BuffAggregate(val defaultBase: Double) {
	SUM(0.0),
	PRODUCT(1.0),
	MAX(Double.NEGATIVE_INFINITY),
	MIN(Double.POSITIVE_INFINITY)
}
