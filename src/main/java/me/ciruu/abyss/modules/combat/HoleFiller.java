package me.ciruu.abyss.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import me.ciruu.abyss.Class110;
import me.ciruu.abyss.Class152;
import me.ciruu.abyss.Class155;
import me.ciruu.abyss.Class202;
import me.ciruu.abyss.Class22;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class26;
import me.ciruu.abyss.Class30;
import me.ciruu.abyss.Class31;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class285;
import me.ciruu.abyss.enums.Class298;
import me.ciruu.abyss.enums.Class299;
import me.ciruu.abyss.enums.Class300;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.events.network.EventNetworkPostPacketEvent;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.events.player.EventPlayerUpdateWalking;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class HoleFiller
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting mode = new Setting("Mode", "Event mode", this, (Object)Class299.Tick);
    private final Setting placemode = new Setting("PlaceMode", "Place mode", this, (Object)Class298.Fast);
    private final Setting placerange = new Setting("Place Range", "Place Range", (Module)this, (Object)Float.valueOf(6.0f), Float.valueOf(0.0f), Float.valueOf(6.0f));
    private final Setting placedelay = new Setting("Place Delay", "Place Delay", (Module)this, (Object)0, 0, 250);
    private final Setting packetplace = new Setting("PacketPlace", "Abyss$preInit$Packet place", this, true);
    private final Setting rotate = new Setting("Rotate", "Rotate", this, false);
    private final Setting blockspertick = new Setting("BlocksPerTick", "Blocks per tick", (Module)this, (Object)8, 1, 20);
    private final Setting disable = new Setting("Disable", "Disable", this, true);
    private final Setting disabletime = new Setting("DisableTime", "Disable time", (Module)this, (Object)100, 1, 500);
    private final Setting offhand = new Setting("OffHand", "Holefill with OffHand", this, false);
    private final Setting silentswitch = new Setting("SilentSwitch", "", (Module)this, (Object)true, this.mainwindow, this::Method1075);
    private final Setting offhanddelay = new Setting("OffHandDelay", "OffHandDelay", (Module)this, (Object)100, 0, 300);
    private final Setting offhandsafe = new Setting("OffHandSafe", "Holefill with OffHand if your health is higher than offhand limit", this, false);
    private final Setting stopAutoCrystal = new Setting("StopAC", "Stop AutoCrystal while holefilling", this, true);
    public static boolean Field826;
    public static boolean Field827;
    private final Class22 Field828 = new Class22();
    private final Class22 Field829 = new Class22();
    private final Class22 Field830 = new Class22();
    private int Field831 = -1;
    private int Field832 = 0;
    private boolean Field833;
    private boolean Field834 = false;
    private int Field835 = -1;
    private boolean Field836 = false;
    private List Field837 = new ArrayList();
    private static boolean Field838;
    private static double Field839;
    private static double Field840;
    private boolean Field841 = false;
    @EventHandler
    private Listener Field842 = new Listener<Class26>(this::Method1076, new Predicate[0]);
    @EventHandler
    private Listener Field843 = new Listener<EventPlayerUpdate>(this::Method1077, new Predicate[0]);
    @EventHandler
    private Listener Field844 = new Listener<EventPlayerUpdateWalking>(this::Method1078, new Predicate[0]);
    @EventHandler
    private Listener Field845 = new Listener<EventNetworkPostPacketEvent>(this::Method1079, new Predicate[0]);

    public HoleFiller() {
        super("HoleFiller", "Fills nearby holes with blocks.", Class11.COMBAT, "");
        this.addSetting(this.mode);
        this.addSetting(this.placemode);
        this.addSetting(this.placerange);
        this.addSetting(this.placedelay);
        this.addSetting(this.packetplace);
        this.addSetting(this.rotate);
        this.addSetting(this.blockspertick);
        this.addSetting(this.disable);
        this.addSetting(this.disabletime);
        this.addSetting(this.silentswitch);
        this.addSetting(this.offhand);
        this.addSetting(this.offhanddelay);
        this.addSetting(this.offhandsafe);
        this.addSetting(this.stopAutoCrystal);
    }

    public String Method1081() {
        return ChatFormatting.WHITE + ((Class298)((Object)this.placemode.getValue())).name();
    }

    private void Method1082() {
        if (this.Method1083()) {
            return;
        }
        if (((Boolean)this.stopAutoCrystal.getValue()).booleanValue()) {
            Class152.Field848 = true;
        }
        if (this.Field837.isEmpty()) {
            this.Method1084();
        }
        BlockPos blockPos = null;
        int n = Class155.Method544(BlockObsidian.class);
        if (((Boolean)this.silentswitch.getValue()).booleanValue() && n != -1 && !((Boolean)this.offhand.getValue()).booleanValue()) {
            this.Field835 = Globals.mc.player.inventory.currentItem;
            Globals.mc.player.inventory.currentItem = n;
            Globals.mc.playerController.updateController();
        }
        for (BlockPos blockPos2 : new ArrayList<BlockPos>(this.Field837)) {
            if (blockPos2 == null) continue;
            Class285 class285 = Class110.Method1043(blockPos2);
            if (class285 != Class285.Ok) {
                this.Field837.remove(blockPos2);
                continue;
            }
            blockPos = blockPos2;
            this.Method1085(blockPos);
            this.Field837.remove(blockPos);
        }
        if (((Boolean)this.silentswitch.getValue()).booleanValue() && this.Field835 != -1 && n != -1 && !((Boolean)this.offhand.getValue()).booleanValue()) {
            Globals.mc.player.inventory.currentItem = this.Field835;
            Globals.mc.playerController.updateController();
        }
    }

    private void Method1085(BlockPos blockPos) {
        if (this.Field832 < (Integer)this.blockspertick.getValue() && this.Method1086(false)) {
            if (this.placemode.getValue() == Class298.Fast) {
                this.Field833 = Class31.Method536(blockPos, this.Field834 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, (Boolean)this.rotate.getValue(), (Boolean)this.packetplace.getValue(), this.Field833);
            }
            if (this.placemode.getValue() == Class298.Rotation) {
                Class31.Method541(blockPos, (Boolean)this.rotate.getValue(), (Boolean)this.rotate.getValue(), this.Field841);
            }
            if (this.placemode.getValue() == Class298.Rotation2) {
                if (((Boolean)this.rotate.getValue()).booleanValue()) {
                    this.Method1087((double)blockPos.getX() + 0.5, (double)blockPos.getY() - 0.5, (double)blockPos.getZ() + 0.5, (EntityPlayer)Globals.mc.player);
                }
                if (!this.Field841) {
                    Class110.Method1088(blockPos, EnumHand.MAIN_HAND);
                    Globals.mc.player.swingArm(EnumHand.MAIN_HAND);
                } else {
                    Class110.Method1088(blockPos, EnumHand.OFF_HAND);
                    Globals.mc.player.swingArm(EnumHand.OFF_HAND);
                }
                if (((Boolean)this.rotate.getValue()).booleanValue()) {
                    HoleFiller.Method1089();
                }
            }
            this.Field830.getNanoTime();
            ++this.Field832;
        }
    }

    private void Method1087(double d, double d2, double d3, EntityPlayer entityPlayer) {
        double[] dArray = HoleFiller.Method1090(d, d2, d3, entityPlayer);
        HoleFiller.Method1091((float)dArray[0], (float)dArray[1]);
    }

    public static double[] Method1090(double d, double d2, double d3, EntityPlayer entityPlayer) {
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

    private static void Method1091(float f, float f2) {
        Field839 = f;
        Field840 = f2;
        Field838 = true;
    }

    private static void Method1089() {
        if (Field838) {
            Field839 = Globals.mc.player.rotationYaw;
            Field840 = Globals.mc.player.rotationPitch;
            Field838 = false;
        }
    }

    private boolean Method1083() {
        this.Field841 = (Boolean)this.offhand.getValue();
        if (((Boolean)this.disable.getValue()).booleanValue() && this.Field828.getLongNanoTime(((Integer)this.disabletime.getValue()).intValue())) {
            if (this.Field829.getLongNanoTime(((Integer)this.offhanddelay.getValue()).intValue())) {
                this.offhand.getValue();
            }
            return true;
        }
        this.Field829.getNanoTime();
        if (Globals.mc.player.inventory.currentItem != this.Field835 && Globals.mc.player.inventory.currentItem != Class155.Method544(BlockObsidian.class)) {
            this.Field835 = Globals.mc.player.inventory.currentItem;
        }
        this.Method1086(true);
        this.Field832 = 0;
        if (this.placemode.getValue() == Class298.Fast) {
            this.Field833 = Class30.Method1093(this.Field833);
        }
        this.Field834 = Class155.Method1094(Globals.mc.player.getHeldItemOffhand().getItem(), BlockObsidian.class);
        this.Field831 = Class155.Method544(BlockObsidian.class);
        if (!((Boolean)this.offhand.getValue()).booleanValue() && ((Boolean)this.silentswitch.getValue()).booleanValue()) {
            return !this.Field830.getLongNanoTime(((Integer)this.placedelay.getValue()).intValue());
        }
        if (((Boolean)this.offhandsafe.getValue()).booleanValue() && OffHand.Field852 && Class155.Method544(BlockObsidian.class) != -1) {
            Globals.mc.player.inventory.currentItem = Class155.Method544(BlockObsidian.class);
            this.Field841 = false;
        }
        if (!this.Field834 && this.Field831 == -1 && !this.Field841) {
            return true;
        }
        if (this.Field841 && !this.Field834) {
            return true;
        }
        return !this.Field830.getLongNanoTime(((Integer)this.placedelay.getValue()).intValue());
    }

    private boolean Method1086(boolean bl) {
        if (((Boolean)this.offhand.getValue()).booleanValue()) {
            return true;
        }
        boolean[] blArray = Class155.Method1095(bl, this.Field835, this.Field836, Class300.NORMAL, BlockObsidian.class);
        this.Field836 = blArray[0];
        return blArray[1];
    }

    public void Method1084() {
        this.Field837.clear();
        float range = ((Float)this.placerange.getValue()).floatValue();
        int intRange = (int) range;

        for (BlockPos blockPos = (BlockPos)null; blockPos == Class110.Method1096(Class202.Method931(), range, intRange, false, true, 0);) {
            Block blockEast = Globals.mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock();
            Block blockSouth = Globals.mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock();
            Block blockWest = Globals.mc.world.getBlockState(blockPos.add(-1, 0, 0)).getBlock();
            Block blockNorth = Globals.mc.world.getBlockState(blockPos.add(0, 0, -1)).getBlock();

            boolean isSurroundedByBedrockOrObsidian =
                    (blockEast == Blocks.BEDROCK || blockEast == Blocks.OBSIDIAN) &&
                            (blockSouth == Blocks.BEDROCK || blockSouth == Blocks.OBSIDIAN) &&
                            (blockWest == Blocks.BEDROCK || blockWest == Blocks.OBSIDIAN) &&
                            (blockNorth == Blocks.BEDROCK || blockNorth == Blocks.OBSIDIAN);

            boolean isHoleEmpty =
                    Globals.mc.world.getBlockState(blockPos).getMaterial() == Material.AIR &&
                            Globals.mc.world.getBlockState(blockPos.add(0, 1, 0)).getMaterial() == Material.AIR &&
                            Globals.mc.world.getBlockState(blockPos.add(0, 2, 0)).getMaterial() == Material.AIR;

            if (isSurroundedByBedrockOrObsidian && isHoleEmpty) {
                this.Field837.add(blockPos);
            }
        }
    }


    public void Method1097() {
        super.getEnable();
        if (Globals.mc.player == null || Globals.mc.world == null) {
            this.Field828.getNanoTime();
        }
        Field826 = (Boolean)this.offhand.getValue();
        Field827 = (Boolean)this.offhandsafe.getValue();
        this.Field828.getNanoTime();
    }

    public void Method1098() {
        super.getDisable();
        this.Method1086(true);
        this.Field833 = Class30.Method1093(this.Field833);
        this.Field834 = false;
        Class152.Field848 = false;
        if (this.placemode.getValue() == Class298.Fast) {
            this.Field833 = Class30.Method1093(this.Field833);
        }
        if (this.placemode.getValue() == Class298.Rotation2 && ((Boolean)this.rotate.getValue()).booleanValue()) {
            HoleFiller.Method1089();
        }
    }

    private void Method1079(EventNetworkPostPacketEvent eventNetworkPostPacketEvent) {
        if (this.placemode.getValue() == Class298.Rotation2 && ((Boolean)this.rotate.getValue()).booleanValue() && eventNetworkPostPacketEvent.getPacket() instanceof CPacketPlayer && Field838) {
            CPacketPlayer cPacketPlayer = null;
            cPacketPlayer.yaw = (float)Field839;
            cPacketPlayer.pitch = (float)Field840;
        }
    }

    private void Method1078(EventPlayerUpdateWalking eventPlayerUpdateWalking) {
        if (this.offhand.getValue() != null && eventPlayerUpdateWalking.getClass53() == Class53.PRE && (Integer)this.blockspertick.getValue() == 1 && ((Boolean)this.rotate.getValue()).booleanValue()) {
            this.Method1082();
        }
    }

    private void Method1077(EventPlayerUpdate eventPlayerUpdate) {
        if (this.offhand.getValue() != null && ((Integer)this.blockspertick.getValue() != 1 || !((Boolean)this.rotate.getValue()).booleanValue()) && this.mode.getValue() == Class299.Update) {
            this.Method1082();
        }
    }

    private void Method1076(Class26 class26) {
        if (this.offhand.getValue() != null && ((Integer)this.blockspertick.getValue() != 1 || !((Boolean)this.rotate.getValue()).booleanValue()) && this.mode.getValue() == Class299.Tick) {
            this.Method1082();
        }
    }

    private boolean Method1075() {
        return (Boolean)this.offhand.getValue() == false;
    }
}
