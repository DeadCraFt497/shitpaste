package me.ciruu.abyss.mixin.client;

import me.ciruu.abyss.AbyssMod;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.events.client.EventClientKeyDown;
import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={KeyBinding.class})
public class MixinKeyBinding {
    @Shadow
    private boolean pressed;

    @Inject(method={"isKeyDown"}, at={@At(value="RETURN")}, cancellable=true)
    private void isKeyDown(CallbackInfoReturnable callbackInfoReturnable) {
        EventClientKeyDown eventClientKeyDown = new EventClientKeyDown(Class53.PRE, (Boolean)callbackInfoReturnable.getReturnValue(), this.pressed);
        AbyssMod.EVENT_BUS.post(eventClientKeyDown);
        callbackInfoReturnable.setReturnValue(eventClientKeyDown.value);
    }
}
