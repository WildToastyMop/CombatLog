package me.toastymop.combatlog;

import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.server.level.ServerPlayer;

public class CombatCheck {
    //? if >1.21.5 {
    /*static ItemStack enderPearl = Items.ENDER_PEARL.getDefaultInstance();
    *///?} else {
    static Item enderPearl = Items.ENDER_PEARL.getDefaultInstance().getItem();
    //?}
    public static void CheckCombat(Entity entity) {
        LivingEntity target = (LivingEntity) entity;
        if (target instanceof Player) {
            LivingEntity attacker = target.getLastHurtByMob();
            if ((attacker instanceof Player) && ((ServerPlayer) target).gameMode.getGameModeForPlayer().isSurvival() && ((ServerPlayer) attacker).gameMode.getGameModeForPlayer().isSurvival()) {
                setCombat((Player) target,(Player) attacker);
            }else if (CombatConfig.Config.allDamage && ((ServerPlayer) target).gameMode.getGameModeForPlayer().isSurvival()) {
                setCombat((Player) target);
            } else if (CombatConfig.Config.mobDamage && (attacker instanceof LivingEntity) && ((ServerPlayer) target).gameMode.getGameModeForPlayer().isSurvival()) {
                setCombat((Player) target);
            }
        }

    }
    public static void setCombat(Player target, Player attacker ){
        TagData.setTagTime((IEntityDataSaver) target);
        TagData.setTagTime((IEntityDataSaver) attacker);
        if (CombatConfig.Config.disablePearl){
            target.getCooldowns().addCooldown(enderPearl,CombatConfig.Config.combatTime * 20);
            attacker.getCooldowns().addCooldown(enderPearl,CombatConfig.Config.combatTime * 20);
        }
    }
    public static void setCombat(Player target){
        TagData.setTagTime((IEntityDataSaver) target);
        if (CombatConfig.Config.disablePearl){
            target.getCooldowns().addCooldown(enderPearl,CombatConfig.Config.combatTime * 20);
        }
    }
}
