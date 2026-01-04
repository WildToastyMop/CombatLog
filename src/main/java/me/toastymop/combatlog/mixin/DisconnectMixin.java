package me.toastymop.combatlog.mixin;

import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.toastymop.combatlog.CombatDisconnect.OnPlayerDisconnect;

@Mixin(ServerPlayer.class)
public abstract class DisconnectMixin {

    @Inject(method = "disconnect", at = @At("HEAD"))
    private void injectDisconnectMethod(CallbackInfo ci) {
        OnPlayerDisconnect((ServerPlayer) (Object) this);
    }

}