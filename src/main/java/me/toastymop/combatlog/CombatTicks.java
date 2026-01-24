package me.toastymop.combatlog;

import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
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

            if (tagTime > 0) {
                TagData.decreaseTagTime(data);
                if (combatNotice && (tagTime % CombatCheck.tickRate) == 0) {
                    String message = CombatConfig.Config.inCombat
                            .replace("{timeLeft}", String.valueOf(tagTime / CombatCheck.tickRate));
                    //? if >=1.19 {
                    MutableComponent inCombat = Component.literal(message);
                    //?} else {
                    /*TextComponent inCombat = new TextComponent(message);
                    *///?}
                    player.displayClientMessage(inCombat
                            .withStyle(Style.EMPTY.withColor(ChatFormatting.RED)), true);
                }
            } else {
                TagData.endCombat(data);
                if (combatNotice) {
                    //? if >=1.19 {
                    MutableComponent outCombat = Component.literal(CombatConfig.Config.outCombat);
                     //?} else {
                    /*TextComponent outCombat = new TextComponent(CombatConfig.Config.outCombat);
                    *///?}

                    player.displayClientMessage(outCombat
                            .withStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)), true);
                }
            }
        }
    }
}