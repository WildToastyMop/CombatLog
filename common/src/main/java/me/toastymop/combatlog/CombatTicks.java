package me.toastymop.combatlog;

import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Map;


public class CombatTicks {
    public static void CombatTick(MinecraftServer server) {
        for (PlayerEntity player : server.getPlayerManager().getPlayerList()) {

            String inCombat = CombatConfig.Config.inCombat;
            Map<String, String> variables = Map.of(
                    "timeLeft", (String.valueOf(TagData.getTagTime((IEntityDataSaver) player) / 20))
            );
            for (Map.Entry<String, String> entry : variables.entrySet()) {
                String placeholder = "{" + entry.getKey() + "}";
                inCombat = inCombat.replace(placeholder, entry.getValue());
            }
            if (CombatConfig.Config.combatNotice) {
                if ((TagData.getTagTime((IEntityDataSaver) player)) > 0) {
                    TagData.decreaseTagTime((IEntityDataSaver) player);
                    player.sendMessage(Text.literal(inCombat).fillStyle(Style.EMPTY.withColor(Formatting.RED)), true);
                } else if (TagData.getCombat((IEntityDataSaver) player)) {
                    TagData.endCombat((IEntityDataSaver) player);
                    player.sendMessage(Text.literal(CombatConfig.Config.outCombat).fillStyle(Style.EMPTY.withColor(Formatting.GREEN)), true);
                }
            }else{
                if ((TagData.getTagTime((IEntityDataSaver) player)) > 0) {
                    TagData.decreaseTagTime((IEntityDataSaver) player);
                } else if (TagData.getCombat((IEntityDataSaver) player)) {
                    TagData.endCombat((IEntityDataSaver) player);
                }
            }
        }
    }

}
