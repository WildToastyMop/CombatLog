package me.toastymop.combatlog;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.GameRules;

import static net.minecraft.world.GameRules.SHOW_DEATH_MESSAGES;


public class CombatDisconnect {
    public static void OnPlayerDisconnect(ServerPlayerEntity entity) {
        if (TagData.getCombat((IEntityDataSaver) entity)) {
            ServerWorld world = entity.getEntityWorld();
            MinecraftServer server = world.getServer();

            if (CombatConfig.Config.disconnectKill) {
                // if anyone knows how to make this less terrible please help me im begging I tried using the new damagetypes for soooo long
                GameRules.BooleanRule gamerule = server.getGameRules().get(SHOW_DEATH_MESSAGES);
                Text deathMessage = Text.of(entity.getDisplayName().getString() + CombatConfig.Config.deathMessage);
                if (gamerule.get()) {
                    gamerule.set(false, server);
                    entity.damage(world, entity.getDamageSources().outOfWorld(), 100000);
                    gamerule.set(true, server);
                    server.getPlayerManager().broadcast(deathMessage, false);
                } else {
                    entity.damage(world, entity.getDamageSources().outOfWorld(), 100000);
                }
            }

            String disconnectCommand = CombatConfig.Config.disconnectCommand.replace("{player}",entity.getName().getString());
            if (disconnectCommand != null && !disconnectCommand.trim().isEmpty()){
                CommandManager manager = server.getCommandManager();
                CommandDispatcher<ServerCommandSource> dispatcher = manager.getDispatcher();
                ServerCommandSource commandSource = server.getCommandSource().withLevel(4);
                ParseResults<ServerCommandSource> parseResults = dispatcher.parse(disconnectCommand,commandSource);
                try {
                    dispatcher.execute(parseResults);
                } catch (CommandSyntaxException e) {
                    commandSource.sendError(Text.of(e.getMessage()));
                }
            }

            TagData.endCombat((IEntityDataSaver) entity);
        }
    }
}