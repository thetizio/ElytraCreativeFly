package com.tizio.elytracreativefly;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ElytraCreativeFly implements ModInitializer {

    public static final TagKey<Item> ELYTRAS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("elytracreativefly","elytra"));

	@Override
	public void onInitialize() {

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            checkElytraAndFly(newPlayer);
        });

	}

    public static void checkElytraAndFly(ServerPlayer player){
        ItemStack item = player.getItemBySlot(EquipmentSlot.CHEST);
        if (item.is(ELYTRAS)){
            player.getAbilities().mayfly = true;
            player.onUpdateAbilities();
        }
    }

}