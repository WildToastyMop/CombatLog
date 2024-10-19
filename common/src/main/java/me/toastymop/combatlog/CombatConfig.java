package me.toastymop.combatlog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.parsers.json.JsonReader;
import org.quiltmc.parsers.json.JsonWriter;

import java.io.*;
// This class was taken from EMITrades please go check out EMI and its addons nothing but love for them <3
public class CombatConfig {
    public static CombatConfig.Config CONFIG;
    static File file = new File("./config/combatlog-common.json5");
    protected static final Logger log = LogManager.getLogger(CombatLog.LOGGER);

    @SuppressWarnings({"SwitchStatementWithTooFewBranches"})
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
                        case "deathMessage":
                            cfg.deathMessage = reader.nextString();
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
            writer.comment("Weather a player should be put in combat from just other players or all damage(true is all damage false is just players)")
                    .name("allDamage").value(cfg.allDamage);
            writer.comment("The death message that shows when a player disconnects while in combat, note that not having a space at the beginning will attach the message to the players name")
                    .name("deathMessage").value(cfg.deathMessage);
            writer.endObject();
        } catch (IOException e) {
            log.error("Failed to save config", e);
        }
    }

    public static class Config {
        public static Integer combatTime = 30;
        public static boolean allDamage = false;
        public static String deathMessage = " has died of cowardice";
    }
}