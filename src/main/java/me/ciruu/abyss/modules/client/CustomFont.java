package me.ciruu.abyss.modules.client;

import java.awt.Font;
import java.util.function.Predicate;
import me.ciruu.abyss.Class139;
import me.ciruu.abyss.Class197;
import me.ciruu.abyss.Class198;
import me.ciruu.abyss.Class210;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class36;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class363;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

public class CustomFont
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    public final Setting newfontrenderer = new Setting("NewFontRenderer", "", (Module)this, (Object)true, this.mainwindow, CustomFont::getBoolean2);
    private final Setting size = new Setting("Size", "", this, Float.valueOf(40.0f), Float.valueOf(5.0f), Float.valueOf(60.0f), this.mainwindow, this.newfontrenderer::getValue);
    private final Setting custom = new Setting("CustomServer", "", (Module)this, (Object)false, this.mainwindow, this.newfontrenderer::getValue);
    private final Setting font = new Setting("Font", "", (Module)this, (Object)"Arial", this.mainwindow, this::getCustome2);
    private final Setting type = new Setting("Type", "", (Module)this, (Object)Class363.PLAIN, this.mainwindow, this::getCustome1);
    private final Setting fontsize = new Setting("FontSize", "", this, Float.valueOf(17.0f), Float.valueOf(5.0f), Float.valueOf(25.0f), this.mainwindow, this::getNewFontFalse3);
    private final Setting antialias = new Setting("AntiAlias", "", (Module)this, (Object)true, this.mainwindow, this::getNewFontFalse2);
    private final Setting fractionalmetrics = new Setting("FractionalMetrics", "", (Module)this, (Object)true, this.mainwindow, this::getNewFontFalse1);
    public final Setting override = new Setting("Override", "", (Module)this, (Object)false, this.mainwindow, CustomFont::getBoolean1);
    @EventHandler
    private Listener listen1 = new Listener<Class139>(this::getFontWitClrRLog, new Predicate[0]);
    @EventHandler
    private Listener listen2 = new Listener<Class210>(this::getFontWitValue, new Predicate[0]);

    public CustomFont() {
        super("CustomFont", "", Class11.CLIENT, "");
        this.addSetting(this.newfontrenderer);
        this.addSetting(this.size);
        this.addSetting(this.custom);
        this.addSetting(this.font);
        this.addSetting(this.type);
        this.addSetting(this.fontsize);
        this.addSetting(this.antialias);
        this.addSetting(this.fractionalmetrics);
        this.addSetting(this.override);
        Class36.Method552(new Class197(Class36.Field458.deriveFont(((Float)this.fontsize.getValue()).floatValue()), (Boolean)this.antialias.getValue(), (Boolean)this.fractionalmetrics.getValue()));
        if (((Boolean)this.custom.getValue()).booleanValue()) {
            try {
                Class36.Method553(new Class198(new Font((String)this.font.getValue(), ((Class363)((Object)this.type.getValue())).Field1206, ((Float)this.size.getValue()).intValue()).deriveFont(((Float)this.size.getValue()).floatValue())));
            }
            catch (Exception exception) {
                Class36.Method553(new Class198(Class36.Field458.deriveFont(((Float)this.size.getValue()).floatValue())));
            }
        } else {
            Class36.Method553(new Class198(new Font((String)this.font.getValue(), ((Class363)((Object)this.type.getValue())).Field1206, ((Float)this.size.getValue()).intValue()).deriveFont(((Float)this.size.getValue()).floatValue())));        }
    }

    public void getNewFont() {
        if (((Boolean)this.newfontrenderer.getValue()).booleanValue()) {
            if (((Boolean)this.custom.getValue()).booleanValue()) {
                try {
                    Class36.Method553(new Class198(new Font((String)this.font.getValue(), ((Class363)((Object)this.type.getValue())).Field1206, ((Float)this.size.getValue()).intValue()).deriveFont(((Float)this.size.getValue()).floatValue())));
                }
                catch (Exception exception) {
                    Class36.Method553(new Class198(Class36.Field458.deriveFont(((Float)this.size.getValue()).floatValue())));
                }
            } else {
                Class36.Method553(new Class198(Class36.Field458.deriveFont(((Float)this.size.getValue()).floatValue())));
            }
        } else {
            Class36.Method552(new Class197(Class36.Field458.deriveFont(((Float)this.fontsize.getValue()).floatValue()), (Boolean)this.antialias.getValue(), (Boolean)this.fractionalmetrics.getValue()));
        }
    }

    private void getFontWitValue(Class210 class210) {
        this.getNewFont();
    }

    private void getFontWitClrRLog(Class139 class139) {
        if (class139.Method1564() == this) {
            this.getNewFont();
        }
    }

    private boolean getNewFontFalse1() {
        return (Boolean)this.newfontrenderer.getValue() == false;
    }

    private boolean getNewFontFalse2() {
        return (Boolean)this.newfontrenderer.getValue() == false;
    }

    private boolean getNewFontFalse3() {
        return (Boolean)this.newfontrenderer.getValue() == false;
    }

    private boolean getCustome1() {
        return (Boolean)this.newfontrenderer.getValue() != false && (Boolean)this.custom.getValue() != false;
    }

    private boolean getCustome2() {
        return (Boolean)this.newfontrenderer.getValue() != false && (Boolean)this.custom.getValue() != false;
    }

    private static boolean getBoolean1() {
        return true;
    }


    private static boolean getBoolean2() {
        return true;
    }
}
