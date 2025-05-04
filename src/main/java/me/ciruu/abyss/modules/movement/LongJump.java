package me.ciruu.abyss.modules.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Objects;
import java.util.function.Predicate;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class30;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class497;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.events.network.EventNetworkPrePacketEvent;
import me.ciruu.abyss.events.player.EventPlayerMove;
import me.ciruu.abyss.events.player.EventPlayerUpdateWalking;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.MovementInput;

public class LongJump
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting mode = new Setting("Mode", "", this, (Object)Class497.Normal);
    private final Setting boost = new Setting("Boost", "", (Module)this, (Object)Float.valueOf(4.48f), Float.valueOf(1.0f), Float.valueOf(20.0f));
    private final Setting timer = new Setting("Timer", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(5.0f));
    private final Setting autodisable = new Setting("AutoDisable", "", this, true);
    private final Setting jumpdisable = new Setting("JumpDisable", "", (Module)this, (Object)false, this.mainwindow, this::Method2148);
    private final Setting reduction = new Setting("Reduction", "", (Module)this, (Object)false, this.mainwindow, this::Method2149);
    private int Field1752;
    private double Field1753;
    private double Field1754;
    private boolean Field1755 = false;
    @EventHandler
    private Listener Field1756 = new Listener<EventNetworkPrePacketEvent>(this::Method2150, new Predicate[0]);
    @EventHandler
    private Listener Field1757 = new Listener<EventPlayerUpdateWalking>(this::Method2151, new Predicate[0]);
    @EventHandler
    private Listener Field1758 = new Listener<EventPlayerMove>(this::Method2152, new Predicate[0]);

    public LongJump() {
        super("LongJump", "Jump further.", Class11.MOVEMENT, "");
        this.addSetting(this.mode);
        this.addSetting(this.boost);
        this.addSetting(this.timer);
        this.addSetting(this.autodisable);
        this.addSetting(this.jumpdisable);
        this.addSetting(this.reduction);
    }

    public void Method2154() {
        super.getEnable();
        this.Field1752 = 0;
    }

    public void Method2155() {
        super.getDisable();
        Manager.Field298.Method337(1.0f);
    }

    public String Method2156() {
        return ChatFormatting.WHITE + ((Class497)((Object)this.mode.getValue())).name();
    }

    private void Method2157(EventPlayerMove eventPlayerMove) {
        if (Globals.mc.player.moveForward != 0.0f || Globals.mc.player.moveStrafing != 0.0f && !Class30.Method1835() && !Class30.Method1834()) {
            if (((Float)this.timer.getValue()).floatValue() != 1.0f) {
                Manager.Field298.Method337(((Float)this.timer.getValue()).floatValue());
            }
            switch (this.Field1752) {
                case 0: {
                    this.Field1753 = (double)((Float)this.boost.getValue()).floatValue() * this.Method2158();
                    this.Field1754 = 0.0;
                    ++this.Field1752;
                    break;
                }
                case 1: {
                    Globals.mc.player.motionY = 0.42;
                    eventPlayerMove.cancel();
                    eventPlayerMove.getDoubVal5(0.42);
                    this.Field1753 *= 2.149;
                    ++this.Field1752;
                    break;
                }
                case 2: {
                    double d = 0.66 * (this.Field1754 - this.Method2158());
                    this.Field1753 = this.Field1754 - d;
                    ++this.Field1752;
                    break;
                }
                case 3: {
                    if (Globals.mc.player.collidedVertically || Globals.mc.world.getCollisionBoxes((Entity)Globals.mc.player, Globals.mc.player.getEntityBoundingBox().offset(0.0, Globals.mc.player.motionY, 0.0)).size() > 0) {
                        this.Field1752 = 0;
                        this.Field1754 = 0.0;
                    }
                    this.Field1753 = this.Field1754 - this.Field1754 / 159.0;
                }
            }
        }
        this.Field1753 = Math.max(this.Method2158(), this.Field1753);
        this.Method2159(eventPlayerMove, this.Field1753);
    }

    private void Method2160(EventPlayerMove eventPlayerMove) {
        if (Globals.mc.player.moveForward != 0.0f || Globals.mc.player.moveStrafing != 0.0f && !Class30.Method1835() && !Class30.Method1834()) {
            if (((Float)this.timer.getValue()).floatValue() != 1.0f) {
                Manager.Field298.Method337(((Float)this.timer.getValue()).floatValue());
            }
            if (this.Field1752 == 0) {
                this.Field1753 = (double)((Float)this.boost.getValue()).floatValue() * this.Method2158();
            } else if (this.Field1752 == 1) {
                Globals.mc.player.motionY = 0.42;
                eventPlayerMove.cancel();
                eventPlayerMove.getDoubVal5(0.42);
                this.Field1753 *= 2.149;
            } else if (this.Field1752 == 2) {
                double d = 0.66 * (this.Field1754 - this.Method2158());
                this.Field1753 = this.Field1754 - d;
            } else {
                if (Globals.mc.player.collidedVertically || Globals.mc.world.getCollisionBoxes((Entity)Globals.mc.player, Globals.mc.player.getEntityBoundingBox().offset(0.0, Globals.mc.player.motionY, 0.0)).size() > 0) {
                    if (!((Boolean)this.reduction.getValue()).booleanValue()) {
                        this.Field1755 = true;
                    } else {
                        this.Field1752 = 0;
                    }
                    if (((Boolean)this.jumpdisable.getValue()).booleanValue()) {
                        isEnabled();
                    }
                }
                this.Field1753 = this.Field1754 - this.Field1754 / 159.0;
            }
            this.Field1753 = Math.max(this.Method2158(), this.Field1753);
            this.Method2159(eventPlayerMove, this.Field1753);
            ++this.Field1752;
        }
    }

    private void Method2159(EventPlayerMove eventPlayerMove, double d) {
        MovementInput movementInput = Globals.mc.player.movementInput;
        double d2 = movementInput.moveForward;
        double d3 = movementInput.moveStrafe;
        float f = Globals.mc.player.rotationYaw;
        eventPlayerMove.cancel();
        if (d2 == 0.0 && d3 == 0.0) {
            eventPlayerMove.getDoubVal4(0.0);
            eventPlayerMove.getDoubVal6(0.0);
        } else {
            if (d2 != 0.0) {
                if (d3 > 0.0) {
                    f += (float)(d2 > 0.0 ? -45 : 45);
                } else if (d3 < 0.0) {
                    f += (float)(d2 > 0.0 ? 45 : -45);
                }
                d3 = 0.0;
                if (d2 > 0.0) {
                    d2 = 1.0;
                } else if (d2 < 0.0) {
                    d2 = -1.0;
                }
            }
            eventPlayerMove.getDoubVal4(d2 * d * Math.cos(Math.toRadians(f + 90.0f)) + d3 * d * Math.sin(Math.toRadians(f + 90.0f)));
            eventPlayerMove.getDoubVal6(d2 * d * Math.sin(Math.toRadians(f + 90.0f)) - d3 * d * Math.cos(Math.toRadians(f + 90.0f)));
        }
    }

    private double Method2158() {
        double d = 0.2873;
        if (Globals.mc.player != null && Globals.mc.player.isPotionActive(MobEffects.SPEED)) {
            int n = Objects.requireNonNull(Globals.mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier();
            d *= 1.0 + 0.2 * (double)(n + 1);
        }
        return d;
    }

    private void Method2152(EventPlayerMove eventPlayerMove) {
        if (eventPlayerMove.getEra() != Class53.PRE) {
            return;
        }
        switch ((Class497)((Object)this.mode.getValue())) {
            case Normal: {
                this.Method2160(eventPlayerMove);
                break;
            }
            case Pause: {
                this.Method2157(eventPlayerMove);
            }
        }
    }

    private void Method2151(EventPlayerUpdateWalking eventPlayerUpdateWalking) {
        if (eventPlayerUpdateWalking.getClass53() != Class53.PRE) {
            return;
        }
        if (Globals.mc.player.moveForward != 0.0f || Globals.mc.player.moveStrafing != 0.0f) {
            double d = Globals.mc.player.posX - Globals.mc.player.prevPosX;
            double d2 = Globals.mc.player.posZ - Globals.mc.player.prevPosZ;
            this.Field1754 = Math.sqrt(d * d + d2 * d2);
        } else {
            eventPlayerUpdateWalking.cancel();
            this.Field1752 = 0;
            this.Field1754 = 0.0;
        }
        if (this.Field1755) {
            this.Field1755 = false;
            eventPlayerUpdateWalking.cancel();
            this.Field1752 = 0;
        }
    }

    private void Method2150(EventNetworkPrePacketEvent eventNetworkPrePacketEvent) {
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketPlayerPosLook) {
            this.Field1752 = 0;
            this.Field1754 = 0.0;
            if (((Boolean)this.autodisable.getValue()).booleanValue()) {
                this.disable();
            }
        }
    }

    private boolean Method2149() {
        return this.mode.getValue() == Class497.Normal;
    }

    private boolean Method2148() {
        return this.mode.getValue() == Class497.Normal;
    }
}
