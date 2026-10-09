package me.toastymop.combatlog;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import me.toastymop.combatlog.config.CombatConfig;
import me.toastymop.combatlog.config.ConfigOption;
import me.toastymop.combatlog.config.ConfigRegistry;
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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


public class CombatCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher /*? >=1.19.2 {*/, CommandBuildContext commandRegistryAccess, Commands.CommandSelection registrationEnvironment /*?}*/) {
        LiteralArgumentBuilder<CommandSourceStack> combatlog = Commands.literal("combatlog").requires(CombatCommands::allowed);

        combatlog.then(Commands.literal("reload").requires(CombatCommands::allowed)
                .executes(context -> {
                    CombatConfig.load();
                    reply(context.getSource(), "CombatLog config reloaded!");
                    return 1;
                }));

        combatlog.then(Commands.literal("set").requires(CombatCommands::allowed)
                .then(Commands.argument("player", EntityArgument.player()).requires(CombatCommands::allowed)
                        .executes(context -> {
                            ServerPlayer target = EntityArgument.getPlayer(context, "player");
                            CombatCheck.setCombat(target);
                            reply(context.getSource(), "Put " + target.getName().getString() + " in combat");
                            return 1;
                        })));

        LiteralArgumentBuilder<CommandSourceStack> config = Commands.literal("config").requires(CombatCommands::allowed);
        config.then(Commands.literal("list").requires(CombatCommands::allowed).executes(CombatCommands::listOptions));

        LiteralArgumentBuilder<CommandSourceStack> get = Commands.literal("get").requires(CombatCommands::allowed);
        LiteralArgumentBuilder<CommandSourceStack> set = Commands.literal("set").requires(CombatCommands::allowed);
        LiteralArgumentBuilder<CommandSourceStack> reset = Commands.literal("reset").requires(CombatCommands::allowed);
        LiteralArgumentBuilder<CommandSourceStack> add = Commands.literal("add").requires(CombatCommands::allowed);
        LiteralArgumentBuilder<CommandSourceStack> remove = Commands.literal("remove").requires(CombatCommands::allowed);

        attach(get, false, (option, name) -> Commands.literal(name)
                .executes(context -> readOption(context, option)));
        attach(set, false, (option, name) -> Commands.literal(name)
                .then(value(option, context -> writeOption(context, option))));
        attach(reset, false, (option, name) -> Commands.literal(name)
                .executes(context -> resetOption(context, option)));
        attach(add, true, (option, name) -> Commands.literal(name)
                .then(value(option, context -> addOption(context, option))));
        attach(remove, true, (option, name) -> Commands.literal(name)
                .then(value(option, context -> removeOption(context, option))));

        config.then(get).then(set).then(reset).then(add).then(remove);
        combatlog.then(config);

        dispatcher.register(combatlog);
    }

    private interface OptionNode {
        LiteralArgumentBuilder<CommandSourceStack> build(ConfigOption<?> option, String name);
    }

    private interface OptionEdit {
        int apply(CommandContext<CommandSourceStack> context);
    }

    private static void attach(LiteralArgumentBuilder<CommandSourceStack> root, boolean listsOnly, OptionNode node) {
        Map<String, LiteralArgumentBuilder<CommandSourceStack>> sections = new LinkedHashMap<>();
        for (ConfigOption<?> option : ConfigRegistry.OPTIONS) {
            int split = option.path.indexOf('.');
            if (split < 0) continue;
            if (listsOnly && !option.isList()) continue;
            String section = option.path.substring(0, split);
            String name = option.path.substring(split + 1);
            sections.computeIfAbsent(section, key -> Commands.literal(key)).then(node.build(option, name));
        }
        for (LiteralArgumentBuilder<CommandSourceStack> section : sections.values()) {
            root.then(section);
        }
    }

    private static RequiredArgumentBuilder<CommandSourceStack, String> value(ConfigOption<?> option, OptionEdit edit) {
        return Commands.argument("value", StringArgumentType.greedyString())
                .suggests((context, builder) -> suggest(option, builder))
                .executes(context -> edit.apply(context));
    }

    private static CompletableFuture<Suggestions> suggest(ConfigOption<?> option, SuggestionsBuilder builder) {
        for (String suggestion : option.suggestions()) {
            builder.suggest(suggestion);
        }
        return builder.buildFuture();
    }

    private static int listOptions(CommandContext<CommandSourceStack> context) {
        for (ConfigOption<?> option : ConfigRegistry.OPTIONS) {
            reply(context.getSource(), option.path + " = " + option.display());
        }
        return 1;
    }

    private static int readOption(CommandContext<CommandSourceStack> context, ConfigOption<?> option) {
        reply(context.getSource(), option.path + " = " + option.display());
        return 1;
    }

    private static int writeOption(CommandContext<CommandSourceStack> context, ConfigOption<?> option) {
        CombatConfig.load();
        return edit(context, option, option.setFromString(value(context)));
    }

    private static int addOption(CommandContext<CommandSourceStack> context, ConfigOption<?> option) {
        CombatConfig.load();
        return edit(context, option, option.addFromString(value(context)));
    }

    private static int removeOption(CommandContext<CommandSourceStack> context, ConfigOption<?> option) {
        CombatConfig.load();
        String input = value(context).trim();
        if (!option.contains(input)) {
            context.getSource().sendFailure(Component.literal("\"" + input + "\" is not in " + option.path + ", it holds " + option.display()));
            return 0;
        }
        return edit(context, option, option.removeFromString(input));
    }

    private static int resetOption(CommandContext<CommandSourceStack> context, ConfigOption<?> option) {
        CombatConfig.load();
        option.reset();
        CombatConfig.save();
        reply(context.getSource(), option.path + " = " + option.display());
        return 1;
    }

    private static int edit(CommandContext<CommandSourceStack> context, ConfigOption<?> option, boolean applied) {
        if (!applied) {
            context.getSource().sendFailure(Component.literal("Invalid value \"" + value(context) + "\" for " + option.path));
            return 0;
        }
        CombatConfig.save();
        reply(context.getSource(), option.path + " = " + option.display());
        return 1;
    }

    private static String value(CommandContext<CommandSourceStack> context) {
        return StringArgumentType.getString(context, "value");
    }

    private static void reply(CommandSourceStack source, String message) {
        //? if >=1.19.2 {
        source.sendSuccess(/*? >=1.20.1 >>*/()-> Component.literal(message), false);
        //?} else {
        /*source.sendSuccess(new TextComponent(message), false);
        *///?}
    }

    private static boolean allowed(CommandSourceStack source) {
        //? if >=1.21.11 {
        return source.permissions().hasPermission(new Permission.HasCommandLevel(PermissionLevel.ADMINS));
        //?} else {
        /*return source.hasPermission(4);
        *///?}
    }
}
