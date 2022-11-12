package me.toastymop.combatlog.forge;

import me.toastymop.combatlog.CombatConfig;
import me.toastymop.combatlog.CombatLog;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkConstants;

@Mod(CombatLog.MOD_ID)
public class CombatLogForge {
    public CombatLogForge() {
        CombatConfig.getConfig();
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    }
}
