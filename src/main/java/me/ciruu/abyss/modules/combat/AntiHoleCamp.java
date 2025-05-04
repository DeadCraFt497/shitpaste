package me.ciruu.abyss.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import me.ciruu.abyss.*;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class194;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.modules.render.Freecam;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPiston;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class AntiHoleCamp
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting checkhole = new Setting("CheckHole", "", this, true);
    private final Setting redstone = new Setting("RedStone", "", this, Class194.Torch);
    private final Setting placeobsidian = new Setting("PlaceObby", "", this, true, this.mainwindow, this::getRedstoneReturnType);
    private final Setting range = new Setting("Range", "", this, Float.valueOf(6.0f), Float.valueOf(0.0f), Float.valueOf(6.0f));
    private final Setting raytrace = new Setting("RayTrace", "", this, false);
    private final Setting rotate = new Setting("Rotate", "", this, false);
    private EntityPlayer currentTarget;
    private BlockPos blockPos1;
    private int int1 = -1;
    private int int2 = -1;
    private int int3 = -1;
    private int int4 = -1;
    private BlockPos blockPos2;
    private final float[] fl_1 = new float[2];
    private boolean bool = false;
    private BlockPos blockPos3 = null;
    @EventHandler
    private final Listener listen1 = new Listener<Class26>(this::getError1ReturnType);
    @EventHandler
    private final Listener listen2 = new Listener<Class66>(this::getColor);

    public AntiHoleCamp() {
        super("AntiHoleCamp", "Pulls players out of their holes.", Class11.COMBAT, "");
        this.addSetting(this.checkhole);
        this.addSetting(this.redstone);
        this.addSetting(this.placeobsidian);
        this.addSetting(this.range);
        this.addSetting(this.raytrace);
        this.addSetting(this.rotate);
        if (isEnabled() == true) {
            getWholeCampFunc();
            getWholeCampMainHandFunc();
        }
    }

    public void getCurrentTarget() {
        super.getEnable();
        this.currentTarget = Class30.Method518();
        if (this.currentTarget == null) {
            Class547.printChatMessage("Can't find any target!");
            this.getError1ReturnType();
            return;
        }
        this.blockPos1 = Class30.Method209(this.currentTarget);
        if (!this.getError2()) {
            this.getError1ReturnType();
        }
    }

    public void getManagerFlVal() {
        super.getDisable();
        if (this.int1 != -1) {
            Class155.Method522(this.int1, false);
        }
        if (this.bool) {
            this.bool = false;
            Manager.Field456.Method523(this.fl_1[0], this.fl_1[1]);
        }
    }

    private void getErrorReturnedType() {
        this.getError();
    }

    private void getError() {
        this.blockPos2 = this.getRayTraceFunc();
        if (this.blockPos2 == null) {
            Class547.printChatMessage("Can't place piston!");
            this.getError1ReturnType();
            return;
        }
        switch ((Class194) this.redstone.getValue()) {
            case Block: {
                this.getWholeCampFunc();
                break;
            }
            case Torch: {
                this.getWholeCampMainHandFunc();
            }
        }
    }

    private void getWholeCampFunc() {
        List list = AntiHoleCamp.getBlockPosList(this.blockPos2);
        if (list.isEmpty()) {
            return;
        }

        // Use a standard for loop with an index
        for (int i = 0; i < list.size(); i++) {
            EnumFacing enumFacing = (EnumFacing) list.get(i);
            BlockPos blockPos = this.blockPos2.offset(enumFacing);

            // Check the distance and raytrace conditions
            if (!(Globals.mc.player.getDistanceSq(blockPos) < Class29.getDouble6(((Float)this.range.getValue()).floatValue())) || Class31.Method533(blockPos, this.raytrace.getValue()) != 3) {
                continue;
            }

            this.blockPos3 = blockPos;
            this.getRotateFunc(this.blockPos2, blockPos, false, enumFacing);
            break;  // Exit the loop after the first valid blockPos is found
        }
    }


    private void getWholeCampMainHandFunc() {
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        BlockPos blockPos = this.blockPos2.north();
        BlockPos blockPos2 = this.blockPos2.west();
        BlockPos blockPos3 = this.blockPos2.south();
        BlockPos blockPos4 = this.blockPos2.east();
        arrayList.add(blockPos);
        arrayList.add(blockPos2);
        arrayList.add(blockPos3);
        arrayList.add(blockPos4);
        boolean bl = false;
        for (BlockPos blockPos5 : arrayList) {
            if (!this.getBlockState(blockPos5) || Globals.mc.world.getBlockState(blockPos5.down()).getMaterial().isReplaceable()) continue;
            this.getRotateFunc(this.blockPos2, blockPos5, true, EnumFacing.UP);
            bl = true;
            break;
        }
        if (!bl && ((Boolean)this.placeobsidian.getValue()).booleanValue()) {
            for (BlockPos blockPos5 : arrayList) {
                if (!this.getBlockState(blockPos5.down()) || !this.getBlockState(blockPos5)) continue;
                Class155.Method522(this.int4, false);
                Class31.Method536(blockPos5.down(), EnumHand.MAIN_HAND, this.rotate.getValue(), true, Globals.mc.player.isSneaking());
                this.getRotateFunc(this.blockPos2, blockPos5, true, EnumFacing.UP);
                break;
            }
        }
    }

    private boolean getBlockState(BlockPos blockPos) {
        return !this.getBlockPos(blockPos) && Globals.mc.world.getBlockState(blockPos).getMaterial().isReplaceable();
    }

    private boolean getBlockPos(BlockPos blockPos) {
        for (Entity entity : Globals.mc.world.loadedEntityList) {
            if (entity.equals(Globals.mc.player) || entity instanceof EntityItem || entity instanceof EntityXPOrb || !new AxisAlignedBB(blockPos).intersects(entity.getEntityBoundingBox())) continue;
            return true;
        }
        return false;
    }

    private void getRotateFunc(BlockPos blockPos, BlockPos blockPos2, boolean bl, EnumFacing enumFacing) {
        Class155.Method522(this.int2, false);
        this.fl_1[0] = Globals.mc.player.rotationYaw;
        this.fl_1[1] = Globals.mc.player.rotationPitch;
        this.bool = true;
        double d = Class84.Method538((float)blockPos.getX() + 0.5f, (float)blockPos.getY() + 0.5f, (float)blockPos.getZ() + 0.5f, this.currentTarget)[0];
        Manager.Field456.Method523(Class84.Method540(Class84.Method539((float)d)), 0.0f);
        Globals.mc.player.connection.sendPacket(new CPacketPlayer.Rotation((float)Class84.Method540(Class84.Method539((float)d)), 0.0f, true));
        Class31.Method536(blockPos, EnumHand.MAIN_HAND, this.rotate.getValue(), true, Globals.mc.player.isSneaking());
        Class155.Method522(this.int3, false);
        Class31.Method541(blockPos2, this.rotate.getValue(), this.rotate.getValue(), false);
        Class547.printChatMessage(ChatFormatting.GREEN + "Done!");
        this.getError1ReturnType();
    }

    private BlockPos getRayTraceFunc() {
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        BlockPos blockPos = this.blockPos1.north().up();
        BlockPos blockPos2 = this.blockPos1.west().up();
        BlockPos blockPos3 = this.blockPos1.south().up();
        BlockPos blockPos4 = this.blockPos1.east().up();
        arrayList.add(blockPos);
        arrayList.add(blockPos2);
        arrayList.add(blockPos3);
        arrayList.add(blockPos4);
        BlockPos blockPos5 = null;
        ArrayList<BlockPos> arrayList2 = new ArrayList<BlockPos>();
        for (BlockPos blockPos6 : arrayList) {
            if (Class31.Method533(blockPos6, this.raytrace.getValue()) != 3 || Globals.mc.player.getDistanceSq(blockPos6) > Class29.getDouble6(((Float)this.range.getValue()).floatValue())) continue;
            arrayList2.add(blockPos6);
        }
        double d = 5.0;
        if (!arrayList2.isEmpty()) {
            for (BlockPos blockPos7 : arrayList2) {
                Vec3d vec3d = new Vec3d(blockPos7);
                if (!(this.currentTarget.getPositionVector().distanceTo(vec3d.add(0.5, 0.0, 0.5)) < d)) continue;
                blockPos5 = blockPos7;
            }
        }
        return blockPos5;
    }

    public static List getBlockPosList(BlockPos blockPos) {
        ArrayList<EnumFacing> arrayList = new ArrayList<EnumFacing>();
        for (EnumFacing enumFacing : EnumFacing.values()) {
            BlockPos blockPos2 = blockPos.offset(enumFacing);
            IBlockState iBlockState = Globals.mc.world.getBlockState(blockPos2);
            if (!iBlockState.getMaterial().isReplaceable()) continue;
            arrayList.add(enumFacing);
        }
        return arrayList;
    }

    private boolean getError1() {
        if (Globals.mc.player == null || Globals.mc.world == null || Manager.moduleManager.isModuleEnabled(AutoTrap.class) || Manager.moduleManager.isModuleEnabled(Freecam.class)) {
            return false;
        }
        if (this.currentTarget != null && !Class30.Method543(this.currentTarget) && ((Boolean)this.checkhole.getValue()).booleanValue()) {
            Class547.printChatMessage("Target it's not in a hole!");
            return false;
        }
        return true;
    }

    private boolean getError2() {
        this.int2 = Class155.Method544(ItemPiston.class);
        if (this.int2 == -1) {
            Class547.printChatMessage("Could not find pistons in hotbar!");
            return false;
        }
        switch ((Class194) this.redstone.getValue()) {
            case Torch: {
                this.int3 = Class155.Method545(Blocks.REDSTONE_TORCH);
                break;
            }
            case Block: {
                this.int3 = Class155.Method545(Blocks.REDSTONE_BLOCK);
            }
        }
        if (this.int3 == -1) {
            Class547.printChatMessage("Could not find redstone in hotbar!");
            return false;
        }
        if (((Boolean)this.placeobsidian.getValue()).booleanValue() && this.redstone.getValue() == Class194.Torch) {
            this.int4 = Class155.Method544(BlockObsidian.class);
            if (this.int4 == -1) {
                Class547.printChatMessage("Could not find obsidian in hotbar!");
                return false;
            }
        }
        this.int1 = Globals.mc.player.inventory.currentItem;
        return true;
    }

    private void getColor(Class66 class66) {
        if (this.blockPos3 != null) {
            Class50.Method137(this.blockPos3, Color.RED, false, Color.RED, 1.0f, true, true, 125, true);
        }
    }

    private void getError1ReturnType(Class26 class26) {
        if (!this.getError1()) {
            this.getError1ReturnType();
            return;
        }
        this.getErrorReturnedType();
    }

    private boolean getRedstoneReturnType() {
        return this.redstone.getValue() == Class194.Torch;
    }

    public void getError1ReturnType() {
        Class547.printChatMessage(ChatFormatting.RED + "Operation canceled or failed.");
        this.bool = false;
        this.bool = false;
        if(this.blockPos3 == null);
        if (this.blockPos2 == null);
        this.bool = false;
        this.currentTarget = null;
        if (!this.getError2()) {
            return;
        }
        this.getManagerFlVal();
        Class547.printChatMessage(ChatFormatting.YELLOW + "You can try again later.");
    }
}
