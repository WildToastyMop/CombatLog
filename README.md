# CombatLog

A simple (not so much anymore) mod that tries to prevent players from leaving while in combat.

When one player hits another, both of them are tagged as in combat for a set time, 30 seconds by default, and the tag is refreshed with each hit. If a tagged player disconnects, they are killed and their items drop where they logged out. The kill can be turned off and replaced with a command.

## Features

- Tags both players on a hit, for a configurable time
- Countdown in the hotbar or on a boss bar
- Kills players who disconnect while tagged, with a configurable death message
- Can run a command on disconnect instead, or as well as
- Optional tagging from mobs, any damage, self damage and fire damage
- Blocks elytra takeoff, ender pearls, chosen items and blocks, and chosen commands while tagged
- Two command hooks, one when a player is tagged and one when a tagged player disconnects
- All messages and defaults in config/combatlog.toml, editable in game with /combatlog config

## Installation

Install it on the server. Clients do not need it, though it works in singleplayer and LAN worlds when installed there. Fabric, Forge and NeoForge builds are available for several Minecraft versions; the releases page lists them.

## Configuration

Settings live in config/combatlog.toml and are grouped into combat, restrictions, messages, notice, disconnect and hooks.

- /combatlog config list
- /combatlog config get, set, add, remove and reset <section>.<option> [value]
- /combatlog reload
- /combatlog set <player>

Blocked commands match the first word only, so blocking `home` also blocks `home bed`. The old json5 config is renamed to combatlog-common.deprecated.json5 on startup and is not imported.

## Building

IntelliJ with the Stonecutter Dev and Minecraft Development plugins, and Java 25, 21 and 17 installed. Use the Gradle wrapper.

## License

MIT. Modpacks are welcome to include it.
