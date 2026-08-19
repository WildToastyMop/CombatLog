package me.toastymop.combatlog;

import me.toastymop.combatlog.util.IEntityDataSaver;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.parsers.json.JsonReader;
import org.quiltmc.parsers.json.JsonWriter;

//? if >=1.21.11 {
import net.minecraft.resources.Identifier;
//?} else {
/*import net.minecraft.resources.Identifier;
*///?}

//? if >=1.21.6 {
import net.minecraft.core.Holder;
//?}

//? if >=1.20.1
import net.minecraft.core.registries.BuiltInRegistries;

//? if >=1.16.5 && <1.21.1
//import net.minecraft.core.Registry;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

// This class was taken from EMITrades please go check out EMI and its addons nothing but love for them <3
public class CombatConfig {
    public static Config CONFIG;
    static File configFolder = new File("./config");
    static File configFile = new File(configFolder+"/combatlog-common.json5");
    protected static final Logger log = LogManager.getLogger(CombatLog.LOGGER);
    static String plainItems = "";
    static String plainBlocks = "";
    static String plainCommands = "";

    public static Config load() {
        if (!configFolder.exists()) {
            configFolder.mkdirs();
        }
        if (!configFile.getName().endsWith(".json5"))
            throw new RuntimeException("Failed to read config");
        Config cfg = null;
        if (configFile.exists()) {
            Set<String> foundKeys = new HashSet<>();
            try (JsonReader reader = JsonReader.json5(configFile.toPath())) {
                cfg = new Config();
                reader.beginObject();
                while (reader.hasNext()) {
                    String nextName = reader.nextName();
                    foundKeys.add(nextName);
                    switch (nextName) {
                        case "combatTime":
                            cfg.combatTime = reader.nextInt();
                            break;
                        case "allDamage":
                            cfg.allDamage = reader.nextBoolean();
                            break;
                        case "mobDamage":
                            cfg.mobDamage = reader.nextBoolean();
                            break;
						case "selfDamage":
							cfg.selfDamage = reader.nextBoolean();
							break;
						case "fireDamage":
							cfg.fireDamage = reader.nextBoolean();
							break;
                        case "disableElytra":
                            cfg.disableElytra = reader.nextBoolean();
                            break;
						case "disablePearl":
							cfg.disablePearl = reader.nextBoolean();
							break;
                        case "disabledItems":
                            plainItems = reader.nextString();
							if (Config.disablePearl && !plainItems.contains("ender_pearl")) {
								plainItems = plainItems.isEmpty() ? "ender_pearl" : plainItems + ",ender_pearl";
							}
                            cfg.disabledItems = findItems(Arrays.asList(plainItems.split(",")));
                            break;
                        case "disabledBlocks":
                            plainBlocks = reader.nextString();
                            cfg.disabledBlocks = findItems(Arrays.asList(plainBlocks.split(",")));
                            break;
                        case "deathMessage":
                            cfg.deathMessage = reader.nextString();
                            break;
                        case "combatNotice":
                            cfg.combatNotice = reader.nextBoolean();
                            break;
						case "noticeType":
							cfg.noticeType = reader.nextString();
							break;
                        case "inCombat":
                            cfg.inCombat = reader.nextString();
                            break;
                        case "outCombat":
                            cfg.outCombat = reader.nextString();
                            break;
                        case "blockedCommands":
                            plainCommands = reader.nextString();
                            cfg.blockedCommands = Arrays.asList(plainCommands.split(","));
                            break;
                        case "blockedCommandMessage":
                            cfg.blockedCommandMessage = reader.nextString();
                            break;
                        case "disabledBlocksMessage":
                            cfg.disabledBlocksMessage = reader.nextString();
                            break;
                        case "disconnectKill":
                            cfg.disconnectKill = reader.nextBoolean();
                            break;
                        case "attackerCredit":
                            cfg.attackerCredit = reader.nextString();
                            break;
                        case "disconnectCommand":
                            cfg.disconnectCommand = reader.nextString();
                            break;
						case "combatCommand":
							cfg.combatCommand = reader.nextString();
							break;
                        default:
                            reader.skipValue();
                            break;
                    }
                }
                reader.endObject();

                Set<String> requiredKeys = Set.of(
                        "combatTime", "allDamage", "mobDamage", "selfDamage", "fireDamage", "disableElytra", "disablePearl",
                        "disabledItems", "disabledBlocks", "deathMessage", "combatNotice", "noticeType",
                        "inCombat", "outCombat", "blockedCommands", "blockedCommandMessage",
                        "disabledBlocksMessage", "disconnectKill", "attackerCredit", "disconnectCommand", "combatCommand"
                );

                if (!foundKeys.containsAll(requiredKeys)) {
                    save(configFile, cfg);
                }

                return cfg;
            } catch (IOException e) {
                log.error("Failed to parse config", e);
            }
        }
        if (cfg == null) cfg = new Config();
        try {
            Files.move(configFile.toPath(), new File(configFile + ".bak").toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ignored) {}
        save(configFile, cfg);
        return cfg;
    }
    public static void save(File file, Config cfg) {
        try (JsonWriter writer = JsonWriter.json5(file.toPath())) {
            writer.beginObject();
            writer.comment("The amount of time in seconds a player should be in combat")
                    .name("combatTime").value(cfg.combatTime);
            writer.comment("Whether a player should be put in combat from just other players or all damage")
                    .name("allDamage").value(cfg.allDamage);
            writer.comment("Whether a player should be put in combat from mobs")
                    .name("mobDamage").value(cfg.mobDamage);
			writer.comment("Whether a player should be put in combat from hurting themselves")
					.name("selfDamage").value(cfg.selfDamage);
			writer.comment("Whether a player should be put in combat from targeted fire damage (fire aspect)")
					.name("fireDamage").value(cfg.fireDamage);
            writer.comment("Whether a player should be able to use their elytra while in combat, this will not make them drop from the sky it simply restricts starting elytra flight")
                    .name("disableElytra").value(cfg.disableElytra);
			writer.comment("Whether a player should be able to use ender pearls in combat")
					.name("disablePearl").value(cfg.disablePearl);
            writer.comment("This is a list of item ids to disable while in combat, use commas to separate them and leave empty to disable, only items that do something when right-clicked. example \"minecraft:firework_rocket,minecraft:ender_pearl,minecraft:water_bucket\"")
                    .name("disabledItems").value(plainItems);
            writer.comment("This is a list of block ids to disable while in combat, use commas to separate them and leave empty to disable, only blocks that do something when right-clicked. example \"minecraft:chest,minecraft:oak_door,waystones:waystone\"")
                    .name("disabledBlocks").value(plainBlocks);
            writer.comment("The death message that shows when a player disconnects while in combat, use {player} to autofill their name")
                    .name("deathMessage").value(cfg.deathMessage);
            writer.comment("Whether a player should get a popup when they enter combat or when trying to run blocked commands")
                    .name("combatNotice").value(cfg.combatNotice);
			writer.comment("Whether the combat notice should appear above the hotbar or as a bossbar use \"hotbar\" or \"bossbar\"")
					.name("noticeType").value(cfg.noticeType);
            writer.comment("The message that shows when a player is in combat, adding {timeLeft} will display how many seconds until combat is over")
                    .name("inCombat").value(cfg.inCombat);
            writer.comment("The message that shows when a player exits combat")
                    .name("outCombat").value(cfg.outCombat);
            writer.comment("This is a list of commands to be blocked while in combat, do not include the slash and use commas to separate them, leave empty to disable, example \"home,spawn,rtp\"")
                    .name("blockedCommands").value(plainCommands);
            writer.comment("This is the message displayed in chat when a player attempts to use a blocked command")
                    .name("blockedCommandMessage").value(cfg.blockedCommandMessage);
            writer.comment("This is the message displayed in chat when a player attempts to use a block")
                    .name("disabledBlocksMessage").value(cfg.disabledBlocksMessage);
            writer.comment("This determines if disconnecting while tagged kills the player")
                    .name("disconnectKill").value(cfg.disconnectKill);
            writer.comment("This determines if the last person to attack or the person with the most damage gets credit, use \"time\" or \"damage\"")
                    .name("attackerCredit").value(cfg.attackerCredit);
            writer.comment("This is a command to be run when a tagged player disconnects, use {player} and {attacker} to autofill their names, leave blank to disable, example \"warn {player} combatlogging\"")
                    .name("disconnectCommand").value(cfg.disconnectCommand);
			writer.comment("This is a command to be run when a player is put into combat, use {player} to autofill their name, and {tagTime} for the duration of combat, runs on BOTH players every time they take damage NOT just the start, leave blank to disable, example \"effect give {player} glowing {tagtime} \"")
					.name("combatCommand").value(cfg.combatCommand);
            writer.endObject();
        } catch (IOException e) {
            log.error("Failed to save config", e);
        }
    }
    public static class Config {
        public static Integer combatTime = 30;
        public static boolean allDamage = false;
        public static boolean mobDamage = false;
		public static boolean selfDamage = true;
		public static boolean fireDamage = true;
        public static boolean disableElytra = false;
		public static boolean disablePearl = false;
        public static List<Item> disabledItems = new ArrayList<>();
        public static List<Item> disabledBlocks = new ArrayList<>();
        public static String deathMessage = "{player} has died of cowardice";
        public static boolean combatNotice = true;
		public static String noticeType = "hotbar";
        public static String inCombat = "You are in combat do not leave! {timeLeft} seconds left";
        public static String outCombat = "You are no longer in combat";
        public static List<String> blockedCommands = new ArrayList<>();
        public static String blockedCommandMessage = "You are in combat and cannot execute this command";
        public static String disabledBlocksMessage = "You are in combat and cannot use this";
        public static boolean disconnectKill = true;
        public static String attackerCredit = "time";
        public static String disconnectCommand = "";
		public static String combatCommand = "";
    }

    private static List<Item> findItems(List<String> list){
        List<Item> finalList = new ArrayList<>();

        for (String s : list) {
            if(s.endsWith(":*")) {
                String namespace = s.substring(0, s.indexOf(":"));

                if (namespace.isEmpty()) continue;
                //? if >=1.20.1 {
                BuiltInRegistries.ITEM.forEach(item -> {
                //?} else
                /*Registry.ITEM.forEach(item -> {*/
                    //? if >= 1.21.11
                    Identifier id = BuiltInRegistries.ITEM.getKey(item);

                    //? if >=1.20.1 && <=1.21.10
                    //Identifier id = BuiltInRegistries.ITEM.getKey(item);

                    //? if >=1.16.5 && <1.20.1
                    /*Identifier id = Registry.ITEM.getKey(item);*/

                    if (namespace.equals(id.getNamespace()) && item != Items.AIR) {
                        finalList.add(item);
                    }
                });
            } else {
                //? if >=1.21.11
                Optional<Holder.Reference<Item>> item = BuiltInRegistries.ITEM.get(Identifier.parse(s));

                //? if >=1.21.6 && <1.21.11
                //Optional<Holder.Reference<Item>> item = BuiltInRegistries.ITEM.get(Identifier.parse(s));

                //? if >=1.21.6 {
                finalList.add(item.map(Holder.Reference::value).orElse(null));
                //?}

                //? if >=1.21.1 && <1.21.6 {
                /*Item item = BuiltInRegistries.ITEM.get(Identifier.parse(s));
                finalList.add(item);
                *///?}

                //? if >=1.20.1 && <1.21.1 {
                /*Item item = BuiltInRegistries.ITEM.get(new Identifier(s));
                finalList.add(item);
                *///?}

                //? if >=1.16.5 && <1.20.1 {
                /*Item item = Registry.ITEM.get(new Identifier(s));
                finalList.add(item);
                *///?}

            }
        }
        return finalList;
    }
}
