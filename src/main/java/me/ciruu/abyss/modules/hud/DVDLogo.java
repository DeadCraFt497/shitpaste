package me.ciruu.abyss.modules.hud;

import java.util.Random;
import java.util.function.Predicate;
import me.ciruu.abyss.Class234;
import me.ciruu.abyss.Class35;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class DVDLogo
extends Module {
    private final Setting scale = new Setting("Scale", "", (Module)this, (Object)Float.valueOf(0.25f), Float.valueOf(0.1f), Float.valueOf(1.0f));
    private final Setting speed = new Setting("Speed", "", (Module)this, (Object)Float.valueOf(0.5f), Float.valueOf(0.0f), Float.valueOf(10.0f));
    private final Setting onlyGUI = new Setting("OnlyGUI", "", this, true);
    private double Field574;
    private double Field575;
    private double Field576;
    private double Field577;
    private int Field578 = 800;
    private int Field579 = 408;
    private Random Field580 = new Random();
    int Field581 = 255;
    int Field582 = 0;
    int Field583 = 0;
    @EventHandler
    private Listener Field584 = new Listener<Class35>(this::Method724, new Predicate[0]);

    public DVDLogo() {
        super("DVDLogo", "", Class11.HUD, "");
        this.addSetting(this.scale);
        this.addSetting(this.speed);
        this.addSetting(this.onlyGUI);
    }

    private static void Method726() {
        GL11.glPushMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.clear((int)256);
        GlStateManager.enableBlend();
        GlStateManager.glTexEnvi((int)8960, (int)8704, (int)8448);
        GlStateManager.blendFunc((int)770, (int)771);
        GlStateManager.disableLighting();
    }

    private static void Method727() {
        GlStateManager.disableBlend();
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.popMatrix();
        GL11.glPopMatrix();
    }

    public void Method728() {
        super.getEnable();
        ScaledResolution scaledResolution = new ScaledResolution(Globals.mc);
        int n = (int)((float)this.Field578 * ((Float)this.scale.getValue()).floatValue());
        int n2 = (int)((float)this.Field579 * ((Float)this.scale.getValue()).floatValue());
        this.Field574 = this.Field580.nextInt(scaledResolution.getScaledWidth() - n);
        this.Field575 = this.Field580.nextInt(scaledResolution.getScaledHeight() - n2);
        this.Field576 = this.Field580.nextBoolean() ? -1.0 : 1.0;
        this.Field577 = this.Field580.nextBoolean() ? -1.0 : 1.0;
        this.Field576 *= (double)((Float)this.speed.getValue()).floatValue();
        this.Field577 *= (double)((Float)this.speed.getValue()).floatValue();
    }

    private void Method729() {
        this.Field581 = this.Field580.nextInt(255);
        this.Field583 = this.Field580.nextInt(255);
        this.Field582 = this.Field580.nextInt(255);
    }

    public void Method730(int n, int n2, float f, float f2, int n3, int n4, int n5, int n6, float f3, float f4) {
        int n7 = 255;
        float f5 = 1.0f / f3;
        float f6 = 1.0f / f4;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferBuilder.pos((double)n, (double)(n2 + n6), 0.0).tex((double)(f * f5), (double)((f2 + (float)n4) * f6)).color(this.Field581, this.Field583, this.Field582, n7).endVertex();
        bufferBuilder.pos((double)(n + n5), (double)(n2 + n6), 0.0).tex((double)((f + (float)n3) * f5), (double)((f2 + (float)n4) * f6)).color(this.Field581, this.Field583, this.Field582, n7).endVertex();
        bufferBuilder.pos((double)(n + n5), (double)n2, 0.0).tex((double)((f + (float)n3) * f5), (double)(f2 * f6)).color(this.Field581, this.Field583, this.Field582, n7).endVertex();
        bufferBuilder.pos((double)n, (double)n2, 0.0).tex((double)(f * f5), (double)(f2 * f6)).color(this.Field581, this.Field583, this.Field582, n7).endVertex();
        tessellator.draw();
    }

    private void Method724(Class35 class35) {
        if (((Boolean)this.onlyGUI.getValue()).booleanValue() && !(Globals.mc.currentScreen instanceof GuiScreen)) {
            return;
        }
        ScaledResolution scaledResolution = new ScaledResolution(Globals.mc);
        int n = (int)((float)this.Field578 * ((Float)this.scale.getValue()).floatValue());
        int n2 = (int)((float)this.Field579 * ((Float)this.scale.getValue()).floatValue());
        this.Field574 += this.Field576;
        this.Field575 += this.Field577;
        if (this.Field574 + (double)n >= (double)scaledResolution.getScaledWidth()) {
            this.Field574 -= this.Field576;
            this.Field576 *= -1.0;
            this.Method729();
        }
        if (this.Field575 + (double)n2 >= (double)scaledResolution.getScaledHeight()) {
            this.Field575 -= this.Field577;
            this.Field577 *= -1.0;
            this.Method729();
        }
        if (this.Field574 <= 0.0) {
            this.Field574 -= this.Field576;
            this.Field576 *= -1.0;
            this.Method729();
        }
        if (this.Field575 <= 0.0) {
            this.Field575 -= this.Field577;
            this.Field577 *= -1.0;
            this.Method729();
        }
        DVDLogo.Method726();
        Globals.mc.renderEngine.bindTexture(Class234.Field585);
        this.Method730((int)this.Field574, (int)this.Field575, 0.0f, 0.0f, 800, 408, n, n2, 800.0f, 408.0f);
        DVDLogo.Method727();
    }
}
