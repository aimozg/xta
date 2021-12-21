# Xianxia Tournament Arc

A multiplayer arena battle version of [Corruption of Champions Xianxia mod](https://github.com/Ormael7/Corruption-of-Champions).

This is a proof-of-concept demo.

## How to play

Download the release and open HTML file in browser.

Drop your CoCX save file and host or join the game.

Requires [lobby server](https://github.com/aimozg/wslobby) up and running.

<!--
### Hosting a game server on your PC

TODO needs to be written (and implemented on lobby server) 
-->

### Hosting an online game server on Heroku

1. Requires an account on [Heroku](https://heroku.com/) and on GitHub.
2. Fork a [lobby server](https://github.com/aimozg/wslobby) to your GitHub account.
3. In Heroku, create new app.
4. Select Deployment method = GitHub.
5. Connect to GitHub, search for `wslobby` repository, and connect to it.
6. Enable automatic deploys or deploy branch `master`.

The lobby server is operational now. **It will let anyone to claim and join any game room**. As per Heroku policy, it will shutdown after 30 minutes of inactivity, and automatically start-up when someone tries to connect to it. 

To configure it to serve game itself online:

7. Go to "Settings" tab of the app. Reveal Config Vars. 
8. Add a variable with key = `WSLOBBY_BUNDLE_URL` and value = URL of game release ZIP file = `https://github.com/aimozg/xta/releases/download/nightly/xta.zip`
9. Click "Open app" to test the deployment.

Lobby server will re-download the game when restarted (including restaring after 30 minutes of inactivity). To force update, click "More > Restart all dynos" in the Heroku app menu.

## Implementation details

All communications are done through a [WebSocket lobby server](https://github.com/aimozg/wslobby).

Game logic runs in host player's browser.

### Network protocol overview

Guest player

1. Connect to a lobby server and authorize as a guest.
2. Join a room.
3. Offer a character.
4. Request screen status and display it.
5. Send action requests, receive scene updates.

Host player

1. Connect to a lobby server an authorize as a host.
2. Claim the room. Join requests from guests will be forwarded to host and validated by it.
3. Process guests' requests. 

Host player is treated as a local guest.

## Developing

Recommended IDE is [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) Community or Ultimate Edition.

This repository contains [Gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) to build and run from command line, invoked with `./gradlew.bat` (Windows) or `./gradlew` (other platforms).

#### Running a development build with hot reload

```
gradlew browserDevelopmentRun --continuous
```
(`BrowserDevelopmentRun in continuous mode` run target in IDEA)

This will compile the game, open it in a browser, and when you edit the sources, re-compile and refresh the page.

#### Packaging a distribution

```
gradlew browserProductionWebpack
```

Results are placed in `build/distributions`.
