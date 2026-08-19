package me.toastymop.combatlog;

import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.MinecraftServer;

import java.util.List;

import static me.toastymop.combatlog.CombatNotice.displayNotice;

public class CombatTicks {
    public static void CombatTick(MinecraftServer server) {
        List<ServerPlayer> players = server.getPlayerList().getPlayers();
        for (int p = 0, pCount = players.size(); p < pCount; p++) {
            ServerPlayer player = players.get(p);
            IEntityDataSaver data = (IEntityDataSaver) player;

            if (!TagData.getCombat(data)) continue;

            int tagTime = TagData.getTagTime(data);

			if (tagTime % 20 == 0) {
				CompoundTag persistentData = (data).getPersistentData();
				//? if >1.21.5 {
				ListTag attackers = persistentData.getListOrEmpty("attackerHistory");
				//?} else {
				/*ListTag attackers = persistentData.getList("attackerHistory", 10);
				*///?}
				boolean anyStillFighting = false;
				for (int i = 0, aCount = attackers.size(); i < aCount; i++) {
					//? if >1.21.5 {
					CompoundTag entry = attackers.getCompoundOrEmpty(i);
					//?} else {
					/*CompoundTag entry = attackers.getCompound(i);
					*///?}
					//? if >1.21.5 {
					String name = entry.getString("Name").orElse("");
					//?} else {
					/*String name = entry.getString("Name");
					*///?}
					ServerPlayer attacker = server.getPlayerList().getPlayerByName(name);
					if (attacker != null && !attacker.isDeadOrDying() && TagData.getCombat((IEntityDataSaver) attacker)) {
						anyStillFighting = true;
						break;
					}
				}
				if (!anyStillFighting && !attackers.isEmpty()) {
					TagData.endCombat(data);
					displayNotice(player,0,true);
					continue;
				}
			}

            if (tagTime > 0) {
                TagData.decreaseTagTime(data);
				displayNotice(player,tagTime,false);
            } else {
                TagData.endCombat(data);
				displayNotice(player,0,true);
            }
        }
    }
}
