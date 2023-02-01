package me.toastymop.combatlog.fabric;

import me.toastymop.combatlog.CombatConfig;
import net.fabricmc.api.DedicatedServerModInitializer;

public class CombatLogFabric implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        CombatConfig.getConfig();
    }
}
