package me.ciruu.abyss.modules.hud;

import java.awt.Color;
import java.util.function.Predicate;
import me.ciruu.abyss.Class22;
import me.ciruu.abyss.Class35;
import me.ciruu.abyss.Class36;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.modules.client.VoiceAssistant;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

public class VoiceResult
extends Module {
    private final Setting x = new Setting("X", "X", (Module)this, (Object)5, 0, 1000);
    private final Setting y = new Setting("Y", "Y", (Module)this, (Object)20, 0, 1000);
    private String Field252 = "";
    private final Class22 Field253 = new Class22();
    @EventHandler
    private final Listener Field254 = new Listener<Class35>(this::Method255, new Predicate[0]);

    public VoiceResult() {
        super("VoiceResult", "Displays the result string of VoiceAssistant", Class11.HUD, "");
        this.addSetting(this.x);
        this.addSetting(this.y);
    }

    private void Method255(Class35 class35) {
        if (Globals.mc.player == null || Globals.mc.world == null || !Manager.moduleManager.isModuleEnabled(VoiceAssistant.class)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Manager.Field255.Method257().getRecognizedStringQueue().forEach(arg_0 -> VoiceResult.Method258(stringBuilder, (String) arg_0));
        if (!this.Field252.equals(stringBuilder.toString()) && !stringBuilder.toString().equals("")) {
            this.Field252 = stringBuilder.toString();
            this.Field253.getNanoTime();
        }
        if (stringBuilder.toString().equals("") && this.Field253.getLongNanoTime(1000L)) {
            this.Field252 = stringBuilder.toString();
            this.Field253.getNanoTime();
        }
        Class50.Method92(((Integer)this.x.getValue()).intValue(), ((Integer)this.y.getValue()).intValue(), (Integer)this.x.getValue() + (Class36.Method259(this.Field252) + 4), (Integer)this.y.getValue() + Class36.Method260() + 4, new Color(0, 0, 0, 155).getRGB());
        Class36.Method63(this.Field252, (Integer)this.x.getValue() + 2, (float)((Integer)this.y.getValue()).intValue() + ((float)Class36.Method260() / 2.0f - (float)Class36.Method260() / 3.0f), Color.WHITE.getRGB());
    }

    private static void Method258(StringBuilder stringBuilder, String string) {
        stringBuilder.append(string).append("");
    }
}
