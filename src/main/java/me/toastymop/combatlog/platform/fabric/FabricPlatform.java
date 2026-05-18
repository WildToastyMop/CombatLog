package me.toastymop.combatlog.platform.fabric;

//? fabric {

import me.toastymop.combatlog.platform.Platform;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatform implements Platform {

	@Override
	public boolean isModLoaded(String modId) {
		return FabricLoader.getInstance().isModLoaded(modId);
	}

	@Override
	public ModLoader loader() {
		return ModLoader.FABRIC;
	}

	@Override
	public String mcVersion() {
		return FabricLoader.getInstance().getRawGameVersion();
	}

	@Override
	public boolean isDevelopmentEnvironment() {
		return FabricLoader.getInstance().isDevelopmentEnvironment();
	}
}
//?}
