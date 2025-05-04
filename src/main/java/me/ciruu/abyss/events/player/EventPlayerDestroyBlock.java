package me.ciruu.abyss.events.player;

import me.ciruu.abyss.events.MinecraftEvent;
import net.minecraft.util.math.BlockPos;

public class EventPlayerDestroyBlock
extends MinecraftEvent {
    public BlockPos blockPos;

    public EventPlayerDestroyBlock(BlockPos blockPos) {
        this.blockPos = blockPos;
    }
}
