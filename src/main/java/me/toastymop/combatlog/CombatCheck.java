package me.toastymop.combatlog;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerPlayer;
//? if >1.21.10
import net.minecraft.server.permissions.PermissionSet;


import java.util.List;

public class CombatCheck {
    public static Integer tickRate = 0;
    public static void CheckCombat(Entity entity, float dmg) {
        LivingEntity target = (LivingEntity) entity;
        if (target instanceof Player) {
            LivingEntity attacker = target.getLastHurtByMob();

			if (!CombatConfig.Config.selfDamage){ if (attacker == target) { return; }}
			if (!CombatConfig.Config.fireDamage){ if (target.getLastDamageSource() != null && target.getLastDamageSource().is(DamageTypeTags.IS_FIRE)) { return; }}

            if ((attacker instanceof Player) && ((ServerPlayer) target).gameMode.getGameModeForPlayer().isSurvival() && ((ServerPlayer) attacker).gameMode.getGameModeForPlayer().isSurvival()) {
                setCombat((Player) target, (Player) attacker, dmg);
            }else if (CombatConfig.Config.allDamage && ((ServerPlayer) target).gameMode.getGameModeForPlayer().isSurvival()) {
                setCombat((Player) target);
            } else if (CombatConfig.Config.mobDamage && (attacker instanceof LivingEntity) && ((ServerPlayer) target).gameMode.getGameModeForPlayer().isSurvival()) {
                setCombat((Player) target);
            }
        }

    }
    public static void setCombat(Player target, Player attacker, float dmg) {
		MinecraftServer server = target.level().getServer();
        //? if >=1.21.1 {
		tickRate = (int) server.tickRateManager().tickrate();
        //?} else {
        /*tickRate = 20;
        *///?}
        TagData.setTagTime((IEntityDataSaver) target);
        TagData.setTagTime((IEntityDataSaver) attacker);
        TagData.updateAttacker(target, attacker, dmg);
        TagData.updateAttacker(attacker, target, 0f);
        if (!CombatConfig.Config.disabledItems.isEmpty()){
            setCooldowns(CombatConfig.Config.disabledItems, target, attacker);
        }

		String combatCommand = CombatConfig.Config.combatCommand;
		if (combatCommand != null && !combatCommand.trim().isEmpty()){
			Commands manager = server.getCommands();
			CommandDispatcher<CommandSourceStack> dispatcher = manager.getDispatcher();
			//? if >1.21.10 {
			CommandSourceStack commandSource = server.createCommandSourceStack().withPermission(PermissionSet.ALL_PERMISSIONS);
			//?} else {
			/*CommandSourceStack commandSource = server.createCommandSourceStack().withPermission(4);
			 *///?}

			CommandSourceStack silent = commandSource.withSuppressedOutput();

			// Run for target
			String targetCommand = combatCommand
					.replace("{player}", target.getName().getString())
					.replace("{tagTime}", CombatConfig.Config.combatTime.toString());
			try {
				dispatcher.execute(dispatcher.parse(targetCommand, silent));
			} catch (CommandSyntaxException e) {
				commandSource.sendFailure(Component.nullToEmpty(e.getMessage()));
			}

			// Run for attacker
			String attackerCommand = combatCommand
					.replace("{player}", attacker.getName().getString())
					.replace("{tagTime}", CombatConfig.Config.combatTime.toString());
			try {
				dispatcher.execute(dispatcher.parse(attackerCommand, silent));
			} catch (CommandSyntaxException e) {
				commandSource.sendFailure(Component.nullToEmpty(e.getMessage()));
			}
		}
    }

    public static void setCombat(Player target) {
		MinecraftServer server = target.level().getServer();
        //? if >=1.21.1 {
		tickRate = (int) server.tickRateManager().tickrate();
        //?} else {
        /*tickRate = 20;
        *///?}
        TagData.setTagTime((IEntityDataSaver) target);
        if (!CombatConfig.Config.disabledItems.isEmpty()){
            setCooldowns(CombatConfig.Config.disabledItems, target);
        }

		String combatCommand = CombatConfig.Config.combatCommand;
		if (combatCommand != null && !combatCommand.trim().isEmpty()){
			Commands manager = server.getCommands();
			CommandDispatcher<CommandSourceStack> dispatcher = manager.getDispatcher();
			//? if >1.21.10 {
			CommandSourceStack commandSource = server.createCommandSourceStack().withPermission(PermissionSet.ALL_PERMISSIONS);
			//?} else {
			/*CommandSourceStack commandSource = server.createCommandSourceStack().withPermission(4);
			 *///?}
			CommandSourceStack silent = commandSource.withSuppressedOutput();
			combatCommand = combatCommand
					.replace("{player}", target.getName().getString())
					.replace("{tagTime}", CombatConfig.Config.combatTime.toString());
			try {
				dispatcher.execute(dispatcher.parse(combatCommand, silent));
			} catch (CommandSyntaxException e) {
				commandSource.sendFailure(Component.nullToEmpty(e.getMessage()));
			}
		}
    }

    public static void setCooldowns(List<Item> list, Player target, Player attacker){
        for (Item item : list) {
            ItemStack stack = item != null ? new ItemStack(item) : ItemStack.EMPTY;
            //? if >=1.21.6 {
            target.getCooldowns().addCooldown(stack, CombatConfig.Config.combatTime * 20);
            attacker.getCooldowns().addCooldown(stack, CombatConfig.Config.combatTime * 20);
            //?} else {
            /*target.getCooldowns().addCooldown(stack.getItem(), CombatConfig.Config.combatTime * 20);
            attacker.getCooldowns().addCooldown(stack.getItem(), CombatConfig.Config.combatTime * 20);
            *///?}

        }
    }

    public static void setCooldowns(List<Item> list, Player target){
        for (Item item : list) {
            ItemStack stack = item != null ? new ItemStack(item) : ItemStack.EMPTY;

            //? if >=1.21.6 {
            target.getCooldowns().addCooldown(stack, CombatConfig.Config.combatTime * 20);
            //?} else {
            /*target.getCooldowns().addCooldown(stack.getItem(), CombatConfig.Config.combatTime * 20);
            *///?}
        }
    }
}
