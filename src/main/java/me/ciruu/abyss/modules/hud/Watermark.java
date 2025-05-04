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

public class Watermark
extends Module {
    private final Setting main = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting version = new Setting("Version", "", this, true);
    private final Setting cleanVersion = new Setting("Clean version", "", this, false);
    private final Setting staticSetting = new Setting("Static", "", this, true);
    private final Setting x = new Setting("X", "X", this, 0, 0, 1000, this.main, this::Method54);
    private final Setting y = new Setting("Y", "Y", this, 0, 0, 1000, this.main, this::Method55);
    private final Setting scale = new Setting("Scale", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(5.0f));
    private final Setting colorLabel = new Setting("ColorLabel", "", this, new Class25("Color Settings"));
    public final Setting color = new Setting("Color", "", (Module)this, (Object)new Color(142, 0, 255, 255), this.colorLabel, Watermark::Method56);
    public final Setting rainbow = new Setting("Rainbow", "", (Module)this, (Object)false, this.colorLabel, Watermark::Method57);
    public final Setting speed = new Setting("Speed", "", this, Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(2.0f), this.colorLabel, this.rainbow::getValue);
    public final Setting saturation = new Setting("Saturation", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.colorLabel, this.rainbow::getValue);
    public final Setting brightness = new Setting("Brightness", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.colorLabel, this.rainbow::getValue);
    public final Setting gradient = new Setting("Gradient", "", (Module)this, (Object)false, this.colorLabel, this.rainbow::getValue);
    public final Setting delta = new Setting("Delta", "", this, Float.valueOf(-0.05f), Float.valueOf(-0.1f), Float.valueOf(0.1f), this.colorLabel, this::Method58);
    @EventHandler
    private Listener Field93 = new Listener<Class35>(this::Method59, new Predicate[0]);

    public Watermark() {
        super("Watermark", "", Class11.HUD, "");
        this.addSetting(this.version);
        this.addSetting(this.cleanVersion);
        this.addSetting(this.staticSetting);
        this.addSetting(this.x);
        this.addSetting(this.y);
        this.addSetting(this.scale);
        this.addSetting(this.colorLabel);
        this.addSetting(this.color);
        this.addSetting(this.rainbow);
        this.addSetting(this.speed);
        this.addSetting(this.saturation);
        this.addSetting(this.brightness);
        this.addSetting(this.gradient);
        this.addSetting(this.delta);
    }

    public int Method61(int n) {
        double d = Math.ceil((double)(System.currentTimeMillis() + (long)n) / (20.0 * (double)((Float)this.speed.getValue()).floatValue()));
        return Color.getHSBColor((float)((d %= 360.0) / 360.0), ((Float)this.saturation.getValue()).floatValue(), ((Float)this.brightness.getValue()).floatValue()).getRGB();
    }

    private void Method59(Class35 class35) {
        GlStateManager.pushMatrix();
        GlStateManager.scale((float)((Float)this.scale.getValue()).floatValue(), (float)((Float)this.scale.getValue()).floatValue(), (float)((Float)this.scale.getValue()).floatValue());
        if (((Boolean)this.gradient.getValue()).booleanValue() && ((Boolean)this.rainbow.getValue()).booleanValue()) {
            Class36.Method62("Abyss" + ((Boolean)this.version.getValue() != false ? " 1.6.0" : ""), (Boolean)this.staticSetting.getValue() != false ? 2.0f / ((Float)this.scale.getValue()).floatValue() : (float)((Integer)this.x.getValue()).intValue() / ((Float)this.scale.getValue()).floatValue(), (Boolean)this.staticSetting.getValue() != false ? 2.0f / ((Float)this.scale.getValue()).floatValue() : (float)((Integer)this.y.getValue()).intValue() / ((Float)this.scale.getValue()).floatValue(), ((Float)this.speed.getValue()).floatValue(), ((Float)this.saturation.getValue()).floatValue(), ((Float)this.brightness.getValue()).floatValue(), ((Float)this.delta.getValue()).floatValue(), true, 0.0f);
        } else if (((Boolean)this.version.getValue()).booleanValue() && !((Boolean)this.cleanVersion.getValue()).booleanValue()) {
            Class36.Method63("Abyss" + ((Boolean)this.version.getValue() != false ? " 1.6.0" : ""), (Boolean)this.staticSetting.getValue() != false ? 2.0f / ((Float)this.scale.getValue()).floatValue() : (float)((Integer)this.x.getValue()).intValue() / ((Float)this.scale.getValue()).floatValue(), (Boolean)this.staticSetting.getValue() != false ? 2.0f / ((Float)this.scale.getValue()).floatValue() : (float)((Integer)this.y.getValue()).intValue() / ((Float)this.scale.getValue()).floatValue(), (Boolean)this.rainbow.getValue() != false ? this.Method61(0) : ((Color)this.color.getValue()).getRGB());
        } else {
            Class36.Method63("Abyss" + ((Boolean)this.cleanVersion.getValue() != false ? "" + "1.6.0".substring(0, 3) : ""), (Boolean)this.staticSetting.getValue() != false ? 2.0f / ((Float)this.scale.getValue()).floatValue() : (float)((Integer)this.x.getValue()).intValue() / ((Float)this.scale.getValue()).floatValue(), (Boolean)this.staticSetting.getValue() != false ? 2.0f / ((Float)this.scale.getValue()).floatValue() : (float)((Integer)this.y.getValue()).intValue() / ((Float)this.scale.getValue()).floatValue(), (Boolean)this.rainbow.getValue() != false ? this.Method61(0) : ((Color)this.color.getValue()).getRGB());
        }
        GlStateManager.popMatrix();
    }

    private boolean Method58() {
        return (Boolean)this.rainbow.getValue() != false && (Boolean)this.gradient.getValue() != false;
    }

    private static boolean Method57() {
        return true;
    }

    private static boolean Method56() {
        return true;
    }

    private boolean Method55() {
        return (Boolean)this.staticSetting.getValue() == false;
    }

    private boolean Method54() {
        return (Boolean)this.staticSetting.getValue() == false;
    }
}
