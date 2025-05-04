package me.ciruu.abyss.events.player;

import me.ciruu.abyss.events.MinecraftEvent;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class EventPlayerDamageBlock
extends MinecraftEvent {
    public BlockPos blockPos;
    public EnumFacing Field2;

    public EventPlayerDamageBlock(BlockPos blockPos, EnumFacing enumFacing) {
        this.blockPos = blockPos;
        this.getEnumValueFacing(enumFacing);
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    public EnumFacing getEnumValue() {
        return this.Field2;
    }

    public void getEnumValueFacing(EnumFacing enumFacing) {
        this.Field2 = enumFacing;
    }
}
