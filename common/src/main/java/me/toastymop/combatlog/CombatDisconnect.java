package me.toastymop.combatlog;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.server.permissions.PermissionSet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.commands.Commands;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;

import net.minecraft.world.level.gamerules.GameRules;



public class CombatDisconnect {
    public static void OnPlayerDisconnect(ServerPlayer entity) {
        if (TagData.getCombat((IEntityDataSaver) entity)) {
            ServerLevel world = entity.level();
            MinecraftServer server = world.getServer();

            if (CombatConfig.Config.disconnectKill) {
                // if anyone knows how to make this less terrible please help me im begging I tried using the new damagetypes for soooo long
                GameRules gamerules = world.getGameRules();
                Component deathMessage = Component.nullToEmpty(entity.getDisplayName().getString() + CombatConfig.Config.deathMessage);
                if (gamerules.get(GameRules.SHOW_DEATH_MESSAGES)) {
                    gamerules.set(GameRules.SHOW_DEATH_MESSAGES,false, server);
                    entity.hurtServer(world, entity.damageSources().fellOutOfWorld(), 100000);
                    gamerules.set(GameRules.SHOW_DEATH_MESSAGES,true, server);
                    server.getPlayerList().broadcastSystemMessage(deathMessage, false);
                } else {
                    entity.hurtServer(world, entity.damageSources().fellOutOfWorld(), 100000);
                }
            }

            String disconnectCommand = CombatConfig.Config.disconnectCommand.replace("{player}",entity.getName().getString());
            if (disconnectCommand != null && !disconnectCommand.trim().isEmpty()){
                Commands manager = server.getCommands();
                CommandDispatcher<CommandSourceStack> dispatcher = manager.getDispatcher();
                CommandSourceStack commandSource = server.createCommandSourceStack().withPermission(PermissionSet.ALL_PERMISSIONS);
                ParseResults<CommandSourceStack> parseResults = dispatcher.parse(disconnectCommand,commandSource);
                try {
                    dispatcher.execute(parseResults);
                } catch (CommandSyntaxException e) {
                    commandSource.sendFailure(Component.nullToEmpty(e.getMessage()));
                }
            }

            TagData.endCombat((IEntityDataSaver) entity);
        }
    }
}