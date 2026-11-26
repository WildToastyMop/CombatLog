package me.toastymop.combatlog.config;

import net.minecraft.core.registries.BuiltInRegistries;

import java.util.ArrayList;
import java.util.List;

public class ConfigRegistry {
	public static final List<ConfigOption<?>> OPTIONS = List.of(
			new ConfigOption<>("combat.duration_seconds", "How long a player stays tagged in combat, in seconds.",
					30, ConfigValue.INT,
					() -> CombatConfig.Config.combatTime, value -> CombatConfig.Config.combatTime = value),
			new ConfigOption<>("combat.any_damage", "Tag a player when damaged by anything, not just another player.",
					false, ConfigValue.BOOL,
					() -> CombatConfig.Config.allDamage, value -> CombatConfig.Config.allDamage = value),
			new ConfigOption<>("combat.mob_damage", "Tag a player when a mob or other living entity damages them.",
					false, ConfigValue.BOOL,
					() -> CombatConfig.Config.mobDamage, value -> CombatConfig.Config.mobDamage = value),
			new ConfigOption<>("combat.self_damage", "Tag a player when they damage themselves.",
					true, ConfigValue.BOOL,
					() -> CombatConfig.Config.selfDamage, value -> CombatConfig.Config.selfDamage = value),
			new ConfigOption<>("combat.fire_damage", "Tag a player from fire damage, e.g. Fire Aspect or lava.",
					true, ConfigValue.BOOL,
					() -> CombatConfig.Config.fireDamage, value -> CombatConfig.Config.fireDamage = value),

			new ConfigOption<>("restrictions.elytra", "Stops players starting elytra flight while tagged. Players already in the air are not pulled down.",
					false, ConfigValue.BOOL,
					() -> CombatConfig.Config.disableElytra, value -> CombatConfig.Config.disableElytra = value),
			new ConfigOption<>("restrictions.ender_pearls", "Stops tagged players throwing ender pearls, and a pearl already in flight is removed when it hits instead of teleporting. Pearls are added to blocked items automatically.",
					false, ConfigValue.BOOL,
					() -> CombatConfig.Config.disablePearl, value -> {
						CombatConfig.Config.disablePearl = value;
						CombatConfig.Config.disabledItems = CombatConfig.resolveItems(CombatConfig.Config.blockedItemIds);
					}),
			new ConfigOption<>("restrictions.blocked_items", "Items that cannot be used while tagged, as ids like minecraft:firework_rocket. Only right-click actions are affected. Use mod:* for a whole mod, e.g. waystones:*.",
					List.<String>of(), ConfigValue.list(ConfigRegistry::itemIds),
					() -> CombatConfig.Config.blockedItemIds, value -> {
						CombatConfig.Config.blockedItemIds = value;
						CombatConfig.Config.disabledItems = CombatConfig.resolveItems(value);
					}),
			new ConfigOption<>("restrictions.blocked_blocks", "Blocks that cannot be right-clicked while tagged, as ids like minecraft:chest or waystones:waystone. Only right-click interactions are affected. Use mod:* for a whole mod, e.g. waystones:*.",
					List.<String>of(), ConfigValue.list(ConfigRegistry::blockIds),
					() -> CombatConfig.Config.blockedBlockIds, value -> {
						CombatConfig.Config.blockedBlockIds = value;
						CombatConfig.Config.disabledBlocks = CombatConfig.findItems(value);
					}),
			new ConfigOption<>("restrictions.blocked_commands", "Commands blocked while tagged, without the slash, e.g. home,spawn,rtp. Matches the first word only, so blocking home also blocks home bed.",
					List.<String>of(), ConfigValue.list(ArrayList::new),
					() -> CombatConfig.Config.blockedCommands, value -> CombatConfig.Config.blockedCommands = value),

			new ConfigOption<>("messages.in_combat", "The message shown while a player is tagged, {timeLeft} displays how many seconds until combat is over.",
					"You are in combat do not leave! {timeLeft} seconds left", ConfigValue.STRING,
					() -> CombatConfig.Config.inCombat, value -> CombatConfig.Config.inCombat = value),
			new ConfigOption<>("messages.out_of_combat", "The message shown when a player exits combat.",
					"You are no longer in combat", ConfigValue.STRING,
					() -> CombatConfig.Config.outCombat, value -> CombatConfig.Config.outCombat = value),
			new ConfigOption<>("messages.blocked_command", "The message shown when a player attempts to use a blocked command.",
					"You are in combat and cannot execute this command", ConfigValue.STRING,
					() -> CombatConfig.Config.blockedCommandMessage, value -> CombatConfig.Config.blockedCommandMessage = value),
			new ConfigOption<>("messages.blocked_block", "The message shown when a player attempts to use a blocked block or item.",
					"You are in combat and cannot use this", ConfigValue.STRING,
					() -> CombatConfig.Config.disabledBlocksMessage, value -> CombatConfig.Config.disabledBlocksMessage = value),
			new ConfigOption<>("messages.disconnect_death", "The death message when a tagged player disconnects. {player} and {attacker} are filled in, e.g. \"{player} was killed by {attacker}\". {attacker} is blank when no other player was involved.",
					"{player} has died of cowardice", ConfigValue.STRING,
					() -> CombatConfig.Config.deathMessage, value -> CombatConfig.Config.deathMessage = value),

			new ConfigOption<>("notice.enabled", "Show the combat countdown and the messages for blocked commands, items and blocks. Turning it off leaves players with no feedback.",
					true, ConfigValue.BOOL,
					() -> CombatConfig.Config.combatNotice, value -> CombatConfig.Config.combatNotice = value),
			new ConfigOption<>("notice.style", "Where the combat notice appears, use \"hotbar\" or \"bossbar\".",
					"hotbar", ConfigValue.oneOf("hotbar", "bossbar"),
					() -> CombatConfig.Config.noticeType, value -> CombatConfig.Config.noticeType = value),

			new ConfigOption<>("disconnect.kill", "Whether disconnecting while tagged kills the player.",
					true, ConfigValue.BOOL,
					() -> CombatConfig.Config.disconnectKill, value -> CombatConfig.Config.disconnectKill = value),
			new ConfigOption<>("disconnect.attacker_credit", "Who gets credit for the kill: \"last_hit\" or \"most_damage\".",
					"last_hit", ConfigValue.oneOf("last_hit", "most_damage"),
					() -> CombatConfig.Config.attackerCredit, value -> CombatConfig.Config.attackerCredit = value),

			new ConfigOption<>("hooks.on_tag", "Command to run when a player is tagged, with {player} and {tagTime} filled in, e.g. \"effect give {player} glowing {tagTime}\". Runs for both players on every hit, not only when combat starts. Leave blank to disable.",
					"", ConfigValue.STRING,
					() -> CombatConfig.Config.combatCommand, value -> CombatConfig.Config.combatCommand = value),
			new ConfigOption<>("hooks.on_disconnect", "Command to run when a tagged player disconnects, with {player} and {attacker} filled in, e.g. \"say {player} combat logged\". Runs in addition to the kill. Leave blank to disable.",
					"", ConfigValue.STRING,
					() -> CombatConfig.Config.disconnectCommand, value -> CombatConfig.Config.disconnectCommand = value)
	);

	public static ConfigOption<?> find(String path) {
		for (ConfigOption<?> option : OPTIONS) {
			if (option.path.equals(path)) return option;
		}
		return null;
	}

	public static List<String> itemIds() {
		List<String> ids = new ArrayList<>();
		BuiltInRegistries.ITEM.forEach(item -> ids.add(BuiltInRegistries.ITEM.getKey(item).toString()));
		return ids;
	}

	public static List<String> blockIds() {
		List<String> ids = new ArrayList<>();
		BuiltInRegistries.BLOCK.forEach(block -> ids.add(BuiltInRegistries.BLOCK.getKey(block).toString()));
		return ids;
	}
}
