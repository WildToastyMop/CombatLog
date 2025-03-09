package me.toastymop.combatlog.mixin;

import me.toastymop.combatlog.CombatConfig;
import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderPearlItem.class)
public abstract class EnderPearlMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void injectEnderPeal(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir){
        if(TagData.getCombat((IEntityDataSaver) user) && CombatConfig.Config.disablePearl){
            cir.setReturnValue(ActionResult.FAIL);
        }
    }
}