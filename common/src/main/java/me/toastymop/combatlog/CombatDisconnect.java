package me.toastymop.combatlog;

import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.network.MessageType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameRules;

import java.util.Objects;
import java.util.UUID;

import static net.minecraft.world.GameRules.SHOW_DEATH_MESSAGES;


public class CombatDisconnect {

    public static void OnPlayerDisconnect(ServerPlayerEntity entity) {
        if (TagData.getCombat((IEntityDataSaver) entity)) {
            // if anyone knows how to make this less terrible please help me im begging I tried using the new damagetypes for soooo long
            GameRules.BooleanRule gamerule = Objects.requireNonNull(entity.getServer()).getGameRules().get(SHOW_DEATH_MESSAGES);
            Text deathMessage = Text.of(entity.getDisplayName().getString()+CombatConfig.CONFIG.deathMessage);
            if (gamerule.get()){
                gamerule.set(false, entity.getServer());
                entity.damage(EntityDamageSource.OUT_OF_WORLD, 100000);
                gamerule.set(true, entity.getServer());
                entity.getServer().getPlayerManager().broadcastChatMessage(deathMessage, MessageType.CHAT, UUID.randomUUID());
            } else {
                entity.damage(EntityDamageSource.OUT_OF_WORLD, 100000);
            }
            TagData.endCombat((IEntityDataSaver) entity);
        }
    }
}
