package me.toastymop.combatlog.mixin;

import me.toastymop.combatlog.util.IEntityDataSaver;
import net.minecraft.world.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >1.21.5 {
/*import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
*///?} else {
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//?}

@Mixin(Entity.class)
public abstract class EntityDataSaverMixin implements IEntityDataSaver {
    private CompoundTag persistentData;

    @Override
    public CompoundTag getPersistentData() {
        if(this.persistentData == null) {
            this.persistentData = new CompoundTag();
        }
        return persistentData;
    }

    //? if >1.21.5 {
    /*@Inject(method = "saveWithoutId", at = @At("HEAD"))
    protected void injectWriteMethod(ValueOutput view, CallbackInfo ci) {
        if(persistentData != null) {
            view.store("combatLog", CompoundTag.CODEC, persistentData);
        }
    }

    @Inject(method = "load", at = @At("HEAD"))
    protected void injectReadMethod(ValueInput view, CallbackInfo ci) {
        persistentData = view.read("combatlog", CompoundTag.CODEC).orElse(null);
    }
    *///?} else {
    @Inject(method = "saveWithoutId", at = @At("HEAD"))
    protected void injectWriteMethod(CompoundTag nbt, CallbackInfoReturnable info) {
        if(persistentData != null) {
            nbt.put("combatLog", persistentData);
        }
    }

    @Inject(method = "load", at = @At("HEAD"))
    protected void injectReadMethod(CompoundTag nbt, CallbackInfo info) {
        if(nbt.contains("combatLog", 10)) {
            persistentData = nbt.getCompound("combatLog");
        }
    }
    //?}
}
