package me.ciruu.abyss.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import me.ciruu.abyss.Class155;
import me.ciruu.abyss.Class31;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.Class66;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class314;
import me.ciruu.abyss.events.player.EventPlayerUpdateWalking;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class AutoBed
extends Module {
    private final Setting range = new Setting("Range", "", (Module)this, (Object)Float.valueOf(6.0f), Float.valueOf(0.0f), Float.valueOf(6.0f));
    private final Setting delay = new Setting("Delay", "", (Module)this, (Object)0, 0, 10);
    private final Setting setrotation = new Setting("SetRotation", "", this, false);
    private final Setting color = new Setting("Color", "", this, new Color(255, 0, 0, 125));
    private int int1;
    private Class314 clazz;
    private EntityPlayer entity = null;
    private int int2 = -1;
    private BlockPos blockPos = null;
    @EventHandler
    private Listener listen1 = new Listener<EventPlayerUpdateWalking>(this::getPlayerWalkingUpdate, new Predicate[0]);
    @EventHandler
    private Listener listen2 = new Listener<Class66>(this::getRenderOptions, new Predicate[0]);

    public AutoBed() {
        super("AutoBed", "Place and break beds in the nether automatically.", Class11.COMBAT, "");
        this.addSetting(this.range);
        this.addSetting(this.delay);
        this.addSetting(this.setrotation);
        this.addSetting(this.color);
    }

    public void getCurrentItem() {
        super.getEnable();
        this.int1 = 0;
        this.entity = null;
        this.int2 = Globals.mc.player.inventory.currentItem;
    }

    public void getIntBlockPos() {
        super.getDisable();
        this.blockPos = null;
        if (this.int2 != -1) {
            Class155.Method522(this.int2, false);
        }
    }

    public String getChat() {
        return this.entity != null ? ChatFormatting.WHITE + this.entity.getName() : null;
    }

    public void getAutoBed() {
        int n = this.getItemBedLogic();
        if (n == -1) {
            return;
        }
        BlockPos blockPos = null;
        EntityPlayer entityPlayer = null;
        float f = ((Float)this.range.getValue()).floatValue();
        for (EntityPlayer entityPlayer2 : Globals.mc.world.playerEntities.stream().filter(AutoBed::getEntity).collect(Collectors.toList())) {
            BlockPos blockPos2;
            BlockPos blockPos3;
            if (entityPlayer2 == Globals.mc.player || f < Globals.mc.player.getDistance((Entity)entityPlayer2)) continue;
            boolean bl = true;
            BlockPos blockPos4 = AutoBed.getFloorMath(entityPlayer2).down();
            BlockPos blockPos5 = this.getCoordinate(blockPos4);
            if (blockPos5 != null) {
                blockPos = blockPos5.up();
                entityPlayer = entityPlayer2;
                f = Globals.mc.player.getDistance((Entity)entityPlayer2);
                bl = false;
            }
            if (!bl || (blockPos3 = this.getCoordinate(blockPos2 = AutoBed.getFloorMath(entityPlayer2))) == null) continue;
            blockPos = blockPos3.up();
            entityPlayer = entityPlayer2;
            f = Globals.mc.player.getDistance((Entity)entityPlayer2);
        }
        if (entityPlayer == null) {
            this.entity = null;
            return;
        }
        this.entity = entityPlayer;
        this.blockPos = blockPos;
        if (this.clazz == Class314.NORTH) {
            if (((Boolean)this.setrotation.getValue()).booleanValue()) {
                Globals.mc.player.rotationYaw = 180.0f;
            }
            Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(180.0f, 0.0f, Globals.mc.player.onGround));
        } else if (this.clazz == Class314.SOUTH) {
            if (((Boolean)this.setrotation.getValue()).booleanValue()) {
                Globals.mc.player.rotationYaw = 0.0f;
            }
            Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, 0.0f, Globals.mc.player.onGround));
        } else if (this.clazz == Class314.WEST) {
            if (((Boolean)this.setrotation.getValue()).booleanValue()) {
                Globals.mc.player.rotationYaw = 90.0f;
            }
            Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(90.0f, 0.0f, Globals.mc.player.onGround));
        } else if (this.clazz == Class314.EAST) {
            if (((Boolean)this.setrotation.getValue()).booleanValue()) {
                Globals.mc.player.rotationYaw = -90.0f;
            }
            Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(-90.0f, 0.0f, Globals.mc.player.onGround));
        }
        Class155.Method522(n, false);
        Class31.Method541(blockPos, false, false, false);
    }

    public int getItemBedLogic() {
        for (int i = 0; i < 9; ++i) {
            if (Globals.mc.player.inventory.getStackInSlot(i).getItem() != Items.BED) continue;
            return i;
        }
        return -1;
    }

    public BlockPos getCoordinate(BlockPos blockPos) {
        if (Globals.mc.world.getBlockState(blockPos.east()).getBlock() != Blocks.AIR && Globals.mc.world.getBlockState(blockPos.east().up()).getBlock() == Blocks.AIR) {
            this.clazz = Class314.WEST;
            return blockPos.east();
        }
        if (Globals.mc.world.getBlockState(blockPos.north()).getBlock() != Blocks.AIR && Globals.mc.world.getBlockState(blockPos.north().up()).getBlock() == Blocks.AIR) {
            this.clazz = Class314.SOUTH;
            return blockPos.north();
        }
        if (Globals.mc.world.getBlockState(blockPos.west()).getBlock() != Blocks.AIR && Globals.mc.world.getBlockState(blockPos.west().up()).getBlock() == Blocks.AIR) {
            this.clazz = Class314.EAST;
            return blockPos.west();
        }
        if (Globals.mc.world.getBlockState(blockPos.south()).getBlock() != Blocks.AIR && Globals.mc.world.getBlockState(blockPos.south().up()).getBlock() == Blocks.AIR) {
            this.clazz = Class314.NORTH;
            return blockPos.south();
        }
        return null;
    }

    public static BlockPos getFloorMath(EntityPlayer entityPlayer) {
        return new BlockPos(Math.floor(entityPlayer.posX), Math.floor(entityPlayer.posY), Math.floor(entityPlayer.posZ));
    }

    public static boolean getBlock(BlockPos blockPos) {
        Block block = Globals.mc.world.getBlockState(blockPos).getBlock();
        return block == Blocks.BED;
    }

    public static void getBlockFacing(BlockPos blockPos) {
        EnumFacing[] enumFacingArray;
        for (EnumFacing enumFacing : enumFacingArray = EnumFacing.values()) {
            Block block = Globals.mc.world.getBlockState(blockPos.offset(enumFacing)).getBlock();
            if (!Class31.Field912.contains(block)) continue;
            Globals.mc.playerController.processRightClickBlock(Globals.mc.player, Globals.mc.world, blockPos, enumFacing.getOpposite(), new Vec3d((Vec3i)blockPos), EnumHand.MAIN_HAND);
            return;
        }
    }

    private static boolean getEntity(EntityPlayer entityPlayer) {
        return !Manager.Field223.Method233(entityPlayer.getName());
    }

    private void getRenderOptions(Class66 class66) {
        if (Globals.mc.getRenderManager() == null || Globals.mc.getRenderManager().options == null || this.blockPos == null) {
            return;
        }
        Class50.Method137(this.blockPos, (Color)this.color.getValue(), false, (Color)this.color.getValue(), 1.0f, true, true, ((Color)this.color.getValue()).getAlpha(), true);
    }

    private void getPlayerWalkingUpdate(EventPlayerUpdateWalking eventPlayerUpdateWalking) {
        if (Globals.mc.player == null) {
            return;
        }
        if (this.int1 > (Integer)this.delay.getValue()) {
            this.int1 = 0;
            this.getAutoBed();
        }
        ++this.int1;
    }
}
