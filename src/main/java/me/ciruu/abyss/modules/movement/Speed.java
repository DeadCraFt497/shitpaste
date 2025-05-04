package me.ciruu.abyss.modules.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.function.Predicate;
import me.ciruu.abyss.Class165;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class334;
import me.ciruu.abyss.Class51;
import me.ciruu.abyss.Class516;
import me.ciruu.abyss.Class517;
import me.ciruu.abyss.Class518;
import me.ciruu.abyss.Class519;
import me.ciruu.abyss.Class52;
import me.ciruu.abyss.Class520;
import me.ciruu.abyss.Class521;
import me.ciruu.abyss.Class522;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class515;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.events.network.EventNetworkPrePacketEvent;
import me.ciruu.abyss.events.player.EntityPlayerAttackEntityFrom;
import me.ciruu.abyss.events.player.EventPlayerJump;
import me.ciruu.abyss.events.player.EventPlayerMove;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.events.player.EventPlayerUpdateWalking;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class Speed
extends Module {
    private static final Setting mainwindow = new Setting("Main", "", null, new Class25("Main Settings"));
    public static final Setting mode = new Setting("Mode", "The mode of speed to use", null, (Object)Class515.Strafe);
    public static final Setting usetimer = new Setting("UseTimer", "Uses timer to go faster", null, (Object)false, mainwindow, Speed::Method2308);
    public static final Setting autosprint = new Setting("AutoSprint", "Automatically sprints for you", null, (Object)false, mainwindow, Speed::Method2307);
    public static final Setting speedinwater = new Setting("SpeedInWater", "Speeds in water", null, (Object)false, mainwindow, Speed::Method2306);
    public static final Setting autojump = new Setting("AutoJump", "Automatically jumps", null, (Object)false, mainwindow, Speed::Method2305);
    public static final Setting strict = new Setting("Strict", "Strict mode, use this for when hauses patch comes back for strafe", null, (Object)false, mainwindow, Speed::Method2304);
    public static final Setting damageboost = new Setting("DamageBoost", "Boosts you if you take any damage", null, (Object)false, mainwindow, Speed::Method2303);
    public static final Setting boost = new Setting("Boost", "Boosts you if you take any damage", null, Float.valueOf(1.4f), Float.valueOf(1.0f), Float.valueOf(5.0f), mainwindow, Speed::Method2302);
    public static final Setting strictspeed = new Setting("StrictSpeed", "", null, Float.valueOf(0.257f), Float.valueOf(0.0f), Float.valueOf(0.272f), mainwindow, Speed::Method2301);
    public static final Setting strafe2bspeed = new Setting("Strafe2bSpeed", "", null, Float.valueOf(1.6f), Float.valueOf(0.0f), Float.valueOf(3.0f), mainwindow, Speed::Method2300);
    public static final Setting speedboost = new Setting("SpeedBoost", "", null, Float.valueOf(1.8f), Float.valueOf(0.0f), Float.valueOf(3.0f), mainwindow, Speed::Method2299);
    public static final Setting reduction = new Setting("Reduction", "", null, Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(5.0f), mainwindow, Speed::Method2298);
    public static final Setting yportspeed = new Setting("YPortSpeed", "", null, Float.valueOf(0.6f), Float.valueOf(0.1f), Float.valueOf(2.0f), mainwindow, Speed::Method2297);
    private Class52 Field1919;
    private Class516 Field1920 = new Class516();
    private Class517 Field1921 = new Class517();
    private Class518 Field1922 = new Class518();
    private Class519 Field1923 = new Class519();
    private Class334 Field1924 = new Class334();
    private Class520 Field1925 = new Class520();
    private Class521 Field1926 = new Class521();
    private Class51 Field1927 = new Class51();
    private Class165 Field1928 = new Class165();
    @EventHandler
    private Listener Field1929 = new Listener<EventPlayerUpdateWalking>(this::Method2285, new Predicate[0]);
    @EventHandler
    private Listener Field1930 = new Listener<EventPlayerJump>(this::Method2286, new Predicate[0]);
    @EventHandler
    private Listener Field1931 = new Listener<EventPlayerUpdate>(this::Method2287, new Predicate[0]);
    @EventHandler
    private Listener Field1932 = new Listener<EventPlayerMove>(this::Method2288, new Predicate[0]);
    @EventHandler
    private Listener Field1933 = new Listener<EventNetworkPrePacketEvent>(this::Method2289, 4000, new Predicate[0]);
    @EventHandler
    private Listener Field1934 = new Listener<Class522>(this::Method2290, new Predicate[0]);
    @EventHandler
    private Listener Field1935 = new Listener<EntityPlayerAttackEntityFrom>(this::Method2291, new Predicate[0]);

    public Speed() {
        super("Speed", "Allows you go fast.", Class11.MOVEMENT, "");
        this.addSetting(mode);
        this.addSetting(usetimer);
        this.addSetting(autosprint);
        this.addSetting(speedinwater);
        this.addSetting(autojump);
        this.addSetting(strict);
        this.addSetting(damageboost);
        this.addSetting(boost);
        this.addSetting(strictspeed);
        this.addSetting(speedboost);
        this.addSetting(reduction);
        this.addSetting(yportspeed);
        this.Method2293();
    }

    private void Method2293() {
        switch ((Class515)((Object)mode.getValue())) {
            case DamageTest: {
                this.Field1919 = this.Field1920;
                break;
            }
            case Strafe: {
                this.Field1919 = this.Field1921;
                break;
            }
            case LowHop: {
                this.Field1919 = this.Field1922;
                break;
            }
            case Boost: {
                this.Field1919 = this.Field1923;
                break;
            }
            case OnGround: {
                this.Field1919 = this.Field1924;
                break;
            }
            case Strafe2b: {
                this.Field1919 = this.Field1925;
                break;
            }
            case GayHop: {
                this.Field1919 = this.Field1926;
                break;
            }
            case StrafeBoost: {
                this.Field1919 = this.Field1927;
                break;
            }
            case YPort: {
                this.Field1919 = this.Field1928;
            }
        }
    }

    public void Method2294() {
        super.getEnable();
        this.Method2293();
        this.Field1919.Method124();
    }

    public void Method2295() {
        super.getDisable();
        this.Field1919.Method125();
        Manager.Field298.Method337(1.0f);
    }

    public String Method2296() {
        return ChatFormatting.WHITE + ((Class515)((Object)mode.getValue())).name();
    }

    private void Method2291(EntityPlayerAttackEntityFrom entityPlayerAttackEntityFrom) {
        if (Anchor.Field473) {
            return;
        }
        this.Field1919.Method121(entityPlayerAttackEntityFrom);
    }

    private void Method2290(Class522 class522) {
        if (Anchor.Field473) {
            return;
        }
        this.Method2293();
    }

    private void Method2289(EventNetworkPrePacketEvent eventNetworkPrePacketEvent) {
        SPacketEntityVelocity sPacketEntityVelocity;
        if (Anchor.Field473) {
            return;
        }
        if (eventNetworkPrePacketEvent.getEra() == Class53.PRE && eventNetworkPrePacketEvent.getPacket() instanceof SPacketPlayerPosLook) {
            this.Field1919.Method120();
        }
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketEntityVelocity && (sPacketEntityVelocity = (SPacketEntityVelocity)eventNetworkPrePacketEvent.getPacket()).getEntityID() == Globals.mc.player.getEntityId()) {
            this.Field1919.Method122((SPacketEntityVelocity)eventNetworkPrePacketEvent.getPacket());
        }
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketExplosion) {
            this.Field1919.Method123((SPacketExplosion)eventNetworkPrePacketEvent.getPacket());
        }
    }

    private void Method2288(EventPlayerMove eventPlayerMove) {
        if (Anchor.Field473) {
            return;
        }
        this.Field1919.Method119(eventPlayerMove);
    }

    private void Method2287(EventPlayerUpdate eventPlayerUpdate) {
        if (Anchor.Field473) {
            return;
        }
        this.Field1919.Method116(eventPlayerUpdate);
    }

    private void Method2286(EventPlayerJump eventPlayerJump) {
        if (Anchor.Field473) {
            return;
        }
        this.Field1919.Method118(eventPlayerJump);
    }

    private void Method2285(EventPlayerUpdateWalking eventPlayerUpdateWalking) {
        if (Anchor.Field473) {
            return;
        }
        this.Field1919.Method117(eventPlayerUpdateWalking);
    }

    private static boolean Method2297() {
        return mode.getValue() == Class515.YPort;
    }

    private static boolean Method2298() {
        return mode.getValue() == Class515.StrafeBoost;
    }

    private static boolean Method2299() {
        return mode.getValue() == Class515.StrafeBoost;
    }

    private static boolean Method2300() {
        return mode.getValue() == Class515.Strafe2b;
    }

    private static boolean Method2301() {
        return mode.getValue() == Class515.Strafe && (Boolean)strict.getValue() != false;
    }

    private static boolean Method2302() {
        return (Boolean)damageboost.getValue() != false && mode.getValue() == Class515.DamageTest;
    }

    private static boolean Method2303() {
        return mode.getValue() == Class515.DamageTest;
    }

    private static boolean Method2304() {
        return mode.getValue() == Class515.DamageTest || mode.getValue() == Class515.Strafe;
    }

    private static boolean Method2305() {
        return mode.getValue() == Class515.DamageTest;
    }

    private static boolean Method2306() {
        return mode.getValue() == Class515.DamageTest;
    }

    private static boolean Method2307() {
        return mode.getValue() == Class515.DamageTest;
    }

    private static boolean Method2308() {
        return mode.getValue() == Class515.DamageTest || mode.getValue() == Class515.LowHop || mode.getValue() == Class515.GayHop;
    }
}
