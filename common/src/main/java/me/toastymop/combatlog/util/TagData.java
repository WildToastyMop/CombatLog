package me.toastymop.combatlog.util;

import me.toastymop.combatlog.CombatConfig;
import net.minecraft.nbt.NbtCompound;

public class TagData {
    public static void decreaseTagTime(IEntityDataSaver player) {

        NbtCompound nbt = player.getPersistentData();
        int tagTime = nbt.getInt("combatTime");
        if(tagTime>0) {
            tagTime--;
            nbt.putInt("combatTime", tagTime);
        }

    }
    public static void setTagTime(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt("combatTime", CombatConfig.Config.combatTime * 20);
        nbt.putBoolean("inCombat", true);
    }

    public static void endCombat(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt("combatTime", 0);
        nbt.putBoolean("inCombat", false);
    }
    public static int getTagTime(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return nbt.getInt("combatTime");
    }
    public static boolean getCombat(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return nbt.getBoolean("inCombat");
    }
}
