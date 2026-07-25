package me.toastymop.combatlog;

import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.MinecraftServer;
import net.minecraft.network.chat.Style;
import net.minecraft.ChatFormatting;
//? if >=1.19 {
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
//?} else {
/*import net.minecraft.network.chat.TextComponent;
*///?}


public class CombatTicks {
    public static void CombatTick(MinecraftServer server) {
        for (Player player : server.getPlayerList().getPlayers()) {
            IEntityDataSaver data = (IEntityDataSaver) player;

            if (!TagData.getCombat(data)) continue;

            int tagTime = TagData.getTagTime(data);
            boolean combatNotice = CombatConfig.Config.combatNotice;

			if (tagTime % 20 == 0) {
				CompoundTag persistentData = (data).getPersistentData();
				//? if >1.21.5 {
				ListTag attackers = persistentData.getListOrEmpty("attackerHistory");
				//?} else {
				/*ListTag attackers = persistentData.getList("attackerHistory", 10);
				*///?}
				boolean anyStillFighting = false;
				for (int i = 0; i < attackers.size(); i++) {
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
					if (attacker != null && TagData.getCombat((IEntityDataSaver) attacker)) {
						anyStillFighting = true;
						break;
					}
				}
				if (!anyStillFighting) {
					TagData.endCombat(data);
					if (combatNotice) {
						//? if >=1.19 {
						MutableComponent outCombat = Component.literal(CombatConfig.Config.outCombat);
						//?} else {
						/*TextComponent outCombat = new TextComponent(CombatConfig.Config.outCombat);
						*///?}
						//? if <=1.21.11 {
						/*player.displayClientMessage(outCombat
								.withStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)), true);
						*///?} else {
						player.sendOverlayMessage(outCombat
								.withStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)));
						//?}
					}
					continue;
				}
			}

            if (tagTime > 0) {
                TagData.decreaseTagTime(data);
                if (combatNotice && CombatCheck.tickRate > 0 && (tagTime % CombatCheck.tickRate) == 0) {
                    String message = CombatConfig.Config.inCombat
                            .replace("{timeLeft}", String.valueOf(tagTime / CombatCheck.tickRate));
                    //? if >=1.19 {
                    MutableComponent inCombat = Component.literal(message);
                    //?} else {
                    /*TextComponent inCombat = new TextComponent(message);
                    *///?}

					//? if <=1.21.11 {
                    /*player.displayClientMessage(inCombat
                            .withStyle(Style.EMPTY.withColor(ChatFormatting.RED)), true);
					*///?} else {
					player.sendOverlayMessage(inCombat
							.withStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
					//?}
                }
            } else {
                TagData.endCombat(data);
                if (combatNotice) {
                    //? if >=1.19 {
                    MutableComponent outCombat = Component.literal(CombatConfig.Config.outCombat);
                     //?} else {
                    /*TextComponent outCombat = new TextComponent(CombatConfig.Config.outCombat);
                    *///?}

					//? if <=1.21.11 {
                    /*player.displayClientMessage(outCombat
                            .withStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)), true);
					*///?} else {
					player.sendOverlayMessage(outCombat
							.withStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)));
					//?}
                }
            }
        }
    }
}
