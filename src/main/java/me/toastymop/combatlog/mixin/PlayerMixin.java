package me.toastymop.combatlog.mixin;


import me.toastymop.combatlog.config.CombatConfig;
import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {
    @Inject(method = "startFallFlying", at = @At("HEAD"), cancellable = true)
    private void preventFlying(CallbackInfo ci) {
        if (CombatConfig.Config.disableElytra && TagData.getCombat((IEntityDataSaver) this)) {
            ci.cancel();
        }
    }
}
