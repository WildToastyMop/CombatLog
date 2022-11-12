package me.toastymop.combatlog.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.toastymop.combatlog.CombatDisconnect.OnPlayerDisconnect;

@Mixin(ServerPlayerEntity.class)
public abstract class ModDisconnectMixin {

    @Inject(method = "onDisconnect", at = @At("HEAD"))
    private void injectDisconnectMethod(CallbackInfo ci) {
        OnPlayerDisconnect((ServerPlayerEntity) (Object) this);
    }

}