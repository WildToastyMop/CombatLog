package me.toastymop.combatlog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.parsers.json.JsonReader;
import org.quiltmc.parsers.json.JsonWriter;

import java.io.File;
import java.io.IOException;
// This class was taken from EMITrades please go check out EMI and its addons nothing but love for them <3
public class CombatConfig {
    public static Config CONFIG;
    static File file = new File("./config/combatlog-common.json5");
    protected static final Logger log = LogManager.getLogger(CombatLog.LOGGER);

    public static Config load() {
        if (!file.getName().endsWith(".json5"))
            throw new RuntimeException("Failed to read config");
        Config cfg = null;
        if (file.exists()) {
            try (JsonReader reader = JsonReader.json5(file.toPath())) {
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
        save(file, cfg);
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
            writer.comment("Weather a player should get a popup when they enter combat")
                    .name("combatNotice").value(cfg.combatNotice);
            writer.comment("The message that shows when a player is in combat, adding {timeLeft} will display how many seconds until combat is over")
                    .name("inCombat").value(cfg.inCombat);
            writer.comment("The message that shows when a player exits combat")
                    .name("outCombat").value(cfg.outCombat);
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
    }
}