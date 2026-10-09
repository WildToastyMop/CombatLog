package me.toastymop.combatlog.config;

import me.toastymop.combatlog.CombatLog;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

public class CombatConfig {
    static final File configFolder = new File("./config");
    static final File configFile = new File(configFolder, "combatlog.toml");
    static final File legacyFile = new File(configFolder, "combatlog-common.json5");
    static final File legacyMoved = new File(configFolder, "combatlog-common.deprecated.json5");
    protected static final Logger log = LogManager.getLogger(CombatLog.LOGGER);

    public static void load() {
        if (!configFolder.exists()) {
            configFolder.mkdirs();
        }
        if (legacyFile.exists()) {
            try {
                Files.move(legacyFile.toPath(), legacyMoved.toPath(), StandardCopyOption.REPLACE_EXISTING);
                log.info("Moved {} to {}, its settings were not imported", legacyFile, legacyMoved);
            } catch (IOException e) {
                log.warn("Could not move the old config {}", legacyFile);
            }
        }
        ConfigFile.load(configFile);
    }

    public static void save() {
        ConfigFile.save(configFile);
    }

    public static List<Item> resolveItems(List<String> ids) {
        List<Item> items = findItems(ids);
        if (Config.disablePearl && !items.contains(Items.ENDER_PEARL)) {
            items.add(Items.ENDER_PEARL);
        }
        return items;
    }

    public static class Config {
        public static Integer combatTime = 30;
        public static boolean allDamage = false;
        public static boolean mobDamage = false;
        public static boolean selfDamage = true;
        public static boolean fireDamage = true;
        public static boolean disableElytra = false;
        public static boolean disablePearl = false;
        public static List<String> blockedItemIds = new ArrayList<>();
        public static List<String> blockedBlockIds = new ArrayList<>();
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
        public static String attackerCredit = "last_hit";
        public static String disconnectCommand = "";
        public static String combatCommand = "";
    }

    public static List<Item> findItems(List<String> list){
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
