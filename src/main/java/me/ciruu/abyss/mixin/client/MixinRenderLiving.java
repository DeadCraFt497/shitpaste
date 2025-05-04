package me.ciruu.abyss.mixin.client;

import me.ciruu.abyss.Manager;
import me.ciruu.abyss.modules.render.Chams;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RenderLiving.class})
public class MixinRenderLiving {
    @Inject(method={"doRender"}, at={@At(value="HEAD")})
    private void injectChamsPre(EntityLiving entityLiving, double d, double d2, double d3, float f, float f2, CallbackInfo callbackInfo) {
        if (Manager.moduleManager.isModuleEnabled(Chams.class) && Chams.Method230((Entity)entityLiving)) {
            GL11.glEnable((int)32823);
            GL11.glPolygonOffset((float)1.0f, (float)-1000000.0f);
        }
    }

    @Inject(method={"doRender"}, at={@At(value="RETURN")})
    private void injectChamsPost(EntityLiving entityLiving, double d, double d2, double d3, float f, float f2, CallbackInfo callbackInfo) {
        if (Manager.moduleManager.isModuleEnabled(Chams.class) && Chams.Method230((Entity)entityLiving)) {
            GL11.glPolygonOffset((float)1.0f, (float)1000000.0f);
            GL11.glDisable((int)32823);
        }
    }
}
