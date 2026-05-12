package com.tizio.elytracreativefly;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@Mod("elytracreativefly")
public class ElytraCreativeFly {

    public static final Boolean CURIOS_LOADED = ModList.get().isLoaded("curios");

    public static final TagKey<Item> ELYTRAS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("elytracreativefly","elytra"));

    public ElytraCreativeFly(IEventBus modEventBus, ModContainer modContainer) {

        if (CURIOS_LOADED) {
            CuriosCompat cp = new CuriosCompat();
            cp.init(modEventBus);
        }

        NeoForge.EVENT_BUS.addListener(this::onRespawn);

    }

    public void onRespawn(PlayerEvent.PlayerRespawnEvent event){
        Player player = event.getEntity();
        checkElytraAndFly(player);
    }

    public static void checkElytraAndFly(Player player){
        ItemStack item = player.getInventory().getArmor(2);
        if (item.is(ELYTRAS) || (CURIOS_LOADED && CuriosCompat.hasElytra(player))){
            player.getAbilities().mayfly = true;
            player.onUpdateAbilities();
        }
    }

}