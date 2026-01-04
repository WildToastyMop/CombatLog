package me.toastymop.combatlog.util;

import me.toastymop.combatlog.CombatConfig;
import net.minecraft.nbt.CompoundTag;

public class TagData {
    public static void decreaseTagTime(IEntityDataSaver player) {

        CompoundTag nbt = player.getPersistentData();
        int tagTime = nbt.getIntOr("combatTime",0);
        if(tagTime>0) {
            tagTime--;
            nbt.putInt("combatTime", tagTime);
        }

    }
    public static void setTagTime(IEntityDataSaver player) {
        CompoundTag nbt = player.getPersistentData();
        nbt.putInt("combatTime", CombatConfig.Config.combatTime * 20);
        nbt.putBoolean("inCombat", true);
    }

    public static void endCombat(IEntityDataSaver player) {
        CompoundTag nbt = player.getPersistentData();
        nbt.putInt("combatTime", 0);
        nbt.putBoolean("inCombat", false);
    }
    public static int getTagTime(IEntityDataSaver player) {
        CompoundTag nbt = player.getPersistentData();
        return nbt.getIntOr("combatTime",0);
    }
    public static boolean getCombat(IEntityDataSaver player) {
        CompoundTag nbt = player.getPersistentData();
        return nbt.getBooleanOr("inCombat",false);
    }
}
