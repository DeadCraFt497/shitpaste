package me.ciruu.abyss.events.render;

import me.ciruu.abyss.events.MinecraftEvent;
import net.minecraft.item.ItemStack;

public class EventRenderToolTip
extends MinecraftEvent {
    public final ItemStack stack;
    public final int int_1;
    public final int int_3;

    public EventRenderToolTip(ItemStack itemStack, int n, int n2) {
        this.stack = itemStack;
        this.int_1 = n;
        this.int_3 = n2;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public int getInt1() {
        return this.int_1;
    }

    public int getInt2() {
        return this.int_3;
    }
}
