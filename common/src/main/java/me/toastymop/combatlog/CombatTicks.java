package me.toastymop.combatlog;

import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.MinecraftServer;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

public class CombatTicks {
    public static void CombatTick(MinecraftServer server) {
        for (Player player : server.getPlayerList().getPlayers()) {
            IEntityDataSaver data = (IEntityDataSaver) player;

            if (!TagData.getCombat(data)) continue;

            int tagTime = TagData.getTagTime(data);
            boolean combatNotice = CombatConfig.Config.combatNotice;

            if (tagTime > 0) {
                TagData.decreaseTagTime(data);
                if (combatNotice) {
                    String message = CombatConfig.Config.inCombat
                            .replace("{timeLeft}", String.valueOf(tagTime / 20));
                    player.displayClientMessage(Component.literal(message)
                            .withStyle(Style.EMPTY.withColor(ChatFormatting.RED)), true);
                }
            }
            else {
                TagData.endCombat(data);
                if (combatNotice) {
                    player.displayClientMessage(Component.literal(CombatConfig.Config.outCombat)
                            .withStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)), true);
                }
            }
        }
    }
}