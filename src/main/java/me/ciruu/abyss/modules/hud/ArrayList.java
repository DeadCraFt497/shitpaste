package me.ciruu.abyss.modules.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class35;
import me.ciruu.abyss.Class36;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.Class536;
import me.ciruu.abyss.Class556;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class331;
import me.ciruu.abyss.enums.Class540;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.modules.client.CustomFont;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;

public class ArrayList
extends Module {
    private final Setting main = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting scale = new Setting("Scale", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(5.0f));
    private final Setting step = new Setting("Step", "", (Module)this, (Object)9, 5, 15);
    private final Setting renderMode = new Setting("RenderMode", "", this, (Object)Class331.Up);
    private final Setting corner = new Setting("Corner", "", this, (Object)Class540.Right);
    private final Setting shadow = new Setting("Shadow", "", (Module)this, (Object)true, this.main, ArrayList::Method2619);
    private final Setting metadata = new Setting("Metadata", "", (Module)this, (Object)true, this.main, ArrayList::Method2620);
    public final Setting color = new Setting("Color", "", (Module)this, (Object)new Color(142, 0, 255, 255), this.main, ArrayList::Method2621);
    public final Setting rainbow = new Setting("Rainbow", "", (Module)this, (Object)false, this.main, ArrayList::Method2622);
    public final Setting speed = new Setting("Speed", "", this, Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(2.0f), this.main, this.rainbow::getValue);
    public final Setting saturation = new Setting("Saturation", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.main, this.rainbow::getValue);
    public final Setting brightness = new Setting("Brightness", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.main, this.rainbow::getValue);
    public final Setting gradient = new Setting("Gradient", "", (Module)this, (Object)false, this.main, this.rainbow::getValue);
    public final Setting delta = new Setting("Delta", "", this, Float.valueOf(-0.05f), Float.valueOf(-0.1f), Float.valueOf(0.1f), this.main, this::Method2623);
    private final Setting background = new Setting("Background", "", (Module)this, (Object)true, this.main, ArrayList::Method2624);
    private final Setting backgroundColor = new Setting("Background color", "", (Module)this, (Object)new Color(0, 0, 0, 100), this.main, this.background::getValue);
    private final Setting line = new Setting("Line", "", (Module)this, (Object)true, this.main, ArrayList::Method2625);
    private final Setting horizontalLine = new Setting("Horizontal Line", "", (Module)this, (Object)true, this.main, this.line::getValue);
    private final Setting animations = new Setting("Animations", "", this, new Class25("Animation Settings"));
    private final Setting animation = new Setting("Animation", "", (Module)this, (Object)true, this.animations, ArrayList::Method2626);
    private final Setting animationSpeed = new Setting("AnimationSpeed", "", this, Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(5.0f), this.animations, this.animation::getValue);
    private final List Field2154 = new java.util.ArrayList();
    private final List Field2155 = new java.util.ArrayList();
    @EventHandler
    private Listener Field2156 = new Listener<Class35>(this::Method2627, new Predicate[0]);
    public static Comparator Field2157 = new Class556();
    public static Class536 class536;
    public ArrayList() {
        super("ArrayList", "", Class11.HUD, "");
        this.addSetting(this.scale);
        this.addSetting(this.step);
        this.addSetting(this.renderMode);
        this.addSetting(this.corner);
        this.addSetting(this.shadow);
        this.addSetting(this.metadata);
        this.addSetting(this.color);
        this.addSetting(this.rainbow);
        this.addSetting(this.speed);
        this.addSetting(this.saturation);
        this.addSetting(this.brightness);
        this.addSetting(this.gradient);
        this.addSetting(this.delta);
        this.addSetting(this.background);
        this.addSetting(this.backgroundColor);
        this.addSetting(this.line);
        this.addSetting(this.horizontalLine);
        this.addSetting(this.animations);
        this.addSetting(this.animation);
        this.addSetting(this.animationSpeed);
    }

    private void Method2629() {
        this.Field2155.sort(Field2157);
    }

    private void Method2630(String string, float f, float f2, int n, Module module) {
        float f3 = 0x0b;
        int n2 = Class36.Method259(string);
        if (module.Method588() < (float)n2 && module.getBoolean18()) {
            module.Method589(this.Method2631(module.Method588(), n2 + 1, 0.01f + ((Float)this.animationSpeed.getValue()).floatValue() / 30.0f, 0.1f));
        } else if (module.Method588() > 1.5f && !module.getBoolean18()) {
            module.Method589(this.Method2631(module.Method588(), -1.5f, 0.01f + ((Float)this.animationSpeed.getValue()).floatValue() / 30.0f, 0.1f));
        } else if (module.Method588() <= 1.5f && !module.getBoolean18()) {
            module.Method589(-1.0f);
        }
        if (module.Method588() > (float)n2 && module.getBoolean18()) {
            module.Method589(n2);
        }
        float f4 = ((Boolean)this.animation.getValue()).booleanValue() ? (this.corner.getValue() == Class540.Left ? f - (float)n2 + module.Method588() : f + (float)n2 - module.Method588()) : (f3 = f);
        if (((Boolean)this.gradient.getValue()).booleanValue() && ((Boolean)this.rainbow.getValue()).booleanValue()) {
            Class36.Method566(string, f3, f2, this.Method2632(n * 300), 1.0f + ((Float)this.delta.getValue()).floatValue(), (Boolean)this.shadow.getValue(), 0.3f);
        } else if (((Boolean)this.shadow.getValue()).booleanValue()) {
            Class36.Method63(string, f3, f2, (Boolean)this.rainbow.getValue() != false ? this.Method2633(n * 300) : ((Color)this.color.getValue()).getRGB());
        } else {
            Class36.Method555(string, f3, f2, (Boolean)this.rainbow.getValue() != false ? this.Method2633(n * 300) : ((Color)this.color.getValue()).getRGB());
        }
    }

    private float Method2631(float f, float f2, float f3, float f4) {
        float f5 = (f2 - f) * f3;
        if (f5 > 0.0f) {
            f5 = Math.max(f4, f5);
            f5 = Math.min(f2 - f, f5);
        } else if (f5 < 0.0f) {
            f5 = Math.min(-f4, f5);
            f5 = Math.max(f2 - f, f5);
        }
        return f + f5;
    }

    private void Method2634(String string, float f, float f2, int n) {
        int n2 = (Integer)null;
        float f3;
        float f4 = f3 = this.corner.getValue() == Class540.Left ? f + (float)Class36.Method259(string) + 2.0f : f - (float)Class36.Method259(string) - 3.0f;
        int n3 = this.renderMode.getValue() == Class331.Up ? (((Boolean)this.rainbow.getValue()).booleanValue() ? this.Method2633(n * 300) : ((Color)this.color.getValue()).getRGB()) : (n2 = (Boolean)this.rainbow.getValue() != false ? this.Method2633((int)((float)(n * 300) + f2 / 2.0f)) : ((Color)this.color.getValue()).getRGB());
        int n4 = this.renderMode.getValue() == Class331.Up ? (((Boolean)this.rainbow.getValue()).booleanValue() ? this.Method2633((int)((float)(n * 300) + f2 / 2.0f)) : ((Color)this.color.getValue()).getRGB()) : ((Boolean)this.rainbow.getValue() != false ? this.Method2633(n * 300) : ((Color)this.color.getValue()).getRGB());
        Class50.Method817(f3, f2, f3, f2 + (float)((Integer)this.step.getValue()).intValue(), 1.5f, n2, n4);
    }

    public int Method2633(int n) {
        double d = Math.ceil((double)(System.currentTimeMillis() + (long)n) / (20.0 * (double)((Float)this.speed.getValue()).floatValue()));
        return Color.getHSBColor((float)((d %= 360.0) / 360.0), ((Float)this.saturation.getValue()).floatValue(), ((Float)this.brightness.getValue()).floatValue()).getRGB();
    }

    public Color Method2632(int n) {
        double d = Math.ceil((double)(System.currentTimeMillis() + (long)n) / (20.0 * (double)((Float)this.speed.getValue()).floatValue()));
        return Color.getHSBColor((float)((d %= 360.0) / 360.0), ((Float)this.saturation.getValue()).floatValue(), ((Float)this.brightness.getValue()).floatValue());
    }

    private void Method2627(Class35 class35) {
        GlStateManager.pushMatrix();
        GlStateManager.scale((float) ((Float) this.scale.getValue()).floatValue(), (float) ((Float) this.scale.getValue()).floatValue(), (float) ((Float) this.scale.getValue()).floatValue());
        this.Field2155.clear();
        this.Method2629();
        float f = this.corner.getValue() == Class540.Left ? 1.0f : (float) class35.Method2006();
        int n = 1;
        float f2 = this.renderMode.getValue() == Class331.Up ? 0.0f : ((float) class35.Method1967() - (float) ((Integer) this.step.getValue()).intValue() * ((Float) this.scale.getValue()).floatValue() - (float) (Globals.mc.currentScreen instanceof GuiChat ? 14 : 0)) / ((Float) this.scale.getValue()).floatValue();
        f /= ((Float) this.scale.getValue()).floatValue();
        if (((Boolean) this.background.getValue()).booleanValue()) {
            for (Object object : this.Field2155) {
                float f3 = this.corner.getValue() == Class540.Right ? f - (float) Class36.Method259(((Class536) object).getText()) - 3.0f : f - 1.0f;
                Class50.Method92(f3, f2, (float) (3 + Class36.Method259(((Class536) object).getText())) + f3, f2 + (float) ((Integer) this.step.getValue()).intValue(), ((Color) this.backgroundColor.getValue()).getRGB());
                f2 = this.renderMode.getValue() == Class331.Up ? f2 + (float) ((Integer) this.step.getValue()).intValue() : f2 - (float) ((Integer) this.step.getValue()).intValue();
            }
        }
        f2 = this.renderMode.getValue() == Class331.Up ? 0.0f : ((float) class35.Method1967() - (float) ((Integer) this.step.getValue()).intValue() * ((Float) this.scale.getValue()).floatValue() - (float) (Globals.mc.currentScreen instanceof GuiChat ? 14 : 0)) / ((Float) this.scale.getValue()).floatValue();
        int n2 = 0;
        for (n2 = (Integer) 0; ; ) {
            float f4 = this.corner.getValue() == Class540.Right ? f - (float) Class36.Method259(class536.getText()) - 1.0f : f + 1.0f;
            float f5 = (float) ((Integer) this.step.getValue()).intValue() / 2.0f - (float) Class36.Method260() / (Manager.moduleManager.isModuleEnabled(CustomFont.class) ? (((Boolean) ((CustomFont) Manager.moduleManager.getModuleByClass(CustomFont.class)).newfontrenderer.getValue()).booleanValue() ? 3.0f : 2.0f) : 2.5f);
            this.Method2630(class536.getText(), f4, f2 + f5, n, class536.getFeature());
            if (((Boolean) this.line.getValue()).booleanValue()) {
                this.Method2634(class536.getText(), f, f2, n);
            }
            if (((Boolean) this.horizontalLine.getValue()).booleanValue() && ((Boolean) this.line.getValue()).booleanValue()) {
                float f6;
                int n3 = (Integer) null;
                float f7;
                String string = "";
                boolean bl = false;
                try {
                    string = ((Class536) this.Field2155.get(n2 + 1)).getText();
                } catch (Exception exception) {
                    string = "";
                    bl = true;
                }
                float f8 = f7 = this.renderMode.getValue() == Class331.Up ? f2 + (float) ((Integer) this.step.getValue()).intValue() : f2;
                int n4 = this.renderMode.getValue() == Class331.Up ? (((Boolean) this.rainbow.getValue()).booleanValue() ? this.Method2633((int) ((float) (n * 300) + f2 / 2.0f)) : ((Color) this.color.getValue()).getRGB()) : (n3 = (Boolean) this.rainbow.getValue() != false ? this.Method2633((int) ((float) (n * 300) + f2)) : ((Color) this.color.getValue()).getRGB());
                int n5 = this.renderMode.getValue() == Class331.Up ? (((Boolean) this.rainbow.getValue()).booleanValue() ? this.Method2633((int) ((float) (n * 300) + f2)) : ((Color) this.color.getValue()).getRGB()) : ((Boolean) this.rainbow.getValue() != false ? this.Method2633((int) ((float) (n * 300) + f2 / 2.0f)) : ((Color) this.color.getValue()).getRGB());
                float f9 = f6 = this.corner.getValue() == Class540.Left ? f + (float) Class36.Method259(class536.getText()) + 2.0f : f - (float) Class36.Method259(class536.getText()) - 3.0f;
                float f10 = bl ? (this.corner.getValue() == Class540.Left ? 0.0f : f) : (this.corner.getValue() == Class540.Left ? (float) (3 + Class36.Method259(string)) : f - (float) Class36.Method259(string) - 3.0f);
                Class50.Method817(f6, f7, f10, f7, 1.5f, n3, n5);
            }
            ++n2;
            f2 = this.renderMode.getValue() == Class331.Up ? f2 + (float) ((Integer) this.step.getValue()).intValue() : f2 - (float) ((Integer) this.step.getValue()).intValue();
            ++n;
            GlStateManager.popMatrix();
        }
    }

    private void Method2636(Module module) {
        this.Field2155.add(new Class536(module, "" + ((Boolean)this.metadata.getValue() != false && module.Method587() != null ? ChatFormatting.GRAY + " [" + module.Method587() + ChatFormatting.GRAY + "]" : "")));
    }

    private boolean Method2635(Module module) {
        return !module.Method492() && (module.getBoolean18() || (Boolean)this.animation.getValue() != false && module.Method588() != 0.0f && module.Method588() != -1.0f);
    }

    private static boolean Method2626() {
        return true;
    }

    private static boolean Method2625() {
        return true;
    }

    private static boolean Method2624() {
        return true;
    }

    private boolean Method2623() {
        return (Boolean)this.rainbow.getValue() != false && (Boolean)this.gradient.getValue() != false;
    }

    private static boolean Method2622() {
        return true;
    }

    private static boolean Method2621() {
        return true;
    }

    private static boolean Method2620() {
        return true;
    }

    private static boolean Method2619() {
        return true;
    }
}
