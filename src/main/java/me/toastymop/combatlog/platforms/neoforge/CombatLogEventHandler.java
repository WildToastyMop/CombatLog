//? if neoforge {
/*package me.toastymop.combatlog.platforms.neoforge;

import me.toastymop.combatlog.CombatTicks;
import me.toastymop.combatlog.CombatCommands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber(modid = "combatlog")
public class CombatLogEventHandler {
    @SubscribeEvent
    public static void onTick(ServerTickEvent.Post event) {
        CombatTicks.CombatTick(event.getServer());
    }
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CombatCommands.register(
                event.getDispatcher(),
                event.getBuildContext(),
                event.getCommandSelection()
        );
    }
}
*///?}