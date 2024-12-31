package me.toastymop.combatlog;

import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.network.message.MessageType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameRules;

import java.util.Objects;

import static net.minecraft.world.GameRules.SHOW_DEATH_MESSAGES;


public class CombatDisconnect {

    public static void OnPlayerDisconnect(ServerPlayerEntity entity) {
        if (TagData.getCombat((IEntityDataSaver) entity)) {
            // if anyone knows how to make this less terrible please help me im begging I tried using the new damagetypes for soooo long
            GameRules.BooleanRule gamerule = Objects.requireNonNull(entity.getServer()).getGameRules().get(SHOW_DEATH_MESSAGES);
            Text deathMessage = Text.of(entity.getDisplayName().getString()+CombatConfig.Config.deathMessage);
            if (gamerule.get()){
                gamerule.set(false, entity.getServer());
                entity.damage(entity.getServerWorld(),entity.getDamageSources().outOfWorld(), 100000);
                gamerule.set(true, entity.getServer());
                entity.getServer().getPlayerManager().broadcast(deathMessage,false);
            } else {
                entity.damage(entity.getServerWorld(),entity.getDamageSources().outOfWorld(), 100000);
            }
            TagData.endCombat((IEntityDataSaver) entity);
        }
    }
}
