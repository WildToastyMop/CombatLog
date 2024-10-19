package me.toastymop.combatlog.forge;

import me.toastymop.combatlog.CombatConfig;
import me.toastymop.combatlog.CombatLog;
import net.minecraftforge.fml.common.Mod;

@Mod(CombatLog.MOD_ID)
public class CombatLogForge {
    public CombatLogForge() {
        CombatConfig.CONFIG = CombatConfig.load();
    }
}
