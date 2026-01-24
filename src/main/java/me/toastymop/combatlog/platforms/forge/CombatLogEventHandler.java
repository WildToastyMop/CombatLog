//? if forge {
/*package me.toastymop.combatlog.platforms.forge;

import me.toastymop.combatlog.CombatCommands;
import me.toastymop.combatlog.CombatTicks;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
//? if =1.16.5 {
/^import net.minecraftforge.fml.server.ServerLifecycleHooks;
^///?}

@Mod.EventBusSubscriber(modid = "combatlog", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CombatLogEventHandler {
    @SubscribeEvent
    public static void onTick(TickEvent.ServerTickEvent event){
        if (event.phase != TickEvent.Phase.END) return;
        //? if >=1.17 {
        MinecraftServer server = event.getServer();
        //?} else {
        /^MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        ^///?}
        CombatTicks.CombatTick(server);
    }
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CombatCommands.register(
                event.getDispatcher()
                //? if >=1.19.2 {
                ,event.getBuildContext(),
                event.getCommandSelection()
                //?}
        );
    }
}
*///?}