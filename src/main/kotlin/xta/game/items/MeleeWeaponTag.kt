package xta.game.items

enum class MeleeWeaponTag(
	val displayName: String
) {
	// Misc tags
	DUAL("Dual"),
	HYBRID("Hyrid"),
	// TODO consider making katana,rapier,tetsubo into a type or subtype
	KATANA("Katana"),
	RAPIER("Rapier"),
	STAFF_PART("Partially Staff"),
	TESTUBO("Tetsubo"),
	THROWN("Thrown"),
	WHIPPING("Whipping"),
	WHIRLWIND("Whirlwind"),
	// Size tags - TODO consider making a required separate property instead
	SMALL("Small"),
	LARGE("Large"),
	MASSIVE("Massive"),
	// Wrath tags
	LGWRATH("Low Grade Wrath"),
	// TODO refactor stun and bleed into "OnHit"
	// Stun tags
	STUN10("+10% Stun"),
	STUN15("+15% Stun"),
	STUN20("+20% Stun"),
	STUN25("+25% Stun"),
	STUN30("+30% Stun"),
	STUN40("+40% Stun"),
	STUN50("+50% Stun"),
	// Bleed tags
	BLEED10("+10% Bleed"),
	BLEED25("+25% Bleed"),
	BLEED45("+45% Bleed"),
	BLEED100("+100% Bleed"),
}
