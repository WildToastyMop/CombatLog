package me.toastymop.combatlog.mixin;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.toastymop.combatlog.CombatConfig;
import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.commands.Commands;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//? if =1.16.5 {
/*import net.minecraft.Util;
*///?}

@Mixin(Commands.class)
public class CommandsMixin {
    @Inject(method = "performCommand", at = @At("HEAD"), cancellable = true)
        //? if >1.20.1 {
        private void onExecuteCommand(ParseResults<CommandSourceStack> parseResults, String command,CallbackInfo ci) {
        //?} else {
        /*//? if >1.17 {
        private void onExecuteCommand(ParseResults<CommandSourceStack> parseResults, String command, CallbackInfoReturnable<Integer> ci) {
        //?} else {
        /^private void onExecuteCommand(CommandSourceStack arg, String command, CallbackInfoReturnable<Integer> ci) throws CommandSyntaxException {
        ^///?}
        *///?}
        //? if >1.17 {
        ServerPlayer player = parseResults.getContext().getSource().getPlayer();
        if (player == null) return;
        String[] words = command.split("\\s+");
        if(CombatConfig.Config.blockedCommands.contains(words[0]) && TagData.getCombat((IEntityDataSaver) player)){
            if(CombatConfig.Config.combatNotice) {
                player.sendSystemMessage(Component.nullToEmpty(CombatConfig.Config.blockedCommandMessage).copy().withStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
            }
            ci.cancel();
        }
        //?} else {
        /*ServerPlayer player = arg.getPlayerOrException();
        if (player == null) return;
        String[] words = command.split("\\s+");
        if (words[0].charAt(0) == '/') {words[0] = words[0].substring(1);}
        if(CombatConfig.Config.blockedCommands.contains(words[0]) && TagData.getCombat((IEntityDataSaver) player)){
            if(CombatConfig.Config.combatNotice) {
                player.sendMessage(Component.nullToEmpty(CombatConfig.Config.blockedCommandMessage).copy().withStyle(Style.EMPTY.withColor(ChatFormatting.RED)), Util.NIL_UUID);
            }
            ci.cancel();
        }
        *///?}
    }
}