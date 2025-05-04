package me.ciruu.abyss.modules.hud;

import java.awt.Color;
import java.util.function.Predicate;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class35;
import me.ciruu.abyss.Class36;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.renderer.GlStateManager;

public class WatermarkCool
extends Module {
    private final Setting main = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting staticSetting = new Setting("Static", "", this, true);
    private final Setting x = new Setting("X", "X", this, 0, 0, 1000, this.main, this::Method3832);
    private final Setting y = new Setting("Y", "Y", this, 0, 0, 1000, this.main, this::Method3833);
    private final Setting scale = new Setting("Scale", "", (Module)this, (Object)Float.valueOf(2.0f), Float.valueOf(0.0f), Float.valueOf(5.0f));
    private final Setting colorLabel = new Setting("ColorLabel", "", this, new Class25("Color Settings"));
    public final Setting color = new Setting("Color", "", (Module)this, (Object)new Color(142, 0, 255, 255), this.colorLabel, WatermarkCool::Method3834);
    public final Setting rainbow = new Setting("Rainbow", "", (Module)this, (Object)false, this.colorLabel, WatermarkCool::Method3835);
    public final Setting speed = new Setting("Speed", "", this, Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(2.0f), this.colorLabel, this.rainbow::getValue);
    public final Setting saturation = new Setting("Saturation", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.colorLabel, this.rainbow::getValue);
    public final Setting brightness = new Setting("Brightness", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.colorLabel, this.rainbow::getValue);
    public final Setting gradient = new Setting("Gradient", "", (Module)this, (Object)false, this.colorLabel, this.rainbow::getValue);
    public final Setting delta = new Setting("Delta", "", this, Float.valueOf(-0.05f), Float.valueOf(-0.1f), Float.valueOf(0.1f), this.colorLabel, this::Method3836);
    @EventHandler
    private Listener Field3165 = new Listener<Class35>(this::Method3837, new Predicate[0]);

    public WatermarkCool() {
        super("WatermarkCool", "", Class11.HUD, "");
        this.addSetting(this.staticSetting);
        this.addSetting(this.x);
        this.addSetting(this.y);
        this.addSetting(this.colorLabel);
        this.addSetting(this.color);
        this.addSetting(this.rainbow);
        this.addSetting(this.speed);
        this.addSetting(this.saturation);
        this.addSetting(this.brightness);
        this.addSetting(this.gradient);
        this.addSetting(this.delta);
    }

    public int Method3839(int n) {
        double d = Math.ceil((double)(System.currentTimeMillis() + (long)n) / (20.0 * (double)((Float)this.speed.getValue()).floatValue()));
        return Color.getHSBColor((float)((d %= 360.0) / 360.0), ((Float)this.saturation.getValue()).floatValue(), ((Float)this.brightness.getValue()).floatValue()).getRGB();
    }

    public static void Method3840(String string, float f, float f2, float f3, float f4, float f5, float f6, boolean bl, boolean bl2) {
        char[] cArray = string.toCharArray();
        int n = 1;
        float f7 = 0.0f;
        for (char c : cArray) {
            if (bl) {
                Class36.Field466.drawStringWithShadow(String.valueOf(c), f + 0.0f + f7, f2, WatermarkCool.Method3841(f3, f4, f5, (float)n * f6));
            } else {
                Class36.Field466.drawString(String.valueOf(c), f + 0.0f + f7, f2, WatermarkCool.Method3841(f3, f4, f5, (float)n * f6), false);
            }
            f7 += (float)Class36.Field466.getStringWidth(String.valueOf(c)) + 0.5f;
            ++n;
        }
    }

    private static int Method3841(float f, float f2, float f3, float f4) {
        double d = Math.ceil((double)System.currentTimeMillis() / (20.0 * (double)f));
        return Color.getHSBColor((float)((d %= 360.0) / 360.0) + f4, f2, f3).getRGB();
    }

    private void Method3837(Class35 class35) {
        GlStateManager.pushMatrix();
        GlStateManager.scale((float)((Float)this.scale.getValue()).floatValue(), (float)((Float)this.scale.getValue()).floatValue(), (float)((Float)this.scale.getValue()).floatValue());
        int n = class35.Method2006();
        if (((Boolean)this.gradient.getValue()).booleanValue() && ((Boolean)this.rainbow.getValue()).booleanValue()) {
            WatermarkCool.Method3840("Abyss", (Boolean)this.staticSetting.getValue() != false ? ((float)n / 2.0f - (float)Class36.Field466.getStringWidth("Abyss")) / ((Float)this.scale.getValue()).floatValue() : (float)((Integer)this.x.getValue()).intValue() / ((Float)this.scale.getValue()).floatValue(), (Boolean)this.staticSetting.getValue() != false ? 6.0f / ((Float)this.scale.getValue()).floatValue() : (float)((Integer)this.y.getValue()).intValue() / ((Float)this.scale.getValue()).floatValue(), ((Float)this.speed.getValue()).floatValue(), ((Float)this.saturation.getValue()).floatValue(), ((Float)this.brightness.getValue()).floatValue(), ((Float)this.delta.getValue()).floatValue(), true, true);
        } else {
            Class36.Field466.drawStringWithShadow("Abyss", (Boolean)this.staticSetting.getValue() != false ? ((float)n / 2.0f - (float)Class36.Field466.getStringWidth("Abyss") / 2.0f) / ((Float)this.scale.getValue()).floatValue() : (float)((Integer)this.x.getValue()).intValue() / ((Float)this.scale.getValue()).floatValue(), (Boolean)this.staticSetting.getValue() != false ? 6.0f / ((Float)this.scale.getValue()).floatValue() : (float)((Integer)this.y.getValue()).intValue() / ((Float)this.scale.getValue()).floatValue(), (Boolean)this.rainbow.getValue() != false ? this.Method3839(0) : ((Color)this.color.getValue()).getRGB());
        }
        GlStateManager.popMatrix();
    }

    private boolean Method3836() {
        return (Boolean)this.rainbow.getValue() != false && (Boolean)this.gradient.getValue() != false;
    }

    private static boolean Method3835() {
        return true;
    }

    private static boolean Method3834() {
        return true;
    }

    private boolean Method3833() {
        return (Boolean)this.staticSetting.getValue() == false;
    }

    private boolean Method3832() {
        return (Boolean)this.staticSetting.getValue() == false;
    }
}
