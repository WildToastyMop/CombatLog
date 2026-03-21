package me.toastymop.combatlog.util;

import me.toastymop.combatlog.CombatCheck;
import me.toastymop.combatlog.CombatConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;

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
        nbt.remove("attackerHistory");
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
    //? if >1.21.5 {
    public static void addAttacker(Player player, Player attacker, float dmg) {
        CompoundTag nbt = ((IEntityDataSaver)player).getPersistentData();
        
        ListTag attackersList = nbt.getListOrEmpty("attackerHistory");

        CompoundTag attackerData = new CompoundTag();
        attackerData.putString("Name", attacker.getName().getString());
        attackerData.putFloat("Damage", dmg);
        attackerData.putLong("Time", player.tickCount);

        attackersList.add(attackerData);

        nbt.put("attackerHistory", attackersList);
    }

    public static void updateAttacker(Player player, Player attacker, float dmg) {
        CompoundTag nbt = ((IEntityDataSaver)player).getPersistentData();
        ListTag attackers = nbt.getListOrEmpty("attackerHistory");

        for (int i = 0; i < attackers.size(); i++) {
            CompoundTag entry = attackers.getCompoundOrEmpty(i);
            if (entry.getStringOr("Name","").equals(attacker.getName().getString())) {
                entry.putFloat("Damage", dmg + entry.getFloatOr("Damage",0f));
                entry.putLong("Time", player.tickCount);
                return;
            }
        }
        addAttacker(player, attacker, dmg);
    }

    public static String getAttacker(IEntityDataSaver player) {
        CompoundTag persistentData = player.getPersistentData();
        ListTag attackers = persistentData.getListOrEmpty("attackerHistory");

        CompoundTag prevCompound = new CompoundTag();
        for (int i = 0; i < attackers.size(); i++) {
            CompoundTag entry = attackers.getCompoundOrEmpty(i);
            if (CombatConfig.Config.attackerCredit.equals("time")) {
                Long time = entry.getLongOr("Time",0L);
                if (time > prevCompound.getLongOr("Time",0L)) {
                    prevCompound = entry;
                }
            }
            if (CombatConfig.Config.attackerCredit.equals("damage")) {
                Float time = entry.getFloatOr("Damage",0f);
                if (time > prevCompound.getFloatOr("Damage",0f)) {
                    prevCompound = entry;
                }
            }
        }
        return prevCompound.getStringOr("Name","");
    }
     //?} else {
    /*public static void addAttacker(Player player, Player attacker, float dmg) {
        CompoundTag nbt = ((IEntityDataSaver)player).getPersistentData();
        
        ListTag attackersList = nbt.getList("attackerHistory", 10);

        CompoundTag attackerData = new CompoundTag();
        attackerData.putString("Name", attacker.getName().getString());
        attackerData.putFloat("Damage", dmg);
        attackerData.putLong("Time", player.tickCount);

        attackersList.add(attackerData);

        nbt.put("attackerHistory", attackersList);
    }

    public static void updateAttacker(Player player, Player attacker, float dmg) {
        CompoundTag nbt = ((IEntityDataSaver)player).getPersistentData();
        ListTag attackers = nbt.getList("attackerHistory", 10);

        for (int i = 0; i < attackers.size(); i++) {
            CompoundTag entry = attackers.getCompoundOrEmpty(i);
            if (entry.getString("Name").equals(attacker.getName().getString())) {
                entry.putFloat("Damage", dmg + entry.getFloat("Damage"));
                entry.putLong("Time", player.tickCount);
                return;
            }
        }
        addAttacker(player, attacker, dmg);
    }

    public static String getAttacker(IEntityDataSaver player) {
        CompoundTag persistentData = player.getPersistentData();
        ListTag attackers = persistentData.getList("attackerHistory", 10);

        CompoundTag prevCompound = new CompoundTag();
        for (int i = 0; i < attackers.size(); i++) {
            CompoundTag entry = attackers.getCompound(i);
            if (CombatConfig.Config.attackerCredit.equals("time")) {
                Long time = entry.getLong("Time");
                if (time > prevCompound.getLong("Time")) {
                    prevCompound = entry;
                }
            }
            if (CombatConfig.Config.attackerCredit.equals("damage")) {
                Float time = entry.getFloat("Damage");
                if (time > prevCompound.getFloat("Damage")) {
                    prevCompound = entry;
                }
            }
        }
        return prevCompound.getString("Name");
    }
    *///?}
}
