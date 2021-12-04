package xta.charview

/*
 * Created by aimozg on 02.12.2021.
 */
val colordb by lazy {
	NamedColorDb().apply {
		for (palette in palette_json) {
			if (palette.name == "common") {
				for (color in palette.colors) {
					addCommonColor(color.name, Color.fromRRGGBB(color.rgb))
				}
			} else {
				for (color in palette.colors) {
					addColor(palette.name, color.name, Color.fromRRGGBB(color.rgb))
				}
			}
		}
	}
}
