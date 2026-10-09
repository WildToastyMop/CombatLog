package me.toastymop.combatlog;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;

public class CombatDamage {
    public static final ResourceKey<DamageType> COMBAT_LOG = ResourceKey.create(Registries.DAMAGE_TYPE,
            Identifier.tryParse("combatlog:combat_log"));

    public static DamageSource source(ServerLevel world, LivingEntity attacker) {
        //? if >=1.21 {
        Holder<DamageType> type = world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(COMBAT_LOG);
        //?} else {
        /*Holder<DamageType> type = world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(COMBAT_LOG);
        *///?}
        return attacker == null ? new DamageSource(type) : new DamageSource(type, attacker);
    }

    public static boolean isCombatLog(DamageSource source) {
        return source.is(COMBAT_LOG);
    }
}
