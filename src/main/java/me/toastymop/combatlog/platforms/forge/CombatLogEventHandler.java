//? if forge {
package me.toastymop.combatlog.platforms.forge;

import me.toastymop.combatlog.CombatTicks;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "combatlog", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CombatLogEventHandler {
    @SubscribeEvent
    public static void onTick(TickEvent.ServerTickEvent event){
        if (event.phase != TickEvent.Phase.END) return;
        CombatTicks.CombatTick(event.getServer());
    }
}
//?}