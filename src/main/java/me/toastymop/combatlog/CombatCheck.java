package me.toastymop.combatlog;

import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public class CombatCheck {
    public static Integer tickRate = 0;
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
    public static void setCombat(Player target, Player attacker ) {
        //? if >=1.21.1 {
        tickRate = (int) target.level().getServer().tickRateManager().tickrate();
        //?} else {
        /*tickRate = 20;
        *///?}
        TagData.setTagTime((IEntityDataSaver) target);
        TagData.setTagTime((IEntityDataSaver) attacker);
        if (!CombatConfig.Config.disabledItems.isEmpty()){
            setCooldowns(CombatConfig.Config.disabledItems, target, attacker);
        }
    }
    public static void setCombat(Player target) {
        //? if >=1.21.1 {
        tickRate = (int) target.level().getServer().tickRateManager().tickrate();
         //?} else {
        /*tickRate = 20;
        *///?}
        TagData.setTagTime((IEntityDataSaver) target);
        if (!CombatConfig.Config.disabledItems.isEmpty()){
            setCooldowns(CombatConfig.Config.disabledItems, target);
        }
    }

    public static void setCooldowns(List<ItemStack> list, Player target, Player attacker){
        for (ItemStack stack : list) {
            //? if >=1.21.6 {
            target.getCooldowns().addCooldown(stack, CombatConfig.Config.combatTime * 20);
            attacker.getCooldowns().addCooldown(stack, CombatConfig.Config.combatTime * 20);
            //?} else {
            /*target.getCooldowns().addCooldown(stack.getItem(), CombatConfig.Config.combatTime * 20);
            attacker.getCooldowns().addCooldown(stack.getItem(), CombatConfig.Config.combatTime * 20);
            *///?}

        }
    }

    public static void setCooldowns(List<ItemStack> list, Player target){
        for (ItemStack stack : list) {
            //? if >=1.21.6 {
            target.getCooldowns().addCooldown(stack, CombatConfig.Config.combatTime * 20);
            //?} else {
            /*target.getCooldowns().addCooldown(stack.getItem(), CombatConfig.Config.combatTime * 20);
            *///?}
        }
    }
}
