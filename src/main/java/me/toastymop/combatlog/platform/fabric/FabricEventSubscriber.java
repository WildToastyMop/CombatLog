package me.toastymop.combatlog.platform.fabric;

//? fabric {

import me.toastymop.combatlog.CombatConfig;
import me.toastymop.combatlog.CombatTicks;
import me.toastymop.combatlog.util.IEntityDataSaver;
import me.toastymop.combatlog.util.TagData;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
//? if <1.17
/*import net.minecraft.Util;*/


public class FabricEventSubscriber implements ServerTickEvents.EndTick{
	public static final FabricEventSubscriber INSTANCE = new FabricEventSubscriber();

	@Override
	public void onEndTick(MinecraftServer server) {
		CombatTicks.CombatTick(server);
	}

	public InteractionResult onUseBlock(Player player, Level world, net.minecraft.world.InteractionHand hand, BlockHitResult hitResult) {
		if (world.isClientSide()) return InteractionResult.PASS;
		ItemStack block = new ItemStack(world.getBlockState(hitResult.getBlockPos()).getBlock().asItem());
		if(!TagData.getCombat((IEntityDataSaver) player)) return InteractionResult.PASS;
		//if(CombatConfig.Config.disabledBlocks.stream().anyMatch(disableStack -> disableStack == block.getItem())) {
		if(CombatConfig.Config.disabledBlocks.contains(block.getItem())) {
			//? if >1.17 {
			((ServerPlayer)player).sendSystemMessage(Component.nullToEmpty(CombatConfig.Config.disabledBlocksMessage).copy().withStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
			//?} else
			/*player.sendMessage(Component.nullToEmpty(CombatConfig.Config.disabledBlocksMessage).copy().withStyle(Style.EMPTY.withColor(ChatFormatting.RED)), Util.NIL_UUID);*/
			return InteractionResult.FAIL;
		}
		return InteractionResult.PASS;
	}

}
//?}
