package me.toastymop.combatlog.forge;

import me.toastymop.combatlog.CombatConfig;
import me.toastymop.combatlog.CombatLog;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;

@Mod(CombatLog.MOD_ID)
public class CombatLogForge {
    public CombatLogForge() {
        CombatConfig.getConfig();
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    }
}
