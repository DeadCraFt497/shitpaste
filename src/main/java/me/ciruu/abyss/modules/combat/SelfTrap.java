package me.ciruu.abyss.modules.combat;

import java.util.function.Predicate;

import me.ciruu.abyss.*;
import me.ciruu.abyss.enums.Class109;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class285;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.events.player.EventPlayerUpdateWalking;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class SelfTrap
extends Module {
    private Setting toggle = new Setting("Toggle", "Toggles the features after being used", this, true);
    private Setting checkhole = new Setting("CheckHole", "", this, true);
    private Setting rotate = new Setting("Rotate", "", this, false);
    private int Field786 = -1;
    private BlockPos Field787 = null;
    @EventHandler
    private Listener Field788 = new Listener<EventPlayerUpdateWalking>(this::Method1032, new Predicate[0]);

    public SelfTrap() {
        super("SelfTrap", "Traps the player by placing obsidian over your head.", Class11.COMBAT, "");
        this.addSetting(this.toggle);
        this.addSetting(this.checkhole);
        this.addSetting(this.rotate);
    }

    public void Method1034(boolean bl) {
        for (; ; ) {
            if(bl == false) continue;
            if (this.isEnabled()) {
                bl = true;
            }
        } 
    }

    public void Method1038() {
        super.getEnable();
        this.Field786 = Globals.mc.player.inventory.currentItem;
    }

    public void Method1037() {
        super.getDisable();
        this.Method1039();
    }

    private void Method1039() {
        if (this.Field786 != -1) {
            Globals.mc.player.inventory.currentItem = this.Field786;
            Globals.mc.playerController.updateController();
        }
    }

    private boolean Method1040() {
        IBlockState iBlockState = Globals.mc.world.getBlockState(Class30.Method209((EntityPlayer)Globals.mc.player));
        return iBlockState.getBlock() != Blocks.AIR && iBlockState.getBlock() != Blocks.WATER && iBlockState.getBlock() != Blocks.LAVA;
    }

    private void Method1032(EventPlayerUpdateWalking eventPlayerUpdateWalking) {
        if (eventPlayerUpdateWalking.getClass53() != Class53.PRE) {
            return;
        }
        this.Field786 = Globals.mc.player.inventory.currentItem;
        if (this.Method1040()) {
            if (((Boolean)this.toggle.getValue()).booleanValue()) {
                this.Method1039();
            }
            return;
        }
        if (((Boolean)this.checkhole.getValue()).booleanValue() && !Class30.Method543((Entity)Globals.mc.player)) {
            return;
        }
        int n = Class155.Method544(BlockObsidian.class);
        if (n == -1) {
            Class547.printChatMessage("Can't find obsidian in hotbar!");
            this.Method1039();
            return;
        }
        Vec3d vec3d = Class29.getVector((Entity)Globals.mc.player, Globals.mc.getRenderPartialTicks());
        this.Field787 = new BlockPos(vec3d.x, vec3d.y, vec3d.z).up().up();
        if (Globals.mc.player.onGround) {
            Class285 class285 = Class110.Method1043(this.Field787);
            if (class285 == Class285.AlreadyBlockThere && !Globals.mc.world.getBlockState(this.Field787).getMaterial().isReplaceable()) {
                this.Method1039();
                return;
            }
            Globals.mc.player.inventory.currentItem = n;
            Globals.mc.playerController.updateController();
            if (class285 == Class285.NoNeighbors) {
                BlockPos[] blockPosArray;
                for (BlockPos blockPos : blockPosArray = new BlockPos[]{this.Field787.north(), this.Field787.south(), this.Field787.east(), this.Field787.west(), this.Field787.up(), this.Field787.down().west()}) {
                    Class109 class109;
                    Class285 class2852 = Class110.Method1043(blockPos);
                    if (class2852 == Class285.NoNeighbors || class2852 == Class285.NoEntityCollision || (class109 = Class110.Method1044(blockPos, 5.0f, false, false)) != Class109.Placed || !((Boolean)this.rotate.getValue()).booleanValue()) continue;
                    eventPlayerUpdateWalking.cancel();
                    float[] fArray = Class110.Method1045(new Vec3d((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ()));
                    eventPlayerUpdateWalking.setPitch(fArray[1]);
                    eventPlayerUpdateWalking.setYaw(fArray[0]);
                    this.Method1039();
                    return;
                }
                this.Method1039();
                return;
            }
            Class109 class109 = Class110.Method1044(this.Field787, 5.0f, false, false);
            if (class109 == Class109.Placed && ((Boolean)this.rotate.getValue()).booleanValue()) {
                eventPlayerUpdateWalking.cancel();
                float[] fArray = Class110.Method1045(new Vec3d((double)this.Field787.getX(), (double)this.Field787.getY(), (double)this.Field787.getZ()));
                eventPlayerUpdateWalking.setPitch(fArray[1]);
                eventPlayerUpdateWalking.setYaw(fArray[0]);
            }
            this.Method1039();
        }
    }
}
