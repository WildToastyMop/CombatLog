package me.toastymop.combatlog.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.toastymop.combatlog.CombatTicks.CombatTick;

@Mixin(MinecraftServer.class)
public abstract class TickMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void injectTickMethod(CallbackInfo ci) {
        CombatTick((MinecraftServer) (Object) this);
    }

}