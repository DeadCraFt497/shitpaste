package me.ciruu.abyss.modules.hud;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import me.ciruu.abyss.Class26;
import me.ciruu.abyss.Class35;
import me.ciruu.abyss.Class36;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class283;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.modules.client.WebChat;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

public class WebChatList
extends Module {
    private final Setting mode = new Setting("Mode", "", this, (Object)Class283.All);
    private final Setting x = new Setting("X", "X", (Module)this, (Object)5, 0, 1000);
    private final Setting y = new Setting("Y", "Y", (Module)this, (Object)20, 0, 1000);
    private final Setting step = new Setting("Step", "Y", (Module)this, (Object)12, 6, 15);
    private final Setting lineWidth = new Setting("LineWidth", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(5.0f));
    private final HashMap Field774 = new HashMap();
    private final Color Field775 = new Color(0, 0, 0, 128);
    private final Color Field776 = new Color(0, 0, 0, 255);
    private int Field777 = Class36.Method259("Abyss Team5");
    private int Field778 = 0;
    @EventHandler
    private final Listener Field779 = new Listener<Class26>(this::Method1024, new Predicate[0]);
    @EventHandler
    private final Listener Field780 = new Listener<Class35>(this::Method1025, new Predicate[0]);

    public WebChatList() {
        super("WebChatList", "", Class11.HUD, "");
        this.addSetting(this.mode);
        this.addSetting(this.x);
        this.addSetting(this.y);
        this.addSetting(this.step);
        this.addSetting(this.lineWidth);
    }

    public void Method1027() {
        super.getEnable();
    }

    private void Method1028() {
        this.Field774.clear();
        this.Field774.putAll(((WebChat)Manager.moduleManager.getModuleByClass(WebChat.class)).getMappedWebChat());
        this.Field778 = (Integer)this.step.getValue();
        block4:
        for (Map.Entry entry = (Map.Entry) this.Field774.entrySet();;) {
            switch ((Class283)((Object)this.mode.getValue())) {
                case Connected: {
                    if (((Boolean)entry.getValue()).booleanValue()) break;
                    continue block4;
                }
                case Disconnected: {
                    if (!((Boolean)entry.getValue()).booleanValue()) break;
                    continue block4;
                }
            }
            this.Field778 += ((Integer)this.step.getValue()).intValue();
            int n = Class36.Method259((String)entry.getKey()) + 20;
            if (n <= this.Field777) continue;
            this.Field777 = n;
        }
    }

    private void Method1030() {
        Class50.Method802(((Integer)this.x.getValue()).intValue(), ((Integer)this.y.getValue()).intValue(), (Integer)this.x.getValue() + this.Field777, ((Integer)this.y.getValue()).intValue(), ((Float)this.lineWidth.getValue()).floatValue(), this.Field776.getRGB());
        Class50.Method802((Integer)this.x.getValue() + this.Field777, ((Integer)this.y.getValue()).intValue(), (Integer)this.x.getValue() + this.Field777, (Integer)this.y.getValue() + this.Field778, ((Float)this.lineWidth.getValue()).floatValue(), this.Field776.getRGB());
        Class50.Method802(((Integer)this.x.getValue()).intValue(), (Integer)this.y.getValue() + this.Field778, (Integer)this.x.getValue() + this.Field777, (Integer)this.y.getValue() + this.Field778, ((Float)this.lineWidth.getValue()).floatValue(), this.Field776.getRGB());
        Class50.Method802(((Integer)this.x.getValue()).intValue(), (Integer)this.y.getValue() + this.Field778, ((Integer)this.x.getValue()).intValue(), ((Integer)this.y.getValue()).intValue(), ((Float)this.lineWidth.getValue()).floatValue(), this.Field776.getRGB());
    }

    private void Method1025(Class35 class35) {
        if (Globals.mc.player == null || Globals.mc.world == null || this.Field774.isEmpty()) {
            return;
        }
        Class50.Method92(((Integer)this.x.getValue()).intValue(), ((Integer)this.y.getValue()).intValue(), (Integer)this.x.getValue() + this.Field777, (Integer)this.y.getValue() + this.Field778, this.Field775.getRGB());
        Class36.Method557("Abyss Team", (float)((Integer)this.x.getValue()).intValue() + (float)this.Field777 / 2.0f, (float)((Integer)this.y.getValue()).intValue() + ((float)((Integer)this.step.getValue()).intValue() / 2.0f - (float)Class36.Method260() / 3.0f), Color.WHITE.getRGB());
        int n = 1;
        block4: for (Map.Entry entry = (Map.Entry) this.Field774.entrySet();;) {
            switch ((Class283)((Object)this.mode.getValue())) {
                case Connected: {
                    if (((Boolean)entry.getValue()).booleanValue()) break;
                    continue block4;
                }
                case Disconnected: {
                    if (!((Boolean)entry.getValue()).booleanValue()) break;
                    continue block4;
                }
            }
            Class36.Method63((String)entry.getKey(), (Integer)this.x.getValue() + 2, (float)((Integer)this.y.getValue() + (Integer)this.step.getValue() * n) + ((float)((Integer)this.step.getValue()).intValue() / 2.0f - (float)Class36.Method260() / 3.0f), Color.WHITE.getRGB());
            Class50.Method802(((Integer)this.x.getValue()).intValue(), (Integer)this.y.getValue() + (Integer)this.step.getValue() * n, (Integer)this.x.getValue() + this.Field777, (Integer)this.y.getValue() + (Integer)this.step.getValue() * n, ((Float)this.lineWidth.getValue()).floatValue(), this.Field776.getRGB());
            int n2 = (Integer)this.step.getValue() / 2 - 2;
            Class50.Method891((Integer)this.x.getValue() + this.Field777 - n2 - 2, (Integer)this.y.getValue() + (Integer)this.step.getValue() * n + n2 + 2, n2, (Boolean)entry.getValue() != false ? Color.GREEN : Color.RED);
            this.Method1030();
            ++n;
        }
    }

    private void Method1024(Class26 class26) {
        if (Globals.mc.player == null || Globals.mc.world == null) {
            System.out.println("Abyss: Error with WebChat. Try connecting to a server monkey.");
            return;
        }
        if (Globals.mc.world.getTotalWorldTime() % 20L == 0L) {
            this.Method1028();
        }
    }
}
