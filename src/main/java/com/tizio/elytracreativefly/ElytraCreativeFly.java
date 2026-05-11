package com.tizio.elytracreativefly;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ElytraCreativeFly implements ModInitializer {

	public static final boolean TRINKETS_LOADED = FabricLoader.getInstance().isModLoaded("trinkets");

	public static final TagKey<Item> ELYTRAS = TagKey.of(RegistryKeys.ITEM, Identifier.of("elytracreativefly","elytra"));

	@Override
	public void onInitialize() {

		ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
			checkElytraAndFly(newPlayer);
		});

		if (TRINKETS_LOADED) TrinketsCompat.init();

	}

	public static void checkElytraAndFly(ServerPlayerEntity player){
		ItemStack item = player.getEquippedStack(EquipmentSlot.CHEST);
		if (item.isIn(ELYTRAS) || (TRINKETS_LOADED && TrinketsCompat.hasElytra(player))){
			player.getAbilities().allowFlying = true;
			player.sendAbilitiesUpdate();
		}
	}

}