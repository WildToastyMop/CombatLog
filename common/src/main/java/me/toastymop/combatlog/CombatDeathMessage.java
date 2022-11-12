package me.toastymop.combatlog;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class CombatDeathMessage extends EntityDamageSource {

    public static final String COMBATLOG = "combatlog";

    public CombatDeathMessage(String name, Entity source) {
        super(name, source);
    }

    public CombatDeathMessage setBypassesArmor(){
        super.setBypassesArmor();
        return this;
    }

    @Override
    public Text getDeathMessage(LivingEntity entity) {
        Text result = new LiteralText("");
        result.getSiblings().add(entity.getDisplayName());
        result.getSiblings().add(Text.of(CombatConfig.deathMessage));

        return result;
    }
}