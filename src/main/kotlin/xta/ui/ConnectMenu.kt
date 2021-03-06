package xta.ui

import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLInputElement
import xta.Game
import xta.ScreenManager
import xta.game.settings.GameSettings
import xta.net.setupWsLobbyGuest
import xta.net.setupWsLobbyHost

class ConnectMenu(val asHost:Boolean) : UiScreen("connect-screen") {
	private val lobbyInput = fragment.ref<HTMLInputElement>("lobby-url")
	private val identityInput = fragment.ref<HTMLInputElement>("identity")
	private val tokenInput = fragment.ref<HTMLInputElement>("token")
	private val roomIdInput = fragment.ref<HTMLInputElement>("room")
	private val inviteInput = fragment.ref<HTMLInputElement>("invite")
	private val connectButton = fragment.ref<HTMLButtonElement>("connect-btn")
	private val backButton = fragment.ref<HTMLButtonElement>("back-btn")

	init {
		ScreenManager.chatEnabled = false
		lobbyInput.value = GameSettings.data.wsLobbyUrl
		identityInput.value = GameSettings.data.wsIdentity
		tokenInput.value = GameSettings.data.wsToken
		if (asHost) {
			fragment.ref("guest-block").remove()
			roomIdInput.value = GameSettings.data.wsHostRoom
		} else {
			fragment.ref("host-block").remove()
			inviteInput.value = GameSettings.data.wsJoinInvite
		}
		connectButton.onclick = {
			connectButton.disabled = true
			GameSettings.data.wsLobbyUrl = lobbyInput.value
			GameSettings.data.wsIdentity = identityInput.value
			// TODO saving passwords in localStorage is bad
			//      but at the same time, need to avoid excess configuration or overcomplicating things
			//      Use session tokens?
			GameSettings.data.wsToken = tokenInput.value
			if (asHost) {
				GameSettings.data.wsHostRoom = roomIdInput.value
			} else {
				GameSettings.data.wsJoinInvite = inviteInput.value
			}
			GameSettings.save()
			if (asHost) {
				setupWsLobbyHost(
					GameSettings.data.wsLobbyUrl,
					GameSettings.data.wsIdentity,
					tokenInput.value,
					GameSettings.data.wsHostRoom
				).catch {
					connectButton.disabled = false
					console.error(it)
					Game.localErrorMessage("Connection error: ${it.message}")
				}
			} else {
				setupWsLobbyGuest(
					GameSettings.data.wsLobbyUrl,
					GameSettings.data.wsIdentity,
					tokenInput.value,
					GameSettings.data.wsJoinInvite
				).catch {
					connectButton.disabled = false
					console.error(it)
					Game.localErrorMessage("Connection error: ${it.message}")
				}
			}
			Unit
		}
		backButton.onclick = {
			ScreenManager.showStartMenu()
		}
	}
}
