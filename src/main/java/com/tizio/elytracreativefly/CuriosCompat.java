package com.tizio.elytracreativefly;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CuriosCompat {

    public void init(IEventBus modEventBus){
        modEventBus.addListener(this::registerCapabilities);
    }

    public void registerCapabilities(final RegisterCapabilitiesEvent evt) {
        evt.registerItem(
                CuriosCapability.ITEM,
                (stack, context) -> new ICurio() {

                    @Override
                    public ItemStack getStack() {
                        return stack;
                    }

                    @Override
                    public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                        LivingEntity entity = slotContext.entity();
                        if (entity instanceof Player player){
                            player.getAbilities().mayfly = true;
                            player.onUpdateAbilities();
                        }
                    }

                    @Override
                    public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                        LivingEntity entity = slotContext.entity();
                        if (entity instanceof Player player){
                            if (!hasElytra(player)){
                            player.getAbilities().mayfly = false;
                            player.getAbilities().flying = false;
                            player.onUpdateAbilities();
                            }
                        }
                    }

                },
                Items.ELYTRA);
    }

    public static boolean hasElytra(Player player){
        return CuriosApi.getCuriosInventory(player).map(inv -> inv.findFirstCurio(Items.ELYTRA).isPresent()).orElse(false);
    }

}