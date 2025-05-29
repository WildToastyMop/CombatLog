package me.toastymop.combatlog;

import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;

public class CombatCheck {
    public static void CheckCombat(Entity entity) {
        LivingEntity target = (LivingEntity) entity;
        if (target instanceof PlayerEntity) {
            LivingEntity attacker = target.getAttacker();
            if ((attacker instanceof PlayerEntity) && ((ServerPlayerEntity) target).interactionManager.getGameMode().isSurvivalLike() && ((ServerPlayerEntity) attacker).interactionManager.getGameMode().isSurvivalLike()) {
                setCombat((PlayerEntity) target,(PlayerEntity) attacker);
            }else if (CombatConfig.Config.allDamage && ((ServerPlayerEntity) target).interactionManager.getGameMode().isSurvivalLike()) {
                setCombat((PlayerEntity) target);
            } else if (CombatConfig.Config.mobDamage && (attacker instanceof LivingEntity) && ((ServerPlayerEntity) target).interactionManager.getGameMode().isSurvivalLike()) {
                setCombat((PlayerEntity) target);
            }
        }

    }
    public static void setCombat(PlayerEntity target, PlayerEntity attacker ){
        TagData.setTagTime((IEntityDataSaver) target);
        TagData.setTagTime((IEntityDataSaver) attacker);
        if (CombatConfig.Config.disablePearl){
            target.getItemCooldownManager().set(Items.ENDER_PEARL.getDefaultStack(),CombatConfig.Config.combatTime * 20);
            attacker.getItemCooldownManager().set(Items.ENDER_PEARL.getDefaultStack(),CombatConfig.Config.combatTime * 20);
        }
    }
    public static void setCombat(PlayerEntity target){
        TagData.setTagTime((IEntityDataSaver) target);
        if (CombatConfig.Config.disablePearl){
            target.getItemCooldownManager().set(Items.ENDER_PEARL.getDefaultStack(),CombatConfig.Config.combatTime * 20);
        }
    }
}
