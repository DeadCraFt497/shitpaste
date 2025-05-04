package me.ciruu.abyss.mixin.client;

import java.awt.Color;
import me.ciruu.abyss.Class325;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class88;
import me.ciruu.abyss.modules.render.Chams;
import me.ciruu.abyss.modules.render.NameTags;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RenderPlayer.class})
public class MixinRenderPlayer {
    @Shadow
    public ModelPlayer getMainModel() {
        return new ModelPlayer(0.0f, false);
    }

    @Inject(method={"renderEntityName"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderLivingLabel(AbstractClientPlayer abstractClientPlayer, double d, double d2, double d3, String string, double d4, CallbackInfo callbackInfo) {
        if (Manager.moduleManager.isModuleEnabled(NameTags.class)) {
            callbackInfo.cancel();
        }
    }

    @Redirect(method={"renderRightArm"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/ModelRenderer;render(F)V"))
    private void renderRightArm(ModelRenderer modelRenderer, float f) {
        Color color;
        Chams chams = (Chams)Manager.moduleManager.getModuleByClass(Chams.class);
        Color color2 = color = (Boolean)chams.rainbow.getValue() != false ? this.getRainbowColor((int)(((Float)chams.rainbowspeed.getValue()).floatValue() * 1000.0f), ((Float)chams.rainbowsaturation.getValue()).floatValue(), ((Float)chams.rainbowbrightness.getValue()).floatValue()) : (Color)chams.color.getValue();
        if (((Boolean)chams.hand.getValue()).booleanValue() && Manager.moduleManager.isModuleEnabled(Chams.class) && Chams.mode.getValue() == Class88.ESP) {
            GL11.glPushMatrix();
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
        }
        modelRenderer.render(f);
        if (((Boolean)chams.hand.getValue()).booleanValue() && Manager.moduleManager.isModuleEnabled(Chams.class) && Chams.mode.getValue() == Class88.ESP) {
            GL11.glPopAttrib();
            GL11.glPolygonOffset((float)1.0f, (float)100000.0f);
            GL11.glDisable((int)32823);
            GL11.glPopMatrix();
        }
    }

    @Redirect(method={"renderRightArm"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/ModelRenderer;render(F)V", ordinal=1))
    private void renderRightArmWear(ModelRenderer modelRenderer, float f) {
        Color color;
        Chams chams = (Chams)Manager.moduleManager.getModuleByClass(Chams.class);
        Color color2 = color = (Boolean)chams.rainbow.getValue() != false ? this.getRainbowColor((int)(((Float)chams.rainbowspeed.getValue()).floatValue() * 1000.0f), ((Float)chams.rainbowsaturation.getValue()).floatValue(), ((Float)chams.rainbowbrightness.getValue()).floatValue()) : (Color)chams.color.getValue();
        if (((Boolean)chams.hand.getValue()).booleanValue() && Manager.moduleManager.isModuleEnabled(Chams.class) && Chams.mode.getValue() == Class88.ESP) {
            GL11.glPushMatrix();
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
        }
        modelRenderer.render(f);
        if (((Boolean)chams.hand.getValue()).booleanValue() && Manager.moduleManager.isModuleEnabled(Chams.class) && Chams.mode.getValue() == Class88.ESP) {
            GL11.glPopAttrib();
            GL11.glPolygonOffset((float)1.0f, (float)100000.0f);
            GL11.glDisable((int)32823);
            GL11.glPopMatrix();
        }
    }

    @Redirect(method={"renderLeftArm"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/ModelRenderer;render(F)V"))
    private void renderLeftArm(ModelRenderer modelRenderer, float f) {
        Color color;
        Chams chams = (Chams)Manager.moduleManager.getModuleByClass(Chams.class);
        Color color2 = color = (Boolean)chams.rainbow.getValue() != false ? this.getRainbowColor((int)(((Float)chams.rainbowspeed.getValue()).floatValue() * 1000.0f), ((Float)chams.rainbowsaturation.getValue()).floatValue(), ((Float)chams.rainbowbrightness.getValue()).floatValue()) : (Color)chams.color.getValue();
        if (((Boolean)chams.hand.getValue()).booleanValue() && Manager.moduleManager.isModuleEnabled(Chams.class) && Chams.mode.getValue() == Class88.ESP) {
            GL11.glPushMatrix();
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
        }
        modelRenderer.render(f);
        if (((Boolean)chams.hand.getValue()).booleanValue() && Manager.moduleManager.isModuleEnabled(Chams.class) && Chams.mode.getValue() == Class88.ESP) {
            GL11.glPopAttrib();
            GL11.glPolygonOffset((float)1.0f, (float)100000.0f);
            GL11.glDisable((int)32823);
            GL11.glPopMatrix();
        }
    }

    @Redirect(method={"renderLeftArm"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/ModelRenderer;render(F)V", ordinal=1))
    private void renderLeftArmWear(ModelRenderer modelRenderer, float f) {
        Color color;
        Chams chams = (Chams)Manager.moduleManager.getModuleByClass(Chams.class);
        Color color2 = color = (Boolean)chams.rainbow.getValue() != false ? this.getRainbowColor((int)(((Float)chams.rainbowspeed.getValue()).floatValue() * 1000.0f), ((Float)chams.rainbowsaturation.getValue()).floatValue(), ((Float)chams.rainbowbrightness.getValue()).floatValue()) : (Color)chams.color.getValue();
        if (((Boolean)chams.hand.getValue()).booleanValue() && Manager.moduleManager.isModuleEnabled(Chams.class) && Chams.mode.getValue() == Class88.ESP) {
            GL11.glPushMatrix();
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
        }
        modelRenderer.render(f);
        if (((Boolean)chams.hand.getValue()).booleanValue() && Manager.moduleManager.isModuleEnabled(Chams.class) && Chams.mode.getValue() == Class88.ESP) {
            GL11.glPopAttrib();
            GL11.glPolygonOffset((float)1.0f, (float)100000.0f);
            GL11.glDisable((int)32823);
            GL11.glPopMatrix();
        }
    }

    private Color getRainbowColor(int n, float f, float f2) {
        float f3 = System.currentTimeMillis() % (long)n;
        return Color.getHSBColor(f3 /= (float)n, f, f2);
    }

    @Redirect(method={"doRender"}, require=0, at=@At(value="INVOKE", target="Lnet/minecraft/client/entity/AbstractClientPlayer;isUser()Z"))
    private boolean overrideIsUser(AbstractClientPlayer abstractClientPlayer) {
        if (Class325.Method1305() && abstractClientPlayer == Globals.mc.player) {
            return false;
        }
        return abstractClientPlayer.isUser();
    }
}
