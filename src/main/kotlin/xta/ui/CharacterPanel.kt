package xta.ui

import kotlinx.dom.addClass
import kotlinx.dom.clear
import kotlinx.dom.removeClass
import org.w3c.dom.HTMLElement
import xta.charview.CharViewImage
import xta.game.PlayerCharacter
import xta.game.settings.GameSettings
import xta.game.stats.PrimaryStat
import kotlin.math.roundToInt

/*
 * Created by aimozg on 02.12.2021.
 */
class CharacterPanel : UiTemplate("char-panel") {
	private val container = fragment.ref("container")
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
	private val btnRenderZoom = fragment.ref("render-zoombtn")
	private val btnRenderShow = fragment.ref("render-showbtn")
	private val renderDiv = fragment.ref("render")

	private var lastCharacter: PlayerCharacter? = null
	init {
		btnRenderZoom.onclick = {
			GameSettings.data.renderX2 = GameSettings.data.renderX2?.not()?:true
			GameSettings.save()
			refresh()
		}
		btnRenderShow.onclick = {
			GameSettings.data.render = GameSettings.data.render?.not()?:true
			GameSettings.save()
			refresh()
		}
	}
	fun hide() {
		container.style.display = "none"
	}
	fun show() {
		container.style.display = ""
	}
	fun refresh() {
		showCharacter(lastCharacter)
	}
	fun showCharacter(char: PlayerCharacter?,
	                  render: Boolean = GameSettings.data.render?:false,
	                  renderX2: Boolean = GameSettings.data.renderX2?:false
	) {
		lastCharacter = char
		btnRenderZoom.textContent = if (renderX2) "zoom_out" else "zoom_in"
		btnRenderShow.textContent = if (render) "visibility_off" else "visibility"
		hideTooltip()
		if (char == null) {
			hide()
			return
		}
		show()
		// TODO tooltips!
		titleDiv.textContent = char.name
		subtitleDiv.textContent = "Level ${char.level} ${char.raceFullName()}"
		val xpp = char.xp.toDouble() / char.xpToLevelUp()
		xpBar.style.width = "" + (xpp * 100).coerceIn(0.0, 100.0) + "%"
		if (xpp >= 1.0) {
			xpBar.addClass("-levelup")
		} else {
			xpBar.removeClass("-levelup")
		}
		// TODO mark buffed/debuffed stats with text-positive/text-negative classses
		strVal.textContent = char.str.roundToInt().toString()
		addBuffTooltip(strVal,char.strStat,"Strength")
		touVal.textContent = char.tou.roundToInt().toString()
		addBuffTooltip(touVal,char.touStat,"Toughness")
		speVal.textContent = char.spe.roundToInt().toString()
		addBuffTooltip(speVal,char.speStat,"Speed")
		intVal.textContent = char.int.roundToInt().toString()
		addBuffTooltip(intVal,char.intStat,"Intelligence")
		wisVal.textContent = char.wis.roundToInt().toString()
		addBuffTooltip(wisVal,char.wisStat,"Wisdom")
		libVal.textContent = char.lib.roundToInt().toString()
		addBuffTooltip(libVal,char.libStat,"Libido")
		senVal.textContent = char.sens.roundToInt().toString()
		corVal.textContent = char.cor.toString()
		hpBar.displayValue(
			value = char.hp.toDouble(),
			max = char.maxHp().toDouble(),
			text = char.hp.toString()
		)
		hpBar.container.addTooltip(
			"HP: ${char.hp}/${char.maxHp()}\n" +
					"(${char.hpRatio.times(100).roundToInt()}%)")
		lustBar.displayValue(
			value = char.lust.toDouble(),
			extra = char.minLust().toDouble(),
			max = char.maxLust().toDouble(),
			text = char.lust.toString()
		)
		lustBar.container.addTooltip(
			"Lust: ${char.lust}/${char.maxLust()}\n" +
					"(${char.lustRatio.times(100).roundToInt()}%)"
		)
		wrathBar.displayValue(
			value = char.wrath.toDouble(),
			max = char.maxWrath().toDouble(),
			text = char.wrath.toString()
		)
		wrathBar.container.addTooltip(
			"Wrath: ${char.wrath}/${char.maxWrath()}\n" +
					"(${char.wrathRatio.times(100).roundToInt()}%)"
		)
		staminaBar.displayValue(
			value = char.stamina.toDouble(),
			max = char.maxFatigue().toDouble(),
			text = char.stamina.toString()
		)
		staminaBar.container.addTooltip(
			"Stamina: ${char.stamina}/${char.maxFatigue()}\n" +
					"(${char.staminaRatio.times(100).roundToInt()}%)"
		)
		manaBar.displayValue(
			value = char.mana.toDouble(),
			max = char.maxMana().toDouble(),
			text = char.mana.toString()
		)
		manaBar.container.addTooltip(
			"Mana: ${char.mana}/${char.maxMana()}\n" +
					"(${char.manaRatio.times(100).roundToInt()}%)"
		)
		sfBar.displayValue(
			value = char.soulforce.toDouble(),
			max = char.maxSoulforce().toDouble(),
			text = char.soulforce.toString()
		)
		sfBar.container.addTooltip(
			"Soulforce: ${char.soulforce}/${char.maxSoulforce()}\n" +
					"(${char.sfRatio.times(100).roundToInt()}%)"
		)
		gemsValue.textContent = char.gems.toString()
		ssValue.textContent = "0" // TODO soulstones
		// TODO status effects (the cool kind)

		renderDiv.clear()
		if (render) {
			renderDiv.append(CharViewImage.INSTANCE.renderCharacter(char, renderX2).canvas)
		}
	}

	private fun addBuffTooltip(
		element: HTMLElement,
		stat: PrimaryStat,
		fullStatName: String
	) {
		var tooltip = fullStatName + "\n"
		tooltip += "Core: "+stat.core.value+"\n"
		for (buff in stat.mult.buffs) {
			val x = buff.value
			tooltip += buff.text+": "
			if (x > 0) tooltip += "+"
			tooltip += (x*100).roundToInt()
			tooltip += "%"

			tooltip += "\n"
		}
		for (buff in stat.bonus.buffs) {
			val x = buff.value
			tooltip += buff.text+": "
			if (x > 0) tooltip += "+"
			tooltip += x.roundToInt()

			tooltip += "\n"
		}
		element.addTooltip(tooltip)
	}
}
