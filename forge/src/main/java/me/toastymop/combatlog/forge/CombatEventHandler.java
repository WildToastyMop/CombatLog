package me.toastymop.combatlog.forge;

import me.toastymop.combatlog.CombatTicks;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "combatlog", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CombatEventHandler {
    @SubscribeEvent
    public static void onTick(TickEvent.ServerTickEvent.Post event) {
        CombatTicks.CombatTick(event.server());
    }
}