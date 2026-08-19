package me.toastymop.combatlog;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.bossevents.CustomBossEvent;
import net.minecraft.server.bossevents.CustomBossEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static me.toastymop.combatlog.CombatConfig.Config.combatTime;
import static me.toastymop.combatlog.CombatConfig.Config.noticeType;

public class CombatNotice {

	public static void displayNotice(ServerPlayer player,int tagTime, boolean endCombat) {
		MinecraftServer server = player.level().getServer();
		CustomBossEvents bossManager = server.getCustomBossEvents();
		//? if >= 1.21.1 {
		Identifier bossID = Identifier.fromNamespaceAndPath("combatlog", player.getUUID().toString());
		//?} else
		//Identifier bossID = Identifier.tryBuild("combatlog", player.getUUID().toString());
		if (!endCombat) {
			String message = CombatConfig.Config.inCombat
					.replace("{timeLeft}", String.valueOf(tagTime / CombatCheck.tickRate));
			//? if >=1.19 {
			MutableComponent inCombat = Component.literal(message);
			//?} else {
			/*TextComponent inCombat = new TextComponent(message);
			 *///?}

			if (Objects.equals(noticeType, "hotbar")) {
				//? if <=1.21.11 {
                    	/*player.displayClientMessage(inCombat
                            .withStyle(Style.EMPTY.withColor(ChatFormatting.RED)), true);
						*///?} else {
				player.sendOverlayMessage(inCombat
						.withStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
				//?}
			} else if (Objects.equals(noticeType, "bossbar")) {
				CustomBossEvent event = bossManager.get(bossID);
				if (event == null) {
					event = bossManager.create(/*? >1.21.11 >> 'bossID'*/ RandomSource.create(), bossID, inCombat);
					event.setColor(BossEvent.BossBarColor.RED);
				}
				event.addPlayer((ServerPlayer) player);
				event.setName(inCombat);
				event.setProgress((float) (tagTime / CombatCheck.tickRate) / combatTime);
			}
		}

		if (endCombat) {
			//? if >=1.19 {
			MutableComponent outCombat = Component.literal(CombatConfig.Config.outCombat);
			//?} else {
			/*TextComponent outCombat = new TextComponent(CombatConfig.Config.outCombat);
			 *///?}

			if (Objects.equals(noticeType, "hotbar")) {
				//? if <=1.21.11 {
                    	/*player.displayClientMessage(outCombat
                            .withStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)), true);
						*///?} else {
				player.sendOverlayMessage(outCombat
						.withStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)));
				//?}
			} else if (Objects.equals(noticeType, "bossbar")) {
				CustomBossEvent event = bossManager.get(bossID);
				assert event != null;
				event.setName(outCombat);
				event.setProgress((float) (tagTime / CombatCheck.tickRate) / combatTime);
				event.setColor(BossEvent.BossBarColor.GREEN);
				Timer timer = new Timer("BossTimer", true);
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						server.execute(() -> {
							event.removeAllPlayers();
							bossManager.remove(event);
						});
					}
				}, 3000);
			}
		}
	}



}
