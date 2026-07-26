package me.toastymop.combatlog.mixin;

import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=1.21.11 {
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEnderpearl;

@Mixin(ThrownEnderpearl.class)
//?} else {
/*@Mixin(net.minecraft.world.entity.projectile.ThrownEnderpearl.class)
*///?}
public abstract class EnderpearlMixin {

	@Inject(method = "onHit", at = @At("HEAD"), cancellable = true)
	private void injectPearlConfigMethod(CallbackInfo ci) {
		Entity owner = ((net.minecraft.world.entity.projectile.Projectile)(Object)this).getOwner();
		if (owner != null && TagData.getCombat((IEntityDataSaver) owner)){
			((Entity) (Object) this).discard();
			ci.cancel();
		}
	}
}
