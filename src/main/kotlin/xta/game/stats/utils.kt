package xta.game.stats

/*
 * Created by aimozg on 05.12.2021.
 */

fun<T:IStat> T.addTo(store:StatStore):T {
	store.addStat(this)
	return this
}
