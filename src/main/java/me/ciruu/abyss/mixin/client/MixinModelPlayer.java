package me.ciruu.abyss.mixin.client;

import me.ciruu.abyss.AbyssMod;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.events.render.EventRenderModelPlayer;
import me.ciruu.abyss.modules.render.Skeleton;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ModelPlayer.class}, priority=9999)
public class MixinModelPlayer {
    @Shadow
    public void render(Entity entity, float f, float f2, float f3, float f4, float f5, float f6) {
    }

    @Inject(method={"setRotationAngles"}, at={@At(value="RETURN")})
    public void setRotationAngles(float f, float f2, float f3, float f4, float f5, float f6, Entity entity, CallbackInfo callbackInfo) {
        if (Globals.mc.world != null && Globals.mc.player != null && entity instanceof EntityPlayer) {
            ((Skeleton)Manager.moduleManager.getModuleByClass(Skeleton.class)).Method2381((EntityPlayer)entity, null);
        }
    }

    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private void renderPre(Entity entity, float f, float f2, float f3, float f4, float f5, float f6, CallbackInfo callbackInfo) {
        EventRenderModelPlayer eventRenderModelPlayer = new EventRenderModelPlayer((ModelBase)ModelPlayer.class.cast(this), entity, f, f2, f3, f4, f5, f6);
        eventRenderModelPlayer.getClass53(Class53.PRE);
        AbyssMod.EVENT_BUS.post(eventRenderModelPlayer);
        if (eventRenderModelPlayer.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"render"}, at={@At(value="RETURN")})
    private void renderPost(Entity entity, float f, float f2, float f3, float f4, float f5, float f6, CallbackInfo callbackInfo) {
        EventRenderModelPlayer eventRenderModelPlayer = new EventRenderModelPlayer((ModelBase)ModelPlayer.class.cast(this), entity, f, f2, f3, f4, f5, f6);
        eventRenderModelPlayer.getClass53(Class53.POST);
        AbyssMod.EVENT_BUS.post(eventRenderModelPlayer);
    }
}
