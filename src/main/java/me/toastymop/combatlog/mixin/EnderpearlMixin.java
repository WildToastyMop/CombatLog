package me.toastymop.combatlog.mixin;

import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=1.21.11 {
import org.jspecify.annotations.Nullable;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEnderpearl;

@Mixin(ThrownEnderpearl.class)
//?} else {
/*import org.jetbrains.annotations.Nullable;

@Mixin(net.minecraft.world.entity.projectile.ThrownEnderpearl.class)
 *///?}
public abstract class EnderpearlMixin {
	@Shadow
	public abstract @Nullable Entity getOwner();

	@Inject(method = "onHit", at = @At("HEAD"), cancellable = true)
	private void injectPearlConfigMethod(CallbackInfo ci) {
		if (TagData.getCombat((IEntityDataSaver) this.getOwner())){
			((Entity) (Object) this).discard();
			ci.cancel();
		}
	}
}
