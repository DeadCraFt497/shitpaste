package me.ciruu.abyss.modules.client;

import java.awt.Color;
import java.util.function.Predicate;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class26;
import me.ciruu.abyss.Class74;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class218;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

public class ClickGUI
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    public final Setting framecolor = new Setting("FrameColor", "", this, new Color(19, 18, 18, 151));
    public final Setting enabledcolor = new Setting("EnabledColor", "", this, new Color(106, 0, 193, 255));
    public final Setting fontcolor = new Setting("FontColor", "", this, new Color(255, 255, 255, 255));
    public final Setting linecolor = new Setting("LineColor", "", this, new Color(142, 0, 255, 255));
    public final Setting typingslidercolor = new Setting("TypingSliderColor", "", this, new Color(160, 0, 160, 255));
    private final Setting saveonclose = new Setting("SaveOnClose", "", this, true);
    public final Setting autowidth = new Setting("AutoWidth", "", this, false);
    public final Setting width = new Setting("Width", "", this, 90, 90, 170, this.mainwindow, this::getAutoWidth1);
    private final Setting rainbow = new Setting("rainbow", "", this, new Class25("Rainbow Settings"));
    public final Setting rline = new Setting("RLine", "", this, false, this.rainbow, () -> true);
    private final Setting rspeedline = new Setting("RSpeedLine", "", this, Float.valueOf(7.0f), Float.valueOf(0.001f), Float.valueOf(10.0f), this.rainbow, this.rline::getValue);
    private final Setting rsatline = new Setting("RSatLine", "", this, Float.valueOf(0.5f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.rainbow, this.rline::getValue);
    private final Setting rbrightline = new Setting("RBrightLine", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.rainbow, this.rline::getValue);
    public final Setting gradientline = new Setting("GradientLine", "", (Module)this, (Object)false, this.rainbow, this.rline::getValue);
    private final Setting lineoffset = new Setting("LineOffset", "", this, Float.valueOf(3.0f), Float.valueOf(0.0f), Float.valueOf(7.0f), this.rainbow, this::getRLineBool);
    public final Setting renabled = new Setting("REnabled", "", (Module)this, (Object)false, this.rainbow, ClickGUI::getReturnType1);
    public final Setting rspeedenabled = new Setting("RSpeedEnabled", "", this, Float.valueOf(7.0f), Float.valueOf(0.001f), Float.valueOf(10.0f), this.rainbow, this.renabled::getValue);
    public final Setting rsatenabled = new Setting("RSatEnabled", "", this, Float.valueOf(0.5f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.rainbow, this.renabled::getValue);
    public final Setting rbrightenabled = new Setting("RBrightEnabled", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.rainbow, this.renabled::getValue);
    public final Setting gradientenabled = new Setting("GradientEnabled", "", (Module)this, (Object)false, this.rainbow, this.rline::getValue);
    public final Setting enabledoffset = new Setting("EnabledOffset", "", this, Float.valueOf(10.0f), Float.valueOf(2.0f), Float.valueOf(20.0f), this.rainbow, this::Method676);
    public final Setting ignoredisabled = new Setting("IgnoreDisabled", "", (Module)this, (Object)false, this.rainbow, this::getGradientBoolean1);
    public final Setting gradientmode = new Setting("GradientMode", "", (Module)this, (Object)Class218.Button, this.rainbow, this::getGradientBoolean);
    private final Setting backgroundwindow = new Setting("background", "", this, new Class25("Background Settings"));
    public final Setting backgroundenabled = new Setting("Background", "", (Module)this, (Object)false, this.backgroundwindow, ClickGUI::getBooleanReturnTrue);
    public final Setting backgroundgradient = new Setting("Gradient", "", (Module)this, (Object)true, this.backgroundwindow, this.backgroundenabled::getValue);
    public final Setting backgroundcolor = new Setting("BackgroundColor", "", (Module)this, (Object)new Color(0, 0, 0, 180), this.backgroundwindow, this::getBackgroundOther2);
    public final Setting backgroundcolortop = new Setting("BgColorTop", "", (Module)this, (Object)new Color(0, 0, 0, 0), this.backgroundwindow, this::getBackgroundOther);
    public final Setting backgroundcolorbottom = new Setting("BgColorBottom", "", (Module)this, (Object)new Color(142, 0, 255, 180), this.backgroundwindow, this::getBackground);
    @EventHandler
    private Listener listen = new Listener<Class26>(this::getGUIActual, new Predicate[0]);

    public ClickGUI() {
        super("ClickGUI", "", Class11.CLIENT, "");
        this.Method684(Keyboard.getKeyName((int)25));
        this.addSetting(this.framecolor);
        this.addSetting(this.enabledcolor);
        this.addSetting(this.fontcolor);
        this.addSetting(this.linecolor);
        this.addSetting(this.typingslidercolor);
        this.addSetting(this.saveonclose);
        this.addSetting(this.autowidth);
        this.addSetting(this.width);
        this.addSetting(this.rainbow);
        this.addSetting(this.rline);
        this.addSetting(this.rspeedline);
        this.addSetting(this.rsatline);
        this.addSetting(this.rbrightline);
        this.addSetting(this.gradientline);
        this.addSetting(this.lineoffset);
        this.addSetting(this.renabled);
        this.addSetting(this.rspeedenabled);
        this.addSetting(this.rsatenabled);
        this.addSetting(this.rbrightenabled);
        this.addSetting(this.gradientenabled);
        this.addSetting(this.enabledoffset);
        this.addSetting(this.ignoredisabled);
        this.addSetting(this.gradientmode);
        this.addSetting(this.backgroundwindow);
        this.addSetting(this.backgroundenabled);
        this.addSetting(this.backgroundgradient);
        this.addSetting(this.backgroundcolor);
        this.addSetting(this.backgroundcolortop);
        this.addSetting(this.backgroundcolorbottom);
    }

    public void getBooleanSaveOnClose(boolean bl) {
        this.getSaveOnClose();
    }

    public void getScreen() {
        super.getEnable();
        Globals.mc.displayGuiScreen((GuiScreen)Manager.GUI);
    }

    public void getSaveOnClose() {
        super.getDisable();
        if (((Boolean)this.saveonclose.getValue()).booleanValue() && Globals.mc.world != null) {
            Module.getSaveSettings();
        }
    }

    private Color getColor(int n, float f, float f2) {
        float f3 = System.currentTimeMillis() % (long)n;
        return Color.getHSBColor(f3 /= (float)n, f, f2);
    }

    private Color getHSBColorRandom(int n, float f, float f2, int n2) {
        float f3 = System.currentTimeMillis() % (long)n + (long)n2;
        return Color.getHSBColor(f3 /= (float)n, f, f2);
    }

    private void getGUIActual(Class26 class26) {
        Class74.Field172.getColor((Color)this.framecolor.getValue());
        Class74.Field172.Method693((Color)this.fontcolor.getValue());
        Class74.Field172.Method694((Color)this.typingslidercolor.getValue());
        if (!((Boolean)this.renabled.getValue()).booleanValue()) {
            Class74.Field172.Method695((Color)this.enabledcolor.getValue());
        } else {
            Class74.Field172.Method695(this.getColor((int)(((Float)this.rspeedenabled.getValue()).floatValue() * 1000.0f), ((Float)this.rsatenabled.getValue()).floatValue(), ((Float)this.rbrightenabled.getValue()).floatValue()));
            if (((Boolean)this.gradientenabled.getValue()).booleanValue() && this.gradientmode.getValue() == Class218.Button) {
                Class74.Field172.Method696(this.getHSBColorRandom((int)(((Float)this.rspeedenabled.getValue()).floatValue() * 1000.0f), ((Float)this.rsatenabled.getValue()).floatValue(), ((Float)this.rbrightenabled.getValue()).floatValue(), (int)(((Float)this.enabledoffset.getValue()).floatValue() * 1000.0f)));
            }
        }
        if (!((Boolean)this.rline.getValue()).booleanValue()) {
            Class74.Field172.Method697((Color)this.linecolor.getValue());
        } else {
            Class74.Field172.Method697(this.getColor((int)(((Float)this.rspeedline.getValue()).floatValue() * 1000.0f), ((Float)this.rsatline.getValue()).floatValue(), ((Float)this.rbrightline.getValue()).floatValue()));
            if (((Boolean)this.gradientline.getValue()).booleanValue()) {
                Class74.Field172.Method698(this.getHSBColorRandom((int)(((Float)this.rspeedline.getValue()).floatValue() * 1000.0f), ((Float)this.rsatline.getValue()).floatValue(), ((Float)this.rbrightline.getValue()).floatValue(), (int)(((Float)this.lineoffset.getValue()).floatValue() * 1000.0f)));
            }
        }
    }

    private boolean getBackground() {
        return (Boolean)this.backgroundenabled.getValue() != false && (Boolean)this.backgroundgradient.getValue() != false;
    }

    private boolean getBackgroundOther() {
        return (Boolean)this.backgroundenabled.getValue() != false && (Boolean)this.backgroundgradient.getValue() != false;
    }

    private boolean getBackgroundOther2() {
        return (Boolean)this.backgroundenabled.getValue() != false && (Boolean)this.backgroundgradient.getValue() == false;
    }

    private static boolean getBooleanReturnTrue() {
        return true;
    }

    private boolean getGradientBoolean() {
        return (Boolean)this.renabled.getValue() != false && (Boolean)this.gradientenabled.getValue() != false;
    }

    private boolean getGradientBoolean1() {
        return (Boolean)this.renabled.getValue() != false && (Boolean)this.gradientenabled.getValue() != false;
    }

    private boolean Method676() {
        return (Boolean)this.renabled.getValue() != false && (Boolean)this.gradientenabled.getValue() != false;
    }

    private static boolean getReturnType1() {
        return true;
    }

    private boolean getRLineBool() {
        return (Boolean)this.rline.getValue() != false && (Boolean)this.gradientline.getValue() != false;
    }

    private static boolean getReturnType2() {
        return true;
    }

    private boolean getAutoWidth1() {
        return (Boolean)this.autowidth.getValue() == false;
    }
}
