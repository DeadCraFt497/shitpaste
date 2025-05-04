package me.ciruu.abyss.mixin.client;

import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import me.ciruu.abyss.AbyssMod;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.events.render.EventRenderWorld;
import me.ciruu.abyss.modules.exploit.NoEntityTrace;
import me.ciruu.abyss.modules.movement.Anchor;
import me.ciruu.abyss.modules.render.Aspect;
import me.ciruu.abyss.modules.render.NoRender;
import me.ciruu.abyss.modules.render.ViewClip;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.util.glu.Project;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={EntityRenderer.class}, priority=0x7FFFFFFE)
public class MixinEntityRenderer {
    @Shadow
    public ItemStack itemActivationItem;

    @Redirect(method={"getMouseOver"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
    public List getEntitiesInAABBexcludingHook(WorldClient worldClient, @Nullable Entity entity, AxisAlignedBB axisAlignedBB, @Nullable Predicate predicate) {
        if (Manager.moduleManager.isModuleEnabled(Anchor.class) && Anchor.Field333 || Manager.moduleManager.getModuleByClass(NoEntityTrace.class) != null && ((NoEntityTrace)Manager.moduleManager.getModuleByClass(NoEntityTrace.class)).Method436()) {
            return new ArrayList();
        }
        return worldClient.getEntitiesInAABBexcluding(entity, axisAlignedBB, predicate);
    }

    @Inject(method={"hurtCameraEffect"}, at={@At(value="HEAD")}, cancellable=true)
    public void hurtCameraEffect(float f, CallbackInfo callbackInfo) {
        if (Manager.moduleManager.isModuleEnabled(NoRender.class) && ((Boolean)((NoRender)Manager.moduleManager.getModuleByClass(NoRender.class)).hurtcam.getValue()).booleanValue()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderItemActivation"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderItemActivationHook(CallbackInfo callbackInfo) {
        if (this.itemActivationItem != null && Manager.moduleManager.isModuleEnabled(NoRender.class) && ((Boolean)((NoRender)Manager.moduleManager.getModuleByClass(NoRender.class)).totempop.getValue()).booleanValue() && this.itemActivationItem.getItem() == Items.TOTEM_OF_UNDYING) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"updateLightmap"}, at={@At(value="HEAD")}, cancellable=true)
    private void updateLightmap(float f, CallbackInfo callbackInfo) {
        if (Manager.moduleManager.isModuleEnabled(NoRender.class) && ((Boolean)((NoRender)Manager.moduleManager.getModuleByClass(NoRender.class)).skylight.getValue()).booleanValue()) {
            callbackInfo.cancel();
        }
    }

    @Redirect(method={"setupCameraTransform"}, at=@At(value="FIELD", target="Lnet/minecraft/client/entity/EntityPlayerSP;prevTimeInPortal:F"))
    public float prevTimeInPortalHook(EntityPlayerSP entityPlayerSP) {
        if (Manager.moduleManager.isModuleEnabled(NoRender.class) && ((Boolean)((NoRender)Manager.moduleManager.getModuleByClass(NoRender.class)).nausea.getValue()).booleanValue()) {
            return -3.4028235E38f;
        }
        return entityPlayerSP.prevTimeInPortal;
    }

    @Inject(method={"setupFog"}, at={@At(value="HEAD")}, cancellable=true)
    public void setupFogHook(int n, float f, CallbackInfo callbackInfo) {
        if (Manager.moduleManager.isModuleEnabled(NoRender.class) && ((Boolean)((NoRender)Manager.moduleManager.getModuleByClass(NoRender.class)).fog.getValue()).booleanValue()) {
            callbackInfo.cancel();
        }
    }

    @ModifyVariable(method={"orientCamera"}, ordinal=3, at=@At(value="STORE", ordinal=0), require=1)
    public double changeCameraDistanceHook(double d) {
        return Manager.moduleManager.isModuleEnabled(ViewClip.class) ? (double)((Float)((ViewClip)Manager.moduleManager.getModuleByClass(ViewClip.class)).distance.getValue()).floatValue() : d;
    }

    @ModifyVariable(method={"orientCamera"}, ordinal=7, at=@At(value="STORE", ordinal=0), require=1)
    public double orientCameraHook(double d) {
        return Manager.moduleManager.isModuleEnabled(ViewClip.class) ? (double)((Float)((ViewClip)Manager.moduleManager.getModuleByClass(ViewClip.class)).distance.getValue()).floatValue() : d;
    }

    @Redirect(method={"setupCameraTransform"}, at=@At(value="INVOKE", target="Lorg/lwjgl/util/glu/Project;gluPerspective(FFFF)V"))
    private void onSetupCameraTransform(float f, float f2, float f3, float f4) {
        EventRenderWorld eventRenderWorld = new EventRenderWorld((float)Globals.mc.displayWidth / (float)Globals.mc.displayHeight);
        AbyssMod.EVENT_BUS.post(eventRenderWorld);
        Project.gluPerspective((float)f, (float)(Manager.moduleManager.getModuleByClass(Aspect.class) != null && Manager.moduleManager.isModuleEnabled(Aspect.class) ? eventRenderWorld.getFloatFL() : f2), (float)f3, (float)f4);
    }

    @Redirect(method={"renderWorldPass"}, at=@At(value="INVOKE", target="Lorg/lwjgl/util/glu/Project;gluPerspective(FFFF)V"))
    private void onRenderWorldPass(float f, float f2, float f3, float f4) {
        EventRenderWorld eventRenderWorld = new EventRenderWorld((float)Globals.mc.displayWidth / (float)Globals.mc.displayHeight);
        AbyssMod.EVENT_BUS.post(eventRenderWorld);
        Project.gluPerspective((float)f, (float)(Manager.moduleManager.getModuleByClass(Aspect.class) != null && Manager.moduleManager.isModuleEnabled(Aspect.class) ? eventRenderWorld.getFloatFL() : f2), (float)f3, (float)f4);
    }

    @Redirect(method={"renderCloudsCheck"}, at=@At(value="INVOKE", target="Lorg/lwjgl/util/glu/Project;gluPerspective(FFFF)V"))
    private void onRenderCloudsCheck(float f, float f2, float f3, float f4) {
        EventRenderWorld eventRenderWorld = new EventRenderWorld((float)Globals.mc.displayWidth / (float)Globals.mc.displayHeight);
        AbyssMod.EVENT_BUS.post(eventRenderWorld);
        Project.gluPerspective((float)f, (float)(Manager.moduleManager.getModuleByClass(Aspect.class) != null && Manager.moduleManager.isModuleEnabled(Aspect.class) ? eventRenderWorld.getFloatFL() : f2), (float)f3, (float)f4);
    }
}
