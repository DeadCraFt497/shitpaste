package me.ciruu.abyss.mixin.client;

import java.awt.Color;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class88;
import me.ciruu.abyss.modules.render.Chams;
import me.ciruu.abyss.modules.render.CrystalESP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RenderEnderCrystal.class}, priority=2000)
public abstract class MixinRenderEnderCrystal
extends Render {
    @Shadow
    public ModelBase modelEnderCrystal;
    @Shadow
    public ModelBase modelEnderCrystalNoBase;
    @Final
    @Shadow
    private static ResourceLocation ENDER_CRYSTAL_TEXTURES;

    protected MixinRenderEnderCrystal(RenderManager renderManager) {
        super(renderManager);
    }

    @Shadow
    public abstract void doRender(EntityEnderCrystal var1, double var2, double var4, double var6, float var8, float var9);

    private Color getRainbowColor(int n, float f, float f2) {
        float f3 = System.currentTimeMillis() % (long)n;
        return Color.getHSBColor(f3 /= (float)n, f, f2);
    }

    @Redirect(method={"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void render1(ModelBase modelBase, Entity entity, float f, float f2, float f3, float f4, float f5, float f6) {
        if (Manager.moduleManager.isModuleEnabled(Chams.class) && ((Boolean)((Chams)Manager.moduleManager.getModuleByClass(Chams.class)).crystals.getValue()).booleanValue() && Chams.mode.getValue() == Class88.ESP) {
            return;
        }
    }

    @Redirect(method={"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V", ordinal=1))
    private void render2(ModelBase modelBase, Entity entity, float f, float f2, float f3, float f4, float f5, float f6) {
        if (Manager.moduleManager.isModuleEnabled(Chams.class) && ((Boolean)((Chams)Manager.moduleManager.getModuleByClass(Chams.class)).crystals.getValue()).booleanValue() && Chams.mode.getValue() == Class88.ESP) {
            return;
        }
    }

    @Inject(method={"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"}, at={@At(value="RETURN")}, cancellable=true)
    public void IdoRender(EntityEnderCrystal entityEnderCrystal, double d, double d2, double d3, float f, float f2, CallbackInfo callbackInfo) {
        float f3;
        float f4;
        Globals.mc.renderManager.renderEngine.bindTexture(ENDER_CRYSTAL_TEXTURES);
        Chams chams = (Chams)Manager.moduleManager.getModuleByClass(Chams.class);
        if (chams == null) {
            return;
        }
        if (Manager.moduleManager.isModuleEnabled(Chams.class) && ((Boolean)chams.crystals.getValue()).booleanValue() && Chams.mode.getValue() == Class88.ESP) {
            Color color = (Boolean)chams.rainbow.getValue() != false ? this.getRainbowColor((int)(((Float)chams.rainbowspeed.getValue()).floatValue() * 1000.0f), ((Float)chams.rainbowsaturation.getValue()).floatValue(), ((Float)chams.rainbowbrightness.getValue()).floatValue()) : (Color)chams.color.getValue();
            GL11.glPushMatrix();
            f4 = (float)entityEnderCrystal.innerRotation + f2;
            GlStateManager.translate((double)d, (double)d2, (double)d3);
            Globals.mc.renderManager.renderEngine.bindTexture(ENDER_CRYSTAL_TEXTURES);
            f3 = MathHelper.sin((float)(f4 * 0.2f)) / 2.0f + 0.5f;
            f3 = f3 * f3 + f3;
            GL11.glEnable((int)32823);
            GL11.glPolygonOffset((float)1.0f, (float)-1.0E7f);
            GL11.glPushAttrib((int)1048575);
            if (!((Boolean)chams.lines.getValue()).booleanValue()) {
                GL11.glPolygonMode((int)1028, (int)6914);
            } else {
                GL11.glPolygonMode((int)1028, (int)6913);
            }
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)2848);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)((Color)chams.color.getValue()).getAlpha() / 255.0f));
            if (((Boolean)chams.lines.getValue()).booleanValue()) {
                GL11.glLineWidth((float)((Float)chams.width.getValue()).floatValue());
            }
            if (entityEnderCrystal.shouldShowBottom()) {
                this.modelEnderCrystal.render((Entity)entityEnderCrystal, 0.0f, f4 * 3.0f, f3 * 0.2f, 0.0f, 0.0f, 0.0625f);
            } else {
                this.modelEnderCrystalNoBase.render((Entity)entityEnderCrystal, 0.0f, f4 * 3.0f, f3 * 0.2f, 0.0f, 0.0f, 0.0625f);
            }
            GL11.glPopAttrib();
            GL11.glPolygonOffset((float)1.0f, (float)100000.0f);
            GL11.glDisable((int)32823);
            GL11.glPopMatrix();
        } else if (Manager.moduleManager.isModuleEnabled(Chams.class) && ((Boolean)chams.crystals.getValue()).booleanValue() && Chams.mode.getValue() == Class88.Walls) {
            GL11.glPushMatrix();
            float f5 = (float)entityEnderCrystal.innerRotation + f2;
            GlStateManager.translate((double)d, (double)d2, (double)d3);
            Globals.mc.renderManager.renderEngine.bindTexture(ENDER_CRYSTAL_TEXTURES);
            f4 = MathHelper.sin((float)(f5 * 0.2f)) / 2.0f + 0.5f;
            f4 = f4 * f4 + f4;
            GL11.glDisable((int)2929);
            GL11.glColor4f((float)((float)((Color)chams.walls.getValue()).getRed() / 255.0f), (float)((float)((Color)chams.walls.getValue()).getGreen() / 255.0f), (float)((float)((Color)chams.walls.getValue()).getBlue() / 255.0f), (float)((Color)chams.walls.getValue()).getAlpha());
            GL11.glDisable((int)3553);
            if (entityEnderCrystal.shouldShowBottom()) {
                this.modelEnderCrystal.render((Entity)entityEnderCrystal, 0.0f, f5 * 3.0f, f4 * 0.2f, 0.0f, 0.0f, 0.0625f);
            } else {
                this.modelEnderCrystalNoBase.render((Entity)entityEnderCrystal, 0.0f, f5 * 3.0f, f4 * 0.2f, 0.0f, 0.0f, 0.0625f);
            }
            GL11.glEnable((int)2929);
            GL11.glColor4f((float)((float)((Color)chams.visible.getValue()).getRed() / 255.0f), (float)((float)((Color)chams.visible.getValue()).getGreen() / 255.0f), (float)((float)((Color)chams.visible.getValue()).getBlue() / 255.0f), (float)((Color)chams.visible.getValue()).getAlpha());
            if (entityEnderCrystal.shouldShowBottom()) {
                this.modelEnderCrystal.render((Entity)entityEnderCrystal, 0.0f, f5 * 3.0f, f4 * 0.2f, 0.0f, 0.0f, 0.0625f);
            } else {
                this.modelEnderCrystalNoBase.render((Entity)entityEnderCrystal, 0.0f, f5 * 3.0f, f4 * 0.2f, 0.0f, 0.0f, 0.0625f);
            }
            GL11.glEnable((int)3553);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.popMatrix();
        } else {
            float f6 = (float)entityEnderCrystal.innerRotation + f2;
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)((float)d), (float)((float)d2), (float)((float)d3));
            Globals.mc.renderManager.renderEngine.bindTexture(ENDER_CRYSTAL_TEXTURES);
            f4 = MathHelper.sin((float)(f6 * 0.2f)) / 2.0f + 0.5f;
            f4 = f4 * f4 + f4;
            if (entityEnderCrystal.shouldShowBottom()) {
                this.modelEnderCrystal.render((Entity)entityEnderCrystal, 0.0f, f6 * 3.0f, f4 * 0.2f, 0.0f, 0.0f, 0.0625f);
            } else {
                this.modelEnderCrystalNoBase.render((Entity)entityEnderCrystal, 0.0f, f6 * 3.0f, f4 * 0.2f, 0.0f, 0.0f, 0.0625f);
            }
            GlStateManager.popMatrix();
        }
        if (Manager.moduleManager.isModuleEnabled(CrystalESP.class)) {
            float f7;
            float f8;
            float f9;
            float f10 = (float)entityEnderCrystal.innerRotation + f2;
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)((float)d), (float)((float)d2), (float)((float)d3));
            this.bindTexture(ENDER_CRYSTAL_TEXTURES);
            f4 = MathHelper.sin((float)(f10 * 0.2f)) / 2.0f + 0.5f;
            f4 += f4 * f4;
            if (this.renderOutlines) {
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode((int)this.getTeamColor((Entity)entityEnderCrystal));
            }
            if (Manager.moduleManager.isModuleEnabled(CrystalESP.class)) {
                f3 = (float)((Color)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).crystalcolor.getValue()).getRed() / 255.0f;
                f9 = (float)((Color)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).crystalcolor.getValue()).getGreen() / 255.0f;
                f8 = (float)((Color)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).crystalcolor.getValue()).getBlue() / 255.0f;
                f7 = (float)((Color)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).crystalcolor.getValue()).getAlpha() / 255.0f;
                if (((Boolean)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).crystalmodel.getValue()).booleanValue()) {
                    this.modelEnderCrystalNoBase.render((Entity)entityEnderCrystal, 0.0f, f10 * 3.0f, f4 * 0.2f, 0.0f, 0.0f, 0.0625f);
                }
                GlStateManager.pushMatrix();
                GL11.glPushAttrib((int)1048575);
                GL11.glPolygonMode((int)1032, (int)6913);
                GL11.glDisable((int)3553);
                GL11.glDisable((int)2896);
                GL11.glEnable((int)2848);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glDisable((int)2929);
                GL11.glDepthMask((boolean)false);
                GL11.glColor4f((float)((Boolean)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbow.getValue() != false ? (float)this.getRainbowColor((int)(((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowspeed.getValue()).floatValue() * 1000.0f), ((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowsaturation.getValue()).floatValue(), ((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowbrightness.getValue()).floatValue()).getRed() / 255.0f : f3), (float)((Boolean)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbow.getValue() != false ? (float)this.getRainbowColor((int)(((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowspeed.getValue()).floatValue() * 1000.0f), ((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowsaturation.getValue()).floatValue(), ((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowbrightness.getValue()).floatValue()).getGreen() / 255.0f : f9), (float)((Boolean)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbow.getValue() != false ? (float)this.getRainbowColor((int)(((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowspeed.getValue()).floatValue() * 1000.0f), ((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowsaturation.getValue()).floatValue(), ((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowbrightness.getValue()).floatValue()).getBlue() / 255.0f : f8), (float)((Boolean)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbow.getValue() != false ? (float)this.getRainbowColor((int)(((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowspeed.getValue()).floatValue() * 1000.0f), ((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowsaturation.getValue()).floatValue(), ((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowbrightness.getValue()).floatValue()).getRed() / 255.0f : f7));
                GL11.glLineWidth((float)((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).linewidth.getValue()).floatValue());
                this.modelEnderCrystalNoBase.render((Entity)entityEnderCrystal, 0.0f, f10 * 3.0f, f4 * 0.2f, 0.0f, 0.0f, 0.0625f);
                GL11.glDisable((int)2896);
                GL11.glEnable((int)2929);
                GL11.glDepthMask((boolean)true);
                GL11.glColor4f((float)((Boolean)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbow.getValue() != false ? (float)this.getRainbowColor((int)(((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowspeed.getValue()).floatValue() * 1000.0f), ((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowsaturation.getValue()).floatValue(), ((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowbrightness.getValue()).floatValue()).getRed() / 255.0f : f3), (float)((Boolean)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbow.getValue() != false ? (float)this.getRainbowColor((int)(((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowspeed.getValue()).floatValue() * 1000.0f), ((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowsaturation.getValue()).floatValue(), ((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowbrightness.getValue()).floatValue()).getGreen() / 255.0f : f9), (float)((Boolean)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbow.getValue() != false ? (float)this.getRainbowColor((int)(((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowspeed.getValue()).floatValue() * 1000.0f), ((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowsaturation.getValue()).floatValue(), ((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowbrightness.getValue()).floatValue()).getBlue() / 255.0f : f8), (float)((Boolean)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbow.getValue() != false ? (float)this.getRainbowColor((int)(((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowspeed.getValue()).floatValue() * 1000.0f), ((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowsaturation.getValue()).floatValue(), ((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).rainbowbrightness.getValue()).floatValue()).getRed() / 255.0f : f7));
                GL11.glLineWidth((float)((Float)((CrystalESP)Manager.moduleManager.getModuleByClass(CrystalESP.class)).linewidth.getValue()).floatValue());
                this.modelEnderCrystalNoBase.render((Entity)entityEnderCrystal, 0.0f, f10 * 3.0f, f4 * 0.2f, 0.0f, 0.0f, 0.0625f);
                GlStateManager.enableDepth();
                GlStateManager.popAttrib();
                GlStateManager.popMatrix();
            } else {
                this.modelEnderCrystalNoBase.render((Entity)entityEnderCrystal, 0.0f, f10 * 3.0f, f4 * 0.2f, 0.0f, 0.0f, 0.0625f);
            }
            if (this.renderOutlines) {
                GlStateManager.disableOutlineMode();
                GlStateManager.disableColorMaterial();
            }
            GlStateManager.popMatrix();
            BlockPos blockPos = entityEnderCrystal.getBeamTarget();
            if (blockPos != null) {
                this.bindTexture(RenderDragon.ENDERCRYSTAL_BEAM_TEXTURES);
                f9 = (float)blockPos.getX() + 0.5f;
                f8 = (float)blockPos.getY() + 0.5f;
                f7 = (float)blockPos.getZ() + 0.5f;
                double d4 = (double)f9 - entityEnderCrystal.posX;
                double d5 = (double)f8 - entityEnderCrystal.posY;
                double d6 = (double)f7 - entityEnderCrystal.posZ;
                RenderDragon.renderCrystalBeams((double)(d + d4), (double)(d2 - 0.3 + (double)(f4 * 0.4f) + d5), (double)(d3 + d6), (float)f2, (double)f9, (double)f8, (double)f7, (int)entityEnderCrystal.innerRotation, (double)entityEnderCrystal.posX, (double)entityEnderCrystal.posY, (double)entityEnderCrystal.posZ);
            }
        }
    }

    @Shadow
    public void doRender(Entity entity, double d, double d2, double d3, float f, float f2) {
        this.doRender((EntityEnderCrystal)entity, d, d2, d3, f, f2);
    }
}
