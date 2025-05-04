package me.ciruu.abyss.modules.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.function.Predicate;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class56;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class393;
import me.ciruu.abyss.events.player.EventPlayerUpdateWalking;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

public class Step
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    public final Setting mode = new Setting("Mode", "Mode", (Module)this, (Object)Class393.Vanilla, this.mainwindow, Step::Method3591);
    private final Setting height = new Setting("Height", "", this, Float.valueOf(2.0f), Float.valueOf(1.0f), Float.valueOf(10.0f), this.mainwindow, this::Method3592);
    private final Setting heightNCP = new Setting("HeightNCP", "", this, 2, 1, 2, this.mainwindow, this::Method3593);
    private final Setting disable = new Setting("Disable", "", (Module)this, (Object)false, this.mainwindow, this::Method3594);
    private float Field2921 = 1.0f;
    private final double[] Field2922 = new double[]{0.42, 0.75};
    private final double[] Field2923 = new double[]{0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43};
    private double[] Field2924 = new double[0];
    private Minecraft Field2925 = Globals.mc;
    @EventHandler
    private Listener Field2926 = new Listener<EventPlayerUpdateWalking>(this::Method3595, new Predicate[0]);

    public Step() {
        super("Step", "Step up blocks.", Class11.MOVEMENT, "");
        this.addSetting(this.mode);
        this.addSetting(this.height);
        this.addSetting(this.heightNCP);
        this.addSetting(this.disable);
    }

    public void Method3597(boolean bl) {
        if (isEnabled() == true) {
            bl = false;
        }
        this.addSetting00();
    }

    public void addSetting01() {
        super.getEnable();
        if (this.mode.getValue() == Class393.Vanilla) {
            this.Field2921 = this.Field2925.player.stepHeight;
        }
    }

    public void addSetting00() {
        super.getDisable();
        if (this.mode.getValue() == Class393.Vanilla && this.Field2925.player != null) {
            this.Field2925.player.stepHeight = 0.6f;
        }
    }

    public String addSetting02() {
        return ChatFormatting.WHITE + ((Class393)((Object)this.mode.getValue())).name();
    }

    private double addSetting03() {
        boolean bl;
        boolean bl2 = bl = this.Field2925.player.onGround && this.Field2925.player.collidedHorizontally;
        if (!bl) {
            return 0.0;
        }
        double d = -1.0;
        float f = (float)Class56.Method3164();
        double d2 = (double)(-MathHelper.sin((float)f)) * 0.4;
        double d3 = (double)MathHelper.cos((float)f) * 0.4;
        AxisAlignedBB axisAlignedBB = this.Field2925.player.getEntityBoundingBox().offset(0.0, 0.05, 0.0).grow(0.05);
        axisAlignedBB = axisAlignedBB.setMaxY(axisAlignedBB.maxY + (double)((Integer)this.heightNCP.getValue()).floatValue());
        for (AxisAlignedBB axisAlignedBB2 : this.Field2925.world.getCollisionBoxes((Entity)this.Field2925.player, axisAlignedBB)) {
            if (!(axisAlignedBB2.maxY > d)) continue;
            d = axisAlignedBB2.maxY;
        }
        return (d -= this.Field2925.player.posY) > 0.0 && d <= (double)((Integer)this.heightNCP.getValue()).floatValue() ? d : 0.0;
    }

    private void Method3595(EventPlayerUpdateWalking eventPlayerUpdateWalking) {
        if (this.mode.getValue() == Class393.Vanilla) {
            this.Field2925.player.stepHeight = ((Float)this.height.getValue()).floatValue();
        } else if (this.mode.getValue() == Class393.NCP && this.Field2925.player.collidedHorizontally && this.Field2925.player.onGround && this.Field2925.player.fallDistance == 0.0f && !this.Field2925.player.isInWeb && !this.Field2925.player.isOnLadder() && !this.Field2925.player.movementInput.jump) {
            double d = this.addSetting03();
            if (d == 0.0) {
                return;
            }
            if (d <= 1.0) {
                eventPlayerUpdateWalking.getBool();
                this.Field2925.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.Field2925.player.posX, this.Field2925.player.posY + 0.42, this.Field2925.player.posZ, this.Field2925.player.onGround));
                this.Field2925.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.Field2925.player.posX, this.Field2925.player.posY + 0.75, this.Field2925.player.posZ, this.Field2925.player.onGround));
                this.Field2925.player.setPosition(this.Field2925.player.posX, this.Field2925.player.posY + 1.0, this.Field2925.player.posZ);
                if (((Boolean)this.disable.getValue()).booleanValue()) {
                    boolean bl = this.isEnabled();
                    bl = false;
                }
                return;
            }
            if (d <= (double)((Integer)this.heightNCP.getValue()).floatValue() && d <= 1.5) {
                eventPlayerUpdateWalking.getBool();
                this.Field2925.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.Field2925.player.posX, this.Field2925.player.posY + 0.42, this.Field2925.player.posZ, this.Field2925.player.onGround));
                this.Field2925.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.Field2925.player.posX, this.Field2925.player.posY + 0.75, this.Field2925.player.posZ, this.Field2925.player.onGround));
                this.Field2925.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.Field2925.player.posX, this.Field2925.player.posY + 1.0, this.Field2925.player.posZ, this.Field2925.player.onGround));
                this.Field2925.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.Field2925.player.posX, this.Field2925.player.posY + 1.16, this.Field2925.player.posZ, this.Field2925.player.onGround));
                this.Field2925.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.Field2925.player.posX, this.Field2925.player.posY + 1.23, this.Field2925.player.posZ, this.Field2925.player.onGround));
                this.Field2925.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.Field2925.player.posX, this.Field2925.player.posY + 1.2, this.Field2925.player.posZ, this.Field2925.player.onGround));
                this.Field2925.player.setPosition(this.Field2925.player.posX, this.Field2925.player.posY + d, this.Field2925.player.posZ);
                if (((Boolean)this.disable.getValue()).booleanValue()) {
                    boolean bl = this.isEnabled();
                    bl = false;
                }
                return;
            }
            if (d <= (double)((Integer)this.heightNCP.getValue()).floatValue()) {
                eventPlayerUpdateWalking.getBool();

                this.Field2925.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.Field2925.player.posX, this.Field2925.player.posY + 0.7800000000000002, this.Field2925.player.posZ, this.Field2925.player.onGround));
                this.Field2925.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.Field2925.player.posX, this.Field2925.player.posY + 0.42, this.Field2925.player.posZ, this.Field2925.player.onGround));
                this.Field2925.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.Field2925.player.posX, this.Field2925.player.posY + 0.63, this.Field2925.player.posZ, this.Field2925.player.onGround));
                this.Field2925.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.Field2925.player.posX, this.Field2925.player.posY + 0.51, this.Field2925.player.posZ, this.Field2925.player.onGround));
                this.Field2925.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.Field2925.player.posX, this.Field2925.player.posY + 0.9, this.Field2925.player.posZ, this.Field2925.player.onGround));
                this.Field2925.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.Field2925.player.posX, this.Field2925.player.posY + 1.21, this.Field2925.player.posZ, this.Field2925.player.onGround));
                this.Field2925.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.Field2925.player.posX, this.Field2925.player.posY + 1.45, this.Field2925.player.posZ, this.Field2925.player.onGround));
                this.Field2925.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.Field2925.player.posX, this.Field2925.player.posY + 1.43, this.Field2925.player.posZ, this.Field2925.player.onGround));
                this.Field2925.player.setPosition(this.Field2925.player.posX, this.Field2925.player.posY + d, this.Field2925.player.posZ);
                if (((Boolean)this.disable.getValue()).booleanValue()) {
                    this.disable();
                }
            }
        }
    }

    private boolean Method3594() {
        return this.mode.getValue() == Class393.NCP;
    }

    private boolean Method3593() {
        return this.mode.getValue() == Class393.NCP;
    }

    private boolean Method3592() {
        return this.mode.getValue() == Class393.Vanilla;
    }

    private static boolean Method3591() {
        return true;
    }
}
