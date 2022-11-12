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

            if ((TagData.getTagTime((IEntityDataSaver) player)) > 0) {
                TagData.decreaseTagTime((IEntityDataSaver) player);
                player.sendMessage(Text.literal("You are in combat do not leave!").fillStyle(Style.EMPTY.withColor(Formatting.RED)), true);
            }
            else if (TagData.getCombat((IEntityDataSaver) player)) {
                TagData.endCombat((IEntityDataSaver) player);
                player.sendMessage(Text.literal("You are no longer in combat").fillStyle(Style.EMPTY.withColor(Formatting.GREEN)), true);
            }
        }
    }

}
