package me.toastymop.combatlog.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.toastymop.combatlog.CombatCheck.CheckCombat;

@Mixin(LivingEntity.class)
public abstract class EntityDamageMixin extends Entity {

    public EntityDamageMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    //? if >1.21.5 {
    /*@Shadow
    public abstract boolean hurtServer(ServerLevel world, DamageSource source, float amount);

    @Inject(method = "hurtServer", at = @At("TAIL"))
    protected void injectCheckMethod(ServerLevel world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        CheckCombat(this);
    }
    *///?} else {
    @Shadow
    public abstract boolean hurt(DamageSource source, float amount);

    @Inject(method = "hurt", at = @At("TAIL"))
    protected void injectCheckMethod(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        CheckCombat(this);
    }
    //?}
}
