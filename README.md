# Xianxia Tournament Arc

A multiplayer arena battle version of Corruption of Champions Xianxia mod.

## How to play

Download the release and open HTML file in browser.

Drop your CoCX save file and host or join the game.

Requires [lobby server](https://github.com/aimozg/wslobby) up and running.

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
