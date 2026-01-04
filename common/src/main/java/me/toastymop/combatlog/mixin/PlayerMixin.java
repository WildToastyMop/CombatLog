package me.toastymop.combatlog.mixin;


import me.toastymop.combatlog.CombatConfig;
import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {
    @Inject(method = "tryToStartFallFlying", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;startFallFlying()V"), cancellable = true)
    private void preventFlying(CallbackInfoReturnable<Boolean> cir) {
        if (CombatConfig.Config.disableElytra && TagData.getCombat((IEntityDataSaver) this)) {
            cir.setReturnValue(false);
        }
    }
}
