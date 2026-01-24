package me.toastymop.combatlog;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

//? if >=1.21.11 {
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;
//?}

//? if >=1.19.2 {
import net.minecraft.commands.CommandBuildContext;
//?}

//? if >=1.16.5 && <1.19.2 {
/*import net.minecraft.network.chat.TextComponent;
*///?}


public class CombatCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher /*? >=1.19.2 {*/, CommandBuildContext commandRegistryAccess, Commands.CommandSelection registrationEnvironment /*?}*/) {
        //? if >=1.21.11 {
        dispatcher.register(Commands.literal("combatlog").requires(source -> source.permissions().hasPermission(new Permission.HasCommandLevel(PermissionLevel.ADMINS)))
                .then(Commands.literal("reload").requires(source -> source.permissions().hasPermission(new Permission.HasCommandLevel(PermissionLevel.ADMINS)))
        //?} else {
        /*dispatcher.register(Commands.literal("combatlog").requires(source -> source.hasPermission(4))
                .then(Commands.literal("reload").requires(source -> source.hasPermission(4))
        *///?}
                        .executes(context -> {
                            CombatConfig.CONFIG = CombatConfig.load();
                            //? if >=1.19.2 {
                            context.getSource().sendSuccess(/*? >=1.20.1 >>*/()-> Component.literal("CombatLog config reloaded!"),false);
                            //?} else {
                            /*context.getSource().sendSuccess(new TextComponent("CombatLog config reloaded!"),false);
                            *///?}
                            return 1;
                        })
                )
                //? if >=1.21.11 {
                .then(Commands.literal("set").requires(source -> source.permissions().hasPermission(new Permission.HasCommandLevel(PermissionLevel.ADMINS)))
                    .then(Commands.argument("player", EntityArgument.player()).requires(source -> source.permissions().hasPermission(new Permission.HasCommandLevel(PermissionLevel.ADMINS)))
                //?} else {
                /*.then(Commands.literal("set").requires(source -> source.hasPermission(4))
                                .then(Commands.argument("player", EntityArgument.player()).requires(source -> source.hasPermission(4))
                *///?}
                        .executes(context -> {
                            ServerPlayer target = EntityArgument.getPlayer(context,"player");
                            CombatCheck.setCombat(target);
                            //? if >=1.19.2 {
                            context.getSource().sendSuccess(/*? >=1.20.1 >>*/()-> Component.literal("Put " + target.getName().getString()+ " in combat"), false);
                            //?} else {
                            /*context.getSource().sendSuccess(new TextComponent("Put " + target.getName().getString()+ " in combat"), false);
                            *///?}
                            return 1;
                        })
                    )
                )
        );
    }
}
