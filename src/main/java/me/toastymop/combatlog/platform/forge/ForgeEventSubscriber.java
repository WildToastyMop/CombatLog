package me.toastymop.combatlog.platform.forge;

//? if forge {

/*import me.toastymop.combatlog.CombatCommands;
import me.toastymop.combatlog.CombatConfig;
import me.toastymop.combatlog.CombatTicks;
import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.server.ServerStartedEvent;

@Mod.EventBusSubscriber(modid = "combatlog", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventSubscriber {
	@SubscribeEvent
	public static void onStart(ServerStartedEvent event) {

		CombatConfig.CONFIG = CombatConfig.load();
	}

	@SubscribeEvent
	public static void onTick(TickEvent.ServerTickEvent event){
		if (event.phase != TickEvent.Phase.END) return;
		MinecraftServer server = event.getServer();
		CombatTicks.CombatTick(server);
	}
	@SubscribeEvent
	public static void onRegisterCommands(RegisterCommandsEvent event) {
		CombatCommands.register(
				event.getDispatcher()
				,event.getBuildContext(),
				event.getCommandSelection()
		);
	}
	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		if (event.getLevel().isClientSide()) return;
		ItemStack block = new ItemStack(event.getLevel().getBlockState(event.getPos()).getBlock().asItem());
		ServerPlayer player = (ServerPlayer) event.getEntity();
		if(!TagData.getCombat((IEntityDataSaver) player)) return;
		if(CombatConfig.Config.disabledBlocks.contains(block.getItem())) {
			((ServerPlayer)player).sendSystemMessage(Component.nullToEmpty(CombatConfig.Config.disabledBlocksMessage).copy().withStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
			event.setCanceled(true);
		}
	}

}
*///?}
