package xta.utils

/*
 * Created by aimozg on 06.12.2021.
 */

inline fun<T> MutableListIterator<T>.walk(body:(MutableListIterator<T>,value:T)->Unit) {
	for (ele in this) {
		body(this, ele)
	}
}
inline fun<T> MutableList<T>.walk(body:(MutableListIterator<T>,value:T)->Unit) {
	val li = this.listIterator()
	for (ele in li) {
		body(li, ele)
	}
}
