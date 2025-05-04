package me.ciruu.abyss.modules.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.function.Predicate;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class35;
import me.ciruu.abyss.Class36;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;

public class Coordinates
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting step = new Setting("Step", "", (Module)this, (Object)9, 5, 15);
    public final Setting coords = new Setting("Coords", "", (Module)this, (Object)true, this.mainwindow, Coordinates::Method1958);
    public final Setting nethercoords = new Setting("NetherCoords", "", (Module)this, (Object)true, this.mainwindow, this.coords::getValue);
    public final Setting decimals = new Setting("Decimals", "", (Module)this, (Object)true, this.mainwindow, this.coords::getValue);
    public final Setting direction = new Setting("Direction", "", (Module)this, (Object)true, this.mainwindow, Coordinates::Method1959);
    public final Setting rotations = new Setting("Rotations", "", (Module)this, (Object)true, this.mainwindow, this.direction::getValue);
    private final Setting scale = new Setting("Scale", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(5.0f));
    private final Setting colorlabel = new Setting("ColorLabel", "", this, new Class25("Color Settings"));
    public final Setting color = new Setting("Color", "", (Module)this, (Object)new Color(142, 0, 255, 255), this.colorlabel, Coordinates::Method1960);
    public final Setting rainbow = new Setting("Rainbow", "", (Module)this, (Object)false, this.colorlabel, Coordinates::Method1961);
    public final Setting speed = new Setting("Speed", "", this, Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(2.0f), this.colorlabel, this.rainbow::getValue);
    public final Setting saturation = new Setting("Saturation", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.colorlabel, this.rainbow::getValue);
    public final Setting brightness = new Setting("Brightness", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.colorlabel, this.rainbow::getValue);
    public final Setting gradient = new Setting("Gradient", "", (Module)this, (Object)false, this.colorlabel, this.rainbow::getValue);
    public final Setting delta = new Setting("Delta", "", this, Float.valueOf(-0.05f), Float.valueOf(-0.1f), Float.valueOf(0.1f), this.colorlabel, this::Method1962);
    private final DecimalFormat fmt = new DecimalFormat("#.#");
    @EventHandler
    private Listener Field1559 = new Listener<Class35>(this::Method1963, new Predicate[0]);

    public Coordinates() {
        super("Coordinates", "", Class11.HUD, "");
        this.addSetting(this.step);
        this.addSetting(this.coords);
        this.addSetting(this.nethercoords);
        this.addSetting(this.decimals);
        this.addSetting(this.direction);
        this.addSetting(this.rotations);
        this.addSetting(this.scale);
        this.addSetting(this.colorlabel);
        this.addSetting(this.color);
        this.addSetting(this.rainbow);
        this.addSetting(this.speed);
        this.addSetting(this.saturation);
        this.addSetting(this.brightness);
        this.addSetting(this.gradient);
        this.addSetting(this.delta);
    }

    public int Method1965(int n) {
        double d = Math.ceil((double)(System.currentTimeMillis() + (long)n) / (20.0 * (double)((Float)this.speed.getValue()).floatValue()));
        return Color.getHSBColor((float)((d %= 360.0) / 360.0), ((Float)this.saturation.getValue()).floatValue(), ((Float)this.brightness.getValue()).floatValue()).getRGB();
    }

    public Color Method1966(int n) {
        double d = Math.ceil((double)(System.currentTimeMillis() + (long)n) / (20.0 * (double)((Float)this.speed.getValue()).floatValue()));
        return Color.getHSBColor((float)((d %= 360.0) / 360.0), ((Float)this.saturation.getValue()).floatValue(), ((Float)this.brightness.getValue()).floatValue());
    }

    private void Method1963(Class35 class35) {
        GlStateManager.pushMatrix();
        GlStateManager.scale((float)((Float)this.scale.getValue()).floatValue(), (float)((Float)this.scale.getValue()).floatValue(), (float)((Float)this.scale.getValue()).floatValue());
        float f = (float)class35.Method1967() - 10.0f * ((Float)this.scale.getValue()).floatValue();
        int n = 1;
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = Globals.mc.currentScreen instanceof GuiChat ? 14 : 0;
        f -= (float)n2;
        f /= ((Float)this.scale.getValue()).floatValue();
        if (((Boolean)this.coords.getValue()).booleanValue()) {
            boolean bl = Globals.mc.world.getBiome(Globals.mc.player.getPosition()).getBiomeName().equals("Hell");
            int n3 = (int)Globals.mc.player.posX;
            int n4 = (int)Globals.mc.player.posY;
            int n5 = (int)Globals.mc.player.posZ;
            float f2 = !bl ? 0.125f : 8.0f;
            int n6 = (int)(Globals.mc.player.posX * (double)f2);
            int n7 = (int)(Globals.mc.player.posZ * (double)f2);
            String string = "XYZ" + ChatFormatting.WHITE + "(" + ((Boolean)this.decimals.getValue() == false ? Integer.valueOf(n3) : String.format("%.1f", Globals.mc.player.posX)) + "," + ((Boolean)this.decimals.getValue() == false ? Integer.valueOf(n4) : String.format("%.1f", Globals.mc.player.posY)) + "," + ((Boolean)this.decimals.getValue() == false ? Integer.valueOf(n5) : String.format("%.1f", Globals.mc.player.posZ)) + ")" + (Globals.mc.player.dimension != 1 ? ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + n6 + "," + n7 + ChatFormatting.GRAY + "]" : "");
            arrayList.add(string);
        }
        if (((Boolean)this.direction.getValue()).booleanValue()) {
            String[] stringArray = Manager.Field456.Method1968(false).split("");
            String string = stringArray[0] + ChatFormatting.WHITE + "" + stringArray[1] + ((Boolean)this.rotations.getValue() != false ? "" + ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + String.format("%.1f", Float.valueOf(Globals.mc.player.rotationPitch)) + "," + String.format("%.1f", Float.valueOf(Math.abs(Globals.mc.player.rotationYaw) % 360.0f)) + ChatFormatting.GRAY + "]" : "");
            arrayList.add(string);
        }
        for (String string : arrayList) {
            if (((Boolean)this.gradient.getValue()).booleanValue() && ((Boolean)this.rainbow.getValue()).booleanValue()) {
                Class36.Method565(string, 1.0f / ((Float)this.scale.getValue()).floatValue(), f, this.Method1966(n * 300), 1.0f + ((Float)this.delta.getValue()).floatValue(), true);
            } else {
                Class36.Method63(string, 1.0f / ((Float)this.scale.getValue()).floatValue(), f, (Boolean)this.rainbow.getValue() != false ? this.Method1965(n * 300) : ((Color)this.color.getValue()).getRGB());
            }
            ++n;
            f -= (float)((Integer)this.step.getValue()).intValue();
        }
        GlStateManager.popMatrix();
    }

    private boolean Method1962() {
        return (Boolean)this.rainbow.getValue() != false && (Boolean)this.gradient.getValue() != false;
    }

    private static boolean Method1961() {
        return true;
    }

    private static boolean Method1960() {
        return true;
    }

    private static boolean Method1959() {
        return true;
    }

    private static boolean Method1958() {
        return true;
    }
}
