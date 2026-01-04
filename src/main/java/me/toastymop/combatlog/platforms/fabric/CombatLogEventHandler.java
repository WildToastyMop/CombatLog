//? if fabric {
/*package me.toastymop.combatlog.platforms.fabric;

import me.toastymop.combatlog.CombatTicks;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

public class CombatLogEventHandler implements ServerTickEvents.EndTick{
    public static final CombatLogEventHandler INSTANCE = new CombatLogEventHandler();

    @Override
    public void onEndTick(MinecraftServer server) {
        CombatTicks.CombatTick(server);
    }

}
*///?}