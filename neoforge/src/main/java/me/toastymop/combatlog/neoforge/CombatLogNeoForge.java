package me.toastymop.combatlog.neoforge;

import me.toastymop.combatlog.CombatConfig;
import me.toastymop.combatlog.CombatLog;
import net.neoforged.fml.common.Mod;


@Mod(CombatLog.MOD_ID)
public class CombatLogNeoForge {
    public CombatLogNeoForge() {
        CombatConfig.CONFIG = CombatConfig.load();
    }
}
