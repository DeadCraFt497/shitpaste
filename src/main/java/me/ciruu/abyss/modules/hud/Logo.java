package me.ciruu.abyss.modules.hud;

import java.awt.Color;
import java.util.function.Predicate;
import me.ciruu.abyss.Class234;
import me.ciruu.abyss.Class35;
import me.ciruu.abyss.Class36;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class Logo
extends Module {
    private final Setting x = new Setting("X", "X", (Module)this, (Object)0, 0, 1000);
    private final Setting y = new Setting("Y", "Y", (Module)this, (Object)0, 0, 1000);
    private final Setting width = new Setting("Width", "Width", (Module)this, (Object)60, 0, 1000);
    private final Setting height = new Setting("Height", "Height", (Module)this, (Object)60, 0, 1000);
    private final Setting offset = new Setting("Offset", "", (Module)this, (Object)0, 0, 1000);
    private final Setting renderLogo = new Setting("RenderLogo", "", this, true);
    private final Setting rainbow = new Setting("Rainbow", "", this, false);
    private final Setting speed = new Setting("Speed", "", (Module)this, (Object)Float.valueOf(7.0f), Float.valueOf(0.0f), Float.valueOf(10.0f));
    private final Setting saturation = new Setting("Saturation", "", (Module)this, (Object)Float.valueOf(0.5f), Float.valueOf(0.0f), Float.valueOf(1.0f));
    private final Setting brightness = new Setting("Brightness", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f));
    private final Setting delta = new Setting("Delta", "", (Module)this, (Object)200, 0, 1000);
    private final Setting text = new Setting("Text", "", this, false);
    private final Setting version = new Setting("Version", "", this, true);
    private final Setting shadow = new Setting("Shadow", "", this, false);
    private final Setting color = new Setting("Color", "", this, new Color(255, 255, 255, 255));
    private final Setting rainbowText = new Setting("Rainbow text", "", this, false);
    private String Field2980 = "";
    @EventHandler
    private Listener Field2981 = new Listener<Class35>(this::addSetting58, -100, new Predicate[0]);

    public Logo() {
        super("Logo", "", Class11.HUD, "");
        this.addSetting(this.x);
        this.addSetting(this.y);
        this.addSetting(this.width);
        this.addSetting(this.height);
        this.addSetting(this.offset);
        this.addSetting(this.renderLogo);
        this.addSetting(this.rainbow);
        this.addSetting(this.speed);
        this.addSetting(this.saturation);
        this.addSetting(this.brightness);
        this.addSetting(this.delta);
        this.addSetting(this.text);
        this.addSetting(this.version);
        this.addSetting(this.shadow);
        this.addSetting(this.color);
        this.addSetting(this.rainbowText);
    }

    public void addSetting60() {
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        if (!((Boolean)this.rainbow.getValue()).booleanValue()) {
            Globals.mc.getTextureManager().bindTexture(Class234.Field1807);
        } else {
            Globals.mc.getTextureManager().bindTexture(Class234.Field1808);
        }
        GlStateManager.enableBlend();
        if (((Boolean)this.renderLogo.getValue()).booleanValue()) {
            this.addSetting61((Integer)this.x.getValue(), (Integer)this.y.getValue(), (Integer)this.width.getValue(), (Integer)this.height.getValue());
        }
        if (((Boolean)this.rainbow.getValue()).booleanValue()) {
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)-1.0f);
        }
        if (((Boolean)this.text.getValue()).booleanValue()) {
            this.Field2980 = (Boolean)this.version.getValue() != false ? "Abyss 1.6.0" : "Abyss";
            if (((Boolean)this.shadow.getValue()).booleanValue()) {
                Class36.Method557(this.Field2980, (float)((Integer)this.x.getValue()).intValue() + ((Boolean)this.renderLogo.getValue() != false ? (float)((Integer)this.width.getValue()).intValue() / 2.0f : 0.0f), (Integer)this.y.getValue() + ((Boolean)this.renderLogo.getValue() != false ? (Integer)this.height.getValue() : 0), (Boolean)this.rainbowText.getValue() != false ? this.addSetting62((int)(((Float)this.speed.getValue()).floatValue() * 1000.0f), ((Float)this.saturation.getValue()).floatValue(), ((Float)this.brightness.getValue()).floatValue(), 0.0f).getRGB() : ((Color)this.color.getValue()).getRGB());
            } else {
                Class36.Method559(this.Field2980, (float)((Integer)this.x.getValue()).intValue() + ((Boolean)this.renderLogo.getValue() != false ? (float)((Integer)this.width.getValue()).intValue() / 2.0f : 0.0f), (Integer)this.y.getValue() + ((Boolean)this.renderLogo.getValue() != false ? (Integer)this.height.getValue() : 0), (Boolean)this.rainbowText.getValue() != false ? this.addSetting62((int)(((Float)this.speed.getValue()).floatValue() * 1000.0f), ((Float)this.saturation.getValue()).floatValue(), ((Float)this.brightness.getValue()).floatValue(), 0.0f).getRGB() : ((Color)this.color.getValue()).getRGB());
            }
        }
    }

    public void addSetting61(int n, int n2, int n3, int n4) {
        GL11.glPushAttrib((int)2900);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)n, (float)n2, (float)0.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glShadeModel((int)7425);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        if (((Boolean)this.rainbow.getValue()).booleanValue()) {
            Color color = this.addSetting63(0.0f);
            Color color2 = this.addSetting63(1.0f);
            Color color3 = new Color((color.getRed() + color2.getRed()) / 2, (color.getGreen() + color2.getGreen()) / 2, (color.getBlue() + color2.getBlue()) / 2);
            bufferBuilder.pos(0.0, 0.0, 0.0).tex(0.0, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
            bufferBuilder.pos(0.0, (double)n4, 0.0).tex(0.0, 1.0).color(color3.getRed(), color3.getGreen(), color3.getBlue(), 255).endVertex();
            bufferBuilder.pos((double)n3, (double)n4, 0.0).tex(1.0, 1.0).color(color2.getRed(), color2.getGreen(), color2.getBlue(), 255).endVertex();
            bufferBuilder.pos((double)n3, 0.0, 0.0).tex(1.0, 0.0).color(color3.getRed(), color3.getGreen(), color3.getBlue(), 255).endVertex();
        } else {
            bufferBuilder.pos(0.0, 0.0, 0.0).tex(0.0, 0.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            bufferBuilder.pos(0.0, (double)n4, 0.0).tex(0.0, 1.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            bufferBuilder.pos((double)n3, (double)n4, 0.0).tex(1.0, 1.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            bufferBuilder.pos((double)n3, 0.0, 0.0).tex(1.0, 0.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        }
        Tessellator.getInstance().draw();
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }

    private Color addSetting63(float f) {
        return this.addSetting62((int)(((Float)this.speed.getValue()).floatValue() * 1000.0f), ((Float)this.saturation.getValue()).floatValue(), ((Float)this.brightness.getValue()).floatValue(), (float)((Integer)this.offset.getValue()).intValue() / 1000.0f + f * (float)((Integer)this.delta.getValue()).intValue() / 1000.0f);
    }

    private Color addSetting62(int n, float f, float f2, float f3) {
        float f4 = (float)(Minecraft.getSystemTime() % 0xFFFFFFL) / (float)n;
        return Color.getHSBColor(f4 + f3, f, f2);
    }

    private void addSetting58(Class35 class35) {
        GL11.glPushMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.addSetting60();
        GL11.glPopMatrix();
    }
}
