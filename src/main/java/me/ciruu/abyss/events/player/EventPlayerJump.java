package me.ciruu.abyss.events.player;

import me.ciruu.abyss.events.MinecraftEvent;

public class EventPlayerJump
extends MinecraftEvent {
    public double doub_1;
    public double doub_2;

    public EventPlayerJump(double d, double d2) {
        this.doub_1 = d;
        this.doub_2 = d2;
    }
}
