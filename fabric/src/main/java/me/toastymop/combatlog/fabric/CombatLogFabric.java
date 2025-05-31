package me.toastymop.combatlog.fabric;

import me.toastymop.combatlog.CombatConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class CombatLogFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CombatConfig.CONFIG = CombatConfig.load();
        ServerTickEvents.END_SERVER_TICK.register(CombatEventHandler.INSTANCE);
    }
}