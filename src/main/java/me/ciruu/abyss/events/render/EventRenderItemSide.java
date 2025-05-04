package me.ciruu.abyss.events.render;

import me.ciruu.abyss.events.MinecraftEvent;

public class EventRenderItemSide
extends MinecraftEvent {
    public float x = 1.0f;
    public float y = 1.0f;
    public float z = 1.0f;
    public boolean bool;

    public EventRenderItemSide(boolean bl) {
        this.bool = bl;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float f) {
        this.x = f;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float f) {
        this.y = f;
    }

    public float getZ() {
        return this.z;
    }

    public void setZ(float f) {
        this.z = f;
    }

    public boolean getBool() {
        return this.bool;
    }
}
