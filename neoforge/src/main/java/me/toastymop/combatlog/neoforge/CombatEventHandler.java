package me.toastymop.combatlog.neoforge;

import me.toastymop.combatlog.CombatTicks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import net.neoforged.neoforge.event.tick.ServerTickEvent;


@EventBusSubscriber(modid = "combatlog")
public class CombatEventHandler {
    @SubscribeEvent
    public static void onTick(ServerTickEvent.Post event){
        CombatTicks.CombatTick(event.getServer());
    }
}