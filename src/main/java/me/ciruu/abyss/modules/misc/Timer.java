package me.ciruu.abyss.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.function.Predicate;
import me.ciruu.abyss.Class22;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class461;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

public class Timer
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    public Setting autodisable = new Setting("AutoDisable", "", this, false);
    public Setting timer = new Setting("Timer(ms)", "", this, 250, 1, 2500, this.mainwindow, this::Method2531);
    public Setting mode = new Setting("Mode", "", this, (Object)Class461.NORMAL);
    public Setting speed = new Setting("Speed", "", (Module)this, (Object)Float.valueOf(4.0f), Float.valueOf(0.1f), Float.valueOf(20.0f));
    public Setting fast = new Setting("Fast", "", this, Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(100.0f), this.mainwindow, this::Method2532);
    public Setting fasttime = new Setting("FastTime", "", this, 20, 1, 500, this.mainwindow, this::Method2533);
    public Setting slowtime = new Setting("SlowTime", "", this, 20, 1, 500, this.mainwindow, this::Method2534);
    public Setting startfast = new Setting("StartFast", "", (Module)this, (Object)false, this.mainwindow, this::Method2535);
    public float Field2104 = 1.0f;
    private Class22 Field2105 = new Class22();
    private Class22 Field2106 = new Class22();
    private boolean Field2107 = false;
    @EventHandler
    private Listener Field2108 = new Listener<EventPlayerUpdate>(this::Method2536, new Predicate[0]);

    public Timer() {
        super("Timer", "Speed up the game.", Class11.MISC, "");
        this.addSetting(this.autodisable);
        this.addSetting(this.timer);
        this.addSetting(this.speed);
    }

    public void Method2538() {
        super.getEnable();
        this.Field2106.getNanoTime();
        this.Field2104 = ((Float)this.speed.getValue()).floatValue();
        if (!((Boolean)this.startfast.getValue()).booleanValue()) {
            this.Field2105.getNanoTime();
        }
    }

    public void Method2539() {
        super.getDisable();
        this.Field2104 = 1.0f;
        Manager.Field298.Method2540();
        this.Field2107 = false;
    }

    public String Method2541() {
        return ChatFormatting.WHITE + ((Float)this.speed.getValue()).toString();
    }

    private void Method2536(EventPlayerUpdate eventPlayerUpdate) {
        if (((Boolean)this.autodisable.getValue()).booleanValue() && this.Field2106.getLongNanoTime(((Integer)this.timer.getValue()).intValue())) {
            this.disable();
            return;
        }
        if (this.mode.getValue() == Class461.NORMAL) {
            this.Field2104 = ((Float)this.speed.getValue()).floatValue();
            return;
        }
        if (!this.Field2107 && this.Field2105.getBooleanTime3(((Integer)this.slowtime.getValue()).intValue())) {
            this.Field2107 = true;
            this.Field2104 = ((Float)this.fast.getValue()).floatValue();
            this.Field2105.getNanoTime();
        }
        if (this.Field2107 && this.Field2105.getBooleanTime3(((Integer)this.fasttime.getValue()).intValue())) {
            this.Field2107 = false;
            this.Field2104 = ((Float)this.speed.getValue()).floatValue();
            this.Field2105.getNanoTime();
        }
    }

    private boolean Method2535() {
        return this.mode.getValue() == Class461.SWITCH;
    }

    private boolean Method2534() {
        return this.mode.getValue() == Class461.SWITCH;
    }

    private boolean Method2533() {
        return this.mode.getValue() == Class461.SWITCH;
    }

    private boolean Method2532() {
        return this.mode.getValue() == Class461.SWITCH;
    }

    private boolean Method2531() {
        return (Boolean)this.autodisable.getValue();
    }
}
