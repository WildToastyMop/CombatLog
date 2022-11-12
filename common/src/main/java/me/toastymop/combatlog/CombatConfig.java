package me.toastymop.combatlog;

import com.electronwill.nightconfig.core.file.FileConfig;

import java.io.*;

public class CombatConfig {
    static File file = new File("./config/combatlog-common.toml");
    public static int combatTime;
    public static Boolean allDamage;
    public static String deathMessage;
    public static void getConfig() {
        if (!file.exists()) {
            try {
                combatTime = 30;
                allDamage = false;
                deathMessage = "\" has died of cowardice\"";

                file.getParentFile().mkdirs();
                file.createNewFile();
                PrintWriter fos = new PrintWriter(new FileOutputStream(file, false));
                fos.println("[config]");
                fos.println("\t#The amount of time a player should be in combat");
                fos.println("\tcombatTime = "+ combatTime);
                fos.println("\t#Weather a player should be put in combat from just other players or all damage(true is all damage false is just players)");
                fos.println("\tallDamage = "+ allDamage.toString());
                fos.println("\t#The message that shows up when I player disconnects while in combat");
                fos.println("\t#If you don't add a space to the beginning it will look like \"(player)deathmessage\" instead of \"(player) deathmessage\" ingame");
                fos.println("\tdeathMessage = "+ deathMessage);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            FileConfig config = FileConfig.of(file);
            config.load();
            if((config.get("config.combatTime") instanceof Integer) && (config.get("config.allDamage") instanceof Boolean)  && (config.get("config.deathMessage") instanceof String)){
                combatTime = config.getInt("config.combatTime");
                allDamage = config.get("config.allDamage");
                deathMessage = config.get("config.deathMessage").toString();
            } else {
                File rename = new File("./config/combatlog-common.toml.bak");
                file.renameTo(rename);
                getConfig();
            }
        }
    }
}
