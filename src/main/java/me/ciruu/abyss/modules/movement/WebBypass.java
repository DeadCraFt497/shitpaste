package me.ciruu.abyss.modules.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.function.Predicate;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class26;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class217;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

public class WebBypass
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    public final Setting mode = new Setting("Mode", "", this, (Object)Class217.Motion);
    private final Setting sneakenable = new Setting("SneakEnable", "", (Module)this, (Object)false, this.mainwindow, this::Method664);
    private final Setting motionY = new Setting("MotionY", "", this, Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(20.0f), this.mainwindow, this::Method665);
    private final Setting timer = new Setting("Timer", "", this, Float.valueOf(20.0f), Float.valueOf(0.0f), Float.valueOf(100.0f), this.mainwindow, this::Method666);
    @EventHandler
    private final Listener Field497 = new Listener<Class26>(this::Method667, new Predicate[0]);

    public WebBypass() {
        super("WebBypass", "Prevents slowing while in contact to a web.", Class11.MOVEMENT, "");
        this.addSetting(this.mode);
        this.addSetting(this.sneakenable);
        this.addSetting(this.motionY);
        this.addSetting(this.timer);
    }

    public String Method669() {
        return ChatFormatting.WHITE + ((Class217)((Object)this.mode.getValue())).name();
    }

    public void Method670() {
        super.getDisable();
        Manager.Field298.Method337(1.0f);
    }

    private void Method671() {
        if (!Globals.mc.player.onGround) {
            Manager.Field298.Method337(((Float)this.timer.getValue()).floatValue());
        } else {
            Manager.Field298.Method337(1.0f);
        }
    }

    private void Method667(Class26 class26) {
        if (Globals.mc.player == null || Globals.mc.world == null) {
            Manager.Field298.Method337(1.0f);
            return;
        }
        if (this.mode.getValue() == Class217.Ghost) {
            Globals.mc.player.isInWeb = false;
        }
        if (Globals.mc.player.isInWeb) {
            if (!((Boolean)this.sneakenable.getValue()).booleanValue() || Globals.mc.gameSettings.keyBindSneak.pressed) {
                switch ((Class217)((Object)this.mode.getValue())) {
                    case Motion: {
                        Globals.mc.player.motionY = -((Float)this.motionY.getValue()).floatValue();
                        break;
                    }
                    case Strict: {
                        this.Method671();
                    }
                }
            }
            if (((Boolean)this.sneakenable.getValue()).booleanValue() && !Globals.mc.gameSettings.keyBindSneak.pressed) {
                Manager.Field298.Method337(1.0f);
            }
        }
    }

    private boolean Method666() {
        return this.mode.getValue() == Class217.Strict;
    }

    private boolean Method665() {
        return this.mode.getValue() == Class217.Motion;
    }

    private boolean Method664() {
        return this.mode.getValue() != Class217.Ghost;
    }
}
