package me.ciruu.abyss.modules.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.function.Predicate;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class29;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.enums.Class542;
import me.ciruu.abyss.events.player.EventPlayerTravel;
import me.ciruu.abyss.events.player.EventPlayerUpdateWalking;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

public class Flight
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    public final Setting mode = new Setting("Mode", "Modes of the speed to use", this, (Object)Class542.Vanilla);
    public final Setting speed = new Setting("Speed", "Speed to use", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(20.0f));
    public final Setting glide = new Setting("Glide", "Allows the glide speed under this to function.", this, false);
    public final Setting movingglide = new Setting("MovingGlide", "If no binds are pressed, this, should glide be enabled?", (Module)this, (Object)false, this.mainwindow, this.glide::getValue);
    public final Setting glidespeed = new Setting("GlideSpeed", "Glide speed of going down", this, Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(10.0f), this.mainwindow, this.glide::getValue);
    public final Setting onlyelytra = new Setting("OnlyElytra", "Only functions while on an elytra.", this, false);
    public final Setting antikick = new Setting("AntiKick", "Prevents you from getting kicked while flying by vanilla anticheat", this, true);
    @EventHandler
    private Listener Field2543 = new Listener<EventPlayerTravel>(this::Method3053, new Predicate[0]);
    @EventHandler
    private Listener Field2544 = new Listener<EventPlayerUpdateWalking>(this::Method3054, new Predicate[0]);

    public Flight() {
        super("Flight", "Allows you to fly.", Class11.MOVEMENT, "");
        this.addSetting(this.mode);
        this.addSetting(this.speed);
        this.addSetting(this.glide);
        this.addSetting(this.movingglide);
        this.addSetting(this.glidespeed);
        this.addSetting(this.onlyelytra);
        this.addSetting(this.antikick);
    }

    public String Method3056() {
        return ChatFormatting.WHITE + ((Class542)((Object)this.mode.getValue())).name();
    }

    private void Method3054(EventPlayerUpdateWalking eventPlayerUpdateWalking) {
        if (eventPlayerUpdateWalking.getClass53() != Class53.PRE) {
            return;
        }
        if (((Boolean)this.onlyelytra.getValue()).booleanValue() && !Globals.mc.player.isElytraFlying()) {
            return;
        }
        if (this.mode.getValue() == Class542.Vanilla) {
            Globals.mc.player.setVelocity(0.0, 0.0, 0.0);
            double[] dArray = Class29.getDouble10(((Float)this.speed.getValue()).floatValue());
            if (Globals.mc.player.movementInput.moveStrafe != 0.0f || Globals.mc.player.movementInput.moveForward != 0.0f) {
                Globals.mc.player.motionX = dArray[0];
                Globals.mc.player.motionZ = dArray[1];
            }
            if (Globals.mc.gameSettings.keyBindJump.isKeyDown()) {
                Globals.mc.player.motionY = ((Float)this.speed.getValue()).floatValue();
            }
            if (Globals.mc.gameSettings.keyBindSneak.isKeyDown()) {
                Globals.mc.player.motionY = -((Float)this.speed.getValue()).floatValue();
            }
        }
        if (((Boolean)this.antikick.getValue()).booleanValue() && Globals.mc.player.ticksExisted % 4 == 0) {
            Globals.mc.player.motionY += -0.04;
        }
    }

    private void Method3053(EventPlayerTravel eventPlayerTravel) {
        if (Globals.mc.player == null) {
            return;
        }
        if (((Boolean)this.onlyelytra.getValue()).booleanValue() && !Globals.mc.player.isElytraFlying()) {
            return;
        }
        if (this.mode.getValue() == Class542.Creative) {
            Globals.mc.player.setVelocity(0.0, 0.0, 0.0);
            double[] dArray = Class29.getDouble10(((Float)this.speed.getValue()).floatValue());
            if (Globals.mc.player.movementInput.moveStrafe != 0.0f || Globals.mc.player.movementInput.moveForward != 0.0f) {
                Globals.mc.player.motionX = dArray[0];
                Globals.mc.player.motionZ = dArray[1];
            }
            if (Globals.mc.player.movementInput.jump && !Globals.mc.player.isElytraFlying()) {
                Globals.mc.player.motionY = ((Float)this.speed.getValue()).floatValue();
            }
            if (Globals.mc.player.movementInput.sneak) {
                Globals.mc.player.motionY = -((Float)this.speed.getValue()).floatValue();
            }
            if (((Boolean)this.glide.getValue()).booleanValue() && (!((Boolean)this.movingglide.getValue()).booleanValue() || Globals.mc.player.movementInput.moveStrafe != 0.0f || Globals.mc.player.movementInput.moveForward != 0.0f)) {
                Globals.mc.player.motionY += (double)(-((Float)this.glidespeed.getValue()).floatValue());
            }
            eventPlayerTravel.cancel();
            Globals.mc.player.prevLimbSwingAmount = 0.0f;
            Globals.mc.player.limbSwingAmount = 0.0f;
            Globals.mc.player.limbSwing = 0.0f;
        }
    }
}
