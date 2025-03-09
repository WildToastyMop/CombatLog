package me.toastymop.combatlog.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.toastymop.combatlog.CombatCheck.CheckCombat;

@Mixin(LivingEntity.class)
public abstract class EntityDamageMixin extends Entity{

    public EntityDamageMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract boolean damage(ServerWorld world, DamageSource source, float amount);

    @Inject(method = "damage", at = @At("TAIL"))
    protected void injectCheckMethod(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        CheckCombat(this);
    }
}
