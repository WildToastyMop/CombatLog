package me.toastymop.combatlog;


import java.nio.file.Path;

public class CombatExpectPlatform {
    public static Path getConfigDirectory() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }
}
