package me.ciruu.abyss.modules.hud;

import java.awt.Color;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class36;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;

public class ChatWatermark
extends Module {
    private final Setting main = new Setting("Main", "", this, new Class25("Main Settings"));
    public final Setting color = new Setting("Color", "", (Module)this, (Object)new Color(142, 0, 255, 255), this.main, ChatWatermark::Method2971);
    public final Setting rainbow = new Setting("Rainbow", "", (Module)this, (Object)false, this.main, ChatWatermark::Method2972);
    public final Setting speed = new Setting("Speed", "", this, Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(2.0f), this.main, this.rainbow::getValue);
    public final Setting saturation = new Setting("Saturation", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.main, this.rainbow::getValue);
    public final Setting brightness = new Setting("Brightness", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.main, this.rainbow::getValue);
    public final Setting gradient = new Setting("Gradient", "", (Module)this, (Object)true, this.main, this.rainbow::getValue);
    public final Setting delta = new Setting("Delta", "", this, Float.valueOf(-0.02f), Float.valueOf(-0.1f), Float.valueOf(0.1f), this.main, this::Method2973);

    public ChatWatermark() {
        super("ChatWatermark", "", Class11.HUD, "");
        this.addSetting(this.color);
        this.addSetting(this.rainbow);
        this.addSetting(this.speed);
        this.addSetting(this.saturation);
        this.addSetting(this.brightness);
        this.addSetting(this.gradient);
        this.addSetting(this.delta);
    }

    public void Method2975(String string, float f, float f2) {
        if (((Boolean)this.gradient.getValue()).booleanValue() && ((Boolean)this.rainbow.getValue()).booleanValue()) {
            Class36.Method62(string, f, f2, ((Float)this.speed.getValue()).floatValue(), ((Float)this.saturation.getValue()).floatValue(), ((Float)this.brightness.getValue()).floatValue(), ((Float)this.delta.getValue()).floatValue(), true, 0.5f);
        } else {
            Class36.Method63(string, f, f2, (Boolean)this.rainbow.getValue() != false ? this.Method2976(0) : ((Color)this.color.getValue()).getRGB());
        }
    }

    private int Method2976(int n) {
        double d = Math.ceil((double)(System.currentTimeMillis() + (long)n) / (20.0 * (double)((Float)this.speed.getValue()).floatValue()));
        return Color.getHSBColor((float)((d %= 360.0) / 360.0), ((Float)this.saturation.getValue()).floatValue(), ((Float)this.brightness.getValue()).floatValue()).getRGB();
    }

    private boolean Method2973() {
        return (Boolean)this.rainbow.getValue() != false && (Boolean)this.gradient.getValue() != false;
    }

    private static boolean Method2972() {
        return true;
    }

    private static boolean Method2971() {
        return true;
    }
}
