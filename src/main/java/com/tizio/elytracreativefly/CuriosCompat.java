package com.tizio.elytracreativefly;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CuriosCompat implements ICurioItem {

    public static void init(){
        CuriosApi.registerCurio(Items.ELYTRA, new CuriosCompat());
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity instanceof Player player){
            player.getAbilities().mayfly = true;
            player.onUpdateAbilities();
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity instanceof Player player){
            if (!player.getInventory().getArmor(2).is(ElytraCreativeFly.ELYTRAS)){
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
                player.onUpdateAbilities();
            }
        }
    }

    public static boolean hasElytra(Player player){
        return CuriosApi.getCuriosInventory(player).map(inv -> inv.findFirstCurio(Items.ELYTRA).isPresent()).orElse(false);
    }

}