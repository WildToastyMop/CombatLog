package me.toastymop.combatlog.fabric;

import me.toastymop.combatlog.CombatConfig;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;

public class CombatLogFabric implements ModInitializer {
    //dont know why this got broke if anyone know why please help
    @Override
    public void onInitialize() {
        CombatConfig.getConfig();
    }
}
