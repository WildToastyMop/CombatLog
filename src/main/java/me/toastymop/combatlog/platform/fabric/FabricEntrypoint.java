package me.toastymop.combatlog.platform.fabric;

//? fabric {

import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import me.toastymop.combatlog.CombatConfig;
import me.toastymop.combatlog.CombatCommands;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
//? if >=1.19.2 {
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
//?} else {
/*import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
 *///?}

public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		CombatConfig.CONFIG = CombatConfig.load();
		ServerTickEvents.END_SERVER_TICK.register(FabricEventSubscriber.INSTANCE);
		UseBlockCallback.EVENT.register(FabricEventSubscriber.INSTANCE::onUseBlock);
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
