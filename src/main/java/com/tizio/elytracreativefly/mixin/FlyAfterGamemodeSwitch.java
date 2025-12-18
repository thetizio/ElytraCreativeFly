package com.tizio.elytracreativefly.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerInteractionManager.class)
public class FlyAfterGamemodeSwitch {

    @Final
    @Shadow
    protected ServerPlayerEntity player;

    @Inject(method = "setGameMode", at = @At("TAIL"))
    private void onChange(GameMode gameMode, GameMode previousGameMode, CallbackInfo ci) {
        if (this.player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA){
            this.player.getAbilities().allowFlying = true;
            this.player.sendAbilitiesUpdate();
        }
    }
}