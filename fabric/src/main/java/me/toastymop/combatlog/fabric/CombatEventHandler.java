package me.toastymop.combatlog.fabric;

import me.toastymop.combatlog.CombatTicks;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

public class CombatEventHandler implements ServerTickEvents.EndTick{
    public static final CombatEventHandler INSTANCE = new CombatEventHandler();

    @Override
    public void onEndTick(MinecraftServer server) {
        CombatTicks.CombatTick(server);
    }

}