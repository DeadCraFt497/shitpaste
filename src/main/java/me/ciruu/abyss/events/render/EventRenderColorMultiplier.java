package me.ciruu.abyss.events.render;

import me.ciruu.abyss.events.MinecraftEvent;

public class EventRenderColorMultiplier
extends MinecraftEvent {
    public float alpha;

    public void setAlpha(float f) {
        this.alpha = f;
    }

    public float getAlpha() {
        return this.alpha;
    }
}
