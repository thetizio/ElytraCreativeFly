package com.tizio.elytracreativefly;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("elytracreativefly")
public class ElytraCreativeFly {

    public static final Boolean CURIOS_LOADED = ModList.get().isLoaded("curios");

    public static final TagKey<Item> ELYTRAS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("elytracreativefly","elytra"));

    public ElytraCreativeFly(FMLJavaModLoadingContext context) {

        MinecraftForge.EVENT_BUS.addListener(this::onRespawn);
        MinecraftForge.EVENT_BUS.addListener(this::onChangeArmor);

        if(CURIOS_LOADED) CuriosCompat.init();

    }

    public void onRespawn(PlayerEvent.PlayerRespawnEvent playerEvent){
        Player player = playerEvent.getEntity();
        checkElytraAndFly(player);
    }

    public void onChangeArmor(LivingEquipmentChangeEvent event){
        if (event.getEntity() instanceof Player player){
            if ((ElytraCreativeFly.CURIOS_LOADED && !CuriosCompat.hasElytra(player)) || !ElytraCreativeFly.CURIOS_LOADED) {
                if (event.getTo().is(ELYTRAS)) {
                    player.getAbilities().mayfly = true;
                    player.onUpdateAbilities();
                } else {
                    if (!player.isCreative() && !player.isSpectator()) {
                        player.getAbilities().mayfly = false;
                        player.getAbilities().flying = false;
                        player.onUpdateAbilities();
                    }
                }
            }
        }
    }

    public static void checkElytraAndFly(Player player){
        ItemStack item = player.getInventory().getArmor(2);
        if (item.is(ELYTRAS) || (CURIOS_LOADED && CuriosCompat.hasElytra(player))){
            player.getAbilities().mayfly = true;
            player.onUpdateAbilities();
        }
    }

}