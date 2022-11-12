package me.toastymop.combatlog;

import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;

public class CombatCheck {
    public static void CheckCombat(Entity entity) {
        LivingEntity target = (LivingEntity) entity;
        if (target instanceof PlayerEntity) {
            if ((target.getAttacker() instanceof PlayerEntity) && ((ServerPlayerEntity) target).interactionManager.getGameMode().isSurvivalLike() && ((ServerPlayerEntity) Objects.requireNonNull(target.getAttacker())).interactionManager.getGameMode().isSurvivalLike()) {
                TagData.setTagTime((IEntityDataSaver) target);
                TagData.setTagTime((IEntityDataSaver) target.getAttacker());
            } else if (CombatConfig.allDamage) {
                TagData.setTagTime((IEntityDataSaver) target);
            }
        }

    }
}
