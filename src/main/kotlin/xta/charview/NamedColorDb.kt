package xta.charview

class NamedColorDb {
	val commons = HashMap<String, Color>()
	val categories = HashMap<String,HashMap<String, Color>>()

	fun addCommonColor(colorName:String, color: Color) {
		commons[colorName] = color
	}
	fun addColor(category:String,colorName:String,color: Color) {
		categories.getOrPut(category) { HashMap() }[colorName] = color
	}
	fun find(category:String, colorName:String, defautlValue: Color = Color.ZERO): Color {
		return categories[category]?.get(colorName)
			?: commons[colorName]
			?: defautlValue
	}
}
