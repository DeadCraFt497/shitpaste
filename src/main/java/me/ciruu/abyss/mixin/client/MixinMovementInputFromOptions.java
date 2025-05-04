package me.ciruu.abyss.mixin.client;

import me.ciruu.abyss.AbyssMod;
import me.ciruu.abyss.Class325;
import me.ciruu.abyss.events.player.EventPlayerUpdateMoveState;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={MovementInputFromOptions.class}, priority=10000)
public abstract class MixinMovementInputFromOptions
extends MovementInput {
    @Inject(method={"updatePlayerMoveState"}, at={@At(value="RETURN")})
    public void updatePlayerMoveStateReturn(CallbackInfo callbackInfo) {
        AbyssMod.EVENT_BUS.post(new EventPlayerUpdateMoveState());
    }

    @Redirect(method={"updatePlayerMoveState"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/settings/KeyBinding;isKeyDown()Z"))
    public boolean isKeyPressed(KeyBinding keyBinding) {
        if (Class325.Method1305()) {
            return false;
        }
        return keyBinding.isKeyDown();
    }
}
