package me.toastymop.combatlog;

import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.server.network.ServerPlayerEntity;


public class CombatDisconnect {

    public static void OnPlayerDisconnect(ServerPlayerEntity entity) {
        CombatDeathMessage damageSource = new CombatDeathMessage(CombatDeathMessage.COMBATLOG, entity);
        damageSource.setBypassesArmor();
        if (TagData.getCombat((IEntityDataSaver) entity)) {
            entity.damage(damageSource,1000);
            TagData.endCombat((IEntityDataSaver) entity);
        }
    }
}
