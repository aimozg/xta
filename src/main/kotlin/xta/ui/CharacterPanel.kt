package xta.ui

import kotlinx.dom.addClass
import kotlinx.dom.clear
import kotlinx.dom.removeClass
import xta.charview.CharViewImage
import xta.game.PlayerCharacter
import kotlin.math.roundToInt

/*
 * Created by aimozg on 02.12.2021.
 */
class CharacterPanel : UiTemplate("char-panel") {
	private val titleDiv = fragment.ref("title")
	private val subtitleDiv = fragment.ref("subtitle")
	private val xpBar = fragment.ref("xpbar")
	private val strVal = fragment.ref("stat-str")
	private val touVal = fragment.ref("stat-tou")
	private val speVal = fragment.ref("stat-spe")
	private val intVal = fragment.ref("stat-int")
	private val wisVal = fragment.ref("stat-wis")
	private val libVal = fragment.ref("stat-lib")
	private val senVal = fragment.ref("stat-sen")
	private val corVal = fragment.ref("stat-cor")
	private val hpBar = BarGauge().also { it.insertTo(fragment.ref("bar-hp")) }
	private val lustBar = BarGauge().also { it.insertTo(fragment.ref("bar-lust")) }
	private val wrathBar = BarGauge().also { it.insertTo(fragment.ref("bar-wrath")) }
	private val staminaBar = BarGauge().also { it.insertTo(fragment.ref("bar-stamina")) }
	private val manaBar = BarGauge().also { it.insertTo(fragment.ref("bar-mana")) }
	private val sfBar = BarGauge().also { it.insertTo(fragment.ref("bar-sf")) }
	private val gemsValue = fragment.ref("gems")
	private val ssValue = fragment.ref("soulstones")

	private val renderDiv = fragment.ref("render")

	fun showCharacter(char: PlayerCharacter, render: Boolean) {
		// TODO tooltips!
		titleDiv.textContent = char.name
		subtitleDiv.textContent = "Level ${char.level} ${char.race()}"
		val xpp = char.xp.toDouble() / char.xpToLevelUp()
		xpBar.style.width = "" + (xpp * 100).coerceIn(0.0, 100.0) + "%"
		if (xpp >= 1.0) {
			xpBar.addClass("-levelup")
		} else {
			xpBar.removeClass("-levelup")
		}
		// TODO mark buffed/debuffed stats with text-positive/text-negative classses
		strVal.textContent = char.str.roundToInt().toString()
		touVal.textContent = char.tou.roundToInt().toString()
		speVal.textContent = char.spe.roundToInt().toString()
		intVal.textContent = char.int.roundToInt().toString()
		wisVal.textContent = char.wis.roundToInt().toString()
		libVal.textContent = char.lib.roundToInt().toString()
		senVal.textContent = char.sens.roundToInt().toString()
		corVal.textContent = char.cor.toString()
		hpBar.displayValue(
			value = char.hp.toDouble(),
			max = char.maxHp().toDouble(),
			text = "${char.hp}/${char.maxHp()}"
		)
		lustBar.displayValue(
			value = char.lust.toDouble(),
			extra = char.minLust().toDouble(),
			max = char.maxLust().toDouble(),
			text = "${char.lust}/${char.maxLust()}"
		)
		wrathBar.displayValue(
			value = char.wrath.toDouble(),
			max = char.maxWrath().toDouble(),
			text = "${char.wrath}/${char.maxWrath()}"
		)
		staminaBar.displayValue(
			value = char.stamina.toDouble(),
			max = char.maxFatigue().toDouble(),
			text = "${char.stamina}/${char.maxFatigue()}"
		)
		manaBar.displayValue(
			value = char.mana.toDouble(),
			max = char.maxMana().toDouble(),
			text = "${char.mana}/${char.maxMana()}"
		)
		sfBar.displayValue(
			value = char.soulforce.toDouble(),
			max = char.maxSoulforce().toDouble(),
			text = "${char.soulforce}/${char.maxSoulforce()}"
		)
		gemsValue.textContent = char.gems.toString()
		ssValue.textContent = "0" // TODO soulstones
		// TODO status effects (the cool kind)

		renderDiv.clear()
		if (render) {
			renderDiv.append(CharViewImage.INSTANCE.renderCharacter(char).canvas)
		}
	}
}
