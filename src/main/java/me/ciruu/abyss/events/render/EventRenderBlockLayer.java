package me.ciruu.abyss.events.render;

import me.ciruu.abyss.events.MinecraftEvent;
import net.minecraft.block.Block;
import net.minecraft.util.BlockRenderLayer;

public class EventRenderBlockLayer
extends MinecraftEvent {
    public BlockRenderLayer blockRenderLayer;
    public Block block;

    public EventRenderBlockLayer(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return this.block;
    }

    public void setBlockRenderLayer(BlockRenderLayer blockRenderLayer) {
        this.blockRenderLayer = blockRenderLayer;
    }

    public BlockRenderLayer getBlockRenderLayer() {
        return this.blockRenderLayer;
    }
}
