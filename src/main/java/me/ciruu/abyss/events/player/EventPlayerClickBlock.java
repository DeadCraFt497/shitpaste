package me.ciruu.abyss.events.player;

import me.ciruu.abyss.events.MinecraftEvent;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class EventPlayerClickBlock
extends MinecraftEvent {
    public BlockPos blockPos;
    public EnumFacing enumerable;

    public EventPlayerClickBlock(BlockPos blockPos, EnumFacing enumFacing) {
        this.blockPos = blockPos;
        this.enumerable = enumFacing;
    }
}
