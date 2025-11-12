package com.tizio.elytracreativefly.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class CreativeFly {

	@Inject(method = "onEquipStack", at = @At("TAIL"))
	private void onEquipStack(EquipmentSlot slot, ItemStack oldStack, ItemStack newStack, CallbackInfo ci) {
		LivingEntity entity = (LivingEntity) (Object) this;

		if (entity instanceof PlayerEntity player && slot == EquipmentSlot.CHEST) {

			if (newStack.getItem() == Items.ELYTRA) {
				player.getAbilities().allowFlying = true;
				player.sendAbilitiesUpdate();
			} else {
				if (!player.isCreative() && !player.isSpectator()) {
					player.getAbilities().allowFlying = false;
					player.getAbilities().flying = false;
					player.sendAbilitiesUpdate();
				}
			}
		}
	}
}