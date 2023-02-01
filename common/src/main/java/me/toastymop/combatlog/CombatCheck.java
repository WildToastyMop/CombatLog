package me.toastymop.combatlog;

import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;

public class CombatCheck {
    public static void CheckCombat(Entity entity) {
        // Contributor note: I have refactored this method a bit
        // to make it a bit easier to read
        // and remove some unnecessary code like multiple casts, instead of one
        if (entity instanceof ServerPlayerEntity player) {
            if ((player.getAttacker() instanceof ServerPlayerEntity attacker) && player.interactionManager.getGameMode().isSurvivalLike() && attacker.interactionManager.getGameMode().isSurvivalLike()) {
                TagData.setTagTime((IEntityDataSaver) player);
                if (CombatConfig.disableElytra) {
                    // Stop the player who took damage from flying
                    // The attacker will be able to fly until taking damage from another player or until it lands
                    player.stopFallFlying();
                    if (CombatConfig.attackerNoFly) {
                        attacker.stopFallFlying();
                    }
                }
                TagData.setTagTime((IEntityDataSaver) attacker);
            } else if (CombatConfig.allDamage) {
                TagData.setTagTime((IEntityDataSaver) player);
            }
        }

    }
}
