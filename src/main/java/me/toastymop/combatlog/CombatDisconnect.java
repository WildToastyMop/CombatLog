package me.toastymop.combatlog;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.toastymop.combatlog.config.CombatConfig;
import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

//? if >1.21.10 {
import net.minecraft.server.permissions.PermissionSet;
//?}

public class CombatDisconnect {
    public static void OnPlayerDisconnect(ServerPlayer entity) {
        if (TagData.getCombat((IEntityDataSaver) entity)) {
            //? if >1.21.5 {
            ServerLevel world = entity.level();
            //?} else {
            /*ServerLevel world = entity.serverLevel();
            *///?}
            MinecraftServer server = world.getServer();

            String playerName = entity.getName().getString();
            String attackerName = TagData.getAttacker((IEntityDataSaver) entity);

            if (CombatConfig.Config.disconnectKill) {
                ServerPlayer attacker = attackerName.isEmpty() ? null : server.getPlayerList().getPlayerByName(attackerName);
                //? if >1.21.5 {
                entity.hurtServer(world, CombatDamage.source(world, attacker), Float.MAX_VALUE);
                //?} else {
                /*entity.hurt(CombatDamage.source(world, attacker), Float.MAX_VALUE);
                *///?}
            }

            String disconnectCommand = CombatConfig.Config.disconnectCommand;
            if (disconnectCommand != null && !disconnectCommand.trim().isEmpty()){
                Commands manager = server.getCommands();
                CommandDispatcher<CommandSourceStack> dispatcher = manager.getDispatcher();
                //? if >1.21.10 {
                CommandSourceStack commandSource = server.createCommandSourceStack().withPermission(PermissionSet.ALL_PERMISSIONS);
                //?} else {
                /*CommandSourceStack commandSource = server.createCommandSourceStack().withPermission(4);
                *///?}
                disconnectCommand = disconnectCommand
                        .replace("{player}", playerName)
                        .replace("{attacker}", attackerName);
                CommandSourceStack silent = commandSource.withSuppressedOutput();
                try {
                    dispatcher.execute(dispatcher.parse(disconnectCommand, silent));
                } catch (CommandSyntaxException e) {
                    commandSource.sendFailure(Component.nullToEmpty(e.getMessage()));
                }
            }

            TagData.endCombat((IEntityDataSaver) entity);
        }
    }

    public static Component deathMessage(LivingEntity victim) {
        String attacker = victim instanceof ServerPlayer player ? TagData.getAttacker((IEntityDataSaver) player) : "";
        String message = CombatConfig.Config.deathMessage
                .replace("{player}", victim.getName().getString())
                .replace("{attacker}", attacker);
        return Component.nullToEmpty(message);
    }
}
