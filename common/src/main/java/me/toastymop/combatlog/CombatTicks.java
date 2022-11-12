package me.toastymop.combatlog;

import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;


public class CombatTicks {
    public static void CombatTick(MinecraftServer server) {
        for (PlayerEntity player : server.getPlayerManager().getPlayerList()) {

            if ((TagData.getTagTime((IEntityDataSaver) player)) > 0) {
                TagData.decreaseTagTime((IEntityDataSaver) player);
                LiteralText text = new LiteralText("You are in combat do not leave!");
                player.sendMessage(text.fillStyle(Style.EMPTY.withColor(Formatting.RED)), true);
            }
            else if (TagData.getCombat((IEntityDataSaver) player)) {
                TagData.endCombat((IEntityDataSaver) player);
                LiteralText text = new LiteralText("You are no longer in combat");
                player.sendMessage(text.fillStyle(Style.EMPTY.withColor(Formatting.GREEN)), true);
            }
        }
    }

}
