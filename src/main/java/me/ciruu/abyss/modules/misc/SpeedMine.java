package me.ciruu.abyss.modules.misc;

import java.awt.Color;
import java.util.function.Predicate;
import me.ciruu.abyss.Class22;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class26;
import me.ciruu.abyss.Class29;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.Class66;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class595;
import me.ciruu.abyss.enums.Class634;
import me.ciruu.abyss.events.player.EventPlayerClickBlock;
import me.ciruu.abyss.events.player.EventPlayerDamageBlock;
import me.ciruu.abyss.events.player.EventPlayerResetBlockRemoving;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpeedMine
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting mode = new Setting("Mode", "Mode", this, (Object)Class634.Packet);
    private final Setting startdamage = new Setting("StartDamage", "Start damage", this, Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.mainwindow, this::Method3421);
    private final Setting finaldamage = new Setting("FinalDamage", "Final damage", this, Float.valueOf(0.7f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.mainwindow, this::Method3422);
    private final Setting reset = new Setting("Reset", "Reset", this, true);
    private final Setting render = new Setting("Render", "", (Module)this, (Object)true, this.mainwindow, this::Method3423);
    private final Setting rendermode = new Setting("RenderMode", "", (Module)this, (Object)Class595.Progressive, this.mainwindow, this::Method3424);
    private final Setting startcolor = new Setting("StartColor", "", (Module)this, (Object)new Color(255, 0, 0, 255), this.mainwindow, this::Method3425);
    private final Setting endcolor = new Setting("EndColor", "", (Module)this, (Object)new Color(0, 255, 0, 255), this.mainwindow, this::Method3426);
    private final Setting range = new Setting("Range", "", (Module)this, (Object)Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(50.0f));
    private final Setting silentswitch = new Setting("SilentSwitch", "", this, false);
    private final Setting delay = new Setting("Delay", "", this, 0, 0, 500, this.mainwindow, this.silentswitch::getValue);
    private final Setting rotate = new Setting("Rotate", "", (Module)this, (Object)false, this.mainwindow, this.silentswitch::getValue);
    private final Setting linewidth = new Setting("LineWidth", "", this, Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(10.0f), this.mainwindow, this.render::getValue);
    private BlockPos Field2833;
    private BlockPos Field2834;
    private Color Field2835 = Color.RED;
    private boolean Field2836 = false;
    private float[] Field2837 = new float[3];
    private final Class22 Field2838 = new Class22();
    private final Class22 Field2839 = new Class22();
    private boolean Field2840 = false;
    private int Field2841 = -1;
    private boolean Field2842 = false;
    @EventHandler
    private Listener Field2843 = new Listener<Class26>(this::Method3427, new Predicate[0]);
    @EventHandler
    private Listener Field2844 = new Listener<EventPlayerUpdate>(this::Method3428, new Predicate[0]);
    @EventHandler
    private Listener Field2845 = new Listener<EventPlayerResetBlockRemoving>(this::Method3429, new Predicate[0]);
    @EventHandler
    private Listener Field2846 = new Listener<EventPlayerClickBlock>(this::Method3430, new Predicate[0]);
    @EventHandler
    private Listener Field2847 = new Listener<EventPlayerDamageBlock>(this::Method3431, new Predicate[0]);
    @EventHandler
    private Listener Field2848 = new Listener<Class66>(this::Method3432, new Predicate[0]);

    public SpeedMine() {
        super("SpeedMine", "Mines blocks faster.", Class11.MISC, "");
        this.addSetting(this.mode);
        this.addSetting(this.startdamage);
        this.addSetting(this.finaldamage);
        this.addSetting(this.reset);
        this.addSetting(this.render);
        this.addSetting(this.rendermode);
        this.addSetting(this.startcolor);
        this.addSetting(this.endcolor);
        this.addSetting(this.range);
        this.addSetting(this.silentswitch);
        this.addSetting(this.delay);
        this.addSetting(this.rotate);
        this.addSetting(this.linewidth);
    }

    private boolean Method3434(BlockPos blockPos) {
        IBlockState iBlockState = Globals.mc.world.getBlockState(blockPos);
        Block block = iBlockState.getBlock();
        return block.getBlockHardness(iBlockState, (World)Globals.mc.world, blockPos) != -1.0f;
    }

    private float Method3435() {
        float[] fArray = new float[3];
        Color.RGBtoHSB(((Color)this.startcolor.getValue()).getRed(), ((Color)this.startcolor.getValue()).getGreen(), ((Color)this.startcolor.getValue()).getBlue(), fArray);
        float[] fArray2 = new float[3];
        Color.RGBtoHSB(((Color)this.endcolor.getValue()).getRed(), ((Color)this.endcolor.getValue()).getGreen(), ((Color)this.endcolor.getValue()).getBlue(), fArray2);
        float f = Math.abs(fArray2[0] - fArray[0]);
        float f2 = f * 0.003f / 0.24f;
        return f2;
    }

    private void Method3436() {
        this.Field2835 = (Color)this.startcolor.getValue();
        this.Field2836 = false;
        this.Field2837 = new float[3];
        this.Field2838.getNanoTime();
    }

    private int Method3437() {
        for (int i = 0; i < 9; ++i) {
            if (Globals.mc.player.inventory.getStackInSlot(i).getItem() != Items.DIAMOND_PICKAXE) continue;
            return i;
        }
        return -1;
    }

    private void Method3438(double d, double d2, double d3, EntityPlayer entityPlayer) {
        double[] dArray = this.Method3439(d, d2, d3, entityPlayer);
        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation((float)dArray[0], (float)dArray[1], entityPlayer.onGround));
    }

    private double[] Method3439(double d, double d2, double d3, EntityPlayer entityPlayer) {
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

    private void Method3432(Class66 class66) {
        if (((Boolean)this.render.getValue()).booleanValue() && this.Field2833 != null && Globals.mc.world.getBlockState(this.Field2833).getBlock() != Blocks.AIR && this.mode.getValue() == Class634.Packet) {
            if (this.rendermode.getValue() == Class595.Progressive) {
                Class50.Method137(this.Field2833, this.Field2835, false, this.Field2835, ((Float)this.linewidth.getValue()).floatValue(), true, true, 125, false);
            } else if (this.rendermode.getValue() == Class595.Instant) {
                Color color = this.Field2838.getLongNanoTime((int)(2000.0f * Manager.Field1645.Method2895())) ? (Color)this.endcolor.getValue() : (Color)this.startcolor.getValue();
                Class50.Method137(this.Field2833, color, false, color, ((Float)this.linewidth.getValue()).floatValue(), true, true, 125, false);
            }
        }
    }

    private void Method3431(EventPlayerDamageBlock eventPlayerDamageBlock) {
        if (this.Method3434(eventPlayerDamageBlock.getBlockPos())) {
            if (((Boolean)this.reset.getValue()).booleanValue()) {
                Globals.mc.playerController.isHittingBlock = false;
            }
            if (Globals.mc.player.getDistanceSq(eventPlayerDamageBlock.getBlockPos()) > Class29.getDouble6(((Float)this.range.getValue()).floatValue())) {
                return;
            }
            this.Field2833 = eventPlayerDamageBlock.getBlockPos();
            if (!this.Field2833.equals((Object)this.Field2834)) {
                this.Method3436();
                this.Field2839.getNanoTime();
            }
            this.Field2834 = this.Field2833;
            switch ((Class634)((Object)this.mode.getValue())) {
                case Packet: {
                    Globals.mc.player.swingArm(EnumHand.MAIN_HAND);
                    Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, eventPlayerDamageBlock.getBlockPos(), eventPlayerDamageBlock.getEnumValue()));
                    Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, eventPlayerDamageBlock.getBlockPos(), eventPlayerDamageBlock.getEnumValue()));
                    eventPlayerDamageBlock.cancel();
                    break;
                }
                case Damage: {
                    Globals.mc.playerController.curBlockDamageMP = ((Float)this.startdamage.getValue()).floatValue();
                    if (!(Globals.mc.playerController.curBlockDamageMP >= ((Float)this.finaldamage.getValue()).floatValue())) break;
                    Globals.mc.playerController.curBlockDamageMP = 1.0f;
                }
            }
        }
    }

    private void Method3430(EventPlayerClickBlock eventPlayerClickBlock) {
        this.Field2842 = true;
        this.Field2839.getNanoTime();
        if (((Boolean)this.reset.getValue()).booleanValue() && Globals.mc.playerController.curBlockDamageMP > 0.1f) {
            Globals.mc.playerController.isHittingBlock = true;
        }
    }

    private void Method3429(EventPlayerResetBlockRemoving eventPlayerResetBlockRemoving) {
        if (((Boolean)this.reset.getValue()).booleanValue()) {
            eventPlayerResetBlockRemoving.cancel();
        }
    }

    private void Method3428(EventPlayerUpdate eventPlayerUpdate) {
        if (Globals.mc.player == null || Globals.mc.world == null) {
            return;
        }
        if (this.Field2833 != null && Globals.mc.player.getDistanceSq(this.Field2833) > Class29.getDouble6(((Float)this.range.getValue()).floatValue())) {
            this.Field2833 = null;
            this.Method3436();
            return;
        }
        if (this.Field2833 == null || Globals.mc.world.getBlockState(this.Field2833).getBlock() == Blocks.AIR) {
            this.Method3436();
            this.Field2839.getNanoTime();
            this.Field2833 = null;
        }
        if (this.Field2840 && this.Field2841 != -1) {
            Globals.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.Field2841));
            this.Field2840 = false;
        }
        int n = this.Method3437();
        if (((Boolean)this.silentswitch.getValue()).booleanValue() && this.Field2839.getLongNanoTime((int)((2000.0f + (float)((Integer)this.delay.getValue()).intValue()) * Manager.Field1645.Method2895())) && n != -1 && this.Field2833 != null && Globals.mc.world.getBlockState(this.Field2833).getBlock() != Blocks.AIR && this.Field2842) {
            this.Field2841 = Globals.mc.player.inventory.currentItem;
            Globals.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n));
            if (((Boolean)this.rotate.getValue()).booleanValue()) {
                this.Method3438((double)this.Field2833.getX() + 0.5, (double)this.Field2833.getY() - 0.5, (double)this.Field2833.getZ() + 0.5, (EntityPlayer)Globals.mc.player);
            }
            this.Field2840 = true;
            this.Field2842 = false;
        }
        Globals.mc.playerController.blockHitDelay = 0;
        if (((Boolean)this.reset.getValue()).booleanValue() && Globals.mc.gameSettings.keyBindUseItem.isKeyDown()) {
            Globals.mc.playerController.isHittingBlock = false;
        }
    }

    private void Method3427(Class26 class26) {
        if (this.Field2836) {
            return;
        }
        float f = this.Method3435();
        Color.RGBtoHSB(this.Field2835.getRed(), this.Field2835.getGreen(), this.Field2835.getBlue(), this.Field2837);
        float[] fArray = new float[3];
        Color.RGBtoHSB(((Color)this.endcolor.getValue()).getRed(), ((Color)this.endcolor.getValue()).getGreen(), ((Color)this.endcolor.getValue()).getBlue(), fArray);
        if (this.Field2837[0] == fArray[0]) {
            this.Field2836 = true;
        }
        float f2 = this.Field2837[0];
        this.Field2837[0] = this.Field2837[0] + f;
        if (this.Field2837[0] < fArray[0] && fArray[0] - f2 <= f || this.Field2837[0] > fArray[0] && this.Field2837[0] - fArray[0] <= f) {
            this.Field2837[0] = fArray[0];
            this.Field2836 = true;
        }
        if (!this.Field2836) {
            this.Field2835 = new Color(Color.HSBtoRGB(this.Field2837[0], this.Field2837[1], this.Field2837[2]));
        }
    }

    private boolean Method3426() {
        return this.mode.getValue() == Class634.Packet && (Boolean)this.render.getValue() != false;
    }

    private boolean Method3425() {
        return this.mode.getValue() == Class634.Packet && (Boolean)this.render.getValue() != false;
    }

    private boolean Method3424() {
        return this.mode.getValue() == Class634.Packet && (Boolean)this.render.getValue() != false;
    }

    private boolean Method3423() {
        return this.mode.getValue() == Class634.Packet;
    }

    private boolean Method3422() {
        return this.mode.getValue() == Class634.Damage;
    }

    private boolean Method3421() {
        return this.mode.getValue() == Class634.Damage;
    }
}
