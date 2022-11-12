package me.toastymop.combatlog.forge;

import me.toastymop.combatlog.CombatExpectPlatform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class CombatExpectPlatformImpl {
    /**
     * This is our actual method to {@link CombatExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
