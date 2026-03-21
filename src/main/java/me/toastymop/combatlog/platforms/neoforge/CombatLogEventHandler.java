//? if neoforge {
/*package me.toastymop.combatlog.platforms.neoforge;

import me.toastymop.combatlog.CombatCheck;
import me.toastymop.combatlog.CombatConfig;
import me.toastymop.combatlog.CombatTicks;
import me.toastymop.combatlog.CombatCommands;
import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber(modid = "combatlog")
public class CombatLogEventHandler {
    @SubscribeEvent
    public static void onStart(ServerStartedEvent event) {
        CombatConfig.CONFIG = CombatConfig.load();
    }
    @SubscribeEvent
    public static void onTick(ServerTickEvent.Post event) {
        CombatTicks.CombatTick(event.getServer());
    }
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CombatCommands.register(
                event.getDispatcher(),
                event.getBuildContext(),
                event.getCommandSelection()
        );
    }
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide()) return;
        ItemStack block = new ItemStack(event.getLevel().getBlockState(event.getPos()).getBlock().asItem());
        ServerPlayer player = (ServerPlayer) event.getEntity();
        if(!TagData.getCombat((IEntityDataSaver) player)) return;
        //if(CombatConfig.Config.disabledBlocks.stream().anyMatch(disableStack -> disableStack == block.getItem())) {
        if(CombatConfig.Config.disabledBlocks.contains(block.getItem())) {
            ((ServerPlayer)player).sendSystemMessage(Component.nullToEmpty(CombatConfig.Config.disabledBlocksMessage).copy().withStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
            event.setCanceled(true);
        }
    }
}
*///?}