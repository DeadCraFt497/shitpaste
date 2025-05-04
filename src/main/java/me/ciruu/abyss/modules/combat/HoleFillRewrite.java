package me.ciruu.abyss.modules.combat;

import com.google.common.collect.Sets;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import me.ciruu.abyss.Class108;
import me.ciruu.abyss.Class110;
import me.ciruu.abyss.Class155;
import me.ciruu.abyss.Class202;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class273;
import me.ciruu.abyss.Class30;
import me.ciruu.abyss.Class31;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.Class66;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class274;
import me.ciruu.abyss.events.network.EventNetworkPostPacketEvent;
import me.ciruu.abyss.events.player.EventPlayerUpdateWalking;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import static me.ciruu.abyss.Globals.mc;

public class HoleFillRewrite
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting range = new Setting("Range", "", (Module)this, (Object)Float.valueOf(6.0f), Float.valueOf(0.0f), Float.valueOf(6.0f));
    private final Setting delay = new Setting("Delay", "", (Module)this, (Object)0, 0, 10);
    private final Setting rotate = new Setting("Rotate", "", this, false);
    private final Setting blockspertick = new Setting("BlocksPerTick", "", this, 4, 1, 12, this.mainwindow, this::Method4300);
    private final Setting swing = new Setting("Swing", "", this, true);
    private final Setting timer = new Setting("Timer", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(5.0f));
    private final Setting disable = new Setting("Disable", "", this, true);
    private final Setting silent = new Setting("Silent", "", this, true);
    private final Setting doubles = new Setting("Doubles", "", this, false);
    private final Setting smart = new Setting("Smart", "", this, false);
    private final Setting autorange = new Setting("AutoRange", "", (Module)this, (Object)false, this.mainwindow, this.smart::getValue);
    private final Setting smartrange = new Setting("SmartRange", "", this, Float.valueOf(3.0f), Float.valueOf(0.0f), Float.valueOf(6.0f), this.mainwindow, this::Method4301);
    private final Setting render = new Setting("Render", "", this, true);
    private final Setting color = new Setting("Color", "", (Module)this, (Object)new Color(255, 0, 0, 125), this.mainwindow, this.render::getValue);
    private List Field3560 = new ArrayList();
    private int Field3561 = -1;
    private static boolean Field3562;
    private static double Field3563;
    private static double Field3564;
    private int Field3565 = 0;
    private int Field3566 = -1;
    private int Field3567 = 0;
    @EventHandler
    private Listener Field3568 = new Listener<EventPlayerUpdateWalking>(this::Method4302, new Predicate[0]);
    @EventHandler
    private Listener Field3569 = new Listener<EventNetworkPostPacketEvent>(this::Method4303, new Predicate[0]);
    @EventHandler
    private Listener Field3570 = new Listener<Class66>(this::Method4304, new Predicate[0]);

    public HoleFillRewrite() {
        super("HoleFillRewrite", "Fills nearby holes with blocks.", Class11.COMBAT, "");
        this.addSetting(this.range);
        this.addSetting(this.delay);
        this.addSetting(this.rotate);
        this.addSetting(this.blockspertick);
        this.addSetting(this.swing);
        this.addSetting(this.timer);
        this.addSetting(this.disable);
        this.addSetting(this.silent);
        this.addSetting(this.doubles);
        this.addSetting(this.smart);
        this.addSetting(this.autorange);
        this.addSetting(this.smartrange);
        this.addSetting(this.render);
        this.addSetting(this.color);
    }

    public void Method4306() {
        super.getDisable();
        Manager.Field298.Method337(1.0f);
        if (((Boolean)this.rotate.getValue()).booleanValue()) {
            HoleFillRewrite.Method4307();
        }
        if (this.Field3561 != -1 && !((Boolean)this.silent.getValue()).booleanValue()) {
            Class155.Method522(this.Field3561, false);
        }
        this.Field3567 = 0;
    }

    public void Method4308() {
        super.getEnable();
        if (!this.Method4309()) {
            this.Method4311();
            return;
        }
        if (((Boolean)this.disable.getValue()).booleanValue()) {
            this.Method4311();
        }
    }

    private void Method4312() {
        int n = Class155.Method544(BlockObsidian.class);
        if (((Boolean)this.silent.getValue()).booleanValue() && n != -1 && !Item.getItemFromBlock((Block)Blocks.OBSIDIAN).equals(mc.player.getHeldItemMainhand().getItem())) {
            this.Field3566 = mc.player.inventory.currentItem;
            mc.player.inventory.currentItem = n;
            mc.playerController.updateController();
        }
        Manager.Field298.Method337(((Float)this.timer.getValue()).floatValue());
        this.Field3565 = 0;
        if (this.Field3560.isEmpty()) {
            if (((Boolean)this.silent.getValue()).booleanValue() && this.Field3566 != -1 && n != -1) {
                mc.player.inventory.currentItem = this.Field3566;
                mc.playerController.updateController();
            }
            if (((Boolean)this.silent.getValue()).booleanValue() && this.Field3566 != -1 && n != -1) {
                return;
            }
            return;
        }
        BlockPos blockPos = (BlockPos)this.Field3560.get(this.Field3560.size() - 1);
        if (((Boolean)this.rotate.getValue()).booleanValue()) {
            this.Method4313((double)blockPos.getX() + 0.5, (double)blockPos.getY() - 0.5, (double)blockPos.getZ() + 0.5, (EntityPlayer) mc.player);
        }
        Class110.Method1088(blockPos, EnumHand.MAIN_HAND);
        if (((Boolean)this.swing.getValue()).booleanValue()) {
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
        if (((Boolean)this.rotate.getValue()).booleanValue()) {
            HoleFillRewrite.Method4307();
        }
        this.Field3560.remove(blockPos);
        if (((Boolean)this.silent.getValue()).booleanValue() && this.Field3566 != -1 && n != -1) {
            mc.player.inventory.currentItem = this.Field3566;
            mc.playerController.updateController();
        }
        ++this.Field3567;
    }

    private boolean Method4309() {
        if (mc.player == null || mc.world == null) {
            return false;
        }
        int n = Class155.Method544(BlockObsidian.class);
        if (n == -1) {
            return false;
        }
        if (!((Boolean) this.silent.getValue()).booleanValue()) {
            this.Field3561 = mc.player.inventory.currentItem;
            Class155.Method522(n, false);
        }
        return true;
    }

    public void Method4311() {
        this.Field3560.clear();

        if (!((Boolean)this.doubles.getValue()).booleanValue()) {
            for (BlockPos blockPos = (BlockPos)null; blockPos ==Class110.Method1096(
                    Class202.Method931(),
                    ((Float)this.range.getValue()).floatValue(),
                    ((Float)this.range.getValue()).intValue(),
                    false, true, 0);) {

                boolean bl =
                        (mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK
                                || mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN)
                                && (mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK
                                || mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN)
                                && (mc.world.getBlockState(blockPos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK
                                || mc.world.getBlockState(blockPos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN)
                                && (mc.world.getBlockState(blockPos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK
                                || mc.world.getBlockState(blockPos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN)
                                && mc.world.getBlockState(blockPos).getMaterial() == Material.AIR
                                && mc.world.getBlockState(blockPos.add(0, 1, 0)).getMaterial() == Material.AIR
                                && mc.world.getBlockState(blockPos.add(0, 2, 0)).getMaterial() == Material.AIR;

                if (!bl) continue;
                this.Field3560.add(blockPos);
            }
        } else {
            HashSet<Object> hashSet = Sets.newHashSet();
            List<BlockPos> list = Class31.Method210(
                    Class30.Method209((EntityPlayer) mc.player),
                    ((Float)this.range.getValue()).floatValue(),
                    ((Float)this.range.getValue()).intValue(),
                    false, true, 0);

            for (BlockPos blockPos : list) {
                if (!mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)
                        || mc.world.getBlockState(blockPos.add(0, -1, 0)).getBlock().equals(Blocks.AIR)
                        || !mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)
                        || !mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) continue;

                hashSet.add(blockPos);
            }

            hashSet.forEach(Method4314());
        }
    }




    private void Method4313(double d, double d2, double d3, EntityPlayer entityPlayer) {
        double[] dArray = HoleFillRewrite.Method4315(d, d2, d3, entityPlayer);
        HoleFillRewrite.Method4316((float)dArray[0], (float)dArray[1]);
    }

    public static double[] Method4315(double d, double d2, double d3, EntityPlayer entityPlayer) {
        double d4 = entityPlayer.posX - d;
        double d5 = entityPlayer.posY - d2;
        double d6 = entityPlayer.posZ - d3;
        double d7 = Math.sqrt(d4 * d4 + d5 * d5 + d6 * d6);
        double d8 = Math.asin(d5 /= d7);
        double d9 = Math.atan2(d6 /= d7, d4 /= d7);
        d8 = d8 * 180.0 / Math.PI;
        d9 = d9 * 180.0 / Math.PI;
        return new double[]{d9 += 90.0, d8};
    }

    private static void Method4316(float f, float f2) {
        Field3563 = f;
        Field3564 = f2;
        Field3562 = true;
    }

    private static void Method4307() {
        if (Field3562) {
            Field3563 = mc.player.rotationYaw;
            Field3564 = mc.player.rotationPitch;
            Field3562 = false;
        }
    }

    private Consumer<? super Object> Method4314() {
        BlockPos blockPos = (BlockPos)null;
        Class273 class273 = Class108.Method1012(blockPos, false, false);
        Class274 class274 = class273.Method1013();
        if (class274 != Class274.NONE) {
            AxisAlignedBB axisAlignedBB = class273.Method1015();
            if (axisAlignedBB == null) {
                return null;
            }
            if (class274 == Class274.DOUBLE || class274 == Class274.SINGLE) {
                this.Field3560.add(blockPos);
            }
        }

        if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {
            mc.playerController.processRightClickBlock(
                    mc.player, mc.world, blockPos, EnumFacing.UP, new Vec3d(0.5, 1.0, 0.5), EnumHand.MAIN_HAND
            );
            System.out.println("Block placed at: " + blockPos);
        }
        return Method4314();
    }

    private void Method4304(Class66 class66) {
        if (((Boolean)this.render.getValue()).booleanValue() && !this.Field3560.isEmpty()) {
            this.Field3560.forEach(null);
        }
        this.Method4304(class66);
    }

    private void Method4317(BlockPos blockPos) {
        Class50.Method137(blockPos, (Color)this.color.getValue(), false, (Color)this.color.getValue(), 1.0f, true, true, ((Color)this.color.getValue()).getAlpha(), true);
    }

    private void Method4303(EventNetworkPostPacketEvent eventNetworkPostPacketEvent) {
        if (((Boolean)this.rotate.getValue()).booleanValue() && eventNetworkPostPacketEvent.getPacket() instanceof CPacketPlayer && Field3562) {
            CPacketPlayer cPacketPlayer = (CPacketPlayer)eventNetworkPostPacketEvent.getPacket();
            cPacketPlayer.yaw = (float)Field3563;
            cPacketPlayer.pitch = (float)Field3564;
        }
    }

    private void Method4302(EventPlayerUpdateWalking eventPlayerUpdateWalking) {
        if (!((Boolean)this.disable.getValue()).booleanValue()) {
            this.Method4311();
        }
        if (((Boolean)this.disable.getValue()).booleanValue() && this.Field3560.isEmpty()) {
            this.disable();
            return;
        }
        if (this.Field3560.isEmpty()) {
            Manager.Field298.Method337(1.0f);
        }
        if (this.Field3565 >= (Integer)this.delay.getValue() && !this.Field3560.isEmpty()) {
            if (((Boolean)this.smart.getValue()).booleanValue()) {
                ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
                for (BlockPos blockPos : new ArrayList<BlockPos>(this.Field3560)) {
                    for (EntityPlayer entityPlayer : mc.world.playerEntities) {
                        double d = entityPlayer.getDistance((double)blockPos.getX() + 0.5, (double)blockPos.getY() - 0.5, (double)blockPos.getZ() + 0.5);
                        if (entityPlayer == mc.player || Manager.Field223.Method711((Entity) mc.player) || mc.player.getDistance((double)blockPos.getX() + 0.5, (double)blockPos.getY() - 0.5, (double)blockPos.getZ() + 0.5) > (double)((Float)this.range.getValue()).floatValue()) continue;
                        if (d > ((Boolean)this.autorange.getValue() == false ? (double)((Float)this.smartrange.getValue()).floatValue() : 4.0)) continue;
                        arrayList.add(blockPos);
                    }
                }
                this.Field3560 = arrayList;
            }
            if (((Boolean)this.rotate.getValue()).booleanValue()) {
                this.Method4312();
            } else {
                while (this.Field3567 < (Integer)this.blockspertick.getValue() && !this.Field3560.isEmpty()) {
                    this.Method4312();
                }
            }
            this.Field3567 = 0;
        }
        ++this.Field3565;
    }


    private boolean Method4301() {
        return (Boolean)this.smart.getValue() != false && (Boolean)this.autorange.getValue() == false;
    }

    private boolean Method4300() {
        return (Boolean)this.rotate.getValue() == false;
    }
}
