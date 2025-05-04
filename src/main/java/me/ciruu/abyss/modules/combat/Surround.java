package me.ciruu.abyss.modules.combat;

import java.util.function.Predicate;

import me.ciruu.abyss.*;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class285;
import me.ciruu.abyss.enums.Class451;
import me.ciruu.abyss.enums.Class481;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Surround
extends Module {
    private final Setting mode = new Setting("Mode", "Event mode", this, (Object)Class451.Update);
    private final Setting rotate = new Setting("Rotate", "Rotate", this, false);
    private final Setting centermode = new Setting("CenterMode", "Center mode", this, (Object)Class481.Motion);
    private final Setting blockspertick = new Setting("BlocksPerTick", "Blocks per tick", (Module)this, (Object)8, 1, 20);
    private final Setting toggleoffground = new Setting("ToggleOffGround", "ToggleOffGround", this, true);
    private final Setting keepenabled = new Setting("KeepEnabled", "Keep enabled after finish", this, true);
    private final Setting antifaceplace = new Setting("AntiFaceplace", "Anti faceplace", this, false);
    private final Setting stopAC = new Setting("StopAC", "Stop AutoCrystal while holefilling", this, true);
    private final Setting stepfix = new Setting("StepFix", "", this, false);
    private boolean Field1701 = false;
    private Vec3d Field1702 = Vec3d.ZERO;
    private int Field1703 = 0;
    private int Field1704 = 0;
    private int Field1705 = 0;
    private int Field1706 = -1;
    private double Field1707;
    Vec3d[] Field1708 = new Vec3d[]{new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(0.0, -1.0, 0.0)};
    Vec3d[] Field1709 = new Vec3d[]{new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(0.0, -1.0, 0.0)};
    @EventHandler
    private Listener Field1710 = new Listener<Class26>(this::Method2101, new Predicate[0]);
    @EventHandler
    private Listener Field1711 = new Listener<EventPlayerUpdate>(this::Method2102, new Predicate[0]);

    public Surround() {
        super("Surround", "Place obsidian around you to prevent getting damage from nearby end crystals.", Class11.COMBAT, "");
        this.addSetting(this.mode);
        this.addSetting(this.rotate);
        this.addSetting(this.centermode);
        this.addSetting(this.blockspertick);
        this.addSetting(this.toggleoffground);
        this.addSetting(this.keepenabled);
        this.addSetting(this.antifaceplace);
        this.addSetting(this.stopAC);
        this.addSetting(this.stepfix);
    }

    public void Method2104() {
        super.getEnable();
        if (Class155.Method544(BlockObsidian.class) == -1) {
            Class547.printChatMessage("You don't have obsidian in your hotbar! Toggling");
            this.Method2111();
            return;
        }
        if (Globals.mc.player == null) {
            this.Method2111();
            return;
        }
        this.Field1702 = this.Method2106(Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ);
        if (this.centermode.getValue() != Class481.None) {
            Globals.mc.player.motionX = 0.0;
            Globals.mc.player.motionZ = 0.0;
        }
        if (this.centermode.getValue() == Class481.Teleport) {
            Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.Field1702.x, this.Field1702.y, this.Field1702.z, true));
            Globals.mc.player.setPosition(this.Field1702.x, this.Field1702.y, this.Field1702.z);
        }
        this.Field1707 = Globals.mc.player.posY;
    }

    public void Method2107(boolean bl) {
        if (bl == false) {
            this.Method2111();
        }
        this.isEnabled();
        return;
    }

    private boolean wasOnGround = false; // Track the previous onGround state manually

    public void Method2111() {
        this.Field1706 = Globals.mc.player.inventory.currentItem;
        this.Method2112();

        // Toggle off ground check
        if (((Boolean)this.toggleoffground.getValue()).booleanValue() && !Globals.mc.player.onGround && !wasOnGround) {
            this.Method2111();
            Class547.printChatMessage("Toggling, you are off ground!");
            return;
        }

        // Step fix check
        if (((Boolean)this.stepfix.getValue()).booleanValue() && Globals.mc.player.onGround && wasOnGround && Globals.mc.player.posY - this.Field1707 > 0.99) {
            this.Method2111();
            return;
        }

        // Keep enabled check
        if (!((Boolean)this.keepenabled.getValue()).booleanValue() && this.Field1704 >= (Integer)this.blockspertick.getValue()) {
            this.Field1704 = 0;
            this.Method2111();
            return;
        }

        // Block placement logic
        int n = 0;
        while (n < (Integer)this.blockspertick.getValue()) {
            boolean bl = false;
            if (Globals.mc.player.posY < (double)Math.round(Globals.mc.player.posY)) {
                bl = true;
            }
            if (((Boolean)this.antifaceplace.getValue()).booleanValue()) {
                bl = true;
            }
            if (this.Field1705 >= (bl ? this.Field1709.length : this.Field1708.length)) {
                this.Field1705 = 0;
                break;
            }

            BlockPos blockPos = new BlockPos(bl ? this.Field1709[this.Field1705] : this.Field1708[this.Field1705]);
            BlockPos blockPos2 = new BlockPos(Globals.mc.player.getPositionVector()).add(blockPos.getX(), blockPos.getY(), blockPos.getZ());

            boolean bl2 = true;
            if (!Globals.mc.world.getBlockState(blockPos2).getMaterial().isReplaceable()) {
                bl2 = false;
            }
            for (Entity entity : Globals.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(blockPos2))) {
                bl2 = false;
                break;
            }

            // Check for obsidian in hotbar
            int n2 = Class155.Method544(BlockObsidian.class);
            if (n2 == -1) {
                Class547.printChatMessage("You don't have obsidian in your hotbar! Toggling");
                this.Method2111();
                return;
            }

            // Method to place the block
            if (!this.Method2113((EntityPlayer)Globals.mc.player)) {
                Globals.mc.player.inventory.currentItem = n2;
                Globals.mc.playerController.updateController();
            }

            // Stop anti-cheat check
            if (((Boolean)this.stopAC.getValue()).booleanValue()) {
                Class152.Field1104 = true;
            }

            // Place block
            if (bl2 && Class31.Method541(blockPos2, (Boolean)this.rotate.getValue(), (Boolean)this.rotate.getValue(), false)) {
                ++n;
            }

            ++this.Field1705;
        }

        // Reset anti-cheat flag
        Class152.Field1104 = false;

        // Restore previous item in hotbar
        Globals.mc.player.inventory.currentItem = this.Field1706;
        Globals.mc.playerController.updateController();

        // Increment block counter
        ++this.Field1704;

        // Track the player's current position
        if (Globals.mc.player != null) {
            this.Field1707 = Globals.mc.player.posY;
        }

        // Update the previous onGround state
        wasOnGround = Globals.mc.player.onGround;
    }


    public boolean Method2113(EntityPlayer entityPlayer) {
        BlockPos[] blockPosArray;
        Vec3d vec3d = Class29.getVector((Entity)Globals.mc.player, Globals.mc.getRenderPartialTicks());
        BlockPos blockPos = new BlockPos(vec3d.x, vec3d.y, vec3d.z);
        BlockPos blockPos2 = blockPos.north();
        BlockPos blockPos3 = blockPos.south();
        BlockPos blockPos4 = blockPos.east();
        BlockPos blockPos5 = blockPos.west();
        for (BlockPos blockPos6 : blockPosArray = new BlockPos[]{blockPos2, blockPos3, blockPos4, blockPos5}) {
            if (Class110.Method1043(blockPos6) == Class285.AlreadyBlockThere) continue;
            return false;
        }
        return true;
    }

    private void Method2112() {
        if (this.Field1702 != Vec3d.ZERO && this.centermode.getValue() == Class481.Motion) {
            double d = Math.abs(this.Field1702.x - Globals.mc.player.posX);
            double d2 = Math.abs(this.Field1702.z - Globals.mc.player.posZ);
            if (d <= 0.1 && d2 <= 0.1) {
                this.Field1702 = Vec3d.ZERO;
            } else {
                double d3 = this.Field1702.x - Globals.mc.player.posX;
                double d4 = this.Field1702.z - Globals.mc.player.posZ;
                Globals.mc.player.motionX = d3 / 2.0;
                Globals.mc.player.motionZ = d4 / 2.0;
            }
        }
    }

    public Vec3d Method2106(double d, double d2, double d3) {
        double d4 = Math.floor(d) + 0.5;
        double d5 = Math.floor(d2);
        double d6 = Math.floor(d3) + 0.5;
        return new Vec3d(d4, d5, d6);
    }

    private void Method2102(EventPlayerUpdate eventPlayerUpdate) {
        if (eventPlayerUpdate.getEra() != Class53.PRE) {
            return;
        }
        if (Globals.mc.player == null || Globals.mc.world == null) {
            return;
        }
        if (this.mode.getValue() == Class451.Update) {
            this.Method2111();
        }
    }

    private void Method2101(Class26 class26) {
        if (Globals.mc.player == null || Globals.mc.world == null) {
            return;
        }
        if (this.mode.getValue() == Class451.Tick) {
            this.Method2111();
        }
    }
}
