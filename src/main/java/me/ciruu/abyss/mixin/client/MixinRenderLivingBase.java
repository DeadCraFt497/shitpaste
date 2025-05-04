package me.ciruu.abyss.mixin.client;

import me.ciruu.abyss.AbyssMod;
import me.ciruu.abyss.Class90;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.events.render.EventRenderEntityModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RenderLivingBase.class}, priority=2000)
public abstract class MixinRenderLivingBase
extends Render {
    @Shadow
    protected ModelBase mainModel;

    public MixinRenderLivingBase(RenderManager renderManager, ModelBase modelBase, float f) {
        super(renderManager);
    }

    @Redirect(method={"renderModel"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void renderModelWrapper(ModelBase modelBase, Entity entity, float f, float f2, float f3, float f4, float f5, float f6) {
        EventRenderEntityModel eventRenderEntityModel = new EventRenderEntityModel(modelBase, entity, f, f2, f3, f4, f5, f6);
        eventRenderEntityModel.era = Class53.PRE;
        AbyssMod.EVENT_BUS.post(eventRenderEntityModel);
        if (eventRenderEntityModel.isCancelled()) {
            return;
        }
        modelBase.render(entity, f, f2, f3, f4, f5, f6);
        EventRenderEntityModel eventRenderEntityModel2 = new EventRenderEntityModel(modelBase, entity, f, f2, f3, f4, f5, f6);
        eventRenderEntityModel2.era = Class53.PRE;
        AbyssMod.EVENT_BUS.post(eventRenderEntityModel2);
    }

    @Inject(method={"renderLayers"}, at={@At(value="RETURN")})
    public void renderLayers(EntityLivingBase entityLivingBase, float f, float f2, float f3, float f4, float f5, float f6, float f7, CallbackInfo callbackInfo) {
        Class90 class90 = new Class90((RenderLivingBase)RenderLivingBase.class.cast((Object)this), this.mainModel, entityLivingBase, f, f2, f3, f4, f5, f6, f7);
        AbyssMod.EVENT_BUS.post(class90);
    }
}
