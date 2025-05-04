package me.ciruu.abyss.modules.client;

import java.awt.Color;
import java.util.function.Predicate;
import me.ciruu.abyss.Class219;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class26;
import me.ciruu.abyss.Class35;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.GuiChat;

public class Particles
extends Module {
    private final Setting chat = new Setting("Chat", "", this, true);
    private final Setting gui = new Setting("GUI", "", this, true);
    private final Setting radius = new Setting("Radius", "", (Module)this, (Object)Float.valueOf(50.0f), Float.valueOf(10.0f), Float.valueOf(500.0f));
    private final Setting speed = new Setting("Speed", "", (Module)this, (Object)Float.valueOf(0.5f), Float.valueOf(0.0f), Float.valueOf(5.0f));
    private final Setting delta = new Setting("Delta", "", (Module)this, (Object)1, 0, 5);
    private final Setting amount = new Setting("Amount", "", (Module)this, (Object)100, 1, 300);
    private final Setting linewidth = new Setting("LineWidth", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(5.0f));
    private final Setting colorlabel = new Setting("ColorLabel", "", this, new Class25("Color Settings"));
    private final Setting color = new Setting("Color", "", (Module)this, (Object)new Color(142, 0, 255, 255), this.colorlabel, Particles::getBoolean2);
    private final Setting rainbow = new Setting("Rainbow", "", (Module)this, (Object)false, this.colorlabel, Particles::getBoolean1);
    private final Setting rspeed = new Setting("RSpeed", "", this, Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(2.0f), this.colorlabel, this.rainbow::getValue);
    private final Setting saturation = new Setting("Saturation", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.colorlabel, this.rainbow::getValue);
    private final Setting brightness = new Setting("Brightness", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.colorlabel, this.rainbow::getValue);
    private final Setting gradient = new Setting("Gradient", "", (Module)this, (Object)true, this.colorlabel, this.rainbow::getValue);
    private final Setting offset = new Setting("Offset", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(4.0f), this.colorlabel, this::getGradient);
    @EventHandler
    private Listener listen1 = new Listener<Class26>(this::getCurrentScreen2, new Predicate[0]);
    @EventHandler
    private Listener listen2 = new Listener<Class35>(this::getCurrentScreenChat1, new Predicate[0]);

    public Particles() {
        super("Particles", "", Class11.CLIENT, "");
        this.addSetting(this.chat);
        this.addSetting(this.gui);
        this.addSetting(this.radius);
        this.addSetting(this.speed);
        this.addSetting(this.delta);
        this.addSetting(this.amount);
        this.addSetting(this.linewidth);
        this.addSetting(this.colorlabel);
        this.addSetting(this.color);
        this.addSetting(this.rainbow);
        this.addSetting(this.rspeed);
        this.addSetting(this.saturation);
        this.addSetting(this.brightness);
        this.addSetting(this.gradient);
        this.addSetting(this.offset);
    }

    public void getAmount() {
        super.getEnable();
        Manager.Field1648.Method2201((Integer)this.amount.getValue());
    }

    public Color getColorRainbow() {
        return (Boolean)this.rainbow.getValue() != false ? this.getRainbowHSBColor(0) : (Color)this.color.getValue();
    }

    public Color getRainbowHSBColor(int n) {
        double d = Math.ceil((double)(System.currentTimeMillis() + (long)n) / (20.0 * (double)((Float)this.rspeed.getValue()).floatValue()));
        return Color.getHSBColor((float)((d %= 360.0) / 360.0), ((Float)this.saturation.getValue()).floatValue(), ((Float)this.brightness.getValue()).floatValue());
    }

    private void getCurrentScreenChat1(Class35 class35) {
        if (((Boolean)this.chat.getValue()).booleanValue() && Globals.mc.currentScreen instanceof GuiChat || ((Boolean)this.gui.getValue()).booleanValue() && Globals.mc.currentScreen instanceof Class219) {
            Manager.Field1648.Method2195(this.getColorRainbow(), ((Float)this.radius.getValue()).floatValue(), (Boolean)this.rainbow.getValue() != false && (Boolean)this.gradient.getValue() != false, ((Float)this.offset.getValue()).floatValue(), ((Float)this.linewidth.getValue()).floatValue(), class35);
        }
    }

    private void getCurrentScreen2(Class26 class26) {
        if (((Boolean)this.chat.getValue()).booleanValue() && Globals.mc.currentScreen instanceof GuiChat || ((Boolean)this.gui.getValue()).booleanValue() && Globals.mc.currentScreen instanceof Class219) {
            Manager.Field1648.Method2198((Integer)this.delta.getValue(), ((Float)this.speed.getValue()).floatValue());
        }
    }

    private boolean getGradient() {
        return (Boolean)this.rainbow.getValue() != false && (Boolean)this.gradient.getValue() != false;
    }

    private static boolean getBoolean1() {
        return true;
    }

    private static boolean getBoolean2() {
        return true;
    }
}
