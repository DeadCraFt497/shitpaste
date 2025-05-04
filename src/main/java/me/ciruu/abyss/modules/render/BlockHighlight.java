package me.ciruu.abyss.modules.render;

import java.awt.Color;
import java.util.function.Predicate;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.Class66;
import me.ciruu.abyss.Class67;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class65;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.util.math.RayTraceResult;

public class BlockHighlight
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting mode = new Setting("Mode", "", this, (Object)Class65.Box);
    private final Setting box = new Setting("Box", "Box", (Module)this, (Object)false, this.mainwindow, this::Method126);
    private final Setting outline = new Setting("Outline", "Outline", (Module)this, (Object)true, this.mainwindow, this::Method127);
    private final Setting linewidth = new Setting("LineWidth", "LineWidth", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(5.0f));
    private final Setting rainbow = new Setting("Rainbow", "", (Module)this, (Object)true, this.mainwindow, BlockHighlight::Method128);
    private final Setting rainbowspeed = new Setting("RainbowSpeed", "", this, Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(2.0f), this.mainwindow, this.rainbow::getValue);
    private final Setting rainbowsaturation = new Setting("RainbowSaturation", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.mainwindow, this.rainbow::getValue);
    private final Setting rainbowbrightness = new Setting("RainbowBrightness", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.mainwindow, this.rainbow::getValue);
    private final Setting rainbowalpha = new Setting("RainbowAlpha", "", this, 255, 0, 255, this.mainwindow, this::Method129);
    private final Setting rainbowinverse = new Setting("RainbowInverse", "", (Module)this, (Object)true, this.mainwindow, this::Method130);
    private final Setting color = new Setting("Color", "", (Module)this, (Object)new Color(255, 0, 255, 125), this.mainwindow, this::Method131);
    @EventHandler
    private Listener Field157 = new Listener<Class66>(this::Method132, new Predicate[0]);

    public BlockHighlight() {
        super("BlockHighlight", "Highlights the block in your crosshair.", Class11.RENDER, "");
        this.addSetting(this.mode);
        this.addSetting(this.box);
        this.addSetting(this.outline);
        this.addSetting(this.linewidth);
        this.addSetting(this.rainbow);
        this.addSetting(this.rainbowspeed);
        this.addSetting(this.rainbowsaturation);
        this.addSetting(this.rainbowbrightness);
        this.addSetting(this.rainbowalpha);
        this.addSetting(this.rainbowinverse);
        this.addSetting(this.color);
    }

    public Color Method134(int n) {
        double d = Math.ceil((double)(System.currentTimeMillis() + (long)n) / (20.0 * (double)((Float)this.rainbowspeed.getValue()).floatValue()));
        Color color = Color.getHSBColor((float)((d %= 360.0) / 360.0), ((Float)this.rainbowsaturation.getValue()).floatValue(), ((Float)this.rainbowbrightness.getValue()).floatValue());
        try {
            return new Color(color.getRed(), color.getGreen(), color.getBlue(), (Integer)this.rainbowalpha.getValue());
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return color;
        }
    }

    private void Method132(Class66 class66) {
        if (Globals.mc.getRenderManager() == null) {
            return;
        }
        RayTraceResult rayTraceResult = Globals.mc.objectMouseOver;
        if (rayTraceResult == null || rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) {
            return;
        }
        switch ((Class65)((Object)this.mode.getValue())) {
            case Box: {
                if (((Boolean)this.rainbow.getValue()).booleanValue()) {
                    if (((Boolean)this.box.getValue()).booleanValue()) {
                        Class50.Method135(rayTraceResult.getBlockPos(), this.Method134((Boolean)this.rainbowinverse.getValue() != false ? 0 : 3000), this.Method134((Boolean)this.rainbowinverse.getValue() != false ? 3000 : 0));
                    }
                    if (!((Boolean)this.outline.getValue()).booleanValue()) break;
                    Class50.Method136(rayTraceResult.getBlockPos(), this.Method134(0), this.Method134(3000), ((Float)this.linewidth.getValue()).floatValue());
                    break;
                }
                Class50.Method137(rayTraceResult.getBlockPos(), (Color)this.color.getValue(), false, (Color)this.color.getValue(), ((Float)this.linewidth.getValue()).floatValue(), (Boolean)this.outline.getValue(), (Boolean)this.box.getValue(), ((Color)this.color.getValue()).getAlpha(), false);
                break;
            }
            case Clock: {
                Class67.Method138(rayTraceResult.getBlockPos(), (Boolean)this.rainbow.getValue() != false ? this.Method134(0) : (Color)this.color.getValue(), ((Float)this.linewidth.getValue()).floatValue());
                break;
            }
            case Triangle: {
                Class67.Method139(rayTraceResult.getBlockPos(), (Boolean)this.rainbow.getValue() != false ? this.Method134(0) : (Color)this.color.getValue(), ((Float)this.linewidth.getValue()).floatValue());
                break;
            }
            case Butterfly: {
                Class67.Method140(rayTraceResult.getBlockPos(), (Boolean)this.rainbow.getValue() != false ? this.Method134(0) : (Color)this.color.getValue(), ((Float)this.linewidth.getValue()).floatValue());
            }
        }
    }

    private boolean Method131() {
        return (Boolean)this.rainbow.getValue() == false;
    }

    private boolean Method130() {
        return (Boolean)this.rainbow.getValue() != false && (Boolean)this.box.getValue() != false;
    }

    private boolean Method129() {
        return (Boolean)this.rainbow.getValue() != false && (Boolean)this.box.getValue() != false;
    }

    private static boolean Method128() {
        return true;
    }

    private boolean Method127() {
        return this.mode.getValue() == Class65.Box;
    }

    private boolean Method126() {
        return this.mode.getValue() == Class65.Box;
    }
}
