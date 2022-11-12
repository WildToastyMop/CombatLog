package me.toastymop.combatlog.fabric;

import me.toastymop.combatlog.CombatExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class CombatExpectPlatformImpl {
    /**
     * This is our actual method to {@link CombatExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
