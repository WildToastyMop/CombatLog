//? if fabric {
package me.toastymop.combatlog.platforms.fabric;

import me.toastymop.combatlog.CombatConfig;
import me.toastymop.combatlog.CombatCommands;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
//? if >=1.19.2 {
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
//?} else {
/*import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
*///?}

public class CombatLogFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CombatConfig.CONFIG = CombatConfig.load();
        ServerTickEvents.END_SERVER_TICK.register(CombatLogEventHandler.INSTANCE);
        //? if >=1.19.2 {
        CommandRegistrationCallback.EVENT.register(CombatCommands::register);
        //?} else {
        /*ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            CombatCommands.register(server.getCommands().getDispatcher());
        });
        *///?}

    }
}
//?}