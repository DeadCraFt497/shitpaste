package me.ciruu.abyss.mixin.client;

import me.ciruu.abyss.Manager;
import me.ciruu.abyss.modules.movement.BoatFly;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityBoat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RenderBoat.class})
public abstract class MixinRenderBoat
extends Render {
    protected MixinRenderBoat(RenderManager renderManager) {
        super(renderManager);
    }

    @Inject(method={"doRender"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/renderer/GlStateManager;pushMatrix()V", shift=At.Shift.AFTER)})
    public void scale(EntityBoat entityBoat, double d, double d2, double d3, float f, float f2, CallbackInfo callbackInfo) {
        if (Manager.moduleManager.getModuleByClass(BoatFly.class) != null && Manager.moduleManager.isModuleEnabled(BoatFly.class)) {
            GlStateManager.scale((float)((Float)((BoatFly)Manager.moduleManager.getModuleByClass(BoatFly.class)).renderscale.getValue()).floatValue(), (float)((Float)((BoatFly)Manager.moduleManager.getModuleByClass(BoatFly.class)).renderscale.getValue()).floatValue(), (float)((Float)((BoatFly)Manager.moduleManager.getModuleByClass(BoatFly.class)).renderscale.getValue()).floatValue());
        }
    }
}
