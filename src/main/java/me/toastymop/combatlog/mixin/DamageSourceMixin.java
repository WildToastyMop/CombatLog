package me.toastymop.combatlog.mixin;

import me.toastymop.combatlog.CombatDamage;
import me.toastymop.combatlog.CombatDisconnect;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageSource.class)
public class DamageSourceMixin {

    @Inject(method = "getLocalizedDeathMessage", at = @At("HEAD"), cancellable = true)
    private void injectDeathMessage(LivingEntity victim, CallbackInfoReturnable<Component> cir) {
        if (!CombatDamage.isCombatLog((DamageSource) (Object) this)) return;
        cir.setReturnValue(CombatDisconnect.deathMessage(victim));
    }
}
