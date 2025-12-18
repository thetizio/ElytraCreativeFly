package com.tizio.elytracreativefly;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;

public class ElytraCreativeFly implements ModInitializer {

	@Override
	public void onInitialize() {

		ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
			checkElytraAndFly(newPlayer);
		});

	}

	public void checkElytraAndFly(ServerPlayerEntity player){
		ItemStack item = player.getEquippedStack(EquipmentSlot.CHEST);
		if (item.getItem() == Items.ELYTRA){
			player.getAbilities().allowFlying = true;
			player.sendAbilitiesUpdate();
		}
	}

}