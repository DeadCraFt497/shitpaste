package me.ciruu.abyss.events.liquid;

import me.ciruu.abyss.events.MinecraftEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class EventLiquidCollisionBB
extends MinecraftEvent {
    private final BlockPos blockPos;
    private AxisAlignedBB axis;

    public EventLiquidCollisionBB(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    public AxisAlignedBB getAxis() {
        return this.axis;
    }

    public void getAxisWitValue(AxisAlignedBB axisAlignedBB) {
        this.axis = axisAlignedBB;
    }
}
