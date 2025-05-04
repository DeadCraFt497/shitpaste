package me.ciruu.abyss.events.player;

import me.ciruu.abyss.events.MinecraftEvent;

public class EventPlayerPushOutOfBlocks
extends MinecraftEvent {
    public double dub_1;
    public double dub_2;
    public double dub_3;

    public EventPlayerPushOutOfBlocks(double d, double d2, double d3) {
        this.dub_1 = d;
        this.dub_2 = d2;
        this.dub_3 = d3;
    }
}
