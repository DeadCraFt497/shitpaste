package me.ciruu.abyss.modules.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.netty.util.internal.ConcurrentSet;
import java.util.function.Predicate;

import me.ciruu.abyss.*;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class571;
import me.ciruu.abyss.enums.Class572;
import me.ciruu.abyss.events.network.EventNetworkPostPacketEvent;
import me.ciruu.abyss.events.network.EventNetworkPrePacketEvent;
import me.ciruu.abyss.events.player.EventPlayerTravel;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.network.play.server.SPacketEntity;
import net.minecraft.network.play.server.SPacketEntityAttach;
import net.minecraft.network.play.server.SPacketMoveVehicle;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BoatFly
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting mode = new Setting("Mode", "", this, (Object)Class571.Packet);
    private final Setting speed = new Setting("Speed", "", (Module)this, (Object)Float.valueOf(2.0f), Float.valueOf(0.0f), Float.valueOf(40.0f));
    private final Setting yspeed = new Setting("YSpeed", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(10.0f));
    private final Setting glidespeed = new Setting("GlideSpeed", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(10.0f));
    private final Setting timer = new Setting("Timer", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(5.0f));
    private final Setting strict = new Setting("Strict", "", (Module)this, (Object)Class572.None, this.mainwindow, this::Method2858);
    private final Setting limit = new Setting("Limit", "", (Module)this, (Object)false, this.mainwindow, this::Method2859);
    private final Setting remount = new Setting("Remount", "", this, true);
    private final Setting cancel = new Setting("Cancel", "", this, true);
    private final Setting cancelrotations = new Setting("CancelRotations", "", this, true);
    private final Setting spoofpackets = new Setting("SpoofPackets", "", this, false);
    public final Setting offset = new Setting("Offset", "", this, Float.valueOf(0.1f), Float.valueOf(0.0f), Float.valueOf(10.0f), this.mainwindow, this.spoofpackets::getValue);
    private final Setting ongroundpacket = new Setting("OnGroundPacket", "", this, false);
    private final Setting phase = new Setting("Phase", "", (Module)this, (Object)true, this.mainwindow, this::Method2860);
    private final Setting gravity = new Setting("Gravity", "", (Module)this, (Object)true, this.mainwindow, this::Method2861);
    public final Setting renderscale = new Setting("RenderScale", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.01f), Float.valueOf(1.0f));
    private final Setting stop = new Setting("Stop", "", this, false);
    private final Setting enableticks = new Setting("EnableTicks", "", this, 10, 1, 100, this.mainwindow, this.stop::getValue);
    private final Setting waitticks = new Setting("WaitTicks", "", this, 10, 1, 100, this.mainwindow, this.stop::getValue);
    private final Setting stopunloaded = new Setting("StopUnloaded", "", this, true);
    private final Setting automount = new Setting("AutoMount", "", this, true);
    private final Setting debug = new Setting("Debug", "", this, true);
    private final Setting ylimit = new Setting("yLimit", "", this, false);
    private final Setting height = new Setting("Height", "", this, Float.valueOf(127.0f), Float.valueOf(0.0f), Float.valueOf(256.0f), this.mainwindow, this.ylimit::getValue);
    private final ConcurrentSet Field2263 = new ConcurrentSet();
    private int Field2264 = 0;
    private int Field2265 = 0;
    private boolean Field2266 = false;
    private boolean Field2267 = false;
    private boolean Field2268 = false;
    @EventHandler
    private final Listener Field2269 = new Listener<EventNetworkPostPacketEvent>(this::Method2862, 1000, new Predicate[0]);
    @EventHandler
    private final Listener Field2270 = new Listener<EventNetworkPrePacketEvent>(this::Method2863, new Predicate[0]);
    @EventHandler
    private final Listener Field2271 = new Listener<EventPlayerTravel>(this::Method2864, new Predicate[0]);

    public BoatFly() {
        super("BoatFly", "Allows you to fly with a boat.", Class11.MOVEMENT, "");
        this.addSetting(this.mode);
        this.addSetting(this.speed);
        this.addSetting(this.yspeed);
        this.addSetting(this.glidespeed);
        this.addSetting(this.timer);
        this.addSetting(this.limit);
        this.addSetting(this.remount);
        this.addSetting(this.cancel);
        this.addSetting(this.cancelrotations);
        this.addSetting(this.phase);
        this.addSetting(this.gravity);
        this.addSetting(this.renderscale);
        this.addSetting(this.stop);
        this.addSetting(this.enableticks);
        this.addSetting(this.waitticks);
        this.addSetting(this.stopunloaded);
        this.addSetting(this.automount);
        this.addSetting(this.debug);
        this.addSetting(this.ylimit);
        this.addSetting(this.height);
    }

    public void Method2866() {
        super.getEnable();
        if (Globals.mc.player == null || Globals.mc.player.world == null) {
            this.disable();
            return;
        }
        if (Globals.mc.player != null || Globals.mc.player.world != null) {
            this.isEnabled();
        }
        if (((Boolean)this.automount.getValue()).booleanValue()) {
            this.Method2868();
        }
    }

    public void Method2869() {
        super.getDisable();
        Manager.Field298.Method337(1.0f);
        this.Field2263.clear();
        this.Field2266 = false;
        if (Globals.mc.player == null) {
            return;
        }
        if (((Boolean)this.phase.getValue()).booleanValue() && this.mode.getValue() == Class571.Motion) {
            if (Globals.mc.player.getRidingEntity() != null) {
                Globals.mc.player.getRidingEntity().noClip = false;
            }
            Globals.mc.player.noClip = false;
        }
        if (Globals.mc.player.getRidingEntity() != null) {
            Globals.mc.player.getRidingEntity().setNoGravity(false);
        }
        Globals.mc.player.setNoGravity(false);
    }

    public void Method2870(boolean bl) {
        if (bl == false) {
            isEnabled();
        }
            this.Method2869();
    }

    public String Method2873() {
        return ChatFormatting.WHITE + ((Class571)((Object)this.mode.getValue())).name();
    }

    private float Method2874() {
        this.Field2268 = !this.Field2268;
        return this.Field2268 ? ((Float)this.offset.getValue()).floatValue() : -((Float)this.offset.getValue()).floatValue();
    }

    private void Method2875(CPacketVehicleMove cPacketVehicleMove) {
        this.Field2263.add((Object)cPacketVehicleMove);
        Globals.mc.player.connection.sendPacket((Packet)cPacketVehicleMove);
    }

    private void Method2876(Entity entity) {
        double d = entity.posY;
        BlockPos blockPos = new BlockPos(entity.posX, (double)((int)entity.posY), entity.posZ);
        for (int i = 0; i < 255; ++i) {
            if (!Globals.mc.world.getBlockState(blockPos).getMaterial().isReplaceable() || Globals.mc.world.getBlockState(blockPos).getBlock() == Blocks.WATER) {
                entity.posY = blockPos.getY() + 1;
                if (((Boolean)this.debug.getValue()).booleanValue()) {
                    Class547.printChatMessage("GroundY" + entity.posY);
                }
                this.Method2875(new CPacketVehicleMove(entity));
                entity.posY = d;
                break;
            }
            blockPos = blockPos.add(0, -1, 0);
        }
    }

    private void Method2868() {
        for (Entity entity : Globals.mc.world.loadedEntityList) {
            if (!(entity instanceof EntityBoat) || !(Globals.mc.player.getDistance(entity) < 5.0f)) continue;
            Globals.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity, EnumHand.MAIN_HAND));
            break;
        }
    }

    private void Method2864(EventPlayerTravel eventPlayerTravel) {
        if (Globals.mc.player == null || Globals.mc.world == null) {
            return;
        }
        if (Globals.mc.player.getRidingEntity() == null) {
            if (((Boolean)this.automount.getValue()).booleanValue()) {
                this.Method2868();
            }
            return;
        }
        if (((Boolean)this.phase.getValue()).booleanValue() && this.mode.getValue() == Class571.Motion) {
            Globals.mc.player.getRidingEntity().noClip = true;
            Globals.mc.player.getRidingEntity().setNoGravity(true);
            Globals.mc.player.noClip = true;
        }
        if (!this.Field2267) {
            Globals.mc.player.getRidingEntity().setNoGravity((Boolean)this.gravity.getValue() == false);
            Globals.mc.player.setNoGravity((Boolean)this.gravity.getValue() == false);
        }
        if (((Boolean)this.stop.getValue()).booleanValue()) {
            if (this.Field2264 > (Integer)this.enableticks.getValue() && !this.Field2266) {
                this.Field2264 = 0;
                this.Field2266 = true;
                this.Field2265 = (Integer)this.waitticks.getValue();
            }
            if (this.Field2265 > 0 && this.Field2266) {
                --this.Field2265;
                return;
            }
            if (this.Field2265 <= 0) {
                this.Field2266 = false;
            }
        }
        Entity entity = Globals.mc.player.getRidingEntity();
        if (((Boolean)this.debug.getValue()).booleanValue()) {
            Class547.printChatMessage("Y" + entity.posY);
            Class547.printChatMessage("Fall" + entity.fallDistance);
        }
        if ((!Globals.mc.world.isChunkGeneratedAt(entity.getPosition().getX() >> 4, entity.getPosition().getZ() >> 4) || entity.getPosition().getY() < 0) && ((Boolean)this.stopunloaded.getValue()).booleanValue()) {
            if (((Boolean)this.debug.getValue()).booleanValue()) {
                Class547.printChatMessage("Detected unloaded chunk!");
            }
            this.Field2267 = true;
            return;
        }
        if (((Float)this.timer.getValue()).floatValue() != 1.0f) {
            Manager.Field298.Method337(((Float)this.timer.getValue()).floatValue());
        }
        entity.rotationYaw = Globals.mc.player.rotationYaw;
        double[] dArray = Class29.getDouble10(((Float)this.speed.getValue()).floatValue());
        double d = entity.posX + dArray[0];
        double d2 = entity.posZ + dArray[1];
        double d3 = entity.posY;
        if ((!Globals.mc.world.isChunkGeneratedAt((int)d >> 4, (int)d2 >> 4) || entity.getPosition().getY() < 0) && ((Boolean)this.stopunloaded.getValue()).booleanValue()) {
            if (((Boolean)this.debug.getValue()).booleanValue()) {
                Class547.printChatMessage("Detected unloaded chunk!");
            }
            this.Field2267 = true;
            return;
        }
        this.Field2267 = false;
        entity.motionY = -(((Float)this.glidespeed.getValue()).floatValue() / 100.0f);
        if (this.mode.getValue() == Class571.Motion) {
            entity.motionX = dArray[0];
            entity.motionZ = dArray[1];
        }
        if (Globals.mc.player.movementInput.jump) {
            if (!((Boolean)this.ylimit.getValue()).booleanValue() || entity.posY <= (double)((Float)this.height.getValue()).floatValue()) {
                if (this.mode.getValue() == Class571.Motion) {
                    entity.motionY += (double)((Float)this.yspeed.getValue()).floatValue();
                } else {
                    d3 += (double)((Float)this.yspeed.getValue()).floatValue();
                }
            }
        } else if (Globals.mc.player.movementInput.sneak) {
            if (this.mode.getValue() == Class571.Motion) {
                entity.motionY += (double)(-((Float)this.yspeed.getValue()).floatValue());
            } else {
                d3 += (double)(-((Float)this.yspeed.getValue()).floatValue());
            }
        }
        if (Globals.mc.player.movementInput.moveStrafe == 0.0f && Globals.mc.player.movementInput.moveForward == 0.0f) {
            entity.motionX = 0.0;
            entity.motionZ = 0.0;
        }
        if (((Boolean)this.ongroundpacket.getValue()).booleanValue()) {
            this.Method2876(entity);
        }
        if (this.mode.getValue() != Class571.Motion) {
            entity.setPosition(d, d3, d2);
        }
        if (this.mode.getValue() == Class571.Packet) {
            this.Method2875(new CPacketVehicleMove(entity));
        }
        if (this.strict.getValue() == Class572.Invalid) {
            Globals.mc.player.connection.sendPacket((Packet)new CPacketClickWindow(0, 0, 0, ClickType.CLONE, ItemStack.EMPTY, (short) 0));
        }
        if (((Boolean)this.spoofpackets.getValue()).booleanValue()) {
            Vec3d vec3d = entity.getPositionVector().add(0.0, (double)this.Method2874(), 0.0);
            EntityBoat entityBoat = new EntityBoat((World)Globals.mc.world, vec3d.x, vec3d.y, vec3d.z);
            entityBoat.rotationYaw = entity.rotationYaw;
            entityBoat.rotationPitch = entity.rotationPitch;
            this.Method2875(new CPacketVehicleMove((Entity)entityBoat));
        }
        if (((Boolean)this.remount.getValue()).booleanValue()) {
            Globals.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity, EnumHand.MAIN_HAND));
        }
        eventPlayerTravel.cancel();
        ++this.Field2264;
    }

    private void Method2863(EventNetworkPrePacketEvent eventNetworkPrePacketEvent) {
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketDisconnect) {
            this.module.disable();
        }
        if (!Globals.mc.player.isRiding() || this.Field2267 || this.Field2266) {
            return;
        }
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketMoveVehicle && Globals.mc.player.isRiding() && ((Boolean)this.cancel.getValue()).booleanValue()) {
            eventNetworkPrePacketEvent.cancel();
        }
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketPlayerPosLook && Globals.mc.player.isRiding() && ((Boolean)this.cancel.getValue()).booleanValue()) {
            eventNetworkPrePacketEvent.cancel();
        }
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketEntity && ((Boolean)this.cancel.getValue()).booleanValue()) {
            eventNetworkPrePacketEvent.cancel();
        }
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketEntityAttach && ((Boolean)this.cancel.getValue()).booleanValue()) {
            eventNetworkPrePacketEvent.cancel();
        }
    }

    private void Method2862(EventNetworkPostPacketEvent eventNetworkPostPacketEvent) {
        if (Globals.mc.player == null || Globals.mc.world == null) {
            return;
        }

        Packet<?> packet = eventNetworkPostPacketEvent.getPacket();

        if (((packet instanceof CPacketPlayer.Rotation && (Boolean) this.cancelrotations.getValue())
                || packet instanceof CPacketInput) && Globals.mc.player.isRiding()) {
            eventNetworkPostPacketEvent.cancel();
        }

        if (this.Field2267 && packet instanceof CPacketVehicleMove) {
            eventNetworkPostPacketEvent.cancel();
        }

        if (!Globals.mc.player.isRiding() || this.Field2267 || this.Field2266) {
            return;
        }

        Entity entity = Globals.mc.player.getRidingEntity();
        if ((!Globals.mc.world.isChunkGeneratedAt(entity.getPosition().getX() >> 4, entity.getPosition().getZ() >> 4)
                || entity.getPosition().getY() < 0) && (Boolean) this.stopunloaded.getValue()) {
            return;
        }

        if (packet instanceof CPacketVehicleMove && (Boolean) this.limit.getValue() && this.mode.getValue() == Class571.Packet) {
            CPacketVehicleMove cPacketVehicleMove = (CPacketVehicleMove) packet;
            if (this.Field2263.contains(cPacketVehicleMove)) {
                this.Field2263.remove(cPacketVehicleMove);
            } else {
                eventNetworkPostPacketEvent.cancel();
            }
        }
    }


    private boolean Method2861() {
        return this.mode.getValue() != Class571.Motion;
    }

    private boolean Method2860() {
        return this.mode.getValue() == Class571.Motion;
    }

    private boolean Method2859() {
        return this.mode.getValue() == Class571.Packet;
    }

    private boolean Method2858() {
        return this.mode.getValue() != Class571.Motion;
    }
}
