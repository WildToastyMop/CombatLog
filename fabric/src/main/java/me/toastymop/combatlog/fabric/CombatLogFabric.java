package me.toastymop.combatlog.fabric;

import me.toastymop.combatlog.CombatConfig;
import net.fabricmc.api.ModInitializer;

public class CombatLogFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CombatConfig.CONFIG = CombatConfig.load();
    }
}