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
        String attackerName = attacker.getName().getString();

        for (int i = 0, size = attackers.size(); i < size; i++) {
            CompoundTag entry = attackers.getCompoundOrEmpty(i);
            if (entry.getStringOr("Name","").equals(attackerName)) {
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
        if (attackers.isEmpty()) return "";

        boolean byDamage = "damage".equals(CombatConfig.Config.attackerCredit);
        int bestIndex = -1;
        long maxTime = -1L;
        float maxDamage = -1.0f;

        for (int i = 0, size = attackers.size(); i < size; i++) {
            CompoundTag entry = attackers.getCompoundOrEmpty(i);
            if (byDamage) {
                float damage = entry.getFloatOr("Damage", 0f);
                if (bestIndex == -1 || damage > maxDamage) {
                    maxDamage = damage;
                    bestIndex = i;
                }
            } else {
                long time = entry.getLongOr("Time", 0L);
                if (bestIndex == -1 || time > maxTime) {
                    maxTime = time;
                    bestIndex = i;
                }
            }
        }
        return bestIndex >= 0 ? attackers.getCompoundOrEmpty(bestIndex).getStringOr("Name", "") : "";
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
        String attackerName = attacker.getName().getString();

        for (int i = 0, size = attackers.size(); i < size; i++) {
            CompoundTag entry = attackers.getCompound(i);
            if (entry.getString("Name").equals(attackerName)) {
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
        if (attackers.isEmpty()) return "";

        boolean byDamage = "damage".equals(CombatConfig.Config.attackerCredit);
        int bestIndex = -1;
        long maxTime = -1L;
        float maxDamage = -1.0f;

        for (int i = 0, size = attackers.size(); i < size; i++) {
            CompoundTag entry = attackers.getCompound(i);
            if (byDamage) {
                float damage = entry.getFloat("Damage");
                if (bestIndex == -1 || damage > maxDamage) {
                    maxDamage = damage;
                    bestIndex = i;
                }
            } else {
                long time = entry.getLong("Time");
                if (bestIndex == -1 || time > maxTime) {
                    maxTime = time;
                    bestIndex = i;
                }
            }
        }
        return bestIndex >= 0 ? attackers.getCompound(bestIndex).getString("Name") : "";
    }
    *///?}
}
