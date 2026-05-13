package com.tizio.elytracreativefly.mixin;

import com.tizio.elytracreativefly.ElytraCreativeFly;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class ChangeFlyOnEquip {

    @Inject(method = "onEquipItem", at = @At("TAIL"))
    private void onEquipItem(EquipmentSlot slot, ItemStack oldItem, ItemStack newItem, CallbackInfo ci){
        if (oldItem.toString().equals(newItem.toString())) return;

        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity instanceof Player player && slot == EquipmentSlot.CHEST) {

            if (newItem.is(ElytraCreativeFly.ELYTRAS)) {
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