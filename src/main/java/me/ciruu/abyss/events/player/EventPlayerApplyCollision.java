package me.ciruu.abyss.events.player;

import me.ciruu.abyss.events.MinecraftEvent;
import net.minecraft.entity.Entity;

public class EventPlayerApplyCollision
extends MinecraftEvent {
    public Entity entity;

    public EventPlayerApplyCollision(Entity entity) {
        this.entity = entity;
    }
}
