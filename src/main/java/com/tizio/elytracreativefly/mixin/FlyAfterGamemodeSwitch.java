package com.tizio.elytracreativefly.mixin;

import com.tizio.elytracreativefly.ElytraCreativeFly;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerGameMode.class)
public class FlyAfterGamemodeSwitch {

    @Shadow
    @Final
    protected ServerPlayer player;

    @Inject(method = "setGameModeForPlayer", at = @At("TAIL"))
    private void onChange(GameType gameModeForPlayer, GameType previousGameModeForPlayer, CallbackInfo ci) {
        ElytraCreativeFly.checkElytraAndFly(this.player);
    }

}