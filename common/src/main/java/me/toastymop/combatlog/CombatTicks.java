package me.toastymop.combatlog;

import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class CombatTicks {
    public static void CombatTick(MinecraftServer server) {
        for (PlayerEntity player : server.getPlayerManager().getPlayerList()) {
            IEntityDataSaver data = (IEntityDataSaver) player;

            if (!TagData.getCombat(data)) continue;

            int tagTime = TagData.getTagTime(data);
            boolean combatNotice = CombatConfig.Config.combatNotice;

            if (tagTime > 0) {
                TagData.decreaseTagTime(data);
                if (combatNotice) {
                    String message = CombatConfig.Config.inCombat
                            .replace("{timeLeft}", String.valueOf(tagTime / 20));
                    player.sendMessage(Text.literal(message)
                            .fillStyle(Style.EMPTY.withColor(Formatting.RED)), true);
                }
            }
            else {
                TagData.endCombat(data);
                if (combatNotice) {
                    player.sendMessage(Text.literal(CombatConfig.Config.outCombat)
                            .fillStyle(Style.EMPTY.withColor(Formatting.GREEN)), true);
                }
            }
        }
    }
}
