package me.ciruu.abyss.mixin.client;

import me.ciruu.abyss.AbyssMod;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.events.render.EventRenderItemSide;
import me.ciruu.abyss.events.render.EventRenderPostFirstPerson;
import me.ciruu.abyss.events.render.EventRenderPreFirstPerson;
import me.ciruu.abyss.modules.render.ItemViewModel;
import me.ciruu.abyss.modules.render.NoRender;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ItemRenderer.class})
public abstract class MixinItemRenderer {
    private boolean injection = true;

    @Shadow
    public abstract void renderItemInFirstPerson(AbstractClientPlayer var1, float var2, float var3, EnumHand var4, float var5, ItemStack var6, float var7);

    @Inject(method={"transformSideFirstPerson"}, at={@At(value="HEAD")})
    public void transformSideFirstPerson(EnumHandSide enumHandSide, float f, CallbackInfo callbackInfo) {
        EventRenderPreFirstPerson eventRenderPreFirstPerson = new EventRenderPreFirstPerson(enumHandSide);
        AbyssMod.EVENT_BUS.post(eventRenderPreFirstPerson);
    }

    @Inject(method={"transformFirstPerson"}, at={@At(value="TAIL")})
    public void transformFirstPersonTail(EnumHandSide enumHandSide, float f, CallbackInfo callbackInfo) {
        EventRenderPostFirstPerson eventRenderPostFirstPerson = new EventRenderPostFirstPerson(enumHandSide);
        AbyssMod.EVENT_BUS.post(eventRenderPostFirstPerson);
    }

    @Inject(method={"transformFirstPerson"}, at={@At(value="HEAD")})
    public void transformFirstPerson(EnumHandSide enumHandSide, float f, CallbackInfo callbackInfo) {
        EventRenderPreFirstPerson eventRenderPreFirstPerson = new EventRenderPreFirstPerson(enumHandSide);
        AbyssMod.EVENT_BUS.post(eventRenderPreFirstPerson);
    }

    @Inject(method={"renderItemSide"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/renderer/GlStateManager;pushMatrix()V", shift=At.Shift.AFTER)})
    public void scale(EntityLivingBase entityLivingBase, ItemStack itemStack, ItemCameraTransforms.TransformType transformType, boolean bl, CallbackInfo callbackInfo) {
        if (Manager.moduleManager.isModuleEnabled(ItemViewModel.class) && Globals.mc.player != null && entityLivingBase == Globals.mc.player && (transformType == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND || transformType == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND)) {
            EventRenderItemSide eventRenderItemSide = new EventRenderItemSide(bl);
            AbyssMod.EVENT_BUS.post(eventRenderItemSide);
            GlStateManager.scale((float)eventRenderItemSide.getX(), (float)eventRenderItemSide.getY(), (float)eventRenderItemSide.getZ());
        }
    }

    @Inject(method={"renderFireInFirstPerson"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderFireInFirstPersonHook(CallbackInfo callbackInfo) {
        if (Manager.moduleManager.isModuleEnabled(NoRender.class) && ((Boolean)((NoRender)Manager.moduleManager.getModuleByClass(NoRender.class)).fire.getValue()).booleanValue()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderSuffocationOverlay"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderSuffocationOverlay(CallbackInfo callbackInfo) {
        if (Manager.moduleManager.isModuleEnabled(NoRender.class) && ((Boolean)((NoRender)Manager.moduleManager.getModuleByClass(NoRender.class)).blocks.getValue()).booleanValue()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderWaterOverlayTexture"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderWaterOverlayTexture(CallbackInfo callbackInfo) {
        if (Manager.moduleManager.isModuleEnabled(NoRender.class) && ((Boolean)((NoRender)Manager.moduleManager.getModuleByClass(NoRender.class)).blocks.getValue()).booleanValue()) {
            callbackInfo.cancel();
        }
    }
}
