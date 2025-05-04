package me.ciruu.abyss.modules.movement;

import java.util.function.Predicate;
import me.ciruu.abyss.Class26;
import me.ciruu.abyss.Class85;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.modules.movement.Speed;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;

public class HoleTP
extends Module {
    private final double[] Field3590 = new double[]{0.42, 0.75};
    private int Field3591;
    private boolean Field3592 = false;
    @EventHandler
    private Listener Field3593 = new Listener<Class26>(this::Method4334, new Predicate[0]);

    public HoleTP() {
        super("HoleTP", "Teleports you to nearby holes.", Class11.MOVEMENT, "");
    }

    public boolean Method4335() {
        BlockPos blockPos = new BlockPos(Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ);
        IBlockState iBlockState = Globals.mc.world.getBlockState(blockPos);
        return this.Method4336(iBlockState, blockPos);
    }

    private double Method4337() {
        for (double d = Globals.mc.player.posY; d > 0.0; d -= 0.001) {
            if (Globals.mc.world.getBlockState(new BlockPos(Globals.mc.player.posX, d, Globals.mc.player.posZ)).getBlock() instanceof BlockSlab || Globals.mc.world.getBlockState(new BlockPos(Globals.mc.player.posX, d, Globals.mc.player.posZ)).getBlock().getDefaultState().getCollisionBoundingBox((IBlockAccess)Globals.mc.world, new BlockPos(0, 0, 0)) == null) continue;
            return d;
        }
        return -1.0;
    }

    private boolean Method4336(IBlockState iBlockState, BlockPos blockPos) {
        if (iBlockState.getBlock() != Blocks.AIR) {
            return false;
        }
        if (Globals.mc.player.getDistanceSq(blockPos) < 1.0) {
            return false;
        }
        if (Globals.mc.world.getBlockState(blockPos.up()).getBlock() != Blocks.AIR) {
            return false;
        }
        if (Globals.mc.world.getBlockState(blockPos.up(2)).getBlock() != Blocks.AIR) {
            return false;
        }
        return Class85.Method206(blockPos) || Class85.Method1585(blockPos) || Class85.Method208(blockPos) || Class85.Method1586(blockPos);
    }

    private boolean Method4338() {
        double d = Globals.mc.player.posY - 0.03;
        for (int i = MathHelper.floor((double)Globals.mc.player.posX); i < MathHelper.ceil((double)Globals.mc.player.posX); ++i) {
            for (int j = MathHelper.floor((double)Globals.mc.player.posZ); j < MathHelper.ceil((double)Globals.mc.player.posZ); ++j) {
                BlockPos blockPos = new BlockPos(i, MathHelper.floor((double)d), j);
                if (!(Globals.mc.world.getBlockState(blockPos).getBlock() instanceof BlockLiquid)) continue;
                return true;
            }
        }
        return false;
    }

    private boolean Method4339() {
        double d = Globals.mc.player.posY + 0.01;
        for (int i = MathHelper.floor((double)Globals.mc.player.posX); i < MathHelper.ceil((double)Globals.mc.player.posX); ++i) {
            for (int j = MathHelper.floor((double)Globals.mc.player.posZ); j < MathHelper.ceil((double)Globals.mc.player.posZ); ++j) {
                BlockPos blockPos = new BlockPos(i, (int)d, j);
                if (!(Globals.mc.world.getBlockState(blockPos).getBlock() instanceof BlockLiquid)) continue;
                return true;
            }
        }
        return false;
    }

    private void Method4334(Class26 class26) {
        if (Globals.mc.world == null || Globals.mc.player == null || Manager.moduleManager.isModuleEnabled(Speed.class)) {
            return;
        }
        if (!Globals.mc.player.onGround) {
            if (Globals.mc.gameSettings.keyBindJump.isKeyDown()) {
                this.Field3592 = true;
            }
        } else {
            this.Field3592 = false;
        }
        if (!this.Field3592 && (double)Globals.mc.player.fallDistance < 0.5 && this.Method4335() && Globals.mc.player.posY - this.Method4337() <= 1.125 && Globals.mc.player.posY - this.Method4337() <= 0.95 && !this.Method4338() && !this.Method4339()) {
            if (!Globals.mc.player.onGround) {
                ++this.Field3591;
            }
            if (!(Globals.mc.player.onGround || Globals.mc.player.isInsideOfMaterial(Material.WATER) || Globals.mc.player.isInsideOfMaterial(Material.LAVA) || Globals.mc.gameSettings.keyBindJump.isKeyDown() || Globals.mc.player.isOnLadder() || this.Field3591 <= 0)) {
                BlockPos blockPos = new BlockPos(Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ);
                for (double d : this.Field3590) {
                    Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position((double)((float)blockPos.getX() + 0.5f), Globals.mc.player.posY - d, (double)((float)blockPos.getZ() + 0.5f), true));
                }
                Globals.mc.player.setPosition((double)((float)blockPos.getX() + 0.5f), this.Method4337() + 0.1, (double)((float)blockPos.getZ() + 0.5f));
                this.Field3591 = 0;
            }
        }
    }
}
