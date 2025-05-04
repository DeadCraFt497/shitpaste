package me.ciruu.abyss.modules.client;

import java.awt.Color;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.modules.hud.ArrayList;
import me.ciruu.abyss.modules.hud.ChatWatermark;
import me.ciruu.abyss.modules.hud.Coordinates;
import me.ciruu.abyss.modules.hud.Greeter;
import me.ciruu.abyss.modules.hud.InfoList;
import me.ciruu.abyss.modules.hud.Watermark;
import me.ciruu.abyss.modules.hud.WatermarkCool;
import me.ciruu.abyss.settings.Setting;

public class HUDColorSync
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting color = new Setting("Color", "", (Module)this, (Object)new Color(142, 0, 255, 255), this.mainwindow, HUDColorSync::getBoolean2);
    private final Setting rainbow = new Setting("Rainbow", "", (Module)this, (Object)false, this.mainwindow, HUDColorSync::getBoolean1);
    private final Setting speed = new Setting("Speed", "", this, Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(2.0f), this.mainwindow, this.rainbow::getValue);
    private final Setting saturation = new Setting("Saturation", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.mainwindow, this.rainbow::getValue);
    private final Setting brightness = new Setting("Brightness", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.mainwindow, this.rainbow::getValue);
    private final Setting gradient = new Setting("Gradient", "", (Module)this, (Object)false, this.mainwindow, this.rainbow::getValue);
    private final Setting delta = new Setting("Delta", "", this, Float.valueOf(-0.02f), Float.valueOf(-0.1f), Float.valueOf(0.1f), this.mainwindow, this::getRainbowGradient);

    public HUDColorSync() {
        super("HUDColorSync", "", Class11.CLIENT, "");
        this.addSetting(this.color);
        this.addSetting(this.rainbow);
        this.addSetting(this.speed);
        this.addSetting(this.saturation);
        this.addSetting(this.brightness);
        this.addSetting(this.gradient);
        this.addSetting(this.delta);
    }

    public void getHUDColorSync() {
        super.getEnable();
        ChatWatermark chatWatermark = (ChatWatermark)Manager.moduleManager.getModuleByClass(ChatWatermark.class);
        chatWatermark.color.setValue(this.color.getValue());
        chatWatermark.rainbow.setValue(this.rainbow.getValue());
        chatWatermark.speed.setValue(this.speed.getValue());
        chatWatermark.saturation.setValue(this.saturation.getValue());
        chatWatermark.brightness.setValue(this.brightness.getValue());
        chatWatermark.gradient.setValue(this.gradient.getValue());
        chatWatermark.delta.setValue(this.delta.getValue());
        Coordinates coordinates = (Coordinates)Manager.moduleManager.getModuleByClass(Coordinates.class);
        coordinates.color.setValue(this.color.getValue());
        coordinates.rainbow.setValue(this.rainbow.getValue());
        coordinates.speed.setValue(this.speed.getValue());
        coordinates.saturation.setValue(this.saturation.getValue());
        coordinates.brightness.setValue(this.brightness.getValue());
        coordinates.gradient.setValue(this.gradient.getValue());
        coordinates.delta.setValue(this.delta.getValue());
        Greeter greeter = (Greeter)Manager.moduleManager.getModuleByClass(Greeter.class);
        greeter.color.setValue(this.color.getValue());
        greeter.rainbow.setValue(this.rainbow.getValue());
        greeter.speed.setValue(this.speed.getValue());
        greeter.saturation.setValue(this.saturation.getValue());
        greeter.brightness.setValue(this.brightness.getValue());
        greeter.gradient.setValue(this.gradient.getValue());
        greeter.delta.setValue(this.delta.getValue());
        InfoList infoList = (InfoList)Manager.moduleManager.getModuleByClass(InfoList.class);
        infoList.color.setValue(this.color.getValue());
        infoList.rainbow.setValue(this.rainbow.getValue());
        infoList.rSpeed.setValue(this.speed.getValue());
        infoList.saturation.setValue(this.saturation.getValue());
        infoList.brightness.setValue(this.brightness.getValue());
        infoList.gradient.setValue(this.gradient.getValue());
        infoList.delta.setValue(this.delta.getValue());
        ArrayList arrayList = (ArrayList)Manager.moduleManager.getModuleByClass(ArrayList.class);
        arrayList.color.setValue(this.color.getValue());
        arrayList.rainbow.setValue(this.rainbow.getValue());
        arrayList.speed.setValue(this.speed.getValue());
        arrayList.saturation.setValue(this.saturation.getValue());
        arrayList.brightness.setValue(this.brightness.getValue());
        arrayList.gradient.setValue(this.gradient.getValue());
        arrayList.delta.setValue(this.delta.getValue());
        Watermark watermark = (Watermark)Manager.moduleManager.getModuleByClass(Watermark.class);
        watermark.color.setValue(this.color.getValue());
        watermark.rainbow.setValue(this.rainbow.getValue());
        watermark.speed.setValue(this.speed.getValue());
        watermark.saturation.setValue(this.saturation.getValue());
        watermark.brightness.setValue(this.brightness.getValue());
        watermark.gradient.setValue(this.gradient.getValue());
        watermark.delta.setValue(this.delta.getValue());
        WatermarkCool watermarkCool = (WatermarkCool)Manager.moduleManager.getModuleByClass(WatermarkCool.class);
        watermarkCool.color.setValue(this.color.getValue());
        watermarkCool.rainbow.setValue(this.rainbow.getValue());
        watermarkCool.speed.setValue(this.speed.getValue());
        watermarkCool.saturation.setValue(this.saturation.getValue());
        watermarkCool.brightness.setValue(this.brightness.getValue());
        watermarkCool.gradient.setValue(this.gradient.getValue());
        watermarkCool.delta.setValue(this.delta.getValue());
        this.getStarted();
    }

    private boolean getRainbowGradient() {
        return (Boolean)this.rainbow.getValue() != false && (Boolean)this.gradient.getValue() != false;
    }

    private static boolean getBoolean1() {
        return true;
    }

    private static boolean getBoolean2() {
        return true;
    }

    private void getStarted() {
        // Add the necessary functionality here.
        System.out.println("Method2257 executed.");
    }
}
