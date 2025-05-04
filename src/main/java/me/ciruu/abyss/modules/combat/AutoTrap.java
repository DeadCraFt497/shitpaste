package me.ciruu.abyss.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Predicate;

import me.ciruu.abyss.*;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class238;
import me.ciruu.abyss.enums.Class580;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.BlockObsidian;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import static me.ciruu.abyss.Globals.mc;

public class AutoTrap
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting mode = new Setting("Mode", "", this, (Object)Class580.Normal);
    private final Setting rotate = new Setting("Rotate", "Rotate", this, false);
    private final Setting smartdisable = new Setting("SmartDisable", "", this, true);
    private final Setting range = new Setting("Range", "", (Module)this, (Object)Float.valueOf(6.0f), Float.valueOf(0.0f), Float.valueOf(6.0f));
    private final Setting delay = new Setting("Delay", "Delay in ticks", (Module)this, (Object)0, 0, 10);
    private final Setting blockspertick = new Setting("BlocksPerTick", "Blocks per tick", (Module)this, (Object)4, 1, 8);
    private final Setting silentswitch = new Setting("SilentSwitch", "", this, false);
    private final Setting disableswitchback = new Setting("DisableSwitchBack", "", this, false);
    private final Setting disable = new Setting("Disable", "", this, (Object)Class238.None);
    private final Setting timer = new Setting("Timer", "Delay (MS)", this, Float.valueOf(1000.0f), Float.valueOf(0.0f), Float.valueOf(10000.0f), this.mainwindow, this::Method3319);
    private final Setting crystaltrapwindow = new Setting("", "", this, new Class25("CrystalTrap Settings"));
    private final Setting crystalbind = new Setting("CrystalBind", "", (Module)this, (Object)new Class207(0), this.crystaltrapwindow, AutoTrap::Method3320);
    private final Setting breakdelay = new Setting("BreakDelay", "Delay in ticks", this, 1, 0, 10, this.crystaltrapwindow, AutoTrap::Method3321);
    private int int1;
    private EntityPlayer entity1;
    private BlockPos blockPos1;
    private int int2 = 0;
    private int int3 = 0;
    private boolean bool_1 = false;
    private boolean bool_2 = false;
    private EntityEnderCrystal entity2;
    private BlockPos blockPos3;
    private double[] db_1 = new double[2];
    public Vec3d vec3d = new Vec3d(0.0, 0.0, 0.0);
    private int int5 = 0;
    private boolean bool_3 = false;
    private final Class22 clazz = new Class22();
    private final Vec3d[] vec3d1 = new Vec3d[]{new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 0.0)};
    private final Vec3d[] vec3d2 = new Vec3d[]{new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 1.0), new Vec3d(1.0, 3.0, 0.0), new Vec3d(-1.0, 3.0, 0.0), new Vec3d(0.0, 3.0, 0.0)};
    private final Vec3d[] vec3d3 = new Vec3d[]{new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 0.0)};
    private final Vec3d[] vec3d4 = new Vec3d[]{new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 0.0)};
    private final Vec3d[] vec3d5 = new Vec3d[]{new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 0.0), new Vec3d(0.0, 4.0, 0.0)};
    @EventHandler
    private Listener listen1 = new Listener<EventPlayerUpdate>(this::Method3322, new Predicate[0]);
    @EventHandler
    private Listener listen2 = new Listener<Class350>(this::Method3323, new Predicate[0]);

    public AutoTrap() {
        super("AutoTrap", "Trap nearby players in obsidian.", Class11.COMBAT, "");
        this.addSetting(this.mode);
        this.addSetting(this.rotate);
        this.addSetting(this.smartdisable);
        this.addSetting(this.range);
        this.addSetting(this.delay);
        this.addSetting(this.blockspertick);
        this.addSetting(this.silentswitch);
        this.addSetting(this.disableswitchback);
        this.addSetting(this.disable);
        this.addSetting(this.timer);
        this.addSetting(this.crystaltrapwindow);
        this.addSetting(this.crystalbind);
        this.addSetting(this.breakdelay);
    }

    public void getEnabled() {
        super.getEnable();
        if (!this.getObsidianError()) {
            this.disable();
            return;
        }
        this.getObsidianError();
        if (mc.player != null)this.getObsidianError();
        this.int1 = mc.player.inventory.currentItem;
        this.entity1 = this.getEntity();
        if (this.entity1 == null) {
            Class547.printChatMessage("Can't find target!");
            this.disable();
            return;
        }
        this.blockPos1 = Class30.Method209(this.entity1);
        this.clazz.getNanoTime();
    }

    public void getDisabled() {
        super.getDisable();
        this.bool_1 = false;
        if (((Boolean)this.disableswitchback.getValue()).booleanValue() && !((Boolean)this.silentswitch.getValue()).booleanValue()) {
            Class155.Method522(this.int1, false);
        }
    }

    public String getChatCrystal() {
        return this.bool_1 ? ChatFormatting.YELLOW + "Crystal" : ChatFormatting.WHITE + ((Class580)((Object)this.mode.getValue())).name();
    }

    public void getItemPacketBoolean(boolean bl) {
        if (this.int2 != -1) {
            Class155.Method522(this.int2, false);
        }
    }

    private void getAutoTrap() {
        if (this.entity1.isDead || this.entity1 == null || this.entity1.getDistance((Entity) mc.player) >= 8.0f) {
            this.getObsidianError();
            return;
        }
        if (((Boolean)this.smartdisable.getValue()).booleanValue() && (Math.abs(Class30.Method209(this.entity1).getZ() - this.blockPos1.getZ()) >= 1 || Math.abs(Class30.Method209(this.entity1).getX() - this.blockPos1.getX()) >= 1)) {
            this.getObsidianError();
            return;
        }
        ArrayList arrayList = new ArrayList();
        switch ((Class580)((Object)this.mode.getValue())) {
            case Normal: {
                Collections.addAll(arrayList, this.vec3d1);
                break;
            }
            case DoubleTop: {
                Collections.addAll(arrayList, this.vec3d5);
                break;
            }
            case Feet: {
                Collections.addAll(arrayList, this.vec3d4);
                break;
            }
            case Face: {
                Collections.addAll(arrayList, this.vec3d2);
                break;
            }
            default: {
                Collections.addAll(arrayList, this.vec3d3);
            }
        }
        if (this.int2 >= (Integer)this.delay.getValue()) {
            this.int2 = 0;
            int n = 0;
            int n2 = Class155.Method544(BlockObsidian.class);
            if (((Boolean)this.silentswitch.getValue()).booleanValue() && n2 != -1) {
                this.int1 = mc.player.inventory.currentItem;
                mc.player.inventory.currentItem = n2;
                mc.playerController.updateController();
            }
            while (n < (Integer)this.blockspertick.getValue()) {
                if (this.int3 >= arrayList.size()) {
                    this.int3 = 0;
                    break;
                }
                BlockPos blockPos = new BlockPos((Vec3d)arrayList.get(this.int3));
                BlockPos blockPos2 = new BlockPos(this.entity1.getPositionVector()).down().add(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                boolean bl = true;
                if (!mc.world.getBlockState(blockPos2).getMaterial().isReplaceable()) {
                    bl = false;
                }
                for (Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(blockPos2))) {
                    if (entity instanceof EntityItem || entity instanceof EntityXPOrb) continue;
                    bl = false;
                    break;
                }
                if (blockPos.equals((Object)new BlockPos(0, 3, 0)) && this.mode.getValue() != Class580.DoubleTop && this.bool_1) {
                    this.blockPos3 = blockPos2;
                    for (Entity entity : mc.world.loadedEntityList) {
                        if (entity instanceof EntityEnderCrystal && entity.getPosition().equals((Object)blockPos2.add(new Vec3i(0.5, 1.0, 0.5)))) {
                            bl = false;
                            this.bool_2 = true;
                            this.entity2 = (EntityEnderCrystal)entity;
                            break;
                        }
                        this.bool_2 = false;
                        this.entity2 = null;
                    }
                }
                if (bl) {
                    if (!((Boolean)this.silentswitch.getValue()).booleanValue()) {
                        Class155.Method522(Class155.Method544(BlockObsidian.class), false);
                    }
                    if (Class31.Method1261(blockPos2, (Boolean)this.rotate.getValue(), ((Float)this.range.getValue()).floatValue())) {
                        ++n;
                    }
                }
                ++this.int3;
                if (bl || this.int3 < arrayList.size() || this.disable.getValue() != Class238.Blocks) continue;
                this.getObsidianError();
            }
            if (((Boolean)this.silentswitch.getValue()).booleanValue() && this.int1 != -1 && n2 != -1) {
                mc.player.inventory.currentItem = this.int1;
                mc.playerController.updateController();
            }
        }
        ++this.int2;
        if (this.bool_1 && this.mode.getValue() != Class580.DoubleTop) {
            if (this.blockPos3 == null || !(mc.world.getBlockState(this.blockPos3).getBlock() instanceof BlockObsidian)) {
                Class602.Method3024(null);
                this.bool_3 = false;
            } else if (!this.bool_3) {
                Class602.Method3024(this.blockPos3);
                Class602.Method3026(((Float)this.range.getValue()).floatValue(), false);
                this.bool_3 = true;
            }
            if (!this.bool_2 && this.blockPos3 != null) {
                if (mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL && mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) {
                    if (Class155.Method544(ItemEndCrystal.class) == -1) {
                        Class547.printChatMessage(ChatFormatting.RED + "Can't find crystals in your hotbar!");
                        this.bool_1 = false;
                        return;
                    }
                    Class155.Method1491(ItemEndCrystal.class, false);
                }
                if (Class308.Method1106(this.blockPos3, true, false)) {
                    this.getRotation(this.blockPos3);
                    Class31.Method52(this.blockPos3, mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, (Boolean)this.rotate.getValue(), this.vec3d);
                }
            }
            if (this.entity2 != null && this.blockPos3 != null) {
                if (mc.world.getBlockState(this.blockPos3).getMaterial().isReplaceable() && this.int5 > (Integer)this.breakdelay.getValue()) {
                    this.int5 = 0;
                    this.getRotation2((Entity)this.entity2);
                    this.getSwingCrystal(this.entity2);
                }
                if (!mc.world.getBlockState(this.blockPos3).getMaterial().isReplaceable()) {
                    if (this.getItem()) {
                        Class602.Method3026(((Float)this.range.getValue()).floatValue(), false);
                    } else {
                        Class547.printChatMessage(ChatFormatting.RED + "Can't find pickaxe in your hotbar!");
                        this.bool_1 = false;
                        return;
                    }
                }
            }
            ++this.int5;
        }
    }

    private boolean getItem() {
        boolean bl;
        boolean bl2 = bl = mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE;
        if (!bl) {
            for (int i = 0; i < 9; ++i) {
                ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
                if (itemStack.isEmpty() || itemStack.getItem() != Items.DIAMOND_PICKAXE) continue;
                Class155.Method522(i, false);
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    private void getRotation(BlockPos blockPos) {
        if (((Boolean)this.rotate.getValue()).booleanValue()) {
            Class353 class353 = Class84.Method1492(blockPos);
            if (class353 != null) {
                this.vec3d = class353.Method1493();
            }
            this.db_1 = Class354.Method1505((double)blockPos.getX() + 0.5, (double)blockPos.getY() - 0.5, (double)blockPos.getZ() + 0.5, (EntityPlayer) mc.player);
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation((float)this.db_1[0], (float)this.db_1[1], mc.player.onGround));
        }
    }

    private void getRotation2(Entity entity) {
        if (((Boolean)this.rotate.getValue()).booleanValue()) {
            double[] dArray = Class354.Method1505(entity.posX + 0.5, entity.posY - 0.5, entity.posZ + 0.5, (EntityPlayer) mc.player);
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation((float)dArray[0], (float)dArray[1], mc.player.onGround));
        }
    }

    private void getSwingCrystal(EntityEnderCrystal entityEnderCrystal) {
        mc.player.connection.sendPacket((Packet)new CPacketUseEntity((Entity)entityEnderCrystal));
        mc.player.swingArm(mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
    }

    private EntityPlayer getEntity() {
        if (mc.world.playerEntities.isEmpty()) {
            return null;
        }
        EntityPlayer entityPlayer = null;
        for (EntityPlayer entityPlayer2 : mc.world.playerEntities) {
            if (entityPlayer2 == mc.player || Manager.Field223.Method711((Entity)entityPlayer2) || !Class354.Method1908((Entity)entityPlayer2) || entityPlayer2.getHealth() <= 0.0f || entityPlayer != null && Minecraft.getMinecraft().player.getDistance((Entity)entityPlayer2) > Minecraft.getMinecraft().player.getDistance((Entity)entityPlayer)) continue;
            entityPlayer = entityPlayer2;
        }
        return entityPlayer;
    }

    private boolean getObsidianError() {
        if (Class155.Method544(BlockObsidian.class) == -1) {
            Class547.printChatMessage("Can't find obsidian in hotbar!");
            return false;
        }
        return true;
    }

    private void Method3323(Class350 class350) {
        if (!(mc.currentScreen instanceof Class219) && class350.Method1535() == ((Class207)this.crystalbind.getValue()).Method592() && this.mode.getValue() != Class580.DoubleTop) {
            this.bool_1 = !this.bool_1;
        }
    }

    private void Method3322(EventPlayerUpdate eventPlayerUpdate) {
        if (this.disable.getValue() == Class238.Timer && this.clazz.getLongNanoTime(((Float)this.timer.getValue()).longValue())) {
            this.getObsidianError();
            return;
        }
        this.getAutoTrap();
    }

    private static boolean Method3321() {
        return true;
    }

    private static boolean Method3320() {
        return true;
    }

    private boolean Method3319() {
        return this.disable.getValue() == Class238.Timer;
    }
}
