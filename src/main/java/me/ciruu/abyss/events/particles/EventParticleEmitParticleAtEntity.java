package me.ciruu.abyss.events.particles;

import me.ciruu.abyss.events.MinecraftEvent;

public class EventParticleEmitParticleAtEntity
extends MinecraftEvent {
    public final int emit_value;

    public EventParticleEmitParticleAtEntity(int n) {
        this.emit_value = n;
    }

    public int getEmit() {
        return this.emit_value;
    }
}
