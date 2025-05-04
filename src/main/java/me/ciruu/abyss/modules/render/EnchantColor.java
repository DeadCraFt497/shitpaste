package me.ciruu.abyss.modules.render;

import java.awt.Color;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;

public class EnchantColor
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting color = new Setting("Color", "", (Module)this, (Object)new Color(0, 255, 255, 255), this.mainwindow, EnchantColor::Method2375);
    private final Setting rainbow = new Setting("Rainbow", "", (Module)this, (Object)true, this.mainwindow, EnchantColor::Method2376);
    private final Setting rainbowspeed = new Setting("RainbowSpeed", "", this, Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(2.0f), this.mainwindow, this.rainbow::getValue);
    private final Setting rainbowsaturation = new Setting("RainbowSaturation", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.mainwindow, this.rainbow::getValue);
    private final Setting rainbowbrightness = new Setting("RainbowBrightness", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.mainwindow, this.rainbow::getValue);

    public EnchantColor() {
        super("EnchantColor", "Change the color of the enchantment glint.", Class11.RENDER, "");
        this.addSetting(this.color);
        this.addSetting(this.rainbow);
        this.addSetting(this.rainbowspeed);
        this.addSetting(this.rainbowsaturation);
        this.addSetting(this.rainbowbrightness);
    }

    public Color Method1573() {
        return (Boolean)this.rainbow.getValue() != false ? this.Method2378(0) : (Color)this.color.getValue();
    }

    private Color Method2378(int n) {
        double d = Math.ceil((double)(System.currentTimeMillis() + (long)n) / (20.0 * (double)((Float)this.rainbowspeed.getValue()).floatValue()));
        return Color.getHSBColor((float)((d %= 360.0) / 360.0), ((Float)this.rainbowsaturation.getValue()).floatValue(), ((Float)this.rainbowbrightness.getValue()).floatValue());
    }

    private static boolean Method2376() {
        return true;
    }

    private static boolean Method2375() {
        return true;
    }
}
