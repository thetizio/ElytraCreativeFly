package com.tizio.elytracreativefly;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class TrinketsCompat {

    public static void init(){
        TrinketsApi.registerTrinket(Items.ELYTRA, new Trinket() {

            @Override
            public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
                if (entity instanceof PlayerEntity player) {
                    player.getAbilities().allowFlying = true;
                    player.sendAbilitiesUpdate();
                }
            }

            @Override
            public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
                if (entity instanceof PlayerEntity player) {
                    if (!player.getEquippedStack(EquipmentSlot.CHEST).isIn(ElytraCreativeFly.ELYTRAS)) {
                        player.getAbilities().allowFlying = false;
                        player.getAbilities().flying = false;
                        player.sendAbilitiesUpdate();
                    }
                }
            }
        });

    }

    public static boolean hasElytra(PlayerEntity player) {

        var component = TrinketsApi.getTrinketComponent(player);

        if (component.isEmpty()) {
            return false;
        }

        return component.get().isEquipped(stack ->
                stack.isIn(ElytraCreativeFly.ELYTRAS)
        );
    }

}