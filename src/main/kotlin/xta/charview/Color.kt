package xta.charview

data class Color(
	val rgb: Int
) {
	constructor(r: Int, g: Int, b: Int) :
			this(
				((r and 0xff) shl 16) or
						((g and 0xff) shl 8) or
						(b and 0xff)
			)
	constructor(r:Double,g:Double,b:Double):
			this((r*255).toInt(),(g*255).toInt(),(b*255).toInt())

	val r get() = (rgb shr 16) and 0xff
	val g get() = (rgb shr 8) and 0xff
	val b get() = rgb and 0xff
	fun toHsl():IntArray {
		// Ported from https://github.com/bgrins/TinyColor (MIT licensed)
		val r = r/255.0
		val g = g/255.0
		val b = b/255.0
		val max = maxOf(r,g,b)
		val min = minOf(r,g,b)
		val l = (max+min)/2
		val h:Double
		val s:Double
		if (max == min) {
			h = 0.0
			s = 0.0
		} else {
			val d = max - min
			s = if (l > 0.5) d/(2-max-min) else d/(max+min)
			h = when {
				max == r && g < b ->
					(g-b)/d + 6
				max == r ->
					(g-b)/d
				max == g ->
					(b-r)/d+2
				else -> // max == b
					(r-g)/d+4
			}/6.0
		}
		return intArrayOf(
			(h*360).toInt(),
			(s*100).toInt(),
			(l*100).toInt()
		)
	}

	fun lighten(percentage:Int): Color {
		val hsl = toHsl()
		hsl[2] = (hsl[2] + percentage).coerceIn(0,100)
		return fromHsl(hsl)
	}
	fun darken(percentage:Int): Color = lighten(-percentage)
	fun saturate(percentage:Int): Color {
		val hsl = toHsl()
		hsl[1] = (hsl[1] + percentage).coerceIn(0,100)
		return fromHsl(hsl)
	}
	fun shiftTo(targetHue:Int, maxShift:Int): Color {
		val hsl = toHsl()
		val h = hsl[0]
		val diff = targetHue - h
		when {
			diff == 0 ->
				return this
			diff in -maxShift..+maxShift -> {
				hsl[0] = targetHue
			}
			diff > 180 -> {
				// Ex. target = 350, h = 10; diff is 330
				val mdiff = diff-360 // negative value, in this example -20
				hsl[0] = h + maxOf(mdiff, -maxShift)
			}
			diff < -180 -> {
				// Ex. target = 10, h = 350; diff is -330
				val mdiff = diff+360 // +20
				hsl[0] = h + minOf(mdiff, maxShift)
			}
			diff > 0 -> {
				hsl[0] = h + minOf(diff, maxShift)
			}
			diff < 0 -> {
				hsl[0] = h + maxOf(diff, -maxShift)
			}
		}
		if (hsl[0] < 0) hsl[0] += 360
		if (hsl[0] >= 360) hsl[0] -= 360
		return fromHsl(hsl)
	}

	companion object {
		fun fromHsl(hsl:IntArray): Color {
			// Ported from https://github.com/bgrins/TinyColor (MIT licensed)
			val h = hsl[0]/360.0
			val s = hsl[1]/100.0
			val l = hsl[2]/100.0
			if (s == 0.0) {
				return Color(l, l, l)
			}
			fun hue2rgb(p:Double,q:Double,t:Double):Double {
				return when {
					t < 0.0 -> hue2rgb(p,q,t+1)
					t > 1.0 -> hue2rgb(p,q,t-1)
					t < 1.0/6.0 -> p + (q - p) * 6 * t
					t < 1.0/2.0 -> q
					t < 2.0/3.0 -> p + (q - p) * (2.0/3.0 - t) * 6
					else -> p
				}
			}
			val q = if (l < 0.5) l*(1+s) else (l+s-l*s)
			val p = 2*l-q
			return Color(
				hue2rgb(p, q, h + 1.0 / 3.0),
				hue2rgb(p, q, h),
				hue2rgb(p, q, h - 1.0 / 3.0)
			)
		}
		fun fromRRGGBB(s:String): Color {
			val r = s.substring(1,3).toInt(16)
			val g = s.substring(3,5).toInt(16)
			val b = s.substring(5,7).toInt(16)
			return Color(r,g,b)
		}
		val ZERO = Color(0)
	}
}
