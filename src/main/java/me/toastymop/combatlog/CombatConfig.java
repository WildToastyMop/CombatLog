package me.toastymop.combatlog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.parsers.json.JsonReader;
import org.quiltmc.parsers.json.JsonWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// This class was taken from EMITrades please go check out EMI and its addons nothing but love for them <3
public class CombatConfig {
    public static Config CONFIG;
    static File configFolder = new File("./config");
    static File configFile = new File(configFolder+"/combatlog-common.json5");
    protected static final Logger log = LogManager.getLogger(CombatLog.LOGGER);

    public static Config load() {
        if(!configFolder.exists()){
            configFolder.mkdirs();
        }
        if (!configFile.getName().endsWith(".json5"))
            throw new RuntimeException("Failed to read config");
        Config cfg = null;
        if (configFile.exists()) {
            try (JsonReader reader = JsonReader.json5(configFile.toPath())) {
                cfg = new Config();
                reader.beginObject();
                while (reader.hasNext()) {
                    String nextName = reader.nextName();
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
                        case "disableElytra":
                            cfg.disableElytra = reader.nextBoolean();
                            break;
                        case "disablePearl":
                            cfg.disablePearl = reader.nextBoolean();
                            break;
                        case "deathMessage":
                            cfg.deathMessage = reader.nextString();
                            break;
                        case "combatNotice":
                            cfg.combatNotice = reader.nextBoolean();
                            break;
                        case "inCombat":
                            cfg.inCombat = reader.nextString();
                            break;
                        case "outCombat":
                            cfg.outCombat = reader.nextString();
                            break;
                        case "blockedCommands":
                            cfg.blockedCommands = Arrays.asList(reader.nextString().split(","));
                            break;
                        case "blockedCommandMessage":
                            cfg.blockedCommandMessage = reader.nextString();
                            break;
                        case "disconnectKill":
                            cfg.disconnectKill = reader.nextBoolean();
                            break;
                        case "disconnectCommand":
                            cfg.disconnectCommand = reader.nextString();
                            break;
                        default:
                            reader.skipValue();
                            break;
                    }
                }
                reader.endObject();
                return cfg;
            } catch (IOException e) {
                log.error("Failed to parse config", e);
            }
        }
        if (cfg == null) cfg = new Config();
        save(configFile, cfg);
        return cfg;
    }
    public static void save(File file, Config cfg) {
        try (JsonWriter writer = JsonWriter.json5(file.toPath())) {
            writer.beginObject();
            writer.comment("The amount of time in seconds a player should be in combat")
                    .name("combatTime").value(cfg.combatTime);
            writer.comment("Weather a player should be put in combat from just other players or all damage")
                    .name("allDamage").value(cfg.allDamage);
            writer.comment("Weather a player should be put in combat from mobs")
                    .name("mobDamage").value(cfg.mobDamage);
            writer.comment("Weather a player should be able to use their elytra while in combat, this will not make them drop from the sky it simply restricts starting elytra flight")
                    .name("disableElytra").value(cfg.disableElytra);
            writer.comment("Weather a player should be able to use ender pearls while in combat")
                    .name("disablePearl").value(cfg.disablePearl);
            writer.comment("The death message that shows when a player disconnects while in combat, note that not having a space at the beginning will attach the message to the players name")
                    .name("deathMessage").value(cfg.deathMessage);
            writer.comment("Weather a player should get a popup when they enter combat or when trying to run blocked commands")
                    .name("combatNotice").value(cfg.combatNotice);
            writer.comment("The message that shows when a player is in combat, adding {timeLeft} will display how many seconds until combat is over")
                    .name("inCombat").value(cfg.inCombat);
            writer.comment("The message that shows when a player exits combat")
                    .name("outCombat").value(cfg.outCombat);
            writer.comment("This is a list of commands to be blocked while in combat, do not include the slash and use commas to separate them, leave empty to disable, example \"home,spawn,rtp\"")
                    .name("blockedCommands").value("");
            writer.comment("This is the message displayed in chat when a player attempts to use a blocked command")
                    .name("blockedCommandMessage").value(cfg.blockedCommandMessage);
            writer.comment("This determines if disconnecting while tagged kills the player")
                    .name("disconnectKill").value(cfg.disconnectKill);
            writer.comment("This is a command to be run when a tagged player disconnects, use {player} to autofill their name, leave blank to disable, example \"warn {player} combatlogging\"")
                    .name("disconnectCommand").value(cfg.disconnectCommand);
            writer.endObject();
        } catch (IOException e) {
            log.error("Failed to save config", e);
        }
    }
    public static class Config {
        public static Integer combatTime = 30;
        public static boolean allDamage = false;
        public static boolean mobDamage = false;
        public static boolean disableElytra = false;
        public static boolean disablePearl = false;
        public static String deathMessage = " has died of cowardice";
        public static boolean combatNotice = true;
        public static String inCombat = "You are in combat do not leave! {timeLeft} seconds left";
        public static String outCombat = "You are no longer in combat";
        public static List<String> blockedCommands = new ArrayList<>();
        public static String blockedCommandMessage = "You are in combat and cannot execute this command";
        public static boolean disconnectKill = true;
        public static String disconnectCommand = "";
    }
}