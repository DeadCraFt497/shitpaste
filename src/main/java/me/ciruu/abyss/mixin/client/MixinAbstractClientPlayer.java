package me.ciruu.abyss.mixin.client;

import me.ciruu.abyss.Manager;
import me.ciruu.abyss.mixin.client.MixinEntityPlayer;
import me.ciruu.abyss.modules.client.Capes;
import me.ciruu.abyss.util.Cape;
import net.minecraft.client.entity.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={AbstractClientPlayer.class}, priority=0x7FFFFFFE)
public abstract class MixinAbstractClientPlayer
extends MixinEntityPlayer {
    @Shadow
    public abstract boolean isSpectator();

    @Inject(method={"getLocationCape"}, at={@At(value="RETURN")}, cancellable=true)
    public void getCape(CallbackInfoReturnable callbackInfoReturnable) {
        if (!Manager.moduleManager.isModuleEnabled(Capes.class)) {
            return;
        }
        ((Capes)Manager.moduleManager.getModuleByClass(Capes.class)).updateAnimationTime();
        AbstractClientPlayer abstractClientPlayer = null;
        String string = abstractClientPlayer.getUniqueID().toString();
        Cape cape = Manager.capeManager.getCape(string);
        if (cape != null && cape.Method1543()) {
            callbackInfoReturnable.setReturnValue(cape.Method1544());
        }
    }
}
