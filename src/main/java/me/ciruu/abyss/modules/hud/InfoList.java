package me.ciruu.abyss.modules.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;
import me.ciruu.abyss.Class208;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class35;
import me.ciruu.abyss.Class36;
import me.ciruu.abyss.Class499;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class262;
import me.ciruu.abyss.enums.Class500;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.modules.hud.CrystalCounter;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class InfoList
extends Module {
    private final Setting main = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting scale = new Setting("Scale", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(5.0f));
    private final Setting step = new Setting("Step", "", (Module)this, (Object)9, 5, 15);
    public final Setting renderMode = new Setting("RenderMode", "", (Module)this, (Object)Class262.Down, this.main, InfoList::Method2466);
    public final Setting order = new Setting("Order", "", (Module)this, (Object)false, this.main, InfoList::Method2467);
    public final Setting speed = new Setting("Speed", "", (Module)this, (Object)true, this.main, InfoList::Method2468);
    public final Setting serverBrand = new Setting("ServerBrand", "", (Module)this, (Object)true, this.main, InfoList::Method2469);
    public final Setting durability = new Setting("Durability", "", (Module)this, (Object)true, this.main, InfoList::Method2470);
    public final Setting tps = new Setting("TPS", "", (Module)this, (Object)true, this.main, InfoList::Method2471);
    public final Setting fps = new Setting("FPS", "", (Module)this, (Object)true, this.main, InfoList::Method2472);
    public final Setting ping = new Setting("Ping", "", (Module)this, (Object)true, this.main, InfoList::Method2473);
    public final Setting serverIP = new Setting("ServerIP", "", (Module)this, (Object)true, this.main, InfoList::Method2474);
    public final Setting cps = new Setting("CPS", "", (Module)this, (Object)true, this.main, InfoList::Method2475);
    public final Setting potions = new Setting("Potions", "", (Module)this, (Object)true, this.main, InfoList::Method2476);
    public final Setting inverse = new Setting("Inverse", "", (Module)this, (Object)false, this.main, this.potions::getValue);
    public final Setting romanNumbers = new Setting("RomanNumbers", "", (Module)this, (Object)true, this.main, this.potions::getValue);
    public final Setting profile = new Setting("Profile", "", (Module)this, (Object)true, this.main, InfoList::Method2477);
    public final Setting crystals = new Setting("Crystals", "", (Module)this, (Object)true, this.main, InfoList::Method2478);
    private final Setting colorLabel = new Setting("ColorLabel", "", this, new Class25("Color Settings"));
    public final Setting color = new Setting("Color", "", (Module)this, (Object)new Color(142, 0, 255, 255), this.colorLabel, InfoList::Method2479);
    public final Setting rainbow = new Setting("Rainbow", "", (Module)this, (Object)false, this.colorLabel, InfoList::Method2480);
    public final Setting rSpeed = new Setting("RSpeed", "", this, Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(2.0f), this.colorLabel, this.rainbow::getValue);
    public final Setting saturation = new Setting("Saturation", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.colorLabel, this.rainbow::getValue);
    public final Setting brightness = new Setting("Brightness", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.colorLabel, this.rainbow::getValue);
    public final Setting gradient = new Setting("Gradient", "", (Module)this, (Object)false, this.colorLabel, this.rainbow::getValue);
    public final Setting delta = new Setting("Delta", "", this, Float.valueOf(-0.05f), Float.valueOf(-0.1f), Float.valueOf(0.1f), this.colorLabel, this::Method2481);
    private final DecimalFormat Field2050 = new DecimalFormat("#.#");
    private Map Field2051 = new HashMap();
    @EventHandler
    private Listener Field2052 = new Listener<Class35>(this::Method2482, new Predicate[0]);

    public InfoList() {
        super("InfoList", "", Class11.HUD, "");
        this.addSetting(this.scale);
        this.addSetting(this.step);
        this.addSetting(this.renderMode);
        this.addSetting(this.order);
        this.addSetting(this.speed);
        this.addSetting(this.serverBrand);
        this.addSetting(this.durability);
        this.addSetting(this.tps);
        this.addSetting(this.fps);
        this.addSetting(this.ping);
        this.addSetting(this.serverIP);
        this.addSetting(this.cps);
        this.addSetting(this.potions);
        this.addSetting(this.inverse);
        this.addSetting(this.romanNumbers);
        this.addSetting(this.profile);
        this.addSetting(this.crystals);
        this.addSetting(this.colorLabel);
        this.addSetting(this.color);
        this.addSetting(this.rainbow);
        this.addSetting(this.rSpeed);
        this.addSetting(this.saturation);
        this.addSetting(this.brightness);
        this.addSetting(this.gradient);
        this.addSetting(this.delta);
        this.Field2051.put(MobEffects.SPEED, new Color(124, 175, 198));
        this.Field2051.put(MobEffects.SLOWNESS, new Color(90, 108, 129));
        this.Field2051.put(MobEffects.HASTE, new Color(217, 192, 67));
        this.Field2051.put(MobEffects.MINING_FATIGUE, new Color(74, 66, 23));
        this.Field2051.put(MobEffects.STRENGTH, new Color(147, 36, 35));
        this.Field2051.put(MobEffects.INSTANT_HEALTH, new Color(67, 10, 9));
        this.Field2051.put(MobEffects.INSTANT_DAMAGE, new Color(67, 10, 9));
        this.Field2051.put(MobEffects.JUMP_BOOST, new Color(34, 255, 76));
        this.Field2051.put(MobEffects.NAUSEA, new Color(85, 29, 74));
        this.Field2051.put(MobEffects.REGENERATION, new Color(205, 92, 171));
        this.Field2051.put(MobEffects.RESISTANCE, new Color(153, 69, 58));
        this.Field2051.put(MobEffects.FIRE_RESISTANCE, new Color(228, 154, 58));
        this.Field2051.put(MobEffects.WATER_BREATHING, new Color(46, 82, 153));
        this.Field2051.put(MobEffects.INVISIBILITY, new Color(127, 131, 146));
        this.Field2051.put(MobEffects.BLINDNESS, new Color(31, 31, 35));
        this.Field2051.put(MobEffects.NIGHT_VISION, new Color(31, 31, 161));
        this.Field2051.put(MobEffects.HUNGER, new Color(88, 118, 83));
        this.Field2051.put(MobEffects.WEAKNESS, new Color(72, 77, 72));
        this.Field2051.put(MobEffects.POISON, new Color(78, 147, 49));
        this.Field2051.put(MobEffects.WITHER, new Color(53, 42, 39));
        this.Field2051.put(MobEffects.HEALTH_BOOST, new Color(248, 125, 35));
        this.Field2051.put(MobEffects.ABSORPTION, new Color(37, 82, 165));
        this.Field2051.put(MobEffects.SATURATION, new Color(248, 36, 35));
        this.Field2051.put(MobEffects.GLOWING, new Color(148, 160, 97));
        this.Field2051.put(MobEffects.LEVITATION, new Color(206, 255, 255));
        this.Field2051.put(MobEffects.LUCK, new Color(51, 153, 0));
        this.Field2051.put(MobEffects.UNLUCK, new Color(192, 164, 77));
    }

    public int Method2484(int n) {
        double d = Math.ceil((double)(System.currentTimeMillis() + (long)n) / (20.0 * (double)((Float)this.rSpeed.getValue()).floatValue()));
        return Color.getHSBColor((float)((d %= 360.0) / 360.0), ((Float)this.saturation.getValue()).floatValue(), ((Float)this.brightness.getValue()).floatValue()).getRGB();
    }

    public Color Method2485(int n) {
        double d = Math.ceil((double)(System.currentTimeMillis() + (long)n) / (20.0 * (double)((Float)this.rSpeed.getValue()).floatValue()));
        return Color.getHSBColor((float)((d %= 360.0) / 360.0), ((Float)this.saturation.getValue()).floatValue(), ((Float)this.brightness.getValue()).floatValue());
    }

    private String Method2486(int n) {
        LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<String, Integer>();
        linkedHashMap.put("M", 1000);
        linkedHashMap.put("CM", 900);
        linkedHashMap.put("D", 500);
        linkedHashMap.put("CD", 400);
        linkedHashMap.put("C", 100);
        linkedHashMap.put("XC", 90);
        linkedHashMap.put("L", 50);
        linkedHashMap.put("XL", 40);
        linkedHashMap.put("X", 10);
        linkedHashMap.put("IX", 9);
        linkedHashMap.put("V", 5);
        linkedHashMap.put("IV", 4);
        linkedHashMap.put("I", 1);
        String string = "";
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            int n2 = n / (Integer)entry.getValue();
            string = string + this.Method2487((String)entry.getKey(), n2);
            n %= ((Integer)entry.getValue()).intValue();
        }
        return string;
    }

    private String Method2487(String string, int n) {
        if (string == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    private void Method2482(Class35 class35) {
        String string;
        GlStateManager.pushMatrix();
        GlStateManager.scale((float)((Float)this.scale.getValue()).floatValue(), (float)((Float)this.scale.getValue()).floatValue(), (float)((Float)this.scale.getValue()).floatValue());
        float f = (float)class35.Method2006() / ((Float)this.scale.getValue()).floatValue();
        int n = 1;
        float f2 = this.renderMode.getValue() == Class262.Up ? 1.0f : ((float)class35.Method1967() - 10.0f * ((Float)this.scale.getValue()).floatValue() - (float)(Globals.mc.currentScreen instanceof GuiChat ? 14 : 0)) / ((Float)this.scale.getValue()).floatValue();
        ArrayList<String> arrayList = new ArrayList<String>();
        HashMap hashMap = new HashMap();
        if (((Boolean)this.serverBrand.getValue()).booleanValue()) {
            string = "Server brand" + ChatFormatting.WHITE + Manager.Field1645.Method2488();
            arrayList.add(string);
        }
        if (((Boolean)this.speed.getValue()).booleanValue()) {
            string = "Speed" + ChatFormatting.WHITE + Manager.Field1643.Method469() + " km/h";
            arrayList.add(string);
        }
        if (((Boolean)this.durability.getValue()).booleanValue()) {
            int n2 = Globals.mc.player.getHeldItemMainhand().getMaxDamage() - Globals.mc.player.getHeldItemMainhand().getItemDamage();
            ItemStack object = Globals.mc.player.getHeldItemMainhand();
            boolean bl = false;
            if (!object.isEmpty() && (object.getItem() instanceof ItemTool || object.getItem() instanceof ItemArmor || object.getItem() instanceof ItemSword)) {
                try {
                    bl = object.getTagCompound().hasKey("Unbreakable");
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            if (n2 > 0) {
                String string2 = "Durability" + (bl ? ChatFormatting.DARK_PURPLE + "Infinite" : ChatFormatting.GREEN + String.valueOf(n2));
                arrayList.add(string2);
            }
        }
        if (((Boolean)this.ping.getValue()).booleanValue()) {
            String string3 = "Ping" + ChatFormatting.WHITE + Manager.Field1645.Method2389() + " ms";
            arrayList.add(string3);
        }
        if (((Boolean)this.tps.getValue()).booleanValue()) {
            String string4 = "TPS" + ChatFormatting.WHITE + String.format("%.1f", Float.valueOf(Manager.Field1645.Method2230()));
            arrayList.add(string4);
        }
        if (((Boolean)this.fps.getValue()).booleanValue()) {
            String string5 = "FPS" + ChatFormatting.WHITE + Minecraft.debugFPS;
            arrayList.add(string5);
        }
        if (((Boolean)this.serverIP.getValue()).booleanValue()) {
            String string6 = "Server" + ChatFormatting.WHITE + (Globals.mc.getCurrentServerData() == null ? "None" : Globals.mc.getCurrentServerData().serverIP);
            arrayList.add(string6);
        }
        if (((Boolean)this.cps.getValue()).booleanValue()) {
            String string7 = "CPS" + ChatFormatting.WHITE + Class499.Method2193(Class500.LEFT) + ":" + Class499.Method2193(Class500.RIGHT);
            arrayList.add(string7);
        }
        if (((Boolean)this.profile.getValue()).booleanValue()) {
            String string8 = "Profile" + ChatFormatting.WHITE + Class208.Field486;
            arrayList.add(string8);
        }
        if (((Boolean)this.crystals.getValue()).booleanValue() && Manager.moduleManager.isModuleEnabled(CrystalCounter.class)) {
            String string9 = "P/B" + ChatFormatting.WHITE + CrystalCounter.Field2055 + ":" + CrystalCounter.Field2056;
            arrayList.add(string9);
        }
        if (((Boolean)this.order.getValue()).booleanValue()) {
            arrayList.sort(InfoList::Method2489);
        }
        for (String string3 : arrayList) {
            if (((Boolean)this.gradient.getValue()).booleanValue() && ((Boolean)this.rainbow.getValue()).booleanValue()) {
                Class36.Method565(string3, f - (float)Class36.Method259(string3) - 2.0f, f2, this.Method2485(n * 300), 1.0f + ((Float)this.delta.getValue()).floatValue(), true);
            } else {
                Class36.Method63(string3, f - (float)Class36.Method259(string3) - 2.0f, f2, (Boolean)this.rainbow.getValue() != false ? this.Method2484(n * 300) : ((Color)this.color.getValue()).getRGB());
            }
            ++n;
            f2 = this.renderMode.getValue() == Class262.Up ? f2 + (float)((Integer)this.step.getValue()).intValue() : f2 - (float)((Integer)this.step.getValue()).intValue();
        }
        float f3 = ((float)class35.Method1967() - 10.0f * ((Float)this.scale.getValue()).floatValue() - (float)(Globals.mc.currentScreen instanceof GuiChat ? 14 : 0)) / ((Float)this.scale.getValue()).floatValue();
        float f4 = 1.0f;
        if (((Boolean)this.potions.getValue()).booleanValue()) {
            float f5 = (Boolean)this.inverse.getValue() == false ? f2 : (this.renderMode.getValue() == Class262.Up ? f3 : f4);
            for (PotionEffect potionEffect : Globals.mc.player.getActivePotionEffects()) {
                Potion potion = potionEffect.getPotion();
                String string4 = I18n.format((String)potion.getName(), (Object[])new Object[0]) + "" + (potionEffect.getAmplifier() == 0 ? "" : ((Boolean)this.romanNumbers.getValue() != false ? this.Method2486(potionEffect.getAmplifier() + 1) : Integer.valueOf(potionEffect.getAmplifier() + 1)) + "") + ChatFormatting.WHITE + Potion.getPotionDurationString((PotionEffect)potionEffect, (float)1.0f);
                Class36.Method63(string4, f - (float)Class36.Method259(string4) - 2.0f, f5, ((Color)this.Field2051.get(potionEffect.getPotion())).getRGB());
                f2 = this.renderMode.getValue() == Class262.Up ? f2 + (float)((Integer)this.step.getValue()).intValue() : f2 - (float)((Integer)this.step.getValue()).intValue();
                f5 = (Boolean)this.inverse.getValue() == false ? f2 : (this.renderMode.getValue() == Class262.Up ? (f3 -= (float)((Integer)this.step.getValue()).intValue()) : (f4 += (float)((Integer)this.step.getValue()).intValue()));
            }
        }
        GlStateManager.popMatrix();
    }

    private static int Method2489(String string, String string2) {
        return Class36.Method259(string) >= Class36.Method259(string2) ? -1 : 0;
    }

    private boolean Method2481() {
        return (Boolean)this.rainbow.getValue() != false && (Boolean)this.gradient.getValue() != false;
    }

    private static boolean Method2480() {
        return true;
    }

    private static boolean Method2479() {
        return true;
    }

    private static boolean Method2478() {
        return true;
    }

    private static boolean Method2477() {
        return true;
    }

    private static boolean Method2476() {
        return true;
    }

    private static boolean Method2475() {
        return true;
    }

    private static boolean Method2474() {
        return true;
    }

    private static boolean Method2473() {
        return true;
    }

    private static boolean Method2472() {
        return true;
    }

    private static boolean Method2471() {
        return true;
    }

    private static boolean Method2470() {
        return true;
    }

    private static boolean Method2469() {
        return true;
    }

    private static boolean Method2468() {
        return true;
    }

    private static boolean Method2467() {
        return true;
    }

    private static boolean Method2466() {
        return true;
    }
}
