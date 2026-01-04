package me.toastymop.combatlog;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.network.chat.ChatType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.commands.Commands;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;

//? if >1.21.10 {
/*import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.server.permissions.PermissionSet;
*///?} else {
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import java.util.UUID;
import static net.minecraft.world.level.GameRules.RULE_SHOWDEATHMESSAGES;
//?}

public class CombatDisconnect {
    public static void OnPlayerDisconnect(ServerPlayer entity) {
        if (TagData.getCombat((IEntityDataSaver) entity)) {
            //? if >1.21.5 {
            /*ServerLevel world = entity.level();
            *///?} else {
            //? if >=1.20 {
            /*ServerLevel world = entity.serverLevel();
             *///?} else {
            ServerLevel world = entity.getLevel();
            //?}
            //?}
            MinecraftServer server = world.getServer();

            if (CombatConfig.Config.disconnectKill) {
                // if anyone knows how to make this less terrible please help me im begging I tried using the new damagetypes for soooo long
                Component deathMessage = Component.nullToEmpty(entity.getDisplayName().getString() + CombatConfig.Config.deathMessage);
                //? if >1.21.10 {
                /*GameRules gamerule = world.getGameRules();
                if (gamerule.get(GameRules.SHOW_DEATH_MESSAGES)) {
                    gamerule.set(GameRules.SHOW_DEATH_MESSAGES,false, server);
                    entity.hurtServer(world, entity.damageSources().fellOutOfWorld(), 100000);
                    gamerule.set(GameRules.SHOW_DEATH_MESSAGES,true, server);
                    server.getPlayerList().broadcastSystemMessage(deathMessage, false);
                *///?} else {
                GameRules.BooleanValue gamerule = server.getGameRules().getRule(RULE_SHOWDEATHMESSAGES);
                if (gamerule.get()) {
                    gamerule.set(false, server);
                    //? if >1.21.5 {
                    /*entity.hurtServer(world, entity.damageSources().fellOutOfWorld(), 100000);
                     *///?} else {
                    //? if >=1.20 {
                    /*entity.hurt(entity.damageSources().fellOutOfWorld(), 100000);
                     *///?} else {
                    entity.hurt(DamageSource.OUT_OF_WORLD, 100000);
                    //?}
                    //?}
                    gamerule.set(true, server);
                    //? if >=1.19 {
                    /*server.getPlayerList().broadcastSystemMessage(deathMessage, false);
                    *///?} else {
                    server.getPlayerList().broadcastMessage(deathMessage, ChatType.CHAT, UUID.randomUUID());
                    //?}
                //?}
                } else {
                    //? if >1.21.5 {
                    /*entity.hurtServer(world, entity.damageSources().fellOutOfWorld(), 100000);
                    *///?} else {
                        //? if >=1.20 {
                        /*entity.hurt(entity.damageSources().fellOutOfWorld(), 100000);
                        *///?} else {
                        entity.hurt(DamageSource.OUT_OF_WORLD, 100000);
                        //?}
                    //?}
                }
            }

            String disconnectCommand = CombatConfig.Config.disconnectCommand.replace("{player}",entity.getName().getString());
            if (disconnectCommand != null && !disconnectCommand.trim().isEmpty()){
                Commands manager = server.getCommands();
                CommandDispatcher<CommandSourceStack> dispatcher = manager.getDispatcher();
                //? if >1.21.10 {
                /*CommandSourceStack commandSource = server.createCommandSourceStack().withPermission(PermissionSet.ALL_PERMISSIONS);
                *///?} else {
                CommandSourceStack commandSource = server.createCommandSourceStack().withPermission(4);
                //?}
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