package me.toastymop.combatlog.mixin;

import com.mojang.brigadier.ParseResults;
import me.toastymop.combatlog.CombatConfig;
import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandManager.class)
public class CommandManagerMixin {
    @Inject(method = "execute", at = @At("HEAD"), cancellable = true)
    private void onExecuteCommand(ParseResults<ServerCommandSource> parseResults, String command, CallbackInfo ci) {
        ServerPlayerEntity player = parseResults.getContext().getSource().getPlayer();
        if (player == null) return;
        String[] words = command.split("\\s+");
        if(CombatConfig.Config.blockedCommands.contains(words[0]) && TagData.getCombat((IEntityDataSaver) player)){
            if(CombatConfig.Config.combatNotice) {
                player.sendMessage(Text.of(CombatConfig.Config.blockedCommandMessage).copy().fillStyle(Style.EMPTY.withColor(Formatting.RED)));
            }
            ci.cancel();
        }
    }
}
