package me.toastymop.combatlog.util;

import me.toastymop.combatlog.CombatCheck;
import me.toastymop.combatlog.CombatConfig;
import net.minecraft.nbt.CompoundTag;

public class TagData {
    public static void decreaseTagTime(IEntityDataSaver player) {

        CompoundTag nbt = player.getPersistentData();
        //? if >1.21.5 {
        int tagTime = nbt.getIntOr("combatTime",0);
        //?} else {
        /*int tagTime = nbt.getInt("combatTime");
        *///?}
        if(tagTime>0) {
            tagTime--;
            nbt.putInt("combatTime", tagTime);
        }

    }
    public static void setTagTime(IEntityDataSaver player) {
        CompoundTag nbt = player.getPersistentData();
        nbt.putInt("combatTime", (int) (CombatConfig.Config.combatTime * CombatCheck.tickRate));
        nbt.putBoolean("inCombat", true);
    }

    public static void endCombat(IEntityDataSaver player) {
        CompoundTag nbt = player.getPersistentData();
        nbt.putInt("combatTime", 0);
        nbt.putBoolean("inCombat", false);
    }
    public static int getTagTime(IEntityDataSaver player) {
        CompoundTag nbt = player.getPersistentData();
        //? if >1.21.5 {
        return nbt.getIntOr("combatTime",0);
         //?} else {
        /*return nbt.getInt("combatTime");
        *///?}

    }
    public static boolean getCombat(IEntityDataSaver player) {
        CompoundTag nbt = player.getPersistentData();
        //? if >1.21.5 {
        return nbt.getBooleanOr("inCombat",false);
         //?} else {
        /*return nbt.getBoolean("inCombat");
        *///?}

    }
}
